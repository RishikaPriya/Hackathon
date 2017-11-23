package com.example.rishikapriya.barclaycard.Utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rishikapriya on 10/11/17.
 */

public class CommonUtils {

    public static void debug(String tag , String message){
        Log.d(tag,message);
    }

    public static void error(String tag, String message){
        Log.e(tag,message);
    }

    public static boolean checkNullOrEmpty(String string) {
        return string != null && !string.isEmpty();
    }

    public static String getCurrentDate(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String formatDate(String input_date) {
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        DateFormat dateParse = new SimpleDateFormat("yyyy-MM-dd");
        String date = "";
        try {
            Date startDate = dateParse.parse(input_date.substring(0, 10));
            date = dateFormat.format(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


}
