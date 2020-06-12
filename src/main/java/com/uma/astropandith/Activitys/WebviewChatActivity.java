package com.uma.astropandith.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.uma.astropandith.Model.ChatAttachModel;
import com.uma.astropandith.Model.ChatStatusValueModel;
import com.uma.astropandith.Model.UserChatStatus;
import com.uma.astropandith.R;
import com.uma.astropandith.Retrofit.GlobalVariables;
import com.uma.astropandith.Retrofit.MyCallback;
import com.uma.astropandith.Retrofit.RestCallback;
import com.uma.astropandith.Retrofit.RestService;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class WebviewChatActivity extends AppCompatActivity implements RestCallback {


    private static final int INPUT_FILE_REQUEST_CODE = 1;
    private static final int FILECHOOSER_RESULTCODE = 1;
    private static final String TAG = MainActivity.class.getSimpleName();
    private ValueCallback<Uri> mUploadMessage;
    private Uri mCapturedImageURI = null;
    private ValueCallback<Uri[]> mFilePathCallback;
    private String mCameraPhotoPath;
    private String uMins;
    private TextView _time_show;
    private CountDownTimer uMisTime;
    private String FIRST = "FIRST";
    private int minSecns;
    private AlertDialog.Builder dialog2;
    private AlertDialog alertDialog2;
    private ProgressDialog progressBar;
    private SharedPreferences sharedPreferences1;
    private String _chatId;
    private String uname;
    private String uid;
    private View profile_image;
    private View username;
    private String _pid;
    private String _pname;
    private String _uname;
    private String _uid;
    private String _reportid;
    private String userid;
    private String reciverid;
    private String url;
    private WebView mWebView;
    private Disposable disposable;
    private CountDownTimer TTTTT;
    private ArrayList<ChatStatusValueModel> _chatRequest;
    private String _chatNotify;
    private String _status;
    private ArrayList<UserChatStatus> _chatUseRespnce;
    private ArrayList<ChatAttachModel> _ChatAttachModel;
    private Date currentTime;
    private Calendar calander;
    private SimpleDateFormat simpledateformat;
    private SimpleDateFormat simpleDate;
    private String simpleTime;
    private TextView timerTv;
    private String time;
    private CoverstionActivity.CountUpTimer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_chat);

        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Loading");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);
        progressBar.show();


        sharedPreferences1 = getSharedPreferences("notifyID", Context.MODE_PRIVATE);
        _chatId = sharedPreferences1.getString("reportid", null); // getting String
        uid = sharedPreferences1.getString("uid", null); // getting String
        uname = sharedPreferences1.getString("uname", null); // getting String
        uMins = sharedPreferences1.getString("uMins", null); // getting String



        profile_image = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);
        _time_show = findViewById(R.id.time_show);


        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            _pid = extras.getString("pid");
            _pname = extras.getString("pname");
            _uname = extras.getString("uname");
            _uid = extras.getString("uid");
            _reportid = extras.getString("reportid");

        }


        userid = _chatId;
        reciverid = _uid;



        if(uMins!=null) {

            int InTime = Integer.valueOf(uMins);

            minSecns = (InTime - 1 ) * 60000;

        }



        uMisTime =  new CountDownTimer(minSecns, 1000) {

            public void onTick(long millisUntilFinished) {

                // long minutes = (millisUntilFinished % 3600) / 60;
                //  Toast.makeText(ChatActivity.this, millisUntilFinished +" ", Toast.LENGTH_SHORT).show();

                updateCountDownText(millisUntilFinished);

                // _time_show.setText(String.valueOf(minutes));


            }

            public void onFinish() {

                _time_show.setText("Low Balance Chat will end soon");


            }

        };


        disposable = Observable.interval(1000, 10000,
                TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this:: getChatAPI, this::onError);



        timer = new CoverstionActivity.CountUpTimer(1000000000) {
            public void onTick(int second) {

                timerTv.setText(String.valueOf(second));

            }
        };


        dialog2 =new AlertDialog.Builder(WebviewChatActivity.this);
        dialog2.setMessage("Kindly wait for the user to accept the chat Don't Do any Operations");
        dialog2.setTitle("Please Wait");
        alertDialog2=dialog2.create();
        alertDialog2.show();
        alertDialog2.setCancelable(true);

        TTTTT = new CountDownTimer(60000*4, 1000) {

            public void onTick(long millisUntilFinished) {

                // long minutes = (millisUntilFinished % 3600) / 60;
                //  Toast.makeText(ChatActivity.this, millisUntilFinished +" ", Toast.LENGTH_SHORT).show();

                updateText(millisUntilFinished);
                // _time_show.setText(String.valueOf(minutes));

            }

            public void onFinish() {


                if(_chatNotify.equals("2")){

                    getChatServerCancelAPI();
                    getWaitlistOnlineAPI();
                    // getChatEndAPI();

                }
            }

        };

        TTTTT.start();

        //  url = "https://meraastro.com/new_version/chat-messeges.php?pandit_id="+_pid+"&user_id="+_uid+"&chat_id="+_chatId+"";
        url = "https://meraastro.com/new_version/pandit-chat-messeges.php?pandit_id="+_pid+"&user_id="+_uid+"&chat_id="+_chatId+"";

        // url = "https://meraastro.com/new_version/pandit-chat-messeges.php?pandit_id=169&user_id=126&chat_id=1";
        timerTv = findViewById(R.id.timer);

        mWebView = (WebView) findViewById(R.id.web_view);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.loadUrl(url);



        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                progressBar.show();
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.dismiss();
                super.onPageFinished(view, url);
            }
        });


        mWebView.setWebChromeClient(new WebChromeClient() {

            // For Android 5.0
            public boolean onShowFileChooser(WebView view, ValueCallback<Uri[]> filePath, WebChromeClient.FileChooserParams fileChooserParams) {
                // Double check that we don't have any existing callbacks
                if (mFilePathCallback != null) {
                    mFilePathCallback.onReceiveValue(null);
                }
                mFilePathCallback = filePath;
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                        takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                        Log.e(TAG, "Unable to create Image File", ex);
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(photoFile));
                    } else {
                        takePictureIntent = null;
                    }
                }
                Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                contentSelectionIntent.setType("image/*");
                Intent[] intentArray;
                if (takePictureIntent != null) {
                    intentArray = new Intent[]{takePictureIntent};
                } else {
                    intentArray = new Intent[0];
                }
                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                chooserIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
                startActivityForResult(chooserIntent, INPUT_FILE_REQUEST_CODE);
                return true;
            }
            // openFileChooser for Android 3.0+
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                mUploadMessage = uploadMsg;
                // Create AndroidExampleFolder at sdcard
                // Create AndroidExampleFolder at sdcard
                File imageStorageDir = new File(
                        Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES)
                        , "AndroidExampleFolder");
                if (!imageStorageDir.exists()) {
                    // Create AndroidExampleFolder at sdcard
                    imageStorageDir.mkdirs();
                }
                // Create camera captured image file path and name
                File file = new File(
                        imageStorageDir + File.separator + "IMG_"
                                + String.valueOf(System.currentTimeMillis())
                                + ".jpg");
                mCapturedImageURI = Uri.fromFile(file);
                // Camera capture image intent
                final Intent captureIntent = new Intent(
                        android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                // Create file chooser intent
                Intent chooserIntent = Intent.createChooser(i, "Image Chooser");
                // Set camera intent to file chooser
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS
                        , new Parcelable[] { captureIntent });
                // On select image call onActivityResult method of activity
                startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);
            }
            // openFileChooser for Android < 3.0
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                openFileChooser(uploadMsg, "");
            }
            //openFileChooser for other Android versions
            public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                        String acceptType,
                                        String capture) {
                openFileChooser(uploadMsg, acceptType);
            }

        });

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (requestCode != INPUT_FILE_REQUEST_CODE || mFilePathCallback == null) {
                super.onActivityResult(requestCode, resultCode, data);
                return;
            }
            Uri[] results = null;
            // Check that the response is a good one
            if (resultCode == Activity.RESULT_OK) {
                if (data == null) {
                    // If there is not data, then we may have taken a photo
                    if (mCameraPhotoPath != null) {
                        results = new Uri[]{Uri.parse(mCameraPhotoPath)};
                    }
                } else {
                    String dataString = data.getDataString();
                    if (dataString != null) {
                        results = new Uri[]{Uri.parse(dataString)};
                    }
                }
            }
            mFilePathCallback.onReceiveValue(results);
            mFilePathCallback = null;
        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            if (requestCode != FILECHOOSER_RESULTCODE || mUploadMessage == null) {
                super.onActivityResult(requestCode, resultCode, data);
                return;
            }
            if (requestCode == FILECHOOSER_RESULTCODE) {
                if (null == this.mUploadMessage) {
                    return;
                }
                Uri result = null;
                try {
                    if (resultCode != RESULT_OK) {
                        result = null;
                    } else {
                        // retrieve from the private variable if the intent is null
                        result = data == null ? mCapturedImageURI : data.getData();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "activity :" + e,
                            Toast.LENGTH_LONG).show();
                }
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }
        }
        return;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return imageFile;
    }


    private void onError(Throwable throwable) {
        Toast.makeText(this, "OnError in Observable Timer",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {

        Toast.makeText(getApplicationContext(), "Back press disabled!", Toast.LENGTH_SHORT).show();

    }

    private String getDateTime() {

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);

    }
    private String getDateTimeSec() {

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);

    }


    private void getChatServerCancelAPI() {


        HashMap<String, String> map = new HashMap<String, String>();
        map.put("rid", _chatId);
        map.put("StatusValue","41");
        map.put("pid", _pid);
        map.put("uid", uid);
        RestService.getInstance(WebviewChatActivity.this).getNoResponceFromUser(map, new MyCallback<ResponseBody>(WebviewChatActivity.this,
                WebviewChatActivity.this, GlobalVariables.SERVICE_MODE.CHATSATUS_NORESPONCE_FROM_USER));


    }

    public void getChatDataAPI() {

        //progressBar.show();
        progressBar.show();


        currentTime = Calendar.getInstance().getTime();
        calander = Calendar.getInstance();
        simpledateformat = new SimpleDateFormat("dd-MM-yyyy");
        String Datet = simpledateformat.format(calander.getTime());
        simpleDate = new SimpleDateFormat("HH:mm:ss");
        simpleTime = simpleDate.format(calander.getTime());

        time = timerTv.getText().toString();


        HashMap<String, String> map = new HashMap<String, String>();
        map.put("chatid", _chatId);
        map.put("pid", _pid);
        map.put("pname", _pname);
        map.put("uid", uid);
        map.put("uname", uname);
        map.put("chtime", String.valueOf(simpleTime));
        map.put("chdate", Datet);
        map.put("duration", time);
        RestService.getInstance(WebviewChatActivity.this).getChatInfoData(map, new MyCallback<ResponseBody>(WebviewChatActivity.this,
                WebviewChatActivity.this, GlobalVariables.SERVICE_MODE.CHAT_INFO_DATA));

    }

    private void getChatEndAPI() {

        //progressBar.show();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("rid", _chatId);
        map.put("StatusValue", "5");
        map.put("pid", _pid);
        map.put("uid", uid);
        RestService.getInstance(WebviewChatActivity.this).getChatSstatus(map, new MyCallback<ResponseBody>(WebviewChatActivity.this,
                WebviewChatActivity.this, GlobalVariables.SERVICE_MODE.CHAT_SATUS_UPDATE));

    }



    private void getChatAPI(Long aLong) {

        //   Toast.makeText(this, reportid, Toast.LENGTH_SHORT).show();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("rid", _chatId);
        map.put("pid", _pid);
        RestService.getInstance(WebviewChatActivity.this).getChatStateValues(map, new MyCallback<ArrayList<ChatStatusValueModel>>(WebviewChatActivity.this,
                WebviewChatActivity.this, GlobalVariables.SERVICE_MODE.CHAT_SATE_VALUES));


    }

    private void getWaitlistOnlineAPI() {


        HashMap<String, String> map = new HashMap<String, String>();
        map.put("pid", _pid);
        RestService.getInstance(WebviewChatActivity.this).getStatusWaitlisOnline(map, new MyCallback<ResponseBody>(WebviewChatActivity.this,
                WebviewChatActivity.this, GlobalVariables.SERVICE_MODE.STATUS_WAITLIST_ONLINE));

    }


    @Override
    public void onFailure(Call call, Throwable t, GlobalVariables.SERVICE_MODE mode) {

        switch (mode) {


            case USER_CHAT_STATE:

                Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
                _status = "99";


                break;

            case CHATSATUS_NORESPONCE_FROM_USER:

                Toast.makeText(this, "Pandith server fail", Toast.LENGTH_SHORT).show();


                break;



            case CHAT_SATUS_UPDATE:

                Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();


                break;


            case CHAT_SATE_VALUES:

                Toast.makeText(this,"FAIL", Toast.LENGTH_SHORT).show();

                break;

            case STATUS_WAITLIST_ONLINE:


                Toast.makeText(this, "No Data Foun", Toast.LENGTH_SHORT).show();


                break;

            case CHAT_INFO_DATA:

                Toast.makeText(this, "Insert fail No Data Foun", Toast.LENGTH_SHORT).show();

                break;




        }

    }

    @Override
    public void onSuccess(Response response, GlobalVariables.SERVICE_MODE mode) {


        switch (mode) {


            case USER_CHAT_STATE:

                _chatUseRespnce = (ArrayList<UserChatStatus>) response.body();

                _status = _chatUseRespnce.get(0).getChatStatusValue();

                break;



            case CHATSATUS_NORESPONCE_FROM_USER:

                Toast.makeText(this, "No Response From User", Toast.LENGTH_SHORT).show();

                Intent intent1 = new Intent(getApplicationContext(), ShowMsgActivity.class);
                intent1.putExtra("msg","No Response From User");

                startActivity(intent1);
                break;



            case CHAT_SATUS_UPDATE:


                disposable.dispose();
                progressBar.dismiss();

                //Toast.makeText(CoverstionActivity.this, "Chat End", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), ShowMsgActivity.class);
                intent.putExtra("msg","Chat Ended");

                startActivity(intent);


                finish();





                break;


            case CHAT_SATE_VALUES:


                _chatRequest = (ArrayList<ChatStatusValueModel>) response.body();


                _chatNotify = _chatRequest.get(0).getChatStatusValue();



                if (!_chatNotify.isEmpty()) {


                    if(_chatNotify.equals("51")){

                        // Toast.makeText(this,_chatNotify+"   " +"User Chat Ended Button ", Toast.LENGTH_SHORT).show();


                        disposable.dispose();


                        AlertDialog.Builder dialog1 =new AlertDialog.Builder(WebviewChatActivity.this);
                        dialog1.setMessage("User has Ended the Chat");
                        dialog1.setTitle("Alert");
                        dialog1.setPositiveButton("Okay",
                                new   DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog,
                                                        int which) {

                                        getWaitlistOnlineAPI();
                                        //Toast.makeText(CoverstionActivity.this, "Chat Ended", Toast.LENGTH_SHORT).show();
                                        Intent intent1 = new Intent(getApplicationContext(), ShowMsgActivity.class);
                                        intent1.putExtra("msg","User has Ended the Chat");
                                        startActivity(intent1);

                                        finish();

                                    }
                                });

                        AlertDialog alertDialog1=dialog1.create();
                        alertDialog1.show();
                        alertDialog1.setCancelable(false);


                    }else  if(_chatNotify.equals("8")){

                        // Toast.makeText(this,_chatNotify+"   " +"User Chat Ended Button ", Toast.LENGTH_SHORT).show();

                        disposable.dispose();

                        AlertDialog.Builder dialog1 =new AlertDialog.Builder(WebviewChatActivity.this);
                        dialog1.setMessage("User has Canceled the Chat");
                        dialog1.setTitle("Alert");
                        dialog1.setPositiveButton("Okay",
                                new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog,
                                                        int which) {

                                        // Toast.makeText(CoverstionActivity.this, "Chat Ended", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(getApplicationContext(), ShowMsgActivity.class);
                                        intent.putExtra("msg","User has Canceled the Chat");
                                        startActivity(intent);

                                        finish();

                                    }
                                });

                        AlertDialog alertDialog1=dialog1.create();
                        alertDialog1.show();
                        alertDialog1.setCancelable(false);

                    }
                    else if(_chatNotify.equals("7")){

                        if(FIRST.equals("FIRST")) {

                            if(uMins!=null) {

                                uMisTime.start();
                                TTTTT.cancel();

                                timer.start();

                                if(alertDialog2!=null) {
                                    alertDialog2.dismiss();
                                }

                            }

                            FIRST = "SECOND";

                        }

                    }

                }

                break;

            case STATUS_WAITLIST_ONLINE:

                //Toast.makeText(this, "Ss", Toast.LENGTH_SHORT).show();


                break;

            case CHAT_INFO_DATA:
