package com.nik.shouts.fragments.search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nik.shouts.R;
import com.nik.shouts.activities.SearchActivity;
import com.nik.shouts.adapters.FeedListAdapter;
import com.nik.shouts.adapters.SearchListAdapter;
import com.nik.shouts.fragments.FragmentShoutDetail;
import com.nik.shouts.models.PlaceResult;
import com.nik.shouts.models.SearchFragment;
import com.nik.shouts.models.SearchResult;
import com.nik.shouts.models.Shout;

import java.util.ArrayList;

/**
 * Created by nik on 22/01/16.
 */

public class FragmentSearchPlaces extends SearchFragment {


    public static FragmentSearchPlaces newInstance(int page, String title) {
        FragmentSearchPlaces fragmentSearchPlaces = new FragmentSearchPlaces();
        return fragmentSearchPlaces;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_places, container, false);

        listSearchResults = (ListView) rootView.findViewById(R.id.placesList);
        listSearchResults.setAdapter(new SearchListAdapter(this.getContext(), R.layout.feed_search_row, new ArrayList<SearchResult>()));
        listSearchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PlaceResult placeResult = (PlaceResult) adapterView.getItemAtPosition(i);
                ((SearchActivity)getActivity()).readAndUploadShout(placeResult.getLocation());
            }
        });

        return rootView;
    }
}
