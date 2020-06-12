package com.uma.astropandith.Retrofit;

import retrofit2.Call;
import retrofit2.Response;

/**
 *
 *
 * interface that includes two methods
 * which are called once the Retrofit finishes making an API call.
 *
 */
public interface RestCallback<T> {

    public void onFailure(Call<Response<T>> call, Throwable t, GlobalVariables.SERVICE_MODE mode);
    public void onSuccess(Response<T> response, GlobalVariables.SERVICE_MODE mode);

}