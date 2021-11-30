package com.example.final_project_rss_reader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * Class to load the emptyActivity which fetches the fragment class
 */
public class EmptyActivity extends AppCompatActivity {

    /**
     * Override method that creates and passes data to another activity
     * @param savedInstanceState - Object to hold bundled information to be passed to the the empty activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        /** Constructs a new, empty Bundle that uses a specific ClassLoader for instantiating Parcelable and Serializable objects */
        Bundle dataToPass = getIntent().getExtras();

        /** Creates a new fragment to be passed to another activity */
        RSSFragment fragment = new RSSFragment();
        fragment.setArguments(dataToPass);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();

    }
}