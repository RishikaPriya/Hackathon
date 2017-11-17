package com.example.rishikapriya.barclaycard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rishikapriya on 15/11/17.
 */

public class OAuthAccessToken{

    @Expose
    @SerializedName("scope")
    public String scope;

    @Expose
    @SerializedName("token_type")
    public String tokenType;

    @Expose
    @SerializedName("expires_in")
    public long expiresIn;

    @Expose
    @SerializedName("access_token")
    public String accessToken;

    @Expose
    @SerializedName("refresh_token")
    public String refreshToken;

}
