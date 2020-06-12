package com.uma.astropandith.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uma.astropandith.Adapters.ConversionAdapter;
import com.uma.astropandith.Adapters.MessageAdapter;
import com.uma.astropandith.Model.Chat;
import com.uma.astropandith.Model.ChatAttachModel;
import com.uma.astropandith.R;
import com.uma.astropandith.Retrofit.GlobalVariables;
import com.uma.astropandith.Retrofit.MyCallback;
import com.uma.astropandith.Retrofit.RestCallback;
import com.uma.astropandith.Retrofit.RestService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity implements RestCallback {


    private String userid;
    private String reciverid;
    private RecyclerView recyclerView;
    private ImageView profile_image,selectImage;
    private TextView username;
    private ImageButton btn_send;
    private TextView text_send;
    private String _pname;
    private boolean notify;
    private DatabaseReference reference;
    private ArrayList<Chat> mchat;
    private MessageAdapter messageAdapter;
    private ValueEventListener seenListener;
    private ProgressDialog progressBar;
    private String _uname;
    private String _pid;
    private String _uid;
    private Bitmap resizeBitmap;
    private String bitmapImage;
    private Uri filePath;
    private ArrayList<ChatAttachModel> _ChatAttachModel;
    private String _imageURL;
    private String _rowid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toast.makeText(ChatActivity.this, "this activity has bugs", Toast.LENGTH_LONG).show();
        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Loading");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);


        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        profile_image = findViewById(R.id.profile_image);
        selectImage = findViewById(R.id.selectImage);
        username = findViewById(R.id.username);
        btn_send = findViewById(R.id.btn_send);
        text_send = findViewById(R.id.text_send);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            _pid = extras.getString("pid");
            _pname = extras.getString("pname");
            _uname = extras.getString("uname");
            _uid = extras.getString("uid");
            _rowid = extras.getString("reportid");

        }


        userid = _pid;
        reciverid = _rowid;

//        Toast.makeText(this, _rowid+" "+ _pid, Toast.LENGTH_SHORT).show();


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notify = true;
                String msg = text_send.getText().toString();
                if (!msg.equals("")){

                    sendMessage(userid, reciverid, msg);


                } else {
                    Toast.makeText(ChatActivity.this, "You can't send empty message", Toast.LENGTH_SHORT).show();
                }
                text_send.setText("");
            }
        });

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ChatActivity.this, "this activity has bugs", Toast.LENGTH_LONG).show();
                selectImage();
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("Users").child(reciverid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //             User user = dataSnapshot.getValue(User.class);
                username.setText(_pname);

                readMesagges(userid, reciverid);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        seenMessage(reciverid);

    }


    private void seenMessage(final String userid){
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        seenListener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(_pid) && chat.getSender().equals(userid)){
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

    private void sendMessage(String sender, final String receiver, String message){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        hashMap.put("isseen", false);

        reference.child("Chats").push().setValue(hashMap);


        // add user to chat fragment
        final DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(_pid)
                .child(_uid);

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
//                User user = dataSnapshot.getValue(User.class);
//                if (notify) {
//
//                    sendNotifiaction(receiver, user.getUsername(), msg);
//                }
//                notify = false;
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
      //  hashMap.put("time", getDateTimeSec());
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



    public void selectImage() {
        Toast.makeText(ChatActivity.this, "this activity has bugs", Toast.LENGTH_LONG).show();
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

    private void readMesagges(final String myid, final String userid) {

        mchat = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mchat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(myid) && chat.getSender().equals(userid) ||
                            chat.getReceiver().equals(userid) && chat.getSender().equals(myid)) {

                        mchat.add(chat);

                    }
                    messageAdapter = new MessageAdapter(ChatActivity.this, mchat,_pid);
                    recyclerView.setAdapter(messageAdapter);
                    progressBar.dismiss();
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

    private void status(String status){

        reference = FirebaseDatabase.getInstance().getReference("Users").child("userid");

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", status);

        reference.updateChildren(hashMap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        status("online");
        currentUser(_uid);
    }

    @Override
    protected void onPause() {
        super.onPause();
        reference.removeEventListener(seenListener);
        status("offline");
        currentUser("none");
    }
    private void chatAtachment() {


        HashMap<String, String> map = new HashMap<String, String>();
        map.put("image", bitmapImage);
        RestService.getInstance(ChatActivity.this).getChatAttachment(map, new MyCallback<ArrayList<ChatAttachModel>>(ChatActivity.this,
                ChatActivity.this, GlobalVariables.SERVICE_MODE.CHAT_ATACHMENT));

    }

    @Override
    public void onFailure(Call call, Throwable t, GlobalVariables.SERVICE_MODE mode) {

        switch (mode) {


            case CHAT_ATACHMENT:

                Toast.makeText(this, "Attach fail", Toast.LENGTH_SHORT).show();

                break;


        }


    }

    @Override
    public void onSuccess(Response response, GlobalVariables.SERVICE_MODE mode) {

        switch (mode) {


            case CHAT_ATACHMENT:

                _ChatAttachModel = (ArrayList<ChatAttachModel>) response.body();


                Toast.makeText(this, "Attach Success", Toast.LENGTH_SHORT).show();


                String url = _ChatAttachModel.get(0).getImgUrl();

                _imageURL = "https://meraastro.com/admin/"+url;

                if(url!= null){

                    Toast.makeText(this, url, Toast.LENGTH_LONG).show();

                    sendMe(userid, reciverid, "Image Attach",_imageURL );


                }


                break;


        }

    }
}