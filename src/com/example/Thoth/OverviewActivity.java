package com.example.Thoth;

import android.app.Activity;
import android.content.Intent;
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

        Intent intent = getIntent();
        String message = intent.getStringExtra("CardName");

        for (int i=0; i<78; i++)
            cards.add(Utils.GetCardCode(i));

        Log.i("Test", "CARD: " + message);
        if (message != null) {
            if (message.startsWith("M "))
                message = message.substring(2);
            currentCard = cards.get(Integer.valueOf(message));
        }
        else
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

    private void setCardImage(ViewFlipper flipper, int viewId, String card) {
        ImageView image = (ImageView) ((LinearLayout)flipper.getChildAt(viewId)).getChildAt(0);
        JustifiedTextView justified = (JustifiedTextView) ((LinearLayout)flipper.getChildAt(viewId)).getChildAt(1);

        Log.i("Overview", Utils.GetResourceName(card));
        int drawableID = this.getResources().getIdentifier(Utils.GetResourceName(card), "drawable", getPackageName());
        int stringID = this.getResources().getIdentifier(Utils.GetResourceName(card), "string", getPackageName());
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