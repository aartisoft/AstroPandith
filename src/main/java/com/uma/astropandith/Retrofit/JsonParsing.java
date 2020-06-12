package com.uma.astropandith.Retrofit;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Venkatesh on 4/12/2018.
 */

public class JsonParsing extends Application {
     static JsonParsing mInstance;
    private RequestQueue mRequestQueue;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public RequestQueue getRequestQueue(){
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(this);
        }
        return mRequestQueue;
    }

}
