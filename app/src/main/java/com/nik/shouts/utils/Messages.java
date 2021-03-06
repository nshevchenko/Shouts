package com.nik.shouts.utils;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.nik.shouts.R;

/**
 * Created by nik on 15/12/15.
 */

public class Messages {

    // GENERAL MESSAGES
    public static String INTERNET_CONNECTION_REQUIRED = "Internet connection is required";

    // NEW SHOUT SCREEN MESSAGES

    public static String ERROR_NEW_SHOUT_TITLE_REQUIRED = "Title is required";
    public static String ERROR_NEW_SHOUT_DESCRIPTION_REQUIRED = "Description is required";
    public static String ERROR_NEW_SHOUT_TIME_REQUIRED = "Time is required";
    public static String ERROR_DATE_IN_THE_PAST = "Date is in the past";

    // NEW USER SCREEN MESSAGES

    public static String ERROR_NEW_USER_NAME_REQUIRED = "Name is required";
    public static String ERROR_NEW_SHOUT_EMAIL_REQUIRED = "Email is required";
    public static String ERROR_NEW_SHOUT_PASSWORD_REQUIRED = "Password is required";
    public static String ERROR_PASSWORD_TOO_SHORT = "Your password is too short";
    public static String ERROR_UPLOADING_NEW_USER = "Sorry, technical problems are getting fixed... :)";


    // UPLAOD NEW SHOUT MESSAGES

    public static String ERROR_UPLOAD_NEW_SHOUT = "Error uploading your shout";
    public static String DONE_UPLOAD_NEW_SHOUT = "Your new shout is Live!";
    public static String DONE_ADD_NEW_SHOUT_PARTICIPANT = "Looking foward to see you!";
    public static String DONE_UPDATE_NEW_SHOUT = "Your shout has been updated";

    public static void showSnackBar(View view, String message ) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}
