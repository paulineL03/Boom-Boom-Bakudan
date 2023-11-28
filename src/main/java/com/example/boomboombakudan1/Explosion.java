package com.example.boomboombakudan1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Explosion {

    Bitmap explosion[] = new Bitmap[4];
    int explosionFrame = 0;
    int explosionX, explosionY;

    public Explosion(Context context){
        explosion[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.boom1);
        explosion[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.boom2);
        explosion[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.boom3);
        explosion[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.boom3);
    }

    public Bitmap getExplosion(int explosionFrame){
        return explosion[explosionFrame];
    }
}
