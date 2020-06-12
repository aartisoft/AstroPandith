package com.uma.astropandith.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.uma.astropandith.Adapters.MessageAdapter;
import com.uma.astropandith.Model.Chat;

import com.uma.astropandith.Model.ChatAttachModel;
import com.uma.astropandith.Model.ChatRequestModel;
import com.uma.astropandith.Model.ChatStatusValueModel;
import com.uma.astropandith.Model.UserChatStatus;
import com.uma.astropandith.R;
import com.uma.astropandith.Retrofit.GlobalVariables;
import com.uma.astropandith.Retrofit.MyCallback;
import com.uma.astropandith.Retrofit.RestCallback;
import com.uma.astropandith.Retrofit.RestService;
import com.uma.astropandith.Service.ChatNotifyService;
import com.uma.astropandith.Service.RemoveAppTrack;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
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

import static com.uma.astropandith.Retrofit.GlobalVariables.BASE_URL;

public class CoverstionActivity extends AppCompatActivity implements RestCallback {


    private RecyclerView recyclerView;
    private ImageView profile_image;
    private TextView username;
    private TextView text_send;
    private String _pid;
    private String _pname;
    private boolean notify = false;
    private DatabaseReference reference;
    private ArrayList<Chat> mchat;
    private MessageAdapter messageAdapter;
    private ValueEventListener seenListener;
    private ProgressDialog progressBar;
    private String _uname;
    private String _uid;
    private ImageButton btn_send;
    private String userid;
    private String reciverid;
    private String _status;
    private AlertDialog alertD;
    private ArrayList<UserChatStatus> _chatUseRespnce;
    private String _reportid;
    private SharedPreferences sharedPreferences1;
    private String _chatId;
    private CountUpTimer timer;
    private String bitmapImage;
    private Uri filePath;
    private Bitmap resizeBitmap;
    private ArrayList<ChatAttachModel> _ChatAttachModel;
    private String _imageURL;
    private ImageView _selectImage;
    private ArrayList<ChatStatusValueModel> _chatRequest;
    private String _chatNotify;
    private Handler ha;
    private Disposable disposable;
    private CountDownTimer TTTTT;
    private WebView mWebView;
    private String url;

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
    private Date currentTime;
    private Calendar calander;
    private SimpleDateFormat simpledateformat;
    private SimpleDateFormat simpleDate;
    private String simpleTime;
    private TextView timerTv;
    private String time;
    private Integer tTime;
    private boolean ImageChoose= true;
    private JsonObjectRequest mJsonRequest;
    private RequestQueue requestQueue;
    private String _serviceStarted;
    private CountDownTimer timeM;
    private Context Context;
    private ArrayList<ChatRequestModel> _CchatRequest;
    private String notifyState;
    private String rowid;
    private String pname;
    private String uid;
    private String uname;
    private ArrayList<ChatStatusValueModel> _chatRequestb;
    private String _chatNotifyb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coverstion);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


       // Toast.makeText(CoverstionActivity.this, "this activity has bugs", Toast.LENGTH_LONG).show();
        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Loading");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);
        progressBar.show();


        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            _pid = extras.getString("pid");
            _pname = extras.getString("pname");
             uname = extras.getString("uname");
             uid = extras.getString("uid");
            _chatId = extras.getString("reportid");
            uMins = extras.getString("uMins");

        }

        requestQueue = Volley.newRequestQueue(this);

//
//        sharedPreferences1 = getSharedPreferences("notifyID", Context.MODE_PRIVATE);
//        _chatId = sharedPreferences1.getString("reportid", null); // getting String
//        uid = sharedPreferences1.getString("uid", null); // getting String
//        uname = sharedPreferences1.getString("uname", null); // getting String
//        uMins = sharedPreferences1.getString("uMins", null); // getting String
//

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        profile_image = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);
        text_send = findViewById(R.id.text_send);
        btn_send = findViewById(R.id.btn_send);
        _selectImage = findViewById(R.id.selectImage);
        _time_show = findViewById(R.id.time_show);
        timerTv = findViewById(R.id.timer);



