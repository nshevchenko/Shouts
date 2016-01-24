package com.nik.shouts.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nik.shouts.fragments.FragmentFeed;
import com.nik.shouts.fragments.FragmentMaps;
import com.nik.shouts.utils.Configurations;

/**
 * Created by nik on 16/12/15.
 */

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position) {
            case Configurations.MAPS_TAB_ID:
                return FragmentMaps.newInstance(Configurations.MAPS_TAB_ID, Configurations.TABS[Configurations.MAPS_TAB_ID]);
            case Configurations.FEED_TAB_ID:
                return FragmentFeed.newInstance(Configurations.FEED_TAB_ID, Configurations.TABS[Configurations.FEED_TAB_ID]);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        // Show total pages.
        return Configurations.PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Configurations.TABS[position];
    }
}