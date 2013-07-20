package com.example.Thoth;

import android.graphics.*;

public class Sprite {
    /**Объект класса GameView*/
    private GameView gameView;

    /**Картинка*/
    private Bitmap bmp;

    private int margin = 5;

    /**Позиция по Х=0*/
    private int x = 5;

    /**Позиция по У=0*/
    private int y = 0;

    /**Скорость по Х=5*/
    private int xSpeed = 5;

    private int ySpeed = 5;

    /**Текущий кадр = 0*/
    private int currentFrame = 0;

    /**Ширина*/
    private int width;

    /**Ввыоста*/
    private int height;

    /**Рядков в спрайте = 4*/
    private static final int BMP_ROWS = 4;
    /**Колонок в спрайте = 3*/
    private static final int BMP_COLUMNS = 3;

    // direction = 0 up, 1 left, 2 down, 3 right,
    // animation = 3 up, 1 left, 0 down, 2 right
    int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };
    private int getAnimationRow() {
        double dirDouble = (Math.atan2(xSpeed, ySpeed) / (Math.PI / 2) + 2);
        int direction = (int) Math.round(dirDouble) % BMP_ROWS;
        return DIRECTION_TO_ANIMATION_MAP[direction];
    }

    private String cardNumber;

    /**Конструктор*/
    public Sprite(GameView gameView, Bitmap bmp, String card)
    {
        this.gameView = gameView;
        this.bmp = bmp;
        this.width = gameView.screenWidth / 9 - margin;
        this.height = this.width * bmp.getHeight() / bmp.getWidth();
        this.cardNumber = card;
    }

    public void SetPosition(int i) {
        int centerX = gameView.screenWidth / 2 - width / 2;
        int centerY = gameView.screenHeight / 2 - height / 2;
        switch(i) {
            case 0:
                x = centerX;
                y = centerY;
                break;
            case 1:
                x = centerX - (width + margin) * 2;
                y = centerY;
                break;
            case 2:
                x = centerX + (width + margin) * 2;
                y = centerY;
                break;
            case 3:
                x = centerX + (width + margin);
                y = centerY - (height + margin) / 2;
                break;
            case 4:
                x = centerX - (width + margin);
                y = centerY + (height + margin) / 2;
                break;
            case 5:
                x = centerX - (width + margin);
                y = centerY - (height + margin) / 2;
                break;
            case 6:
                x = centerX + (width + margin);
                y = centerY + (height + margin) / 2;
                break;
            case 7:
                x = centerX + (width + margin) * 3;
                y = centerY - (height + margin) / 2;
                break;
            case 8:
                x = centerX - (width + margin) * 3;
                y = centerY - (height + margin) / 2;
                break;
            case 9:
                x = centerX - (width + margin) * 3;
                y = centerY + (height + margin) / 2;
                break;
            case 10:
                x = centerX + (width + margin) * 3;
                y = centerY + (height + margin) / 2;
                break;
            case 11:
                x = centerX + (width + margin) * 4;
                y = centerY - (height + margin) / 2;
                break;
            case 12:
                x = centerX - (width + margin) * 4;
                y = centerY - (height + margin) / 2;
                break;
            case 13:
                x = centerX - (width + margin) * 4;
                y = centerY + (height + margin) / 2;
                break;
            case 14:
                x = centerX + (width + margin) * 4;
                y = centerY + (height + margin) / 2;
                break;
            default:
                x = centerX;
                y = centerY;
                break;
        }
    }

    /**Перемещение объекта, его направление*/
    private void update()
    {
//        if (x > gameView.getWidth() - bmp.getWidth() - xSpeed) {
//            xSpeed = -5;
//        }
//        if (x + xSpeed< 0) {
//            xSpeed = 5;
//        }
//        x = x + xSpeed;
    }

    /**Рисуем наши спрайты*/
    public void onDraw(Canvas canvas)
    {
        update();
        int srcX = 0;
        int srcY = 0;
        Rect src = new Rect(srcX, srcY, srcX + bmp.getWidth(), srcY + bmp.getHeight());
        Rect dst = new Rect(x, y, x + width, y + height);

        canvas.drawBitmap(bmp, src, dst, null);

        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        canvas.drawText(cardNumber, x, y, paint);
    }

    /**Проверка на столкновения*/
    public boolean isCollision(float x2, float y2) {
        return x2 > x && x2 < x + width && y2 > y && y2 < y + height;
    }
}