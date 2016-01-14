package com.nik.shouts.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nik.shouts.R;
import com.nik.shouts.adapters.FeedListAdapter;

/**
 * Created by nik on 26/10/15.
 */

public class FragmentShoutDetail extends Fragment {


    public static FragmentFeed newInstance(int page, String title) {
        FragmentFeed fragmentFirst = new FragmentFeed();
        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);

        ListView feedList = (ListView) rootView.findViewById(R.id.listFeeds);
        feedList.setAdapter(new FeedListAdapter(this.getContext(), R.layout.feed_row));

        return rootView;
    }

    private void findElements() {

    }
}

