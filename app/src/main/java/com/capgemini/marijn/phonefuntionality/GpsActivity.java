package com.capgemini.marijn.phonefuntionality;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GpsActivity extends AppCompatActivity {

    private static final int SETTINGS_GPS = 1;

    private LocationManager locationManager;
    private LocationListener locationListener;

    @BindView(R.id.txlatitude) TextView txlatitude;
    @BindView(R.id.txlongitude) TextView txlongitude;
    @BindView(R.id.btnEnableGps) Button btnEnableGps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        ButterKnife.bind(this);

        initLocationManager();
    }

    // Acquire a reference to the system Location Manager
    private void initLocationManager(){
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                txlatitude.setText(getString(R.string.Latitude, String.valueOf(location.getLatitude())));
                txlongitude.setText(getString(R.string.Latitude, String.valueOf(location.getLongitude())));
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                Toast.makeText(GpsActivity.this,"onStatusChanged",Toast.LENGTH_SHORT).show();
            }

            public void onProviderEnabled(String provider) {
                Toast.makeText(GpsActivity.this,"onProviderEnabled",Toast.LENGTH_SHORT).show();
            }

            public void onProviderDisabled(String provider) {
                Toast.makeText(GpsActivity.this,"onProviderDisabled",Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    protected void onStop() {
        super.onStop();
        //stop tracking the location of the user
        locationManager.removeUpdates(locationListener);
    }

    @OnClick(R.id.btnEnableGps)
    public void enableLocationTracking(){
        if(Build.VERSION.SDK_INT>22) {
            if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
                // user permission not granted
                // ask permission
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, SETTINGS_GPS);
            } else {
                // user already provided permission
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                btnEnableGps.setVisibility(View.GONE);
            }
        } else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            btnEnableGps.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case SETTINGS_GPS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Enable location tracking
                    enableLocationTracking();
                } else {
                    //User denied
                    if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        Toast.makeText(this,getString(R.string.permission_denied_needed_gps),Toast.LENGTH_SHORT).show();
                    }
                    //User denied and pressed never ask again
                    else {
                        //Show toast that we can't run this activity without permission and finish it
                        Toast.makeText(this,getString(R.string.permission_denied_required_gps),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

}