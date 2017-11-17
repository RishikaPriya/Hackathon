package com.example.rishikapriya.barclaycard.Security;

/**
 * Created by rishikapriya on 15/11/17.
 */

public class Security {
    public static Security instance ;
    public String authorizationKey;
    public String deviceId;
    public String userAccessToken;
    public String accessToken;

    public static Security getInstance(){
        if(instance == null){
            instance = new Security();
        }
        return instance;
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
        return userAccessToken;
    }

    public void setUserAccessToken(String userAccessToken) {
        this.userAccessToken = userAccessToken;
    }
}
