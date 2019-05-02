package com.example.ovix.swipefragment2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    CustomPageAdapter mCustomPageAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView nameExcercise = (TextView)findViewById(R.id.bttn1);
        TextView nameApp = (TextView)findViewById(R.id.textView2);
        TextView hello = (TextView)findViewById(R.id.hello);


        Typeface fontGothamBold = Typeface.createFromAsset(getAssets(),"fonts/Gotham_Bold.ttf");
        Typeface fontGothamBook = Typeface.createFromAsset(getAssets(),"fonts/Gotham_Book.ttf");
        Typeface fontGoboldBold = Typeface.createFromAsset(getAssets(),"fonts/Gobold_Bold.ttf");

        nameExcercise.setTypeface(fontGothamBold);
        nameApp.setTypeface(fontGothamBold);
        hello.setTypeface(fontGoboldBold);





            mCustomPageAdapter = new CustomPageAdapter(getSupportFragmentManager(), this);

            //getPosisi.getItem();

            mViewPager = (ViewPager) findViewById(R.id.pager);
            mViewPager.setAdapter(mCustomPageAdapter);

            mViewPager.getCurrentItem();




    }

}
