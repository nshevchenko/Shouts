package com.nik.shouts.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.nik.shouts.models.Shout;
import com.nik.shouts.models.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by nik on 22/11/15.
 */

public class Configurations {

    // TABS
    public static final int PAGE_COUNT = 2;
    public static final String[] TABS = {"MAP", "FEED"};

    public static final int REQUEST_CODE_ASK_PERMISSIONS = 123;
    public static final int REQUEST_CODE_PLACE_PICKER = 122;

    // LOGIN MODES AVAILABLE IN THE APP
    public static final int APP_MODE_FACEBOOK_LOGIN = 0;
    public static final int APP_MODE_USERNAME_LOGIN = 1;
    public static final int APP_MODE_TRY_APP_MODE   = 2;


    // REQUEST CODES FOR OPENING PARENT ACTIVITIES
    public static final int REQUEST_CODE_PARENT_NEW_SHOUT_ACTIVITIY = 1;
    public static final int REQUEST_CODE_PARENT_NEW_USER_ACTIVITIY = 2;
    public static final int REQUEST_CODE_PARENT_NEW_SEARCH_ACTIVITIY = 3;

    public static final String REQUEST_STRING_NEW_SHOUT_ID = "new_created_shout_id";
    public static final String REQUEST_STRING_NEW_SEARCH_ID = "new_search_id";
    public static final String REQUEST_STRING_NEW_USER_ID = "new_user_id";


    public static final String SUCCESS_STATUS_CODE = "200";

    // DATE
    public static final DateFormat DATE_FORMAT_SHOUT_CREATION_DB = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    public static final DateFormat DATE_FORMAT_SHOUT_CREATION_USER = new SimpleDateFormat("EEE, MMM d, ''yy");


    // INIT SOME DUMB DATA
    public void init() {
        // APP MAIN ARRAYS

    }

    public static boolean validateDateInThePast(Date date){
        return true;
    }

    public static boolean checkEditTextIsEmpty(View view, int editTextId) {
        if(((EditText) view.findViewById(editTextId)).getText().length() == 0)
            return true;
        return false;
    }
}
