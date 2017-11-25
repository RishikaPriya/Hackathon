package com.example.rishikapriya.barclaycard.model

import com.google.gson.annotations.SerializedName

/**
 * Created by rishikapriya on 20/11/17.
 */

class CreateWalletResponse(
    @SerializedName("DataObject")
    val wallet: Wallet? = null,
    @SerializedName ("Status")
    val status: String? = null,
    @SerializedName("Message")
    val message: String? = null
);

class Wallet(
    @SerializedName("WalletCode")
    val walletCode: String? = null
);

class OAuthAccessTokenResponse(
        @SerializedName("scope")
        val scope: String,
        @SerializedName("token_type")
        val token: String,
        @SerializedName("expires_in")
        val expiresIn: String,
        @SerializedName("access_token")
        val accessToken: String,
        @SerializedName("refresh_token")
        val refresh_token: String
);

class InitiatePaymentResponse(
    @SerializedName("DataObject")
    val transactionId: Transaction? = null,
    @SerializedName ("Status")
    val status: String? = null,
    @SerializedName("Message")
    val message: String? = null
);

class Transaction(
    @SerializedName("TransactionID")
    val transactionId: String? = null
);

class WalletInfoResponse(
        @SerializedName("DataObject")
        val walletInfo: WalletInfo? = null,
        @SerializedName ("Status")
        val status: String? = null,
        @SerializedName("Message")
        val message: String? = null
);

class WalletInfo(
    @SerializedName("WalletCode")
    val walletCode: String? = null,
    @SerializedName("CurrentBalance")
    val currentBalance: String? = null,
    @SerializedName("Name")
    val walletName: String? =null
);

class WalletListResponse(
        @SerializedName("TotalRecords")
        val size: Int? = null,
        @SerializedName("ListOfObjects")
        val walletListInfo: List<WalletInfo>? = null,
        @SerializedName ("Status")
        val status: String? = null,
        @SerializedName("Message")
        val message: String? = null
);

class StatusResponse(
        @SerializedName ("Status")
        val status: String? = null,
        @SerializedName("Message")
        val message: String? = null
);

class TransactionListResponse(
        @SerializedName("TotalRecords")
        val size: Int? = null,
        @SerializedName("ListOfObjects")
        val transactionListInfo: List<TransactionInfo>? = null,
        @SerializedName ("Status")
        val status: String? = null,
        @SerializedName("Message")
        val message: String? = null
);

class TransactionInfo(
        @SerializedName("StartDate")
        val startDate: String? = null,
        @SerializedName("Code")
        val referenceNumber: String? = null,
        @SerializedName("Description")
        val description: String? =null,
        @SerializedName("EndDate")
        val endDate: String? = null,
        @SerializedName("TranAmount")
        val transaction_amount: String? = null,
        @SerializedName("BalanceAfter")
        val closing_balance: String? =null
);

class Notification(
        val title: String,
        val description: String,
        val link: String,
        val validity_date: String,
        val category: String
);