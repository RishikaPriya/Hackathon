package com.example.rishikapriya.barclaycard.service;

import com.example.rishikapriya.barclaycard.Utils.CommonUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by rishikapriya on 10/11/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if(remoteMessage == null)
            return;

        //Check if message contains a notification payload
        if(remoteMessage.getNotification()!=null){
            CommonUtils.debug(TAG+":NOTIFICATION-PAYLOAD:",remoteMessage.getNotification().getBody());
        }

        //Check if message contains a data payload
        if(remoteMessage.getData().size()>0){
            CommonUtils.debug(TAG+":DATA-PAYLOAD:",remoteMessage.getData().toString());
        }
    }
}
