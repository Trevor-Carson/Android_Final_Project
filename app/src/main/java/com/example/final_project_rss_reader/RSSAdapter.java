package com.example.final_project_rss_reader;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Class to get information about the current RSS feeds stored in the database
 * and create a list of RSS Feeds to be filtered and passed to the next activity
 */
public class RSSAdapter extends BaseAdapter implements Filterable {

    private Activity activity;              // Activity object to hold information about activities
    private ArrayList<RssItem> itemList;    // Array to store sever RssItem Objects

    // Parameterized constructor for RSSAdapter
    public RSSAdapter(Activity activity, ArrayList<RssItem> list) {
        this.activity = activity;
        this.itemList = list;
    }

    // Non parameterized constructor for RSSAdapter
    public RSSAdapter() {
    }

    // Method to find the amount of RSS feeds stored in the RSSItem array list
    @Override
    public int getCount() {
        return itemList.size();
    }

    // Method to return a specific RSS feed from the array based on its position
    @Override
    public RssItem getItem(int position) {
        return itemList.get(position);
    }

    // Method to return a specific RSS feed ID from the array based on its position
    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    // Method to populate RSS list items
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater(); // Instantiates a layout XML file into its corresponding View objects
        View view = convertView;                                // Creates a base object for the inflater widget
        RssItem rssItem = itemList.get(position);               // Object to store a single rss items position

        if (view == null) {
            view = inflater.inflate(R.layout.rss_feed_item_row, parent, false);

        }
        TextView title = view.findViewById(R.id.rssTitleTextView);
        title.setText(rssItem.getTitle());

        return view;    //
    }

    // Override method to generate search results based on user input
    @Override
    public Filter getFilter() {
        return new Filter() {

            // Override method to create a readable string and filter the rss feeds based on user input
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                charSequence = charSequence.toString().toLowerCase();
                FilterResults filterResults = new FilterResults();
                if (charSequence == null || charSequence.length() == 0){
                    filterResults.values = itemList;
                    filterResults.count = itemList.size();
                } else {
                    // performs filter operation
                    ArrayList<RssItem> itemsFiltered = new ArrayList<>();
                    for (RssItem l : itemList){ // loops through itemList
                        if (l.getTitle().startsWith(charSequence.toString())){
                            itemsFiltered.add(l); // adds items to the list that match the filter
                        }
                    }
                    filterResults.values = itemsFiltered; // sets values to the itemsFiltered list
                    filterResults.count = itemsFiltered.size();

                }
                return filterResults;   // Returns the users specified search results
            }

            // Override method to populate the list view with the filtered results
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                // inform the adapter about the new list filtered

                itemList = (ArrayList<RssItem>) filterResults.values;
                notifyDataSetChanged();

            }

        };
    }

    // Method stops the adapter from accessing the data if its invalid
    public void notifyDataSetInvalidated()
    {
        super.notifyDataSetInvalidated();
    }
}