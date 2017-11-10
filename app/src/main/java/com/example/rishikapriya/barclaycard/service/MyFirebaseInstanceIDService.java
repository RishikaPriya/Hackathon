package com.example.rishikapriya.barclaycard.service;


import com.example.rishikapriya.barclaycard.Utils.CommonUtils;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by rishikapriya on 10/11/17.
 */

//TOKEN: fLg5161JP3Q:APA91bHbqMcnpdY5EBbha3Mrq2gIix8dKo62YPlvwt2zkutYtuw-Iq78-g-ET2V8hXmT0-GuC-ypuhWPkn4B1VlD5rA2K5rOY-532d9r0buFJa9WxYl4xj1SyuJK3EBaehkLNpgd2U_R

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = MyFirebaseInstanceIDService.class.getName();

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        CommonUtils.debug(TAG,":TOKEN:"+refreshedToken);

    }
}
