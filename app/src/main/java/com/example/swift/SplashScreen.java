package com.example.swift;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CAMERA = 0;
    private boolean cameraPermissionGranted = false;
    private boolean appSettingsOpened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_splash_screen);

        // Check if camera permission is granted
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            cameraPermissionGranted = true;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreen.this, MainMenu.class);
                    startActivity(intent);
                }
            }, 2000);

        } else {
            // Request camera permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    cameraPermissionGranted = true;
                } else {

                    // Camera permission not granted
                    showPermissionDeniedDialog();
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        boolean isCameraPermissionGranted = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;

        if (!isCameraPermissionGranted) {
            showPermissionDeniedDialog();
            //Toast.makeText(this, "Grant camera permissions to use the app.", Toast.LENGTH_LONG).show();
            //finish();
        }else{
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreen.this, MainMenu.class);
                    startActivity(intent);
                }
            }, 2000);
        }
        /*Log.wtf("Value ng opened", String.valueOf(appSettingsOpened));
        boolean isCameraPermissionGranted = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;

        if(appSettingsOpened){
            if (!isCameraPermissionGranted) {
                Toast.makeText(this, "Grant camera permissions to use the app.", Toast.LENGTH_LONG).show();
                finish();
            }
        }else{
            Intent intent = new Intent(SplashScreen.this, MainMenu.class);
            finish();
            startActivity(intent);
        }*/
    }


    private void showPermissionDeniedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Camera and storage permissions are required to use this app.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // Open app settings to allow the user to grant permissions manually
                        openAppSettings();
                        boolean isCameraPermissionGranted = ActivityCompat.checkSelfPermission(SplashScreen.this,
                                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
                        boolean isStoragePermissionGranted = ActivityCompat.checkSelfPermission(SplashScreen.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

                        if (!isCameraPermissionGranted || !isStoragePermissionGranted) {
                            // Permission not granted, show a message to the user and close the app if the app settings were not opened
                            if (!appSettingsOpened) {
                                Toast.makeText(SplashScreen.this, "Please grant camera and storage permissions to use the app.", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Toast.makeText(SplashScreen.this,
                                "Permissions denied, app cannot function properly.",
                                Toast.LENGTH_SHORT).show();
                        // Close the app
                        finish();
                    }
                })
                .create().show();
    }


    private void openAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", getPackageName(), null));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        appSettingsOpened = true;

        startActivity(intent);

    }

}