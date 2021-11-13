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

    public void gotoAddFeed(View view) {
        Intent addFeedIntent = new Intent(this, AddFeedActivity.class);
        this.startActivity(addFeedIntent);
        // intent to go to AddFeedActivity activity
    }
}