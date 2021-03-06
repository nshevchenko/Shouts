package com.nik.shouts.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.nik.shouts.R;
import com.nik.shouts.adapters.FeedListAdapter;
import com.nik.shouts.adapters.UserSearchAutoCompleteAdapter;
import com.nik.shouts.fragments.DatePickerFragment;
import com.nik.shouts.fragments.TimePickerFragment;
import com.nik.shouts.interfaces.RequestCallback;
import com.nik.shouts.models.Shout;
import com.nik.shouts.models.App;
import com.nik.shouts.utils.ApiUtils;
import com.nik.shouts.utils.Configurations;
import com.nik.shouts.utils.MapUtils;
import com.nik.shouts.utils.Messages;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by nik on 23/11/15.
 */

public class NewShoutActivity extends Activity implements View.OnClickListener {

    // Activity buttons to received the callback for
    private int[] activity_buttons = {
            R.id.doneButton
    };


    // Activities icons to be read and validated
    private int[] activity_icons = {
            R.id.clockDateImg,
            R.id.limitPeopleImg,
            R.id.inviteFriendsImg,
            R.id.hashtagsImg
    };

    // Activities edit textes to be read and validated
    private int[] activity_editText = {
            R.id.titleEditText,
            R.id.descriptionEditText,
            R.id.dateEditText,
            R.id.timeEditText,
            R.id.limitPeopleEditText,
            R.id.hashtagsEditText
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
        findActivityElements();
    }

    /**
     * create Google view map
     * @param savedInstanceState
     */

