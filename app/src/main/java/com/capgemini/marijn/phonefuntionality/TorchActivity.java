package com.capgemini.marijn.phonefuntionality;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TorchActivity extends AppCompatActivity {

    @BindView(R.id.IbtnToggleFlashlight)
    ImageButton IbtnToggleFlashlight;

    private boolean isEnabledFlashlight = false;
    private static final int CAMERA_REQUEST = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_torch);
        ButterKnife.bind(this);
        if(Build.VERSION.SDK_INT>22) {
            if ((ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
                // user permission not granted
                // ask permission
                requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
            } else {
                // user already provided permission
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        flashLight(false);
    }

    @OnClick(R.id.IbtnToggleFlashlight)
    public void toggleFlashlight(){
        flashLight(!isEnabledFlashlight);
    }

    private void flashLight(boolean activate) {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, activate);
            isEnabledFlashlight = activate;
            if(isEnabledFlashlight){
                IbtnToggleFlashlight.setImageResource(R.drawable.torchon);
            } else {
                IbtnToggleFlashlight.setImageResource(R.drawable.torchoff);
            }

        } catch (CameraAccessException e) {
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Do nothing if permission is granted
                } else {
                    //User denied
                    if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                        Toast.makeText(this,getString(R.string.permission_denied_needed_torch),Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    //User denied and pressed never ask again
                    else {
                        //Show toast that we can't run this activity without permission and finish it
                        Toast.makeText(this,getString(R.string.permission_denied_required_torch),Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        }
    }

}