//
//
//
//        Intent intent1 = new Intent(this, RemoveAppTrack.class);
//        ServiceCaller(intent1);
//
//

        userid = _chatId;
        reciverid = _uid;

       // Toast.makeText(this, _uid + "  "+ reportid, Toast.LENGTH_SHORT).show();


//        reference = FirebaseDatabase.getInstance().getReference("Users").child(reciverid);
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                username.setText(_uname);
//
//               // readMesagges(userid, reciverid);
//
//               // Toast.makeText(CoverstionActivity.this, "Sttttttt", Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        if(uMins!=null) {

            int InTime = Integer.valueOf(uMins);

             minSecns = (int) (InTime  * 60000);


        }


       uMisTime =  new CountDownTimer(minSecns, 1000) {

            public void onTick(long millisUntilFinished) {

                // long minutes = (millisUntilFinished % 3600) / 60;
                //  Toast.makeText(ChatActivity.this, millisUntilFinished +" ", Toast.LENGTH_SHORT).show();

                updateCountDownText(millisUntilFinished);

                // _time_show.setText(String.valueOf(minutes));

            }

            public void onFinish() {

                ImageChoose = true;

                _time_show.setText("Low Balance Chat will end soon");

                  timer.cancel();

                 time = timerTv.getText().toString();

                getChatCheckAPI();


            }

        };



        disposable = Observable.interval(1000, 7000,
                TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this:: getChatAPI, this::onError);

//
//        ha =new Handler();
//
//        ha.postDelayed( new Runnable() {
//
//            @Override
//            public void run() {
//
//
//
//
//              //  ChatUserStateAPI();
//
//
//                ha.postDelayed(this, 10000);
//            }
//
//        }, 10000);


