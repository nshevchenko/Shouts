package com.nik.shouts.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.nik.shouts.R;
import com.nik.shouts.models.Shout;
import com.nik.shouts.models.App;
import com.nik.shouts.utils.Configurations;
import com.nik.shouts.utils.Messages;
import com.nik.shouts.utils.MapUtils;

import java.text.ParseException;
import java.util.Calendar;

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


    //location
    private Place shoutLocationPlace;

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
    private void setUpGoogleMap(Bundle savedInstanceState) {
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        googleMap = MapUtils.initGoogleMap(mapView, this);
    }

    /**
     * On activity result of the activities for parent windows (like the location picker)
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Configurations.REQUEST_CODE_PLACE_PICKER) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
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
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(this), Configurations.REQUEST_CODE_PLACE_PICKER);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     *
     */
    private void readAndUploadShout() {
        Shout newShout = generateShoutFromElements();
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
    private Shout generateShoutFromElements(){
        // read title
        EditText tempEditText = (EditText) findViewById(R.id.titleEditText);
        String title = tempEditText.getText().toString();

        // read content
        tempEditText = (EditText) findViewById(R.id.descriptionEditText);
        String content = tempEditText.getText().toString();

        // get date
        tempEditText = (EditText) findViewById(R.id.dateEditText);
        String dateStr = tempEditText.getText().toString();
        Calendar date = Calendar.getInstance();
        try {
            date.setTime(Configurations.DATE_FORMAT_SHOUT_CREATION_USER.parse(dateStr));
        } catch(ParseException ex){
            Log.e("CREATE NEW SHOUT ", "Date error parsing");
        }
        // read and save as string date in database format

        // get participation limit people
        tempEditText = (EditText) findViewById(R.id.limitPeopleEditText);
        String participationLimitStr = tempEditText.getText().toString();
        int participationLimit = Integer.parseInt(participationLimitStr);

        String creatorId = App.usersCollections.getCurrentlyLoggedInUser().getId();

        String locationName = shoutLocationPlace.getName().toString();
        String locationCoordinates = shoutLocationPlace.getLatLng().toString();
        System.out.println("Location name : " +  locationName + ", /n coordinate: " + shoutLocationPlace.getLatLng().toString());


        return App.shoutsCollections.createNewShout(
                title, content, creatorId, date, participationLimit, locationName, locationCoordinates);
    }
}
