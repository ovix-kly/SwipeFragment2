package com.example.ovix.swipefragment2;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

import static android.R.attr.defaultValue;

/**
 * Created by ovix on 10/11/17.
 */

public class Walking extends AppCompatActivity {

    TextView timer,millisecond,txtBttnStart,txtStateUser,CalIndicator,kecepatanSementara;
    //Button pause, reset, lap;
    ImageView start,bttnMap;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    Handler handler;
    int Seconds, Minutes, MilliSeconds ;
    //ListView listView ;
    String[] ListElements = new String[] {  };
    ArrayList<String> ListElementsArrayList,coordToStringLat  = new ArrayList<String>();
    ArrayList<String> coordToStringLong = new ArrayList<String>();
    ArrayAdapter<String> adapter ;
    double OldLatitude,OldLongitude,NewLatitude,NewLongitude;
    LatLng coordinat,NewCoordinat,OldCoordinat;
    ArrayList<LatLng> coordList = new ArrayList<LatLng>();

    double totalWaktu,pencacahJarak = 1000.00,NilaiKalori,Hours;
    double kecepatan,totalDistance = 0.0;
    double pencacahWaktu = 3600.00,pengkaliDetik = 60.0;
    double BodyWeight = 58.0,TinggiBadan = 169.0;
    DecimalFormat numberFormat = new DecimalFormat("0.000");

