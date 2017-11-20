package com.example.rishikapriya.barclaycard.communication;

import android.test.mock.MockApplication;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.rishikapriya.barclaycard.ApplicationController;
import com.example.rishikapriya.barclaycard.Utils.CommonUtils;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by rishikapriya on 14/11/17.
 */

public class ServerCommunication {

    private static final String TAG = ServerCommunication.class.getName();
    private static ServerCommunication mInstance;
    private static String authorizationHeader;
    private static String deviceId;

    public static ServerCommunication getmInstance(){
        if(mInstance == null){
            mInstance = new ServerCommunication();
        }
        return mInstance;
    }

    public void addJSONPostRequest(String url, final Map<String,String> headers, final Map<String,String> request,
                                   final WebResponseListener webResponseListener){

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(
                        Request.Method.POST,
                        url,null,
                        new ResponseListener(webResponseListener),
                        new ErrorListener(webResponseListener)){

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        return headers;
                    }

                    @Override
                    public byte[] getBody() {


                        String httpPostBody=getStringFromRequest(request);
                        // usually you'd have a field with some values you'd want to escape, you need to do it yourself if overriding getBody. here's how you do it
                        try {
                            httpPostBody=httpPostBody+"&randomFieldFilledWithAwkwardCharacters="+
                                    URLEncoder.encode("{{%stuffToBe Escaped/","UTF-8");
                        } catch (UnsupportedEncodingException exception) {
                            CommonUtils.error("ERROR", "exception" + exception);
                            // return null and don't pass any POST string if you encounter encoding error
                            return null;
                        }
                        return httpPostBody.getBytes();
                    }


                };

        ApplicationController.getInstance().getRequestQueue().add(jsonObjectRequest);
    }

    public void addJSONPostRequestWithParameters(String url, final Map<String,String> headers, final Map<String,String> request,
                                   final WebResponseListener webResponseListener){

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(
                        Request.Method.POST,
                        url,new JSONObject(request),
                        new ResponseListener(webResponseListener),
                        new ErrorListener(webResponseListener)){

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        return headers;
                    }

                };

        ApplicationController.getInstance().getRequestQueue().add(jsonObjectRequest);
    }

    private String getStringFromRequest(Map<String, String> request) {
        Iterator iterator = request.entrySet().iterator();
        String request_string = "";

        while (iterator.hasNext()){
            Map.Entry request_item = (Map.Entry) iterator.next();
            request_string += request_item.getKey() + "=" + request_item.getValue() + "&";
        }
        return request_string.substring(0, request_string.length()-1);
    }

    public void addJSONGetRequest(String url, final Map<String, String> requestParameters,
                                   final WebResponseListener<JSONObject> webResponseListener){

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(
                        Request.Method.GET,
                        url,null,
                        new ResponseListener<>(webResponseListener),
                        new ErrorListener(webResponseListener)){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> headers =  new HashMap<>();
                        headers.put("Authorization",authorizationHeader);
                        headers.put("DeviceId",deviceId);
                        return headers;
                    }

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        return requestParameters;
                    }

                    @Override
                    public byte[] getBody() {
                        String httpPostBody="grant_type=client_credentials&scope=PRODUCTION";
                        // usually you'd have a field with some values you'd want to escape, you need to do it yourself if overriding getBody. here's how you do it
                        try {
                            httpPostBody=httpPostBody+"&randomFieldFilledWithAwkwardCharacters="+
                                    URLEncoder.encode("{{%stuffToBe Escaped/","UTF-8");
                        } catch (UnsupportedEncodingException exception) {
                            CommonUtils.error("ERROR", "exception" + exception);
                            // return null and don't pass any POST string if you encounter encoding error
                            return null;
                        }
                        return httpPostBody.getBytes();
                    }
                };

        ApplicationController.getInstance().getRequestQueue().add(jsonObjectRequest);
    }

    public void setAuthorizationHeader(String authorizationHeader) {
        this.authorizationHeader = authorizationHeader;
    }

    public void setDeviceID(String deviceId){
        this.deviceId = deviceId;
    }


    private class ResponseListener<T> implements Response.Listener<T> {

        WebResponseListener<T> webResponseListener;

        ResponseListener(WebResponseListener<T> webResponseListener){
            super();
            this.webResponseListener = webResponseListener;
        }
        @Override
        public void onResponse(T t) {
            CommonUtils.debug(TAG+":RESPONSE:",t.toString());
            webResponseListener.onReceiveResponse(t);
        }
    }

    private class ErrorListener implements Response.ErrorListener {

        WebResponseListener webResponseListener;

        ErrorListener(WebResponseListener webResponseListener){
            super();
            this.webResponseListener = webResponseListener;
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            CommonUtils.error(TAG+":ERRRO:","ERROR");
            webResponseListener.onReceiveError(error);
        }
    }

}
