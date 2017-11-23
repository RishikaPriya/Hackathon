package com.example.rishikapriya.barclaycard.wallets;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.rishikapriya.barclaycard.R;
import com.example.rishikapriya.barclaycard.communication.WebResponseListener;
import com.example.rishikapriya.barclaycard.deals.TransactionSuccessfulFragment;
import com.example.rishikapriya.barclaycard.model.StatusResponse;
import com.example.rishikapriya.barclaycard.model.WalletInfo;
import com.example.rishikapriya.barclaycard.model.WalletListResponse;
import com.example.rishikapriya.barclaycard.service.GetWalletListService;
import com.example.rishikapriya.barclaycard.service.TransferMoneyService;
import com.google.gson.Gson;

import java.util.List;

import static com.example.rishikapriya.barclaycard.constants.Constants.API_SUCCESS_CODE;

/**
 * Created by rishikapriya on 23/11/17.
 */

public class SelectWalletFragment extends Fragment {

    private ListView listView;
    private String fromWallet;
    private String toWallet;
    private String amount;
    private String reason;
    private String fromFragment;

    public static Fragment getInstance(String toWallet, String amount, String reason, String fromFragment) {
        SelectWalletFragment selectWalletFragment = new SelectWalletFragment();
        selectWalletFragment.toWallet = toWallet;
        selectWalletFragment.amount = amount;
        selectWalletFragment.reason = reason;
        selectWalletFragment.fromFragment = fromFragment;
        return selectWalletFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_wallet_fragment,null,false);
        listView = view.findViewById(R.id.list_view);

        Button transferButton = view.findViewById(R.id.transfer_amount);
        transferButton.setOnClickListener(transferListener);
        getWalletList();
        return view;
    }

    private View.OnClickListener transferListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TransferMoneyService.transferAmount(fromWallet, toWallet, amount, reason,new WebResponseListener() {
                @Override
                public void onReceiveResponse(Object response) {
                    Gson gson = new Gson();
                    StatusResponse statusResponse = gson.fromJson(response.toString(), StatusResponse.class);
                    if(API_SUCCESS_CODE.equals(statusResponse.getStatus())){
                            if(fromFragment.contains("FundTransferFragment")){
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                                        TransactionSuccessfulFragment.getInstance(getString(R.string.transaction_successful), getString(R.string.transaction_successful_message))).commit();
                            }
                            else if(fromFragment.contains("SelectAddressFragment")){
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                                            TransactionSuccessfulFragment.getInstance(getString(R.string.payment_successful), getString(R.string.order_placed))).commit();
                            }
                    }
                }
                @Override
                public void onReceiveError(VolleyError volleyError) {

                }
            });
        }
    };

    private void getWalletList() {
        GetWalletListService.getWalletList(new WebResponseListener() {
            @Override
            public void onReceiveResponse(Object response) {
                Gson gson =  new Gson();
                WalletListResponse walletListResponse = gson.fromJson(response.toString(), WalletListResponse.class);
                setListAdapter(walletListResponse.getWalletListInfo());
            }

            @Override
            public void onReceiveError(VolleyError volleyError) {

            }
        });

    }

    private void setListAdapter(List<WalletInfo> walletListInfo) {
        listView.setAdapter(new SelectWalletAdapter(walletListInfo, getActivity(), 0));
    }


    public class SelectWalletAdapter extends BaseAdapter {

        private final LayoutInflater inflater;
        private List<WalletInfo> walletList;
        private Context context;
        private int selectedItem;

        private SelectWalletAdapter(List<WalletInfo> list, Context context, int selectedItem){

            this.walletList = list;
            this.inflater = LayoutInflater.from(context);
            this.context = context;
            this.selectedItem = selectedItem;
        }

        @Override
        public int getCount() {
            return walletList.size();
        }

        @Override
        public Object getItem(int position) {
            return walletList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final SelectWalletFragment.SelectWalletAdapter.ViewHolder holder;
            if(convertView == null){
                convertView =  inflater.inflate(R.layout.selectwalletlist,null);
                holder = new SelectWalletFragment.SelectWalletAdapter.ViewHolder();
                holder.radioButton = convertView.findViewById(R.id.select_wallet);
                holder.cardName = convertView.findViewById(R.id.wallet_name);
                holder.currentBalance = convertView.findViewById(R.id.current_balance);
                convertView.setTag(holder);
            }else{
                holder = (SelectWalletFragment.SelectWalletAdapter.ViewHolder) convertView.getTag();
            }

            final WalletInfo walletInfo = walletList.get(position);
            holder.cardName.setText(walletInfo.getWalletName());
            holder.currentBalance.setText(walletInfo.getCurrentBalance());

            if(position==selectedItem){
                holder.radioButton.setChecked(true);
                fromWallet = walletList.get(position).getWalletCode();
            } else {
                holder.radioButton.setChecked(false);
            }

            View.OnClickListener radioButtonListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedItem = position;
                    SelectWalletAdapter.this.notifyDataSetChanged();
                }
            };

            convertView.setOnClickListener(radioButtonListener);
            holder.radioButton.setOnClickListener(radioButtonListener);

            return convertView;
        }

        class ViewHolder {
            RadioButton radioButton;
            TextView cardName;
            TextView currentBalance;
        }
    }
}

