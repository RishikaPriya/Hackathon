package com.example.rishikapriya.barclaycard.service;

import com.example.rishikapriya.barclaycard.communication.ServerCommunication;
import com.example.rishikapriya.barclaycard.communication.WebResponseListener;
import com.example.rishikapriya.barclaycard.constants.Constants;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.rishikapriya.barclaycard.constants.Constants.AUTH_KEY;

/**
 * Created by rishikapriya on 24/11/17.
 */

public class NotificationService {

    public static void sendNotification(JSONObject jsonObject, WebResponseListener listener){
        Map<String,String> headers = new HashMap<>();
        headers.put("Content-Type","application/json");
        headers.put("Authorization", AUTH_KEY);

        ServerCommunication.getmInstance().addJSONPostRequestWithJsonData(Constants.SEND_NOTIFICATION,headers,jsonObject,listener);


    }
}
