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

public class FragmentShout extends Fragment {

    public static FragmentShout newInstance(int page, String title) {
        FragmentShout fragmentShout = new FragmentShout();
        return fragmentShout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shout, container, false);
        TextView text = (TextView) rootView.findViewById(R.id.section_label);
        text.setText("Shout");
        return rootView;
    }
}
