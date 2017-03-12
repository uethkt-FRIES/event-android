package com.fries.hkt.event.eventhackathon.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.fries.hkt.event.eventhackathon.R;
import com.fries.hkt.event.eventhackathon.activities.LoadingActivity;
import com.fries.hkt.event.eventhackathon.activities.NotificationsActivity;
import com.fries.hkt.event.eventhackathon.customview.QuickAnswerViewGroup;
import com.fries.hkt.event.eventhackathon.eventbus.ShowQuickAnswerEvent;
import com.fries.hkt.event.eventhackathon.models.QuestionBean;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    QuestionBean questionBean;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

        sendNotification(remoteMessage.getData());
    }


    private void sendNotification(Map<String, String> msgData) {
        Bundle bundle = new Bundle();
        if (msgData.containsKey("type]")) {
            if (msgData.get("type]").equals("1")) {
                Log.d("FCM", "ABCD");
                handleMessageWithOpenQuickAnswer(msgData);
            } else if (msgData.get("type]").equals("2")){
                Intent intent = new Intent(this, NotificationsActivity.class);
                intent.putExtras(bundle);

                PendingIntent pendingIntent = PendingIntent.getService(this, 0 /* Request code */, intent,
                        0);

                NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(msgData.get("title]"))
                        .setContentText(msgData.get("content]"))
                        .setAutoCancel(true)
                        .setOngoing(true)
                        .setWhen(System.currentTimeMillis())
                        .setContentIntent(pendingIntent);

                NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build());

            }
        } else {
            Intent intent = new Intent(this, LoadingActivity.class);
            intent.putExtras(bundle);

            PendingIntent pendingIntent = PendingIntent.getService(this, 0 /* Request code */, intent,
                    0);

            NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Timy")
                    .setContentText(msgData.get("title"))
                    .setAutoCancel(true)
                    .setOngoing(true)
                    .setWhen(System.currentTimeMillis())
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build());
        }
    }


    private void handleMessageWithOpenQuickAnswer(Map<String, String> msgData){
        Bundle bundle = new Bundle();
        QuestionBean questionBean = new QuestionBean();
        Log.d("HIhi", msgData.get("question_id]"));
        if(msgData.containsKey("question_id]")) {
            bundle.putString("question_id", msgData.get("question_id]"));
            questionBean.setQuestionId(msgData.get("question_id]"));
        }
        if(msgData.containsKey("content]")){
            bundle.putString("content", msgData.get("content]"));
            questionBean.setQuestionId(msgData.get("content]"));
        }
        if(msgData.containsKey("as1]")){
            bundle.putString("as1", msgData.get("as1]"));
            questionBean.setQuestionId(msgData.get("as1]"));
        }
        if(msgData.containsKey("as2]")){
            bundle.putString("as2", msgData.get("as2]"));
            questionBean.setQuestionId(msgData.get("as2]"));
        }
        if(msgData.containsKey("as3]")){
            bundle.putString("as3", msgData.get("as3]"));
            questionBean.setQuestionId(msgData.get("as4]"));
        }
        if(msgData.containsKey("as4]")){
            bundle.putString("as4", msgData.get("as4]"));
            questionBean.setQuestionId(msgData.get("as4]"));
        }
        QuestionBean qq = new QuestionBean(
                bundle.getString("question_id"),
                bundle.getString("content"),
                bundle.getString("as1"),
                bundle.getString("as2"),
                bundle.getString("as3"),
                bundle.getString("as4"));

        EventBus.getDefault().post(qq);
    }


}
