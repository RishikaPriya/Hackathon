package com.example.rishikapriya.barclaycard.service;

import com.example.rishikapriya.barclaycard.Security.Security;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rishikapriya on 15/11/17.
 */

public class BarclayCardServiceTask {

    public static BarclayCardServiceTask instance;
    public static BarclayCardServiceTask getInstance(){
        if(instance ==  null){
            instance = new BarclayCardServiceTask();
        }
        return instance;
    }

    public static Map<String,String> getDefaultHeaders(){
        Map<String,String> headers =  new HashMap<>();
        headers.put("Accept","application/json");
        headers.put("Content-Type","application/x-www-form-urlencoded");
        headers.put("Authorization", Security.getInstance().authorizationKey);
        return headers;
    }

    public static Map<String,String> getJsonRequestObject() {
        Map<String,String> request = new HashMap<>();
        request.put("grant_type","client_credentials");
        request.put("scope","PRODUCTION");

        return (request);
    }
}
