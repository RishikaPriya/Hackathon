package com.example.rishikapriya.barclaycard.service;

import com.example.rishikapriya.barclaycard.communication.ServerCommunication;
import com.example.rishikapriya.barclaycard.communication.WebResponseListener;
import com.example.rishikapriya.barclaycard.constants.Constants;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rishikapriya on 14/11/17.
 */

public class LoginService {

    public static void loginService(String username, String password,WebResponseListener listener){
        Map<String,String> loginCredentials = new HashMap<>();
        loginCredentials.put("username",username);
        loginCredentials.put("password",password);
        loginCredentials.put("scope","PRODUCTION");
        loginCredentials.put("grant_type","password");
        ServerCommunication.getmInstance().addJSONPostRequest(Constants.APP_TOKEN,BarclayCardServiceTask.getDefaultHeaders(),(loginCredentials),listener);
       // ServerCommunication.getmInstance().addJSONPostRequest();
    }

    public static void getAppAccessToken(WebResponseListener listener){
        ServerCommunication.getmInstance().addJSONPostRequest(Constants.APP_TOKEN,BarclayCardServiceTask.getDefaultHeaders(),(BarclayCardServiceTask.getJsonRequestObject()),listener);
    }
}
