package com.example.rishikapriya.barclaycard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.rishikapriya.barclaycard.Security.Security;
import com.example.rishikapriya.barclaycard.Utils.CommonUtils;
import com.example.rishikapriya.barclaycard.communication.AmazonProductGateway;
import com.example.rishikapriya.barclaycard.communication.ServerCommunication;
import com.example.rishikapriya.barclaycard.communication.WebResponseListener;
import com.example.rishikapriya.barclaycard.constants.Constants;
import com.example.rishikapriya.barclaycard.database.DatabaseHandler;
import com.example.rishikapriya.barclaycard.deals.MyOffersFragment;
import com.example.rishikapriya.barclaycard.deals.ProductsFragment;
import com.example.rishikapriya.barclaycard.model.CreateWalletResponse;
import com.example.rishikapriya.barclaycard.model.Item;
import com.example.rishikapriya.barclaycard.model.Notification;
import com.example.rishikapriya.barclaycard.notification.NotificationFragment;
import com.example.rishikapriya.barclaycard.service.AddAmountService;
import com.example.rishikapriya.barclaycard.service.CreateWalletService;
import com.example.rishikapriya.barclaycard.wallets.TransactionsFragment;
import com.example.rishikapriya.barclaycard.wallets.WalletFragment;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static com.example.rishikapriya.barclaycard.constants.Constants.API_SUCCESS_CODE;
import static com.example.rishikapriya.barclaycard.constants.Constants.IS_LOGIN;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private int backPressCount;
    private DatabaseHandler db;
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHandler(this);
        sharedpreferences = getSharedPreferences(Constants.PREF, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

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
            Intent intent = getIntent();
            String key = intent.getStringExtra("fragment");
            if(key!=null && key.equals("PersonalizedData")){
                showNotification(intent);
            }else {
                showProductOffer(intent);
            }
        }else{
            showMyOffersFragment();
        }
    }

    private void showNotification(Intent intent) {
        Notification notification = new Notification(
                intent.getStringExtra("title"),
                intent.getStringExtra("description"),
                intent.getStringExtra("link"),
                intent.getStringExtra("validity_date"),
                intent.getStringExtra("category")
        );

        db.addNotification(notification);

        showNotificationFragment();
    }

    private void showProductOffer(Intent intent) {
        int size = intent.getIntExtra("NO_OF_ITEMS", 0);
        List<Item> list = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            Item item = new Item(intent.getStringExtra("item" + i), intent.getStringExtra("item" + i + "bought_price"),
                    intent.getStringExtra("item" + i + "current_price"), null, intent.getIntExtra("item" + i + "image_id", R.mipmap.ic_product));
            list.add(item);
        }
        showProductSummaryFragment(list);
    }


    private void showNotificationFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, NotificationFragment.getInstance(),NotificationFragment.class.toString()).commit();
    }

    private void showProductSummaryFragment(List<Item> itemList) {
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, ProductsFragment.getInstance(this, itemList),ProductsFragment.class.toString()).commit();
    }

    private void createWallet() {
        CreateWalletService.createWallet(new WebResponseListener() {
            @Override
            public void onReceiveResponse(Object response) {
                Gson gson =  new Gson();
                CreateWalletResponse walletResponse = gson.fromJson(response.toString(), CreateWalletResponse.class);
                if(API_SUCCESS_CODE.equals(walletResponse.getStatus()) && CommonUtils.checkNullOrEmpty(walletResponse.getWallet().getWalletCode())){
                    Security.getInstance().setWalletCode(walletResponse.getWallet().getWalletCode());
                    AddAmountService.addAmount("TOP UP", "PublicTransport", new WebResponseListener() {
                                @Override
                                public void onReceiveResponse(Object response) {

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
            if(backPressCount==0){
                Toast.makeText(this,"Press back again to exit",Toast.LENGTH_SHORT).show();
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            backPressCount = 0;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
                backPressCount++;
            }else
                System.exit(0);
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
        if (id == R.id.action_logout) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        editor.putBoolean(IS_LOGIN, false);
        editor.commit();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);

        startActivity(intent);
        finish();
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
            showNotificationFragment();
        } else if (id == R.id.nav_payments) {

        } else if (id == R.id.nav_rewards) {

        } else if (id == R.id.nav_transactions) {
            showTransactionFragment();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showTransactionFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, TransactionsFragment.newInstance(MainActivity.class.toString(), null),TransactionsFragment.class.toString()).commit();
    }


}
