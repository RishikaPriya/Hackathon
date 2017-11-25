package com.example.rishikapriya.barclaycard.notification;


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

import com.example.rishikapriya.barclaycard.R;
import com.example.rishikapriya.barclaycard.database.DatabaseHandler;
import com.example.rishikapriya.barclaycard.wallets.TransactionsFragment;
import com.example.rishikapriya.barclaycard.wallets.WalletFragment;

import java.util.List;

/**
 * Created by rishikapriya on 25/11/17.
 */

public class NotificationFragment extends Fragment {

    private ListView listView;
    private DatabaseHandler databaseHandler;

    public static Fragment getInstance() {
        return new NotificationFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notification, null, false);
        databaseHandler = new DatabaseHandler(getContext());
        listView = view.findViewById(R.id.list_view);
        getNotificationCategory();
        return view;
    }

    private void getNotificationCategory() {
        List<String> categoryList = databaseHandler.getDistinctCategory();
        listView.setAdapter(new CategoryAdapter(categoryList, getContext(), 0));
    }


    public class CategoryAdapter extends BaseAdapter {

        private final LayoutInflater inflater;
        private List<String> categoryList;
        private Context context;
        private int selectedItem;

        private CategoryAdapter(List<String> list, Context context, int selectedItem){

            this.categoryList = list;
            this.inflater = LayoutInflater.from(context);
            this.selectedItem = selectedItem;
            this.context = context;
        }

        @Override
        public int getCount() {
            return categoryList.size();
        }

        @Override
        public Object getItem(int position) {
            return categoryList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final NotificationFragment.CategoryAdapter.ViewHolder holder;
            if(convertView == null){
                convertView =  inflater.inflate(R.layout.category_list,null);
                holder = new NotificationFragment.CategoryAdapter.ViewHolder();
                holder.category_title = convertView.findViewById(R.id.category_title);
                convertView.setTag(holder);
            }else{
                holder = (NotificationFragment.CategoryAdapter.ViewHolder) convertView.getTag();
            }

            holder.category_title.setText(categoryList.get(position));

            View.OnClickListener categoryClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedItem = position;
                    NotificationFragment.CategoryAdapter.this.notifyDataSetChanged();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, ShowNotificationFragment.newInstance(categoryList.get(selectedItem)),ShowNotificationFragment.class.toString()).commit();
                }
            };

            convertView.setOnClickListener(categoryClickListener);
            return convertView;
        }

        class ViewHolder {
            TextView category_title;
        }
    }

}
