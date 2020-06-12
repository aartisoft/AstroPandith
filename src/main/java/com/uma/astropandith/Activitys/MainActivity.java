package com.uma.astropandith.Activitys;

import android.Manifest;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.uma.astropandith.BuildConfig;
import com.uma.astropandith.Model.AppVersionModel;
import com.uma.astropandith.Model.ChatRNotify;
import com.uma.astropandith.Model.ChatRequestModel;
import com.uma.astropandith.Model.ChatStatusValueModel;
import com.uma.astropandith.Model.PriceModel;
import com.uma.astropandith.Model.StausMoldel;
import com.uma.astropandith.Service.ChatNotifyService;
import com.uma.astropandith.Service.ChatReqPopService;
import com.uma.astropandith.Service.NotificationService;
import com.uma.astropandith.Service.OneSignel;
import com.uma.astropandith.R;
import com.uma.astropandith.Retrofit.GlobalVariables;
import com.uma.astropandith.Retrofit.MyCallback;
import com.uma.astropandith.Retrofit.RestCallback;
import com.uma.astropandith.Retrofit.RestService;
import com.uma.astropandith.Service.RemoveAppTrack;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import static com.uma.astropandith.Retrofit.GlobalVariables.BASE_URL;

public class MainActivity extends AppCompatActivity implements RestCallback {

    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private int mYear;
    private int mMonth;
    private int mDay;
    private String date_time;
    private int mHour;
    private int mMinute;
    private TextView _dateTime;
    private Switch switchButton;
    private LinearLayout _report;
    private LinearLayout _support;
    private LinearLayout _updatePhone;
    private LinearLayout _earning;
    private String _pId;
    private String _name;
    private String _phone;
    private String _email;
    private TextView _nameAppbar;
    private TextView _mail_appbar;
    private String format;
    private ProgressDialog progressBar;
    private String _satus;
    private String _time;
    private String _rStatus;
    private StausMoldel _statusData;
    private String _rstatus;
    private RequestQueue requestQueue;
    private JsonObjectRequest mJsonRequest;
    private StausMoldel _satusModel;
    private boolean b;
    private Switch switchButtonChat;
    private String _chatStatus;
    private String _callStatus;
    private Switch switchButtonCall;
    private String _rdate;
    private String _rtime;
    private String _chatDate;
    private String _chatTime;
    private TextView _dateChat;
    private TextView _dateCall;
    private String _callDate;
    private String _callTime;
    private String ChatSatus;
    private String CallSatus;
    private int mHourChat;
    private int mMinuteChat = 00;
    private String formatChat;
    private String _time_chat;
    private int mYearChat;
    private int mMonthChat;
    private int mDayChat;
    private String date_time_chat;
    private int mYearCall;
    private int mMonthCall;
    private int mDayCall;
    private String date_time_call;
    private int mHourCall;
    private int mMinuteCall;
    private String formatCall;
    private String _time_call;
    private SharedPreferences sharedPreferences;
    private String _p;
    private LinearLayout _waitList;
    private LinearLayout _callHistory;
    private LinearLayout _chating;
    private OneSignel mOneSignal;
    private CircleImageView _profile;
    private TextView _viewBalance;
    private TextView _reportPrice;
    private TextView _chatPrice;
    private TextView _callPrice;
    private PriceModel _priceModel;
    private String _rPrice;
    private String _cPrice;
    private String _caPrice;
    private AlertDialog alertD;
    private String _cid;
    private String uname;
    private String uid;
    private String pname;
    public static final int SYSTEM_ALERT_WINDOW_PERMISSION = 7;

