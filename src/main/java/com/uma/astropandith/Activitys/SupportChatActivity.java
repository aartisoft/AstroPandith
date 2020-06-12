package com.uma.astropandith.Activitys;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.uma.astropandith.Adapters.MessageAdapter;
import com.uma.astropandith.Adapters.SupprtAdapter;
import com.uma.astropandith.Model.CallHistory;
import com.uma.astropandith.Model.Chat;
import com.uma.astropandith.Model.ChatAttachModel;
import com.uma.astropandith.Model.UserChatStatus;
import com.uma.astropandith.R;
import com.uma.astropandith.Retrofit.GlobalVariables;
import com.uma.astropandith.Retrofit.MyCallback;
import com.uma.astropandith.Retrofit.RestCallback;
import com.uma.astropandith.Retrofit.RestService;
import com.uma.astropandith.Service.ChatNotifyService;
import com.uma.astropandith.Service.NotificationService;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

import static com.uma.astropandith.Retrofit.GlobalVariables.BASE_URL;

public class SupportChatActivity extends AppCompatActivity implements RestCallback {

    private RecyclerView recyclerView;
    private ImageView profile_image;
    private TextView username;
    private ImageButton btn_send;
    private TextView text_send;
    private boolean notify;
    private DatabaseReference reference;
    private ArrayList<Chat> mchat;
    private SupprtAdapter messageAdapter;
    private String _pid;
    private String _pname;
    private NotificationCompat.Builder builder;
    private ProgressDialog progressBar;
    private TextView timerTv;
    private Uri imageURI;
    private String userid;
    private String reciverid;
    private ValueEventListener seenListener;
    private RequestQueue requestQueue;
    private JsonObjectRequest mJsonRequest;
    private SharedPreferences sharedPreferences;
    private String _name;
    private String _id;
    private String _mail;
    private String _phone;
    private Uri filePath;
    private Bitmap resizeBitmap;
    private String bitmapImage;
    private ImageView _selectImage;
    private ArrayList<ChatAttachModel> _ChatAttachModel;
    private String _imageURL;
    private SharedPreferences sharedPreferences1;
    private String _date;
    private SharedPreferences sharedPreferences2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatsupport);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        requestQueue = Volley.newRequestQueue(this);

        sharedPreferences = getSharedPreferences("save", Context.MODE_PRIVATE);
        _name = sharedPreferences.getString("name", null); // getting String
        _id = sharedPreferences.getString("id", null); // getting String
        _mail = sharedPreferences.getString("email", null); // getting String
        _phone = sharedPreferences.getString("phone", null); // getting String

        reqestChatAPI();


        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Loading");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);
        progressBar.show();


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



        userid = _id;
        reciverid = "999";


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notify = true;
                String msg = text_send.getText().toString();
                if (!msg.equals("")){

                    sendMessage(userid, reciverid, msg);

                } else {
                    Toast.makeText(SupportChatActivity.this, "You can't send empty message", Toast.LENGTH_SHORT).show();
                }
                text_send.setText("");
            }
        });


        _selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectImage();
            }
        });



        reference = FirebaseDatabase.getInstance().getReference("Users").child(reciverid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                username.setText("Support");

                readMesagges(userid, reciverid);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        seenMessage(reciverid);

        sharedPreferences1 = getSharedPreferences("Date", Context.MODE_PRIVATE);
        _date = sharedPreferences1.getString("date", null); // getting String

        if(_date==null){

            _date = "5";

        }

        if (!(getDateTime().equals(_date))) {

            sendMessag(userid, reciverid, "ID : " + _id + "\n" + "NAME : " + _name + "\n" + "PHONE : " + _phone);


            sharedPreferences2 = getSharedPreferences("Date", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit1 = sharedPreferences2.edit();
            edit1.putString("date",getDateTime());
            edit1.clear();
            edit1.apply();

        }


        if(isServiceRunning()){

            stopService(new Intent(this, NotificationService.class));

        }


    }

    private boolean isServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if("com.uma.astropandith.Service.NotificationService".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    @Override
    protected void onDestroy() {


        if(!isServiceRunning()){

            startService(new Intent(this, NotificationService.class));

        }


        super.onDestroy();
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

    private void sendMe(String sender, final String receiver, String image ){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("name", _name+ " Astrologer");
        hashMap.put("receiver", receiver);
        hashMap.put("image", image);
        hashMap.put("time", getDateTimeSec());
        hashMap.put("isseen", false);

        reference.child("SupportChats").push().setValue(hashMap);


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

    //    final String msg = message;

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

    private void sendMessage(String sender, final String receiver, String message){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("name", _name + " Astrologer");
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        hashMap.put("time", getDateTimeSec());
        hashMap.put("isseen", false);

        reference.child("SupportChats").push().setValue(hashMap);


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


        sharedPreferences1 = getSharedPreferences("Date",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences1.edit();
        editor.putString("date",getDateTime());
        editor.commit();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("name", _name+ " Astrologer");
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        hashMap.put("date", getDateTime());
        hashMap.put("time", getDateTimeSec());
        hashMap.put("isseen", false);

        reference.child("SupportChats").push().setValue(hashMap);


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

        reference = FirebaseDatabase.getInstance().getReference("SupportChats");
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
                    messageAdapter = new SupprtAdapter(SupportChatActivity.this, mchat,myid);
                    recyclerView.setAdapter(messageAdapter);
                    progressBar.dismiss();

                    if (chat.getSender().equals("999") && chat.getReceiver().equals(myid)) {

                        SharedPreferences sharedPreferences = getSharedPreferences("Notif", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("message", chat.getMessage());
                        editor.commit();
                        editor.apply();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void seenMessage( String userid) {
        reference = FirebaseDatabase.getInstance().getReference("SupportChats");
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
    public void callToSupport(View view) {

        Intent dialIntent = new Intent();
        dialIntent.setAction(Intent.ACTION_DIAL);
        dialIntent.setData(Uri.parse("tel:+919555266000"));
        startActivity(dialIntent);
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


    public void reqestChatAPI(){

        mJsonRequest = new JsonObjectRequest(
                BASE_URL+"pandit/panditRequestSupportTeam.php?pid="+_id+"&pname="+_name+"&pphone="+_phone+"", null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SupportChatActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(mJsonRequest);

    }


    private void chatAtachment() {

        progressBar.show();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("image", bitmapImage);
        RestService.getInstance(SupportChatActivity.this).getChatAttachment(map, new MyCallback<ArrayList<ChatAttachModel>>(SupportChatActivity.this,
                SupportChatActivity.this, GlobalVariables.SERVICE_MODE.CHAT_ATACHMENT));

    }


    @Override
    public void onFailure(Call call, Throwable t, GlobalVariables.SERVICE_MODE mode) {

        switch (mode) {


            case CHAT_ATACHMENT:

                Toast.makeText(this, "Attach fail", Toast.LENGTH_SHORT).show();
                progressBar.dismiss();

                break;


        }


    }

    @Override
    public void onSuccess(Response response, GlobalVariables.SERVICE_MODE mode) {

        switch (mode) {


            case CHAT_ATACHMENT:

                _ChatAttachModel = (ArrayList<ChatAttachModel>) response.body();


              //  Toast.makeText(this, "Attach Success", Toast.LENGTH_SHORT).show();


               String url = _ChatAttachModel.get(0).getImgUrl();

              // _imageURL = "https://meraastro.com/admin/"+url;
               _imageURL = "https://meraastro.in/admin/"+url;

               if(url!= null){

               //    Toast.makeText(this, url, Toast.LENGTH_LONG).show();

                   sendMe(userid, reciverid,_imageURL );

               }

               progressBar.dismiss();

                break;


        }

    }
}