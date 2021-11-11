package com.example.final_project_rss_reader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent addFeedIntent = new Intent(this, AddFeedActivity.class);
//        this.startActivity(addFeedIntent);

//        ((Button) findViewById(R.id.openFeedButton)).setOnClickListener(clk -> {
//            startActivity(new Intent(this, ViewFeedActivity.class));
//        });
    }

    public void gotoAddFeed(View view) {
        Intent addFeedIntent = new Intent(this, AddFeedActivity.class);
        this.startActivity(addFeedIntent);
    }
}