package com.example.final_project_rss_reader;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "savedRSS";
    public static final String TABLE_NAME = "feed";
    public static final String COL_ID = "id";
    public static final String COL_TITLE = "title";
    public static final String COL_DESCRIPTION = "description";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creates database/table
        String CREATE_FEEDS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_TITLE + " TEXT,"
                + COL_DESCRIPTION + " TEXT" + ")";
        db.execSQL(CREATE_FEEDS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

