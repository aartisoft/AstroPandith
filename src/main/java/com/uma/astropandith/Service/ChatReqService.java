package com.uma.astropandith.Service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uma.astropandith.Activitys.SupportChatActivity;
import com.uma.astropandith.Model.ChatNotify;
import com.uma.astropandith.Model.ChatRNotify;
import com.uma.astropandith.R;


public class ChatReqService extends Service {


    private static final String CHANNEL_ID = "167677";
    private DatabaseReference reference1;
    private  int notificationId = 12345;
    private ChatRNotify chat;
    private SharedPreferences sharedPreferences;
    private String _name;
    private String _pId;

    private ValueEventListener lisner;


    public ChatReqService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        sharedPreferences = getSharedPreferences("save", Context.MODE_PRIVATE);
        _name = sharedPreferences.getString("name", null); // getting String
        _pId = sharedPreferences.getString("id", null); // getting String


        reference1 = FirebaseDatabase.getInstance().getReference("ChatAstrologer").child(_pId);

        lisner = reference1.addValueEventListener(new ValueEventListener() {
            private String rid;
            private String chatStatus,uid,name;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                     chat = snapshot.getValue(ChatRNotify.class);


                    if (chat.getReceiver().equals(_pId) || chat.getSender().equals(_pId)){

                        chatStatus = chat.getChatstatus();
                        uid = chat.getSender();
                        rid = chat.getReceiver();
                       // name = chat.getName();

                    }

                }

                if(!chatStatus.equals("1")) {

                    if(rid!=null) {

                        if (uid.equals(_pId) && rid.equals(_pId)) {


                            Toast.makeText(ChatReqService.this, "You Got New Chat Request", Toast.LENGTH_LONG).show();


                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {

        if(lisner!=null) {

            reference1.removeEventListener(lisner);

        }

        super.onDestroy();

    }


}
