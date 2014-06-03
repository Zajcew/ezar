package com.ezar.xBikeChallenge;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Context;

public class RideActivity extends Activity implements LocationListener {
    /**
     * Called when the activity is first created.
     */
    TextView czas;
    TextView locationView;
    Handler handler;
    LocationManager locationManager;
    Button startBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ride_layout);
        czas = (TextView) findViewById(R.id.timerView);
        locationView = (TextView) findViewById(R.id.locationView);
        czas.setText("5:00");

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //sprawdzenie czy wlaczono gps
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            showSettingsAlert();

        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, this);

        }


        handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                czas.setText(msg.obj.toString());

            }
        };


    }


    public void onStartButton(View view) {
        final long startMilis = System.currentTimeMillis();

        new Thread(new Runnable() {
            public void run() {
                long currentMilis;
                int sec;
                int min;
                try {
                    while (true) {
                        currentMilis = System.currentTimeMillis();
                        sec = (int) (currentMilis - startMilis) / 1000;
                        min = sec / 60;
                        sec = sec % 60;
                        Message msg = new Message();

                        msg.obj = String.format("%d:%02d", min, sec);

                        handler.sendMessage(msg);
                        Thread.sleep(100);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    @Override
    public void onLocationChanged(Location location) {
        String str = "Latitude: " + location.getLatitude() + "Longitude: " + location.getLongitude();
        locationView.setText(str);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        //Toast.makeText(getApplicationContext(), "GPS signal recived", Toast.LENGTH_LONG).show();
        startBtn.setEnabled(true);
    }

    @Override
    public void onProviderDisabled(String provider) {
        //Toast.makeText(getApplicationContext(), "GPS turned off", Toast.LENGTH_LONG).show();
        startBtn.setEnabled(false);
    }

    public void showSettingsAlert() {
        new AlertDialog.Builder(this)
                .setTitle("Turn on gps")
                .setMessage(getString(R.string.wouldGps))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();


    }
}