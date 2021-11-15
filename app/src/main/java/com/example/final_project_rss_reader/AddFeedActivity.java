package com.example.final_project_rss_reader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddFeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feed);
        // sets layout for AddFeedActivity as activity_add_feed.xml

        Button saveButton = findViewById(R.id.addFeedButton); // button to save a feed object
        final EditText feedAddress = findViewById(R.id.addFeedEditText); // edittext to save feed address
        final EditText feedName = findViewById(R.id.addFeedNameEditText); // edittext to save feed name
        saveButton.setOnClickListener(view -> {
            if (feedAddress.getText().length() < 6) {
                Toast.makeText(getApplicationContext(), "RSS feed address is too short", Toast.LENGTH_LONG).show();
                return;
                // if feed address is less than 6 characters, display the toast message
            }

            Database db = new Database(AddFeedActivity.this);
            RssFeed rssFeed = new RssFeed(feedName.getText().toString(), feedAddress.getText().toString());

            db.addRssFeed(rssFeed);
            // adds selected RSS feed to the local database
            Toast.makeText(getApplicationContext(), "RSS feed added!", Toast.LENGTH_LONG).show();
            feedName.setText("");
            feedAddress.setText("");
            // empties EditText fields after an RSS feed object is added to the list
        });
    }

    public void gotoViewFeeds(View view) {
        Intent viewFeedsIntent = new Intent(this, EditRssFeedActivity.class);
        this.startActivity(viewFeedsIntent);
        // intent to launch EditRssFeedActivity
    }
}