package com.uma.astropandith.Activitys;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.uma.astropandith.Adapters.MessageAdapter;
import com.uma.astropandith.Model.Chat;
import com.uma.astropandith.Model.ChatAttachModel;
import com.uma.astropandith.Model.ChatStatusValueModel;
import com.uma.astropandith.Model.UserChatStatus;
import com.uma.astropandith.R;
import com.uma.astropandith.Retrofit.GlobalVariables;
import com.uma.astropandith.Retrofit.MyCallback;
import com.uma.astropandith.Retrofit.RestService;
import com.uma.astropandith.Service.ChatNotifyService;
import com.uma.astropandith.Service.RemoveAppTrack;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class ConverstionHistory extends AppCompatActivity {


    private ProgressDialog progressBar;
    private RecyclerView recyclerView;
    private ImageView profile_image;
    private TextView username;
    private EditText text_send;
    private ImageButton btn_send;
    private String _pid;
    private String _pname;
    private String _uname;
    private String _uid;
    private String reportid;
    private String userid;
    private String reciverid;
    private DatabaseReference reference;
    private ArrayList<Chat> mchat;
    private MessageAdapter messageAdapter;
    private String _feedback;
    private String _rating;
    private RatingBar ratingbar;
    private TextView feedback_text;

    private static final int INPUT_FILE_REQUEST_CODE = 1;
    private static final int FILECHOOSER_RESULTCODE = 1;
    private static final String TAG = MainActivity.class.getSimpleName();
    private ValueCallback<Uri> mUploadMessage;
    private Uri mCapturedImageURI = null;
    private ValueCallback<Uri[]> mFilePathCallback;
    private String mCameraPhotoPath;
    private String url;
    private WebView mWebView;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converstion_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Loading");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);
        progressBar.show();



        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        profile_image = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);
        text_send = findViewById(R.id.text_send);
        btn_send = findViewById(R.id.btn_send);



        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            _pid = extras.getString("pid");
            _pname = extras.getString("pname");
            _uname = extras.getString("uname");
            _uid = extras.getString("uid");
            reportid = extras.getString("chatId");
            _feedback = extras.getString("feedback");
            _rating = extras.getString("rating");

        }

        userid = reportid;
        reciverid = _uid;


//        reference = FirebaseDatabase.getInstance().getReference("Users").child(reciverid);
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                username.setText(_uname);
//
//               readMesagges(userid, reciverid);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


        username.setText(_pname);

       // getMesages(_pid, _uid);


        db = FirebaseFirestore.getInstance();

        mchat = new ArrayList<>();

        db.collection("chat").whereEqualTo("chatid", reportid).orderBy("time", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {

                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {

                        if (e != null) {

                            return;
                        }

                        mchat.clear();

                        for (QueryDocumentSnapshot doc : value) {

                            Chat chat = doc.toObject( Chat.class);


                            if (chat.getChatid().equals(reportid)) {

                                if (chat.getReceiver().equals(_pid) && chat.getSender().equals(_uid) ||
                                        chat.getReceiver().equals(_uid) && chat.getSender().equals(_pid)) {
                                    mchat.add(chat);
//                                    Collections.reverse(mchat); // ADD THIS LINE TO REVERSE ORDER!
                                }

                            }

                            messageAdapter = new MessageAdapter(ConverstionHistory.this, mchat, _pid);
                            recyclerView.setAdapter(messageAdapter);

                        }

                        progressBar.dismiss();

                    }
                });




        ratingbar = (RatingBar) findViewById(R.id.ratingbarcall);
        feedback_text = (TextView) findViewById(R.id.feedback_text);
        feedback_text.setText(_feedback);
        ratingbar.setRating(Float.parseFloat(_rating));


 //       url = "https://meraastro.com/new_version/pandit-chat-messeges.php?pandit_id="+_pid+"&user_id="+_uid+"&chat_id="+reportid+"";
        url = "https://meraastro.com/Pandit/chat-messeges-history.php?id="+reportid;
       // url = "https://meraastro.com/test.meraastro.com/messenger/user-chat-history.php?cid="+reportid+"&pid="+_pid;



