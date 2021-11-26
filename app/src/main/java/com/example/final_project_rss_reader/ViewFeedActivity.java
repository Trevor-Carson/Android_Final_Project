package com.example.final_project_rss_reader;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ViewFeedActivity extends AppCompatActivity {
    ArrayList<RssItem> rssItemList = new ArrayList<>();
    ListView listView;
    RssItem item;
    SQLiteDatabase sqldb;
    Cursor c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feed);
        RssLoad loadRss = new RssLoad();
        loadRss.execute("http://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml");
        listView = (ListView) findViewById(R.id.rssFeedItemListView);
        RSSAdapter adapter = new RSSAdapter();

        Database db = new Database(this);
        sqldb = db.getWritableDatabase();

        listView.setOnItemLongClickListener((p, b, pos, id) -> {
            item = rssItemList.get(pos);
            AlertDialog alertDelete = new AlertDialog.Builder(this)
                    .setTitle("Save this article?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        addToDatabase(item);
                        adapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("No", null)
                    .create();
            alertDelete.show();
            return true;
        });
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

    public class RssLoad extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... args) {

            try {
                URL rssURL = new URL(args[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) rssURL.openConnection();

                InputStream response = urlConnection.getInputStream();
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(response, "UTF-8");


                boolean done = false;
                int eventType = xpp.getEventType();

                RssItem currentRSSItem = new RssItem();
                while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                    String name = null;
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            name = xpp.getName();
                            if (name.equalsIgnoreCase("item")) {
                                currentRSSItem = new RssItem();
                            } else if (currentRSSItem != null) {
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
                            if (name.equalsIgnoreCase("item") && currentRSSItem != null) {
                                rssItemList.add(currentRSSItem);
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

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ListView listView = (ListView) findViewById(R.id.rssFeedItemListView);
            RSSAdapter adapter = new RSSAdapter(ViewFeedActivity.this, rssItemList);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}