package com.nik.shouts.utils;

import com.nik.shouts.models.App;
import com.nik.shouts.models.User;

/**
 * Created by nik on 12/12/15.
 */

public class UserUtils {

    public static User getUserByUserName(String username) {
        for(User user : App.usersCollections.getUsers())
            if(user.getUsername().equals(username))
                return user;
        return null;
    }

    public static User getUserById(String ID) {
        for(User user : App.usersCollections.getUsers())
            if(user.getId().equals(ID))
                return user;
        return null;
    }
}
