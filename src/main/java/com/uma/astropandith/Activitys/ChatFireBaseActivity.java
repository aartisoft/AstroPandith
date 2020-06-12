package com.uma.astropandith.Activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.uma.astropandith.Adapters.MessageAdapter;
import com.uma.astropandith.Model.Chat;
import com.uma.astropandith.Model.ChatAttachModel;
import com.uma.astropandith.Model.ChatRequestModel;
import com.uma.astropandith.Model.ChatStatusValueModel;
import com.uma.astropandith.Model.UserChatStatus;
import com.uma.astropandith.R;
import com.uma.astropandith.Retrofit.GlobalVariables;
import com.uma.astropandith.Retrofit.MyCallback;
import com.uma.astropandith.Retrofit.RestCallback;
import com.uma.astropandith.Retrofit.RestService;
import com.uma.astropandith.Service.RemoveAppTrack;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import static com.uma.astropandith.Retrofit.GlobalVariables.BASE_URL;

public class ChatFireBaseActivity extends AppCompatActivity implements RestCallback {


    private RecyclerView recyclerView;
    private ImageView profile_image;
    private TextView username;
    private TextView text_send;
    private String _pid;
    private String _pname;
    private boolean notify = false;
    private DatabaseReference reference;
    private ArrayList<Chat> mchat;
    private MessageAdapter messageAdapter;
    private ValueEventListener seenListener;
    private ProgressDialog progressBar;
    private ImageButton btn_send;
    private String userid;
    private String reciverid;
    private String _status;
    private AlertDialog alertD;
    private ArrayList<UserChatStatus> _chatUseRespnce;
    private String _reportid;
    private SharedPreferences sharedPreferences1;
    private String _chatId;
    private CountUpTimer timer;
    private String bitmapImage;
    private Uri filePath;
    private Bitmap resizeBitmap;
    private ArrayList<ChatAttachModel> _ChatAttachModel;
    private String _imageURL;
    private ImageView _selectImage;
    private ArrayList<ChatStatusValueModel> _chatRequest;
    private String _chatNotify;
    private Handler ha;
    private Disposable disposable;
    private CountDownTimer TTTTT;
    private WebView mWebView;
    private String url;


    private String uMins;
    private TextView _time_show;
    private CountDownTimer uMisTime;
    private String FIRST = "FIRST";
    private int minSecns;
    private AlertDialog.Builder dialog2;
    private AlertDialog alertDialog2;
    private Date currentTime;
    private Calendar calander;
    private SimpleDateFormat simpledateformat;
    private SimpleDateFormat simpleDate;
    private String simpleTime;
    private TextView timerTv;
    private String time;
    private Integer tTime;
    private boolean ImageChoose= true;
    private JsonObjectRequest mJsonRequest;
    private RequestQueue requestQueue;
    private String _serviceStarted;
    private CountDownTimer timeM;
    private Context Context;
    private ArrayList<ChatRequestModel> _CchatRequest;
    private String notifyState;
    private String rowid;
    private String pname;
    private String uid;
    private String uname;
    private ArrayList<ChatStatusValueModel> _chatRequestb;
    private String _chatNotifyb;
    private FirebaseFirestore db;
    private Button _endchat;
    private Timer timeDueLow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_firebase_chat);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Loading");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(true);
       // progressBar.show();

        Bundle extras = getIntent().getExtras();


        if (extras != null) {

            _pid = extras.getString("pid");
            _pname = extras.getString("pname");
            uname = extras.getString("uname");
            uid = extras.getString("uid");
            _chatId = extras.getString("reportid");
            uMins = extras.getString("uMins");

           // Toast.makeText(this, uname, Toast.LENGTH_SHORT).show();

        }

        requestQueue = Volley.newRequestQueue(this);


        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        profile_image = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);
        text_send = findViewById(R.id.text_send);
        btn_send = findViewById(R.id.btn_send);
        _selectImage = findViewById(R.id.selectImage);
        _time_show = findViewById(R.id.time_show);
        timerTv = findViewById(R.id.timer);
        _endchat = findViewById(R.id.endchat);



        userid = _chatId;
        reciverid = uid;

       // Toast.makeText(this, _uid + "  "+ reportid, Toast.LENGTH_SHORT).show();


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notify = true;
                String msg = text_send.getText().toString();
                if (!msg.equals("")){

                  //  sendMessage(userid, reciverid, msg);

                    sendMessageFireStore(_pid, uid, msg);


                } else {
                    Toast.makeText(ChatFireBaseActivity.this, "You can't send empty message", Toast.LENGTH_SHORT).show();
                }
                text_send.setText("");
            }
        });

        username.setText(uname);


       db = FirebaseFirestore.getInstance();
