package com.nik.shouts.fragments.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nik.shouts.R;
import com.nik.shouts.adapters.SearchListAdapter;
import com.nik.shouts.fragments.FragmentUserDetails;
import com.nik.shouts.models.SearchFragment;
import com.nik.shouts.models.SearchResult;
import com.nik.shouts.models.User;
import com.nik.shouts.utils.UserUtils;

import java.util.ArrayList;

/**
 * Created by nik on 22/01/16.
 */

public class FragmentSearchPeople extends SearchFragment {


    public static FragmentSearchPeople newInstance(int page, String title) {
        FragmentSearchPeople fragmentSearchPeople = new FragmentSearchPeople();
        return fragmentSearchPeople;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_people, container, false);

        listSearchResults = (ListView) rootView.findViewById(R.id.peopleList);
        listSearchResults.setAdapter(new SearchListAdapter(this.getContext(), R.layout.feed_search_row, new ArrayList<SearchResult>()));
        listSearchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SearchResult clickedResult = (SearchResult) adapterView.getItemAtPosition(i);
                User user = UserUtils.getUserById(clickedResult.getId() + "");
                FragmentUserDetails fragmentUserDetails = new FragmentUserDetails();
                fragmentUserDetails.setUserDetail(user);
                fragmentUserDetails.show(getFragmentManager(), "User detail");
            }
        });
        return rootView;
    }

}