    private static final int APP_OVERLAY_PERMISSION = 1000;
    private Context context;
    private String _adminRLock;
    private TextView _lockedTEXT;
    private TextView _lockedTEXTCHAT;
    private TextView _lockedTEXTCALL;
    private String _adminChatLock;
    private String _adminCallLock;
    private String ddd;
    private String dddChat;
    private String dddCall;
    private JsonObjectRequest mJsonRequest1;
    private RequestQueue requestQueue1;
    private ArrayList<ChatStatusValueModel> _chatRequest;
    private String chatTimeResult;
    private String callTimeResult;
    private String reportTimeResult;
//    private String _chatNotify;
//    private String _CchatId;
//    private String _Cuid;
//    private String _Cuname;
//    private String _Cpid;
//    private String _Cpname;
//    private String minSecns;
//    private String usedSec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mOneSignal =  OneSignel.getInstance();

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getSupportActionBar().hide();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkAndRequestPermissions()) {
                // carry on the normal flow, as the case of  permissions  granted.
            }
        } else {

            finish();

        }


        sharedPreferences = getSharedPreferences("save", Context.MODE_PRIVATE);
        _name = sharedPreferences.getString("name", null); // getting String
        _pId = sharedPreferences.getString("id", null); // getting String
        _email = sharedPreferences.getString("email", null); // getting String
        _phone = sharedPreferences.getString("phone", null); // getting String




 //       Toast.makeText(this, _phone, Toast.LENGTH_LONG).show();


//
//        SharedPreferences sharedPreferences1 = getSharedPreferences("notifyID", Context.MODE_PRIVATE);
//        _CchatId = sharedPreferences1.getString("reportid", null); // getting String
//        _Cuid = sharedPreferences1.getString("uid", null); // getting String
//        _Cuname = sharedPreferences1.getString("uname", null); // getting String
//        _Cpid = sharedPreferences1.getString("pid", null); // getting String
//        _Cpname = sharedPreferences1.getString("pname", null); // getting String
//        minSecns = sharedPreferences1.getString("uMins", null); // getting String
//        usedSec = sharedPreferences1.getString("usedSec", null); // getting String
//
//
//        if(_CchatId!=null){
//
//            getChatAPI();
//
//        }
//


        Bundle extras = getIntent().getExtras();


        if (extras != null) {

            _pId = extras.getString("id");
            _name = extras.getString("name");
            _phone = extras.getString("phone");
            _email = extras.getString("email");

        }

      //  Toast.makeText(this, _pId, Toast.LENGTH_SHORT).show();


        requestQueue = Volley.newRequestQueue(this);
        requestQueue1 = Volley.newRequestQueue(this);


        _dateTime = (TextView) findViewById(R.id.date_time);
        _dateChat = (TextView) findViewById(R.id.date_timeChat);
        _dateCall = (TextView) findViewById(R.id.date_timeCall);
        _nameAppbar = (TextView) findViewById(R.id.Name_appbar);
        _mail_appbar = (TextView) findViewById(R.id.Mail_appbar);
        _viewBalance = (TextView) findViewById(R.id.viewBalance);
        _report = (LinearLayout) findViewById(R.id.report);
        _support = (LinearLayout) findViewById(R.id.support);
        _reportPrice = (TextView) findViewById(R.id.reportPrice);
        _chatPrice = (TextView) findViewById(R.id.chatPrice);
        _callPrice = (TextView) findViewById(R.id.callPrice);
        _lockedTEXT = (TextView) findViewById(R.id.lockedTEXT);
        _lockedTEXTCHAT = (TextView) findViewById(R.id.lockedTEXTCHAT);
        _lockedTEXTCALL = (TextView) findViewById(R.id.lockedTEXTCALL);

        _callHistory = (LinearLayout) findViewById(R.id.callHistory);
        _chating = (LinearLayout) findViewById(R.id.chating);
        _profile = (CircleImageView) findViewById(R.id.profile);




        _nameAppbar.setText(_name);
        _mail_appbar.setText(_email);


        switchButton = (Switch) findViewById(R.id.switchButton);
        switchButtonChat = (Switch) findViewById(R.id.switchButtonChat);
        switchButtonCall = (Switch) findViewById(R.id.switchButtonCall);
        progressBar = new ProgressDialog(this);

        progressBar.setMessage("Loading");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);

        CallstatusAPI();

        priceAPI();


