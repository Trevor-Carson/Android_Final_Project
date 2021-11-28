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

public class RSSFragment extends Fragment {
    Bundle dataFromActivity;
    private AppCompatActivity parentActivity;
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
    public void onAttach(Context context) {
        super.onAttach(context);
        parentActivity = (AppCompatActivity) context;
    }
}


