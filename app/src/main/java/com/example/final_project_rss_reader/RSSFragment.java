package com.example.final_project_rss_reader;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

/**
 * Class to fetch information from the empty activity class and bundle it together
 */
public class RSSFragment extends Fragment {
    /** Constructs a new, empty Bundle that uses a specific ClassLoader for instantiating Parcelable and Serializable objects */
    Bundle dataFromActivity;
    /** Base class for activities that wish to use some of the newer platform features on older Android devices */
    private AppCompatActivity parentActivity;

    /**
     * Method to create a view based on information generated in the empty activity
     * @param inflater - Instantiates a layout XML file into its corresponding View objects
     * @param container - A view object used to contain other views
     * @param savedInstanceState - Stores the current start of the running application
     * @return - Returns the result view
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dataFromActivity = getArguments();

        View result = inflater.inflate(R.layout.rss_fragment_details, container, false);

        TextView titleTextView = result.findViewById(R.id.rssTitleTextView);
        titleTextView.setText(dataFromActivity.getString(ViewFeedActivity.ITEM_TITLE));

        TextView descTextView = result.findViewById(R.id.rssDescTextView);
        descTextView.setText(dataFromActivity.getString(ViewFeedActivity.ITEM_DESCRIPTION));

        TextView linkTextView = result.findViewById(R.id.rssLinkTextView);
        linkTextView.setText((dataFromActivity.getString(ViewFeedActivity.ITEM_LINK)));

        TextView dateTextView = result.findViewById(R.id.rssDateTextView);
        dateTextView.setText((dataFromActivity.getString(ViewFeedActivity.ITEM_DATE)));

        Button button_L07 = result.findViewById((R.id.rssHideButton));
        button_L07.setOnClickListener(v -> {

            parentActivity.getSupportFragmentManager()
                    .beginTransaction()
                    .remove(this)
                    .commit();
            if (getActivity() instanceof EmptyActivity) {
                parentActivity.onBackPressed();
            }

        });
        return result;

    }

    //

    /**
     * Method to attach information about an RSS feed to the parent activity
     * @param context - object used to obtaining access to database
     */
    public void onAttach(Context context) {
        super.onAttach(context);
        parentActivity = (AppCompatActivity) context;
    }
}


