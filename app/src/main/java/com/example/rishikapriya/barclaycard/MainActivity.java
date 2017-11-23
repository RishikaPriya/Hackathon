package com.example.rishikapriya.barclaycard;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.VolleyError;
import com.example.rishikapriya.barclaycard.Utils.CommonUtils;
import com.example.rishikapriya.barclaycard.communication.AmazonProductGateway;
import com.example.rishikapriya.barclaycard.communication.ServerCommunication;
import com.example.rishikapriya.barclaycard.communication.WebResponseListener;
import com.example.rishikapriya.barclaycard.deals.MyOffersFragment;
import com.example.rishikapriya.barclaycard.deals.ProductsFragment;
import com.example.rishikapriya.barclaycard.model.CreateWalletResponse;
import com.example.rishikapriya.barclaycard.model.Item;
import com.example.rishikapriya.barclaycard.service.AddAmountService;
import com.example.rishikapriya.barclaycard.service.CreateWalletService;
import com.example.rishikapriya.barclaycard.wallets.WalletFragment;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.StringReader;
import java.util.Date;

import static com.example.rishikapriya.barclaycard.constants.Constants.API_SUCCESS_CODE;
import static com.example.rishikapriya.barclaycard.constants.Constants.MINIMUM_AMOUNT;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        createWallet();

        if(getIntent().getExtras() != null){
            Item item = new Item(
                    getIntent().getStringExtra("name"),
                    getIntent().getStringExtra("boughtPrice"),
                    getIntent().getStringExtra("currentPrice"),
                    getIntent().getStringExtra("asin"),
                    R.mipmap.ic_product
            );
            showProductSummaryFragment(item);
        }else{
            showMyOffersFragment();
        }
    }

    private void showProductSummaryFragment(Item item) {
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, ProductsFragment.getInstance(this, item),ProductsFragment.class.toString()).commit();
    }

    private void createWallet() {
        CreateWalletService.createWallet(new WebResponseListener() {
            @Override
            public void onReceiveResponse(Object response) {
                Gson gson =  new Gson();
                CreateWalletResponse walletResponse = gson.fromJson(response.toString(), CreateWalletResponse.class);
                if(API_SUCCESS_CODE.equals(walletResponse.getStatus()) && !CommonUtils.checkNullOrEmpty(walletResponse.getWallet().getWalletCode())){
                    //Security.getInstance().setWalletCode(walletResponse.getWallet().getWalletCode());
                    AddAmountService.addAmount("TOP UP", "PublicTransport", new WebResponseListener() {
                                @Override
                                public void onReceiveResponse(Object response) {
                                    showWalledListInfoFragment();
                                }

                                @Override
                                public void onReceiveError(VolleyError volleyError) {

                                }
                            });
                }
            }

            @Override
            public void onReceiveError(VolleyError volleyError) {

            }
        });
    }

    private void getAmazon() {
        ServerCommunication.getmInstance().stringGETRequest(AmazonProductGateway.getInstance().productLookUp("B01BBNF6GM"), new WebResponseListener<String>() {
            @Override
            public void onReceiveResponse(String response) {
                Log.v("RESPONSE",response);
                parseResponse(response);

                XmlToJson xml = new XmlToJson.Builder(response).build();

                JSONObject jsonObj = xml.toJson();
                JSONObject jsonObject = null;
                try {
                    jsonObject = XML.toJSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.v("JSON: ", jsonObject.toString());
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
        String price  = getElementValue("LowestNewPrice", rootElement);


    }

    private String getElementValue(String tagName, Element element) {
        return element.getChildNodes().item(1).getChildNodes().item(1).getChildNodes().item(1).getChildNodes().item(0).getChildNodes().item(0).getTextContent();
    }



    private void showMyOffersFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, MyOffersFragment.newInstance(this),MyOffersFragment.class.toString()).commit();
    }

    private void showWalledListInfoFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, WalletFragment.newInstance(this),WalletFragment.class.toString()).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_summary) {
            showWalledListInfoFragment();
        } else if (id == R.id.nav_deals) {
            showMyOffersFragment();
        } else if (id == R.id.nav_notification) {

        } else if (id == R.id.nav_payments) {

        } else if (id == R.id.nav_rewards) {

        } else if (id == R.id.nav_transactions) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
