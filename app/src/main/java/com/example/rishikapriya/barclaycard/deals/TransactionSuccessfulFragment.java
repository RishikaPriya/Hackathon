package com.example.rishikapriya.barclaycard.deals;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.rishikapriya.barclaycard.R;
import com.example.rishikapriya.barclaycard.communication.WebResponseListener;
import com.example.rishikapriya.barclaycard.service.GetWalletInfoService;
import com.example.rishikapriya.barclaycard.wallets.WalletFragment;

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
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, WalletFragment.newInstance(getActivity()),WalletFragment.class.toString()).commit();

    }
}
