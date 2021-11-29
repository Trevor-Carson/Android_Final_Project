package com.example.final_project_rss_reader;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ViewFeedActivity extends AppCompatActivity {
    ArrayList<RssItem> rssItemList = new ArrayList<>();
    ArrayList<RssItem> rssItemsFiltered;
    int textLength;
    ProgressBar loadProgressBar;
    ListView listView;
    RssItem item;
    SQLiteDatabase sqldb;
    EditText searchBar;

    public static final String ITEM_TITLE = "TITLE";
    public static final String ITEM_DATE= "DATE";
    public static final String ITEM_DESCRIPTION = "DESCRIPTION";
    public static final String ITEM_LINK = "LINK";
    public static final String ITEM_POSITION = "POSITION";
    public static final String ITEM_ID = "ID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feed);
        RssLoad loadRss = new RssLoad();

        loadProgressBar = findViewById(R.id.progressBar);
        loadProgressBar.setVisibility(View.VISIBLE);

        loadRss.execute("http://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml");
        listView = (ListView) findViewById(R.id.rssFeedItemListView);
        searchBar = (EditText) findViewById(R.id.editTextSearch);
        listView.setTextFilterEnabled(true);
        RSSAdapter adapter = new RSSAdapter();
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                        listView.invalidate();
                        adapter.notifyDataSetChanged();
            }
            @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s.toString());
                        textLength = s.length();
                        rssItemsFiltered = new ArrayList<RssItem>();
                        for (RssItem ll : rssItemList){
                            if(textLength <= ll.getTitle().length()){
                                if (ll.getTitle().toLowerCase().contains(s.toString().toLowerCase())){
                                    rssItemsFiltered.add(ll);
                                }
                            }
                        }
                        RSSAdapter adapter = new RSSAdapter(ViewFeedActivity.this, rssItemsFiltered);
                        listView.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        Database db = new Database(this);
        sqldb = db.getWritableDatabase();
        boolean isTablet = findViewById(R.id.frameLayout) != null;

        listView.setOnItemLongClickListener((p, b, pos, id) -> {
            if (textLength == 0) {
                item = rssItemList.get(pos);
            } else {
                item = rssItemsFiltered.get(pos);
            }
            AlertDialog alertDelete = new AlertDialog.Builder(this)
                    .setTitle("Save this article?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        addToDatabase(item);
                        adapter.notifyDataSetChanged();
                        Snackbar snackbar = Snackbar.make(listView,
                                "Item added to saved items.",Snackbar.LENGTH_SHORT);
                        snackbar.setAction("UNDO", view -> undoAdd(item));


                        snackbar.show();
                    })
                    .setNegativeButton("No", null)
                    .create();
            alertDelete.show();
            return true;
        });

        listView.setOnItemClickListener(((parent, view, position, id) -> {
            Bundle dataToPass = new Bundle();
            if (textLength == 0) {
                dataToPass.putString(ITEM_TITLE, rssItemList.get(position).getTitle());
                dataToPass.putString(ITEM_DATE, rssItemList.get(position).getPubDate());
                dataToPass.putString(ITEM_DESCRIPTION, rssItemList.get(position).getDescription());
                dataToPass.putString(ITEM_LINK, rssItemList.get(position).getLink());

            } else {

                dataToPass.putString(ITEM_TITLE, rssItemsFiltered.get(position).getTitle());
                dataToPass.putString(ITEM_DATE, rssItemsFiltered.get(position).getPubDate());
                dataToPass.putString(ITEM_DESCRIPTION, rssItemsFiltered.get(position).getDescription());
                dataToPass.putString(ITEM_LINK, rssItemsFiltered.get(position).getLink());
            }
            dataToPass.putInt(ITEM_POSITION, position);
            dataToPass.putLong(ITEM_ID, id);
            RSSFragment fragment = new RSSFragment();

            if (isTablet) {
                fragment.setArguments(dataToPass);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, fragment)
                        .commit();
            } else {
                Intent nextActivity = new Intent(ViewFeedActivity.this, EmptyActivity.class);
                nextActivity.putExtras(dataToPass);
                startActivity(nextActivity);

            }
        }));
    }

    protected void addToDatabase(RssItem item) {
        String title = item.getTitle();
        String description = item.getDescription();

        ContentValues newRowValues = new ContentValues();
        newRowValues.put(Database.COL_DESCRIPTION, description);
        newRowValues.put(Database.COL_TITLE, title);
        // Log.i("DB values", String.valueOf(newRowValues));

        sqldb.insert(Database.TABLE_NAME, null, newRowValues);
    }
    protected void undoAdd(RssItem item) {
        Log.i("Undo", "loaded");
        Toast toast = Toast.makeText(getApplicationContext(),
                "Item removed.",
                Toast.LENGTH_SHORT);
        String title = item.getTitle();
        String description = item.getDescription();

        sqldb.delete(Database.TABLE_NAME, Database.COL_TITLE + "= ?", new String[]{title});
        toast.show();
    }

    public class RssLoad extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... args) {

            try {
                URL rssURL = new URL(args[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) rssURL.openConnection();
                InputStream response = urlConnection.getInputStream();
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParserFactory factory1 = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                factory1.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(response, "UTF-8");


                boolean done = false;
                int eventType = xpp.getEventType();
                int i = 1;
                float count = 56; // number of items in BBC RSS feed

                RssItem currentRSSItem = new RssItem();

                while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                    String name = null;
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            name = xpp.getName();

                            if (name.equalsIgnoreCase("item")) {
                                currentRSSItem = new RssItem();
                            } else {
                                if (name.equalsIgnoreCase("link")) {
                                    currentRSSItem.setLink(xpp.nextText());
                                } else if (name.equalsIgnoreCase("description")) {
                                    currentRSSItem.setDescription(xpp.nextText());
                                } else if (name.equalsIgnoreCase("pubDate")) {
                                    currentRSSItem.setPubDate(xpp.nextText());
                                } else if (name.equalsIgnoreCase("title")) {
                                    currentRSSItem.setTitle(xpp.nextText());
                                }

                            }
                            break;
                        case XmlPullParser.END_TAG:

                            name = xpp.getName();
                            if (name.equalsIgnoreCase("item")) {
                                rssItemList.add(currentRSSItem);


                                   float progress = (i / count) * 100;
                                   publishProgress((int)progress);
                                    i++;

                            } else if (name.equalsIgnoreCase("channel")) {


                                done = true;
                            }
                            break;
                    }
                        eventType = xpp.next();
                }

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
            }

            return "done";
        }
        protected void onProgressUpdate(Integer ...value){
            loadProgressBar.setVisibility(View.VISIBLE);
            loadProgressBar.setProgress(value[0]);


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ListView listView = (ListView) findViewById(R.id.rssFeedItemListView);
            RSSAdapter adapter = new RSSAdapter(ViewFeedActivity.this, rssItemList);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            loadProgressBar.setVisibility(View.INVISIBLE);
        }
    }
}