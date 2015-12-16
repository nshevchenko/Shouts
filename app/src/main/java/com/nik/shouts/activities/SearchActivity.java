package com.nik.shouts.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.nik.shouts.R;

/**
 * Created by nik on 23/11/15.
 */

public class SearchActivity extends Activity implements View.OnClickListener {

    private int[] activity_buttons = {

    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_search);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
//        findViewElements();
    }

    //
    // Set up main elements/views inside the activity
    //
    public void findViewElements() {

        // assign listener to all buttons in the activity
//        for (int i = 0; i < activity_buttons.length; i++) {
//            Button activity_button = (Button) this.findViewById(activity_buttons[i]);
//            activity_button.setOnClickListener(this);
//        }
//
//        EditText username = (EditText) findViewById(R.id.usernameEditText);
//
//        EditText password = (EditText) findViewById(R.id.passwordEditText);
//        password.setSelected(false);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        System.out.println("button clicked " + view.getId());
        switch (view.getId()) {
            case R.id.create:
                break;
        }
    }
}
