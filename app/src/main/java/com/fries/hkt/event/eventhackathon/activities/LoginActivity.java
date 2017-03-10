package com.fries.hkt.event.eventhackathon.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.fries.hkt.event.eventhackathon.R;

/**
 * Created by tmq on 10/03/2017.
 */

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
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
                Intent intent = new Intent(LoginActivity.this, CheckInActivity.class);
                startActivity(intent);

                LoginActivity.this.finish();
            }
        }, 1000);
    }

}
