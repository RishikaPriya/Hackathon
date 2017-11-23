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
import com.google.gson.Gson;

import static com.example.rishikapriya.barclaycard.constants.Constants.API_SUCCESS_CODE;

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
        Button deliverButton = view.findViewById(R.id.proceed_payment);

        deliverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TransferMoneyService.transferAmount(item.getNewPrice(),item.getName(),new WebResponseListener() {
                    @Override
                    public void onReceiveResponse(Object response) {
                        Gson gson = new Gson();
                        StatusResponse statusResponse = gson.fromJson(response.toString(), StatusResponse.class);
                        if(API_SUCCESS_CODE.equals(statusResponse.getStatus())){
                            showPaymentSuccessful();
                        }

                    }

                    @Override
                    public void onReceiveError(VolleyError volleyError) {

                    }
                });
            }
        });
        return view;
    }

    private void showPaymentSuccessful() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,OrderPlacedSuccessfully.getInstance()).commit();
    }
}
