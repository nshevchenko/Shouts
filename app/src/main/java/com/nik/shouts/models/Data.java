package com.nik.shouts.models;

import android.util.Log;

import com.nik.shouts.utils.App;
import com.nik.shouts.utils.UserUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by nik on 12/12/15.
 */

public class Data {


    private ArrayList<Shout> shouts;
    private ArrayList<User> users;
    private User currentLoggedinUser;

    public void init(){
        App.data = this;
        shouts = new ArrayList<Shout>();
        users = new ArrayList<User>();
    }

    // add Data to arrays
    public void addUser(User user){
        users.add(user);
    }

    public void addShout(Shout shout){
        shouts.add(shout);
    }

    public ArrayList<Shout> getShouts(){
        return this.shouts;
    }

    public ArrayList<User> getUsers(){
        return this.users;
    }

    public void setCurrentLoggedInUser(User user) {
        currentLoggedinUser = currentLoggedinUser;
    }
}
