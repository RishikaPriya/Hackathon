package com.example.rishikapriya.barclaycard.service;

import com.example.rishikapriya.barclaycard.Security.Security;
import com.example.rishikapriya.barclaycard.communication.ServerCommunication;
import com.example.rishikapriya.barclaycard.communication.WebResponseListener;

import java.util.HashMap;
import java.util.Map;

import static com.example.rishikapriya.barclaycard.Utils.CommonUtils.getCurrentDate;
import static com.example.rishikapriya.barclaycard.constants.Constants.*;

/**
 * Created by rishikapriya on 24/11/17.
 */

public class GetTransactionService {

    public static void getTransactions(WebResponseListener listener){
        Map<String,String> headers =  new HashMap<>();
        headers.put("Accept","application/json");
        headers.put("Content-Type","application/json");
        headers.put("Authorization","Bearer "+ Security.getInstance().getUserAccessToken());
        headers.put("DeviceId",QUICKSTART_DEVICE_ID);

        Map<String, String> params = new HashMap<>();
        params.put("startDate", "01/01/2016");
        params.put("endDate", getCurrentDate());
        params.put("pageSize", "100");
        params.put("pageNum", "1");

        ServerCommunication.getmInstance().addJSONGetRequest(GET_ALL_TRANSACTION, params, headers, listener);

    }
}
