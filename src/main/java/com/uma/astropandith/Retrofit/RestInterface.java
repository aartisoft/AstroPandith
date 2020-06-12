package com.uma.astropandith.Retrofit;


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

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;


public interface RestInterface {


    @GET(GlobalVariables.REGISTRATION)
    Call<ResponseBody> getRegistration(@QueryMap Map<String, String> map);


    @GET(GlobalVariables.LOGIN)
    Call<List<LoginModel>> getLogin(@QueryMap Map<String, String> map);


    @GET(GlobalVariables.STATUS)
    Call<ResponseBody> getStatus(@QueryMap Map<String, String> map);


    @FormUrlEncoded
    @POST(GlobalVariables.PANDITH_REPLAY)
    Call<ResponseBody> setPandihReplay(@FieldMap Map<String, String> map);


    @GET(GlobalVariables.UPDATE_REPORT)
    Call<ResponseBody> setUpdateReport(@QueryMap Map<String, String> map);



    @GET(GlobalVariables.CALLSTATUS)
    Call<ResponseBody> getCallStatus(@QueryMap Map<String, String> map);


    @GET(GlobalVariables.CHATSTATUS)
    Call<ResponseBody> getChatStatus(@QueryMap Map<String, String> map);

    @GET(GlobalVariables.REPORT_PENDING_LIST)
    Call<ArrayList<ReportPendingList>> getPendingList(@QueryMap Map<String, String> map);

    @GET(GlobalVariables.STATUS_WAITLIST_ONLINE)
    Call<ResponseBody> getStatusWaitlisOnline(@QueryMap Map<String, String> map);



    @GET(GlobalVariables.CHAT_INFO_DATA)
    Call<ResponseBody> getChatInfoData(@QueryMap Map<String, String> map);

    @GET(GlobalVariables.REPORT_COMPLETED_LIST)
    Call<ArrayList<ReportCompletedList>> getCompletedReportList(@QueryMap Map<String, String> map);


    @GET(GlobalVariables.TUTORIAL_LIST)
    Call<ArrayList<TutorialListModel>> getTutorialList(@QueryMap Map<String, String> map);



    @GET(GlobalVariables.TUTORIAL_STEPS)
    Call<ArrayList<TutorialModel>> getTutorialSteps(@QueryMap Map<String, String> map);




    @GET(GlobalVariables.CHAT_REQUEST)
    Call<ArrayList<ChatRequestModel>> getChatRequest(@QueryMap Map<String, String> map);




    @GET(GlobalVariables.CHAT_REQUEST1)
    Call<ArrayList<ChatRequestModel>> getChatRequest1(@QueryMap Map<String, String> map);




    @GET(GlobalVariables.USER_CHAT_STATE)
    Call<ArrayList<UserChatStatus>> getUserChatState(@QueryMap Map<String, String> map);



    @GET(GlobalVariables.CHAT_SATE_VALUES)
    Call<ArrayList<ChatStatusValueModel>> getChatStateValues(@QueryMap Map<String, String> map);




    @GET(GlobalVariables.CHAT_SATE_VALUES_BAL)
    Call<ArrayList<ChatStatusValueModel>> getChatStateValuesbal(@QueryMap Map<String, String> map);




    @FormUrlEncoded
    @POST(GlobalVariables.CHAT_ATACHMENT)
    Call<ArrayList<ChatAttachModel>> getChatAttachment(@FieldMap Map<String, String> map);



    @GET(GlobalVariables.CALL_CLARIFICATION)
    Call<ResponseBody> getCALL_CLARIFICATION(@QueryMap Map<String, String> map);


    @FormUrlEncoded
    @POST(GlobalVariables.UPLOAD_CERTIFICATE)
    Call<ResponseBody> getUploadCertificate(@FieldMap Map<String, String> map);


    @FormUrlEncoded
    @POST(GlobalVariables.UPLOAD_CERTIFICATE2)
    Call<ResponseBody> getUploadCertificate2(@FieldMap Map<String, String> map);




    @FormUrlEncoded
    @POST(GlobalVariables.UPLOAD_CERTIFICATE3)
    Call<ResponseBody> getUploadCertificate3(@FieldMap Map<String, String> map);


    @GET(GlobalVariables.PANDITH_REPLY)
    Call<ResponseBody> getPandithReply(@QueryMap Map<String, String> map);



    @GET(GlobalVariables.CHAT_SATUS_UPDATE)
    Call<ResponseBody> getChatSstatus(@QueryMap Map<String, String> map);


    @GET(GlobalVariables.NO_RESPONCE)
    Call<ResponseBody> getNoResponce(@QueryMap Map<String, String> map);



    @GET(GlobalVariables.CHATSATUS_NORESPONCE_FROM_USER)
    Call<ResponseBody> getNoResponceFromUser(@QueryMap Map<String, String> map);



    @GET(GlobalVariables.CHAT_SATUS_CANCEL)
    Call<ResponseBody> getChatStatusCancel(@QueryMap Map<String, String> map);



    @GET(GlobalVariables.CHAT_HISTORY)
    Call<ArrayList<ChatHistory>> getChatHistory(@QueryMap Map<String, String> map);



    @GET(GlobalVariables.CALL_HISTORY)
    Call<ArrayList<CallHistory>> getCallHistory(@QueryMap Map<String, String> map);



    @GET(GlobalVariables.WALLET_HISTORY)
    Call<ArrayList<WalletHistory>> getWalletHistory(@QueryMap Map<String, String> map);






}
