package com.uma.astropandith.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.uma.astropandith.Activitys.CoverstionActivity;
import com.uma.astropandith.Activitys.ShowMsgActivity;
import com.uma.astropandith.Retrofit.GlobalVariables;
import com.uma.astropandith.Retrofit.MyCallback;
import com.uma.astropandith.Retrofit.RestCallback;
import com.uma.astropandith.Retrofit.RestService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class RemoveAppTrack extends Service implements RestCallback {
    private String _chatId;
    private String _uid;
    private String _uname;
    private String _pid;
    private String _pname;
    private String _sec;
    private Date currentTime;
    private Calendar calander;
    private SimpleDateFormat simpledateformat;
    private SimpleDateFormat simpleDate;
    private String simpleTime;
    private RequestQueue requestQueue;
    private CountDownTimer time;

    public RemoveAppTrack() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        requestQueue = Volley.newRequestQueue(this);

        SharedPreferences sharedPreferences = getSharedPreferences("ChatC", Context.MODE_PRIVATE);
        _chatId = sharedPreferences.getString("chatId", null); // getting String
        _uid = sharedPreferences.getString("uid", null); // getting String
        _uname = sharedPreferences.getString("uname", null); // getting String
        _pid = sharedPreferences.getString("pid", null); // getting String
        _pname = sharedPreferences.getString("pname", null); // getting String
        _sec = sharedPreferences.getString("sec", null); // getting String




        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {

//        time.cancel();

        //  Toast.makeText(this, "Service stopped", Toast.LENGTH_SHORT).show();

        super.onDestroy();
    }


    @Override
    public void onTaskRemoved(Intent rootIntent) {
        System.out.println("onTaskRemoved called");

        requestQueue = Volley.newRequestQueue(this);


        SharedPreferences sharedPreferences = getSharedPreferences("ChatC", Context.MODE_PRIVATE);
        _chatId = sharedPreferences.getString("chatId", null); // getting String
        _uid = sharedPreferences.getString("uid", null); // getting String
        _uname = sharedPreferences.getString("uname", null); // getting String
        _pid = sharedPreferences.getString("pid", null); // getting String
        _pname = sharedPreferences.getString("pname", null); // getting String
        _sec = sharedPreferences.getString("sec", null); // getting String

        if(_chatId!=null) {

            getChatDataAPI();

          // Toast.makeText(this, _chatId, Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Chat Ended", Toast.LENGTH_SHORT).show();


        }


        super.onTaskRemoved(rootIntent);


      //  this.stopSelf();
    }


    public void getChatDataAPI() {

        currentTime = Calendar.getInstance().getTime();
        calander = Calendar.getInstance();
        simpledateformat = new SimpleDateFormat("dd-MM-yyyy");
        String Datet = simpledateformat.format(calander.getTime());
        simpleDate = new SimpleDateFormat("HH:mm:ss");
        simpleTime = simpleDate.format(calander.getTime());



        HashMap<String, String> map = new HashMap<String, String>();
        map.put("chatid", _chatId);
        map.put("pid", _pid);
        map.put("pname", _pname);
        map.put("uid", _uid);
        map.put("uname", _uname);
        map.put("chtime", String.valueOf(simpleTime));
        map.put("chdate", Datet);
        map.put("duration", _sec);
        RestService.getInstance(RemoveAppTrack.this).getChatInfoData(map, new MyCallback<ResponseBody>(RemoveAppTrack.this,
                RemoveAppTrack.this, GlobalVariables.SERVICE_MODE.CHAT_INFO_DATA));



        SharedPreferences sharedPreferences1 = getSharedPreferences("ChatC", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit1 = sharedPreferences1.edit();
        edit1.clear();
        edit1.apply();


    }



    private void getChatEndAPI() {


        SharedPreferences sharedPreferences1 = getSharedPreferences("ChatC", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit1 = sharedPreferences1.edit();
        edit1.clear();
        edit1.apply();


        HashMap<String, String> map = new HashMap<String, String>();
        map.put("rid", _chatId);
        map.put("StatusValue", "5");
        map.put("pid", _pid);
        map.put("uid", _uid);
        RestService.getInstance(RemoveAppTrack.this).getChatSstatus(map, new MyCallback<ResponseBody>(RemoveAppTrack.this,
                RemoveAppTrack.this, GlobalVariables.SERVICE_MODE.CHAT_SATUS_UPDATE));

    }

    private void getWaitlistOnlineAPI() {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("pid", _pid);
        RestService.getInstance(RemoveAppTrack.this).getStatusWaitlisOnline(map, new MyCallback<ResponseBody>(RemoveAppTrack.this,
                RemoveAppTrack.this, GlobalVariables.SERVICE_MODE.STATUS_WAITLIST_ONLINE));


    }


    @Override
    public void onFailure(Call call, Throwable t, GlobalVariables.SERVICE_MODE mode) {

        switch (mode){

            case CHAT_INFO_DATA:

                Toast.makeText(this, "Insert fail No Data Foun", Toast.LENGTH_SHORT).show();

                break;


            case CHAT_SATUS_UPDATE:

                Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();


                break;



            case STATUS_WAITLIST_ONLINE:


                Toast.makeText(this, "No Data Foun", Toast.LENGTH_SHORT).show();


                break;


        }

    }

    @Override
    public void onSuccess(Response response, GlobalVariables.SERVICE_MODE mode) {

        switch (mode){


            case CHAT_INFO_DATA:

                getChatEndAPI();
                getWaitlistOnlineAPI();

                break;

            case CHAT_SATUS_UPDATE:

                //Toast.makeText(CoverstionActivity.this, "Chat End", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), ShowMsgActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("msg","Chat Ended");

                startActivity(intent);

                stopSelf();

                break;

            case STATUS_WAITLIST_ONLINE:

                //Toast.makeText(this, "Ss", Toast.LENGTH_SHORT).show();

                break;

        }

    }
}