//        final Handler ha=new Handler();
//
//        ha.postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//
//                if(alertD!=null) {
//
//                    alertD.dismiss();
//                }
//
//                getChatRequestAPI();
//
//
//                ha.postDelayed(this, 20000);
//            }
//
//        }, 20000);
//
//        getChatRequestAPI();



        _profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });


        _viewBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WalletActivity.class);
                startActivity(intent);
            }
        });

        _report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ReportsActivity.class);
                startActivity(intent);
            }
        });


        _callHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, CallLogActivity.class);
                startActivity(intent);


//                Intent intent = new Intent(MainActivity.this, CallingActivity.class);
//                startActivity(intent);

            }
        });
        _chating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
//                dialog.setMessage("Coming soon");
//                dialog.setTitle("Alert");
//                dialog.setPositiveButton("Okay",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog,
//                                                int which) {
//
//
//
//                            }
//                        });
//
//                AlertDialog alertDialog=dialog.create();
//                alertDialog.show();

                Intent intent = new Intent(MainActivity.this, ChatLogActivity.class);

                intent.putExtra("pid", _pId);
                intent.putExtra("pname", _name);
                startActivity(intent);


//                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
//
//                intent.putExtra("pid", _pId);
//                intent.putExtra("pname", _name);
//                startActivity(intent);

            }
        });


        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(switchButton.isChecked()){

                    switchButton.setText("ON");
                    _satus="1";
                    date_time = "0000-00-00";
                    _time = "00:00";
                     getStatusAPI();
                    _dateTime.setText("ONLINE");

                } else {

                    switchButton.setChecked(true);
                    datePicker();

                }
            }
        });


        switchButtonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(switchButtonChat.isChecked()){

                    switchButtonChat.setText("ON");
                    ChatSatus="1";
                    date_time_chat = "0000-00-00";
                    _time_chat = "00:00";

                    getChatStatusAPI();

                    _dateChat.setText("ONLINE");


                }else {

                    switchButtonChat.setChecked(true);

                    datePickerChat();

                }
            }
        });


        switchButtonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(switchButtonCall.isChecked()){

                    switchButtonCall.setText("ON");
                    CallSatus="1";
                    date_time_call = "0000-00-00";
                    _time_call = "00:00";

                    getCallStatusAPI();

                    _dateCall.setText("ONLINE");


                }else {

                   switchButtonCall.setChecked(true);

                    datePickerCall();

                }
            }
        });



        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.FOREGROUND_SERVICE}, PackageManager.PERMISSION_GRANTED);



       if (Settings.canDrawOverlays(this)) {

           // startService(new Intent(MainActivity.this, ChatService.class));

        } else {

            RuntimePermissionForUser();

            Toast.makeText(MainActivity.this, "System Alert Window Permission Is Required For Floating Widget.", Toast.LENGTH_LONG).show();

        }


      //  Toast.makeText(mOneSignal, _pId, Toast.LENGTH_SHORT).show();

        if(isServiceRunning()){

         //   Toast.makeText(MainActivity.this, "Service Running", Toast.LENGTH_SHORT).show();


        } else {

            final Intent intent = new Intent(this, ChatNotifyService.class);
            ServiceCaller(intent);

        }

        if(isSupportChatRunning()){

            stopService(new Intent(this, NotificationService.class));

        } else {

            Intent intent = new Intent(this, NotificationService.class);

            ServiceCaller(intent);
        }

//        if(isServiceRunningC()){
//
//            startService(new Intent(this, ChatReqPopService.class));
//
//        }

    }


