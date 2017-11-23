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
import com.example.rishikapriya.barclaycard.model.Item;
import com.example.rishikapriya.barclaycard.model.StatusResponse;
import com.example.rishikapriya.barclaycard.service.TransferMoneyService;
import com.example.rishikapriya.barclaycard.wallets.SelectWalletFragment;
import com.google.gson.Gson;

import static com.example.rishikapriya.barclaycard.constants.Constants.*;

/**
 * Created by rishikapriya on 22/11/17.
 */

public class SelectAddressFragment extends Fragment {

    private Item item;

    public static SelectAddressFragment getInstance(Item product) {
        SelectAddressFragment fragment = new SelectAddressFragment();
        fragment.item = product;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_address_fragment, null, false);
        Button proceedPaymentButton = view.findViewById(R.id.proceed_payment);

        proceedPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectWalletFragment();
            }
        });
        return view;
    }

    private void showSelectWalletFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                SelectWalletFragment.getInstance(VENDOR_WALLET_CODE, item.getNewPrice(), PURCHASE_REASON, SelectAddressFragment.class.toString()),
                SelectWalletFragment.class.toString()).commit();
    }
}
