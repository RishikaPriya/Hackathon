package com.example.rishikapriya.barclaycard.summary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.example.rishikapriya.barclaycard.R;
import com.example.rishikapriya.barclaycard.communication.WebResponseListener;
import com.example.rishikapriya.barclaycard.model.Account;
import com.example.rishikapriya.barclaycard.model.WalletInfoResponse;
import com.example.rishikapriya.barclaycard.service.GetWalletInfoService;
import com.google.gson.Gson;

/**
 * Created by rishikapriya on 17/11/17.
 */

public class AccountSummaryFragment extends Fragment {

    private Account account ;

    public static AccountSummaryFragment newInstance(Account account) {

        Bundle args = new Bundle();

        AccountSummaryFragment fragment = new AccountSummaryFragment();
        args.putSerializable("account",account);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg = getArguments();

        account = (Account) arg.getSerializable("account");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_summary, null, false);

        getWalletInfo();
        return view;
    }

    private void getWalletInfo() {
        GetWalletInfoService.getWalletInfo(new WebResponseListener() {
            @Override
            public void onReceiveResponse(Object response) {
                Gson gson =  new Gson();
                WalletInfoResponse walletInfoResponse = gson.fromJson(response.toString(), WalletInfoResponse.class);
                System.out.println(walletInfoResponse.getWalletInfo().getCurrentBalance());
                setFields();
            }

            @Override
            public void onReceiveError(VolleyError volleyError) {

            }
        });
    }

    private void setFields() {
    }
}
