package com.uma.astropandith.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

public class MyBroadCast extends BroadcastReceiver {
    public MyBroadCast() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Service restarted", Toast.LENGTH_SHORT).show();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            context.startForegroundService(new Intent(context, ChatNotifyService.class));
        } else {
            context.startService(new Intent(context, ChatNotifyService.class));
        }

    }


}