    private static GoogleApiClient mGoogleApiClient;
    private static LocationRequest locationRequest;
    final static int REQUEST_CHECK_SETTINGS = 0x1;
    private static final int ACCESS_FINE_LOCATION_INTENT_ID = 3;
    private static final String BROADCAST_ACTION = "android.location.PROVIDERS_CHANGED";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.walking_page);

        Typeface fontBigNumber = Typeface.createFromAsset(getAssets(),"fonts/Athletic.TTF");
        final Typeface fontGothamBook = Typeface.createFromAsset(getAssets(),"fonts/Gotham_Book.ttf");
        final Typeface fontGothamBold = Typeface.createFromAsset(getAssets(),"fonts/Gotham_Bold.ttf");

        timer = (TextView)findViewById(R.id.timer);
        millisecond = (TextView)findViewById(R.id.millisecond);
        start = (ImageView)findViewById(R.id.bttnStart);
        txtBttnStart = (TextView)findViewById(R.id.txtBttnStart);
        txtStateUser = (TextView)findViewById(R.id.textView2);
        CalIndicator = (TextView)findViewById(R.id.CalIndicator);
        bttnMap = (ImageView)findViewById(R.id.bttnMap);
        kecepatanSementara = (TextView)findViewById(R.id.textView6);
        //pause = (Button)findViewById(R.id.bttnPause);
        //reset = (Button)findViewById(R.id.bttnReset);
        //lap = (Button)findViewById(R.id.bttnLap);
        //listView = (ListView)findViewById(R.id.listview1);

        timer.setTypeface(fontBigNumber);
        millisecond.setTypeface(fontBigNumber);
        txtBttnStart.setTypeface(fontGothamBold);
        txtBttnStart.setText("START");
        txtStateUser.setText("Selamat Pagi, Andi \ningin memulai aktivitas?");
        CalIndicator.setTextColor(Color.parseColor("#FFD4D4D4"));
        CalIndicator.setTypeface(fontGothamBold);


        handler = new Handler();

        ListElementsArrayList = new ArrayList<String>(Arrays.asList(ListElements));

        adapter = new ArrayAdapter<String>(Walking.this,android.R.layout.simple_list_item_1,ListElements);

        //LocalBroadcastManager.getInstance(Walking.this).registerReceiver(mMessageReceiver, new IntentFilter("take-latlng"));

        //listView.setAdapter(adapter);

        initGoogleApiClient();
        showSettingsDialog();


        LocalBroadcastManager.getInstance(Walking.this).registerReceiver(mMessageReceiver, new IntentFilter("take-latlng"));

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        }else {


            //Log.d("Isi Dari stateBttn ",stateBttn);


            start.setOnClickListener(new View.OnClickListener() {

                //String stateBttn = String.valueOf(txtBttnStart.getText());

                                         @Override
                                         public void onClick(View view) {

                                             String stateBttn = String.valueOf(txtBttnStart.getText());

                                             if (stateBttn.equals("START")) {

                                                 StartTime = SystemClock.uptimeMillis();
                                                 handler.postDelayed(runnable, 0);


                                                 txtBttnStart.setText("STOP");
                                                 txtStateUser.setText("Hi Andi, semoga latihanmu \nhari ini menyenangkan");

                                                 Log.d("is START? ", "START");

                                                 CalIndicator.setTextColor(Color.parseColor("#FFD4D4D4"));
                                                 CalIndicator.setTypeface(fontGothamBold);


                                                 LocalBroadcastManager.getInstance(Walking.this).registerReceiver(mMessageReceiver, new IntentFilter("take-latlng"));

                                                 startService(new Intent(Walking.this, MyGps.class));

                                                 Walking.this.onResume();


                                             } else {
                                                 Log.d("is STOPED? ", "STOP");

                                                 TimeBuff += MillisecondTime;
                                                 handler.removeCallbacks(runnable);

                                                 txtBttnStart.setText("START");

                                                 CalIndicator.setTextColor(Color.parseColor("#FF35586D"));
                                                 CalIndicator.setTypeface(fontGothamBold);


                                                 String storingValueLat = TextUtils.join(",",coordToStringLat);
                                                 String storingValueLong = TextUtils.join(",",coordToStringLong);

                                                 Log.d("StoringValueLat ",storingValueLat);
                                                 Log.d("StoringValueLong",storingValueLong);

                                                 stopService(new Intent(Walking.this, MyGps.class));

                                                 Walking.this.hitungDetailsActivity();

                                             }

                                             //reset.setEnabled(false);
                                         }
                                     }

            );


            bttnMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent sendIntent = new Intent(Walking.this, MyRouteMaps.class);

                    //startActivity(new Intent(Walking.this, MyRouteMaps.class));

                    sendIntent.putParcelableArrayListExtra("coordlist",coordList);
                    sendIntent.putExtra("minute",Minutes);
                    sendIntent.putExtra("second",Seconds);
                    sendIntent.putExtra("jarak",totalDistance);
                    sendIntent.putExtra("speed",kecepatan);
                    sendIntent.putExtra("kalori",NilaiKalori);
                    sendIntent.putExtra("strLat",coordToStringLat);
                    sendIntent.putExtra("strLong",coordToStringLong);


                    startActivity(sendIntent);
                    //Walking.this.hitungKalori();

                }

            });




            /*start.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    TimeBuff += MillisecondTime;

                    handler.removeCallbacks(runnable);

                    //reset.setEnabled(true);
                }

            });*/


        /*pause.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                TimeBuff += MillisecondTime;

                handler.removeCallbacks(runnable);

                reset.setEnabled(true);
            }

        });

        reset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                MillisecondTime = 0L ;
                StartTime = 0L ;
                TimeBuff = 0L ;
                UpdateTime = 0L ;
                Seconds = 0 ;
                Minutes = 0 ;
                MilliSeconds = 0 ;

                textView.setText("00:00:00");

                ListElementsArrayList.clear();

                adapter.notifyDataSetChanged();
            }
        });


        lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ListElementsArrayList.add(textView.getText().toString());

                adapter.notifyDataSetChanged();

            }
        });*/

        }

    }


    public void hitungDetailsActivity(){

        Toast.makeText(Walking.this, "Eksekusi Method Hitung Kalorie", Toast.LENGTH_SHORT).show();

        totalWaktu = (Minutes*pengkaliDetik)+Seconds;

        Hours = Minutes/60.00;

        kecepatan = ((pencacahWaktu/totalWaktu)*totalDistance)/pencacahJarak;

        //Rumur menghitung kalori yg terbakar saat berjalan
        //(0,035 x berat badan dalam kilogram) + ((percepatan^2): tinggi badan)) x (0,029) x (berat badan dalam kilogram) = Kilo kalori terbakar per menit.

        NilaiKalori = (0.035 * BodyWeight) + (Math.pow(kecepatan,2.0)/TinggiBadan) * (0.029 * BodyWeight);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            double latitude = intent.getDoubleExtra("sendValLat",defaultValue);
            double longitude = intent.getDoubleExtra("sendValLng",defaultValue);

            if(NewLatitude == 0.0 && NewLongitude == 0.0){
                NewLatitude = latitude;
                NewLongitude = longitude;

                //coordToStringLat = new ArrayList<String>();
                Log.d("Isi NewLatitude Pertama : ",String.valueOf(NewLatitude));

                coordinat = new LatLng(NewLatitude, NewLongitude);
                coordList.add(coordinat);


                coordToStringLat.add(String.valueOf(NewLatitude));
                coordToStringLong.add(String.valueOf(NewLongitude));

            }else{
                OldLatitude = NewLatitude;
                OldLongitude = NewLongitude;
                NewLatitude = latitude;
                NewLongitude = longitude;

                coordinat = new LatLng(OldLatitude, OldLongitude);
                coordList.add(coordinat);

                coordToStringLat.add(String.valueOf(OldLatitude));
                coordToStringLong.add(String.valueOf(OldLongitude));
                //String storingValueLat = TextUtils.join(",",coordToStringLat);
            }





            if(NewCoordinat == null){
                NewCoordinat = coordinat;
            }else if(NewCoordinat != null){
                OldCoordinat = NewCoordinat;
                NewCoordinat = coordinat;
                Double distance = SphericalUtil.computeDistanceBetween(OldCoordinat, NewCoordinat);

                totalDistance += + distance;

                Log.d("JARAK_2_TITIK_ADALAH : ",String.valueOf(Math.round(distance)));

                Log.d("TOTAL_JARAK_TEMPUH_SEMENTARA : ",String.valueOf(Math.floor(totalDistance)));

            }



            //Toast.makeText(MapsActivity.this, "GET VALUE FROM SERVICE", Toast.LENGTH_SHORT).show();



            if(coordList.size()==1){
                //mMap.addMarker(new MarkerOptions().position(coordList.get(0)).title("Marker in Sydney"));
            }

            //Log.d("Size of CoordList : ",String.valueOf(coordList.size()));

            Log.d("IS NEW RECEIVED? : ","YES");

            Log.d("VAL_OLD_COORDINAT : ",String.valueOf(OldCoordinat));
            Log.d("Val_NEW_COORDINAT : ",String.valueOf(NewCoordinat));

            kecepatanSementara.setText(String.valueOf(numberFormat.format(totalDistance/1000)+" km"));


        }

    };

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime/1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            MilliSeconds = (int)(UpdateTime % 1000);

            timer.setText(String.format("%02d", Minutes)+":"
                    + String.format("%02d", Seconds));
            millisecond.setText(String.format("%02d", MilliSeconds));

            handler.postDelayed(this,0);

            //Log.d("Isi dari MillisecondTime : ", String.valueOf(Seconds));


        }
    };




    // START TO CHECK GPS ENABLED or DISABLED
    private void initGoogleApiClient(){
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                //.addConnectionCallbacks(this)
                .build();
        mGoogleApiClient.connect();
    }

    private void showSettingsDialog(){
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        final PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                final Status status = locationSettingsResult.getStatus();
                final LocationSettingsStates states = locationSettingsResult.getLocationSettingsStates();

                switch (status.getStatusCode()){
                    case LocationSettingsStatusCodes.SUCCESS:
                        Toast.makeText(getApplicationContext(), "GPS is Enabled in your device" , Toast.LENGTH_LONG).show();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try{
                            status.startResolutionForResult(Walking.this, REQUEST_CHECK_SETTINGS);
                        }catch(IntentSender.SendIntentException e){
                            e.printStackTrace();
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;

                }

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode){
                    case RESULT_OK:
                        Toast.makeText(getApplicationContext(), "GPS is Enabled in your device" , Toast.LENGTH_LONG).show();
                        break;
                    case RESULT_CANCELED:
                        Toast.makeText(getApplicationContext(), "GPS is Disables in your device" , Toast.LENGTH_LONG).show();
                        break;
                }
                break;
        }

    };

    @Override
    protected void onResume(){
        super.onResume();
        registerReceiver(gpsLocationReceiver, new IntentFilter(BROADCAST_ACTION));
    };

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(gpsLocationReceiver != null){
            unregisterReceiver(gpsLocationReceiver);
        }
    };


    private Runnable sendUpdateToUi = new Runnable() {
        @Override
        public void run() {
            showSettingsDialog();
        }
    };



    private BroadcastReceiver gpsLocationReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent){
            if(intent.getAction().matches(BROADCAST_ACTION)){
                LocationManager locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
                if(locationManager.isProviderEnabled(locationManager.GPS_PROVIDER)){
                    Toast.makeText(getApplicationContext(), "GPS is Enabled in your device" , Toast.LENGTH_LONG).show();
                }else{
                    new Handler().postDelayed(sendUpdateToUi,10);
                }
            }
        }

    };


    /* On Request permission method to check the permisison is granted or not for Marshmallow+ Devices  */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ACCESS_FINE_LOCATION_INTENT_ID: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //If permission granted show location dialog if APIClient is not null
                    if (mGoogleApiClient == null) {
                        initGoogleApiClient();
                        showSettingsDialog();
                    } else
                        showSettingsDialog();


                } else {
                    //updateGPSStatus("Location Permission denied.");
                    Toast.makeText(Walking.this, "Location Permission denied.", Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

   //END OF CHECK GPS ENABLED or DISABLED



}
