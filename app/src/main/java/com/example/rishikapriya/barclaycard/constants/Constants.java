package com.example.rishikapriya.barclaycard.constants;

/**
 * Created by rishikapriya on 14/11/17.
 */

public final class Constants {
    public static final String API_ENDPOINT = "https://api.risemarketplace.net/";

    public static final String APP_TOKEN = "https://api.risemarketplace.net/token";

    public static final String API_BASE_URL =  API_ENDPOINT + "rise/v1.0.0/api/";

    public static final String CREATE_WALLET = API_BASE_URL + "wallet/CreateWallet";
    public static final String API_SUCCESS_CODE = "000000";

    public static final String CONSUMER_KEY = "BNJcXtG7De1EFRssRGN2y52gbD8a";
    public static final String CONSUMER_SECRET = "EzZ3fBbMeYy40CnxwSCJBJIVnLoa";
    public static final String AUTHORIZATION_HEADER = "Basic Qk5KY1h0RzdEZTFFRlJzc1JHTjJ5NTJnYkQ4YTpFelozZkJiTWVZeTQwQ254d1NDSkJKSVZuTG9h";


    // Can be static for a given device
    public static final String QUICKSTART_DEVICE_ID = "228030";

    public static final String MINIMUM_AMOUNT = "100000";
    public static final String ADD_AMOUNT = API_BASE_URL + "wallet/AddValue";


    private Constants() {}
}
