package com.example.Thoth;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

public class MenuActivity extends Activity{
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onOverview(View view) {
        Intent intent = new Intent(this, OverviewListActivity.class);
        startActivity(intent);
    }

    public void onGrow(View view) {
        Intent intent = new Intent(this, GrowActivity.class);
        startActivity(intent);
    }

    public void onQuestion(View view) {
        Intent intent = new Intent(this, DispositionActivity.class);
        intent.putExtra("Layout", "Question");
        startActivity(intent);
    }

    public void onSeven(View view) {
        Intent intent = new Intent(this, DispositionActivity.class);
        intent.putExtra("Layout", "Seven");
        startActivity(intent);
    }

    public void onCeltic(View view) {
        Intent intent = new Intent(this, DispositionActivity.class);
        intent.putExtra("Layout", "Celtic");
        startActivity(intent);
    }

    public void onModified(View view) {
        Intent intent = new Intent(this, DispositionActivity.class);
        intent.putExtra("Layout", "Modified");
        startActivity(intent);
    }

    public void onCenters(View view) {
        Intent intent = new Intent(this, DispositionActivity.class);
        intent.putExtra("Layout", "Centers");
        startActivity(intent);
    }

    public void onRelations(View view) {
        Intent intent = new Intent(this, DispositionActivity.class);
        intent.putExtra("Layout", "Relations");
        startActivity(intent);
    }

    public void onBalance(View view) {
        Intent intent = new Intent(this, DispositionActivity.class);
        intent.putExtra("Layout", "Balance");
        startActivity(intent);
    }

    public void onDisposition(View view) {
        final Dialog dialog = new Dialog(this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog);
        dialog.setCanceledOnTouchOutside(true);

        final DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.datePicker);
        Button button = (Button) dialog.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = String.format("%02d%02d%04d", datePicker.getMonth(), datePicker.getDayOfMonth(), datePicker.getYear());

                Intent intent = new Intent(getApplicationContext(), GrowResultActivity.class);
                intent.putExtra("Date", date);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
