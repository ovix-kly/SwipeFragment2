package com.example.ovix.swipefragment2;

/**
 * Created by ovix on 10/10/17.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CustomPageAdapter extends FragmentPagerAdapter {


    protected Context mContext;


    public CustomPageAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {


        Fragment walking = new fragment_walking();
        Fragment running = new fragment_running();
        Fragment cycling = new fragment_cycling();
        Fragment varFrag = walking;


        switch (position) {
            case 0:
                varFrag = walking;
                break;
            case 1:
                varFrag = running;
                break;
            case 2:
                varFrag = cycling;
                break;

        }

        return varFrag;

    }


    @Override
    public int getCount() {
        return 3;
    }
}