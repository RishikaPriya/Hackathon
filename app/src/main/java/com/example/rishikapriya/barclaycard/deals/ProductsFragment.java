package com.example.rishikapriya.barclaycard.deals;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.rishikapriya.barclaycard.R;
import com.example.rishikapriya.barclaycard.communication.AmazonProductGateway;
import com.example.rishikapriya.barclaycard.communication.ServerCommunication;
import com.example.rishikapriya.barclaycard.communication.WebResponseListener;
import com.example.rishikapriya.barclaycard.model.Item;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by rishikapriya on 21/11/17.
 */

public class ProductsFragment extends Fragment {
    private Activity activity;
    private TextView boughtAt;
    private TextView newPrice;
    private ListView listView;
    private Item item;
    private ProductAdapter adapter;
    private Map<String,Item> itemPriceMap, itemAsinMap;
    private static final String ASIN_SURF = "B00TS88UZW";
    private static final String ASIN_LAKME = "B01BBNF6GM";
    private static final String ASIN_OIL = "B010GGD02C";
    private static final String ASIN_DEO = "B007E9HPLC";
    private static final String ASIN_FACEWASH = "B007921JYI";
    private static final String ASIN_RICE = "B00SWKBO0K";

    public static Fragment getInstance(Activity context, Item item) {
        ProductsFragment fragment = new ProductsFragment();
        fragment.activity = context;
        fragment.item = item;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_summary,null,false);
        listView = view.findViewById(R.id.list_view);
        List<Item> list = new ArrayList<>();
        adapter = new ProductAdapter(list,getActivity());
        listView.setAdapter(adapter);
        if(item != null){
            adapter.addProductToList(item);
            adapter.notifyDataSetChanged();
        }
        createMapOfItems();
        return view;
    }


    private void createMapOfItems() {
        itemAsinMap = new HashMap<>();

        itemAsinMap.put(ASIN_SURF, new Item("Surf Excel", "500.00", "1", ASIN_SURF, R.drawable.ic_surf));
        itemAsinMap.put(ASIN_LAKME, new Item("Lakme", "299.00", "1", ASIN_LAKME, R.drawable.ic_lakme));
        itemAsinMap.put(ASIN_RICE, new Item("Rice", "810.00", "1", ASIN_RICE, R.drawable.ic_rice));
        itemAsinMap.put(ASIN_DEO, new Item("Deo", "250.00", "1", ASIN_DEO, R.drawable.ic_deo));
        itemAsinMap.put(ASIN_OIL, new Item("Oil", "950.00", "1", ASIN_OIL, R.drawable.ic_olive_oil));
        itemAsinMap.put(ASIN_FACEWASH, new Item("Face Wash", "220.00", "1", ASIN_FACEWASH, R.drawable.ic_facewash));

        getAmazonPrice(ASIN_SURF);
        getAmazonPrice(ASIN_LAKME);
        getAmazonPrice(ASIN_RICE);
        getAmazonPrice(ASIN_DEO);
        getAmazonPrice(ASIN_OIL);
        getAmazonPrice(ASIN_FACEWASH);
    }


    private void getAmazonPrice(String id) {
        ServerCommunication.getmInstance().stringGETRequest(AmazonProductGateway.getInstance().productLookUp(id), new WebResponseListener<String>() {
            @Override
            public void onReceiveResponse(String response) {
                Log.v("RESPONSE",response);
                parseResponse(response);
            }

            @Override
            public void onReceiveError(VolleyError volleyError) {

            }
        });

    }

    private void showSelectAddressFragment(Item product) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,SelectAddressFragment.getInstance(product)).commit();
    }

    private void parseResponse(String response) {


        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document document = null;
        try {
            document = builder.parse(new InputSource(new StringReader(response)));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Element rootElement = document.getDocumentElement();

        String id = rootElement.getElementsByTagName("ItemId").item(0).getTextContent();

        String price = rootElement.getElementsByTagName("LowestNewPrice").item(0).getChildNodes().item(0).getTextContent();

        itemAsinMap.get(id).setNewPrice(new StringBuffer(price).insert(price.length()-2, ".").toString());
        adapter.addProductToList(itemAsinMap.get(id));
        adapter.notifyDataSetChanged();

    }

    public class ProductAdapter extends BaseAdapter{

        private final LayoutInflater inflater;
        private List<Item> productList;
        private Context context;

        private ProductAdapter(List<Item> list, Context context){

            this.productList = list;
            this.inflater = LayoutInflater.from(context);
            this.context = context;
        }

        @Override
        public int getCount() {
            return productList.size();
        }

        @Override
        public Object getItem(int position) {
            return productList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if(convertView == null){
                convertView =  inflater.inflate(R.layout.product_item,null);
                holder = new ViewHolder();
                holder.itemName = convertView.findViewById(R.id.item_name);
                holder.boughtPrice = convertView.findViewById(R.id.last_bought_price);
                holder.currentPrice = convertView.findViewById(R.id.current_price);
                holder.itemImage = convertView.findViewById(R.id.item_image);
                holder.buyNow = convertView.findViewById(R.id.buy_button);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            holder.itemName.setText(productList.get(position).getName());
            holder.boughtPrice.setText(getString(R.string.bought_at) + productList.get(position).getBoughtPrice());
            holder.currentPrice.setText(getString(R.string.current_price) + productList.get(position).getNewPrice());
            holder.itemImage.setImageResource(productList.get(position).getImageId());
            holder.buyNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSelectAddressFragment(productList.get(position));
                }
            });

            return convertView;
        }

        public void addProductToList(Item item) {
            productList.add(item);
            notifyDataSetChanged();
        }

        class ViewHolder {
            ImageView itemImage;
            TextView itemName;
            TextView boughtPrice;
            TextView currentPrice;
            Button buyNow;
        }
    }
}
