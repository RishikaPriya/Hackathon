package com.example.rishikapriya.barclaycard.deals;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.rishikapriya.barclaycard.R;
import com.example.rishikapriya.barclaycard.flight.FlightFragment;


/**
 * Created by rishikapriya on 17/11/17.
 */

public class MyOffersFragment extends Fragment {
    private Activity context;
    public static Fragment newInstance(Activity context) {
        MyOffersFragment fragment =  new MyOffersFragment();
        fragment.context = context;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.offers_layout,null,false);
        RelativeLayout offersLayout = view.findViewById(R.id.rl_offers);
        RelativeLayout fundTransferLayout = view.findViewById(R.id.rl_fund_transfer);
        RelativeLayout flightLayout = view.findViewById(R.id.rl_flight);
        offersLayout.setOnClickListener(offerListener);
        fundTransferLayout.setOnClickListener(fundListener);
        flightLayout.setOnClickListener(flightListenser);
        return view;
    }

    private View.OnClickListener offerListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,ProductsFragment.getInstance(context, null),ProductsFragment.class.toString()).commit();
        }
    };

    private View.OnClickListener fundListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,FundTransferFragment.getInstance(),FundTransferFragment.class.toString()).commit();
        }
    };

    private View.OnClickListener flightListenser = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, FlightFragment.getInstance(),FlightFragment.class.toString()).commit();
        }
    };

}
