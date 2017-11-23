package com.example.rishikapriya.barclaycard.service;

import com.example.rishikapriya.barclaycard.Security.Security;
import com.example.rishikapriya.barclaycard.communication.ServerCommunication;
import com.example.rishikapriya.barclaycard.communication.WebResponseListener;
import com.example.rishikapriya.barclaycard.constants.Constants;

import java.util.HashMap;
import java.util.Map;

import static com.example.rishikapriya.barclaycard.constants.Constants.QUICKSTART_DEVICE_ID;

/**
 * Created by rishikapriya on 20/11/17.
 */

public class CreateWalletService {

    public static void createWallet (WebResponseListener listener){
        Map<String,String> headers =  new HashMap<>();
        headers.put("Accept","application/json");
        headers.put("Content-Type","application/json");
        headers.put("Authorization","Bearer "+ Security.getInstance().getUserAccessToken());
        headers.put("DeviceId",QUICKSTART_DEVICE_ID);

        Map<String,String> request = new HashMap<>();
        request.put("WalletName","BankAccount2048");

        ServerCommunication.getmInstance().addJSONPostRequestWithParameters(Constants.CREATE_WALLET,headers,request,listener);
    }
}
