package com.nik.shouts.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nik.shouts.R;
import com.nik.shouts.auth.Authorization;
import com.nik.shouts.interfaces.RequestCallback;
import com.nik.shouts.models.User;
import com.nik.shouts.models.collections.ShoutsCollections;
import com.nik.shouts.models.collections.UsersCollections;
import com.nik.shouts.utils.ApiUtils;
import com.nik.shouts.models.App;
import com.nik.shouts.utils.Configurations;
import com.nik.shouts.utils.UserUtils;

/**
 * Created by nik on 23/11/15.
 */

public class LoginActivity extends Activity implements View.OnClickListener  {


    private int[] activity_buttons = {
            R.id.doneButton,
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

        // Start to download user's data from API. Once complete callback to open the main Button

        RequestCallback apiCallback = new RequestCallback() {
            @Override
            public void onRequestComplete(String result) {
                System.out.println(result);
                ApiUtils.parseAppData(result);
                findViewById(R.id.doneButton).setVisibility(View.VISIBLE);
            }
        };
        // download app data with the related callback
        ApiUtils.getRequestWithCallBack(Configurations.REMOTE_SERVER_URL, apiCallback);
    }


    /**
     * Set up main elements/views inside the activity
     */
    public void findActivityElements() {
        // assign listener to all buttons in the activity
        for (int i = 0; i < activity_buttons.length; i++) {
            Button activity_button = (Button) this.findViewById(activity_buttons[i]);
            activity_button.setOnClickListener(this);
        }

        // disable done Button initially
        findViewById(R.id.doneButton).setVisibility(View.GONE);

        ProgressBar loader = (ProgressBar) findViewById(R.id.progressBar);
        loader.setVisibility(View.GONE);
        EditText username = (EditText) findViewById(R.id.usernameEditText);
        username.setSelected(false);
        EditText password = (EditText) findViewById(R.id.passwordEditText);
        password.setSelected(false);
        addPasswordOnTextChangerListener(password);

        ImageView tempImageView = (ImageView) findViewById(R.id.nameImageView);
        tempImageView.getDrawable().setColorFilter(getResources().getColor(R.color.colorPrimaryLight), PorterDuff.Mode.SRC_ATOP);
        tempImageView = (ImageView) findViewById(R.id.passwordImageView);
        tempImageView.getDrawable().setColorFilter(getResources().getColor(R.color.colorPrimaryLight), PorterDuff.Mode.SRC_ATOP);
    }

    /**
     * Add listener to the text changer in the password field to enable done button
     * @param password
     */
    private void addPasswordOnTextChangerListener(EditText password){
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0
                        && !findViewById(R.id.doneButton).isEnabled()
                        && findViewById(R.id.doneButton).getVisibility() == View.VISIBLE)
                    findViewById(R.id.doneButton).setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
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
            case R.id.doneButton:     // try button clicked
                login(Configurations.APP_MODE_USERNAME_LOGIN);
                break;
            case R.id.facebookLoginButton:     // try button clicked
                login(Configurations.APP_MODE_FACEBOOK_LOGIN);
                break;
            case R.id.tryAppButton:     // try button clicked
                login(Configurations.APP_MODE_TRY_APP_MODE);
                break;
            case R.id.registerUserTextView:
                App.openActivityAsParent(this, RegisterActivity.class, Configurations.REQUEST_CODE_PARENT_NEW_USER_ACTIVITIY);
                break;
        }
    }

    public void login(final int appMode){
//        System.out.println(appMode + " " + Configurations.APP_MODE_USERNAME_LOGIN);

        switch(appMode) {
            case Configurations.APP_MODE_USERNAME_LOGIN:
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

                break;

            case Configurations.APP_MODE_TRY_APP_MODE :
                User newGuest = App.usersCollections.createNewGuest();
                App.usersCollections.setCurrentLoggedInUser(newGuest);
                RequestCallback apiCallback = new RequestCallback() {
                    @Override
                    public void onRequestComplete(String result) {
                        openMainActivity(appMode);
                    }
                };
                // download app data with the related callback
                ApiUtils.uploadNewJsonObject(apiCallback, newGuest.toJSON());
                break;
        }
        // LOGIN CORRECT
        // change UI
        findViewById(R.id.usernameEditText).setEnabled(false);
        findViewById(R.id.passwordEditText).setVisibility(View.GONE);
        findViewById(R.id.doneButton).setEnabled(false);
        ProgressBar loader = (ProgressBar) findViewById(R.id.progressBar);

        loader.setVisibility(View.VISIBLE);
        loader.setProgress(40);

        openMainActivity(appMode);

    }

    /**
     * On activity result returning from register user activity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("callback from parent (y)");
        switch (requestCode) {
            case Configurations.REQUEST_CODE_PARENT_NEW_USER_ACTIVITIY:
                if (resultCode == RESULT_OK) {
                    String newUserId = data.getStringExtra(Configurations.REQUEST_STRING_NEW_USER_ID);
                    System.out.println("newUserId " + newUserId );
                    User currentNewUser = UserUtils.getUserById(newUserId);
                    try {
                        System.out.println("currentNewUser" + currentNewUser.getNameAndSurname());
                        ((EditText) findViewById(R.id.usernameEditText)).setText(currentNewUser.getUsername());
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }
                }
                break;
        }
    }

    /**
     * Open main activityi after successful login
     * @param appMode
     */
    public void openMainActivity(int appMode){
        Intent intent = new Intent(this, MainActivity.class);
        String appModeStr = "" + appMode;
        intent.putExtra("com.example.myfirstapp.MODE", appModeStr);
        startActivity(intent);
        finish();
    }
}

