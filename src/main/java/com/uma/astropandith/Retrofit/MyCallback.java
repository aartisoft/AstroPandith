package com.uma.astropandith.Retrofit;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCallback<T> implements Callback<T> {
    private RestCallback restCallback;
    private Context baseActivity;
    private GlobalVariables.SERVICE_MODE mode;

    public MyCallback(Context context, RestCallback restCallback, GlobalVariables.SERVICE_MODE mode) {
        this.restCallback = restCallback;
        this.baseActivity = context;
        this.mode = mode;

    }



    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.code() == 400) {
            try {
                Log.v("Error code 400",response.errorBody().string().trim());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (restCallback != null && response.isSuccessful()) {
            System.out.println("My Callback onSuccess");
            restCallback.onSuccess(response, mode);
        }
        else {
            System.out.println("rest callback was null");
            //Toast.makeText(baseActivity, "Something went wrong.", Toast.LENGTH_SHORT).show();
        }



    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (restCallback != null) {
            System.out.println("My Callback onFailure");
            restCallback.onFailure(call, t, mode);
        }
    }

}
