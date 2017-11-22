package com.example.rishikapriya.barclaycard.communication;

/**
 * Created by wsyed on 20-11-2017.
 */


import com.example.rishikapriya.barclaycard.service.SignedRequestsHelper;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/*
 * This class shows how to make a simple authenticated call to the
 * Amazon Product Advertising API.
 *
 * See the README.html that came with this sample for instructions on
 * configuring and running the sample.
 */
public class AmazonProductGateway {

    /*
     * Your Access Key ID, as taken from the Your Account page.
     */
    private static final String ACCESS_KEY_ID = "AKIAJ54EAF6RGFL6C5GQ";
   // private static final String ACCESS_KEY_ID = "AKIAIOQ3U6RCJFVVASUA";
    /*
     * Your Secret Key corresponding to the above ID, as taken from the
     * Your Account page.
     */
    private static final String SECRET_KEY = "RssX5RVKZQtjXuG81GYiB2nI2SAiE+9UeUE2XsH0";
    //private static final String SECRET_KEY = "nZagqvzH6ltxnFfrU2mNZ67K5qw+ja706buCk2JY";
    /*
     * Use the end-point according to the region you are interested in.
     */
    private static final String ENDPOINT = "webservices.amazon.in";
    static AmazonProductGateway amazonProductGateway;
    public static AmazonProductGateway getInstance(){
        if(amazonProductGateway == null)
            return  new AmazonProductGateway();
        return amazonProductGateway;
    }
    public  String productLookUp(String itemId) {

        /*
         * Set up the signed requests helper.
         */
        SignedRequestsHelper helper;

        try {
            helper = SignedRequestsHelper.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        String requestUrl = null;

        Map<String, String> params = new HashMap<String, String>();

        params.put("Service", "AWSECommerceService");
        params.put("Operation", "ItemLookup");
        params.put("AWSAccessKeyId", "AKIAJ54EAF6RGFL6C5GQ");
        //params.put("AssociateTag", "hrise-21");
        params.put("AssociateTag", "hrise01-21");
        //params.put("ItemId", "B01BBNF6GM");
        params.put("ItemId",itemId);
        params.put("IdType", "ASIN");
        params.put("ResponseGroup", "Offers");

        requestUrl = helper.sign(params);

        System.out.println("Signed URL: \"" + requestUrl + "\"");
        return requestUrl;
    }

}