//        db.collection("chat").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//
//                getMesages(_pid, uid);
//                username.setText(uname);
//
//            }
//        });

        mchat = new ArrayList<>();

        db.collection("chat").whereEqualTo("chatid", _chatId).orderBy("time", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {

                        if (e != null) {

                            return;
                        }

                        mchat.clear();

                        for (QueryDocumentSnapshot doc : value) {

                            Chat chat = doc.toObject( Chat.class);


                            if (chat.getChatid().equals(_chatId)) {

                                if (chat.getReceiver().equals(_pid) && chat.getSender().equals(uid) ||
                                        chat.getReceiver().equals(uid) && chat.getSender().equals(_pid)) {
                                    mchat.add(chat);
//                                    Collections.reverse(mchat); // ADD THIS LINE TO REVERSE ORDER!
                                }

                            }

                            messageAdapter = new MessageAdapter(ChatFireBaseActivity.this, mchat, _pid);
                            recyclerView.setAdapter(messageAdapter);

                        }


                    }
                });




//
//        reference = FirebaseDatabase.getInstance().getReference("Users").child(reciverid);
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                username.setText(uname);
//
//                readMesagges(userid, reciverid);
//
//               // Toast.makeText(CoverstionActivity.this, "Sttttttt", Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        if(uMins!=null) {

            if(!uMins.equals("0")) {

                int InTime = Integer.valueOf(uMins);

                minSecns = (int) (InTime * 60000);

            }

        }

    //minSecns = 100000;


       uMisTime =  new CountDownTimer(minSecns, 1000) {

            public void onTick(long millisUntilFinished) {

                // long minutes = (millisUntilFinished % 3600) / 60;
                //  Toast.makeText(ChatActivity.this, millisUntilFinished +" ", Toast.LENGTH_SHORT).show();

                updateCountDownText(millisUntilFinished);

                // _time_show.setText(String.valueOf(minutes));

            }

            public void onFinish() {

                timer.cancel();

                disposable.dispose();

                if (progressBar != null){
                    if (!progressBar.isShowing()) {
                        progressBar.show();
                    }
                }

                timeDueLow = new Timer();

                timeDueLow.schedule(new TimerTask() {
                    @Override
                    public void run() {

                        ImageChoose = true;

                        _time_show.setText("Low Balance Chat will end soon");

                        time = timerTv.getText().toString();

                        getChatCheckAPI();

                        timeDueLow.cancel();

                    }
                }, 10000);

            }

        };


        disposable = Observable.interval(1000, 7000,
                TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this:: getChatAPI, this::onError);


        timer = new CountUpTimer(1000000000) {
            public void onTick(int second) {

                timerTv.setText(String.valueOf(second));


            }
        };


        _selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(CoverstionActivity.this, "this activity has bugs", Toast.LENGTH_LONG).show();
                selectImage();
            }
        });


        dialog2 =new AlertDialog.Builder(ChatFireBaseActivity.this);
        dialog2.setMessage("Kindly wait for the user to accept the chat Don't Do any Operations");
        dialog2.setTitle("Waiting for User");

        alertDialog2=dialog2.create();

        alertDialog2.getWindow().setGravity(Gravity.TOP);
        alertDialog2.show();
        alertDialog2.setCancelable(false);
        alertDialog2.getWindow().getAttributes();

        TextView textView = (TextView) alertDialog2.findViewById(android.R.id.message);
        textView.setTextSize(12);

        TTTTT = new CountDownTimer(60000*10, 1000) {

            public void onTick(long millisUntilFinished) {

                // long minutes = (millisUntilFinished % 3600) / 60;
                //  Toast.makeText(ChatActivity.this, millisUntilFinished +" ", Toast.LENGTH_SHORT).show();

                updateText(millisUntilFinished);
                // _time_show.setText(String.valueOf(minutes));

            }

            public void onFinish() {

//                if(_chatNotify.equals("2")){
//
//                    getChatServerCancelAPI();
//                    getWaitlistOnlineAPI();
//                    // getChatEndAPI();
//                    getWiatToOnline();
//
//                }
            }

        };

        TTTTT.start();

    }


    private void sendMessageFireStoreFIRST(String sender, final String receiver, String message) {

         db = FirebaseFirestore.getInstance();

        // Create a new user with a first and last name
        Map<String, Object> chats = new HashMap<>();
        chats.put("chatid", _chatId);
        chats.put("sender", sender);
        chats.put("receiver", receiver);
        chats.put("message", message);
        chats.put("image", "null");
        chats.put("time", getDateTimeSec());


        // Add a new document with a generated ID
        db.collection("chat")
                .add(chats)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.w("TAG", "Error adding document", e);


                    }
                });


    }
    private void sendMessageFireStore(String sender, final String receiver, String message) {

         db = FirebaseFirestore.getInstance();

        // Create a new user with a first and last name
        Map<String, Object> chats = new HashMap<>();
        chats.put("chatid", _chatId);
        chats.put("sender", sender);
        chats.put("receiver", receiver);
        chats.put("message", message);
        chats.put("image", "null");
        chats.put("time", getDateTimeSec());


        // Add a new document with a generated ID
        db.collection("chat")
                .add(chats)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.w("TAG", "Error adding document", e);


                    }
                });


    }
    private void sendImage(String sender, final String receiver, String image) {

         db = FirebaseFirestore.getInstance();

        // Create a new user with a first and last name
        Map<String, Object> chats = new HashMap<>();
        chats.put("chatid", _chatId);
        chats.put("sender", sender);
        chats.put("receiver", receiver);
        chats.put("message", "null");
        chats.put("image", image);
        chats.put("time", getDateTimeSec());


        // Add a new document with a generated ID
        db.collection("chat")
                .add(chats)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.w("TAG", "Error adding document", e);

                    }
                });

    }

    public void getMesages(final String myid, final String userid) {

        mchat = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // [START get_all_users]
        db.collection("chat").orderBy("time", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        mchat.clear();

                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Chat chat = document.toObject( Chat.class);

                                if(chat.getChatid().equals(_chatId)) {

                                    if (chat.getReceiver().equals(myid) && chat.getSender().equals(userid) ||
                                            chat.getReceiver().equals(userid) && chat.getSender().equals(myid)) {
                                        mchat.add(chat);
//                                    Collections.reverse(mchat); // ADD THIS LINE TO REVERSE ORDER!
                                    }

                                }


                                messageAdapter = new MessageAdapter(ChatFireBaseActivity.this, mchat, myid);
                                recyclerView.setAdapter(messageAdapter);

                              //  progressBar.dismiss();

                            }
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
        // [END get_all_users]
    }




    private void readMesagges(final String myid, final String userid){

        mchat = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mchat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(myid) && chat.getSender().equals(userid) ||
                            chat.getReceiver().equals(userid) && chat.getSender().equals(myid)){
                        mchat.add(chat);
                    }
                    messageAdapter = new MessageAdapter(ChatFireBaseActivity.this, mchat,myid);
                    recyclerView.setAdapter(messageAdapter);

                    if(progressBar != null)
                        if(progressBar.isShowing())
                            progressBar.dismiss();


                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void sendMessage(String sender, final String receiver, String message){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        hashMap.put("time", getDateTimeSec());
        hashMap.put("isseen", false);

        reference.child("Chats").push().setValue(hashMap);


        // add user to chat fragment
        final DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(userid)
                .child(reciverid);

        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){

                    chatRef.child("id").setValue(reciverid);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });

        final DatabaseReference chatRefReceiver = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(reciverid)
                .child(userid);
        chatRefReceiver.child("id").setValue(userid);

        final String msg = message;

        reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (notify) {

                    //sendNotifiaction(reciverid, _pname, msg);
                }

                notify = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendMe(String sender, final String receiver, String image ){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("image", image);
        hashMap.put("time", getDateTimeSec());

        reference.child("Chats").push().setValue(hashMap);


        // add user to chat fragment
        final DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(userid)
                .child(reciverid);

        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){

                    chatRef.child("id").setValue(reciverid);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });

        final DatabaseReference chatRefReceiver = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(reciverid)
                .child(userid);
        chatRefReceiver.child("id").setValue(userid);

        // final String msg = message;

        reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (notify) {

                    //sendNotifiaction(reciverid, _pname, msg);
                }

                notify = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void ServiceCaller(Intent intent){

        startService(intent);

    }


    @Override
    protected void onRestart() {


        if(_serviceStarted!=null) {

            if(_serviceStarted.equals("TRUE")) {

                timeM.cancel();

                Intent intent1 = new Intent(this, RemoveAppTrack.class);
                stopService(intent1);

            }

        }

        super.onRestart();
    }



    @Override
    protected void onStop() {

        SharedPreferences sharedPreferences2= getSharedPreferences("notifyID",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sharedPreferences2.edit();
        editor1.putString("uMins", String.valueOf(TimeUnit.MILLISECONDS.toSeconds(minSecns)));
        editor1.putString("usedSec", timerTv.getText().toString());
        editor1.apply();

        SharedPreferences sharedPreferences = getSharedPreferences("ChatC",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("chatId",_chatId);
        editor.putString("uid",uid);
        editor.putString("uname",uname);
        editor.putString("pid",_pid);
        editor.putString("pname",_pname);
        editor.putString("sec", timerTv.getText().toString());
        editor.commit();
        editor.apply();


        if(timeDueLow!=null){

            timeDueLow.cancel();

        }

        if(!ImageChoose) {

            timeM =  new CountDownTimer(2*60000, 2000) {

                public void onTick(long millisUntilFinished) {

                    // long minutes = (millisUntilFinished % 3600) / 60;
                    //  Toast.makeText(ChatActivity.this, millisUntilFinished +" ", Toast.LENGTH_SHORT).show();
                    Toast.makeText(ChatFireBaseActivity.this, "Meraastro Chat Running", Toast.LENGTH_SHORT).show();

                }

                public void onFinish() {

                    if(_chatId!=null) {

                        time = timerTv.getText().toString();
                        if (!_chatNotify.equals("51")) {

                            getChatDataAPI();

                            Toast.makeText(ChatFireBaseActivity.this, "Chat Ended", Toast.LENGTH_SHORT).show();

                        } else if (!_chatNotify.equals("5")) {

                            getChatDataAPI();

                            Toast.makeText(ChatFireBaseActivity.this, "Chat Ended", Toast.LENGTH_SHORT).show();
                        } else if (!_chatNotify.equals("61")) {

                            getChatDataAPI();

                            Toast.makeText(ChatFireBaseActivity.this, "Chat Ended", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            };

            timeM.start();

//
//            Intent intent1 = new Intent(this, RemoveAppTrack.class);
//            ServiceCaller(intent1);

            _serviceStarted = "TRUE";


        }

        super.onStop();

    }



    private void onError(Throwable throwable) {
        Toast.makeText(this, "OnError in Observable Timer",
                Toast.LENGTH_LONG).show();
    }


    @Override
    public void onBackPressed() {

        Toast.makeText(getApplicationContext(), "Back press disabled!", Toast.LENGTH_SHORT).show();

    }


    private String getDateTime() {

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);

    }

    private String getDateTimeSec() {

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));

        Date date = new Date();
        return dateFormat.format(date);

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



    private void ChatUserStateAPI() {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("pid", _pid);
        RestService.getInstance(ChatFireBaseActivity.this).getUserChatState(map, new MyCallback<ArrayList<UserChatStatus>>(ChatFireBaseActivity.this,
                ChatFireBaseActivity.this, GlobalVariables.SERVICE_MODE.USER_CHAT_STATE));

    }


    private void getChatServerCancelAPI() {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("rid", _chatId);
        map.put("StatusValue","58");
        map.put("pid", _pid);
        map.put("uid", uid);
        RestService.getInstance(ChatFireBaseActivity.this).getNoResponceFromUser(map, new MyCallback<ResponseBody>(ChatFireBaseActivity.this,
                ChatFireBaseActivity.this, GlobalVariables.SERVICE_MODE.CHATSATUS_NORESPONCE_FROM_USER));


    }

    private void chatAtachment() {


        HashMap<String, String> map = new HashMap<String, String>();
        map.put("image", bitmapImage);
        RestService.getInstance(ChatFireBaseActivity.this).getChatAttachment(map, new MyCallback<ArrayList<ChatAttachModel>>(ChatFireBaseActivity.this,
                ChatFireBaseActivity.this, GlobalVariables.SERVICE_MODE.CHAT_ATACHMENT));

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
        map.put("uid", uid);
        RestService.getInstance(ChatFireBaseActivity.this).getChatSstatus(map, new MyCallback<ResponseBody>(ChatFireBaseActivity.this,
                ChatFireBaseActivity.this, GlobalVariables.SERVICE_MODE.CHAT_SATUS_UPDATE));

    }


    private void getChatAPI(Long aLong) {

   //     Toast.makeText(this, uMins+" "+_chatId+ "   "+ _pid, Toast.LENGTH_SHORT).show();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("rid", _chatId);
        map.put("pid", _pid);
        RestService.getInstance(ChatFireBaseActivity.this).getChatStateValues(map, new MyCallback<ArrayList<ChatStatusValueModel>>(ChatFireBaseActivity.this,
                ChatFireBaseActivity.this, GlobalVariables.SERVICE_MODE.CHAT_SATE_VALUES));

    }


    private void getChatCheckAPI() {

   //     Toast.makeText(this, uMins+" "+_chatId+ "   "+ _pid, Toast.LENGTH_SHORT).show();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("rid", _chatId);
        map.put("pid", _pid);
        RestService.getInstance(ChatFireBaseActivity.this).getChatStateValuesbal(map, new MyCallback<ArrayList<ChatStatusValueModel>>(ChatFireBaseActivity.this,
                ChatFireBaseActivity.this, GlobalVariables.SERVICE_MODE.CHAT_SATE_VALUES_BAL));

    }

    private void getWaitlistOnlineAPI() {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("pid", _pid);
        RestService.getInstance(ChatFireBaseActivity.this).getStatusWaitlisOnline(map, new MyCallback<ResponseBody>(ChatFireBaseActivity.this,
                ChatFireBaseActivity.this, GlobalVariables.SERVICE_MODE.STATUS_WAITLIST_ONLINE));

    }

    public void getChatDataAPI() {

        //progressBar.show();

        if (progressBar != null){
            if (!progressBar.isShowing()) {
                progressBar.show();
            }
    }

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
        map.put("uid", uid);
        map.put("uname", uname);
        map.put("chtime", String.valueOf(simpleTime));
        map.put("chdate", Datet);
        map.put("duration", String.valueOf(time));

     //   Toast.makeText(this, uid+" "+uname+" "+_pid +" "+_pname+String.valueOf(simpleTime), Toast.LENGTH_LONG).show();

        RestService.getInstance(ChatFireBaseActivity.this).getChatInfoData(map, new MyCallback<ResponseBody>(ChatFireBaseActivity.this,
                ChatFireBaseActivity.this, GlobalVariables.SERVICE_MODE.CHAT_INFO_DATA));


    }


    @Override
    public void onFailure(Call call, Throwable t, GlobalVariables.SERVICE_MODE mode) {

        switch (mode) {


            case USER_CHAT_STATE:

                Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
                _status = "99";


                break;

                case CHATSATUS_NORESPONCE_FROM_USER:

                Toast.makeText(this, "Pandith server fail", Toast.LENGTH_SHORT).show();


                break;

            case CHAT_ATACHMENT:

                Toast.makeText(this, "Attach fail", Toast.LENGTH_SHORT).show();

                break;

            case CHAT_SATUS_UPDATE:

                Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();


                break;


            case CHAT_SATE_VALUES:

                Toast.makeText(this,"FAIL", Toast.LENGTH_SHORT).show();

                break;

            case CHAT_SATE_VALUES_BAL:

                Toast.makeText(this,"FAIL", Toast.LENGTH_SHORT).show();

                _endchat.setActivated(true);


                break;

            case STATUS_WAITLIST_ONLINE:


                Toast.makeText(this, "No Data Foun", Toast.LENGTH_SHORT).show();


                break;

            case CHAT_INFO_DATA:

                Toast.makeText(this, "Insert fail No Data Foun", Toast.LENGTH_SHORT).show();
                _endchat.setClickable(true);


                break;




        }

    }

    @Override
    public void onSuccess(Response response, GlobalVariables.SERVICE_MODE mode) {


        switch (mode) {


            case USER_CHAT_STATE:

                _chatUseRespnce = (ArrayList<UserChatStatus>) response.body();

                _status = _chatUseRespnce.get(0).getChatStatusValue();

                break;


            case CHATSATUS_NORESPONCE_FROM_USER:


                SharedPreferences sharedPreferences1 = getSharedPreferences("notifyID", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit1 = sharedPreferences1.edit();
                edit1.clear();
                edit1.apply();


                Toast.makeText(this, "No Response From User", Toast.LENGTH_SHORT).show();

                Intent intent1 = new Intent(getApplicationContext(), ShowMsgActivity.class);
                intent1.putExtra("msg", "No Response From User");

                startActivity(intent1);
                break;


            case CHAT_ATACHMENT:

                _ChatAttachModel = (ArrayList<ChatAttachModel>) response.body();

                //  Toast.makeText(this, "Attach Success", Toast.LENGTH_SHORT).show();

                String url = _ChatAttachModel.get(0).getImgUrl();


               // _imageURL = "https://meraastro.com/admin/" + url;
                _imageURL = "https://meraastro.in/admin/" + url;

                if (url != null) {

                    // Toast.makeText(this, url, Toast.LENGTH_LONG).show();

                  // sendMe(userid, reciverid, _imageURL);

                   sendImage(_pid, uid, url);

                }

                break;


            case CHAT_SATUS_UPDATE:

                disposable.dispose();
                if (!progressBar.isShowing()) {
                    progressBar.dismiss();
                }

                //Toast.makeText(CoverstionActivity.this, "Chat End", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), ShowMsgActivity.class);
                intent.putExtra("msg", "Chat Ended");

                startActivity(intent);


                finish();

                break;



            case CHAT_SATE_VALUES_BAL:


                _chatRequestb = (ArrayList<ChatStatusValueModel>) response.body();

                _chatNotifyb = _chatRequestb.get(0).getChatStatusValue();

                if (!_chatNotifyb.equals("61")&&!_chatNotifyb.equals("51")&&!_chatNotifyb.equals("5")) {

//                timer.cancel();

                    getChatDataAPI();


                }  else {
//                timer.cancel();

                    ImageChoose = true;

                    disposable.dispose();

                    timer.cancel();

                    TTTTT.cancel();
                    uMisTime.cancel();

                    getWiatToOnline();
                    getWaitlistOnlineAPI();


                    Intent intent22 = new Intent(getApplicationContext(), ShowMsgActivity.class);
                    intent22.putExtra("msg", "Chat Ended");
                    startActivity(intent22);

                    finish();

                }

                break;

          case CHAT_SATE_VALUES:


                _chatRequest = (ArrayList<ChatStatusValueModel>) response.body();


                _chatNotify = _chatRequest.get(0).getChatStatusValue();


                if (!_chatNotify.isEmpty()) {

                    if (_chatNotify.equals("61")) {

                        // Toast.makeText(this,_chatNotify+"   " +"User Chat Ended Button ", Toast.LENGTH_SHORT).show();
                        ImageChoose = true;

                        disposable.dispose();

                        timer.cancel();

                        TTTTT.cancel();
                        uMisTime.cancel();


                        getWiatToOnline();
                        getWaitlistOnlineAPI();

                        //Toast.makeText(CoverstionActivity.this, "Chat Ended", Toast.LENGTH_SHORT).show();
                        Intent intent13 = new Intent(getApplicationContext(), ShowMsgActivity.class);
                        intent13.putExtra("msg", "Chat has Ended due to low Balance");
                        startActivity(intent13);


                        SharedPreferences sharedPreferences13 = getSharedPreferences("notifyID", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit13 = sharedPreferences13.edit();
                        edit13.clear();
                        edit13.apply();


                        finish();


                    } else if (_chatNotify.equals("51")) {

                        // Toast.makeText(this,_chatNotify+"   " +"User Chat Ended Button ", Toast.LENGTH_SHORT).show();

                        ImageChoose = true;

                        disposable.dispose();
                        uMisTime.cancel();
                        TTTTT.cancel();
                        timer.cancel();

                        getWiatToOnline();
                        getWaitlistOnlineAPI();

                        //Toast.makeText(CoverstionActivity.this, "Chat Ended", Toast.LENGTH_SHORT).show();
                        Intent intent3 = new Intent(getApplicationContext(), ShowMsgActivity.class);
                        intent3.putExtra("msg", "User has Ended the Chat");
                        startActivity(intent3);

                        SharedPreferences sharedPreferences3 = getSharedPreferences("notifyID", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit3 = sharedPreferences3.edit();
                        edit3.clear();
                        edit3.apply();

                        finish();


                    } else if (_chatNotify.equals("41")) {

                        // Toast.makeText(this,_chatNotify+"   " +"User Chat Ended Button ", Toast.LENGTH_SHORT).show();

                        ImageChoose = true;

                        disposable.dispose();
                        uMisTime.cancel();
                        TTTTT.cancel();
                        timer.cancel();


                        getWaitlistOnlineAPI();
                        getWiatToOnline();
                        //Toast.makeText(CoverstionActivity.this, "Chat Ended", Toast.LENGTH_SHORT).show();
                        Intent intent3 = new Intent(getApplicationContext(), ShowMsgActivity.class);
                        intent3.putExtra("msg", "User not accepted");
                        startActivity(intent3);

                        SharedPreferences sharedPreferences3 = getSharedPreferences("notifyID", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit3 = sharedPreferences3.edit();
                        edit3.clear();
                        edit3.apply();

                        finish();


                    } else if (_chatNotify.equals("8")) {

                        // Toast.makeText(this,_chatNotify+"   " +"User Chat Ended Button ", Toast.LENGTH_SHORT).show();

                        ImageChoose = true;

                        disposable.dispose();
                        uMisTime.cancel();
                        timer.cancel();



                        getWaitlistOnlineAPI();
                        getWiatToOnline();

                        Intent intent2 = new Intent(getApplicationContext(), ShowMsgActivity.class);
                        intent2.putExtra("msg", "User has Canceled the Chat");
                        startActivity(intent2);


                        SharedPreferences sharedPreferences12 = getSharedPreferences("notifyID", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit12 = sharedPreferences12.edit();
                        edit12.clear();
                        edit12.apply();

                        finish();



                    } else if (_chatNotify.equals("58")) {

                        // Toast.makeText(this,_chatNotify+"   " +"User Chat Ended Button ", Toast.LENGTH_SHORT).show();

                        ImageChoose = true;

                        disposable.dispose();
                        uMisTime.cancel();
                        timer.cancel();


                        Toast.makeText(this, "No Response From User", Toast.LENGTH_SHORT).show();

                        Intent intent2 = new Intent(getApplicationContext(), ShowMsgActivity.class);
                        intent2.putExtra("msg", "No Response From User");

                        startActivity(intent2);


                    } else if (_chatNotify.equals("7")) {

                        if (FIRST.equals("FIRST")) {

                            if (uMins != null) {

                                if(!uMins.equals("0")) {

                                    uMisTime.start();

                                }

                                sendMessageFireStoreFIRST(_pid, uid, "Welcome to Mera Astro. Please allow a moment for the Astrologer/Consultant to prepare your chart based on your details. Until then you may ask your question. For this purpose, the first minute is free for all paid chats and you will not be charged. "+ "\n\n" +"Thank you.");
                                timer.start();

                                TTTTT.cancel();
                                if (alertDialog2 != null) {
                                    alertDialog2.dismiss();
                                    ImageChoose = false;
                                }

                            }

                            FIRST = "SECOND";

                        }

                    }

                }

                break;

            case STATUS_WAITLIST_ONLINE:

                //Toast.makeText(this, "Ss", Toast.LENGTH_SHORT).show();

                break;

            case CHAT_INFO_DATA:



                getChatEndAPI();
                getWaitlistOnlineAPI();
                getWiatToOnline();


                break;



        }
    }


    private void updateText(long mTimeLeftInMillis) {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d Min : %02d Sec", minutes, seconds);

        dialog2.setTitle("Please Wait  \n" +timeLeftFormatted);

    }
    private void updateCountDownText(long mTimeLeftInMillis) {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d Min : %02d Sec", minutes, seconds);

        _time_show.setText("Time Left : "+timeLeftFormatted);

    }


    public void endChat(View view) {

        _endchat.setClickable(false);

        AlertDialog.Builder dialog=new AlertDialog.Builder(ChatFireBaseActivity.this);
        dialog.setMessage("Are You Sure?");
        dialog.setTitle("Alert");
        dialog.setPositiveButton("END",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {

                        if(uMins!=null) {

                            ImageChoose= true;

                            time = timerTv.getText().toString();

                            getChatCheckAPI();
                            //progressBar.show();
                            if(!progressBar.isShowing()) {
                                progressBar.show();
                            }

                        }

                    }
                }); dialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {

                        _endchat.setClickable(true);


                    }
                });

        AlertDialog alertDialog=dialog.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

    }

    @Override
    protected void onDestroy() {

       // getChatDataAPI();
        disposable.dispose();

        timer.cancel();

        TTTTT.cancel();
        uMisTime.cancel();

        FIRST = "FIRST";

        sharedPreferences1 = getSharedPreferences("notifyID",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences1.edit();
        editor.clear();


        super.onDestroy();
    }

    public abstract static class CountUpTimer extends CountDownTimer {
        private static final long INTERVAL_MS = 1000;
        private final long duration;

        protected CountUpTimer(long durationMs) {
            super(durationMs, INTERVAL_MS);
            this.duration = durationMs;
        }

        public abstract void onTick(int second);

        @Override
        public void onTick(long msUntilFinished) {
            int second = (int) ((duration - msUntilFinished) / 1000);
            onTick(second);
        }

        @Override
        public void onFinish() {
            onTick(duration / 1000);
        }
    }


    public void selectImage() {

        ImageChoose = true;


        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 7);
        //Toast.makeText(ChatFireBaseActivity.this, "this activity goes to select image", Toast.LENGTH_LONG).show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 7 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            ImageChoose = false;

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);


                resizeBitmap = resize(bitmap, bitmap.getWidth() / 2, bitmap.getHeight() / 2);

                convertImageToBase64(resizeBitmap);

                //imageName = bitmap.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }


    public void getWiatToOnline() {

        mJsonRequest = new JsonObjectRequest(BASE_URL+"users/updateStatusWaitListToOnline.php?pid=" + _pid, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ChatFireBaseActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(mJsonRequest);

    }


    private void convertImageToBase64(Bitmap bm) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 70, baos); //bm is the bitmap object
        byte[] byteArrayImage = baos.toByteArray();

        bitmapImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);

        if(bitmapImage!=null){

            chatAtachment();

        }

    }

    private static Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > ratioBitmap) {
                finalWidth = (int) ((float) maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float) maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }




}
