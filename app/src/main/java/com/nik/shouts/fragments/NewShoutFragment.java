package com.nik.shouts.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.nik.shouts.R;
import com.nik.shouts.interfaces.NewShoutActivityCallback;
import com.nik.shouts.models.Shout;
import com.nik.shouts.models.App;
import com.nik.shouts.utils.Configurations;
import com.nik.shouts.utils.Errors;
import com.nik.shouts.utils.MapUtils;

import java.util.Date;

/**
 * Created by nik on 23/11/15.
 */

public class NewShoutFragment extends Fragment implements View.OnClickListener {


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
    private View rootView;


    // callback to the main activity with refreshing the map with the new shout
    private NewShoutActivityCallback apiNewShoutCreatedCallback;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_new_shout, container, false);
        setUpGoogleMap(rootView, savedInstanceState);
        findActivityElements(rootView);
        return rootView;
    }

    /**
     * create Google view map
     * @param rootView
     * @param savedInstanceState
     */
    private void setUpGoogleMap(View rootView, Bundle savedInstanceState){
        mapView = (MapView) rootView.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        googleMap = MapUtils.initGoogleMap(mapView, this.getActivity());
    }


    private void findActivityElements(View rootView){
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);

        // assign listener to all buttons in the activity
        for (int i = 0; i < activity_buttons.length; i++) {
            Button activity_button = (Button) rootView.findViewById(activity_buttons[i]);
            activity_button.setOnClickListener(this);
        }
        FloatingActionButton button = (FloatingActionButton) rootView.findViewById(R.id.search);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        System.out.println("button clicked " + view.getId());
        switch (view.getId()) {
            case R.id.create:
                break;

            case R.id.doneButton:
                //validate shout
                if( ! validateNewShout())
                    return;

                // upload shout
                Shout newShout = generateShoutFromScreen();
                App.shoutsCollections.addShout(newShout);
                apiNewShoutCreatedCallback.onRequestCompleteNewShout(newShout);

                // download app data with the related callback

//                Intent intent = NavUtils.getParentActivityIntent(this);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                NavUtils.navigateUpTo(this, intent);
                    getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
//                finish();
                break;
        }
    }

    /**
     * Validate the current Edit Textes present on the screen in order to generate a successful new object shout
     * @return
     */
    private boolean validateNewShout(){
        Snackbar snackbar = null;
        if( Configurations.checkEditTextIsEmpty(rootView, R.id.titleEditText))
            snackbar = Snackbar.make(rootView.findViewById(R.id.main_content), Errors.newShoutTitleEditTextRequired, Snackbar.LENGTH_SHORT);

        if( Configurations.checkEditTextIsEmpty(rootView, R.id.descriptionEditText))
            snackbar = Snackbar.make(rootView.findViewById(R.id.main_content), Errors.newShoutDescriptionEditTextRequired, Snackbar.LENGTH_SHORT);

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
        EditText tempEditText = (EditText) rootView.findViewById(R.id.titleEditText);
        String title = tempEditText.getText().toString();

        // read content
        tempEditText = (EditText) rootView.findViewById(R.id.descriptionEditText);
        String content = tempEditText.getText().toString();

        // get date
        tempEditText = (EditText) rootView.findViewById(R.id.dateEditText);
        String dateStr = tempEditText.getText().toString();
        Date date = null; // get date from str

        // get participation limit people
        tempEditText = (EditText) rootView.findViewById(R.id.limitPeopleEditText);
        String participationLimitStr = tempEditText.getText().toString();
        int participationLimit = Integer.parseInt(participationLimitStr);

        String creatorId = App.usersCollections.getCurrentlyLoggedInUser().getId();
        String coordinatesStr = ""; // mapView.getCoordinates();
        String locationName = "";
        String locationCoordinates = "";

        Shout newShout = new Shout(title, content, creatorId, date, participationLimit, locationName, locationCoordinates);
        return newShout;
    }

    /**
     *    Asssign callback from the parent activitiy when the shout is created
     */
    public void setCallbackNewShoutCreated(NewShoutActivityCallback apiNewShoutCreatedCallback) {
        this.apiNewShoutCreatedCallback = apiNewShoutCreatedCallback;
    }

}
