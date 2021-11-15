package com.example.final_project_rss_reader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    SQLiteDatabase db = this.getWritableDatabase();

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "rssFeeds";
    private static final String TABLE_FEEDS = "feeds";
    private static final String KEY_ID = "id";
    private static final String KEY_FEEDTITLE = "title";
    private static final String KEY_FEEDADDRESS = "address";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creates database/table
        String CREATE_FEEDS_TABLE = "CREATE TABLE " + TABLE_FEEDS + "(" + KEY_ID + " INTEGER_PRIMARY_KEY," + KEY_FEEDTITLE + " TEXT," + KEY_FEEDADDRESS + " TEXT" + ")";
        db.execSQL(CREATE_FEEDS_TABLE);
        getRssFeeds(); // loads the rss data in the database
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FEEDS);
        onCreate(db);
    }

    public void addRssFeed(RssFeed feed) {
        // Open up a connection to the database

        // ContentValues stores an array of values
        ContentValues values = new ContentValues();

        values.put(KEY_FEEDTITLE, feed.rssFeedTitle);
        values.put(KEY_FEEDADDRESS, feed.rssFeedAddress);

        db.insert(TABLE_FEEDS, null, values);
        db.close();
    }

    public List<RssFeed> getRssFeeds() {
        List<RssFeed> results = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_FEEDS;
        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            // this currently executes without an exception when an item is in the the database already

                // Selects the first result from the cursor
                if (cursor.moveToFirst()) {
                    do {
                        RssFeed rssFeed = new RssFeed(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
                        results.add(rssFeed);
                    } while (cursor.moveToNext()); // Runs as long as the cursor has an item to move to next
                }
//                cursor.moveToFirst();
                printCursor(cursor, db.getVersion());
                cursor.close();
                db.close();
        } catch (Exception e) {
//            Toast.makeText(MainActivity.this, "not OK", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        printCursor(cursor, db.getVersion());
        cursor.close();
        db.close();
        return results;

    }

    public void deleteRssFeed(RssFeed feed) {
        // method to delete RSS feed from local database
        db.delete(TABLE_FEEDS, KEY_ID + " = ? ", new String[]{String.valueOf(feed.id)});
        db.close();
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

}
