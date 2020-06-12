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

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uma.astropandith.Activitys.SupportChatActivity;
import com.uma.astropandith.Model.ChatNotify;
import com.uma.astropandith.R;
import com.uma.astropandith.Retrofit.GlobalVariables;


public class NotificationService extends Service {


    private static final String CHANNEL_ID = "167677";
    private DatabaseReference reference1;
    private  int notificationId = 12345;
    private ChatNotify chat;
    private SharedPreferences sharedPreferences;
    private String _name;
    private String _pId;
    private String _email;
    private String _phone;
    private ValueEventListener lisner;
    private String message;


    public NotificationService() {
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
        _email = sharedPreferences.getString("email", null); // getting String
        _phone = sharedPreferences.getString("phone", null); // getting String


        SharedPreferences sharedPreferences = getSharedPreferences("Notif", Context.MODE_PRIVATE);
        message  = sharedPreferences.getString("message", null); // getting String

        if(message == null){

            message = " ";

        }

        reference1 = FirebaseDatabase.getInstance().getReference("SupportChats");

        lisner = reference1.addValueEventListener(new ValueEventListener() {
            private String rid;
            private String ddd,uid,name;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                     chat = snapshot.getValue(ChatNotify.class);


                    if (chat.getReceiver().equals(_pId) && chat.getSender().equals("999") ||
                            chat.getReceiver().equals("999") && chat.getSender().equals(_pId)){

                        ddd = chat.getMessage();
                        uid = chat.getSender();
                        rid = chat.getReceiver();
                       // name = chat.getName();

                    }

                }

                if(!message.equals(ddd)) {

                    if(rid!=null) {

                        if (uid.equals("999") && rid.equals(_pId)) {

                            SharedPreferences sharedPreferences = getSharedPreferences("Notif", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("message", ddd);
                            editor.commit();
                            editor.apply();

                            //   GlobalVariables.MESSAGE = ddd;

                            NotificationCompat.Builder mBuilder =
                                    new NotificationCompat.Builder(NotificationService.this)
                                            .setSmallIcon(R.drawable.guru)
                                            .setContentTitle("Message from MeraAstro Support")
                                            .setAutoCancel(true)
                                            .setContentText(ddd);


                            Intent resultIntent = new Intent(NotificationService.this, SupportChatActivity.class);
                            resultIntent.putExtra("_id", uid);
                            resultIntent.putExtra("name", name);


                            PendingIntent resultPendingIntent =
                                    PendingIntent.getActivity(
                                            NotificationService.this,
                                            0,
                                            resultIntent,
                                            PendingIntent.FLAG_UPDATE_CURRENT
                                    );

                            mBuilder.setContentIntent(resultPendingIntent);
                            int mNotificationId = (int) System.currentTimeMillis();
                            NotificationManager mNotifyMgr =
                                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                int importance = NotificationManager.IMPORTANCE_HIGH;
                                NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);

                                mBuilder.setChannelId(CHANNEL_ID);
                                mNotifyMgr.createNotificationChannel(notificationChannel);
                            }
                            mNotifyMgr.notify(mNotificationId, mBuilder.build());

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
