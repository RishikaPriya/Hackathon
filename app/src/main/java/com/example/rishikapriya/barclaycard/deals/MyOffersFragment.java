package com.example.rishikapriya.barclaycard.deals;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.rishikapriya.barclaycard.R;


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
        setonClickListenerForOffers(offersLayout);
        return view;
    }

    private void setonClickListenerForOffers(RelativeLayout offersLayout) {
        offersLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,ProductsFragment.getInstance(context, null),ProductsFragment.class.toString()).commit();
                //getActivity().getFragmentManager().beginTransaction().add(ProductsFragment.getInstance(context), "fads").commitAllowingStateLoss();
            }
        });
    }
}
