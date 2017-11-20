package com.example.rishikapriya.barclaycard.service;

import com.example.rishikapriya.barclaycard.Security.Security;
import com.example.rishikapriya.barclaycard.communication.ServerCommunication;
import com.example.rishikapriya.barclaycard.communication.WebResponseListener;
import com.example.rishikapriya.barclaycard.constants.Constants;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.rishikapriya.barclaycard.constants.Constants.*;

/**
 * Created by rishikapriya on 20/11/17.
 */

public class AddAmountService {

    public static void addAmount(String description, String subCategory, WebResponseListener listener) {
        Map<String,String> headers =  new HashMap<>();
        headers.put("Accept","application/json");
        headers.put("Content-Type","application/json");
        headers.put("Authorization","Bearer "+ Security.getInstance().getUserAccessToken());
        headers.put("DeviceId",QUICKSTART_DEVICE_ID);

        Map<String,String> request = new HashMap<>();
        request.put("WalletCode",Security.getInstance().getWalletCode());
        request.put("Date", new Date().toString());
        request.put("Amount", MINIMUM_AMOUNT);
        request.put("Reason", description);
        request.put("SubCategoryCode",subCategory);

        ServerCommunication.getmInstance().addJSONPostRequestWithParameters(ADD_AMOUNT,headers,request,listener);

    }
}
