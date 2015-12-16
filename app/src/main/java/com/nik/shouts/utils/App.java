package com.nik.shouts.utils;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.nik.shouts.models.Data;
import com.nik.shouts.models.User;

/**
 * Created by nik on 23/11/15.
 */

public class App {

    // Date object containing Shouts and Users arrays
    public static Data data;

    // currently logged in User in the App
    private static User currentlyLoggedInUser;

    /**
     * Currently logged in user
     * @return
     */
    public static User getCurrentlyLoggedInUser(){
        return currentlyLoggedInUser;
    }

}
