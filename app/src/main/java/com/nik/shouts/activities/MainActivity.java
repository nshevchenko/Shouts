package com.nik.shouts.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.nik.shouts.R;
import com.nik.shouts.adapters.SectionsPagerAdapter;
import com.nik.shouts.fragments.FragmentUserDetails;
import com.nik.shouts.interfaces.ApiRequestCallback;
import com.nik.shouts.models.App;
import com.nik.shouts.models.Shout;
import com.nik.shouts.utils.ApiUtils;
import com.nik.shouts.utils.Configurations;
import com.nik.shouts.utils.MapUtils;
import com.nik.shouts.utils.Messages;

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
        switch (view.getId()) {
            case R.id.create:
                App.openActivityAsParent(this, NewShoutActivity.class, Configurations.REQUEST_CODE_PARENT_NEW_SHOUT_ACTIVITIY);
                break;
            case R.id.searchEditText:
                App.openActivityAsParent(this, SearchActivity.class, Configurations.REQUEST_CODE_PARENT_NEW_SEARCH_ACTIVITIY);
                break;
            case R.id.userDetails:
                App.openFragmentAsParent(this, new FragmentUserDetails());
                break;
        }
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
            case Configurations.REQUEST_CODE_PARENT_NEW_SEARCH_ACTIVITIY:

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
        int newShoutId = Integer.parseInt(newShoutIdStr);
        Shout newShout = App.shoutsCollections.getShoutById(newShoutId);
        System.out.println("Update Map And Feed with new Shout .. " + newShout.getId() + " " + newShout.getContent());

        // update list view in the user's list
        mSectionsPagerAdapter.notifyDataSetChanged();
//        MapUtils.updateMap();
//        this.getSupportFragmentManager().
        ApiRequestCallback apiCallback = new ApiRequestCallback() {
            @Override
            public void onRequestComplete(String result) {
                System.out.println("fuck me: "+ result);
//                Snackbar snackbar = Snackbar.make(findViewById(R.id.main_content_layout), Messages.DONE_UPLOAD_NEW_SHOUT, Snackbar.LENGTH_SHORT);
//                snackbar.show();
            }
        };
        // download app data with the related callback
        ApiUtils.uploadNewShout(apiCallback, newShout.toJSON());
    }
}