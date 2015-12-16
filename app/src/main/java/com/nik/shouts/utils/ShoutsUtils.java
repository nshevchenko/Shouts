package com.nik.shouts.utils;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.nik.shouts.models.Shout;

import java.util.ArrayList;

/**
 * Created by nik on 12/12/15.
 */

public class ShoutsUtils {


    public static ArrayList<Shout> getLastUserShouts(String userId){
        ArrayList<Shout> userShouts = new ArrayList<Shout>();
        for(Shout shout : App.data.getShouts()){
            if(userId.equals(shout.getCreator().getId()))
                userShouts.add(shout);
        }
        return userShouts;
    }

    public static ArrayList<Shout> getLastAcceptedShoutsByUserId(String userId){
        ArrayList<Shout> userLastAcceptedShouts = new ArrayList<Shout>();
        for(Shout shout : App.data.getShouts()){
            if(shout.isUserAParticipant(userId))
                userLastAcceptedShouts.add(shout);
        }
        return userLastAcceptedShouts;
    }
}
