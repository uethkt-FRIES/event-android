package com.fries.hkt.event.eventhackathon.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.fries.hkt.event.eventhackathon.R;
import com.fries.hkt.event.eventhackathon.app.AppConfig;
import com.fries.hkt.event.eventhackathon.network.RequestServer;
import com.fries.hkt.event.eventhackathon.utils.CommonVls;
import com.fries.hkt.event.eventhackathon.utils.SharedPreferencesMgr;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by tmq on 10/03/2017.
 */

public class CheckInActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = CheckInActivity.class.getSimpleName();

    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private SurfaceView cameraView;
    private EditText edtTypeCode;
    private ImageButton btnSendCode;

    private boolean allowScan = true;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            String code = bundle.getString("code");

            Log.i(TAG, "code = " + code);
            checkCode(code);
            Toast.makeText(CheckInActivity.this, code, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);

        initViews();
        initCamera();
    }

    private void initViews() {
        cameraView = (SurfaceView) findViewById(R.id.camera_view);
        edtTypeCode = (EditText) findViewById(R.id.edt_type_code);
        btnSendCode = (ImageButton) findViewById(R.id.btn_send_code);

        btnSendCode.setOnClickListener(this);
    }

    private void initCamera() {
        barcodeDetector =
                new BarcodeDetector.Builder(this)
                        .setBarcodeFormats(Barcode.QR_CODE)
                        .build();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setRequestedPreviewSize(size.y, size.x)
                .build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                startCamera();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                if (!allowScan) return;

                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if (barcodes.size() != 0) {
                    allowScan = false;
                    Message message = mHandler.obtainMessage();

                    Bundle bundle = new Bundle();
                    bundle.putString("code", barcodes.valueAt(0).displayValue);

                    message.setData(bundle);
                    mHandler.sendMessage(message);
                }
            }
        });
    }

    private void startCamera(){
        try {
            if (ActivityCompat.checkSelfPermission(CheckInActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                CommonVls.verifyCameraPermissions(CheckInActivity.this);
                return;
            }
            cameraSource.start(cameraView.getHolder());
        } catch (IOException ie) {
            Log.e("CAMERA SOURCE", ie.getMessage());
        }

    }

    private void checkCode(final String code) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.txt_please_wait));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        final SharedPreferencesMgr preferencesMgr = new SharedPreferencesMgr(this);
        JSONObject json = new JSONObject();//event_id, email, fcm_token
        try {
            json.put("event_id", code);
            json.put("email", preferencesMgr.getUserInfo().getEmail());
            json.put("fcm_token", FirebaseInstanceId.getInstance().getToken());
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i(TAG, json.toString());
        RequestServer requestServer = new RequestServer(this, Request.Method.POST, AppConfig.REGISTER_FCM, json);
        requestServer.setListener(new RequestServer.ServerListener() {
            @Override
            public void onReceive(boolean error, JSONObject response, String message) throws JSONException {
                if (error) {
                    Toast.makeText(CheckInActivity.this, message, Toast.LENGTH_SHORT).show();
                    Log.i(TAG, response.toString());
                    return;
                }
                preferencesMgr.setEventId(code);

                Toast.makeText(CheckInActivity.this, R.string.txt_login_success, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

                Intent intent = new Intent(CheckInActivity.this, MainActivity.class);
                startActivity(intent);

                CheckInActivity.this.finish();
            }
        });
        requestServer.sendRequest("fcm");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_code:
                String code = edtTypeCode.getText().toString();
                if (!code.isEmpty()) {
                    checkCode(code);
                } else {
                    Toast.makeText(this, "Chưa điền mã", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CommonVls.REQUEST_CAMERA: {
                Log.i(TAG, "request camera");
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "grant");
                    startCamera();
                } else {
                    Toast.makeText(this, "Không thể xin quyền sử dụng Camera", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "Request denied");
                }
                break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraSource.release();
        barcodeDetector.release();
    }

}
