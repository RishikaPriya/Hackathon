package com.example.rishikapriya.barclaycard.deals;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.rishikapriya.barclaycard.R;
import com.example.rishikapriya.barclaycard.Utils.CommonUtils;
import com.example.rishikapriya.barclaycard.wallets.SelectWalletFragment;

import static com.example.rishikapriya.barclaycard.constants.Constants.TRANSFER_REASON;

/**
 * Created by rishikapriya on 23/11/17.
 */

public class FundTransferFragment extends Fragment {

    TextInputEditText account_number, amount;

    public static Fragment getInstance() {
        return new FundTransferFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fund_transfer_layout,null,false);

        account_number = view.findViewById(R.id.account_number);
        amount = view.findViewById(R.id.amount);
        Button continueTransfer = view.findViewById(R.id.transfer_amount);
        continueTransfer.setOnClickListener(transferListener);
        return view;
    }

    private boolean isValid(EditText account_number, EditText amount) {
        return CommonUtils.checkNullOrEmpty(account_number.toString()) && CommonUtils.checkNullOrEmpty(amount.toString());
    }

    private View.OnClickListener transferListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(isValid(account_number, amount)){
                showSelectWalletFragment();
            }
        }
    };

    private void showSelectWalletFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                SelectWalletFragment.getInstance(account_number.getText().toString(), amount.getText().toString(), TRANSFER_REASON, FundTransferFragment.class.toString()),
                SelectWalletFragment.class.toString()).commit();
    }
}
