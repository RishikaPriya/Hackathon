package com.example.rishikapriya.barclaycard.service;


import com.example.rishikapriya.barclaycard.Utils.CommonUtils;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by rishikapriya on 10/11/17.
 */

//TOKEN: euYYSP0qG8o:APA91bFvoA-HGVXtQO4As8QcT8D5uSXyyqoixwEQLGbCgKJem318QpCEvy9QU42-Dbd9nHWvySB-QIdYiH_MUjLrPVeBwTbW85N41eLtEYdGZ3HZ58Ky8B_whj921U1wtgEjMttyfmSM

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = MyFirebaseInstanceIDService.class.getName();

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        CommonUtils.debug(TAG,":TOKEN:"+refreshedToken);

    }
}
