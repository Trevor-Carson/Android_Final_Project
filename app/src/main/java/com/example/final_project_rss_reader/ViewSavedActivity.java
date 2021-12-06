package com.example.final_project_rss_reader;


import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to view store saved/favorite RSS feeds from a database
 */
public class ViewSavedActivity extends AppCompatActivity {
    /**
     * A hash map to store menu items
     */
    private static final Map<Integer, Integer> optionsItemMap = new HashMap<>();
    /**
     * Opensource SQL database that stores data to a text file on a device
     */
    SQLiteDatabase db;
    /**
     * Integer to store the value of the RSS feed in the database
     */
    public int id;
    /**
     * Contain the result set of a query made against a database
     */
    Cursor results;
    /**
     * Array list to hold the values of RSS feeds in the database
     */
    ArrayList<RssItem> savedItems = new ArrayList();
    /**
     * A list view is an adapter view that does not know the details, such as type and contents, of the views it contains
     */
    ListView listView;

    /**
     * A button to clear the editText for searching RSS feeds
     */
    Button clearButton;
    /**
     * An edit text used to hold the value for the editText search bar
     */
    EditText searchBar;

    /**
     * Method to show saved RSS feeds from a database when the activity is called/opened
     *
     * @param savedInstanceState - Object to store bundled information to pass to the view feed activity
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feed);
        loadDatabaseData();
        listView = (ListView) findViewById(R.id.rssFeedItemListView);
        ListView listView = (ListView) findViewById(R.id.rssFeedItemListView);

        /**
         * Initialize the toolbar
         */
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * A hashmap to map the buttons to their order
         */
        optionsItemMap.put(R.id.home, 1);
        optionsItemMap.put(R.id.rss, 2);
        optionsItemMap.put(R.id.favorites, 3);

        /**
         * Method to clear the RSS search editText on button click
         */
        searchBar = (EditText) findViewById(R.id.editTextSearch);
        clearButton = (Button) findViewById(R.id.buttonClearTextSearch);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchBar.setText("");
            }
        });

        RSSAdapter adapter = new RSSAdapter(ViewSavedActivity.this, savedItems);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        listView.setOnItemLongClickListener((p, b, pos, id) -> {
            RssItem item = savedItems.get(pos);
            AlertDialog alertDelete = new AlertDialog.Builder(this)
                    .setTitle("Delete this article?")
                    .setMessage((("Row ID: ") + (pos + 1)) + "\n"
                            + ("Database ID: ") + (adapter.getItemId(pos)))

                    .setPositiveButton("Yes", (dialog, which) -> {
                        deleteSavedItem(item);
                        savedItems.remove(pos);
                        adapter.notifyDataSetChanged();

                    })
                    .setNegativeButton("No", null)
                    .create();
            alertDelete.show();
            return true;

        });
    }

    /**
     * Method to load the top menu
     * @param menu - selects the menu to load
     * @return - returns the inflated selected menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    /**
     * Method to assign actions per selected menu item
     * @param item - selects the trigger menu item
     * @return - returns the set action for the selected menu item
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String msg;

        if (optionsItemMap.containsKey(item.getItemId())) {
            switch (item.getItemId()) {
                case R.id.home:
                    startActivity(new Intent(this, MainActivity.class));
                    break;
                case R.id.rss:
                    startActivity(new Intent(this, ViewFeedActivity.class));
                    break;
                case R.id.favorites:
                    startActivity(new Intent(this, ViewSavedActivity.class));
                    break;
            }
        } else {
            msg = "Press and hold an RSS feed to add or remove it from favorites";
            Toast.makeText(ViewSavedActivity.this, msg, Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Method to load the RSS feed information from a database
     */
    private void loadDatabaseData() {
        // Log.i("DB: ", String.valueOf(savedItems));
        Database dbOpener = new Database(this);
        db = dbOpener.getWritableDatabase();

        String[] column = {Database.COL_ID, Database.COL_TITLE, Database.COL_DESCRIPTION};

        results = db.query(false, Database.TABLE_NAME, column, null, null,
                null, null, null, null);

        /** Integer to hold the database column number for the RSS ID */
        int idColIndex = results.getColumnIndex(Database.COL_ID);
        /** Integer to hold the database column number for the RSS title */
        int titleColIndex = results.getColumnIndex(Database.COL_TITLE);
        /** Integer to hold the database column number for the RSS description */
        int descColIndex = results.getColumnIndex(Database.COL_DESCRIPTION);


        while (results.moveToNext()) {
            /** String to hold the value of the RSS title from the database */
            String title = results.getString(titleColIndex);
            /** String to hold the value of the RSS description from the database */
            String description = results.getString(descColIndex);
            /** Long to hold the value of the RSS id from the database */
            long id = results.getLong(idColIndex);
            savedItems.add(new RssItem(id, title, description));
        }
        printCursor(results, db.getVersion());
    }

    /**
     * Method to print an RSS feed based on its position in the database for debugging purposes
     *
     * @param c       - Object to store the current cursor/database item
     * @param version - integer to store the current version of the database
     */
    protected void printCursor(Cursor c, int version) {
        // for debugging purposes
        if (c.moveToFirst()) {
            Log.i("version number", String.valueOf(db.getVersion()));
            Log.i("number of cursor columns", String.valueOf(c.getColumnCount()));
            Log.i("name of cursor columns", Arrays.toString(c.getColumnNames()));
            Log.i("number of cursor results", String.valueOf(c.getCount()));
            Log.i("cursor row results", DatabaseUtils.dumpCursorToString(c));
        }
    }

    /**
     * Method to delete a saved item in the database
     *
     * @param item - RssItem object call from the database
     */
    protected void deleteSavedItem(RssItem item) {
        db.delete(Database.TABLE_NAME, Database.COL_ID + "= ?", new String[]{Long.toString(item.getId())});
    }
}
