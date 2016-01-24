package com.nik.shouts.models;

import android.support.v4.app.Fragment;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.nik.shouts.adapters.SearchListAdapter;

import java.util.ArrayList;

/**
 * Created by nik on 22/01/16.
 */

public class SearchFragment extends Fragment {

    protected ListView listSearchResults;

    /**
     * update ListView adapter with new values and call on data set change
     * @param searchResults
     */
    public void updateListSearchResults(ArrayList<SearchResult> searchResults) {
        ((SearchListAdapter)listSearchResults.getAdapter()).updateSearchResults(searchResults);
        ((BaseAdapter)listSearchResults.getAdapter()).notifyDataSetChanged();
        listSearchResults.invalidateViews();
    }


}
