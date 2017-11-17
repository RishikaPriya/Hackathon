package com.example.rishikapriya.barclaycard.summary;

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

public class AccountSummaryFragment extends Fragment {

    private Account account ;

    public static AccountSummaryFragment newInstance(Account account) {

        Bundle args = new Bundle();

        AccountSummaryFragment fragment = new AccountSummaryFragment();
        args.putSerializable("account",account);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_summary, null, false);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg = getArguments();

        account = (Account) arg.getSerializable("account");

    }
}
