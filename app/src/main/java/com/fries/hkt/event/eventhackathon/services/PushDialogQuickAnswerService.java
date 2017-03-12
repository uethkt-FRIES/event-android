package com.fries.hkt.event.eventhackathon.services;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.fries.hkt.event.eventhackathon.R;
import com.fries.hkt.event.eventhackathon.app.AppConfig;
import com.fries.hkt.event.eventhackathon.customview.QuickAnswerViewGroup;
import com.fries.hkt.event.eventhackathon.eventbus.ShowQuickAnswerEvent;
import com.fries.hkt.event.eventhackathon.models.QuestionBean;
import com.fries.hkt.event.eventhackathon.network.RequestServer;
import com.fries.hkt.event.eventhackathon.utils.SharedPreferencesMgr;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by hungtran on 3/11/17.
 */

public class PushDialogQuickAnswerService extends Service implements View.OnTouchListener,View.OnClickListener{

    private QuestionBean mQuestion;
    String answerChoise;

    private WindowManager windowManager;
    private QuickAnswerViewGroup myViewGroup;
    private WindowManager.LayoutParams mParams;
    private View subView;
    private TextView content;
    private Button rd1;
    private Button rd2;
    private Button rd3;
    private Button rd4;
    private int DOWN_X, DOWN_Y,MOVE_X,MOVE_Y,xparam,yparam;


    private SharedPreferencesMgr sharedPreferencesMgr;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferencesMgr = new SharedPreferencesMgr(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (subView != null) windowManager.removeView(subView);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }

        if(intent != null && intent.getExtras() != null){
            Log.d("hi`hi:,", "" + intent.getExtras().getString("type"));
        }
        return START_STICKY;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rd_1:
                answerChoise = "as1";
                submitAnswer();
                break;
            case R.id.rd_2:
                answerChoise = "as2";
                submitAnswer();
                break;
            case R.id.rd_3:
                answerChoise = "as3";
                submitAnswer();
                break;
            case R.id.rd_4:
                answerChoise = "as4";
                submitAnswer();
                break;

        }
    }


    void submitAnswer(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("event_id", sharedPreferencesMgr.getEventId());
            jsonObject.put("question_id", mQuestion.getQuestionId());
            jsonObject.put("answer", answerChoise);

            RequestServer req = new RequestServer(this, Request.Method.POST, AppConfig.BASE_URL + "/postvote", jsonObject);
            req.setListener(new RequestServer.ServerListener() {
                @Override
                public void onReceive(boolean error, JSONObject response, String message) throws JSONException {
                    if(error){
                        Toast.makeText(PushDialogQuickAnswerService.this, message, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PushDialogQuickAnswerService.this, "Thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            req.sendRequest("tag");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (subView != null) windowManager.removeView(subView);
    }

    private void initView() {
        windowManager = (WindowManager)getSystemService(WINDOW_SERVICE);
        myViewGroup = new QuickAnswerViewGroup(this);
        LayoutInflater minflater = LayoutInflater.from(this);
        subView = minflater.inflate(R.layout.wm_quick_answer, myViewGroup);// nhet cai main vao cai viewGroup, de anh xa ra subView
        //dinh nghia param
        mParams = new WindowManager.LayoutParams();
        mParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.gravity = Gravity.CENTER;
        mParams.format = PixelFormat.TRANSLUCENT;//trong suot
        mParams.type = WindowManager.LayoutParams.TYPE_PHONE;// noi tren all be mat
        mParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS|WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;// khong bi gioi han boi man hinh|Su duoc nut home
        windowManager.addView(subView, mParams);
        subView.setOnTouchListener(this);

        content = (TextView)subView.findViewById(R.id.content);
        rd1 = (Button) subView.findViewById(R.id.rd_1);
        rd2 = (Button) subView.findViewById(R.id.rd_2);
        rd3 = (Button) subView.findViewById(R.id.rd_3);
        rd4 = (Button) subView.findViewById(R.id.rd_4);

        rd1.setOnClickListener(this);
        rd2.setOnClickListener(this);
        rd3.setOnClickListener(this);
        rd4.setOnClickListener(this);

        //subView.bringToFront();


    }

    private void fillDataToView(QuestionBean mQuestion) {
        this.mQuestion = mQuestion;
        content.setText(mQuestion.getContent() == null ? "Timy" : mQuestion.getContent());
        if(!TextUtils.isEmpty(mQuestion.getAs1())){
            rd1.setText(mQuestion.getAs1());
        } else {
            rd1.setVisibility(View.GONE);
        }
        if(!TextUtils.isEmpty(mQuestion.getAs2())){
            rd2.setText(mQuestion.getAs2());
        } else {
            rd1.setVisibility(View.GONE);
        }

        if(!TextUtils.isEmpty(mQuestion.getAs3())){
            rd3.setText(mQuestion.getAs3());
        } else {
            rd3.setVisibility(View.GONE);
        }

        if(!TextUtils.isEmpty(mQuestion.getAs4())){
            rd4.setText(mQuestion.getAs4());
        } else {
            rd4.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                xparam = mParams.x;
                yparam = mParams.y;
                DOWN_X = (int)motionEvent.getRawX();
                DOWN_Y = (int)motionEvent.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                MOVE_X = (int)motionEvent.getRawX()- DOWN_X ;
                MOVE_Y = (int)motionEvent.getRawY()- DOWN_Y ;
                updateView(MOVE_X, MOVE_Y);
                break;
        }
        return true;
    }

    private void updateView(int x, int y) {
        mParams.x = x + xparam;
        mParams.y = y + yparam;
        windowManager.updateViewLayout(subView,mParams);
    }

    @Subscribe
    public void onOpenWindow(QuestionBean questionBean){
        Log.d("AW1", "--" + questionBean.getAs1());
        final QuestionBean q = new QuestionBean(
                questionBean.getQuestionId(),
                questionBean.getContent(),
                questionBean.getAs1(),
                questionBean.getAs2(),
                questionBean.getAs3(),
                questionBean.getAs4()
        );
        Handler mHandler = new Handler(Looper.getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                initView();
                fillDataToView(q);
            }
        });
    }

}
