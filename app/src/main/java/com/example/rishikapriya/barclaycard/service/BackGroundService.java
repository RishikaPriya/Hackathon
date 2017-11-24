package com.example.rishikapriya.barclaycard.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.rishikapriya.barclaycard.model.Item;

import java.util.List;

/**
 * Created by rishikapriya on 24/11/17.
 */

public class BackGroundService extends Service{

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //callAmazon();

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void callAmazon(List<Item> items){

    }

}
