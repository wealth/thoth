package com.example.Thoth;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView {
    /**Загружаем спрайт*/
    private Bitmap bmp;

    /**Поле рисования*/
    private SurfaceHolder holder;

    /**объект класса GameView*/
    private GameManager gameLoopThread;

    /**Объект класса Sprite*/
    private List<Sprite> sprites = new ArrayList<Sprite>();
    private ArrayList<String> deck = new ArrayList<String>();

    public int screenHeight;
    public int screenWidth;

    /**Конструктор*/
    public GameView(Context context)
    {
        super(context);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;

        gameLoopThread = new GameManager(this);
        holder = getHolder();

          /*Рисуем все наши объекты и все все все*/
        holder.addCallback(new SurfaceHolder.Callback()
        {
            /*** Уничтожение области рисования */
            public void surfaceDestroyed(SurfaceHolder holder)
            {
                boolean retry = true;
                gameLoopThread.setRunning(false);
                while (retry)
                {
                    try
                    {
                        gameLoopThread.join();
                        retry = false;
                    } catch (InterruptedException e)
                    {
                    }
                }
            }

            /** Создание области рисования */
            public void surfaceCreated(SurfaceHolder holder)
            {
                if (gameLoopThread.isAlive())
                    gameLoopThread.setRunning(false);
                gameLoopThread.setRunning(true);
                gameLoopThread.start();
            }

            /** Изменение области рисования */
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height)
            {
            }
        });

        initializeCards();
        arrangeCards();
        sliceCards();
        positionCards();
    }

    private Sprite createSprite(int resource, String card) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
        return new Sprite(this,bmp,card);
    }

    private void initializeCards() {
        for (int i=0;i<78;i++)
            deck.add(String.valueOf(i));
    }

    private void arrangeCards() {
        Collections.shuffle(deck);
    }

    private void sliceCards() {
        Random rnd = new Random();
        int slice = rnd.nextInt(78) + 1;
        List<String> lowSlice = deck.subList(0, slice);
        List<String> topSlice = deck.subList(slice + 1, 78);
        (deck = new ArrayList<String>(topSlice)).addAll(lowSlice);
    }

    private void positionCards() {
        for (int i=0;i<15;i++) {
            sprites.add(createSprite(R.drawable.cardback, deck.get(i)));
            sprites.get(i).SetPosition(i);
        }
    }

    /**Функция рисующая все спрайты и фон*/
    protected void onDraw(Canvas canvas)
    {
        if (canvas != null) {
            canvas.drawColor(Color.BLACK);
            for(Sprite sprite : sprites) {
                sprite.onDraw(canvas);
            }
        }
    }

    /**Обработка косания по экрану*/
    public boolean onTouchEvent(MotionEvent event) {
        synchronized (getHolder()) {
            for (int i = sprites.size()-1; i > 0; i--)
            {
                Sprite sprite = sprites.get(i);
                if (sprite.isCollision(event.getX(),event.getY()))
                {
                    sprites.remove(sprite);
                    break;
                }
            }
        }
        return super.onTouchEvent(event);
    }
}