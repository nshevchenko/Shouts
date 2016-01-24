package com.nik.shouts.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nik.shouts.R;
import com.nik.shouts.adapters.FeedListAdapter;
import com.nik.shouts.models.Shout;

/**
 * Created by nik on 26/10/15.
 */

public class FragmentFeed extends Fragment {

    private ListView feedList;

    public static FragmentFeed newInstance(int page, String title) {
        FragmentFeed fragmentFirst = new FragmentFeed();
        return fragmentFirst;
    }

//    @Override
//    public void onResume(){
//
//        System.out.println("resume, update list");
//        if( feedList != null )
//            ((FeedListAdapter)feedList.getAdapter()).notifyDataSetChanged();
//        super.onResume();
//    }

    public void onDataChangedListView(){
        ((BaseAdapter)feedList.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);

        feedList = (ListView) rootView.findViewById(R.id.listFeeds);
        feedList.setAdapter(new FeedListAdapter(this.getContext(), R.layout.feed_row));
        feedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Shout clickedShout = (Shout)adapterView.getItemAtPosition(i);
                FragmentShoutDetail fragmentShoutDetail = new FragmentShoutDetail();
                fragmentShoutDetail.setShoutDetail(clickedShout);

                fragmentShoutDetail.show(getFragmentManager(), "Shout detail");
            }
        });
        return rootView;
    }
}

