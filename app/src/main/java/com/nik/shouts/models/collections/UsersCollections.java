package com.nik.shouts.models.collections;

import com.nik.shouts.models.App;
import com.nik.shouts.models.User;

import java.util.ArrayList;

/**
 * Created by nik on 12/12/15.
 */

public class UsersCollections {

    private ArrayList<User> users;
    private User currentlyLoggedInUser;

    public void init(){
        App.usersCollections = this;
        users = new ArrayList<User>();
    }

    // add Data to arrays
    public void addUser(User user){
        users.add(user);
    }

    public ArrayList<User> getUsers(){
        return this.users;
    }

    /**
     * Currently logged in user
     * @return
     */
    public User getCurrentlyLoggedInUser(){
        return currentlyLoggedInUser == null ? new User() : currentlyLoggedInUser;
    }

    public void setCurrentLoggedInUser(User user) {
        currentlyLoggedInUser = user;
    }
}
