package com.example.final_project_rss_reader;


import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;


public class ViewSavedActivity extends AppCompatActivity {
    // stores RSS feed information
    SQLiteDatabase db;
    public int id;
    Cursor results;
    ArrayList<RssItem> savedItems = new ArrayList();
    ListView listView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feed);
        loadDatabaseData();
        listView = (ListView) findViewById(R.id.rssFeedItemListView);
        ListView listView = (ListView) findViewById(R.id.rssFeedItemListView);

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
    private void loadDatabaseData() {
        // Log.i("DB: ", String.valueOf(savedItems));
        Database dbOpener = new Database(this);
        db = dbOpener.getWritableDatabase();

        String[] column = { Database.COL_ID,Database.COL_TITLE, Database.COL_DESCRIPTION};

        results = db.query(false, Database.TABLE_NAME, column, null, null,
                null, null, null, null);

        int idColIndex = results.getColumnIndex(Database.COL_ID);
        int titleColIndex = results.getColumnIndex(Database.COL_TITLE);
        int descColIndex = results.getColumnIndex(Database.COL_DESCRIPTION);


        while (results.moveToNext()) {
            String title = results.getString(titleColIndex);
            String description = results.getString(descColIndex);
            long id = results.getLong(idColIndex);
            savedItems.add(new RssItem(id, title, description));
        }
        printCursor(results, db.getVersion());
    }
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
    protected void deleteSavedItem(RssItem item){
        db.delete(Database.TABLE_NAME, Database.COL_ID + "= ?", new String[]{Long.toString(item.getId())});
    }
}