//        timer = new CoverstionActivity.CountUpTimer(1000000000) {
//            public void onTick(int second) {
//
//                String  sec =  String.valueOf(second);
//
//
//                if(sec.equals("120")){
//
//                    if(_status.equals("2")){
//
//                        getChatServerCancelAPI();
//
//                    }
//
//                }
//
//
//            }
//        };

        timer = new CountUpTimer(1000000000) {
            public void onTick(int second) {

                timerTv.setText(String.valueOf(second));


            }
        };



        _selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(CoverstionActivity.this, "this activity has bugs", Toast.LENGTH_LONG).show();
                selectImage();
            }
        });


        dialog2 =new AlertDialog.Builder(CoverstionActivity.this);
        dialog2.setMessage("Kindly wait for the user to accept the chat Don't Do any Operations");
        dialog2.setTitle("Waiting for User");
        alertDialog2=dialog2.create();

        alertDialog2.getWindow().setGravity(Gravity.BOTTOM);
        alertDialog2.show();
        alertDialog2.setCancelable(false);

        TTTTT = new CountDownTimer(60000*10, 1000) {

            public void onTick(long millisUntilFinished) {

                // long minutes = (millisUntilFinished % 3600) / 60;
                //  Toast.makeText(ChatActivity.this, millisUntilFinished +" ", Toast.LENGTH_SHORT).show();

                updateText(millisUntilFinished);
                // _time_show.setText(String.valueOf(minutes));

            }

            public void onFinish() {

//                if(_chatNotify.equals("2")){
//
//                    getChatServerCancelAPI();
//                    getWaitlistOnlineAPI();
//                    // getChatEndAPI();
//                    getWiatToOnline();
//
//                }
            }

        };

        TTTTT.start();

      //  url = "https://meraastro.com/new_version/chat-messeges.php?pandit_id="+_pid+"&user_id="+_uid+"&chat_id="+_chatId+"";
        url = "https://meraastro.com/new_version/pandit-chat-messegesApi.php?pandit_id="+_pid+"&user_id="+_uid+"&chat_id="+_chatId+"";
       // url = "https://meraastro.com/test.meraastro.com/messenger/chat-massage.php?cid="+_chatId;

   //    Toast.makeText(this, _pid+ "  "+_chatId , Toast.LENGTH_LONG).show();

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


                try {
                    progressBar.dismiss();
                } catch (final IllegalArgumentException e) {
                    // Do nothing.
                } catch (final Exception e) {
                    // Do nothing.
                }

                super.onPageFinished(view, url);
            }


            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {

                mWebView.loadUrl("about:blank");

                AlertDialog alertDialog = new AlertDialog.Builder(CoverstionActivity.this).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Check your internet connection and try again.");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Try Again", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        mWebView.loadUrl(url);

                        progressBar.show();


                    }
                });


                try {

                    alertDialog.show();

                }
                catch (WindowManager.BadTokenException e) {
                    //use a log message
                }

                super.onReceivedError(view, request, error);
            }

        });


        mWebView.setWebChromeClient(new WebChromeClient() {

            // For Android 5.0
            public boolean onShowFileChooser(WebView view, ValueCallback<Uri[]> filePath, WebChromeClient.FileChooserParams fileChooserParams) {
                ImageChoose = true;

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
                ImageChoose = true;

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


    private void ServiceCaller(Intent intent){

        startService(intent);

    }


    @Override
    protected void onRestart() {


        if(_serviceStarted!=null) {

            if(_serviceStarted.equals("TRUE")) {

                timeM.cancel();

                Intent intent1 = new Intent(this, RemoveAppTrack.class);
                stopService(intent1);

            }

        }

        super.onRestart();
    }



    @Override
    protected void onStop() {

        SharedPreferences sharedPreferences2= getSharedPreferences("notifyID",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sharedPreferences2.edit();
        editor1.putString("uMins", String.valueOf(TimeUnit.MILLISECONDS.toSeconds(minSecns)));
        editor1.putString("usedSec", timerTv.getText().toString());
        editor1.apply();

        SharedPreferences sharedPreferences = getSharedPreferences("ChatC",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("chatId",_chatId);
        editor.putString("uid",uid);
        editor.putString("uname",uname);
        editor.putString("pid",_pid);
        editor.putString("pname",_pname);
        editor.putString("sec", timerTv.getText().toString());
        editor.commit();
        editor.apply();


        if(!ImageChoose) {
//
//
//            time = timerTv.getText().toString();
//
//            getChatDataAPI();
//
//            timer.cancel();
//            uMisTime.cancel();
//

            timeM =  new CountDownTimer(2*60000, 2000) {

                public void onTick(long millisUntilFinished) {

                    // long minutes = (millisUntilFinished % 3600) / 60;
                    //  Toast.makeText(ChatActivity.this, millisUntilFinished +" ", Toast.LENGTH_SHORT).show();
                    Toast.makeText(CoverstionActivity.this, "Meraastro Chat Running", Toast.LENGTH_SHORT).show();

                }

                public void onFinish() {

                    if(_chatId!=null) {

                        time = timerTv.getText().toString();
                        if (!_chatNotify.equals("51")) {

                            getChatDataAPI();

                            Toast.makeText(CoverstionActivity.this, "Chat Ended", Toast.LENGTH_SHORT).show();

                        } else if (!_chatNotify.equals("5")) {

                            getChatDataAPI();

                            Toast.makeText(CoverstionActivity.this, "Chat Ended", Toast.LENGTH_SHORT).show();
                        } else if (!_chatNotify.equals("61")) {

                            getChatDataAPI();

                            Toast.makeText(CoverstionActivity.this, "Chat Ended", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            };

            timeM.start();


            Intent intent1 = new Intent(this, RemoveAppTrack.class);
            ServiceCaller(intent1);

            _serviceStarted = "TRUE";


        }

        super.onStop();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        ImageChoose = false;

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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (mWebView.canGoBack()) {
                        mWebView.goBack();

                    } else {

                        Toast.makeText(getApplicationContext(), "Back press disabled!", Toast.LENGTH_SHORT).show();

                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
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




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                 finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void ChatUserStateAPI() {


        HashMap<String, String> map = new HashMap<String, String>();
        map.put("pid", _pid);
        RestService.getInstance(CoverstionActivity.this).getUserChatState(map, new MyCallback<ArrayList<UserChatStatus>>(CoverstionActivity.this,
                CoverstionActivity.this, GlobalVariables.SERVICE_MODE.USER_CHAT_STATE));

    }


    private void getChatServerCancelAPI() {


        HashMap<String, String> map = new HashMap<String, String>();
        map.put("rid", _chatId);
        map.put("StatusValue","58");
        map.put("pid", _pid);
        map.put("uid", uid);
        RestService.getInstance(CoverstionActivity.this).getNoResponceFromUser(map, new MyCallback<ResponseBody>(CoverstionActivity.this,
                CoverstionActivity.this, GlobalVariables.SERVICE_MODE.CHATSATUS_NORESPONCE_FROM_USER));


    }

    private void chatAtachment() {


        HashMap<String, String> map = new HashMap<String, String>();
        map.put("image", bitmapImage);
        RestService.getInstance(CoverstionActivity.this).getChatAttachment(map, new MyCallback<ArrayList<ChatAttachModel>>(CoverstionActivity.this,
                CoverstionActivity.this, GlobalVariables.SERVICE_MODE.CHAT_ATACHMENT));

    }


    private void getChatEndAPI() {


        SharedPreferences sharedPreferences1 = getSharedPreferences("ChatC", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit1 = sharedPreferences1.edit();
        edit1.clear();
        edit1.apply();


        HashMap<String, String> map = new HashMap<String, String>();
        map.put("rid", _chatId);
        map.put("StatusValue", "5");
        map.put("pid", _pid);
        map.put("uid", uid);
        RestService.getInstance(CoverstionActivity.this).getChatSstatus(map, new MyCallback<ResponseBody>(CoverstionActivity.this,
                CoverstionActivity.this, GlobalVariables.SERVICE_MODE.CHAT_SATUS_UPDATE));

    }


    private void getChatAPI(Long aLong) {

   //     Toast.makeText(this, uMins+" "+_chatId+ "   "+ _pid, Toast.LENGTH_SHORT).show();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("rid", _chatId);
        map.put("pid", _pid);
        RestService.getInstance(CoverstionActivity.this).getChatStateValues(map, new MyCallback<ArrayList<ChatStatusValueModel>>(CoverstionActivity.this,
                CoverstionActivity.this, GlobalVariables.SERVICE_MODE.CHAT_SATE_VALUES));

    }


    private void getChatCheckAPI() {

   //     Toast.makeText(this, uMins+" "+_chatId+ "   "+ _pid, Toast.LENGTH_SHORT).show();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("rid", _chatId);
        map.put("pid", _pid);
        RestService.getInstance(CoverstionActivity.this).getChatStateValuesbal(map, new MyCallback<ArrayList<ChatStatusValueModel>>(CoverstionActivity.this,
                CoverstionActivity.this, GlobalVariables.SERVICE_MODE.CHAT_SATE_VALUES_BAL));

    }

    private void getWaitlistOnlineAPI() {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("pid", _pid);
        RestService.getInstance(CoverstionActivity.this).getStatusWaitlisOnline(map, new MyCallback<ResponseBody>(CoverstionActivity.this,
                CoverstionActivity.this, GlobalVariables.SERVICE_MODE.STATUS_WAITLIST_ONLINE));

    }

    public void getChatDataAPI() {

        //progressBar.show();
        if(!progressBar.isShowing()) {
            progressBar.show();
        }


        currentTime = Calendar.getInstance().getTime();
        calander = Calendar.getInstance();
        simpledateformat = new SimpleDateFormat("dd-MM-yyyy");
        String Datet = simpledateformat.format(calander.getTime());
        simpleDate = new SimpleDateFormat("HH:mm:ss");
        simpleTime = simpleDate.format(calander.getTime());


    //    tTime = Integer.valueOf(time);

//        if(_ChatType!=null) {
//
//            if (_ChatType.equals("CCHAT")) {
//
//                int t = Integer.valueOf(time);
//                int tc = Integer.valueOf(_usedSec);
//
//                tTime = t+tc;
//
//            }
//        }


        HashMap<String, String> map = new HashMap<String, String>();
        map.put("chatid", _chatId);
        map.put("pid", _pid);
        map.put("pname", _pname);
        map.put("uid", uid);
        map.put("uname", uname);
        map.put("chtime", String.valueOf(simpleTime));
        map.put("chdate", Datet);
        map.put("duration", String.valueOf(time));
        RestService.getInstance(CoverstionActivity.this).getChatInfoData(map, new MyCallback<ResponseBody>(CoverstionActivity.this,
                CoverstionActivity.this, GlobalVariables.SERVICE_MODE.CHAT_INFO_DATA));




        SharedPreferences sharedPreferences1 = getSharedPreferences("ChatC", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit1 = sharedPreferences1.edit();
        edit1.clear();
        edit1.apply();


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

            case CHAT_ATACHMENT:

                Toast.makeText(this, "Attach fail", Toast.LENGTH_SHORT).show();

                break;

            case CHAT_SATUS_UPDATE:

                Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();


                break;


            case CHAT_SATE_VALUES:

                Toast.makeText(this,"FAIL", Toast.LENGTH_SHORT).show();

                break;

            case CHAT_SATE_VALUES_BAL:

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


                SharedPreferences sharedPreferences1 = getSharedPreferences("notifyID", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit1 = sharedPreferences1.edit();
                edit1.clear();
                edit1.apply();


                Toast.makeText(this, "No Response From User", Toast.LENGTH_SHORT).show();

                Intent intent1 = new Intent(getApplicationContext(), ShowMsgActivity.class);
                intent1.putExtra("msg", "No Response From User");

                startActivity(intent1);
                break;


            case CHAT_ATACHMENT:

                _ChatAttachModel = (ArrayList<ChatAttachModel>) response.body();


                //  Toast.makeText(this, "Attach Success", Toast.LENGTH_SHORT).show();


                String url = _ChatAttachModel.get(0).getImgUrl();


                _imageURL = "https://meraastro.com/admin/" + url;

                if (url != null) {

                    // Toast.makeText(this, url, Toast.LENGTH_LONG).show();

                   // sendMe(userid, reciverid, _imageURL);

                }

                break;


            case CHAT_SATUS_UPDATE:


                disposable.dispose();
                if (!progressBar.isShowing()) {
                    progressBar.dismiss();
                }

                //Toast.makeText(CoverstionActivity.this, "Chat End", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), ShowMsgActivity.class);
                intent.putExtra("msg", "Chat Ended");

                startActivity(intent);


                finish();

                break;



            case CHAT_SATE_VALUES_BAL:


                _chatRequestb = (ArrayList<ChatStatusValueModel>) response.body();

                _chatNotifyb = _chatRequestb.get(0).getChatStatusValue();

                if (!_chatNotifyb.equals("61")) {
//                timer.cancel();
                    getChatDataAPI();

                } else if (!_chatNotifyb.equals("51")) {
//                timer.cancel();
                    getChatDataAPI();

                } else {
//                timer.cancel();

                    Intent intent22 = new Intent(getApplicationContext(), ShowMsgActivity.class);
                    intent22.putExtra("msg", "Chat Ended");
                    startActivity(intent22);

                    finish();
                }

                break;

          case CHAT_SATE_VALUES:


                _chatRequest = (ArrayList<ChatStatusValueModel>) response.body();


                _chatNotify = _chatRequest.get(0).getChatStatusValue();


                if (!_chatNotify.isEmpty()) {

                    if (_chatNotify.equals("61")) {

                        // Toast.makeText(this,_chatNotify+"   " +"User Chat Ended Button ", Toast.LENGTH_SHORT).show();
                        ImageChoose = true;

                        disposable.dispose();

                        timer.cancel();

                        TTTTT.cancel();
                        uMisTime.cancel();
                        //                       timeM.cancel();


//                        AlertDialog.Builder dialog1 =new AlertDialog.Builder(CoverstionActivity.this);
//                        dialog1.setMessage("User has Ended the Chat");
//                        dialog1.setTitle("Alert");
//                        dialog1.setPositiveButton("Okay",
//                                new   DialogInterface.OnClickListener() {
//
//                                    public void onClick(DialogInterface dialog,
//                                                        int which) {


                        getWaitlistOnlineAPI();
                        getWiatToOnline();
                        //Toast.makeText(CoverstionActivity.this, "Chat Ended", Toast.LENGTH_SHORT).show();
                        Intent intent13 = new Intent(getApplicationContext(), ShowMsgActivity.class);
                        intent13.putExtra("msg", "Chat has Ended due to low Balance");
                        startActivity(intent13);


                        SharedPreferences sharedPreferences13 = getSharedPreferences("notifyID", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit13 = sharedPreferences13.edit();
                        edit13.clear();
                        edit13.apply();


                        finish();

//                                    }
//                                });
//
//                        AlertDialog alertDialog1=dialog1.create();
//                        if(!alertDialog1.isShowing()) {
//                            alertDialog1.show();
//                        }
//                        alertDialog1.setCancelable(false);


                    } else if (_chatNotify.equals("51")) {

                        // Toast.makeText(this,_chatNotify+"   " +"User Chat Ended Button ", Toast.LENGTH_SHORT).show();

                        ImageChoose = true;

                        disposable.dispose();
                        uMisTime.cancel();
                        TTTTT.cancel();
                        timer.cancel();
//                        timeM.cancel();

//
//                        AlertDialog.Builder dialog1 =new AlertDialog.Builder(CoverstionActivity.this);
//                        dialog1.setMessage("User has Ended the Chat");
//                        dialog1.setTitle("Alert");
//                        dialog1.setPositiveButton("Okay",
//                                new   DialogInterface.OnClickListener() {
//
//                                    public void onClick(DialogInterface dialog,
//                                                        int which) {


                        getWaitlistOnlineAPI();
                        getWiatToOnline();
                        //Toast.makeText(CoverstionActivity.this, "Chat Ended", Toast.LENGTH_SHORT).show();
                        Intent intent3 = new Intent(getApplicationContext(), ShowMsgActivity.class);
                        intent3.putExtra("msg", "User has Ended the Chat");
                        startActivity(intent3);

                        SharedPreferences sharedPreferences3 = getSharedPreferences("notifyID", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit3 = sharedPreferences3.edit();
                        edit3.clear();
                        edit3.apply();

                        finish();

//                                    }
//                                });
//
//                        AlertDialog alertDialog1=dialog1.create();
//                        if(alertDialog1!=null) {
//                            alertDialog1.show();
//                        }
//                        alertDialog1.setCancelable(false);


                    } else if (_chatNotify.equals("8")) {

                        // Toast.makeText(this,_chatNotify+"   " +"User Chat Ended Button ", Toast.LENGTH_SHORT).show();

                        ImageChoose = true;

                        disposable.dispose();
                        uMisTime.cancel();
                        timer.cancel();
//                        timeM.cancel();


//                        AlertDialog.Builder dialog1 =new AlertDialog.Builder(CoverstionActivity.this);
//                        dialog1.setMessage("User has Canceled the Chat");
//                        dialog1.setTitle("Alert");
//                        dialog1.setPositiveButton("Okay",
//                                new DialogInterface.OnClickListener() {
//
//                                    public void onClick(DialogInterface dialog,
//                                                        int which) {

                        // Toast.makeText(CoverstionActivity.this, "Chat Ended", Toast.LENGTH_SHORT).show();

                        Intent intent2 = new Intent(getApplicationContext(), ShowMsgActivity.class);
                        intent2.putExtra("msg", "User has Canceled the Chat");
                        startActivity(intent2);


                        SharedPreferences sharedPreferences12 = getSharedPreferences("notifyID", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit12 = sharedPreferences12.edit();
                        edit12.clear();
                        edit12.apply();

                        finish();

//                                    }
//                                });
//
//                        AlertDialog alertDialog1=dialog1.create();
//                        alertDialog1.show();
//                        alertDialog1.setCancelable(false);

                    }else if (_chatNotify.equals("58")) {

                        // Toast.makeText(this,_chatNotify+"   " +"User Chat Ended Button ", Toast.LENGTH_SHORT).show();

                        ImageChoose = true;

                        disposable.dispose();
                        uMisTime.cancel();
                        timer.cancel();
//                        timeM.cancel();


//                        AlertDialog.Builder dialog1 =new AlertDialog.Builder(CoverstionActivity.this);
//                        dialog1.setMessage("User has Canceled the Chat");
//                        dialog1.setTitle("Alert");
//                        dialog1.setPositiveButton("Okay",
//                                new DialogInterface.OnClickListener() {
//
//                                    public void onClick(DialogInterface dialog,
//                                                        int which) {

                        // Toast.makeText(CoverstionActivity.this, "Chat Ended", Toast.LENGTH_SHORT).show();

                        Toast.makeText(this, "No Response From User", Toast.LENGTH_SHORT).show();

                        Intent intent2 = new Intent(getApplicationContext(), ShowMsgActivity.class);
                        intent2.putExtra("msg", "No Response From User");

                        startActivity(intent2);
//                                    }
//                                });
//
//                        AlertDialog alertDialog1=dialog1.create();
//                        alertDialog1.show();
//                        alertDialog1.setCancelable(false);

                    } else if (_chatNotify.equals("7")) {

                        if (FIRST.equals("FIRST")) {

                            if (uMins != null) {

                                uMisTime.start();
                                timer.start();

                                TTTTT.cancel();
                                if (alertDialog2 != null) {
                                    alertDialog2.dismiss();
                                    ImageChoose = false;
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
                getWiatToOnline();


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

        android.app.AlertDialog.Builder dialog=new android.app.AlertDialog.Builder(CoverstionActivity.this);
        dialog.setMessage("Are You Sure?");
        dialog.setTitle("Alert");
        dialog.setPositiveButton("END",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {

                        if(uMins!=null) {

                            ImageChoose= true;

                            time = timerTv.getText().toString();

                            getChatDataAPI();

                        }

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

    @Override
    protected void onDestroy() {

       // getChatDataAPI();
        disposable.dispose();

        timer.cancel();

        TTTTT.cancel();
        uMisTime.cancel();

        FIRST = "FIRST";

        sharedPreferences1 = getSharedPreferences("notifyID",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences1.edit();
        editor.clear();


        super.onDestroy();
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


    public void selectImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 7);
        Toast.makeText(CoverstionActivity.this, "this activity goes to select image", Toast.LENGTH_LONG).show();

    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 7 && resultCode == RESULT_OK && data != null && data.getData() != null) {
//
//            filePath = data.getData();
//
//            try {
//
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//
//
//                resizeBitmap = resize(bitmap, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
//
//
//
//                convertImageToBase64(resizeBitmap);
//
//                //imageName = bitmap.toString();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//
//        }
//    }


    public void getWiatToOnline() {

        mJsonRequest = new JsonObjectRequest(BASE_URL+"users/updateStatusWaitListToOnline.php?pid=" + _pid, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(CoverstionActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(mJsonRequest);

    }


    private void convertImageToBase64(Bitmap bm) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 70, baos); //bm is the bitmap object
        byte[] byteArrayImage = baos.toByteArray();

        bitmapImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);

        if(bitmapImage!=null){

            chatAtachment();

        }

    }

    private static Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > ratioBitmap) {
                finalWidth = (int) ((float) maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float) maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }




}
