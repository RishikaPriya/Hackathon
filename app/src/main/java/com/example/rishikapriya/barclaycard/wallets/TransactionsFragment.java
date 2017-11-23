package com.example.rishikapriya.barclaycard.wallets;


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
import com.example.rishikapriya.barclaycard.communication.WebResponseListener;
import com.example.rishikapriya.barclaycard.model.TransactionInfo;
import com.example.rishikapriya.barclaycard.model.TransactionListResponse;
import com.example.rishikapriya.barclaycard.service.GetTransactionService;
import com.google.gson.Gson;

import java.util.List;

import static com.example.rishikapriya.barclaycard.Utils.CommonUtils.formatDate;

/**
 * Created by rishikapriya on 24/11/17.
 */

public class TransactionsFragment extends Fragment {

    private ListView listView;

    public static Fragment newInstance() {
        return new TransactionsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transactions, null, false);
        listView = view.findViewById(R.id.list_view);
        getTransactionList();
        return view;
    }

    private void getTransactionList() {
        GetTransactionService.getTransactions(new WebResponseListener() {
            @Override
            public void onReceiveResponse(Object response) {
                Gson gson = new Gson();
                TransactionListResponse transactionListResponse = gson.fromJson(response.toString(), TransactionListResponse.class);
                setListAdapter(transactionListResponse.getTransactionListInfo());
            }

            @Override
            public void onReceiveError(VolleyError volleyError) {

            }
        });

    }

    private void setListAdapter(List<TransactionInfo> transactionListInfo) {
        listView.setAdapter(new TransactionListAdapter(transactionListInfo, getActivity()));
    }

    public class TransactionListAdapter extends BaseAdapter {

        private final LayoutInflater inflater;
        private List<TransactionInfo> transactionInfo;
        private Context context;

        private TransactionListAdapter(List<TransactionInfo> list, Context context){

            this.transactionInfo = list;
            this.inflater = LayoutInflater.from(context);
            this.context = context;
        }

        @Override
        public int getCount() {
            return transactionInfo.size();
        }

        @Override
        public Object getItem(int position) {
            return transactionInfo.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final TransactionsFragment.TransactionListAdapter.ViewHolder holder;
            if(convertView == null){
                convertView =  inflater.inflate(R.layout.transaction_list,null);
                holder = new TransactionsFragment.TransactionListAdapter.ViewHolder();

                holder.closingBalance = convertView.findViewById(R.id.closing_balance_value);
                holder.description = convertView.findViewById(R.id.transaction_desc_value);
                holder.endDate = convertView.findViewById(R.id.value_date_value);
                holder.startDate = convertView.findViewById(R.id.date_value);
                holder.referenceNo = convertView.findViewById(R.id.reference_code_value);
                holder.transactionAmount = convertView.findViewById(R.id.withdraw_value);

                convertView.setTag(holder);
            }else{
                holder = (TransactionsFragment.TransactionListAdapter.ViewHolder) convertView.getTag();
            }

            holder.startDate.setText(formatDate(transactionInfo.get(position).getStartDate()));
            holder.endDate.setText(formatDate(transactionInfo.get(position).getEndDate()));
            holder.referenceNo.setText(transactionInfo.get(position).getReferenceNumber());
            holder.transactionAmount.setText(transactionInfo.get(position).getTransaction_amount());
            holder.description.setText(transactionInfo.get(position).getDescription());
            holder.closingBalance.setText(transactionInfo.get(position).getClosing_balance());

            return convertView;
        }


        class ViewHolder {
            TextView startDate;
            TextView endDate;
            TextView description;
            TextView transactionAmount;
            TextView closingBalance;
            TextView referenceNo;
        }
    }
}
