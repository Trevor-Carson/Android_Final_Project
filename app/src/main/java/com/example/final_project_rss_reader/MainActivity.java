package com.example.final_project_rss_reader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Main Class for the BBC RSS new reader, opens the first activity letting the user choose to open the RSS feed list or
 * open previously saved RSS feeds
 */
public class MainActivity extends AppCompatActivity {

    // Override method to set the initial layout to the MainActivity when the application initially opens
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // sets the layout for MainActivity as activity_main.xml
    }

    // Method to load BBC RSS news items into ViewFeedActivity when the "Launch RSS Feed" button is clicked
    public void launchRSSFeed(View view) {
        Intent toFeedIntent = new Intent(this, ViewFeedActivity.class);
        this.startActivity(toFeedIntent);
    }

    // Method to load previously saved BBC RSS news items from ViewSavedActivity when the "Launch Saved Items" button is clicked
    public void launchSavedItems(View view) {
       Intent toSavedIntent = new Intent(this, ViewSavedActivity.class);
        this.startActivity(toSavedIntent);
    }
}