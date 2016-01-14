package com.nik.shouts.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.gms.common.api.Api;
import com.nik.shouts.R;
import com.nik.shouts.auth.Authorization;
import com.nik.shouts.interfaces.ApiRequestCallback;
import com.nik.shouts.models.collections.ShoutsCollections;
import com.nik.shouts.models.collections.UsersCollections;
import com.nik.shouts.utils.ApiUtils;
import com.nik.shouts.models.App;
import com.nik.shouts.utils.Configurations;

/**
 * Created by nik on 23/11/15.
 */

public class LoginActivity extends Activity implements View.OnClickListener  {


    private int[] activity_buttons = {
            R.id.loginButton,
            R.id.tryAppButton,
            R.id.facebookLoginButton
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // set Initial data
        setInitialData();
        // find all elements in the acitivty
        findActivityElements();
    }

    /**
     * Set initial data of the application
     */
    public void setInitialData(){
        // primary sections of the activity.
        App.usersCollections = new UsersCollections();
        App.shoutsCollections= new ShoutsCollections();
        App.usersCollections.init();
        App.shoutsCollections.init();
    }
    //
    // Set up main elements/views inside the activity
    //
    public void findActivityElements() {
        // assign listener to all buttons in the activity
        for (int i = 0; i < activity_buttons.length; i++) {
            Button activity_button = (Button) this.findViewById(activity_buttons[i]);
            activity_button.setOnClickListener(this);
        }
        ProgressBar loader = (ProgressBar) findViewById(R.id.progressBar);
        loader.setVisibility(View.GONE);
        EditText username = (EditText) findViewById(R.id.usernameEditText);
        username.setSelected(false);
        EditText password = (EditText) findViewById(R.id.passwordEditText);
        password.setSelected(false);

        ImageView tempImageView = (ImageView) findViewById(R.id.emailImageView);
        tempImageView.getDrawable().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
        tempImageView = (ImageView) findViewById(R.id.passwordImageView);
        tempImageView.getDrawable().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
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
            case R.id.loginButton:     // try button clicked
                login(Configurations.APP_MODE_USERNAME_LOGIN);
                break;
            case R.id.facebookLoginButton:     // try button clicked
                login(Configurations.APP_MODE_FACEBOOK_LOGIN);
                break;
            case R.id.tryAppButton:     // try button clicked
                login(Configurations.APP_MODE_TRY_APP_MODE);
                break;
        }
    }

    public void login(final int appMode){
//        System.out.println(appMode + " " + Configurations.APP_MODE_USERNAME_LOGIN);

        if(appMode == Configurations.APP_MODE_USERNAME_LOGIN) {
            // read login credentials
            String usernameEditText = ((EditText) findViewById(R.id.usernameEditText)).getText().toString();
            String passwordEditText = ((EditText) findViewById(R.id.passwordEditText)).getText().toString();
            // check user credentials here
            boolean resultLogin = Authorization.checkCredentials(usernameEditText, passwordEditText);
            if (! resultLogin ) {
                Snackbar snackbar = Snackbar.make(findViewById(R.id.main_content), "Username or Password incorrect :(", Snackbar.LENGTH_SHORT);
                snackbar.show();
                return; // don't login if credentials are wrong
            }
        }
        // LOGIN CORRECT
        // change UI
        findViewById(R.id.usernameEditText).setVisibility(View.GONE);
        findViewById(R.id.passwordEditText).setVisibility(View.GONE);
        ProgressBar loader = (ProgressBar) findViewById(R.id.progressBar);
        loader.setVisibility(View.VISIBLE);
        loader.setProgress(40);

        // Start to download user's data from API. Once complete callback to open the main Button

        ApiRequestCallback apiCallback = new ApiRequestCallback() {
            @Override
            public void onRequestComplete(String result) {
                System.out.println(result);
                ApiUtils.parseAppData(result);
                openMainActivity(appMode);
            }
        };
        // download app data with the related callback
        ApiUtils.downloadInitialAppData(apiCallback);
    }


    public void openMainActivity(int appMode){
        Intent intent = new Intent(this, MainActivity.class);
        String appModeStr = "" + appMode;
        intent.putExtra("com.example.myfirstapp.MODE", appModeStr);
        startActivity(intent);
        finish();
    }
}

