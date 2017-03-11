package com.fries.hkt.event.eventhackathon.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.fries.hkt.event.eventhackathon.R;
import com.fries.hkt.event.eventhackathon.models.IUser;
import com.fries.hkt.event.eventhackathon.utils.SharedPreferencesMgr;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by tmq on 10/03/2017.
 */

public class LoginActivity extends AppCompatActivity {

    public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE= 5469;

    private static final String TAG = LoginActivity.class.getSimpleName();

    private ProgressDialog progressDialog;

    SharedPreferencesMgr sharedPreferencesMgr;

    CallbackManager callbackManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferencesMgr = new SharedPreferencesMgr(this);
        initViews();
        checkPermission();
        setupLoginFacebook();
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
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.txt_please_wait));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
    }

    private void setupLoginFacebook() {
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        Log.v("LoginActivity", response.toString());
                                        // Application code
                                        try {
                                            String email = object.getString("email");
                                            String birthday = "33"; // 01/31/1980 format
                                            String name = object.getString("name");
                                            String id = object.getString("id");
                                            saveUserInformation(id, name, email, birthday);
                                            directToCheckIn();
                                            if(progressDialog != null){
                                                progressDialog.dismiss();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender,birthday");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        Log.d("onCancel", "onCancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.d("onError", exception.getMessage());
                    }
                });
    }

    private void saveUserInformation(String id, String name, String email, String birthDay){
                sharedPreferencesMgr.setLogin(true);
                sharedPreferencesMgr.setUserInfo(
                        new IUser(
                                String.format("https://graph.facebook.com/%s/picture?type=large&width=720&height=720",
                                        id),
                                email,
                                name));
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

        } else {
            super.onActivityResult(requestCode, resultCode, data);
            callbackManager.onActivityResult(requestCode, resultCode, data);
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
