package com.example.rishikapriya.barclaycard.model

import com.google.gson.annotations.SerializedName

/**
 * Created by rishikapriya on 20/11/17.
 */

class CreateWalletResponse{
    @SerializedName("DataObject")
    val walletCode: Wallet? = null
    @SerializedName ("Status")
    val status: String? = null
    @SerializedName("Message")
    val message: String? = null

    class Wallet{
        val walletCode: String? = null
    }
}

class OAuthAccessTokenResponse(@SerializedName("scope") val scope: String, @SerializedName("token_type") val token: String,
                               @SerializedName("expires_in") val expiresIn: String, @SerializedName("access_token") val accessToken: String,
                               @SerializedName("refresh_token") val refresh_token: String);