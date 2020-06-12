package com.uma.astropandith;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.uma.astropandith.Activitys.CoverstionActivity;
import com.uma.astropandith.Retrofit.GlobalVariables;
import com.uma.astropandith.Retrofit.MyCallback;
import com.uma.astropandith.Retrofit.RestCallback;
import com.uma.astropandith.Retrofit.RestService;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class ChatRequestActivity extends AppCompatActivity implements RestCallback {

    private AlertDialog alertD;
    private String _pid;
    private String _pname;
    private SharedPreferences sharedPreferences;
    private String _mail;
    private String _phone;
    private String reportid;
    private String uid;
    private String uname;
    private SharedPreferences sharedPreferences1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_request);


        sharedPreferences = getSharedPreferences("save", Context.MODE_PRIVATE);
        _pname = sharedPreferences.getString("name", null); // getting String
        _pid = sharedPreferences.getString("id", null); // getting String
        _mail = sharedPreferences.getString("email", null); // getting String
        _phone = sharedPreferences.getString("phone", null); // getting String

        sharedPreferences1 = getSharedPreferences("notifyID", Context.MODE_PRIVATE);
        reportid = sharedPreferences1.getString("reportid", null); // getting String
        uid = sharedPreferences1.getString("uid", null); // getting String
        uname = sharedPreferences1.getString("uname", null); // getting String




        Toast.makeText(this, reportid, Toast.LENGTH_SHORT).show();


        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.bottom_sheet, null);

        alertD = new AlertDialog.Builder(this).create();

        TextView name = (TextView) promptView.findViewById(R.id.name_u);
        name.setText(uname);

        Button btnAdd1 = (Button) promptView.findViewById(R.id.cancel);

        Button btnAdd2 = (Button) promptView.findViewById(R.id.accept);

        btnAdd1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                getChatCancelAPI();

            }
        });

        btnAdd2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                getChatAcceptAPI();

            }
        });

        alertD.setView(promptView);

        alertD.show();

        alertD.setCancelable(false);

    }

    private void getChatCancelAPI() {


        HashMap<String, String> map = new HashMap<String, String>();
        map.put("rid", reportid);
        map.put("StatusValue", "4");
        map.put("pid", _pid);
        map.put("uid", uid);
        RestService.getInstance(ChatRequestActivity.this).getChatStatusCancel(map, new MyCallback<ResponseBody>(ChatRequestActivity.this,
                ChatRequestActivity.this, GlobalVariables.SERVICE_MODE.CHAT_SATUS_CANCEL));


    }


    private void getChatAcceptAPI() {


        HashMap<String, String> map = new HashMap<String, String>();
        map.put("rid", reportid);
        map.put("StatusValue", "2");
        map.put("pid", _pid);
        map.put("uid", uid);
        RestService.getInstance(ChatRequestActivity.this).getChatSstatus(map, new MyCallback<ResponseBody>(ChatRequestActivity.this,
                ChatRequestActivity.this, GlobalVariables.SERVICE_MODE.CHAT_SATUS_UPDATE));


    }


    @Override
    public void onFailure(Call call, Throwable t, GlobalVariables.SERVICE_MODE mode) {


        switch (mode){

            case CHAT_SATUS_UPDATE:

                Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();


                break;

            case CHAT_SATUS_CANCEL:

                Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();


                break;


        }

    }

    @Override
    public void onSuccess(Response response, GlobalVariables.SERVICE_MODE mode) {


        switch (mode){

            case CHAT_SATUS_UPDATE:

               // Toast.makeText(this, _rid +"  "+_pid +"  "+ _uid, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ChatRequestActivity.this,CoverstionActivity.class);
                intent.putExtra("pid",_pid);
                intent.putExtra("pname",_pname);
                intent.putExtra("uname",uname);
                intent.putExtra("uid",uid);
                intent.putExtra("reportid",reportid);
                startActivity(intent);
                finish();

                break;

            case CHAT_SATUS_CANCEL:


                finish();


                break;
        }

    }
}
