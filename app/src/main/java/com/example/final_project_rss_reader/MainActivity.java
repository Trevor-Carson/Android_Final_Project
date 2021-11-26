package com.example.final_project_rss_reader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
        // launch activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // sets the layout for MainActivity as activity_main.xml
    }

    public void launchRSSFeed(View view) {
        Intent toFeedIntent = new Intent(this, ViewFeedActivity.class);
        this.startActivity(toFeedIntent);
        // intent to go to ViewFeedActivity activity
    }
    public void launchSavedItems(View view) {
       Intent toSavedIntent = new Intent(this, ViewSavedActivity.class);
        this.startActivity(toSavedIntent);
        // intent to go to ViewSavedActivity activity
    }
}