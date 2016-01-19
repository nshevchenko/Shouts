package com.nik.shouts.models;


import android.app.Activity;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;


import com.nik.shouts.R;
import com.nik.shouts.models.collections.ShoutsCollections;
import com.nik.shouts.models.collections.UsersCollections;

/**
 * Created by nik on 23/11/15.
 */

public class App {

    // Date object containing Shouts and Users arrays
    public static UsersCollections usersCollections;
    public static ShoutsCollections shoutsCollections;


    /*
    * OPEN NEW FRAGMENT PARENT ACTIVITY, ALLOW BACK NAVIGATION
    */
    public static void openFragmentAsParent(AppCompatActivity act, Fragment newFragment) {
        FragmentTransaction transaction = act.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /*
    * OPEN NEW FRAGMENT PARENT ACTIVITY
    */
    public static void openFragment(AppCompatActivity act, Fragment newFragment) {
        FragmentTransaction transaction = act.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content, newFragment);
        transaction.commit();
    }

    /**
     * open activity as Parent
     * @param act
     * @param newIntentClass
     */
    public static void openActivityAsParent(Activity act, Class newIntentClass, int id){
        Intent newIntent = new Intent(act, newIntentClass);
        act.startActivityForResult(newIntent, id);
    }

    /**
     * Open activitiy
     * @param act
     * @param newIntentClass
     */
    public static void openActivity(Activity act, Class newIntentClass){
        Intent newIntent = new Intent(act, newIntentClass);
        act.startActivity(newIntent);
    }


}
