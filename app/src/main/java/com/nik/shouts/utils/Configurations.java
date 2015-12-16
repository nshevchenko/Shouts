package com.nik.shouts.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.nik.shouts.models.Shout;
import com.nik.shouts.models.User;

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
    // LOGIN MODES AVAILABLE IN THE APP
    public static final int APP_MODE_FACEBOOK_LOGIN = 0;
    public static final int APP_MODE_USERNAME_LOGIN = 1;
    public static final int APP_MODE_TRY_APP_MODE   = 2;


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
