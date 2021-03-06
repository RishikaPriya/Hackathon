package com.example.rishikapriya.barclaycard.constants;

/**
 * Created by rishikapriya on 14/11/17.
 */

public final class Constants {
    public static final String API_ENDPOINT = "https://api.risemarketplace.net/";

    public static final String APP_TOKEN = "https://api.risemarketplace.net/token";

    public static final String API_BASE_URL =  API_ENDPOINT + "rise/v1.0.0/api/";

    public static final String CREATE_WALLET = API_BASE_URL + "wallet/CreateWallet";
    public static final String INITIATE_PAYMENT = API_BASE_URL + "wallet/InitiatePayment";
    public static final String CONFIRM_PAYMENT = API_BASE_URL + "wallet/ConfirmPayment";
    public static final String TRANSFER_AMOUNT = API_BASE_URL + "wallet/TransferValue";
    public static final String GET_WALLET_INFO = API_BASE_URL + "wallet/GetWalletInfo?walletCode=";
    public static final String GET_WALLET_LIST_INFO = API_BASE_URL + "wallet/GetWalletList?";
    public static final String GET_ALL_TRANSACTION = API_BASE_URL + "cashflow/GetTransactionList?";
    public static final String GET_WALLET_TRANSACTION = API_BASE_URL + "wallet/GetWalletTransactions?";

    public static final String SEND_NOTIFICATION = "https://fcm.googleapis.com/fcm/send";
    public static final String AUTH_KEY = "key=AAAA0b4dEU8:APA91bHYDc7HawBlT5eR93bTtaHMM739fwl3YvqPEeNvztWV65WbjeL5KC_Dp4phUrdTaVxNve0BCxlBpBrR5xhtUZJfBZZTvJ0lYPZhzOu07JyXpfFl04V1290jkRKjaqYZOol6EGfc";

    public static final String PREF = "PREFERENCES";

    public static final String API_SUCCESS_CODE = "000000";
    public static final String PIN = "123456";
    public static final String VENDOR_WALLET_CODE = "5271772269521760491";

    public static final String CONSUMER_KEY = "BNJcXtG7De1EFRssRGN2y52gbD8a";
    public static final String CONSUMER_SECRET = "EzZ3fBbMeYy40CnxwSCJBJIVnLoa";
    public static final String AUTHORIZATION_HEADER = "Basic Qk5KY1h0RzdEZTFFRlJzc1JHTjJ5NTJnYkQ4YTpFelozZkJiTWVZeTQwQ254d1NDSkJKSVZuTG9h";


    // Can be static for a given device
    public static final String QUICKSTART_DEVICE_ID = "228030";
    public static final String WALLET_CODE = "5419679318371569495";
    public static final String MINIMUM_AMOUNT = "100000";
    public static final String ADD_AMOUNT = API_BASE_URL + "wallet/AddValue";

    public static final String TRANSFER_REASON = "Transfer";
    public static final String PURCHASE_REASON = "Purchase";

    public static final String IS_LOGIN = "ISLOGIN";
    public static final String TOKEN = "TOKEN";

    public static final String GET_OFFER_URL = "http://ec2-34-207-116-140.compute-1.amazonaws.com:9095/api/sendMobileTransaction";
    private Constants() {}
}
