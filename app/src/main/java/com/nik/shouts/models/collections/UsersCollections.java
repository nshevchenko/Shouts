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
        return currentlyLoggedInUser;
    }

    public void setCurrentLoggedInUser(User user) {
        currentlyLoggedInUser = user;
    }


    /**
     * Create factory new user
     * @param username
     * @param email
     * @param password
     * @param nameAndSurname
     * @param friendsIDs
     * @param interests
     */
    public User createNewUser(String username, String email, String nameAndSurname, String password, String[] friendsIDs, String[] interests) {
        String newUserID = users.size() + "";
        User user = new User(newUserID, username, email, nameAndSurname, password, friendsIDs, interests);
        addUser(user);
        return user;
    }


    /**
     * Create factory new user
     * @param username
     * @param email
     * @param nameAndSurname
     * @param password
     */
    public User createNewUser(String username, String email, String nameAndSurname, String password) {
        String newUserID = users.size() + "";
        User user = new User(newUserID, username, email, nameAndSurname, password, new String[]{}, new String[]{});
        addUser(user);
        return user;
    }

    /**
     * create new guest as a user
     * @return
     */
    public User createNewGuest(){
        String newUserID = users.size() + "";
        User newGuest = new User(newUserID);
        return newGuest;
    }
}
