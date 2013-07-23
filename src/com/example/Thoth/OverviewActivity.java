package com.example.Thoth;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

    private RingList cards;
    private RingListNode currentCard;

    private RingList views;
    private RingListNode currentView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overview);

        flipper = ((ViewFlipper)findViewById(R.id.viewFlipper));
        flipper.setOnTouchListener(this);

        cards = new RingList();
        for (int i = 0; i < 22; i++) {
            cards.add("M " + String.valueOf(i));
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 14; j++) {
                cards.add(("m " + String.valueOf(i) + " - " + String.valueOf(j)));
            }
        }

        currentCard = cards.get(0);

        views = new RingList();
        views.add(0);
        views.add(1);
        views.add(2);

        currentView = views.get(0);
        setCardImage(flipper, (Integer) currentView.data, (String) currentCard.data);
        setCardImage(flipper, (Integer) currentView.prev.data, (String) currentCard.prev.data);
        setCardImage(flipper, (Integer) currentView.next.data, (String) currentCard.next.data);
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

    private String getResourceName(String string) {
        if (string.startsWith("M")) {
            String num = string.split(" ")[1];
            switch (Integer.parseInt(num)) {
                case 0:
                    return "fool";
                case 1:
                    return "magus";
                case 2:
                    return "priestess";
                case 3:
                    return "empress";
                case 4:
                    return "emperor";
                case 5:
                    return "hierophant";
                case 6:
                    return "lovers";
                case 7:
                    return "chariot";
                case 8:
                    return "adjustment";
                case 9:
                    return "hermit";
                case 10:
                    return "fortune";
                case 11:
                    return "lust";
                case 12:
                    return "hangedman";
                case 13:
                    return "death";
                case 14:
                    return "art";
                case 15:
                    return "devil";
                case 16:
                    return "tower";
                case 17:
                    return "star";
                case 18:
                    return "moon2";
                case 19:
                    return "sun";
                case 20:
                    return "aeon";
                case 21:
                    return "universe";
                default:
                    break;
            }
        }
        else if (string.startsWith("m")) {
            int type = Integer.parseInt(string.split(" ")[1]);
            int value = Integer.parseInt(string.split(" ")[3]);
            switch(type) {
                case 0:
                    return getMinor("cups", value);
                case 1:
                    return getMinor("disks", value);
                case 2:
                    return getMinor("wands", value);
                case 3:
                    return getMinor("swords", value);
                default:
                    break;
            }
        }
        return null;
    }

    private String getMinor(String type, int value) {
        String val;
        switch(value) {
            case 10:
                val = "princessof" + type;
                break;
            case 11:
                val = "princeof" + type;
                break;
            case 12:
                val = "queenof" + type;
                break;
            case 13:
                val = "knightof" + type;
                break;
            default:
                val = String.format("%02d", value + 1);
                break;
        }
        return (type + val);
    }

    private void setCardImage(ViewFlipper flipper, int viewId, String card) {
        ImageView image = (ImageView) ((LinearLayout)flipper.getChildAt(viewId)).getChildAt(0);
        JustifiedTextView justified = (JustifiedTextView) ((LinearLayout)flipper.getChildAt(viewId)).getChildAt(1);

        Log.i("Overview", getResourceName(card));
        int drawableID = this.getResources().getIdentifier(getResourceName(card), "drawable", getPackageName());
        int stringID = this.getResources().getIdentifier(getResourceName(card), "string", getPackageName());
        image.setImageResource(drawableID);
        justified.setText(stringID);
    }

    private void onRightSwipe() {
        currentCard = currentCard.next;
        currentView = currentView.next;
//        setCardImage(flipper, (Integer) currentView.data, (String) currentCard.data);
//        setCardImage(flipper, (Integer) currentView.prev.data, (String) currentCard.prev.data);
        setCardImage(flipper, (Integer) currentView.next.data, (String) currentCard.next.data);


        flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.go_next_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(this,R.anim.go_next_out));
        flipper.showNext();
        isSwipe = false;
    }

    private void onLeftSwipe() {
        currentCard = currentCard.prev;
        currentView = currentView.prev;
//        setCardImage(flipper, (Integer) currentView.data, (String) currentCard.data);
        setCardImage(flipper, (Integer) currentView.prev.data, (String) currentCard.prev.data);
//        setCardImage(flipper, (Integer) currentView.next.data, (String) currentCard.next.data);

        flipper.setInAnimation(AnimationUtils.loadAnimation(this,R.anim.go_prev_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(this,R.anim.go_prev_out));
        flipper.showPrevious();
        isSwipe = false;
    }
}