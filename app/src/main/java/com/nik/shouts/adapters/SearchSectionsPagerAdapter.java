package com.nik.shouts.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nik.shouts.fragments.FragmentFeed;
import com.nik.shouts.fragments.FragmentMaps;
import com.nik.shouts.fragments.search.FragmentSearchHashtags;
import com.nik.shouts.fragments.search.FragmentSearchPeople;
import com.nik.shouts.fragments.search.FragmentSearchPlaces;
import com.nik.shouts.fragments.search.FragmentSearchShouts;
import com.nik.shouts.utils.Configurations;

/**
 * Created by nik on 16/12/15.
 */

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */

public class SearchSectionsPagerAdapter extends FragmentPagerAdapter {

    public SearchSectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int tabId) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (tabId) {
            case Configurations.SEARCH_PEOPLE_TAB_ID:
                return FragmentSearchPeople.newInstance(tabId, Configurations.SEARCH_TABS[tabId]);
            case Configurations.SEARCH_PLACES_TAB_ID:
                return FragmentSearchPlaces.newInstance(tabId, Configurations.SEARCH_TABS[tabId]);
            case Configurations.SEARCH_SHOUTS_TAB_ID:
                return FragmentSearchShouts.newInstance(tabId, Configurations.SEARCH_TABS[tabId]);
            case Configurations.SEARCH_TAGS_TAB_ID:
                return FragmentSearchHashtags.newInstance(tabId, Configurations.SEARCH_TABS[tabId]);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        // Show total pages.
        return Configurations.SEARCH_PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Configurations.SEARCH_TABS[position];
    }
}