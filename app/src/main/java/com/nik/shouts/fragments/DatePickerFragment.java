package com.nik.shouts.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import com.nik.shouts.R;
import com.nik.shouts.interfaces.RequestCallback;
import com.nik.shouts.utils.Configurations;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by nik on 18/01/16.
 */

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private RequestCallback callback;

    public void setCallback(RequestCallback callback){
        this.callback = callback;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), R.style.AppTheme, this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
//        System.out.println(year + "." + month + "." + day );
        try {
            Date date = Configurations.DATE_FORMAT_SHOUT_CREATION_DB.parse(year + "." + month + "." + day ); //yyyy.MM.dd
            callback.onRequestComplete(Configurations.DATE_FORMAT_SHOUT_CREATION_USER.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
