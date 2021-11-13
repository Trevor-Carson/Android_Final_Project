package com.example.final_project_rss_reader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class ViewFeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feed);
        Database db = new Database(this);
        RssLoad loadRss = new RssLoad();
        loadRss.execute("http://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml");

    }
    public class RssLoad extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... args) {
            /* trying to set up the RSS to load like the XMLs in the weather part of
               our labs but I am not finished yet.. may need to look into an alternative way to
               do this versus importing a plain XML file */
            try {
                URL rssURL = new URL(args[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) rssURL.openConnection();

                InputStream response = urlConnection.getInputStream();
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(response, "UTF-8");
                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    Log.i("Connection", String.valueOf(response));

                }


            } catch (Exception e) {
                Log.e("Error", e.getMessage());
            }
            return "done";
        }

    }
}