package com.fries.hkt.event.eventhackathon.network;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fries.hkt.event.eventhackathon.utils.SharedPreferencesMgr;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tmq on 11/03/2017.
 */

public class RequestServer {
    private static final String TAG = RequestServer.class.getSimpleName();
    private JsonObjectRequest request;
    private int method;
    private String url;
    private JSONObject jsonReq;
    private Context mContext;

    private SharedPreferencesMgr session;


    // Request: Not upload data (JSONObject)
    public RequestServer(Context context, int method, String url) {
        session = new SharedPreferencesMgr(context);

        this.mContext = context;
        this.method = method;
        this.url = url;
        this.jsonReq = new JSONObject();

        initListener();
    }

    // Request: Upload data (JSONObject)
    public RequestServer(Context context, int method, String url, JSONObject jsonReq) {
        session = new SharedPreferencesMgr(context);
        this.mContext = context;
        this.method = method;
        this.url = url;
        this.jsonReq = jsonReq;

        initListener();
    }

    private void initListener() {
        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (mListener != null){
                        mListener.onReceive(false, response, response.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    byte[] data = error.networkResponse.data;
                    JSONObject jsonError = new JSONObject(new String(data));
                    if (mListener != null){
                        mListener.onReceive(true, jsonError, jsonError.getString("message"));
//                        if (jsonError.getString("error").equalsIgnoreCase("Unauthorized")) {
//                            Toast.makeText(mContext, "Phiên làm việc của bạn đã hết! Vui lòng đăng nhập lại!", Toast.LENGTH_SHORT).show();
//                            logout();
//                        }
                    }
                } catch (Exception e) {
                    if (mListener != null){
                        try {
                            mListener.onReceive(true, new JSONObject(), "");
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        };

        final boolean isLogin = session.isLoggedIn();

        request = new JsonObjectRequest(method, url, jsonReq, listener, error) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (isLogin) {
                    Map<String, String> params = new HashMap<String, String>();
                    // Send token to server -> finish a session
//                    params.put("Authorization", token);
                    return params;
                }

                return super.getHeaders();
            }
        };
    }

    public boolean sendRequest(String tag) {
        if (isOnline()){
//            AppController.getInstance().addToRequestQueue(request, tag);
            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
            requestQueue.add(request);
            return true;
        } else {
            Toast.makeText(mContext, "Vui lòng kiểm tra kết nối internet!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    // ---------------------------------------------------------------------------------------------
    private ServerListener mListener;

    public void setListener(ServerListener listener) {
        mListener = listener;
    }

    public interface ServerListener {
        void onReceive(boolean error, JSONObject response, String message) throws JSONException;
    }

}
