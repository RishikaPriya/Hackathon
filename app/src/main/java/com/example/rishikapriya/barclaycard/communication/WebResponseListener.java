package com.example.rishikapriya.barclaycard.communication;

import com.android.volley.VolleyError;

/**
 * Created by rishikapriya on 14/11/17.
 */

public interface WebResponseListener<T> {

    public void onReceiveResponse(T response);

    public void onReceiveError(VolleyError volleyError);
}
