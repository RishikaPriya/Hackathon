package com.example.rishikapriya.barclaycard.deals;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.rishikapriya.barclaycard.MainActivity;
import com.example.rishikapriya.barclaycard.R;
import com.example.rishikapriya.barclaycard.communication.WebResponseListener;
import com.example.rishikapriya.barclaycard.service.CallGetOfferService;
import com.example.rishikapriya.barclaycard.service.GetWalletInfoService;
import com.example.rishikapriya.barclaycard.service.MyFirebaseMessagingService;
import com.example.rishikapriya.barclaycard.wallets.WalletFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by rishikapriya on 22/11/17.
 */

public class TransactionSuccessfulFragment extends Fragment {

    private String header;
    private String message;

    public static TransactionSuccessfulFragment getInstance(String header, String message) {
        TransactionSuccessfulFragment transactionSuccessfulFragment = new TransactionSuccessfulFragment();
        transactionSuccessfulFragment.header = header;
        transactionSuccessfulFragment.message = message;
        return transactionSuccessfulFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_placed_successfully, null, false);
        TextView headerView = view.findViewById(R.id.header);
        TextView messageView = view.findViewById(R.id.message);
        Button done = view.findViewById(R.id.done);

        headerView.setText(header);
        messageView.setText(message);

        if(message.equals(getString(R.string.flight_booked))){
            CallGetOfferService.getOffer(new WebResponseListener() {
                @Override
                public void onReceiveResponse(Object response) {
                    try {
                        JSONObject object = new JSONObject(response.toString());
                        if(object != null) {
                            Map<String, String> message = new HashMap<>();
                            message.put("title", object.getString("title"));
                            message.put("description", object.getString("desc"));
                            message.put("link", object.getString("link"));
                            message.put("validity_date", object.getString("validaty_date"));
                            message.put("mcc", object.getString("category"));

                            NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);

                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            setIntentExtrasForData(message, intent);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                            NotificationCompat.Builder notification = new NotificationCompat.Builder(getActivity(), "PersonalizedData")
                                    .setContentTitle(message.get("title"))
                                    .setContentText(message.get("description"))
                                    .setSmallIcon(R.mipmap.ic_notification)
                                    .setAutoCancel(true)
                                    .setContentIntent(pendingIntent);


                            notificationManager.notify(0, notification.build());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onReceiveError(VolleyError volleyError) {

                }
            });

        }

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHomeScreen();
            }
        });

        return view;
    }

    private void setIntentExtrasForData(Map<String, String> data, Intent intent) {
        intent.putExtra("fragment", "PersonalizedData");
        intent.putExtra("title", data.get("title"));
        intent.putExtra("description", data.get("description"));
        intent.putExtra("link", data.get("link"));
        intent.putExtra("validity_date", data.get("validity_date"));
        intent.putExtra("category", data.get("mcc"));
    }

    private void showHomeScreen() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, WalletFragment.newInstance(getActivity()),WalletFragment.class.toString()).commit();

    }
}