//        mWebView = (WebView) findViewById(R.id.web_view);
//        WebSettings webSettings = mWebView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//
//        mWebView.getSettings().setAllowFileAccess(true);
//        mWebView.loadUrl(url);
//
//
//
//        mWebView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                progressBar.show();
//                return false;
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                progressBar.dismiss();
//                super.onPageFinished(view, url);
//            }
//        });
//
//
//        mWebView.setWebChromeClient(new WebChromeClient() {
//
//            // For Android 5.0
//            public boolean onShowFileChooser(WebView view, ValueCallback<Uri[]> filePath, WebChromeClient.FileChooserParams fileChooserParams) {
//                // Double check that we don't have any existing callbacks
//                if (mFilePathCallback != null) {
//                    mFilePathCallback.onReceiveValue(null);
//                }
//                mFilePathCallback = filePath;
//                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//                    // Create the File where the photo should go
//                    File photoFile = null;
//                    try {
//                        photoFile = createImageFile();
//                        takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);
//                    } catch (IOException ex) {
//                        // Error occurred while creating the File
//                        Log.e(TAG, "Unable to create Image File", ex);
//                    }
//                    // Continue only if the File was successfully created
//                    if (photoFile != null) {
//                        mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
//                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
//                                Uri.fromFile(photoFile));
//                    } else {
//                        takePictureIntent = null;
//                    }
//                }
//                Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
//                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
//                contentSelectionIntent.setType("image/*");
//                Intent[] intentArray;
//                if (takePictureIntent != null) {
//                    intentArray = new Intent[]{takePictureIntent};
//                } else {
//                    intentArray = new Intent[0];
//                }
//                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
//                chooserIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
//                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
//                chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
//                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
//                startActivityForResult(chooserIntent, INPUT_FILE_REQUEST_CODE);
//                return true;
//            }
//            // openFileChooser for Android 3.0+
//            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
//                mUploadMessage = uploadMsg;
//                // Create AndroidExampleFolder at sdcard
//                // Create AndroidExampleFolder at sdcard
//                File imageStorageDir = new File(
//                        Environment.getExternalStoragePublicDirectory(
//                                Environment.DIRECTORY_PICTURES)
//                        , "AndroidExampleFolder");
//                if (!imageStorageDir.exists()) {
//                    // Create AndroidExampleFolder at sdcard
//                    imageStorageDir.mkdirs();
//                }
//                // Create camera captured image file path and name
//                File file = new File(
//                        imageStorageDir + File.separator + "IMG_"
//                                + String.valueOf(System.currentTimeMillis())
//                                + ".jpg");
//                mCapturedImageURI = Uri.fromFile(file);
//                // Camera capture image intent
//                final Intent captureIntent = new Intent(
//                        android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
//                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//                i.addCategory(Intent.CATEGORY_OPENABLE);
//                i.setType("image/*");
//                // Create file chooser intent
//                Intent chooserIntent = Intent.createChooser(i, "Image Chooser");
//                // Set camera intent to file chooser
//                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS
//                        , new Parcelable[] { captureIntent });
//                // On select image call onActivityResult method of activity
//                startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);
//            }
//            // openFileChooser for Android < 3.0
//            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
//                openFileChooser(uploadMsg, "");
//            }
//            //openFileChooser for other Android versions
//            public void openFileChooser(ValueCallback<Uri> uploadMsg,
//                                        String acceptType,
//                                        String capture) {
//                openFileChooser(uploadMsg, acceptType);
//            }
//
//        });
//
//
//
//    }
//
//
//
//    private void ServiceCaller(Intent intent){
//
//        startService(intent);
//
//    }
//
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            if (requestCode != INPUT_FILE_REQUEST_CODE || mFilePathCallback == null) {
//                super.onActivityResult(requestCode, resultCode, data);
//                return;
//            }
//            Uri[] results = null;
//            // Check that the response is a good one
//            if (resultCode == Activity.RESULT_OK) {
//                if (data == null) {
//                    // If there is not data, then we may have taken a photo
//                    if (mCameraPhotoPath != null) {
//                        results = new Uri[]{Uri.parse(mCameraPhotoPath)};
//                    }
//                } else {
//                    String dataString = data.getDataString();
//                    if (dataString != null) {
//                        results = new Uri[]{Uri.parse(dataString)};
//                    }
//                }
//            }
//            mFilePathCallback.onReceiveValue(results);
//            mFilePathCallback = null;
//        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
//            if (requestCode != FILECHOOSER_RESULTCODE || mUploadMessage == null) {
//                super.onActivityResult(requestCode, resultCode, data);
//                return;
//            }
//            if (requestCode == FILECHOOSER_RESULTCODE) {
//                if (null == this.mUploadMessage) {
//                    return;
//                }
//                Uri result = null;
//                try {
//                    if (resultCode != RESULT_OK) {
//                        result = null;
//                    } else {
//                        // retrieve from the private variable if the intent is null
//                        result = data == null ? mCapturedImageURI : data.getData();
//                    }
//                } catch (Exception e) {
//                    Toast.makeText(getApplicationContext(), "activity :" + e,
//                            Toast.LENGTH_LONG).show();
//                }
//                mUploadMessage.onReceiveValue(result);
//                mUploadMessage = null;
//            }
//        }
//        return;
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

    public void getMesages(final String myid, final String userid) {

        mchat = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // [START get_all_users]
        db.collection("chat").orderBy("time", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        mchat.clear();

                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Chat chat = document.toObject( Chat.class);

                                if(chat.getChatid().equals(reportid)) {

                                    if (chat.getReceiver().equals(myid) && chat.getSender().equals(userid) ||
                                            chat.getReceiver().equals(userid) && chat.getSender().equals(myid)) {
                                        mchat.add(chat);
//                                    Collections.reverse(mchat); // ADD THIS LINE TO REVERSE ORDER!
                                    }

                                }


                                messageAdapter = new MessageAdapter(ConverstionHistory.this, mchat, myid);
                                recyclerView.setAdapter(messageAdapter);

                               progressBar.dismiss();
                            }
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
        // [END get_all_users]
    }



    private void readMesagges(final String myid, final String userid){

        mchat = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mchat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(myid) && chat.getSender().equals(userid) ||
                            chat.getReceiver().equals(userid) && chat.getSender().equals(myid)){
                        mchat.add(chat);
                    }
                    messageAdapter = new MessageAdapter(ConverstionHistory.this, mchat,myid);
                    recyclerView.setAdapter(messageAdapter);
                    progressBar.dismiss();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (event.getAction() == KeyEvent.ACTION_DOWN) {
//            switch (keyCode) {
//                case KeyEvent.KEYCODE_BACK:
//                    if (mWebView.canGoBack()) {
//                        mWebView.goBack();
//
//                    } else {
////    @Override
////    public boolean onKeyDown(int keyCode, KeyEvent event) {
////        if (event.getAction() == KeyEvent.ACTION_DOWN) {
////            switch (keyCode) {
////                case KeyEvent.KEYCODE_BACK:
////                    if (mWebView.canGoBack()) {
////                        mWebView.goBack();
////
////                    } else {
////
////                        finish();
////
////                    }
////                    return true;
////            }
////
////        }
////        return super.onKeyDown(keyCode, event);
////    }
//                        finish();
//
//                    }
//                    return true;
//            }
//
//        }
//        return super.onKeyDown(keyCode, event);
//    }



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






}
