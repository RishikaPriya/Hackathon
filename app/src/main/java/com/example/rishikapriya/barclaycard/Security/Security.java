package com.example.rishikapriya.barclaycard.Security;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.rishikapriya.barclaycard.ApplicationController;
import com.example.rishikapriya.barclaycard.constants.Constants;
import com.example.rishikapriya.barclaycard.model.Item;

/**
 * Created by rishikapriya on 15/11/17.
 */

public class Security {
    public static Security instance ;
    public String authorizationKey;
    public String deviceId;
    public String userAccessToken;
    public String accessToken;
    public String walletCode;
    private Item item;
    private double balance;

    public static Security getInstance(){
        if(instance == null){
            instance = new Security();

        }
        return instance;
    }

    private Security(){
    }

    public String getAuthorizationKey() {
        return authorizationKey;
    }

    public void setAuthorizationKey(String authorizationKey) {
        this.authorizationKey = authorizationKey;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUserAccessToken() {
        return ApplicationController.getInstance().getPreferance(Constants.TOKEN);
    }

    public void setUserAccessToken(String userAccessToken) {
        this.userAccessToken = userAccessToken;
    }

    public String getWalletCode() {
        return walletCode;
    }

    public void setWalletCode(String walletCode) {
        this.walletCode = walletCode;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
