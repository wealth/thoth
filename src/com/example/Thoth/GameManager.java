package com.example.Thoth;

import android.graphics.Canvas;

/**
 * Created with IntelliJ IDEA.
 * User: waves
 * Date: 19.07.13
 * Time: 23:18
 * To change this template use File | Settings | File Templates.
 */
public class GameManager extends Thread
{
    /**Наша скорость в мс = 10*/
    static final long FPS = 10;

    /**Объект класса GameView*/
    private GameView view;

    /**Задаем состояние потока*/
    private boolean running = false;

    /**Конструктор класса*/
    public GameManager(GameView view)
    {
        this.view = view;
    }

    /**Задание состояния потока*/
    public void setRunning(boolean run)
    {
        running = run;
    }

    /** Действия, выполняемые в потоке */

    @Override
    public void run()
    {
        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;
        while (running) {
            Canvas c = null;
            startTime = System.currentTimeMillis();
            try {
                c = view.getHolder().lockCanvas();
                synchronized (view.getHolder()) {
                    view.onDraw(c);
                }
            } finally {
                if (c != null) {
                    view.getHolder().unlockCanvasAndPost(c);
                }
            }
            sleepTime = ticksPS-(System.currentTimeMillis() - startTime);
            try {
                if (sleepTime > 0)
                    sleep(sleepTime);
                else
                    sleep(10);
            } catch (Exception e) {}
        }
    }
}