    /**
     * On activity result of the activities for parent windows (like the location picker)
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Configurations.REQUEST_CODE_PLACE_PICKER) {
            if (resultCode == RESULT_OK) {
                shoutLocationPlace = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", shoutLocationPlace.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                updateStaticMap(shoutLocationPlace.getLatLng());
            }
        }
    }

    /**
     * Find elements from this activity for initial settings
     */
    private void findActivityElements(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // assign listener to all buttons in the activity
        for (int i = 0; i < activity_buttons.length; i++) {
            Button activity_button = (Button) findViewById(activity_buttons[i]);
            activity_button.setOnClickListener(this);
        }
        for (int i = 0; i < activity_icons.length; i++) {
            ImageView tempImageView = (ImageView) findViewById(activity_icons[i]);
            tempImageView.getDrawable().setColorFilter(getResources().getColor(R.color.colorPrimaryLight), PorterDuff.Mode.SRC_ATOP);
        }

        for (int i = 0; i < activity_editText.length; i++) {
            EditText tempEditText = (EditText) findViewById(activity_editText[i]);
            tempEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if(b) {
                        onClick(view);
                    }
                }
            });
        }

        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.search);
        button.setOnClickListener(this);

        AutoCompleteTextView acTextView = (AutoCompleteTextView) findViewById(R.id.inviteFriendsEditText);
        //Set the number of characters the user must type before the drop down list is shown
        acTextView.setThreshold(1);
        //Set the adapter

        acTextView.setAdapter(new UserSearchAutoCompleteAdapter(this.getApplicationContext(), R.layout.user_search_row, App.usersCollections.getUsers()));
    }


    @Override
    public void onClick(View view) {
        System.out.println("button clicked " + view.getId());
        // initiate request callback for dialogs
        RequestCallback requestCallback = null;
        switch (view.getId()) {
            case R.id.backButton:
                finish();
                break;
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
            case R.id.dateEditText:
                requestCallback = new RequestCallback() {
                    @Override
                    public void onRequestComplete(String result) {
                        ((EditText)findViewById(R.id.dateEditText)).setText(result);
                    }
                };
                DatePickerFragment datePicker = new DatePickerFragment();
                datePicker.setCallback(requestCallback);
                datePicker.show(getFragmentManager(), "datePicker");

                break;
            case R.id.timeEditText:
                requestCallback = new RequestCallback() {
                    @Override
                    public void onRequestComplete(String result) {
                        ((EditText)findViewById(R.id.timeEditText)).setText(result);
                    }
                };
                TimePickerFragment timePicker  = new TimePickerFragment();
                timePicker.setCallback(requestCallback);
                timePicker.show(getFragmentManager(), "timePicker");
                break;
            case R.id.inviteFriendsEditText:

                break;
            case R.id.limitPeopleEditText:
                System.out.println("infiltrare");
                showLimitOfPeoplePickerDialog("Select Limit of People");
                break;
            case R.id.hashtagsEditText:
                break;
        }
    }

    /**
     * Update static map imageview
     * @param latLng
     */
    private void updateStaticMap(LatLng latLng){
        String parameters = MapUtils.getStaticMapFromLatLng(latLng);

        ApiUtils.getPNGBitmap(Configurations.STATIC_MAP_URL + parameters, new RequestCallback() {
            @Override
            public void onRequestComplete(String result) {
                Bitmap staticMap = MapUtils.stringToBitMap(result);
                ImageView staticMapImageView = ((ImageView) findViewById(R.id.mapImageView));
                staticMapImageView.setImageBitmap(staticMap);
                findViewById(R.id.searchForAddressTextView).setVisibility(View.GONE);
            }
        });
    }

    /**
     *   Return to main activity with shout object (precisely the shout id)
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

        // creator
        String creatorId = App.usersCollections.getCurrentlyLoggedInUser().getId();


        // get invitations
        tempEditText = (EditText) findViewById(R.id.inviteFriendsEditText);
        String invitationStr = tempEditText.getText().toString();
        String[] invitationStringArray = invitationStr.split(","); // split into array
        ArrayList<String> invitations = new ArrayList<>(); // add to array size
        for( int i = 0; i < invitationStringArray.length; i++)
            invitations.add(invitationStringArray[i]);


        // location
        String locationName = "";
        String locationCoordinates = "";
        if(shoutLocationPlace != null) {
            locationName = shoutLocationPlace.getName().toString();
            locationCoordinates = shoutLocationPlace.getLatLng().latitude + "," + shoutLocationPlace.getLatLng().longitude;
//            System.out.println("Location name : " + locationName + ", /n coordinate: " + shoutLocationPlace.getLatLng().toString());
        }

        // hashtags
        String hashtagsStr = ((EditText)findViewById(R.id.hashtagsEditText)).getText().toString();
        String[] hashtags = hashtagsStr.split(",");
        System.out.println("hashtags  " + hashtagsStr);
        return App.shoutsCollections.createNewShout(
                title, content, creatorId, date, participationLimit, invitations, locationName, locationCoordinates, hashtags);
    }

    /**
     * Create and show number picker dialog
     */
    private void showLimitOfPeoplePickerDialog(String title){
        LayoutInflater inflater = (LayoutInflater)
                getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View npView = inflater.inflate(R.layout.number_picker_dialog_layout, null);
        final NumberPicker numberPicker = (NumberPicker) npView.findViewById(R.id.numberPicker1);

        numberPicker.setMaxValue(50);
        numberPicker.setMinValue(0);
        setDividerColor(numberPicker, getResources().getColor(R.color.colorPrimary));
        numberPicker.setWrapSelectorWheel(false);
        AlertDialog dialog = new AlertDialog.Builder(NewShoutActivity.this)
                .setTitle(title)
                .setView(npView)
                .setPositiveButton("Ok",  new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int buttonId) {
                        dialog.dismiss();
                        updateLimitOfPeopleEditText(numberPicker.getValue());
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int buttonId) {
                                dialog.dismiss();
                            }
                        })
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {
                AlertDialog alertDialog = (AlertDialog) dialog;
                Button button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                button.setTextColor(getResources().getColor(R.color.colorPrimary));
                button = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                button.setTextColor(getResources().getColor(R.color.colorPrimary));

            }
        });
        dialog.show();
        //}
    }

    /**
     * update limit of people edit text
     * @param numberPickerValue
     */
    private void updateLimitOfPeopleEditText(int numberPickerValue){
        System.out.println("numberPickerValue " + numberPickerValue);
        EditText limitPeopleEditText = (EditText) findViewById(R.id.limitPeopleEditText);
        if (limitPeopleEditText != null) {
            System.out.println("life not null");
            limitPeopleEditText.setText(numberPickerValue + "");
        }else
            System.out.println("no life");
    }

    /**
     * change color of the dividers in the numberpicker dialog
     * @param picker
     * @param color
     */
    private void setDividerColor(NumberPicker picker, int color) {

        java.lang.reflect.Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (java.lang.reflect.Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    ColorDrawable colorDrawable = new ColorDrawable(color);
                    pf.set(picker, colorDrawable);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
