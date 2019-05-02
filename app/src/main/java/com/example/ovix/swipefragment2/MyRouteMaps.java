package com.example.ovix.swipefragment2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by ovix on 11/27/17.
 */

public class MyRouteMaps extends FragmentActivity implements OnMapReadyCallback {

        private GoogleMap mMap;
        PolylineOptions pOption = new PolylineOptions();
        ArrayList<LatLng> coordList = new ArrayList<LatLng>();
        LatLng myPoints;
        int Menit,Detik;
        TextView txtMenit,txtDetik,txtJumlahCalorie,txtTime,txtJarak,txtSpeed;
        Double totalDistance,kecepatan,kalori;
        DecimalFormat numberFormat = new DecimalFormat("0.000");
        String strLat,strLong;
        ImageView btnCancel;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.location_fragment);
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager()
                    .findFragmentById(R.id.mapView);
            mapFragment.getMapAsync(this);

            Typeface fontBigNumber = Typeface.createFromAsset(getAssets(),"fonts/Athletic.TTF");
            final Typeface fontGothamBook = Typeface.createFromAsset(getAssets(),"fonts/Gotham_Book.ttf");
            final Typeface fontGothamBold = Typeface.createFromAsset(getAssets(),"fonts/Gotham_Bold.ttf");

            txtJumlahCalorie = (TextView)findViewById(R.id.txtJumlahCalorie);
            txtTime = (TextView)findViewById(R.id.txtTime);
            txtJarak = (TextView)findViewById(R.id.txtJarak);
            txtSpeed = (TextView)findViewById(R.id.txtSpeed);

            txtJumlahCalorie.setTypeface(fontBigNumber);
            txtTime.setTypeface(fontGothamBold);
            txtJarak.setTypeface(fontGothamBold);
            txtSpeed.setTypeface(fontGothamBold);

            btnCancel = (ImageView)findViewById(R.id.bttnCancel);

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent gotoHome = new Intent(MyRouteMaps.this, MainActivity.class);
                    startActivity(gotoHome);
                }
            });



        }


        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera. In this case,
         * we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to install
         * it inside the SupportMapFragment. This method will only be triggered once the user has
         * installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;

            coordList = getIntent().getParcelableArrayListExtra("coordlist");
            Menit = getIntent().getIntExtra("minute",0);
            Detik = getIntent().getIntExtra("second",0);
            totalDistance = getIntent().getDoubleExtra("jarak",0);
            kecepatan = getIntent().getDoubleExtra("speed",0);
            kalori = getIntent().getDoubleExtra("kalori",0);
            //strLat = getIntent().getStringExtra("strLat");
            //strLong = getIntent().getStringExtra("strLong");





            txtSpeed.setText(String.valueOf(Math.round(kecepatan))+"Km/h");

            txtJarak.setText(String.valueOf(numberFormat.format(totalDistance/1000)));

            txtJumlahCalorie.setText(String.valueOf(numberFormat.format(kalori)));

            //txtMenit = (TextView)findViewById(R.id.menit);
            //txtDetik = (TextView)findViewById(R.id.detik);

            txtTime.setText(String.format("%02d", Menit)+":"
                    + String.format("%02d", Detik)+"hr");
            //txtDetik.setText(String.valueOf(Detik));

            if(coordList.size() <= 1){
                //Toast.makeText(MyRouteMaps.this, "ANDA BELUM BERAKTIFITAS", Toast.LENGTH_SHORT).show();
                drawMap();
            }else{
                drawMap();
            }

            Log.d("ISI DALAM COORDLIST MAP : ",String.valueOf(coordList.size()));


            if(totalDistance < 500) {
                mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
            }else{
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15.5f));
            }

            // Add a marker in Sydney and move the camera
            //LatLng sydney = new LatLng(-34, 151);

        }

    public void drawMap(){


        pOption.width(5);
        pOption.color(Color.BLUE);
        pOption.geodesic(true);
        pOption.addAll(coordList);



        for (int i = 0; i < coordList.size(); i++) {

            //Log.d("Size of CoordList : ",String.valueOf(coordList.size()));

            myPoints = coordList.get(i);



            mMap.moveCamera(CameraUpdateFactory.newLatLng(myPoints));
            mMap.addPolyline(pOption);

        }

        mMap.addMarker(new MarkerOptions().position(coordList.get(0)).title("START"));
        mMap.addMarker(new MarkerOptions().position(myPoints).title("FINISH"));


    }

}
