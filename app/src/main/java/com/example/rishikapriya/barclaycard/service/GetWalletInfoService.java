package com.example.rishikapriya.barclaycard.service;

import com.example.rishikapriya.barclaycard.Security.Security;
import com.example.rishikapriya.barclaycard.communication.ServerCommunication;
import com.example.rishikapriya.barclaycard.communication.WebResponseListener;
import com.example.rishikapriya.barclaycard.deals.SelectAddressFragment;

import java.util.HashMap;
import java.util.Map;

import static com.example.rishikapriya.barclaycard.constants.Constants.*;

/**
 * Created by rishikapriya on 22/11/17.
 */

public class GetWalletInfoService {

    public static void getWalletInfo(WebResponseListener listener){
        Map<String,String> headers =  new HashMap<>();
        headers.put("Accept","application/json");
        headers.put("Content-Type","application/json");
        headers.put("Authorization","Bearer "+ Security.getInstance().getUserAccessToken());
        headers.put("DeviceId",QUICKSTART_DEVICE_ID);


        ServerCommunication.getmInstance().addJSONGetRequest(GET_WALLET_INFO + Security.getInstance().getWalletCode(), null, headers, listener);

    }
}
