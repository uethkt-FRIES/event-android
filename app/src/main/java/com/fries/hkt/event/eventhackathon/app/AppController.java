package com.fries.hkt.event.eventhackathon.app;

import android.content.Intent;
import android.support.multidex.MultiDexApplication;

import com.fries.hkt.event.eventhackathon.services.PushDialogQuickAnswerService;


public class AppController extends MultiDexApplication {

    public static final String TAG = AppController.class.getSimpleName();

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = new Intent(this, PushDialogQuickAnswerService.class);
        startService(intent);
        mInstance = this;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

}
