package com.example.ovix.swipefragment2;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;


public class MyGps extends Service {
    private static final String TAG = "MyGPS Service";
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 0;
    private static final float LOCATION_DISTANCE = 0;
    Location location;
    Double latitude, longitude;

    Context mContext;


    private class LocationListener implements android.location.LocationListener {
        Location mLastLocation;

        public LocationListener(String provider) {
            Log.e(TAG, "LocationListener " + mLastLocation);
            mLastLocation = new Location(provider);

        }

        @Override
        public void onLocationChanged(Location location) {
            Log.e(TAG, "onLocationChanged: " + location);
            mLastLocation.set(location);
            if (mLocationManager != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Log.d("New Latitude = ", String.valueOf(latitude));
                Log.d("New Longitude = ", String.valueOf(longitude));
                sendMessage(latitude, longitude);
            }
        }


        @Override
        public void onProviderDisabled(String provider) {
            Log.e(TAG, "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e(TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.e(TAG, "onStatusChanged: " + provider);
        }
    }

    LocationListener[] mLocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("SERVICE RUNNING?", "IT'S RUNNING");
        Toast.makeText(this, "The Service Started ", Toast.LENGTH_SHORT).show();
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");
        initializeLocationManager();
        /*try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[1]);

            if (mLocationManager != null) {
                Log.d("Status_locationManager Network Provider  ", "Not Null");
                location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    Log.d("Lat = ", String.valueOf(location.getLatitude()));
                    Log.d("Long = ", String.valueOf(location.getLongitude()));
                    sendMessage(latitude, longitude);
                }

            }

        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }*/

        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[0]);

            if (mLocationManager != null) {
                location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                location.getLatitude();
                location.getLongitude();
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                sendMessage(latitude, longitude);
                Log.d("Status_locationManager GPS Provider  ", "Not Null");
            }
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (java.lang.NullPointerException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();

        Toast.makeText(this, "The Service Stop ", Toast.LENGTH_SHORT).show();

        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listners, ignore", ex);
                }
            }
        }
    }

    private void sendMessage(double lat, double lng) {

        double sendValLat = lat;
        double sendValLng = lng;
        Intent intent = new Intent("take-latlng");
        // You can also include some extra data.
        intent.putExtra("sendValLat", sendValLat);
        intent.putExtra("sendValLng", sendValLng);

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

    }

    private void initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager");
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

            boolean enable = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            try {
                mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch (Exception e) {
               e.printStackTrace();

            }

            if(!enable){
                turnGPSOn();

            }




        }


    }


    public void turnGPSOn(){

        Intent dialogIntent = new Intent(this,Walking.class);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(dialogIntent);

        /*AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, R.style.AppFullScreenTheme);

        alertDialogBuilder
                .setMessage("GPS Not Enable, Do You Want to Turn On?")
                .setCancelable(false)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        Intent callGpsSettingIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                        getBaseContext().startActivity(callGpsSettingIntent);
                    }
                });
        alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        alertDialog.show();*/
    }





}


