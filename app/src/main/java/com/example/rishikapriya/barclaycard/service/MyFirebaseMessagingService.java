package com.example.rishikapriya.barclaycard.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.rishikapriya.barclaycard.MainActivity;
import com.example.rishikapriya.barclaycard.R;
import com.example.rishikapriya.barclaycard.Utils.CommonUtils;
import com.example.rishikapriya.barclaycard.model.Item;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by rishikapriya on 10/11/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        System.out.println("methodcalled");

        if(remoteMessage == null)
            return;

        if(remoteMessage.getData().size()>0){
            CommonUtils.debug(TAG+":DATA-PAYLOAD:",remoteMessage.getData().toString());
            showNotificationManager(remoteMessage);
        }
    }

    private void showNotificationManager(RemoteMessage remoteMessage) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, MainActivity.class);
        setIntentExtras(remoteMessage,intent);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, "WEB")
                                    .setContentTitle(remoteMessage.getData().get("title"))
                                    .setContentText(remoteMessage.getData().get("body"))
                                    .setSmallIcon(R.mipmap.ic_notification)
                                    .setAutoCancel(true)
                                    .addAction(0, "View", pendingIntent);

        notificationManager.notify(0, notification.build());
    }

    private void setIntentExtras(RemoteMessage remoteMessage, Intent intent) {
        intent.putExtra("name", remoteMessage.getData().get("name"));
        intent.putExtra("asin", remoteMessage.getData().get("ASIN"));
        intent.putExtra("boughtPrice", remoteMessage.getData().get("boughtPrice"));
        intent.putExtra("currentPrice", remoteMessage.getData().get("currentPrice"));
    }
}
