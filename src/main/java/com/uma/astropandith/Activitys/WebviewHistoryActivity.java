package com.uma.astropandith.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.uma.astropandith.R;

public class WebviewHistoryActivity extends AppCompatActivity {

    private ProgressDialog progressBar;
    private ImageView profile_image;
    private TextView username;
    private EditText text_send;
    private ImageButton btn_send;
    private String _pid;
    private String _pname;
    private String _uname;
    private String _uid;
    private String reportid;
    private String _feedback;
    private String _rating;
    private RatingBar ratingbar;
    private TextView feedback_text;
    private String url;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_history);


        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Loading");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);
         progressBar.show();

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

        username.setText(_pname);

        ratingbar = (RatingBar) findViewById(R.id.ratingbarcall);
        feedback_text = (TextView) findViewById(R.id.feedback_text);
        feedback_text.setText(_feedback);
        ratingbar.setRating(Float.parseFloat(_rating));


        //       url = "https://meraastro.com/new_version/pandit-chat-messeges.php?pandit_id="+_pid+"&user_id="+_uid+"&chat_id="+reportid+"";
        url = "https://meraastro.com/Pandit/pandit-chat-histroy.php?id="+reportid;
        // url = "https://meraastro.com/test.meraastro.com/messenger/user-chat-history.php?cid="+reportid+"&pid="+_pid;



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



}
