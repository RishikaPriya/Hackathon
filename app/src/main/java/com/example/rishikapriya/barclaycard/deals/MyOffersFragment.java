package com.example.rishikapriya.barclaycard.deals;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rishikapriya.barclaycard.R;


/**
 * Created by rishikapriya on 17/11/17.
 */

public class MyOffersFragment extends Fragment {
    public static Fragment newInstance() {
        return new MyOffersFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.offers_layout,null,false);
        return view;
    }
}
