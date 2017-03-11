package com.fries.hkt.event.eventhackathon.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fries.hkt.event.eventhackathon.R;
import com.fries.hkt.event.eventhackathon.utils.SharedPreferencesMgr;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
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
}
