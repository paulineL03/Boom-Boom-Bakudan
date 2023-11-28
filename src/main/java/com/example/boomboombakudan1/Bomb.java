package com.example.boomboombakudan1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Bomb {
    Bitmap bomb[] = new Bitmap[3];
    int bombFrame = 0;
    int bombX, bombY, bombVelocity;
    Random random;

    public Bomb(Context context){
        bomb[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bomb1);
        bomb[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bomb1);
        bomb[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bomb1);
        random = new Random();
        resetPosition();
    }

    public Bitmap getBomb(int bombFrame){
        return bomb[bombFrame];
    }

    public int getBombsWidth(){
        return bomb[0].getWidth();
    }

    public int getBombsHeight(){
        return bomb[0].getHeight();
    }

    public void resetPosition() {
        bombX = random.nextInt(GameView.dWidth-getBombsWidth());
        bombY = -200 + random.nextInt(700) * -1;
        bombVelocity = 30 + random.nextInt(16);
    }

//
}
