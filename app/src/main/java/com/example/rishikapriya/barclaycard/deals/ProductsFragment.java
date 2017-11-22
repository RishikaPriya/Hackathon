package com.example.rishikapriya.barclaycard.deals;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.rishikapriya.barclaycard.R;
import com.example.rishikapriya.barclaycard.Security.Security;
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
    private ProductAdapter adapter;
    private Map<String,Item> itemPriceMap, itemAsinMap;
    private static final String ASIN_SURF = "B00TS88UZW";
    private static final String ASIN_LAKME = "B01BBNF6GM";
    private static final String ASIN_OIL = "B010GGD02C";
    private static final String ASIN_DEO = "B007E9HPLC";
    private static final String ASIN_FACEWASH = "B007921JYI";
    private static final String ASIN_RICE = "B00SWKBO0K";

    public static Fragment getInstance(Activity context) {
        ProductsFragment fragment = new ProductsFragment();
        fragment.activity = context;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_summary,null,false);
        listView = view.findViewById(R.id.list_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        /*listView.setLayoutManager(layoutManager);
        adapter = new ProductAdapter(new ArrayList<Item>(), new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Item item) {

            }
        });
        listView.setAdapter(adapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL);
        listView.addItemDecoration(itemDecoration);
        createMapOfItems();
        //createListOfItems(view);
        itemPriceMap = new HashMap<>();*/
        return view;
    }


    private void createMapOfItems() {
        itemAsinMap = new HashMap<>();

        itemAsinMap.put(ASIN_SURF,new Item("Surf Excel","500.00","1",ASIN_SURF));
        itemAsinMap.put(ASIN_LAKME,new Item("Lakme","299.00","1", ASIN_LAKME));
        itemAsinMap.put(ASIN_RICE,new Item("Rice","500.00","1",ASIN_RICE));
        itemAsinMap.put(ASIN_DEO,new Item("Deo","500.00","1",ASIN_DEO));
        itemAsinMap.put(ASIN_OIL,new Item("Oil","500.00","1",ASIN_OIL));
        itemAsinMap.put(ASIN_FACEWASH,new Item("Face Wash","500.00","1",ASIN_FACEWASH));

        List<Item> list = new ArrayList<>();
        //getAmazonPrice(0,ASIN_SURF);
        /*list.add(new Item("Surf Excel","500.00",getAmazonPrice(ASIN_SURF),ASIN_SURF) );
        list.add(new Item("Lakme","299.00", getAmazonPrice(ASIN_LAKME),ASIN_LAKME));
        list.add(new Item("Rice","500.00", getAmazonPrice(ASIN_RICE),ASIN_RICE));
        list.add(new Item("Deo","500.00", getAmazonPrice(ASIN_DEO),ASIN_DEO));
        list.add(new Item("Oil","700.00", getAmazonPrice(ASIN_OIL),ASIN_OIL));
        list.add(new Item("Face Wash","500.00", getAmazonPrice(ASIN_FACEWASH),ASIN_FACEWASH));
*/
        //setItemValues(list,view);
    }

    private void setItemValues(List<Item> list, View view) {
        boughtAt = view.findViewById(R.id.surfExcel_bought);
        boughtAt.setText(list.get(0).getBoughtPrice());
        newPrice = view.findViewById(R.id.surfExcel_current);
        newPrice.setText(list.get(0).getNewPrice());

        boughtAt = view.findViewById(R.id.lakme_bought);
        boughtAt.setText(list.get(1).getBoughtPrice());
        newPrice = view.findViewById(R.id.lakme_current);
        newPrice.setText(list.get(1).getNewPrice());

        boughtAt = view.findViewById(R.id.rice_bought);
        boughtAt.setText(list.get(2).getBoughtPrice());
        newPrice = view.findViewById(R.id.rice_current);
        newPrice.setText(list.get(2).getNewPrice());

        boughtAt = view.findViewById(R.id.deo_bought);
        boughtAt.setText(list.get(3).getBoughtPrice());
        newPrice = view.findViewById(R.id.deo_current);
        newPrice.setText(list.get(3).getNewPrice());

        boughtAt = view.findViewById(R.id.oil_bought);
        boughtAt.setText(list.get(4).getBoughtPrice());
        newPrice = view.findViewById(R.id.oil_current);
        newPrice.setText(list.get(4).getNewPrice());

        boughtAt = view.findViewById(R.id.face_wash_bought);
        boughtAt.setText(list.get(5).getBoughtPrice());
        newPrice = view.findViewById(R.id.face_wash_current);
        newPrice.setText(list.get(5).getNewPrice());



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
       // itemPriceMap.get(id).setter;
       // adapter.addProductToList(itemPriceMap);

    }

    public static class ProductAdapter extends BaseAdapter{

        private 

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}
