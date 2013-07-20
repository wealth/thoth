package com.example.Thoth;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: waves
 * Date: 19.07.13
 * Time: 22:20
 * To change this template use File | Settings | File Templates.
 */
public class OverviewActivity extends Activity implements View.OnTouchListener {
    private ViewFlipper flipper;

    private LinkedList<String> cards;
    private String currentCard;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overview);

        flipper = ((ViewFlipper)findViewById(R.id.viewFlipper));
        flipper.setOnTouchListener(this);

        cards = new LinkedList<String>();
        for (int i = 0; i < 23; i++) {
            cards.add("M " + String.valueOf(i));
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 14; j++) {
                cards.add(("m " + String.valueOf(i) + " - " + String.valueOf(j)));
            }
        }

        currentCard = cards.get(0);
    }

    private float fromPosition;
    private boolean isSwipe = false;
    public boolean onTouch(View view, MotionEvent event)
    {
        float MOVE_LENGTH = view.getWidth() / 16;
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN: // Пользователь нажал на экран, т.е. начало движения
                // fromPosition - координата по оси X начала выполнения операции
                fromPosition = event.getX();
                isSwipe = true;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("Overview", String.valueOf(isSwipe));
                if (isSwipe) {
                    float toPosition = event.getX();
                    // MOVE_LENGTH - расстояние по оси X, после которого можно переходить на след. экран
                    // В моем тестовом примере MOVE_LENGTH = 150
                    if ((fromPosition - MOVE_LENGTH) > toPosition)
                    {
                        onRightSwipe();
                    }
                    else if ((fromPosition + MOVE_LENGTH) < toPosition)
                    {
                        onLeftSwipe();
                    }
                }
            default:
                break;
        }
        return true;
    }

    private int getDrawable(String string) {
        if (string.startsWith("M")) {
            String num = string.split(" ")[1];
            switch (Integer.getInteger(num)) {
                case 0:
                    return R.drawable.fool;
                case 1:
                    return R.drawable.magus;
                case 2:
                    return R.drawable.priestess;
                case 3:
                    return R.drawable.empress;
                case 4:
                    return R.drawable.emperor;
                case 5:
                    return R.drawable.hierophant;
                case 6:
                    return R.drawable.lovers;
                case 7:
                    return R.drawable.chariot;
                case 8:
                    return R.drawable.adjustment;
                case 9:
                    return R.drawable.hermit;
                case 10:
                    return R.drawable.fortune;
                case 11:
                    return R.drawable.lust;
                case 12:
                    return R.drawable.hangedman;
                case 13:
                    return R.drawable.death;
                case 14:
                    return R.drawable.art;
                case 15:
                    return R.drawable.devil;
                case 16
            }
        }
        else if (string.startsWith("m")) {

        }
    }

    private void onRightSwipe() {
        ImageView image = (ImageView) flipper.getCurrentView().findViewById(R.id.imageView);
        int nextIdx = (cards.indexOf(currentCard) + 1) % 78;
        currentCard = cards.get(nextIdx);

        flipper.setInAnimation(AnimationUtils.loadAnimation(this,R.anim.go_next_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(this,R.anim.go_next_out));
        flipper.showNext();
        isSwipe = false;
    }

    private void onLeftSwipe() {
        flipper.setInAnimation(AnimationUtils.loadAnimation(this,R.anim.go_prev_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(this,R.anim.go_prev_out));
        flipper.showPrevious();
        isSwipe = false;
    }
}