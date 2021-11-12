package com.example.final_project_rss_reader;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class EditRssFeedActivity extends AppCompatActivity {
    List<RssFeed> feeds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_rss_feed);

        feeds = new Database(this).getRssFeeds();
        RssFeedsAdapter adapter = new RssFeedsAdapter(this, feeds);

        ListView listView = findViewById(R.id.editRssListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(EditRssFeedActivity.this);
                dialog.setTitle("Remove Feed");
                dialog.setMessage("Are you sure you want to remove this feed?");

                final int positionToRemove = position;

                dialog.setNegativeButton("No", null);
                dialog.setPositiveButton("Yes", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        RssFeed selectedFeed = feeds.get(positionToRemove);
                        new Database(EditRssFeedActivity.this).deleteRssFeed(selectedFeed);

                        feeds = new Database(EditRssFeedActivity.this).getRssFeeds();
                        RssFeedsAdapter adapterNew = new RssFeedsAdapter(EditRssFeedActivity.this, feeds);

                        listView.setAdapter(adapterNew);
                    }
                });
                dialog.show();
            }
        });
    }
}