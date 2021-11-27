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
        TextView description = view.findViewById(R.id.rssArticleTextView);
        description.setText(rssItem.getDescription());

        return view;
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
              //  Log.d("SEARCHTEXT2", "**** PERFORM FILTERING for: " + charSequence);
                charSequence = charSequence.toString().toLowerCase();
                FilterResults filts = new FilterResults();
                if (charSequence == null || charSequence.length() == 0){
                    filts.values = itemList;
                    filts.count = itemList.size();
                } else {
                    // We perform filtering operation
                    ArrayList<RssItem> rssItemrs = new ArrayList<>();
                    for (RssItem l : itemList){ //you are going to search in labItem that contains the data and not the empty list labrs
                        if (l.getTitle().startsWith(charSequence.toString())){
                     //       Log.e("(l.getName().startsWith(charSequence.toString())","" + charSequence);
                            rssItemrs.add(l); // add to the new list**
                        }
                    }
                    filts.values = rssItemrs; //set values to the new list**
                    filts.count = rssItemrs.size();
                    //filts.count = labItem.size();
                }
                return filts;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                // Now we have to inform the adapter about the new list filtered
              //  Log.d("SEARCHTEXT1", "**** PUBLISHING RESULTS for: " + charSequence);
                ArrayList<RssItem> filtered = (ArrayList<RssItem>) filterResults.values;

                itemList = filtered; // set the new data as you want  with the new set you've received.**
                notifyDataSetChanged();

            }

        };
    }

    public void notifyDataSetInvalidated()
    {
        super.notifyDataSetInvalidated();
    }
}