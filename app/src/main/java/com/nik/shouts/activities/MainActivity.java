package com.nik.shouts.activities;

import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.nik.shouts.R;
import com.nik.shouts.fragments.FragmentFeed;
import com.nik.shouts.fragments.FragmentMaps;
import com.nik.shouts.fragments.FragmentUserDetails;
import com.nik.shouts.fragments.NewShoutFragment;
import com.nik.shouts.interfaces.NewShoutActivityCallback;
import com.nik.shouts.models.Shout;
import com.nik.shouts.utils.Configurations;

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

    /*
    *   Assign listener to all buttons in the activity
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
                NewShoutActivityCallback apiNewShoutCallback = new NewShoutActivityCallback() {
                    @Override
                    public void onRequestCompleteNewShout(Shout newShout) {
                        updateMapAndListFeed(newShout);
                    }
                };
                NewShoutFragment newShoutFragment = new NewShoutFragment();
                newShoutFragment.setCallbackNewShoutCreated(apiNewShoutCallback);
                openFragmentAsParent((Fragment)newShoutFragment);
                break;
            case R.id.searchEditText:
                openActivityAsParent(SearchActivity.class);
                break;
            case R.id.userDetails:
                openFragmentAsParent(new FragmentUserDetails());
                break;
        }
    }

    /*
    * OPEN NEW FRAGMENT PARENT ACTIVITY
    */
    public void openFragmentAsParent(Fragment newFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content, newFragment);
        transaction.commit();
    }

    /*
    * OPEN NEW SHOUT PARENT ACTIVITY
    */

    private void openActivityAsParent(Class newIntentClass){
        Intent newIntent = new Intent(this, newIntentClass);
        if(newIntentClass == NewShoutFragment.class) {

        }
        startActivity(newIntent);
    }

    private void updateMapAndListFeed(Shout newShout){
        System.out.println("update Map And Feed with new Shout");
    }


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
                case 0:
                    return FragmentMaps.newInstance(0, Configurations.TABS[0]);
                case 1:
                    return FragmentFeed.newInstance(1, Configurations.TABS[1]);
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
}
