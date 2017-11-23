package com.example.rishikapriya.barclaycard.service;

import com.example.rishikapriya.barclaycard.Security.Security;
import com.example.rishikapriya.barclaycard.communication.ServerCommunication;
import com.example.rishikapriya.barclaycard.communication.WebResponseListener;

import java.util.HashMap;
import java.util.Map;

import static com.example.rishikapriya.barclaycard.constants.Constants.*;

/**
 * Created by rishikapriya on 23/11/17.
 */

public class GetWalletListService {

    public static void getWalletList(WebResponseListener listener) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + Security.getInstance().getUserAccessToken());
        headers.put("DeviceId", QUICKSTART_DEVICE_ID);


        ServerCommunication.getmInstance().addJSONGetRequest(GET_WALLET_LIST_INFO, null, headers, listener);
    }
}