//    private boolean isServiceRunningC() {
//        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
//        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
//            if("com.uma.astropandith.Service.NotificationService".equals(service.service.getClassName())) {
//                return true;
//            }
//        }
//        return false;
//    }



    public void RuntimePermissionForUser() {

        Intent PermissionIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getPackageName()));

        startActivityForResult(PermissionIntent, SYSTEM_ALERT_WINDOW_PERMISSION);
    }

    private boolean isSupportChatRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if("com.uma.astropandith.Service.NotificationService".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private boolean isServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if("com.uma.astropandith.Service.ChatNotifyService".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    private void ServiceCaller(Intent intent){

        startService(intent);

    }


    private boolean checkAndRequestPermissions() {
        int permissionReadPhoneState = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        int permissionProcessOutGogingCalls = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        int permissionProcessReadContacts = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        int permissionProcessReadCallLog = ContextCompat.checkSelfPermission(this, Manifest.permission.MODIFY_AUDIO_SETTINGS);
        int permissionProcessReadOver = ContextCompat.checkSelfPermission(this, Manifest.permission.SYSTEM_ALERT_WINDOW);

        int permissionWriteStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        int permissionReadStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);


        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionReadPhoneState != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (permissionProcessOutGogingCalls != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO);
        }
        if (permissionProcessReadContacts != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECEIVE_SMS);
        }
        if (permissionProcessReadCallLog != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.MODIFY_AUDIO_SETTINGS);
        }
        if (permissionWriteStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_SMS);
        }
        if (permissionReadStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }if (permissionProcessReadOver != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.SYSTEM_ALERT_WINDOW);
        }

        if (!listPermissionsNeeded.isEmpty()) {

            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;

        }

        return true;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length == 0 || grantResults == null) {
            /*If result is null*/
        } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            /*If We accept permission*/
        } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
            /*If We Decline permission*/
        }
    }

    @Override
    protected void onRestart() {

        sharedPreferences = getSharedPreferences("save", Context.MODE_PRIVATE);
        _name = sharedPreferences.getString("name", null); // getting String
        _pId = sharedPreferences.getString("id", null); // getting String
        _email = sharedPreferences.getString("email", null); // getting String
        _phone = sharedPreferences.getString("phone", null); // getting String


        CallstatusAPI();

        priceAPI();

        if(isServiceRunning()){

            //Toast.makeText(MainActivity.this, "Service Running", Toast.LENGTH_SHORT).show();


        } else {

            final Intent intent = new Intent(this, ChatNotifyService.class);
            ServiceCaller(intent);

        }


        Bundle extras1 = getIntent().getExtras();

        if (extras1 != null) {

            _p = extras1.getString("1");

            if (_p != null && _p.equals("1")) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setMessage("Please Login Again");
                dialog.setTitle("Alert");
                dialog.setPositiveButton("Okay",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                sharedPreferences = getSharedPreferences("save", Context.MODE_PRIVATE);
                                SharedPreferences.Editor edit = sharedPreferences.edit();
                                edit.clear();
                                edit.apply();
                                finish();

                            }
                        });


                AlertDialog alertDialog = dialog.create();
                alertDialog.setCancelable(false);
                alertDialog.show();

            }


        }


        super.onRestart();
    }




