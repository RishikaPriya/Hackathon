package com.example.rishikapriya.barclaycard.Utils;

import android.util.Log;

/**
 * Created by rishikapriya on 10/11/17.
 */

public class CommonUtils {

    private static final String API_URL = "";

    public static void debug(String tag , String message){
        Log.d(tag,message);
    }

    public static void error(String tag, String message){
        Log.e(tag,message);
    }

    public static boolean checkNullOrEmpty(String string) {
        return string != null && !string.isEmpty();
    }
}
