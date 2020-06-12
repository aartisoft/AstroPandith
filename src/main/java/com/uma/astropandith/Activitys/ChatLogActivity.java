package com.uma.astropandith.Activitys;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uma.astropandith.Adapters.ChatLogAdapter;
import com.uma.astropandith.Model.ChatHistory;
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

public class ChatLogActivity extends AppCompatActivity implements RestCallback {

    private static final int SYSTEM_ALERT_WINDOW_PERMISSION  = 7 ;
    private SharedPreferences sharedPreferences;
    private String _name;
    private String _id;
    private String _mail;
    private String _phone;
    private RecyclerView recyclerRV;
    private ProgressDialog progressBar;
    private ArrayList<ChatHistory> _chatHistory;
    private ChatLogAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_log);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        sharedPreferences = getSharedPreferences("save", Context.MODE_PRIVATE);
        _name = sharedPreferences.getString("name", null); // getting String
        _id = sharedPreferences.getString("id", null); // getting String
        _mail = sharedPreferences.getString("email", null); // getting String
        _phone = sharedPreferences.getString("phone", null); // getting String


        recyclerRV = findViewById(R.id.chatLog_rv);

        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Loading");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);


        getChatLogAPI();


        if (Settings.canDrawOverlays(this)) {


            // startService(new Intent(MainActivity.this, ChatService.class));


        } else {

            RuntimePermissionForUser();

            Toast.makeText(ChatLogActivity.this, "System Alert Window Permission Is Required For Floating Widget.", Toast.LENGTH_LONG).show();

        }


    }





    @Override
    protected void onRestart() {

       // getChatLogAPI();

        super.onRestart();

    }


    public void RuntimePermissionForUser() {

        Intent PermissionIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getPackageName()));

        startActivityForResult(PermissionIntent, SYSTEM_ALERT_WINDOW_PERMISSION);
    }

    private void getChatLogAPI() {

        progressBar.show();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("pId", _id);
        RestService.getInstance(ChatLogActivity.this).getChatHistory(map, new MyCallback<ArrayList<ChatHistory>>(ChatLogActivity.this,
                ChatLogActivity.this, GlobalVariables.SERVICE_MODE.CHAT_HISTORY));

    }

    @Override
    public void onFailure(Call call, Throwable t, GlobalVariables.SERVICE_MODE mode) {

        switch (mode) {

            case CHAT_HISTORY:

                Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();

                recyclerRV.setAdapter(null);



                progressBar.dismiss();

                break;
        }

    }

    @Override
    public void onSuccess(Response response, GlobalVariables.SERVICE_MODE mode) {

        switch (mode) {

            case CHAT_HISTORY:

                _chatHistory = (ArrayList<ChatHistory>) response.body();

                Collections.reverse(_chatHistory);



                if(_chatHistory!=null){

                    adapter = new ChatLogAdapter(ChatLogActivity.this, _chatHistory);
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