//
//                Intent intent = new Intent(getApplicationContext(), ChatStaeInfoActivity.class);
//                intent.putExtra("msg", lowB);
//                startActivity(intent);
//                progressBar.dismiss();
//                finish();
                getChatEndAPI();
                getWaitlistOnlineAPI();


                break;


        }


    }

    private void updateText(long mTimeLeftInMillis) {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d Min : %02d Sec", minutes, seconds);

        dialog2.setTitle("Please Wait  \n" +timeLeftFormatted);

    }
    private void updateCountDownText(long mTimeLeftInMillis) {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d Min : %02d Sec", minutes, seconds);

        _time_show.setText("Time Left : "+timeLeftFormatted);

    }


    public void endChat(View view) {



        android.app.AlertDialog.Builder dialog=new android.app.AlertDialog.Builder(WebviewChatActivity.this);
        dialog.setMessage("Are You Sure?");
        dialog.setTitle("Alert");
        dialog.setPositiveButton("END",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {


                       // getChatEndAPI();

                        getChatDataAPI();


                    }
                }); dialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {


                    }
                });

        AlertDialog alertDialog=dialog.create();
        alertDialog.show();




    }

    public abstract static class CountUpTimer extends CountDownTimer {
        private static final long INTERVAL_MS = 1000;
        private final long duration;

        protected CountUpTimer(long durationMs) {
            super(durationMs, INTERVAL_MS);
            this.duration = durationMs;
        }

        public abstract void onTick(int second);

        @Override
        public void onTick(long msUntilFinished) {
            int second = (int) ((duration - msUntilFinished) / 1000);
            onTick(second);
        }

        @Override
        public void onFinish() {
            onTick(duration / 1000);
        }
    }

}
