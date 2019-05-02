package com.example.ovix.swipefragment2;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;


@TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
public class DialogActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);


    }



}