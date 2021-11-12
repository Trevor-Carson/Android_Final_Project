package com.example.final_project_rss_reader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class RssFeedsAdapter extends ArrayAdapter<RssFeed> {

    public RssFeedsAdapter(@NonNull Context context, List<RssFeed> feeds) {
        super(context, 0, feeds);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RssFeed feed = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.rss_edit_feed_item_row, parent, false);
        }

        TextView feedAddress = convertView.findViewById(R.id.rssEditFeedAddressTextView);
        TextView feedTitle = convertView.findViewById(R.id.rssEditFeedTitleTextView);

        feedAddress.setText(feed.rssFeedAddress);
        feedTitle.setText(feed.rssFeedTitle);

        return convertView;
    }
}
