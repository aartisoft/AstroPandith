package com.uma.astropandith.Activitys;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.uma.astropandith.Adapters.WalletHistoryAdapter;
import com.uma.astropandith.Model.WalletHistory;
import com.uma.astropandith.Model.WalletModel;
import com.uma.astropandith.R;
import com.uma.astropandith.Retrofit.GlobalVariables;
import com.uma.astropandith.Retrofit.MyCallback;
import com.uma.astropandith.Retrofit.RestCallback;
import com.uma.astropandith.Retrofit.RestService;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static com.uma.astropandith.Retrofit.GlobalVariables.BASE_URL;

public class WalletActivity extends AppCompatActivity implements RestCallback {

    private SharedPreferences sharedPreferences;
    private String _name;
    private String _id;
    private String _mail;
    private String _phone;
    private RecyclerView recyclerRV;
    private ProgressDialog progressBar;
    private ArrayList<WalletHistory> _walletHistory;
    private WalletHistoryAdapter adapter;
    private TextView _ampunt;
    private RequestQueue requestQueue;
    private JsonObjectRequest mJsonRequest;
    private String walletAmount;
    private Spinner _filterSPIN;
    private String filterSPIN;
    private String astrotypeStatusValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        sharedPreferences = getSharedPreferences("save", Context.MODE_PRIVATE);
        _name = sharedPreferences.getString("name", null); // getting String
        _id = sharedPreferences.getString("id", null); // getting String
        _mail = sharedPreferences.getString("email", null); // getting String
        _phone = sharedPreferences.getString("phone", null); // getting String


        recyclerRV = findViewById(R.id.wallet_RV);


        requestQueue = Volley.newRequestQueue(this);


        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Loading");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);


        _ampunt = (TextView) findViewById(R.id.ampunt);

        _filterSPIN = (Spinner) findViewById(R.id.filter);


        List<String> categories = new ArrayList<String>();
        categories.add("All History");
        categories.add("Report");
        categories.add("Chat");
        categories.add("Calls");

        astrotypeStatusValue = "9";


        getWallet();
        getWalletHistiryAPI();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        _filterSPIN.setAdapter(dataAdapter);
        _filterSPIN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                filterSPIN = (String) adapterView.getItemAtPosition(i);

                if(filterSPIN.equals("All History")){

                    astrotypeStatusValue = "9";
                    getWalletHistiryAPI();


                } else if(filterSPIN.equals("Report")){

                    astrotypeStatusValue = "1";
                    getWalletHistiryAPI();


                } else if(filterSPIN.equals("Chat")){

                    astrotypeStatusValue = "2";
                    getWalletHistiryAPI();


                }else if(filterSPIN.equals("Calls")){

                    astrotypeStatusValue = "3";
                    getWalletHistiryAPI();


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }



    @Override
    protected void onRestart() {
        getWallet();
        getWalletHistiryAPI();
        super.onRestart();
    }

    private void getWalletHistiryAPI() {

        progressBar.show();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("pId", _id);
        map.put("astrotypeStatusValue", astrotypeStatusValue);
        RestService.getInstance(WalletActivity.this).getWalletHistory(map, new MyCallback<ArrayList<WalletHistory>>(WalletActivity.this,
                WalletActivity.this, GlobalVariables.SERVICE_MODE.WALLET_HISTORY));

    }



    @Override
    public void onFailure(Call call, Throwable t, GlobalVariables.SERVICE_MODE mode) {


        switch (mode) {

            case WALLET_HISTORY:

                Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();

                recyclerRV.setAdapter(null);

                progressBar.dismiss();

                break;


        }

    }

    @Override
    public void onSuccess(Response response, GlobalVariables.SERVICE_MODE mode) {


        switch (mode) {

            case WALLET_HISTORY:

                _walletHistory = (ArrayList<WalletHistory>) response.body();

                Collections.reverse(_walletHistory);

                if(_walletHistory!=null){

                    adapter = new WalletHistoryAdapter(WalletActivity.this, _walletHistory);
                    recyclerRV.setAdapter(adapter);
                    recyclerRV.setLayoutManager(new LinearLayoutManager(this));



                    progressBar.dismiss();

                } else {

                    recyclerRV.setAdapter(null);
                }

                break;


        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void getWallet(){


        mJsonRequest = new JsonObjectRequest(BASE_URL+"pandit/panditWalletAmount.php?pid="+_id, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                WalletModel walletModel = gson.fromJson(response.toString(), WalletModel.class);


                walletAmount = walletModel.getWalletAmount();
                _ampunt.setText("\u20B9 "+walletAmount);


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(WalletActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        requestQueue.add(mJsonRequest);

    }

    public void viewMIC(View view) {

        Intent intent = new Intent(WalletActivity.this, WebViewActivity.class);
        intent.putExtra("url","https://meraastro.com/Pandit/monthlyincome.php?pid="+_id);
        startActivity(intent);

    }
}
