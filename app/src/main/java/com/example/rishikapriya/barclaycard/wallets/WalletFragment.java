package com.example.rishikapriya.barclaycard.wallets;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.rishikapriya.barclaycard.R;
import com.example.rishikapriya.barclaycard.Security.Security;
import com.example.rishikapriya.barclaycard.communication.WebResponseListener;
import com.example.rishikapriya.barclaycard.model.WalletInfo;
import com.example.rishikapriya.barclaycard.model.WalletListResponse;
import com.example.rishikapriya.barclaycard.service.GetWalletListService;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by rishikapriya on 23/11/17.
 */

public class WalletFragment extends Fragment{

    private Activity context;
    private ListView listView;

    public static Fragment newInstance(Activity context) {
        WalletFragment fragment =  new WalletFragment();
        fragment.context = context;
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wallet_list_fragment_layout,null,false);

        listView = view.findViewById(R.id.list_view);
        getWalletList();
        return view;
    }

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
        listView.setAdapter(new WalletAdapter(walletListInfo, getActivity()));
    }


    public class WalletAdapter extends BaseAdapter {

        private final LayoutInflater inflater;
        private List<WalletInfo> walletList;
        private Context context;

        private WalletAdapter(List<WalletInfo> list, Context context){

            this.walletList = list;
            this.inflater = LayoutInflater.from(context);
            this.context = context;
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
            final WalletFragment.WalletAdapter.ViewHolder holder;
            if(convertView == null){
                convertView =  inflater.inflate(R.layout.walletlist,null);
                holder = new WalletFragment.WalletAdapter.ViewHolder();
                holder.cardName = convertView.findViewById(R.id.card_name);
                holder.currentBalance = convertView.findViewById(R.id.current_balance);
                convertView.setTag(holder);
            }else{
                holder = (WalletFragment.WalletAdapter.ViewHolder) convertView.getTag();
            }

            holder.cardName.setText(walletList.get(position).getWalletName());
            holder.currentBalance.setText(walletList.get(position).getCurrentBalance());

            return convertView;
        }

        class ViewHolder {
            TextView cardName;
            TextView currentBalance;
        }
    }
}
