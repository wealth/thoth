package com.example.Thoth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

/**
 * Created with IntelliJ IDEA.
 * User: waves
 * Date: 02.08.13
 * Time: 4:13
 * To change this template use File | Settings | File Templates.
 */
public class GrowActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grow);
    }

    public void onCalculate(View view) {
        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        String date = String.format("%02d%02d%04d", datePicker.getMonth(), datePicker.getDayOfMonth(), datePicker.getYear());

        Intent intent = new Intent(this, GrowResultActivity.class);
        intent.putExtra("Date", date);
        startActivity(intent);
    }
}
