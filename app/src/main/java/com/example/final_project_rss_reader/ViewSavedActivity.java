package com.example.final_project_rss_reader;


import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to view store saved/favorite RSS feeds from a database
 */
public class ViewSavedActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

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
    /**
     * Integer to store the length of a users search keyword
     */
    int textLength;
    /**
     * An array to hold RSSItems refined by the users search parameters
     */
    ArrayList<RssItem> rssItemsFiltered;
    /**
     * String to hold the specified RSS feeds title
     */
    public static final String ITEM_TITLE = "TITLE";
    /**
     * String to hold the specified RSS feeds date
     */
    public static final String ITEM_DATE = "DATE";
    /**
     * String to hold the specified RSS feeds description
     */
    public static final String ITEM_DESCRIPTION = "DESCRIPTION";
    /**
     * String to hold the specified RSS feeds link
     */
    public static final String ITEM_LINK = "LINK";
    /**
     * String to hold the specified RSS feeds position
     */
    public static final String ITEM_POSITION = "POSITION";
    /**
     * String to hold the specified RSS feeds id number
     */
    public static final String ITEM_ID = "ID";
    ListView listView;

    AlertDialog alertHelp;
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
    RssItem item;
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

        DrawerLayout drawer = findViewById(R.id.navigation_drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);

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
        listView.setTextFilterEnabled(true);
        RSSAdapter adapter1 = new RSSAdapter();

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchBar.setText("");
            }
        });
        searchBar.addTextChangedListener(new TextWatcher() {


            /**
             * Method to update the adapter list
             * @param charSequence - holds the character sequence for the adapter
             * @param i - first iterator
             * @param i2 - second iterator
             * @param i3 - third iterator
             */
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                listView.invalidate();
                adapter1.notifyDataSetChanged();
            }

            /**
             * Method to update the adapter list
             * @param s - holds the current characters value
             * @param start - holds the starting character position
             * @param before - holds the last characters position
             * @param count - counter for the array
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter1.getFilter().filter(s.toString());
                textLength = s.length();
                rssItemsFiltered = new ArrayList<RssItem>();
                for (RssItem ll : savedItems) {
                    if (textLength <= ll.getTitle().length()) {
                        if (ll.getTitle().toLowerCase().contains(s.toString().toLowerCase())) {
                            rssItemsFiltered.add(ll);
                        }
                    }
                }
                RSSAdapter adapter = new RSSAdapter(ViewSavedActivity.this, rssItemsFiltered);
                listView.setAdapter(adapter);
            }

            /**
             * Method to update the adapter list
             * @param s -  This is the interface for text whose content and markup can be changed
             */
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        RSSAdapter adapter = new RSSAdapter(ViewSavedActivity.this, savedItems);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        boolean isTablet = findViewById(R.id.frameLayout) != null;

        listView.setOnItemLongClickListener((p, b, pos, id) -> {
            if (textLength == 0) {
                item = savedItems.get(pos);
            } else {
                item = rssItemsFiltered.get(pos);
            }
            AlertDialog alertDelete = new AlertDialog.Builder(this)
                    .setTitle(String.valueOf(getText(R.string.article_delete)))
                    .setMessage(((String.valueOf(getText(R.string.row_id))) + (pos + 1)) + "\n"
                            + (String.valueOf(getText(R.string.database_id))) + (adapter.getItemId(pos)))

                    .setPositiveButton(getString(R.string.confirm), (dialog, which) -> {
                        deleteSavedItem(item);
                        savedItems.remove(pos);
                        adapter.notifyDataSetChanged();

                    })
                    .setNegativeButton(getString(R.string.deny), null)
                    .create();
            alertDelete.show();
            return true;

        });
        listView.setOnItemClickListener(((parent, view, position, id) -> {
            Bundle dataToPass = new Bundle();
            if (textLength == 0) {
                dataToPass.putString(ITEM_TITLE, savedItems.get(position).getTitle());
                dataToPass.putString(ITEM_DATE, savedItems.get(position).getPubDate());
                dataToPass.putString(ITEM_DESCRIPTION, savedItems.get(position).getDescription());
                dataToPass.putString(ITEM_LINK, savedItems.get(position).getLink());

            } else {

                dataToPass.putString(ITEM_TITLE, rssItemsFiltered.get(position).getTitle());
                dataToPass.putString(ITEM_DATE, rssItemsFiltered.get(position).getPubDate());
                dataToPass.putString(ITEM_DESCRIPTION, rssItemsFiltered.get(position).getDescription());
                dataToPass.putString(ITEM_LINK, rssItemsFiltered.get(position).getLink());
            }
            dataToPass.putInt(ITEM_POSITION, position);
            dataToPass.putLong(ITEM_ID, id);
            RSSFragment fragment = new RSSFragment();

            if (isTablet) {
                fragment.setArguments(dataToPass);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, fragment)
                        .commit();
            } else {
                Intent nextActivity = new Intent(ViewSavedActivity.this, EmptyActivity.class);
                nextActivity.putExtras(dataToPass);
                startActivity(nextActivity);

            }
        }));
        alertHelp = new AlertDialog.Builder(this)
                .setTitle(String.valueOf(getString(R.string.help_text)))
                .setPositiveButton(String.valueOf(getString(R.string.confirm_help)), (dialog, which) -> {
                })
                .create();

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
            case R.id.help:
                alertHelp.show();
        }

        return true;
    }
    public boolean onNavigationItemSelected( MenuItem item) {

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
            case R.id.help:
                alertHelp.show();
        }

        DrawerLayout drawerLayout = findViewById(R.id.navigation_drawer);
        drawerLayout.closeDrawer(GravityCompat.START);

        return false;
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
