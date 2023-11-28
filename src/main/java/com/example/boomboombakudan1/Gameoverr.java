package com.example.boomboombakudan1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Gameoverr extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameoverr);


    }
    public void retry(){
        Intent i = new Intent(this, GameView.class);
        startActivity(i);
    }
}