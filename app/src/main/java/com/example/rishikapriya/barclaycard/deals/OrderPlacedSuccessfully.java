package com.example.rishikapriya.barclaycard.deals;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.VolleyError;
import com.example.rishikapriya.barclaycard.R;
import com.example.rishikapriya.barclaycard.communication.WebResponseListener;
import com.example.rishikapriya.barclaycard.model.Account;
import com.example.rishikapriya.barclaycard.service.GetWalletInfoService;
import com.example.rishikapriya.barclaycard.summary.AccountSummaryFragment;

/**
 * Created by rishikapriya on 22/11/17.
 */

public class OrderPlacedSuccessfully extends Fragment {

    public static OrderPlacedSuccessfully getInstance() {
        return new OrderPlacedSuccessfully();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_placed_successfully, null, false);
        Button done = view.findViewById(R.id.done);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHomeScreen();
            }
        });

        getWalletInfo();
        return view;
    }

    private void getWalletInfo() {
        GetWalletInfoService.getWalletInfo(new WebResponseListener() {
            @Override
            public void onReceiveResponse(Object response) {
                System.out.println("Wallet now");
            }

            @Override
            public void onReceiveError(VolleyError volleyError) {

            }
        });
    }

    private void showHomeScreen() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, AccountSummaryFragment.newInstance(new Account()), AccountSummaryFragment.class.toString()).commit();
    }
}
