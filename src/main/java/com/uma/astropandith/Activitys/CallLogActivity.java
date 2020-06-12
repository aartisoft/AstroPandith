package com.uma.astropandith.Activitys;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.uma.astropandith.Adapters.CallLogAdapter;
import com.uma.astropandith.Model.CallHistory;
import com.uma.astropandith.R;
import com.uma.astropandith.Retrofit.GlobalVariables;
import com.uma.astropandith.Retrofit.MyCallback;
import com.uma.astropandith.Retrofit.RestCallback;
import com.uma.astropandith.Retrofit.RestService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

public class CallLogActivity extends AppCompatActivity implements RestCallback {

    private SharedPreferences sharedPreferences;
    private String _name;
    private String _id;
    private String _mail;
    private String _phone;
    private RecyclerView recyclerRV;
    private ProgressDialog progressBar;
    private ArrayList<CallHistory> _callHistory;
    private CallLogAdapter adapter;
    private LinearLayout _call;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_log);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        sharedPreferences = getSharedPreferences("save", Context.MODE_PRIVATE);
        _name = sharedPreferences.getString("name", null); // getting String
        _id = sharedPreferences.getString("id", null); // getting String
        _mail = sharedPreferences.getString("email", null); // getting String
        _phone = sharedPreferences.getString("phone", null); // getting String


        recyclerRV = findViewById(R.id.callLog_rv);
        _call = (LinearLayout) findViewById(R.id.call);


        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Loading");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);

        getCallLogAPI();




    }


    private void getCallLogAPI() {

        progressBar.show();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("pid", _id);
        RestService.getInstance(CallLogActivity.this).getCallHistory(map, new MyCallback<ArrayList<CallHistory>>(CallLogActivity.this,
                CallLogActivity.this, GlobalVariables.SERVICE_MODE.CALL_HISTORY));

    }


    @Override
    public void onFailure(Call call, Throwable t, GlobalVariables.SERVICE_MODE mode) {

        switch (mode) {

            case CALL_HISTORY:

                Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();

                recyclerRV.setAdapter(null);



                progressBar.dismiss();

                break;
        }

    }

    @Override
    public void onSuccess(Response response, GlobalVariables.SERVICE_MODE mode) {

        switch (mode) {

            case CALL_HISTORY:

                _callHistory = (ArrayList<CallHistory>) response.body();
                Collections.reverse(_callHistory);


                if(_callHistory!=null){

                    adapter = new CallLogAdapter(CallLogActivity.this, _callHistory);
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
}
