package com.example.final_project_rss_reader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Main Class for the BBC RSS new reader, opens the first activity letting the user choose to open the RSS feed list or
 * open previously saved RSS feeds
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check the shared preferences for a saved email and display it in the email box, otherwise display an empty string.

    }

    /**
     * Method to load BBC RSS news items into ViewFeedActivity when the "Launch RSS Feed" button is clicked
     *
     * @param view - View to hold the current activity
     */
    public void launchRSSFeed(View view) {
        Intent toFeedIntent = new Intent(this, ViewFeedActivity.class);
        this.startActivity(toFeedIntent);
    }

    /**
     * Method to load previously saved BBC RSS news items from ViewSavedActivity when the "Launch Saved Items" button is clicked
     *
     * @param view - View to hold the current activity
     */
    public void launchSavedItems(View view) {
        Intent toSavedIntent = new Intent(this, ViewSavedActivity.class);
        this.startActivity(toSavedIntent);
    }
}