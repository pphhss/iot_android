package com.example.pharmacist;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class FCM extends FirebaseMessagingService {
    private static final String TAG = "MyMs";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map<String,String> data = remoteMessage.getData();
        Log.d(TAG,"onMessageReceived() 호출됨");



        if(data.get("type").equals("0"))
            sendNotification0(data);
        else if(data.get("type").equals("1"))
            sendNotification1(data);

    }
    private void sendNotification1(Map<String,String> dataMap){
        Intent intent = new Intent(this,ConsentTypeOneActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("name",dataMap.get("name"));
        intent.putExtra("result",dataMap.get("result"));
        intent.putExtra("imageurl",dataMap.get("imageurl"));
        intent.putExtra("sid",dataMap.get("sid"));


        PendingIntent contentItent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);

        Notification.Builder nBuilder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentTitle(dataMap.get("title"))
                .setContentText(dataMap.get("body"))
                .setAutoCancel(true)
                .setContentIntent(contentItent)
                .setVisibility(Notification.VISIBILITY_PRIVATE)
                .setDefaults(Notification.DEFAULT_ALL);

        NotificationManagerCompat nManager = NotificationManagerCompat.from(this);
        nManager.notify(0,nBuilder.build());

    }

    private void sendNotification0(Map<String,String> dataMap){
        Intent intent = new Intent(this,ConsentTypeZeroActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("name",dataMap.get("name"));
        intent.putExtra("temperature",dataMap.get("temperature"));
        intent.putExtra("heartrate",dataMap.get("heartrate"));
        intent.putExtra("symptominfo",dataMap.get("symptominfo"));
        intent.putExtra("curdisease",dataMap.get("curdisease"));
        intent.putExtra("sid",dataMap.get("sid"));


        PendingIntent contentItent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);

        Notification.Builder nBuilder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentTitle(dataMap.get("title"))
                .setContentText(dataMap.get("body"))
                .setAutoCancel(true)
                .setContentIntent(contentItent)
                .setVisibility(Notification.VISIBILITY_PRIVATE)
                .setDefaults(Notification.DEFAULT_ALL);

        NotificationManagerCompat nManager = NotificationManagerCompat.from(this);
        nManager.notify(0,nBuilder.build());

    }
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);

        Log.d(TAG,"Refresh Token : "+s);

    }


}
