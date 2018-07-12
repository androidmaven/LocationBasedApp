package com.androidmaven.aquickapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.androidmaven.aquickapp.evaluate.EvaluateActivity;
import com.gauravbhola.ripplepulsebackground.RipplePulseLayout;

import im.delight.android.location.SimpleLocation;

public class FindMyLocationActivity extends AppCompatActivity implements View.OnClickListener {
    SimpleLocation location;
    RipplePulseLayout rp,rp2;
    double lat1, lat2, long1, long2;
    Button evaluate;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_location_layout);
        location = new SimpleLocation(this);
        rp = findViewById(R.id.layout_ripplepulse);
        rp2 = findViewById(R.id.layout_ripplepulse2);
        evaluate = findViewById(R.id.evaluate);
        evaluate.setOnClickListener(this);
        evaluate.setClickable(false);

        findViewById(R.id.backFrom).setOnClickListener(this);
        rp.setOnClickListener(this);
        rp2.setOnClickListener(this);


    }
    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backFrom:
                finish();
                break;
            case R.id.layout_ripplepulse:
                // if we can't access the location yet

                rp.startRippleAnimation();
                if (!location.hasLocationEnabled()) {
                    // ask the user to enable location access
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Kindly turn on your location at your settings");
                    builder.setTitle("Location settings");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Open settings", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            SimpleLocation.openSettings(FindMyLocationActivity.this);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });
                    builder.show();

                }
                else findLocation1();
                break;
            case R.id.layout_ripplepulse2:
                rp2.startRippleAnimation();
                if (!location.hasLocationEnabled()) {
                    // ask the user to enable location access
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Kindly turn on your location at your settings");
                    builder.setTitle("Location settings");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Open settings", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            SimpleLocation.openSettings(FindMyLocationActivity.this);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });

                }
                else findLocation2();
                break;
                case R.id.evaluate:
            Intent intent = new Intent(FindMyLocationActivity.this, EvaluateActivity.class);
            intent.putExtra("lat1", lat1);
            intent.putExtra("lat2", lat2);
            intent.putExtra("long1", long1);
            intent.putExtra("long2", long2);
            startActivity(intent);
            break;

        }
    }

    void findLocation1() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getAddress1();
                    }
                }, 1400);

            }
            else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        440);
            }
        }
    }
    void findLocation2() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getAddress2();
                    }
                }, 1400);

            }
            else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        440);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 440: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        //locationManager.requestLocationUpdates(provider, 400, 1, this);
                        getAddress1();
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }
    void getAddress1() {
        final double latitude = location.getLatitude();
        final double longitude = location.getLongitude();
        lat1 = latitude;
        long1 = longitude;

        rp.stopRippleAnimation();
        TextView textView = findViewById(R.id.pointA);
        textView.setVisibility(View.VISIBLE);

        if(latitude ==0.00 && longitude == 00) {
            textView.setText("Reload");
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    findLocation1();
                }
            });
        }
        else {
            textView.setText("Point A: \n Lat: "+latitude +"and Long: "+longitude);
            findViewById(R.id.pointB).setVisibility(View.VISIBLE);
            findViewById(R.id.layout_ripplepulse2).setVisibility(View.VISIBLE);
        }


    }
    void getAddress2() {
        final double latitude = location.getLatitude();
        final double longitude = location.getLongitude();
        lat2 = latitude;
        long2 = longitude;
        TextView textView = findViewById(R.id.pointB);
        textView.setVisibility(View.VISIBLE);
        rp2.stopRippleAnimation();

        if(latitude ==0.00 && longitude == 00) {
            textView.setText("Reload");
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    findLocation2();
                }
            });
        }
        else {
            textView.setText("Point B: \n Lat: "+latitude +" and Long: "+longitude);
            TextView evaluate = findViewById(R.id.evaluate);
            evaluate.setActivated(true);
            evaluate.setClickable(true);
        }


    }

}
