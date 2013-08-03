package com.example.Thoth;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: waves
 * Date: 02.08.13
 * Time: 3:39
 * To change this template use File | Settings | File Templates.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }


    public void onDateSet(DatePicker view, int year, int month, int day) {
        String date = String.format("%02d%02d%04d", month, day, year);

        Intent intent = new Intent(getActivity(), GrowResultActivity.class);
        intent.putExtra("Date", date);
        startActivity(intent);
    }
}
