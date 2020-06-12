package com.uma.astropandith.Service;


import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.PowerManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.uma.astropandith.Activitys.ChatActivity;
import com.uma.astropandith.Activitys.ChatFireBaseActivity;
import com.uma.astropandith.Activitys.CoverstionActivity;
import com.uma.astropandith.Activitys.MainActivity;
import com.uma.astropandith.Activitys.WebviewChatActivity;
import com.uma.astropandith.ChatRequestActivity;
import com.uma.astropandith.Model.ChatRequestModel;
import com.uma.astropandith.Model.ChatStatusValueModel;
import com.uma.astropandith.R;
import com.uma.astropandith.Retrofit.GlobalVariables;
import com.uma.astropandith.Retrofit.MyCallback;
import com.uma.astropandith.Retrofit.RestCallback;
import com.uma.astropandith.Retrofit.RestService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;


public class ChatNotifyService extends Service implements RestCallback {


    private ArrayList<ChatRequestModel> _chatRequest;
    private String uname;
    private String uid;
    private String pname;
    private String rowid;
    private String _pid;
    private SharedPreferences sharedPreferences;

    private static final String CHANNEL_ID = "12345";
    private SharedPreferences sharedPreferences1;
    private String _chatNotify;

    WindowManager windowManager;
    View floatingView, expandedView;
    WindowManager.LayoutParams params ;
    private Uri notification;
    private Ringtone r;
    private Disposable disposable;
    private String pid;
    private LinearLayout prBar;
    private String notifyState;
    private ArrayList<ChatStatusValueModel> _CchatRequest;
    private Disposable disposable1;
    private String uMins;
    private String request_from;
    private ArrayList<ChatStatusValueModel> _CchatRequestt;
    private String notifyStatee;


    public ChatNotifyService() {


    }


    @Override
    public void onCreate() {
        super.onCreate();


        floatingView = LayoutInflater.from(this).inflate(R.layout.bottom_sheet, null);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
        }

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);


        expandedView = floatingView.findViewById(R.id.Layout_Expended);

      //  collapsedView = floatingView.findViewById(R.id.Layout_Collapsed);

         prBar = (LinearLayout) floatingView.findViewById(R.id.prBar);

        Button btnAdd1 = (Button) floatingView.findViewById(R.id.cancel);

        Button btnAdd2 = (Button) floatingView.findViewById(R.id.accept);

        btnAdd1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                prBar.setVisibility(View.VISIBLE);


                getChatRejectAPI();
                getWaitlistOnlineAPI();


            }
        });

        btnAdd2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                 prBar.setVisibility(View.VISIBLE);

                     getCheckAPI();

            }
        });

//        floatingView.findViewById(R.id.Widget_Close_Icon).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                stopSelf();
//
//            }
//        });





        expandedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                collapsedView.setVisibility(View.VISIBLE);
//                expandedView.setVisibility(View.GONE);

            }
        });

        floatingView.findViewById(R.id.Layout_Expended).setOnTouchListener(new View.OnTouchListener() {
            int X_Axis, Y_Axis;
            float TouchX, TouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        X_Axis = params.x;
                        Y_Axis = params.y;
                        TouchX = event.getRawX();
                        TouchY = event.getRawY();
                        return true;

                    case MotionEvent.ACTION_UP:

                      //  collapsedView.setVisibility(View.GONE);
                      //  expandedView.setVisibility(View.VISIBLE);

                        return true;

                    case MotionEvent.ACTION_MOVE:

                        params.x = X_Axis + (int) (event.getRawX() - TouchX);
                        params.y = Y_Axis + (int) (event.getRawY() - TouchY);
                        windowManager.updateViewLayout(floatingView, params);
                        
                        return true;
                }
                return false;
            }
        });

    }



    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        super.onStartCommand(intent, flags, startId);


        //   onTaskRemoved(intent);


        sharedPreferences = getSharedPreferences("save", Context.MODE_PRIVATE);
        _pid = sharedPreferences.getString("id", null); // getting String



            disposable = Observable.interval(1000, 10000,
                    TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::getChatRequestAPI, this::onError);



        notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        r = RingtoneManager.getRingtone(getApplicationContext(), notification);



         return START_STICKY;

    }


    private void onError(Throwable throwable) {
        Toast.makeText(this, "OnError in Observable Timer",
                Toast.LENGTH_LONG).show();
    }
    private void onError1(Throwable throwable) {
        Toast.makeText(this, "OnError in Observable Timer",
                Toast.LENGTH_LONG).show();
    }



    @Override
    public void onDestroy() {

        if(disposable!=null)

        if (!disposable.isDisposed()) {

            disposable.dispose();

        }
        if(disposable1!=null)

        if (!disposable1.isDisposed()) {

            disposable1.dispose();

        }


   //     if(!(windowManager ==null))

  //      windowManager.removeView(floatingView);


        // if (floatingView != null) windowManager.removeView(floatingView);
//        Intent broadcastIntent = new Intent();
//        broadcastIntent.setAction("restartservice");
//        broadcastIntent.setClass(this, RestarterService.class);
//        this.sendBroadcast(broadcastIntent);


//        Intent broadcastIntent = new Intent();
//        broadcastIntent.setAction("restartservic");
//        broadcastIntent.setClass(this, MyBroadCast.class);
//        this.sendBroadcast(broadcastIntent);

        super.onDestroy();


    }

