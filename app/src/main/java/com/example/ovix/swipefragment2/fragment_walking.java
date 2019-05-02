package com.example.ovix.swipefragment2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by ovix on 10/10/17.
 */

public class fragment_walking extends Fragment {

    Boolean isStarted = false;
    Boolean isVisible = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SaveInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_walking, container, false);

        return rootView;

    }

    @Override
    public void onStart() {
        super.onStart();
        isStarted = true;
        if (isVisible && isStarted){
            TextView textView = (TextView) getActivity().findViewById(R.id.bttn1);
            textView.setText("WALKING");

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(getContext(), "WALKING BUTTON CLICKED" , Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getActivity(), Walking.class);
                    getActivity().startActivity(i);
                }
            });




        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isStarted && isVisible) {
            TextView textView = (TextView) getActivity().findViewById(R.id.bttn1);
            textView.setText("WALKING");

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "WALKING BUTTON CLICKED" , Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getActivity(), Walking.class);
                    getActivity().startActivity(i);
                }
            });

            //Toast.makeText(getContext(), "PAGE  1" , Toast.LENGTH_LONG).show();
        }
    }
}
