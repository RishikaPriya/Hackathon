package com.example.rishikapriya.barclaycard.service;

import com.example.rishikapriya.barclaycard.Security.Security;
import com.example.rishikapriya.barclaycard.communication.ServerCommunication;
import com.example.rishikapriya.barclaycard.communication.WebResponseListener;
import com.example.rishikapriya.barclaycard.constants.Constants;

import java.util.HashMap;
import java.util.Map;

import static com.example.rishikapriya.barclaycard.constants.Constants.*;

/**
 * Created by rishikapriya on 22/11/17.
 */

public class TransferMoneyService {

    public static void initiatePayment(String amount,String description, WebResponseListener listener){
        Map<String,String> headers =  new HashMap<>();
        headers.put("Accept","application/json");
        headers.put("Content-Type","application/json");
        headers.put("Authorization","Bearer "+ Security.getInstance().getUserAccessToken());
        headers.put("DeviceId",QUICKSTART_DEVICE_ID);

        Map<String,String> request = new HashMap<>();
        request.put("WalletCode",WALLET_CODE);
        request.put("Amount",amount);
        request.put("Description",description);

        ServerCommunication.getmInstance().addJSONPostRequestWithParameters(INITIATE_PAYMENT,headers,request,listener);
    }
}
