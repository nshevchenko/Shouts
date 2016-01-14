package com.nik.shouts.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.nik.shouts.R;
import com.nik.shouts.models.Shout;
import com.nik.shouts.models.App;
import com.nik.shouts.utils.Configurations;
import com.nik.shouts.utils.Messages;
import com.nik.shouts.utils.MapUtils;

import java.util.Date;

/**
 * Created by nik on 23/11/15.
 */

public class NewShoutActivity extends Activity implements View.OnClickListener {

    // Activity buttons to received the callback for
    private int[] activity_buttons = {
            R.id.doneButton,
            R.id.dateButton,
            R.id.timeButton,
            R.id.limitPeopleButton,
            R.id.inviteFriendsButton
    };

    // Activities edit textes to be read and validated
    private int[] activity_newShout_editText = {
            R.id.titleEditText,
            R.id.descriptionEditText,
            R.id.dateEditText,
            R.id.timeEditText,
            R.id.limitPeopleTextView,
            R.id.invitePeopleTextView,
            R.id.inviteFriendsEditText
    };

    // map
    private MapView mapView;
    private GoogleMap googleMap;

    // layout view of the fragment (accessed for validation purposes later)
//    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_shout);
        setUpGoogleMap(savedInstanceState);
        findActivityElements();
    }

    /**
     * create Google view map
     * @param savedInstanceState
     */
    private void setUpGoogleMap(Bundle savedInstanceState){
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        googleMap = MapUtils.initGoogleMap(mapView, this);
    }


    private void findActivityElements(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // assign listener to all buttons in the activity
        for (int i = 0; i < activity_buttons.length; i++) {
            Button activity_button = (Button) findViewById(activity_buttons[i]);
            activity_button.setOnClickListener(this);
        }
        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.search);
        button.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        System.out.println("button clicked " + view.getId());
        switch (view.getId()) {
            case R.id.doneButton:
                //validate shout
                if( ! validateNewShout())
                    return;
                // read and upload shout
                readAndUploadShout();
                break;

            case R.id.search:
                break;
        }
    }

    /**
     *
     */
    private void readAndUploadShout() {
        Shout newShout = generateShoutFromScreen();
        Intent intent = new Intent();
        intent.putExtra(Configurations.REQUEST_STRING_NEW_SHOUT_ID, newShout.getId());
        setResult(RESULT_OK, intent);
        finish();
//        ((MainActivity)getActivity()).updateMapAndListFeed(newShout);
//        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

    /**
     * Validate the current Edit Textes present on the screen in order to generate a successful new object shout
     * @return
     */
    private boolean validateNewShout(){
        Snackbar snackbar = null;
        View rootView = findViewById(R.id.main_content_layout);
        if( Configurations.checkEditTextIsEmpty(rootView, R.id.titleEditText))
            snackbar = Snackbar.make(rootView, Messages.ERROR_NEW_SHOUT_TITLE_REQUIRED, Snackbar.LENGTH_SHORT);

        if( Configurations.checkEditTextIsEmpty(rootView, R.id.descriptionEditText))
            snackbar = Snackbar.make(rootView, Messages.ERROR_NEW_SHOUT_DESCRIPTION_REQUIRED, Snackbar.LENGTH_SHORT);

//        String dateStr = findViewById(R.id.dateEditText)
//        if( Configurations.validateDateInThePast(findViewById(R.id.dateEditText)))
//            snackbar = Snackbar.make(findViewById(R.id.main_content), Errors.newShoutDescriptionEditTextRequired, Snackbar.LENGTH_SHORT);

        if(snackbar != null){
            snackbar.show();
            return false;
        }
        return true;
    }


    /**
     * Create new Shout with information from edit textes
     * @return
     */
    private Shout generateShoutFromScreen(){
        // read title
        EditText tempEditText = (EditText) findViewById(R.id.titleEditText);
        String title = tempEditText.getText().toString();

        // read content
        tempEditText = (EditText) findViewById(R.id.descriptionEditText);
        String content = tempEditText.getText().toString();

        // get date
        tempEditText = (EditText) findViewById(R.id.dateEditText);
        String dateStr = tempEditText.getText().toString();
        Date date = null; // get date from str

        // get participation limit people
        tempEditText = (EditText) findViewById(R.id.limitPeopleEditText);
        String participationLimitStr = tempEditText.getText().toString();
        int participationLimit = Integer.parseInt(participationLimitStr);

        String creatorId = App.usersCollections.getCurrentlyLoggedInUser().getId();
        String coordinatesStr = ""; // mapView.getCoordinates();
        String locationName = "";
        String locationCoordinates = "";

        return App.shoutsCollections.createNewShout(
                title, content, creatorId, date, participationLimit, locationName, locationCoordinates);
    }
}