//    private void getChatAPI() {
//
//        //   Toast.makeText(this, reportid, Toast.LENGTH_SHORT).show();
//
//        HashMap<String, String> map = new HashMap<String, String>();
//        map.put("rid", _CchatId);
//        map.put("pid", _pId);
//        RestService.getInstance(MainActivity.this).getChatStateValues(map, new MyCallback<ArrayList<ChatStatusValueModel>>(MainActivity.this,
//                MainActivity.this, GlobalVariables.SERVICE_MODE.CHAT_SATE_VALUES));
//
//    }


    private void getStatusAPI() {

        progressBar.show();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("pid", _pId);
        map.put("rStatus", _satus);
        map.put("date", date_time);
        map.put("time", _time);
        RestService.getInstance(MainActivity.this).getStatus(map, new MyCallback<ResponseBody>(MainActivity.this,
                MainActivity.this, GlobalVariables.SERVICE_MODE.STATUS));

    }

    private void getCallStatusAPI() {

        progressBar.show();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("pid", _pId);
        map.put("callStatus", CallSatus);
        map.put("date", date_time_call);
        map.put("time", _time_call);
        RestService.getInstance(MainActivity.this).getCallStatus(map, new MyCallback<ResponseBody>(MainActivity.this,
                MainActivity.this, GlobalVariables.SERVICE_MODE.CALLSTATUS));

    }


    private void getChatStatusAPI() {

        progressBar.show();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("pid", _pId);
        map.put("chStatus", ChatSatus);
        map.put("date", date_time_chat);
        map.put("time", _time_chat);
        RestService.getInstance(MainActivity.this).getChatStatus(map, new MyCallback<ResponseBody>(MainActivity.this,
                MainActivity.this, GlobalVariables.SERVICE_MODE.CHATSTATUS));

    }

    @Override
    public void onFailure(Call call, Throwable t, GlobalVariables.SERVICE_MODE mode) {

        switch (mode) {

            case STATUS:

                Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();

                switchButton.setChecked(false);

                progressBar.dismiss();

                break;

            case CALLSTATUS:

                Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();

                switchButtonCall.setChecked(false);

                progressBar.dismiss();

                break;

            case CHATSTATUS:

                Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();

                switchButtonChat.setChecked(false);

                progressBar.dismiss();

                break;


            case CHAT_SATE_VALUES:

                Toast.makeText(this,"FAIL", Toast.LENGTH_SHORT).show();

                break;

        }
    }

    @Override
    public void onSuccess(Response response, GlobalVariables.SERVICE_MODE mode) {

        switch (mode) {

            case STATUS:

                progressBar.dismiss();

                break;


            case CALLSTATUS:


                progressBar.dismiss();

                break;

            case CHATSTATUS:


                progressBar.dismiss();


                break;

//            case CHAT_SATE_VALUES:
//
//
//                _chatRequest = (ArrayList<ChatStatusValueModel>) response.body();
//
//
//                _chatNotify = _chatRequest.get(0).getChatStatusValue();
//
//
//                if (!_chatNotify.isEmpty()) {
//
//
//                    if (_chatNotify.equals("7")) {
//
//
//                        if(minSecns!=null) {
//
//                            int mSecons = Integer.parseInt(minSecns);
//                            int second = Integer.parseInt(usedSec);
//
//                            int sec = (mSecons - second);
//                            int mins = sec / 60;
//
//                            SharedPreferences sharedPreferences2 = getSharedPreferences("notifyID", Context.MODE_PRIVATE);
//                            SharedPreferences.Editor editor2 = sharedPreferences2.edit();
//                            editor2.putString("uMins", String.valueOf(mins));
//                            editor2.putString("usedSec", String.valueOf(sec));
//                            editor2.apply();
//
//                        }
//
//
//                        Intent intent = new Intent(MainActivity.this, CoverstionActivity.class);
//                        intent.putExtra("pid", _Cpid);
//                        intent.putExtra("pname", _Cpname);
//                        intent.putExtra("uname", _Cuname);
//                        intent.putExtra("uid", _Cuid);
//                        intent.putExtra("reportid", _CchatId);
//                        intent.putExtra("ChatType", "CCHAT");
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        startActivity(intent);
//                        stopService(new Intent(this, ChatNotifyService.class));
//
//                    }
//
//                }
        }


    }

    public void CallstatusAPI(){

        mJsonRequest = new JsonObjectRequest(BASE_URL+"pandit/panditStutus.php?id="+_pId+"", null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Gson gson = new Gson();

                _satusModel = gson.fromJson(response.toString(), StausMoldel.class);

                _rStatus = _satusModel.getRstatus();
                _rdate = _satusModel.getRsedate();
                _rtime = _satusModel.getRsetime();
                _chatStatus = _satusModel.getChatstatus();
                _chatDate = _satusModel.getChsedate();
                _chatTime = _satusModel.getChsetime();
                _callStatus = _satusModel.getCallstatus();
                _callDate = _satusModel.getCsedate();
                _callTime = _satusModel.getCsetime();
                _adminRLock = _satusModel.getAdminRlock();
                _adminChatLock = _satusModel.getAdminChlock();
                _adminCallLock = _satusModel.getAdminCalock();

                _lockedTEXTCHAT = (TextView) findViewById(R.id.lockedTEXTCHAT);
                _lockedTEXTCALL = (TextView) findViewById(R.id.lockedTEXTCALL);


                    if(_adminRLock!=null) {

                    if (_adminRLock.equals("1")) {

                        _lockedTEXT.setVisibility(View.VISIBLE);

                        if (_rStatus.equals("1")) {

                            _lockedTEXT.setText("LOCKED\nONLINE");

                        } else {

                            _lockedTEXT.setText("LOCKED\nOFFLINE");

                        }
                        switchButton.setVisibility(View.GONE);

                    }

                }


                if(_adminChatLock!=null){

                    if (_adminChatLock.equals("1")){


                        _lockedTEXTCHAT.setVisibility(View.VISIBLE);

                        if (_chatStatus.equals("1")) {

                            _lockedTEXTCHAT.setText("LOCKED\nONLINE");

                        } else {

                            _lockedTEXTCHAT.setText("LOCKED\nOFFLINE");

                        }
                        switchButtonChat.setVisibility(View.GONE);

                    }

                }

                if(_adminCallLock!=null){

                    if (_adminCallLock.equals("1")){


                        _lockedTEXTCALL.setVisibility(View.VISIBLE);
                        if (_callStatus.equals("1")) {

                            _lockedTEXTCALL.setText("LOCKED\nONLINE");

                        } else {

                            _lockedTEXTCALL.setText("LOCKED\nOFFLINE");

                        }
                        switchButtonCall.setVisibility(View.GONE);

                    }

                }

                if(_rStatus!=null) {

                    if (_rStatus.equals("1")) {

                        switchButton.setChecked(true);
                        switchButton.setText("ON");

                        _dateTime.setText("ONLINE");

                    } else if (_rStatus.equals("3")) {

                        switchButton.setChecked(true);
                        switchButton.setText("ON");

                        _dateTime.setText("Busy");

                    } else {

                        switchButton.setChecked(false);
                        switchButton.setText("OFF");

                        _dateTime.setText(_rdate + " " + _rtime);

                    }


                    if (_chatStatus.equals("1")) {

                        switchButtonChat.setChecked(true);
                        switchButtonChat.setText("ON");
                        _dateChat.setText("ONLINE");


                    }  else if (_chatStatus.equals("3")) {

                        switchButtonChat.setChecked(true);
                        switchButtonChat.setText("ON");
                        _dateChat.setText("Busy");


                    } else {

                        switchButtonChat.setChecked(false);
                        switchButtonChat.setText("OFF");
                        _dateChat.setText(_chatDate + " " + _chatTime);


                    }

                    if (_callStatus.equals("1")) {

                        switchButtonCall.setChecked(true);
                        switchButtonCall.setText("ON");
                        _dateCall.setText("ONLINE");


                    } else if (_callStatus.equals("3")) {

                        switchButtonCall.setChecked(true);
                        switchButtonCall.setText("ON");
                        _dateCall.setText("Busy");


                    } else {

                        switchButtonCall.setChecked(false);
                        switchButtonCall.setText("OFF");
                        _dateCall.setText(_callDate + " " + _callTime);


                    }
                }

                getAppVer();

                        }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

              //  Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(mJsonRequest);

    }
    public void priceAPI(){

        mJsonRequest = new JsonObjectRequest(BASE_URL+"pandit/panditPriceDetails.php?pId="+_pId+"&phone="+_phone+"", null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();

                _priceModel = gson.fromJson(response.toString(), PriceModel.class);

                _rPrice = _priceModel.getReportPFprice();
                _cPrice = _priceModel.getChatPFprice();
                _caPrice = _priceModel.getCallPFprice();

                _reportPrice.setText("\u20B9 "+_rPrice+"/Report");
                _chatPrice.setText("\u20B9 "+_cPrice+"/Minute");
                _callPrice.setText("\u20B9 "+_caPrice+"/Minute");

                        }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(mJsonRequest);

    }



    private void datePicker(){

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        date_time = year+ "-" + (monthOfYear + 1) + "-" + dayOfMonth ;
                        //*************Call Time Picker Here ********************
                        ddd = dayOfMonth+ "-" +(monthOfYear + 1) + "-" + year;

                        tiemPicker();
                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        datePickerDialog.show();
        datePickerDialog.setCancelable(false);
    }

    private void tiemPicker(){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


                        mHour = hourOfDay;
                        mMinute = minute;

                        if (hourOfDay == 0) {

                            hourOfDay += 12;

                            format = "AM";
                        }
                        else if (hourOfDay == 12) {

                            format = "PM";
                        }
                        else if (hourOfDay > 12) {

                            hourOfDay -= 12;

                            format = "PM";
                        }
                        else {

                            format = "AM";
                        }

                        reportTimeResult = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);


                        _dateTime.setText(ddd+" "+reportTimeResult+format);
                        _time = reportTimeResult+format;

                        switchButton.setText("OFF");

                        switchButton.setChecked(false);

                        _satus = "0";

                        getStatusAPI();

                    }
                }, mHour, mMinute, false);

        timePickerDialog.show();

        timePickerDialog.setCancelable(false);


    }


    private void datePickerChat(){

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYearChat = c.get(Calendar.YEAR);
        mMonthChat = c.get(Calendar.MONTH);
        mDayChat = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        date_time_chat = year+ "-" + (monthOfYear + 1) + "-" + dayOfMonth ;
                        dddChat = dayOfMonth+ "-" +(monthOfYear + 1) + "-" + year;
                        //*************Call Time Picker Here ********************
                        tiemPickerChat();
                    }
                }, mYearChat, mMonthChat, mDayChat);

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        datePickerDialog.show();
        datePickerDialog.setCancelable(false);
    }

    private void tiemPickerChat(){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHourChat = c.get(Calendar.HOUR_OF_DAY);
        mMinuteChat = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        mHourChat = hourOfDay;
                        mMinuteChat = minute;

                        if (hourOfDay == 0) {

                            hourOfDay += 12;

                            formatChat = "AM";
                        }
                        else if (hourOfDay == 12) {

                            formatChat = "PM";
                        }
                        else if (hourOfDay > 12) {

                            hourOfDay -= 12;

                            formatChat = "PM";
                        }
                        else {

                            formatChat = "AM";

                        }





                        chatTimeResult = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);

                        _dateChat.setText(dddChat+" "+chatTimeResult+ formatChat);

                        _time_chat =chatTimeResult + formatChat;

                    //    Toast.makeText(MainActivity.this, _time_chat, Toast.LENGTH_SHORT).show();

                        switchButtonChat.setText("OFF");

                        ChatSatus = "0";
                        switchButtonChat.setChecked(false);

                        getChatStatusAPI();

                    }
                }, mHourChat, mMinuteChat, false);
        timePickerDialog.show();
        timePickerDialog.setCancelable(false);

    }


    private void datePickerCall(){

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYearCall = c.get(Calendar.YEAR);
        mMonthCall = c.get(Calendar.MONTH);
        mDayCall = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        date_time_call = year+ "-" + (monthOfYear + 1) + "-" + dayOfMonth ;
                        //*************Call Time Picker Here ********************
                        dddCall = dayOfMonth+ "-" +(monthOfYear + 1) + "-" + year;

                        tiemPickerCall();
                    }
                }, mYearCall, mMonthCall, mDayCall);

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        if(datePickerDialog!=null) {
            datePickerDialog.show();
        }
        datePickerDialog.setCancelable(false);
    }

    private void tiemPickerCall(){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHourCall = c.get(Calendar.HOUR_OF_DAY);
        mMinuteCall = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        mHourCall = hourOfDay;
                        mMinuteCall = minute;

                        if (hourOfDay == 0) {

                            hourOfDay += 12;

                            formatCall = "AM";
                        }
                        else if (hourOfDay == 12) {

                            formatCall = "PM";
                        }
                        else if (hourOfDay > 12) {

                            hourOfDay -= 12;

                            formatCall = "PM";
                        }
                        else {

                            formatCall = "AM";
                        }


                        callTimeResult = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);


                        _dateCall.setText(dddCall + " "+callTimeResult +formatCall);
                        _time_call = callTimeResult+formatCall;

                        switchButtonCall.setText("OFF");

                        switchButtonCall.setChecked(false);

                        CallSatus = "0";

                        getCallStatusAPI();

                    }
                }, mHourCall, mMinuteCall, false);
        timePickerDialog.show();
        timePickerDialog.setCancelable(false);

    }

    public void logout(View view) {

        AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
        dialog.setMessage("Are you sure ?");
        dialog.setTitle("Logout");
        dialog.setPositiveButton("logout",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {

                        sharedPreferences = getSharedPreferences("save", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = sharedPreferences.edit();
                        edit.clear();
                        edit.apply();

                        SharedPreferences sharedPreferences1 = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit1 = sharedPreferences1.edit();
                        edit1.clear();
                        edit1.apply();

                        SharedPreferences sharedPreferences2 = getSharedPreferences("notifyID", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit2 = sharedPreferences2.edit();
                        edit2.clear();
                        edit2.apply();

                        SharedPreferences sharedPreferences3 = getSharedPreferences("ChatC", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit3 = sharedPreferences3.edit();
                        edit3.clear();
                        edit3.apply();

                        SharedPreferences sharedPreferences4 = getSharedPreferences("Date", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit4 = sharedPreferences4.edit();
                        edit4.clear();
                        edit4.apply();

                        SharedPreferences sharedPreferences5 = getSharedPreferences("Notif", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit5 = sharedPreferences5.edit();
                        edit5.clear();
                        edit5.apply();


                        Intent intent = new Intent(MainActivity.this,LoginActivity.class);

                        startActivity(intent);
                        finish();

                    }
                });
        dialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {

                    }
                });

        AlertDialog alertDialog=dialog.create();
        alertDialog.show();

    }

    public void query(View view) {


    }

    public void chat(View view) {


    }

    private void deleteAppData() {
        try {
            // clearing app data
            String packageName = getApplicationContext().getPackageName();
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("pm clear "+packageName);
             finish();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getAppVer() {

        mJsonRequest1 = new JsonObjectRequest(BASE_URL+"users/verapp2.php?uid=9", null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                AppVersionModel appVersionModel = gson.fromJson(response.toString(), AppVersionModel.class);
                String appver =  appVersionModel.getMsg();
                int versionCode = BuildConfig.VERSION_CODE;

                int vers = Integer.parseInt(appver);

                if(vers>versionCode) {

                    LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                    View promptView = layoutInflater.inflate(R.layout.custom_layoutapp, null);

                    alertD = new AlertDialog.Builder(MainActivity.this).create();

                    Button button = (Button) promptView.findViewById(R.id.UBT);

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            final String appName = getPackageName();

                            try {

                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appName)));
                                new java.util.Timer().schedule(
                                        new java.util.TimerTask() {
                                            @Override
                                            public void run() {

                                                deleteAppData();

                                            }
                                        },
                                        5000
                                );

                            } catch (android.content.ActivityNotFoundException anfe) {

                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appName)));
                                new java.util.Timer().schedule(
                                        new java.util.TimerTask() {
                                            @Override
                                            public void run() {

                                                deleteAppData();

                                            }
                                        },
                                        5000
                                );

                            }


                        }
                    });


                    alertD.setView(promptView);

                    alertD.show();
                    alertD.setCancelable(false);

                }


//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle("Our App got Update");
//                builder.setIcon(R.mipmap.ic_launcher);
//                builder.setCancelable(false);
//                builder.setMessage("New version available, select update to update our app")
//                        .setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//
//                                final String appName = getActivity().getPackageName();
//
//                                try {
//                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appName)));
//                                } catch (android.content.ActivityNotFoundException anfe) {
//                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appName)));
//                                }
//
//
//                            }
//                        });
//
//                AlertDialog alert = builder.create();
//                alert.show();

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue1.add(mJsonRequest1);

    }



    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }



}

