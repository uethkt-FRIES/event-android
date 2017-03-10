package com.fries.hkt.event.eventhackathon.services;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
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

import com.fries.hkt.event.eventhackathon.R;
import com.fries.hkt.event.eventhackathon.customview.QuickAnswerViewGroup;
import com.fries.hkt.event.eventhackathon.eventbus.ShowQuickAnswerEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by hungtran on 3/11/17.
 */

public class PushDialogQuickAnswerService extends Service implements View.OnTouchListener,View.OnClickListener{

    private WindowManager windowManager;
    private QuickAnswerViewGroup myViewGroup;
    private WindowManager.LayoutParams mParams;
    private View subView;
    private TextView title;
    private TextView content;
    private Button btnSend;
    private Button btnCancel;
    private RadioGroup rdAnswers;
    private RadioButton rd1;
    private RadioButton rd2;
    private RadioButton rd3;
    private RadioButton rd4;
    private int DOWN_X, DOWN_Y,MOVE_X,MOVE_Y,xparam,yparam;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initView();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_send){
            //send answer to firebase
            Toast.makeText(this, "Sending", Toast.LENGTH_SHORT).show();
        } else if(view.getId() == R.id.btn_cancel){
            //close view
        }
    }

    private void initView() {
        windowManager = (WindowManager)getSystemService(WINDOW_SERVICE);
        myViewGroup = new QuickAnswerViewGroup(this);
        LayoutInflater minflater = LayoutInflater.from(this);
        subView = minflater.inflate(R.layout.wm_quick_answer, myViewGroup);// nhet cai main vao cai viewGroup, de anh xa ra subView
        //dinh nghia param
        mParams = new WindowManager.LayoutParams();
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.gravity = Gravity.CENTER;
        mParams.format = PixelFormat.TRANSLUCENT;//trong suot
        mParams.type = WindowManager.LayoutParams.TYPE_PHONE;// noi tren all be mat
        mParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS|WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;// khong bi gioi han boi man hinh|Su duoc nut home
        windowManager.addView(myViewGroup,mParams);
        subView.setOnTouchListener(this);

        title = (TextView)subView.findViewById(R.id.title);
        content = (TextView)subView.findViewById(R.id.content);
        btnSend = (Button)subView.findViewById(R.id.btn_send);
        btnCancel = (Button)subView.findViewById(R.id.btn_cancel);
        rdAnswers = (RadioGroup)subView.findViewById(R.id.radio_group);
        rd1 = (RadioButton) subView.findViewById(R.id.rd_1);
        rd2 = (RadioButton) subView.findViewById(R.id.rd_2);
        rd3 = (RadioButton) subView.findViewById(R.id.rd_3);
        rd4 = (RadioButton) subView.findViewById(R.id.rd_4);

        btnCancel.setOnClickListener(this);
        btnSend.setOnClickListener(this);

        subView.bringToFront();
        fillDataToView();

    }

    private void fillDataToView() {

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
        mParams.x = x+xparam;
        mParams.y=y+yparam;
        windowManager.updateViewLayout(myViewGroup,mParams);
    }

    @Subscribe
    private void onOpenQuickAnswer(ShowQuickAnswerEvent showQuickAnswerEvent){
        Log.d("hihi", "hihe");
        initView();
    }
}
