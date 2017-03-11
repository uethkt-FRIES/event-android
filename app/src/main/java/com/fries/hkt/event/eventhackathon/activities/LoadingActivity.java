package com.fries.hkt.event.eventhackathon.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fries.hkt.event.eventhackathon.R;
import com.fries.hkt.event.eventhackathon.utils.SharedPreferencesMgr;

public class LoadingActivity extends AppCompatActivity {

    public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE= 5469;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        checkPermission();
        Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(2000);
                } catch (Exception e) {

                } finally {
                    SharedPreferencesMgr sharedPreferencesMgr = new SharedPreferencesMgr(LoadingActivity.this);
                    if (sharedPreferencesMgr.isLoggedIn()) {
                        if (sharedPreferencesMgr.getEventId().isEmpty()) {
                            Intent i = new Intent(LoadingActivity.this,
                                    CheckInActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            Intent i = new Intent(LoadingActivity.this,
                                    MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                    } else {
                        Intent i = new Intent(LoadingActivity.this,
                                LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
            }
        };
        welcomeThread.start();
    }


    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
            if (!Settings.canDrawOverlays(this)) {
                // You don't have permission
                checkPermission();
            } else {
                //do as per your logic
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
