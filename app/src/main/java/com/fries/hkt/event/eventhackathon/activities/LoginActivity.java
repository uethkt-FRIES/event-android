package com.fries.hkt.event.eventhackathon.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by tmq on 10/03/2017.
 */

public class LoginActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

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
        initFirebaseAuthen();
        setupLoginFacebook();
    }

    private void initFirebaseAuthen() {
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
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
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.txt_please_wait));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.show();
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
    }

    private void setupLoginFacebook() {
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handleFacebookAccessToken(loginResult.getAccessToken());
//                        GraphRequest request = GraphRequest.newMeRequest(
//                                loginResult.getAccessToken(),
//                                new GraphRequest.GraphJSONObjectCallback() {
//                                    @Override
//                                    public void onCompleted(JSONObject object, GraphResponse response) {
//                                        Log.v("LoginActivity", response.toString());
//                                        // Application code
//                                        try {
//                                            String email = object.getString("email");
//                                            String birthday = "33"; // 01/31/1980 format
//                                            String name = object.getString("name");
//                                            String id = object.getString("id");
//                                            saveUserInformation(id, name, email, birthday);
//                                            directToCheckIn();
//                                            if(progressDialog != null){
//                                                progressDialog.dismiss();
//                                            }
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//
//                                    }
//                                });
//                        Bundle parameters = new Bundle();
//                        parameters.putString("fields", "id,name,email,gender,birthday");
//                        request.setParameters(parameters);
//                        request.executeAsync();
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

    private void saveUserInformation(String avatar, String name, String email){
                sharedPreferencesMgr.setLogin(true);
                sharedPreferencesMgr.setUserInfo(
                        new IUser(
                                avatar,
                                email,
                                name));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }



    private void directToCheckIn(){
        Intent intent = new Intent(LoginActivity.this, CheckInActivity.class);
        startActivity(intent);

        LoginActivity.this.finish();
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            String email = task.getResult().getUser().getEmail();
                            String name = task.getResult().getUser().getDisplayName();
                            String id = task.getResult().getUser().getUid();
                            saveUserInformation(task.getResult().getUser().getPhotoUrl().toString(), name, email);
                            directToCheckIn();
                            if(progressDialog != null){
                                progressDialog.dismiss();
                            }
                        }
                    }
                });
    }
}
