package com.nik.shouts.activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.nik.shouts.R;
import com.nik.shouts.adapters.SectionsPagerAdapter;
import com.nik.shouts.fragments.FragmentFeed;
import com.nik.shouts.fragments.FragmentMaps;
import com.nik.shouts.fragments.FragmentUserDetails;
import com.nik.shouts.interfaces.RequestCallback;
import com.nik.shouts.models.App;
import com.nik.shouts.models.Shout;
import com.nik.shouts.models.User;
import com.nik.shouts.utils.ApiUtils;
import com.nik.shouts.utils.Configurations;
import com.nik.shouts.utils.MapUtils;
import com.nik.shouts.utils.Messages;
import com.nik.shouts.utils.ShoutsUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Custom FragmentPagerAdapter for your tabs
    private SectionsPagerAdapter mSectionsPagerAdapter;

    //The {@link ViewPager} that will host the section contents.
    private ViewPager mViewPager;

    //current app mode
    private int appLoggedInMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // get app mode login
        Bundle extras = getIntent().getExtras();
        if(extras != null)
            appLoggedInMode =  Integer.valueOf(extras.getString("com.example.myfirstapp.MODE"));

        setUpTabsLayout();
        findActivityElements();

        // hide keyboard
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    private void setUpTabsLayout(){
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(this.getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    /**
     * Assign listener to all buttons in the activity
     */
    private void findActivityElements() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.create);
        fab.setOnClickListener(this);

        EditText searchEditText = (EditText) findViewById(R.id.searchEditText);
        searchEditText.setSelected(false);
        searchEditText.setOnClickListener(this);

        // check if currently logged in user is not null
        if (App.usersCollections.getCurrentlyLoggedInUser() != null) {
            TextView nameTextView = (TextView) findViewById(R.id.nameTextView);
            nameTextView.setText(App.usersCollections.getCurrentlyLoggedInUser().getName());
        }
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
        RequestCallback requestCallback = null;
        switch (view.getId()) {
            case R.id.create:
                App.openActivityAsParent(this, NewShoutActivity.class, Configurations.REQUEST_CODE_PARENT_NEW_SHOUT_ACTIVITIY);
                break;
            case R.id.searchEditText:
                // open only if user's last location available
                if(App.usersCollections.getCurrentlyLoggedInUser().getLastKnownCoordinates() != null)
                    App.openActivityAsParent(this, SearchActivity.class, Configurations.REQUEST_CODE_PARENT_NEW_SEARCH_PLACES);
                break;
            case R.id.userDetailsImage:
                openUserDetailsDialog(App.usersCollections.getCurrentlyLoggedInUser());
                break;
            case R.id.nameTextView :
                openUserDetailsDialog(App.usersCollections.getCurrentlyLoggedInUser());
        }
    }

    /**
     * open user dialog
     * @param user
     */
    public void openUserDetailsDialog(User user){
        FragmentUserDetails fragmentUserDetail  = new FragmentUserDetails();
        fragmentUserDetail.setUserDetail(user);
        fragmentUserDetail.show(getSupportFragmentManager(), "User Detail");
    }

    /**
     * Receive results from parent activities
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("callback from parent (y)");
        switch(requestCode){
            case Configurations.REQUEST_CODE_PARENT_NEW_SHOUT_DETAIL:

                break;
            case Configurations.REQUEST_CODE_PARENT_NEW_SEARCH_PLACES:
                if(data != null) {
                    String location = data.getStringExtra(Configurations.REQUEST_STRING_NEW_SEARCH_PLACE_ID);
                    LatLng latLng = MapUtils.getLatLngFromString(location);
                    MapUtils.animateMapViewToCoordinates(
                            ((FragmentMaps) getSupportFragmentManager().getFragments().get(Configurations.MAPS_TAB_ID)).getGoogleMap()
                            , latLng
                    );
                }
                break;

            case Configurations.REQUEST_CODE_PARENT_NEW_SHOUT_ACTIVITIY:
                if(resultCode == RESULT_OK) {
                    String newShoutId = data.getStringExtra(Configurations.REQUEST_STRING_NEW_SHOUT_ID);
                    updateMapAndListFeed(newShoutId);
                }
                break;
        }
    }

    /**
     * Update the Map and List feed in the main activity
     * @param newShoutIdStr
     */
    public void updateMapAndListFeed(String newShoutIdStr){
        Shout newShout = ShoutsUtils.getShoutById(newShoutIdStr);
        // add new shout to
        if(newShout.getCreator() != null)
            newShout.addNewParticipant(newShout.getCreator());
        // update the listview on data changed
        ((FragmentFeed)(getSupportFragmentManager().getFragments().get(Configurations.FEED_TAB_ID))).onDataChangedListView();
        // update list view in the user's list
        mSectionsPagerAdapter.notifyDataSetChanged();

        //add marker to the app
        ((FragmentMaps)(getSupportFragmentManager().getFragments().get(Configurations.MAPS_TAB_ID))).addShoutMarker(newShout);

        RequestCallback apiCallback = new RequestCallback() {
            @Override
            public void onRequestComplete(String result) {
                Messages.showSnackBar(findViewById(R.id.main_content), Messages.DONE_UPLOAD_NEW_SHOUT);
            }
        };
        // download app data with the related callback
        ApiUtils.uploadNewJsonObject(apiCallback, newShout != null ? newShout.toJSON() : null);
    }
}