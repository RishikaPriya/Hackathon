package com.example.rishikapriya.barclaycard.flight;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.rishikapriya.barclaycard.R;
import com.example.rishikapriya.barclaycard.Utils.CommonUtils;
import com.example.rishikapriya.barclaycard.deals.FundTransferFragment;
import com.example.rishikapriya.barclaycard.wallets.SelectWalletFragment;

import static com.example.rishikapriya.barclaycard.constants.Constants.TRANSFER_REASON;
import static com.example.rishikapriya.barclaycard.constants.Constants.VENDOR_WALLET_CODE;

/**
 * Created by arnavaagrawal on 24/11/17.
 */

public class FlightFragment extends Fragment{

    TextInputEditText source, destination, name, date, mobile, email, amount;

    public static Fragment getInstance() {
        return new FlightFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_flight_fragment, null, false);

        source = view.findViewById(R.id.source_value);
        destination = view.findViewById(R.id.destination_value);
        name = view.findViewById(R.id.name);
        date = view.findViewById(R.id.date);
        mobile = view.findViewById(R.id.phone_number);
        email = view.findViewById(R.id.email);
        amount = view.findViewById(R.id.amount_value);

        Button continueTransfer = view.findViewById(R.id.transfer_amount);
        continueTransfer.setOnClickListener(transferListener);

        source.addTextChangedListener(valueChangedListener);
        destination.addTextChangedListener(valueChangedListener);
        name.addTextChangedListener(valueChangedListener);
        date.addTextChangedListener(valueChangedListener);
        mobile.addTextChangedListener(valueChangedListener);
        email.addTextChangedListener(valueChangedListener);

        return view;
    }

    private TextWatcher valueChangedListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(isValid(source, destination, name, date, mobile, email)){
                amount.setText("Rs 11000/-");
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private boolean isValid(TextInputEditText source, TextInputEditText destination, TextInputEditText name,
                            TextInputEditText date, TextInputEditText mobile, TextInputEditText email) {
        return !(CommonUtils.checkNullOrEmpty(source.toString())
                && CommonUtils.checkNullOrEmpty(destination.toString())
                && CommonUtils.checkNullOrEmpty(name.toString())
                && CommonUtils.checkNullOrEmpty(date.toString())
                && CommonUtils.checkNullOrEmpty(mobile.toString())
                && CommonUtils.checkNullOrEmpty(email.toString())
                && CommonUtils.checkNullOrEmpty(mobile.toString()));
    }

    private View.OnClickListener transferListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(isValid(source, destination, name, date, mobile, email)){
                showSelectWalletFragment();
            }
        }
    };

    private void showSelectWalletFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                SelectWalletFragment.getInstance(VENDOR_WALLET_CODE, amount.getText().toString(), TRANSFER_REASON, FlightFragment.class.toString()),
                SelectWalletFragment.class.toString()).commit();
    }
}
