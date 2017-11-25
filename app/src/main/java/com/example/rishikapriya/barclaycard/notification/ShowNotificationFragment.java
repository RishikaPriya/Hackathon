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
import com.example.rishikapriya.barclaycard.model.Notification;

import java.util.List;

/**
 * Created by rishikapriya on 25/11/17.
 */

public class ShowNotificationFragment extends Fragment{

    private String category;
    private ListView listView;
    private DatabaseHandler databaseHandler;

    public static Fragment newInstance(String category) {
        ShowNotificationFragment showNotificationFragment = new ShowNotificationFragment();
        showNotificationFragment.category = category;
        return showNotificationFragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_notification_fragment, null, false);
        databaseHandler = new DatabaseHandler(getContext());
        TextView header = view.findViewById(R.id.header);
        header.setText(category);
        listView = view.findViewById(R.id.list_view);
        getNotificationByCategory();
        return view;
    }

    private void getNotificationByCategory() {
        List<Notification> notificationList = databaseHandler.getAllNotificationsForCategory(category);
        listView.setAdapter(new NotificationByCategoryAdapter(notificationList, getContext()));
    }

    public class NotificationByCategoryAdapter extends BaseAdapter {

        private final LayoutInflater inflater;
        private List<Notification> notificationList;
        private Context context;

        private NotificationByCategoryAdapter(List<Notification> list, Context context){

            this.notificationList = list;
            this.inflater = LayoutInflater.from(context);
            this.context = context;
        }

        @Override
        public int getCount() {
            return notificationList.size();
        }

        @Override
        public Object getItem(int position) {
            return notificationList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ShowNotificationFragment.NotificationByCategoryAdapter.ViewHolder holder;
            if(convertView == null){
                convertView =  inflater.inflate(R.layout.message_layout,null);
                holder = new ShowNotificationFragment.NotificationByCategoryAdapter.ViewHolder();
                holder.title = convertView.findViewById(R.id.title);
                holder.description = convertView.findViewById(R.id.details);
                holder.link = convertView.findViewById(R.id.link);
                holder.validity = convertView.findViewById(R.id.validity);
                convertView.setTag(holder);
            }else{
                holder = (ShowNotificationFragment.NotificationByCategoryAdapter.ViewHolder) convertView.getTag();
            }

            holder.title.setText(notificationList.get(position).getTitle());
            holder.description.setText(notificationList.get(position).getDescription());
            holder.link.setText(notificationList.get(position).getLink());
            holder.validity.setText(notificationList.get(position).getValidity_date());

            return convertView;
        }

        class ViewHolder {
            TextView title;
            TextView description;
            TextView link;
            TextView validity;
        }
    }


}
