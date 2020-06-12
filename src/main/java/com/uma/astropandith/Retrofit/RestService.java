package com.uma.astropandith.Retrofit;

import android.content.Context;

import com.google.gson.GsonBuilder;
import com.uma.astropandith.BuildConfig;
import com.uma.astropandith.Model.CallHistory;
import com.uma.astropandith.Model.ChatAttachModel;
import com.uma.astropandith.Model.ChatHistory;
import com.uma.astropandith.Model.ChatRequestModel;
import com.uma.astropandith.Model.ChatStatusValueModel;
import com.uma.astropandith.Model.LoginModel;
import com.uma.astropandith.Model.ReportCompletedList;
import com.uma.astropandith.Model.ReportPendingList;
import com.uma.astropandith.Model.StausMoldel;
import com.uma.astropandith.Model.TutorialListModel;
import com.uma.astropandith.Model.TutorialModel;
import com.uma.astropandith.Model.UserChatStatus;
import com.uma.astropandith.Model.WalletHistory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestService {

    private static RestService restService;
    private static RestInterface restInterface;
    private static Context mContext;
    private static final int Tseconds = 120;


    private RestService() {

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
//            clientBuilder.addInterceptor(new ChuckInterceptor(mContext).showNotification(true));
//            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            clientBuilder.connectTimeout(Tseconds, TimeUnit.SECONDS);
            clientBuilder.writeTimeout(Tseconds, TimeUnit.SECONDS);
            clientBuilder.readTimeout(Tseconds, TimeUnit.SECONDS);
//            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//            clientBuilder.addInterceptor(httpLoggingInterceptor);
            clientBuilder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        }

        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        System.out.println("REST SERVICE BASE URL : " + GlobalVariables.BASE_URL);
        String baseUrl = GlobalVariables.BASE_URL;
        restInterface = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(clientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                .build()
                .create(RestInterface.class);
    }

    public static RestService getInstance(Context context) {
        if (restService == null) {
            mContext = context;
            restService = new RestService();
        }

        return restService;
    }

    public void getRestration(Map<String, String> map, MyCallback<ResponseBody> callback) {
        Call<ResponseBody> call = restInterface.getRegistration(map);
        call.enqueue(callback);
    }



    public void getLogin(Map<String, String> map, MyCallback<List<LoginModel>> callback) {
        Call<List<LoginModel>> call = restInterface.getLogin(map);
        call.enqueue(callback);
    }




    public void getStatus(Map<String, String> map, MyCallback<ResponseBody> callback) {
        Call<ResponseBody> call = restInterface.getStatus(map);
        call.enqueue(callback);
    }


    public void setPandihReplay(Map<String, String> map, MyCallback<ResponseBody> callback) {
        Call<ResponseBody> call = restInterface.setPandihReplay(map);
        call.enqueue(callback);
    }


    public void setUpdateReport(Map<String, String> map, MyCallback<ResponseBody> callback) {
        Call<ResponseBody> call = restInterface.setUpdateReport(map);
        call.enqueue(callback);
    }


    public void getCallStatus(Map<String, String> map, MyCallback<ResponseBody> callback) {
        Call<ResponseBody> call = restInterface.getCallStatus(map);
        call.enqueue(callback);
    }

    public void getChatStatus(Map<String, String> map, MyCallback<ResponseBody> callback) {
        Call<ResponseBody> call = restInterface.getChatStatus(map);
        call.enqueue(callback);
    }

    public void getStatusWaitlisOnline(Map<String, String> map, MyCallback<ResponseBody> callback) {
        Call<ResponseBody> call = restInterface.getStatusWaitlisOnline(map);
        call.enqueue(callback);

    }


    public void getChatInfoData(Map<String, String> map, MyCallback<ResponseBody> callback) {
        Call<ResponseBody> call = restInterface.getChatInfoData(map);
        call.enqueue(callback);

    }


    public void getPadingReportList(Map<String, String> map, MyCallback<ArrayList<ReportPendingList>> callback) {
        Call<ArrayList<ReportPendingList>> call = restInterface.getPendingList(map);
        call.enqueue(callback);
    }

    public void getCompletedReportList(Map<String, String> map, MyCallback<ArrayList<ReportCompletedList>> callback) {
        Call<ArrayList<ReportCompletedList>> call = restInterface.getCompletedReportList(map);
        call.enqueue(callback);
    }

    public void getTutorialList(Map<String, String> map, MyCallback<ArrayList<TutorialListModel>> callback) {
        Call<ArrayList<TutorialListModel>> call = restInterface.getTutorialList(map);
        call.enqueue(callback);
    }


    public void getTutorialSteps(Map<String, String> map, MyCallback<ArrayList<TutorialModel>> callback) {
        Call<ArrayList<TutorialModel>> call = restInterface.getTutorialSteps(map);
        call.enqueue(callback);
    }


    public void getChatRequest(Map<String, String> map, MyCallback<ArrayList<ChatRequestModel>> callback) {
        Call<ArrayList<ChatRequestModel>> call = restInterface.getChatRequest(map);
        call.enqueue(callback);
    }


    public void getChatRequest1(Map<String, String> map, MyCallback<ArrayList<ChatRequestModel>> callback) {
        Call<ArrayList<ChatRequestModel>> call = restInterface.getChatRequest1(map);
        call.enqueue(callback);
    }

    public void getUserChatState(Map<String, String> map, MyCallback<ArrayList<UserChatStatus>> callback) {
        Call<ArrayList<UserChatStatus>> call = restInterface.getUserChatState(map);
        call.enqueue(callback);
    }


    public void getChatStateValues(Map<String, String> map, MyCallback<ArrayList<ChatStatusValueModel>> callback) {
        Call<ArrayList<ChatStatusValueModel>> call = restInterface.getChatStateValues(map);
        call.enqueue(callback);
    }

    public void getChatStateValuesbal(Map<String, String> map, MyCallback<ArrayList<ChatStatusValueModel>> callback) {
        Call<ArrayList<ChatStatusValueModel>> call = restInterface.getChatStateValuesbal(map);
        call.enqueue(callback);
    }


    public void getChatAttachment(Map<String, String> map, MyCallback<ArrayList<ChatAttachModel>> callback) {
        Call<ArrayList<ChatAttachModel>> call = restInterface.getChatAttachment(map);
        call.enqueue(callback);
    }

    public void getUploadCertficate(Map<String, String> map, MyCallback<ResponseBody> callback) {
        Call<ResponseBody> call = restInterface.getUploadCertificate(map);
        call.enqueue(callback);
    }

    public void getCALL_CLARIFICATION(Map<String, String> map, MyCallback<ResponseBody> callback) {
        Call<ResponseBody> call = restInterface.getCALL_CLARIFICATION(map);
        call.enqueue(callback);
    }

    public void getUploadCertficate2(Map<String, String> map, MyCallback<ResponseBody> callback) {
        Call<ResponseBody> call = restInterface.getUploadCertificate2(map);
        call.enqueue(callback);
    }

    public void getUploadCertficate3(Map<String, String> map, MyCallback<ResponseBody> callback) {
        Call<ResponseBody> call = restInterface.getUploadCertificate3(map);
        call.enqueue(callback);
    }


    public void getPandithReply(Map<String, String> map, MyCallback<ResponseBody> callback) {
        Call<ResponseBody> call = restInterface.getPandithReply(map);
        call.enqueue(callback);
    }

    public void getChatSstatus(Map<String, String> map, MyCallback<ResponseBody> callback) {
        Call<ResponseBody> call = restInterface.getChatSstatus(map);
        call.enqueue(callback);
    }

    public void getNoResponce(Map<String, String> map, MyCallback<ResponseBody> callback) {
        Call<ResponseBody> call = restInterface.getNoResponce(map);
        call.enqueue(callback);
    }

    public void getNoResponceFromUser(Map<String, String> map, MyCallback<ResponseBody> callback) {
        Call<ResponseBody> call = restInterface.getNoResponceFromUser(map);
        call.enqueue(callback);
    }


    public void getChatStatusCancel(Map<String, String> map, MyCallback<ResponseBody> callback) {
        Call<ResponseBody> call = restInterface.getChatStatusCancel(map);
        call.enqueue(callback);
    }


    public void getChatHistory(Map<String, String> map, MyCallback<ArrayList<ChatHistory>> callback) {
        Call<ArrayList<ChatHistory>> call = restInterface.getChatHistory(map);
        call.enqueue(callback);
    }



    public void getCallHistory(Map<String, String> map, MyCallback<ArrayList<CallHistory>> callback) {
        Call<ArrayList<CallHistory>> call = restInterface.getCallHistory(map);
        call.enqueue(callback);
    }




    public void getWalletHistory(Map<String, String> map, MyCallback<ArrayList<WalletHistory>> callback) {
        Call<ArrayList<WalletHistory>> call = restInterface.getWalletHistory(map);
        call.enqueue(callback);
    }


}

