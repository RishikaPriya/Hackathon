package com.example.rishikapriya.barclaycard.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.rishikapriya.barclaycard.ApplicationController;
import com.example.rishikapriya.barclaycard.MainActivity;
import com.example.rishikapriya.barclaycard.R;
import com.example.rishikapriya.barclaycard.Utils.CommonUtils;
import com.example.rishikapriya.barclaycard.constants.Constants;
import com.example.rishikapriya.barclaycard.model.Item;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by rishikapriya on 10/11/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        System.out.println("methodcalled");

        if (remoteMessage == null)
            return;
        if (ApplicationController.getInstance().getPreferanceBoolean(Constants.IS_LOGIN)) {
            if (remoteMessage.getData().size() > 0) {
                CommonUtils.debug(TAG + ":DATA-PAYLOAD:", remoteMessage.getData().toString());
                if (remoteMessage.getData().containsKey("PersonalizedData")) {
                    showPersonalizedData(remoteMessage.getData());
                } else {
                    showNotificationManager(remoteMessage);
                }
            }
        }
    }

    private void showPersonalizedData(Map<String, String> data) {
        CommonUtils.debug("showPersonalizedData", data.toString());
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, MainActivity.class);
        setIntentExtrasForData(data, intent);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, "PersonalizedData")
                .setContentTitle(data.get("title"))
                .setContentText(data.get("description"))
                .setSmallIcon(R.mipmap.ic_notification)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);


        notificationManager.notify(0, notification.build());
    }

    private void showNotificationManager(RemoteMessage remoteMessage) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, MainActivity.class);
        setIntentExtras(remoteMessage, intent);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, "WEB")
                .setContentTitle("Offer!")
                .setContentText("Best Offers for you")
                .setSmallIcon(R.mipmap.ic_notification)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);


        notificationManager.notify(0, notification.build());
    }

    private void setIntentExtras(RemoteMessage remoteMessage, Intent intent) {
        Map<String, String> data = remoteMessage.getData();

        if (data != null && data.size() != 0) {
            intent.putExtra("NO_OF_ITEMS", data.size());
            int i = 1;
            for (String keys : data.keySet()) {
                intent.putExtra("item" + i, keys);
                String[] itemValues = data.get(keys).split(",");
                intent.putExtra("item" + i + "bought_price", itemValues[0]);
                intent.putExtra("item" + i + "current_price", itemValues[1]);
                intent.putExtra("item" + i + "image_id", Integer.parseInt(itemValues[2]));
                i++;
            }
        }
    }

    private void setIntentExtrasForData(Map<String, String> data, Intent intent) {
        intent.putExtra("fragment", "PersonalizedData");
        intent.putExtra("title", data.get("title"));
        intent.putExtra("description", data.get("description"));
        intent.putExtra("link", data.get("link"));
        intent.putExtra("validity_date", data.get("validity_date"));
        intent.putExtra("category", data.get("mcc"));
    }
}
