package com.nik.shouts.auth;

import com.nik.shouts.activities.LoginActivity;
import com.nik.shouts.models.User;
import com.nik.shouts.utils.App;
import com.nik.shouts.utils.UserUtils;

/**
 * Created by nik on 12/12/15.
 */

public class Authorization {


    public static boolean checkCredentials(String username, String password){

        User loggedInUser = UserUtils.getUserByUserName(username);
        if(password.equals(loggedInUser.getPassword())){
            App.data.setCurrentLoggedInUser(loggedInUser);
            return true;
        }
        return false;
    }
}
