package com.nik.shouts.activities;

import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.maps.model.LatLng;
import com.nik.shouts.R;
import com.nik.shouts.adapters.SearchSectionsPagerAdapter;
import com.nik.shouts.adapters.SectionsPagerAdapter;
import com.nik.shouts.interfaces.HttpRequestCallback;
import com.nik.shouts.interfaces.RequestCallback;
import com.nik.shouts.models.App;
import com.nik.shouts.models.SearchFragment;
import com.nik.shouts.models.SearchResult;
import com.nik.shouts.models.Shout;
import com.nik.shouts.utils.ApiUtils;
import com.nik.shouts.utils.Configurations;
import com.nik.shouts.utils.SearchUtils;
import com.nik.shouts.utils.ShoutsUtils;
import com.nik.shouts.utils.UserUtils;

import java.util.ArrayList;

/**
 * Created by nik on 23/11/15.
 */

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    //Custom FragmentPagerAdapter for your tabs
    private SearchSectionsPagerAdapter sectionsPagerAdapter;

    // Opened tab
    private int currentOpenedTab;

    //The {@link ViewPager} that will host the section contents.
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_search);
        findViewElements();
    }

    //
    // Set up main elements/views inside the activity
    //
    public void findViewElements() {
        // set up tabs
        setUpTabsLayout();

        // add text change listener
        ((EditText)findViewById(R.id.searchEditText)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int id = viewPager.getCurrentItem();
                System.out.println("tab edit text " + id);
                updateListFragmentView(id);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    /**
     * Set up tabs layout with and add listener
     */
    private void setUpTabsLayout(){
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        sectionsPagerAdapter = new SearchSectionsPagerAdapter(this.getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(sectionsPagerAdapter);
        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.search_sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        viewPagerAddListener();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        System.out.println("button clicked " + view.getId());
        switch (view.getId()) {
            case R.id.create:
                break;
        }
    }

    /**
     * Add viewpager listener
     */
    private void viewPagerAddListener(){
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            boolean scrolling = false;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (scrolling == false) {
                    currentOpenedTab = position;
                    updateListFragmentView(position);
                    System.out.println("fucked it " + position);
                    if(currentOpenedTab == position)
                        scrolling = false;
                }
            }
            @Override
            public void onPageSelected(int position) {
                if (currentOpenedTab != position) {
                    scrolling = true;
                    currentOpenedTab = position;
                    System.out.println(position + " positioned");
                    updateListFragmentView(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void updateListFragmentView(final int position) {
        ArrayList<SearchResult> results = null;
        System.out.println("SEARCH TAB ID : " + position);
        if(getSearchText().length() <= 0)
            return;
        switch (position) {
            case Configurations.SEARCH_SHOUTS_TAB_ID:
                ArrayList<Shout> shouts = ShoutsUtils.filterShoutsByTitle(getSearchText());
                System.out.println("KEY " + getSearchText() + " and found title in " + shouts.size());
                results = SearchUtils.getSearchResultFromShouts(shouts);
                notifySearchChanged((SearchFragment) getSupportFragmentManager().getFragments().get(position), results);
                break;
            case Configurations.SEARCH_PEOPLE_TAB_ID:
                System.out.println("people");
                results = SearchUtils.getSearchResultFromUsers(UserUtils.filterUsersByStr(getSearchText()));
                notifySearchChanged((SearchFragment) getSupportFragmentManager().getFragments().get(position), results);
                break;
            case Configurations.SEARCH_TAGS_TAB_ID:
                System.out.println("hasht");
                results = SearchUtils.getSearchResultFromShouts(ShoutsUtils.filterShoutsByHashtag(getSearchText()));
                notifySearchChanged((SearchFragment) getSupportFragmentManager().getFragments().get(position), results);
                break;
            case Configurations.SEARCH_PLACES_TAB_ID:
                System.out.println("places");
                RequestCallback requestCallback = new RequestCallback() {
                    @Override
                    public void onRequestComplete(String result) {
                        SearchFragment searchFragment = (SearchFragment) getSupportFragmentManager().getFragments().get(position);
                        if(searchFragment != null) {
                            ArrayList<SearchResult> results = ApiUtils.parsePlacesResult(result);
                            notifySearchChanged(searchFragment, results);
                        }
                    }
                };
                HttpRequestCallback httpRequestCallback = new HttpRequestCallback(requestCallback, "GET");
                String parameter = ApiUtils.createUrlForPlaceSearch(getSearchText());
                httpRequestCallback.execute(Configurations.MAPS_SEARCH_PLACES_URL + parameter );
                break;
        }
    }

    private String getSearchText(){
        System.out.println("search fragment arrived + update");
        return ((EditText) findViewById(R.id.searchEditText)).getText().toString();
    }

    private void notifySearchChanged(SearchFragment searchFragment, ArrayList<SearchResult> results){
        System.out.println("got results");
        searchFragment.updateListSearchResults(results);
    }

    /**
     * read and upload shout
     * @param location
     */
    public void readAndUploadShout(String location) {
        Intent intent = new Intent();
        intent.putExtra(Configurations.REQUEST_STRING_NEW_SEARCH_PLACE_ID, location);
        setResult(RESULT_OK, intent);
        finish();
    }
}
