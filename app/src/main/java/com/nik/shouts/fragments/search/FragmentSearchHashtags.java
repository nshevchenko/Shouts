package com.nik.shouts.fragments.search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.nik.shouts.R;
import com.nik.shouts.adapters.FeedListAdapter;
import com.nik.shouts.adapters.SearchListAdapter;
import com.nik.shouts.fragments.FragmentShoutDetail;
import com.nik.shouts.interfaces.RequestCallback;
import com.nik.shouts.models.App;
import com.nik.shouts.models.SearchFragment;
import com.nik.shouts.models.SearchResult;
import com.nik.shouts.models.Shout;
import com.nik.shouts.utils.SearchUtils;
import com.nik.shouts.utils.ShoutsUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by nik on 22/01/16.
 */

public class FragmentSearchHashtags extends SearchFragment {


    public static FragmentSearchHashtags newInstance(int page, String title) {
        FragmentSearchHashtags fragmentSearchHashtags = new FragmentSearchHashtags();
        return fragmentSearchHashtags;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_hashtags, container, false);

        listSearchResults = (ListView) rootView.findViewById(R.id.hashtagsList);
        listSearchResults.setAdapter(new SearchListAdapter(this.getContext(), R.layout.feed_search_row, new ArrayList<SearchResult>()));
        listSearchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SearchResult clickedResult = (SearchResult) adapterView.getItemAtPosition(i);
                Shout shout = ShoutsUtils.getShoutById(clickedResult.getId());
                FragmentShoutDetail fragmentShoutDetail = new FragmentShoutDetail();
                fragmentShoutDetail.setShoutDetail(shout);
                fragmentShoutDetail.show(getFragmentManager(), "Shout detail");
            }
        });
        return rootView;
    }


}
