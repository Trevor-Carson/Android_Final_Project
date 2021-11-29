package com.example.final_project_rss_reader;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Class to handle creating the database and calling CRUD actions for the saved/favorite BBC RSS feeds
 */
public class Database extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;              // Integer holding databases version number
    private static final String DATABASE_NAME = "savedRSS";     // String holding name of the database
    public static final String TABLE_NAME = "feed";             // String holding database table name
    public static final String COL_ID = "id";                   // String holding the id number assigned to the saved feed
    public static final String COL_TITLE = "title";             // String holding the title assigned to the saved feed
    public static final String COL_DESCRIPTION = "description"; // String holding the description text for the saved feed

    // Parameterized child constructor for the database object
    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Override method for creating a database entry for a favorite feed
    @Override
    public void onCreate(SQLiteDatabase db) {
        // creates database/table
        String CREATE_FEEDS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_TITLE + " TEXT,"
                + COL_DESCRIPTION + " TEXT" + ")";
        db.execSQL(CREATE_FEEDS_TABLE);
    }

    // Override method to create the database table for favorite feeds
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Method to create the database table on older Android API versions
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

