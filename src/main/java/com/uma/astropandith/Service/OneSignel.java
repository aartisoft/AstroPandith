package com.uma.astropandith.Service;

import android.app.Application;

import com.onesignal.OneSignal;


public class OneSignel extends Application {

    private static OneSignel mInstance;

    public OneSignel(){

        mInstance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;


        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }

    public static synchronized OneSignel getInstance(){

        return mInstance;

    }

}
