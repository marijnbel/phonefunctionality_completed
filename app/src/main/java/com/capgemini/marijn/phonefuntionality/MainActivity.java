package com.capgemini.marijn.phonefuntionality;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.IbtnGotoTorch)
    public void gotoTorch() {
        if( this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
            Intent gotoTorchActivity = new Intent(MainActivity.this, TorchActivity.class);
            startActivity(gotoTorchActivity);
        } else {
            Toast.makeText(MainActivity.this, getString(R.string.torch_availabe),
                    Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick(R.id.IbtnGotoGps)
    public void gotoGPS() {
        Intent gotoTorchActivity = new Intent(MainActivity.this, GpsActivity.class);
        startActivity(gotoTorchActivity);

    }


}
