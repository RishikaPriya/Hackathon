package com.example.rishikapriya.barclaycard.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.rishikapriya.barclaycard.R;
import com.example.rishikapriya.barclaycard.Utils.CommonUtils;
import com.example.rishikapriya.barclaycard.communication.AmazonProductGateway;
import com.example.rishikapriya.barclaycard.communication.ServerCommunication;
import com.example.rishikapriya.barclaycard.communication.WebResponseListener;
import com.example.rishikapriya.barclaycard.model.Item;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by rishikapriya on 24/11/17.
 */

public class AlarmReceiver extends BroadcastReceiver {
    private Map<String,Item> itemPriceMap, itemAsinMap;
    private static final String ASIN_SURF = "B00TS88UZW";
    private static final String ASIN_LAKME = "B01BBNF6GM";
    private static final String ASIN_OIL = "B010GGD02C";
    private static final String ASIN_DEO = "B007E9HPLC";
    private static final String ASIN_FACEWASH = "B007921JYI";
    private static final String ASIN_RICE = "B00SWKBO0K";
    private List<Item> list =  new ArrayList<>();
    int count;

    @Override
    public void onReceive(Context context, Intent intent1) {
        Log.d("AAYA","Alarm");

        CommonUtils.setAlarm(context,Calendar.SECOND,30);

        createMapOfItems();

    }
    private void createMapOfItems() {

        itemAsinMap = new HashMap<>();

        itemAsinMap.put(ASIN_SURF, new Item("Surf Excel", "500.00", "1", ASIN_SURF, R.drawable.ic_surf));
        itemAsinMap.put(ASIN_LAKME, new Item("Lakme", "299.00", "1", ASIN_LAKME, R.drawable.ic_lakme));
        itemAsinMap.put(ASIN_RICE, new Item("Rice", "810.00", "1", ASIN_RICE, R.drawable.ic_rice));
        itemAsinMap.put(ASIN_DEO, new Item("Deo", "250.00", "1", ASIN_DEO, R.drawable.ic_deo));
        itemAsinMap.put(ASIN_OIL, new Item("Oil", "950.00", "1", ASIN_OIL, R.drawable.ic_olive_oil));
        itemAsinMap.put(ASIN_FACEWASH, new Item("Face Wash", "220.00", "1", ASIN_FACEWASH, R.drawable.ic_facewash));

        count = itemAsinMap.size();

        getAmazonPrice(ASIN_SURF);
        getAmazonPrice(ASIN_LAKME);
        getAmazonPrice(ASIN_RICE);
        getAmazonPrice(ASIN_DEO);
        getAmazonPrice(ASIN_OIL);
        getAmazonPrice(ASIN_FACEWASH);
    }

    private void getAmazonPrice(String id) {
        ServerCommunication.getmInstance().stringGETRequest(AmazonProductGateway.getInstance().productLookUp(id), new WebResponseListener<String>() {
            @Override
            public void onReceiveResponse(String response) {
                Log.v("RESPONSE",response);
                count--;
                parseResponse(response);
            }

            @Override
            public void onReceiveError(VolleyError volleyError) {
                count--;
            }
        });

    }

    private void parseResponse(String response) {


        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document document = null;
        try {
            document = builder.parse(new InputSource(new StringReader(response)));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Element rootElement = document.getDocumentElement();

        String id = rootElement.getElementsByTagName("ItemId").item(0).getTextContent();

        String price = rootElement.getElementsByTagName("LowestNewPrice").item(0).getChildNodes().item(0).getTextContent();

        itemAsinMap.get(id).setNewPrice(new StringBuffer(price).insert(price.length()-2, ".").toString());

        list.add(itemAsinMap.get(id));
        sendNotification();

    }

    private void sendNotification() {
        if(list.size()!=0 && count == 0){
            try {
                JSONObject jsonObject = new JSONObject();
                JSONObject data = new JSONObject();
                for (int i = 0; i < list.size(); i++) {
                    Item item = list.get(i);
                    data.put(item.getName(), item.getBoughtPrice() + "," + item.getNewPrice() +"," + item.getImageId());
                }
                jsonObject.put("data",data);
                jsonObject.put("to", FirebaseInstanceId.getInstance().getToken());
                System.out.println(jsonObject.toString());
                NotificationService.sendNotification(jsonObject, new WebResponseListener() {
                    @Override
                    public void onReceiveResponse(Object response) {

                    }

                    @Override
                    public void onReceiveError(VolleyError volleyError) {

                    }
                });
            }catch (JSONException e){

            }
        }
    }


}
