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

    /** Activity object to hold information about activities */
    private Activity activity;
    /** Array to store sever RssItem Objects */
    private ArrayList<RssItem> itemList;

    /**
     * Parameterized constructor for RSSAdapter
     * @param activity - Object to store the current activity
     * @param list - Object to store an array list of RSS items
     */
    public RSSAdapter(Activity activity, ArrayList<RssItem> list) {
        this.activity = activity;
        this.itemList = list;
    }

    /**
     * Non parameterized constructor for RSSAdapter
     */
    public RSSAdapter() {
    }

    /**
     * Method to find the amount of RSS feeds stored in the RSSItem array list
     * @return - Returns the size of the RSS array list
     */
    @Override
    public int getCount() {
        return itemList.size();
    }

    /**
     * Method to return a specific RSS feed from the array based on its position
     * @param position - Integer to hold the passed position of the RSS item in the array
     * @return - Returns the position number of the RSS item in the array
     */
    @Override
    public RssItem getItem(int position) {
        return itemList.get(position);
    }

    /**
     * Method to return a specific RSS feed ID from the array based on its position
     * @param position - Integer to hold the passed position ID of the RSS item in the array
     * @return - Returns the position ID number of the RSS item in the array
     */
    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    /**
     * Method to populate RSS list items
     * @param position - Integer to hold the position of the RSS item
     * @param convertView - View object for the inflater widget
     * @param parent - Object to store a collection of RSS feed views
     * @return - Returns the view of RSS feeds
     */
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

        return view;
    }

    /**
     * Override method to generate search results based on user input
     * @return - Returns the filtered results from the user search
     */
    @Override
    public Filter getFilter() {
        return new Filter() {

            /**
             * Override method to create a readable string and filter the rss feeds based on user input
             * @param charSequence
             * @return - Returns the filtered results from the user search
             */
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

            /**
             * Override method to populate the list view with the filtered results
             * @param charSequence - Object to store a readable sequence of char values
             * @param filterResults - Object to holds the results of a filtering operation
             */
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                // inform the adapter about the new list filtered

                itemList = (ArrayList<RssItem>) filterResults.values;
                notifyDataSetChanged();

            }

        };
    }

    /**
     * Method stops the adapter from accessing the data if its invalid
     */
    public void notifyDataSetInvalidated()
    {
        super.notifyDataSetInvalidated();
    }
}