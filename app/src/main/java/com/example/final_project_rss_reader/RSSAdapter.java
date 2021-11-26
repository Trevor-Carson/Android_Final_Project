package com.example.final_project_rss_reader;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RSSAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<RssItem> itemList;

    public RSSAdapter(Activity activity, ArrayList list) {
        this.activity = activity;
        this.itemList = list;
    }

    public RSSAdapter() {
    }

    @Override
    public int getCount() {
        return itemList.size();
        // return rssItemList.size();
    }

    @Override
    public RssItem getItem(int position) {
        return itemList.get(position);
        // return rssItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = convertView;
        RssItem rssItem = itemList.get(position);

        if (view == null) {
            view = inflater.inflate(R.layout.rss_feed_item_row, parent, false);

        }
        TextView title = view.findViewById(R.id.rssTitleTextView);
        title.setText(rssItem.getTitle());
        TextView description = view.findViewById(R.id.rssArticleTextView);
        description.setText(rssItem.getDescription());

        return view;
    }
}