//    @Override
//    public void onTaskRemoved(Intent rootIntent) {
//        Intent restartServiceIntent = new Intent(getApplicationContext(),this.getClass());
//        restartServiceIntent.setPackage(getPackageName());
//        startService(restartServiceIntent);
//        super.onTaskRemoved(rootIntent);
//    }


    private void getChatRequestAPI(Long aLong) {

   //     Toast.makeText(this, _pid, Toast.LENGTH_SHORT).show();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("pid", _pid);
        RestService.getInstance(ChatNotifyService.this).getChatRequest(map, new MyCallback<ArrayList<ChatRequestModel>>(ChatNotifyService.this,
                ChatNotifyService.this, GlobalVariables.SERVICE_MODE.CHAT_REQUEST));

    }

    private void getCheckAPI(Long aLong) {

       // Toast.makeText(this, pid, Toast.LENGTH_SHORT).show();
//
//        HashMap<String, String> map = new HashMap<String, String>();
//        map.put("pid", _pid);
//        RestService.getInstance(ChatNotifyService.this).getChatRequest(map, new MyCallback<ArrayList<ChatRequestModel>>(ChatNotifyService.this,
//                ChatNotifyService.this, GlobalVariables.SERVICE_MODE.CHAT_REQUEST1));

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("rid", rowid);
        map.put("pid", _pid);
        RestService.getInstance(ChatNotifyService.this).getChatStateValues(map, new MyCallback<ArrayList<ChatStatusValueModel>>(ChatNotifyService.this,
                ChatNotifyService.this, GlobalVariables.SERVICE_MODE.CHAT_SATE_VALUES));

    }

    private void getCheckAPI() {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("rid", rowid);
        map.put("pid", _pid);
        RestService.getInstance(ChatNotifyService.this).getChatStateValuesbal(map, new MyCallback<ArrayList<ChatStatusValueModel>>(ChatNotifyService.this,
                ChatNotifyService.this, GlobalVariables.SERVICE_MODE.CHAT_SATE_VALUES_BAL));

    }

    private void getChatRejectAPI() {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("rid", rowid);
        map.put("StatusValue", "3");
        map.put("pid", _pid);
        map.put("uid", uid);
        RestService.getInstance(ChatNotifyService.this).getChatStatusCancel(map, new MyCallback<ResponseBody>(ChatNotifyService.this,
                ChatNotifyService.this, GlobalVariables.SERVICE_MODE.CHAT_SATUS_CANCEL));

    }

    private void getChatAcceptAPI() {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("rid", rowid);
        map.put("StatusValue", "2");
        map.put("pid", _pid);
        map.put("uid", uid);
        RestService.getInstance(ChatNotifyService.this).getChatSstatus(map, new MyCallback<ResponseBody>(ChatNotifyService.this,
                ChatNotifyService.this, GlobalVariables.SERVICE_MODE.CHAT_SATUS_UPDATE));

    }


    private void getWaitlistOnlineAPI() {


        HashMap<String, String> map = new HashMap<String, String>();
        map.put("pid", _pid);
        RestService.getInstance(ChatNotifyService.this).getStatusWaitlisOnline(map, new MyCallback<ResponseBody>(ChatNotifyService.this,
                ChatNotifyService.this, GlobalVariables.SERVICE_MODE.STATUS_WAITLIST_ONLINE));

    }


    private void getChatCancelAPI() {


        HashMap<String, String> map = new HashMap<String, String>();
        map.put("rid", rowid);
        map.put("StatusValue", "4");
        map.put("pid", _pid);
        map.put("uid", uid);
        RestService.getInstance(ChatNotifyService.this).getNoResponce(map, new MyCallback<ResponseBody>(ChatNotifyService.this,
                ChatNotifyService.this, GlobalVariables.SERVICE_MODE.NO_RESPONCE));


    }



    @Override
    public void onFailure(Call call, Throwable t, GlobalVariables.SERVICE_MODE mode) {

        switch (mode) {


            case CHAT_REQUEST:

            //    Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();

//
//                if(r.isPlaying()){
//                    r.stop();
//
//                    Toast.makeText(this, "11", Toast.LENGTH_SHORT).show();
//                    windowManager.removeView(floatingView);
//
//
//
//                }

                break;

            case CHAT_SATE_VALUES:

                //Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
//
//                if(r.isPlaying()){
//                    r.stop();
//
//
//                    windowManager.removeView(floatingView);
//                    disposable.dispose();
//                    disposable1.dispose();
//
//
//                    stopService(new Intent(this, ChatNotifyService.class));
//
//                    Intent intent3 = new Intent(ChatNotifyService.this, MainActivity.class);
//                    intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent3);
//                }

                break;

            case CHAT_SATE_VALUES_BAL:

                //Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
//
//                if(r.isPlaying()){
//                    r.stop();
//
//
//                    windowManager.removeView(floatingView);
//                    disposable.dispose();
//                    disposable1.dispose();
//
//
//                    stopService(new Intent(this, ChatNotifyService.class));
//
//                    Intent intent3 = new Intent(ChatNotifyService.this, MainActivity.class);
//                    intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent3);
//                }

                break;

            case CHAT_SATUS_CANCEL:

                //r.stop();

                Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();


                break;

            case CHAT_SATUS_UPDATE:

               // r.stop();

               // disposable.isDisposed();

                Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();


            break;


            case NO_RESPONCE:

                Toast.makeText(this, "server fail", Toast.LENGTH_SHORT).show();

                break;


            case STATUS_WAITLIST_ONLINE:

                Toast.makeText(this, "No Data Foun", Toast.LENGTH_SHORT).show();

                break;

        }

    }

    @Override
    public void onSuccess(Response response, GlobalVariables.SERVICE_MODE mode) {

        switch (mode) {

            case CHAT_REQUEST:

                _chatRequest = (ArrayList<ChatRequestModel>) response.body();

                rowid = _chatRequest.get(0).getId();
                pname = _chatRequest.get(0).getPname();
                uname = _chatRequest.get(0).getUname();
                uid = _chatRequest.get(0).getUid();
                pid = _chatRequest.get(0).getPid();
                uMins = _chatRequest.get(0).getMins();
                request_from = _chatRequest.get(0).getRequest_from();

                _chatNotify = _chatRequest.get(0).getChatStatusValue();

  //              Toast.makeText(this, _chatNotify, Toast.LENGTH_SHORT).show();

                TextView name = (TextView) floatingView.findViewById(R.id.name_u);
                name.setText(uname);

                if (!pname.isEmpty()){

                  //  Toast.makeText(this, reportid, Toast.LENGTH_SHORT).show();

//                    SharedPreferences sharedPreferences = getSharedPreferences("ChatC",Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor1 = sharedPreferences.edit();
//                    editor1.putString("chatId",rowid);
//                    editor1.putString("uid",uid);
//                    editor1.putString("uname",uname);
//                    editor1.putString("pid",pid);
//                    editor1.putString("pname",pname);
//                    editor1.commit();
//                    editor1.apply();

                    sharedPreferences1 = getSharedPreferences("notifyID",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences1.edit();
                    editor.putString("reportid",rowid);
                    editor.putString("uid",uid);
                    editor.putString("uname",uname);
                    editor.putString("pid",_pid);
                    editor.putString("pname",pname);
                    editor.putString("uMins",uMins);
                    editor.commit();
                    editor.apply();

                  //  Toast.makeText(this, uMins+" "+rowid+ "   "+ _pid, Toast.LENGTH_SHORT).show();

                    try {


                    if(_chatNotify.equals("8")){

                        NotificationCompat.Builder builder1 = new NotificationCompat.Builder(this, CHANNEL_ID)
                                .setSmallIcon(R.drawable.ic_launcher_foreground)
                                .setContentTitle("Mera Astro Site")
                                .setContentText("Chat Request Rejected from Customer")
                                .setColor(Color.BLUE)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                // Set the intent that will fire when the user taps the notification
                                .setAutoCancel(true);

                        NotificationManagerCompat notificationManagerr1 = NotificationManagerCompat.from(this);

                        notificationManagerr1.notify(12345, builder1.build());


                        disposable.dispose();

                        if (r.isPlaying()) {
                            r.stop();

                            windowManager.removeView(floatingView);
                        }

                        Intent intent1 = new Intent(ChatNotifyService.this, MainActivity.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent1);
                        stopService(new Intent(this, ChatNotifyService.class));


                    } else if(_chatNotify.equals("9")) {

                        NotificationCompat.Builder builder1 = new NotificationCompat.Builder(this, CHANNEL_ID)
                                .setSmallIcon(R.drawable.ic_launcher_foreground)
                                .setContentTitle("Mera Astro Site")
                                .setContentText(" No Response from Customer")
                                .setColor(Color.BLUE)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                // Set the intent that will fire when the user taps the notification
                                .setAutoCancel(true);

                        NotificationManagerCompat notificationManagerr1 = NotificationManagerCompat.from(this);

                        notificationManagerr1.notify(12345, builder1.build());

                        disposable.dispose();

                        stopService(new Intent(this, ChatNotifyService.class));

                    }


                    else if(_chatNotify.equals("1")) {

                        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
                        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                                "MyApp::MyWakelockTag");
                        wakeLock.acquire();

                        disposable.dispose();

//                        notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
//                       r = RingtoneManager.getRingtone(getApplicationContext(), notification);

                       if(r.isPlaying()){
                       r.stop();


                       } else {

                           r.play();
                           windowManager.addView(floatingView, params);

                       }


                        Intent notificationIntent = new Intent(this, ChatRequestActivity.class);
                        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

//                        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
//                                .setContentTitle("Mera Astro Site")
//                                .setContentText("New Chat Request").setSmallIcon(R.drawable.ic_launcher_foreground)
//                                .setColor(Color.BLUE)
//                                .setContentIntent(pendingIntent)
//                                .build();


                        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                                .setSmallIcon(R.drawable.ic_launcher_foreground)
                                .setContentTitle("Mera Astro Site")
                                .setContentText("New Chat Request from Customer")
                                .setColor(Color.BLUE)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                // Set the intent that will fire when the user taps the notification
                               // .setContentIntent(pendingIntent)
                                .setAutoCancel(true);


                        NotificationManagerCompat notificationManagerr = NotificationManagerCompat.from(this);

                        notificationManagerr.notify(12345, builder.build());

                        disposable1 = Observable.interval(1000, 10000,
                                TimeUnit.MILLISECONDS)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(this::getCheckAPI, this::onError1);

                        //stopService(new Intent(this, ChatNotifyService.class));

                    }

                      // startForeground(1, notification);

//                        NotificationChannel notificationChannel = null;
//
//                        NotificationManager notificationManager;
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                            notificationChannel = new NotificationChannel(CHANNEL_ID, "Astro Pandith Site", NotificationManager.IMPORTANCE_DEFAULT);
//
//                            notificationManager = getSystemService(NotificationManager.class);
//                            notificationManager.createNotificationChannel(notificationChannel);
//                        }

                    }

                    catch (Exception e){
                        e.printStackTrace();
                    }

                }

                break;

            case NO_RESPONCE:

                Toast.makeText(this, "Server Cancel", Toast.LENGTH_SHORT).show();
                disposable.dispose();
                stopService(new Intent(this, ChatNotifyService.class));
                break;


            case CHAT_SATUS_UPDATE:

                // Toast.makeText(this, _rid +"  "+_pid +"  "+ _uid, Toast.LENGTH_SHORT).show();

                request_from = "0";

                windowManager.removeView(floatingView);
                r.stop();

                sharedPreferences1 = getSharedPreferences("notifyID",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences1.edit();
                editor.putString("reportid",rowid);
                editor.putString("uid",uid);
                editor.putString("uname",uname);
                editor.putString("pid",_pid);
                editor.putString("pname",pname);
                editor.putString("uMins",uMins);
                editor.apply();

                disposable.dispose();
                disposable1.dispose();


                if (request_from.equals("0")) {

                    // stopSelf();
                    Intent intent = new Intent(ChatNotifyService.this, ChatFireBaseActivity.class);
                    intent.putExtra("pid", pid);
                    intent.putExtra("pname", pname);
                    intent.putExtra("uname", uname);
                    intent.putExtra("uid", uid);
                    intent.putExtra("reportid", rowid);
                    intent.putExtra("uMins", uMins);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    stopService(new Intent(this, ChatNotifyService.class));

                } else if(request_from.equals("1")){

                    Intent intent = new Intent(ChatNotifyService.this, WebviewChatActivity.class);
                    intent.putExtra("pid", pid);
                    intent.putExtra("pname", pname);
                    intent.putExtra("uname", uname);
                    intent.putExtra("uid", uid);
                    intent.putExtra("reportid", rowid);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    stopService(new Intent(this, ChatNotifyService.class));


                }


                break;


            case CHAT_SATUS_CANCEL:

                r.stop();

                windowManager.removeView(floatingView);

                disposable.dispose();
                disposable1.dispose();

                stopService(new Intent(this, ChatNotifyService.class));
                // stopSelf();
                Intent intent1 = new Intent(ChatNotifyService.this, MainActivity.class);
//                intent1.putExtra("pid",pid);
//                intent1.putExtra("pname",pname);
//                intent1.putExtra("uname",uname);
//                intent1.putExtra("uid",uid);
//                intent1.putExtra("reportid",rowid);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent1);

                break;


            case CHAT_SATE_VALUES:

                _CchatRequest = (ArrayList<ChatStatusValueModel>) response.body();


                notifyState = _CchatRequest.get(0).getChatStatusValue();

               // Toast.makeText(this, notifyState, Toast.LENGTH_SHORT).show();

                if(notifyState!=null) {

                    if (notifyState.equals("8")) {

                        windowManager.removeView(floatingView);
                        disposable1.dispose();

                        disposable.dispose();

                        stopService(new Intent(this, ChatNotifyService.class));

                        Intent intent3 = new Intent(ChatNotifyService.this, MainActivity.class);
                        intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent3);


                        if (r.isPlaying()) {

                            r.stop();

                        }

                    } else if (notifyState.equals("4")) {

                        windowManager.removeView(floatingView);
                        disposable1.dispose();

                        disposable.dispose();

                        stopService(new Intent(this, ChatNotifyService.class));

                        Intent intent3 = new Intent(ChatNotifyService.this, MainActivity.class);
                        intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent3);

                        if (r.isPlaying()) {

                            r.stop();

                        }

                    } else if (notifyState.isEmpty()) {

                        if (r.isPlaying()) {
                            r.stop();

                            windowManager.removeView(floatingView);

                            disposable.dispose();
                            disposable1.dispose();

                            stopService(new Intent(this, ChatNotifyService.class));

                            Intent intent3 = new Intent(ChatNotifyService.this, MainActivity.class);
                            intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent3);
                        }
                    }
                }

                break;



            case CHAT_SATE_VALUES_BAL:

                _CchatRequestt = (ArrayList<ChatStatusValueModel>) response.body();


                notifyStatee = _CchatRequestt.get(0).getChatStatusValue();

              //  Toast.makeText(this, notifyStatee, Toast.LENGTH_SHORT).show();

                if(notifyStatee!=null) {

                    if (notifyStatee.equals("8")) {

                        windowManager.removeView(floatingView);

                        disposable1.dispose();

                        disposable.dispose();

                        stopService(new Intent(this, ChatNotifyService.class));

                        Intent intent3 = new Intent(ChatNotifyService.this, MainActivity.class);
                        intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent3);


                        if (r.isPlaying()) {

                            r.stop();

                        }

                    }else {


                        getChatAcceptAPI();


                    }
                }

                break;


            case STATUS_WAITLIST_ONLINE:

                //Toast.makeText(this, "Ss", Toast.LENGTH_SHORT).show();


                break;


        }


    }
}
