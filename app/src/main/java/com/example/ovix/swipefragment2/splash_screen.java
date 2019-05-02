package com.example.ovix.swipefragment2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.os.Handler;

/**
 * Created by ovix on 2/4/19.
 */

public class splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent homeIntent = new Intent(splash_screen.this,MainActivity.class);
                startActivity(homeIntent);
            }
        },2000);
    }

}
