package com.example.final_project_rss_reader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * Class to load the emptyActivity which fetches the fragment class
 */
public class EmptyActivity extends AppCompatActivity {

    // Override method that creates and passes data to another activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        Bundle dataToPass = getIntent().getExtras();    // Constructs a new, empty Bundle that uses a specific ClassLoader for instantiating Parcelable and Serializable objects

        RSSFragment fragment = new RSSFragment();       // Creates a new fragment to be passed to another activity
        fragment.setArguments(dataToPass);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();

    }
}