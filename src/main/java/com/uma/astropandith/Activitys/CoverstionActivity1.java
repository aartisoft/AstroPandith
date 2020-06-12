package com.uma.astropandith.Activitys;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uma.astropandith.Adapters.MessageAdapter;
import com.uma.astropandith.Model.Chat;
import com.uma.astropandith.Model.ChatAttachModel;
import com.uma.astropandith.Model.ChatStatusValueModel;
import com.uma.astropandith.Model.UserChatStatus;
import com.uma.astropandith.R;
import com.uma.astropandith.Retrofit.GlobalVariables;
import com.uma.astropandith.Retrofit.MyCallback;
import com.uma.astropandith.Retrofit.RestCallback;
import com.uma.astropandith.Retrofit.RestService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class CoverstionActivity1 extends AppCompatActivity implements RestCallback {


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
    private String _uname;
    private String _uid;
    private ImageButton btn_send;
    private String userid;
    private String reciverid;
    private String _status;
    private AlertDialog alertD;
    private ArrayList<UserChatStatus> _chatUseRespnce;
    private String _reportid;
    private SharedPreferences sharedPreferences1;
    private String reportid;
    private String uid;
    private String uname;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coverstion);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Toast.makeText(CoverstionActivity1.this, "this activity has bugs", Toast.LENGTH_LONG).show();
        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Loading");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);
        progressBar.show();


        sharedPreferences1 = getSharedPreferences("notifyID", Context.MODE_PRIVATE);
        reportid = sharedPreferences1.getString("reportid", null); // getting String
        uid = sharedPreferences1.getString("uid", null); // getting String
        uname = sharedPreferences1.getString("uname", null); // getting String

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



        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            _pid = extras.getString("pid");
            _pname = extras.getString("pname");
            _uname = extras.getString("uname");
            _uid = extras.getString("uid");
            _reportid = extras.getString("reportid");

        }



        userid = reportid;
        reciverid = _uid;

        Toast.makeText(this, _uid + "  "+ reportid, Toast.LENGTH_SHORT).show();





        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notify = true;
                String msg = text_send.getText().toString();
                if (!msg.equals("")){

                    sendMessage(userid, reciverid, msg);

                } else {
                    Toast.makeText(CoverstionActivity1.this, "You can't send empty message", Toast.LENGTH_SHORT).show();
                }
                text_send.setText("");
            }
        });


        reference = FirebaseDatabase.getInstance().getReference("Users").child(reciverid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                username.setText(_uname);

                readMesagges(userid, reciverid);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        seenMessage(reciverid);


        ha =new Handler();

        ha.postDelayed( new Runnable() {

            @Override
            public void run() {


                getChatAPI();


              //  ChatUserStateAPI();


                ha.postDelayed(this, 10000);
            }

        }, 10000);


        timer = new CoverstionActivity1.CountUpTimer(1000000000) {
            public void onTick(int second) {

                String  sec =  String.valueOf(second);


                if(sec.equals("40")){

                    if(_status.equals("2")){

                        getChatServerCancelAPI();

                    }

                }


            }
        };
       // timer.start();

        _selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CoverstionActivity1.this, "this activity has bugs", Toast.LENGTH_LONG).show();
                selectImage();
            }
        });

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
        Date date = new Date();
        return dateFormat.format(date);

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
                    messageAdapter = new MessageAdapter(CoverstionActivity1.this, mchat,myid);
                    recyclerView.setAdapter(messageAdapter);
                    progressBar.dismiss();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void sendMe(String sender, final String receiver, String message, String image ){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        hashMap.put("image", image);
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

    private void sendMessag(String sender, final String receiver, String message){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        hashMap.put("date", getDateTime());
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



    private void seenMessage( String userid) {
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        seenListener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(_pid) && chat.getSender().equals(reciverid)) {
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("isseen", true);
                        snapshot.getRef().updateChildren(hashMap);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });
    }



    private void currentUser(String userid){
        SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
        editor.putString("currentuser", userid);
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentUser(userid);
    }

    @Override
    protected void onPause() {
        super.onPause();
        reference.removeEventListener(seenListener);
        currentUser("none");
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
        RestService.getInstance(CoverstionActivity1.this).getUserChatState(map, new MyCallback<ArrayList<UserChatStatus>>(CoverstionActivity1.this,
                CoverstionActivity1.this, GlobalVariables.SERVICE_MODE.USER_CHAT_STATE));


    }

    private void getChatServerCancelAPI() {


        HashMap<String, String> map = new HashMap<String, String>();
        map.put("rid", reportid);
        map.put("StatusValue","41");
        map.put("pid", _pid);
        map.put("uid", uid);
        RestService.getInstance(CoverstionActivity1.this).getNoResponceFromUser(map, new MyCallback<ResponseBody>(CoverstionActivity1.this,
                CoverstionActivity1.this, GlobalVariables.SERVICE_MODE.CHATSATUS_NORESPONCE_FROM_USER));


    }

    private void chatAtachment() {


        HashMap<String, String> map = new HashMap<String, String>();
        map.put("image", bitmapImage);
        RestService.getInstance(CoverstionActivity1.this).getChatAttachment(map, new MyCallback<ArrayList<ChatAttachModel>>(CoverstionActivity1.this,
                CoverstionActivity1.this, GlobalVariables.SERVICE_MODE.CHAT_ATACHMENT));

    }



    private void getChatEndAPI() {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("rid", reportid);
        map.put("StatusValue", "5");
        map.put("pid", _pid);
        map.put("uid", uid);
        RestService.getInstance(CoverstionActivity1.this).getChatSstatus(map, new MyCallback<ResponseBody>(CoverstionActivity1.this,
                CoverstionActivity1.this, GlobalVariables.SERVICE_MODE.CHAT_SATUS_UPDATE));

    }



    private void getChatAPI() {

     //   Toast.makeText(this, reportid, Toast.LENGTH_SHORT).show();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("rid", reportid);
        map.put("pid", _pid);
        RestService.getInstance(CoverstionActivity1.this).getChatStateValues(map, new MyCallback<ArrayList<ChatStatusValueModel>>(CoverstionActivity1.this,
                CoverstionActivity1.this, GlobalVariables.SERVICE_MODE.CHAT_SATE_VALUES));


    }


    @Override
    public void onFailure(Call call, Throwable t, GlobalVariables.SERVICE_MODE mode) {

        switch (mode) {


            case USER_CHAT_STATE:

                Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();

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



        }

    }

    @Override
    public void onSuccess(Response response, GlobalVariables.SERVICE_MODE mode) {


        switch (mode) {


            case USER_CHAT_STATE:


                _chatUseRespnce = (ArrayList<UserChatStatus>) response.body();


                _status = _chatUseRespnce.get(0).getChatStatusValue();


                if (_status.equals("51")) {



                }

                break;



            case CHATSATUS_NORESPONCE_FROM_USER:

                Toast.makeText(this, "No Responce From User", Toast.LENGTH_SHORT).show();


                finish();

                break;



            case CHAT_ATACHMENT:

                _ChatAttachModel = (ArrayList<ChatAttachModel>) response.body();


              //  Toast.makeText(this, "Attach Success", Toast.LENGTH_SHORT).show();


                String url = _ChatAttachModel.get(0).getImgUrl();


                _imageURL = "http://meraastro.com/admin/"+url;

                if(url!= null){

                   // Toast.makeText(this, url, Toast.LENGTH_LONG).show();

                    sendMe(userid, reciverid, "Image Attach",_imageURL );

                }

                break;


            case CHAT_SATUS_UPDATE:


                AlertDialog.Builder dialog=new AlertDialog.Builder(CoverstionActivity1.this);
                dialog.setMessage("Are you sure?");
                dialog.setTitle("Alert");
                dialog.setPositiveButton("End",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog,
                                                int which) {

                                Toast.makeText(CoverstionActivity1.this, "Chat End", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        });  dialog.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {



                            }
                        });

                AlertDialog alertDialog=dialog.create();
                alertDialog.show();

                break;


            case CHAT_SATE_VALUES:


                _chatRequest = (ArrayList<ChatStatusValueModel>) response.body();


                _chatNotify = _chatRequest.get(0).getChatStatusValue();





                if (!_chatNotify.isEmpty()) {


                    if(_chatNotify.equals("51")){

                       // Toast.makeText(this,_chatNotify+"   " +"User Chat Ended Button ", Toast.LENGTH_SHORT).show();
//
//
//                        AlertDialog.Builder dialog1 =new AlertDialog.Builder(CoverstionActivity.this);
//                        dialog1.setMessage("User has Ended the Chat");
//                        dialog1.setTitle("Alert");
//                        dialog1.setPositiveButton("Okay",
//                                new   DialogInterface.OnClickListener() {
//
//                                    public void onClick(DialogInterface dialog,
//                                                        int which) {

                                        Toast.makeText(this, "Chat Ended", Toast.LENGTH_SHORT).show();
//                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                        startActivity(intent);
                        ha.removeCallbacks(null);
                                        finish();

//                                    }
//                                });
//
//                        AlertDialog alertDialog1=dialog1.create();
//                        alertDialog1.show();

                    }else  if(_chatNotify.equals("41")){

                       // Toast.makeText(this,_chatNotify+"   " +"User Chat Ended Button ", Toast.LENGTH_SHORT).show();

//
//                        AlertDialog.Builder dialog1 =new AlertDialog.Builder(CoverstionActivity.this);
//                        dialog1.setMessage("User has Canceled the Chat");
//                        dialog1.setTitle("Alert");
//                        dialog1.setPositiveButton("Okay",
//                                new DialogInterface.OnClickListener() {
//
//                                    public void onClick(DialogInterface dialog,
//                                                        int which) {
                                        Toast.makeText(this, "Chat Ended", Toast.LENGTH_SHORT).show();
//
//                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                                        startActivity(intent);
                                        ha.removeCallbacks(null);
                                        finish();

//                                    }
//                                });
//
//                        AlertDialog alertDialog1=dialog1.create();
//                        alertDialog1.show();

                    }

                }

                break;

        }


    }

    public void endChat(View view) {



        AlertDialog.Builder dialog=new AlertDialog.Builder(CoverstionActivity1.this);
        dialog.setMessage("Are You Sure?");
        dialog.setTitle("Alert");
        dialog.setPositiveButton("END",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {


                        getChatEndAPI();


                    }
                }); dialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {


                    }
                });

        AlertDialog alertDialog=dialog.create();
        alertDialog.show();




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

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 7);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 7 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();

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


    private void convertImageToBase64(Bitmap bm) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 10, baos); //bm is the bitmap object
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
