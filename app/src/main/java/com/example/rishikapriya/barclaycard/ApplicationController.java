package com.example.rishikapriya.barclaycard;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;

/**
 * Created by rishikapriya on 07/11/17.
 */

public class ApplicationController extends Application {

    private static ApplicationController mApplicationController;
    RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationController = this;
    }

    public static final ApplicationController getInstance(){
        return mApplicationController;
    }

    /**
     *
     * @return single instance of Volley.RequestQueue
     */
    public RequestQueue getRequestQueue(){
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
