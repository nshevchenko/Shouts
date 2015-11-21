package com.nik.shouts.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nik.shouts.R;

/**
 * Created by nik on 26/10/15.
 */

public class FragmentFeed extends Fragment {

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
        TextView text = (TextView) rootView.findViewById(R.id.section_label);
        text.setText("Feed");
        return rootView;
    }
}

