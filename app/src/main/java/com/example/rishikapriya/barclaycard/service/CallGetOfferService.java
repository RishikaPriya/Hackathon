package com.example.rishikapriya.barclaycard.service;

import com.example.rishikapriya.barclaycard.Security.Security;
import com.example.rishikapriya.barclaycard.communication.ServerCommunication;
import com.example.rishikapriya.barclaycard.communication.WebResponseListener;
import com.example.rishikapriya.barclaycard.constants.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.rishikapriya.barclaycard.constants.Constants.QUICKSTART_DEVICE_ID;

/**
 * Created by rishikapriya on 25/11/17.
 */

public class CallGetOfferService {

    public static void getOffer (WebResponseListener listener){
        Map<String,String> headers =  new HashMap<>();
        headers.put("Content-Type","application/json");

        JSONObject request = new JSONObject();
        try {
            request.put("transactionType", "Flight Booking");
            request.put("transactionAmount", 11000);
            request.put("customerId", 3);
            request.put("transactionDescription", "Flight Booked - MakeMyTrip");
            request.put("transactionDate", "2017-9-8/5:251");
            request.put("merchantName", "M22");

            ServerCommunication.getmInstance().addJSONPostRequestWithJSONObject(Constants.GET_OFFER_URL,headers,request,listener);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
