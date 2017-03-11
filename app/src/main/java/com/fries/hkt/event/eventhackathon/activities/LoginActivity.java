package com.fries.hkt.event.eventhackathon.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.fries.hkt.event.eventhackathon.R;
import com.fries.hkt.event.eventhackathon.models.IUser;
import com.fries.hkt.event.eventhackathon.utils.SharedPreferencesMgr;

/**
 * Created by tmq on 10/03/2017.
 */

public class LoginActivity extends AppCompatActivity {

    public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE= 5469;

    private static final String TAG = LoginActivity.class.getSimpleName();

    SharedPreferencesMgr sharedPreferencesMgr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        checkPermission();
    }

    private void initializeView() {
    }

    private void initViews(){
        findViewById(R.id.btn_login_facebook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginWithFaceBook(v);
            }
        });
    }

    private void loginWithFaceBook(View v){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.txt_please_wait));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();


        v.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this, R.string.txt_login_success, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                sharedPreferencesMgr.setLogin(true);
                sharedPreferencesMgr.setUserInfo(new IUser("", "minhquylt95@gmail.com", "Quy dz"));
                directToCheckIn();
            }
        }, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
            if (!Settings.canDrawOverlays(this)) {
                // You don't have permission
                checkPermission();

            }
            else
            {
                //do as per your logic
            }

        }
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

    private void directToCheckIn(){
        Intent intent = new Intent(LoginActivity.this, CheckInActivity.class);
        startActivity(intent);

        LoginActivity.this.finish();
    }
}
