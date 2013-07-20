package com.example.Thoth;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created with IntelliJ IDEA.
 * User: waves
 * Date: 19.07.13
 * Time: 22:22
 * To change this template use File | Settings | File Templates.
 */
public class DispositionActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this));
    }
}