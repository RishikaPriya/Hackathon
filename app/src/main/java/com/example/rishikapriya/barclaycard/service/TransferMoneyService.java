package com.example.rishikapriya.barclaycard.service;

import android.support.annotation.NonNull;

import com.example.rishikapriya.barclaycard.Security.Security;
import com.example.rishikapriya.barclaycard.Utils.CommonUtils;
import com.example.rishikapriya.barclaycard.communication.ServerCommunication;
import com.example.rishikapriya.barclaycard.communication.WebResponseListener;
import com.example.rishikapriya.barclaycard.constants.Constants;

import java.util.HashMap;
import java.util.Map;

import static com.example.rishikapriya.barclaycard.Utils.CommonUtils.getCurrentDate;
import static com.example.rishikapriya.barclaycard.constants.Constants.*;

/**
 * Created by rishikapriya on 22/11/17.
 */

public class TransferMoneyService {

    public static void initiatePayment(String amount,String description, WebResponseListener listener){
        Map<String,String> request = new HashMap<>();
        request.put("WalletCode",WALLET_CODE);
        request.put("Amount",amount);
        request.put("Description",description);

        ServerCommunication.getmInstance().addJSONPostRequestWithParameters(INITIATE_PAYMENT,getHeaderMap(),request,listener);
    }

    @NonNull
    private static Map<String, String> getHeaderMap() {
        Map<String,String> headers =  new HashMap<>();
        headers.put("Accept","application/json");
        headers.put("Content-Type","application/json");
        headers.put("Authorization","Bearer "+ Security.getInstance().getUserAccessToken());
        headers.put("DeviceId",QUICKSTART_DEVICE_ID);
        return headers;
    }

    public static void confirmPayment(String paymentToken, WebResponseListener listener){

        Map<String,String> request = new HashMap<>();
        request.put("PaymentToken",paymentToken);
        request.put("PIN",PIN);

        ServerCommunication.getmInstance().addJSONPostRequestWithParameters(CONFIRM_PAYMENT,getHeaderMap(),request,listener);
    }

    public static void transferAmount(String fromWallet, String toWallet, String amount, String reason, WebResponseListener listener){

        Map<String,String> request = new HashMap<>();
        request.put("FromWalletCode",fromWallet);
        request.put("ToWalletCode",toWallet);
        request.put("Date", getCurrentDate());
        request.put("Amount",amount);
        request.put("Reason", reason);

        ServerCommunication.getmInstance().addJSONPostRequestWithParameters(TRANSFER_AMOUNT,getHeaderMap(),request,listener);
    }
}
