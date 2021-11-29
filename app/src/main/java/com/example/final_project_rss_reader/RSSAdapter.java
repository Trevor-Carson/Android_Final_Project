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

public class RSSAdapter extends BaseAdapter implements Filterable {

    private Activity activity;
    private ArrayList<RssItem> itemList;

    public RSSAdapter(Activity activity, ArrayList<RssItem> list) {
        this.activity = activity;
        this.itemList = list;
    }

    public RSSAdapter() {
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public RssItem getItem(int position) {
        return itemList.get(position);
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

        return view;
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
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
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                // inform the adapter about the new list filtered

                itemList = (ArrayList<RssItem>) filterResults.values;
                notifyDataSetChanged();

            }

        };
    }

    public void notifyDataSetInvalidated()
    {
        super.notifyDataSetInvalidated();
    }
}