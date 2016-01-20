package com.nik.shouts.models.collections;

import com.nik.shouts.models.App;
import com.nik.shouts.models.User;
import com.nik.shouts.utils.UserUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public ArrayList<User> getFriends(User user) {
        ArrayList<User> friends = new ArrayList<>();

        for(int i = 0; i < user.getFriendsIDs().length; i ++){
            String friendId = user.getFriendsIDs()[i];
            User friend = UserUtils.getUserById(friendId);
            friends.add(friend);
            System.out.println("found friend " + user.getUsername());
        }
        return friends;
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
        // set user friends
        currentlyLoggedInUser.setFriends(App.usersCollections.getFriends(currentlyLoggedInUser));
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


    public User createNewUser(JSONObject jsonObject){
        User user = new User(jsonObject);
        addUser(user);
        return user;
    }

    /**
     * Prase users initial json object
     * @param mainObject
     * @throws JSONException
     */
    public void parseJsonUsers(JSONObject mainObject) throws JSONException {
        JSONArray users = mainObject.getJSONArray("users");
        for(int i = 0; i < users.length(); i++) {
            JSONObject jsonObj = users.getJSONObject(i);
            // create the user and add to app's data
            createNewUser(jsonObj);
        }
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
