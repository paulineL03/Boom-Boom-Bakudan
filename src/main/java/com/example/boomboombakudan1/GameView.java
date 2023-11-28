package com.example.boomboombakudan1;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends View {

    /*
            android:hardwareAccelerated="false"
        android:largeHeap="true"
     */

    Bitmap background, ground, cry_shogun;
    Rect rectBackground, rectGround;
    Context context;
    Handler handler;
    final long UPDATE_MILLIS = 30;
    Paint textPaint = new Paint();
    Paint healthPaint = new Paint();
    float TEXT_SIZE = 120;
    int points = 0;
    int life = 3;
    static int dWidth, dHeight;
    Random random;
    float cry_shogunX, cry_shogunY;
    float oldX;
    float oldCry_ShogunX;
    ArrayList<Bomb> bombs;
    ArrayList<Explosion> explosions;
    Runnable runnable;

    public GameView(Context context) {
        super(context);
        this.context = context;
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        ground = BitmapFactory.decodeResource(getResources(), R.drawable.ground);
        cry_shogun = BitmapFactory.decodeResource(getResources(), R.drawable.cry_shogun);
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;
        rectBackground = new Rect(0, 0, dWidth, dHeight);
        rectGround = new Rect(0, dHeight - ground.getHeight(), dWidth, dHeight);
        handler = new Handler();
	    runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
     	   }
  	    };
        textPaint.setColor(Color.rgb(255, 165, 0));
        textPaint.setTextSize(TEXT_SIZE);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTypeface(ResourcesCompat.getFont(context, R.font.kenney_blocks));
        healthPaint.setColor(Color.GREEN);
        random = new Random();
        cry_shogunX = dWidth / 2 - cry_shogun.getWidth() / 2;
        cry_shogunY = dHeight  - ground.getHeight() - cry_shogun.getHeight();
        bombs = new ArrayList<>();
        explosions = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Bomb bomb = new Bomb(context);
            bombs.add(bomb);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(background, null, rectBackground, null);
        canvas.drawBitmap(ground, null, rectGround, null);
        canvas.drawBitmap(cry_shogun, cry_shogunX, cry_shogunY, null);
        for (int i = 0; i < bombs.size(); i++) {
            canvas.drawBitmap(bombs.get(i).getBomb(bombs.get(i).bombFrame), bombs.get(i).bombX, bombs.get(i).bombY, null);
            bombs.get(i).bombFrame++;
            if (bombs.get(i).bombFrame > 2) {
                bombs.get(i).bombFrame = 0;
            }
            bombs.get(i).bombY += bombs.get(i).bombVelocity;
            if (bombs.get(i).bombY + bombs.get(i).getBombsHeight() >= dHeight - ground.getHeight()) {
                points += 10;
                Explosion explosion = new Explosion(context);
                explosion.explosionX = bombs.get(i).bombX;
                explosion.explosionY = bombs.get(i).bombY;
                explosions.add(explosion);
                bombs.get(i).resetPosition();
            }
        }
        for (int i = 0; i < bombs.size(); i++) {
            if (bombs.get(i).bombX + bombs.get(i).getBombsWidth() >= cry_shogunX
                    && bombs.get(i).bombX <= cry_shogunX + cry_shogun.getWidth()
                    && bombs.get(i).bombY + bombs.get(i).getBombsWidth() >= cry_shogunY
                    && bombs.get(i).bombY + bombs.get(i).getBombsWidth() <= cry_shogunY + cry_shogun.getHeight()) {
                         life--;
                         bombs.get(i).resetPosition();
                         if (life == 0) {
                         Intent intent = new Intent(context, GameOver.class);
                         intent.putExtra("points", points);
                         context.startActivity(intent);
                        ((Activity) context).finish();
                }
            }
        }

        for (int i = 0; i < explosions.size(); i++) {
            canvas.drawBitmap(explosions.get(i).getExplosion(explosions.get(i).explosionFrame), explosions.get(i).explosionX,
                    explosions.get(i).explosionY, null);
            explosions.get(i).explosionFrame++;
            if (explosions.get(i).explosionFrame > 3) {
                explosions.remove(i);
            }
        }

        if (life == 2){
            healthPaint.setColor(Color.YELLOW);
        } else if(life == 1){
            healthPaint.setColor(Color.RED);
        }
        //top 30, bottom 80  dwidth-200   dwidth-200+60*life
        canvas.drawRect(dWidth-100, 0, dWidth-100+50*life, 0, healthPaint);
        canvas.drawText("" + points, 20, TEXT_SIZE, textPaint);
        handler.postDelayed(runnable, UPDATE_MILLIS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        if (touchY >= cry_shogunY){
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN){
                oldX = event.getX();
                oldCry_ShogunX = cry_shogunX;
            }
            if (action == MotionEvent.ACTION_MOVE){
                float shift = oldX - touchX;
                float newCryShogunX = oldCry_ShogunX -  shift;
                if (newCryShogunX <= 0)
                    cry_shogunX = 0;
                else if (newCryShogunX >= dWidth - cry_shogun.getWidth())
                    cry_shogunX = dWidth - cry_shogun.getWidth();
                else
                    cry_shogunX = newCryShogunX;
            }
        }
        return true;
    }
}