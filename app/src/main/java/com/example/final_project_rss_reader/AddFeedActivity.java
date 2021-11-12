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

        Button saveButton = findViewById(R.id.addFeedButton);
        final EditText feedAddress = findViewById(R.id.addFeedEditText);
        final EditText feedName = findViewById(R.id.addFeedNameEditText);

        saveButton.setOnClickListener(view -> {
            if (feedAddress.getText().length() < 6) {
                Toast.makeText(getApplicationContext(), "Rss feed address is too short", Toast.LENGTH_LONG).show();
                return;
            }

            Database db = new Database(AddFeedActivity.this);
            RssFeed rssFeed = new RssFeed(feedName.getText().toString(), feedAddress.getText().toString());

            db.addRssFeed(rssFeed);
            Toast.makeText(getApplicationContext(), "Rss feed added!", Toast.LENGTH_LONG).show();
            feedName.setText("");
            feedAddress.setText("");
        });
    }

    public void gotoViewFeeds(View view) {
        Intent viewFeedsIntent = new Intent(this, EditRssFeedActivity.class);
        this.startActivity(viewFeedsIntent);
    }
}