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

import com.nik.shouts.R;
import com.nik.shouts.interfaces.ApiRequestCallback;
import com.nik.shouts.models.App;
import com.nik.shouts.models.User;
import com.nik.shouts.utils.ApiUtils;
import com.nik.shouts.utils.Configurations;
import com.nik.shouts.utils.Messages;

/**
 * Created by nik on 23/11/15.
 */

public class RegisterActivity extends Activity implements View.OnClickListener  {


    private int[] activity_buttons = {
            R.id.doneButton,

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // find all elements in the acitivty
        findActivityElements();
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
        findViewById(R.id.usernameEditText).setSelected(false);
        findViewById(R.id.nameAndSurnameEditText).setSelected(false);;
        findViewById(R.id.passwordEditText).setSelected(false);
        findViewById(R.id.emailEditText).setSelected(false);

        ImageView tempImageView = (ImageView) findViewById(R.id.usernameImageView);
        tempImageView.getDrawable().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
        tempImageView = (ImageView) findViewById(R.id.nameAndSurnameImageView);
        tempImageView.getDrawable().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
        tempImageView = (ImageView) findViewById(R.id.passwordImageView);
        tempImageView.getDrawable().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
        tempImageView = (ImageView) findViewById(R.id.emailImageView);
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
            case R.id.backButton:
                finish();
            case R.id.doneButton:     // done button clicked
                register();
                break;
        }
    }

    public void register(){
        final User newUser = generateUserFromElements();

        ApiRequestCallback apiCallback = new ApiRequestCallback() {
            @Override
            public void onRequestComplete(String result) {
                if(result.equals(Configurations.SUCCESS_STATUS_CODE))
                    returnToLoginActivity(newUser.getId());
                else
                    showFailedSnackBar();
            }
        };

        // download app data with the related callback
        ApiUtils.uploadNewJsonObject(apiCallback, newUser.toJSON());
    }


    public void returnToLoginActivity(String newUserId){
        Intent intent = new Intent();
        intent.putExtra(Configurations.REQUEST_STRING_NEW_USER_ID, newUserId);
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * Creates new User object from data from the screen
     * @return
     */
    private User generateUserFromElements(){
        // read title
        EditText tempEditText = (EditText) findViewById(R.id.usernameEditText);
        String username = tempEditText.getText().toString();

        // read content
        tempEditText = (EditText) findViewById(R.id.nameAndSurnameEditText);
        String nameAndSurname = tempEditText.getText().toString();

        // read email
        tempEditText = (EditText) findViewById(R.id.emailEditText);
        String email = tempEditText.getText().toString();

        // get date
        tempEditText = (EditText) findViewById(R.id.passwordEditText);
        String password = tempEditText.getText().toString();

        return App.usersCollections.createNewUser(username, email, nameAndSurname, password);
    }

    private void showFailedSnackBar(){
        Snackbar snackbar = Snackbar.make(findViewById(R.id.main_content_layout), Messages.ERROR_UPLOADING_NEW_USER, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}

