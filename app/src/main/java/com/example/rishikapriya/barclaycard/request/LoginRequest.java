package com.example.rishikapriya.barclaycard.request;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by rishikapriya on 14/11/17.
 */

public class LoginRequest extends JsonObjectRequest {

    public LoginRequest(int method, String url, JSONObject jsonRequest,
                        Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    public LoginRequest(String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener,
                        Response.ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
    }




    /*final String URL = "/volley/resource/12";
    // Post params to be sent to the server
    HashMap<String, String> params = new HashMap<String, String>();
    params.put("token", "AbCdEfGh123456");

    JsonObjectRequest req = new JsonObjectRequest(URL, new JSONObject(params),
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        VolleyLog.v("Response:%n %s", response.toString(4));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            VolleyLog.e("Error: ", error.getMessage());
        }
    });

    // add the request object to the queue to be executed
    ApplicationController.getInstance().addToRequestQueue(req);*/
}
