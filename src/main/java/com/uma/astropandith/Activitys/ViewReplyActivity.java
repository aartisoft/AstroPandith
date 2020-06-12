package com.uma.astropandith.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.uma.astropandith.R;

public class ViewReplyActivity extends AppCompatActivity {

    private TextView reportViewTV;
    private String _rID;
    private String _uid;
    private String _replayMsg;
    private String _clarificationIn;
    private String _clarificationMsg;
    private TextView clarificationQ;
    private TextView clarificationAns;
    private TextView claTitle;
    private TextView claTitleA;

    private String _reportType;
    private String _namePd;
    private String _phonePd;
    private String _genderPd;
    private String _dobPd;
    private String _tobPd;
    private String _pobcityPd;
    private String _pobstatePd;
    private String _pobcountryPd;
    private String _martialstatusPd;
    private String _occupationPd;
    private String _topicConcrenPd;
    private String _pnamePd;
    private String _pdobPd;
    private String _ptobPd;
    private String _ppobPd;
    private String _ppobcityPd;
    private String _ppobstatePd;
    private String _ppobscountryPd;
    private String _pcommentPd;
    private String _pobPd;
    private TextView _input_namer;
    private TextView _input_mobiler;
    private TextView _input_dobr;
    private TextView _input_timedobr;
    private TextView _input_placedobr;
    private TextView _input_statedobr;
    private TextView _input_countryedobr;
    private TextView _input_occupationr;
    private TextView _input_anyCommentsr;
    private TextView _input_pnamer;
    private TextView _input_partnerdobr;
    private TextView _input_partnertimedobr;
    private TextView _input_parnerplacedobr;
    private TextView _input_partnerstatedobr;
    private TextView _input_partnercountryedobr;
    private TextView _marriage_StatusPD;
    private TextView _reportType_TVPD;
    private TextView _genderP;
    private TextView _topicOfConcrenPD;
    private LinearLayout _parnerdetails;
    private TextView _input_partnerstatedobPD;
    private ProgressDialog progressBar;
    private LinearLayout cusDetails;
    private TextView show;
    private TextView hide;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reply);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            _rID = extras.getString("_rID");
            _uid = extras.getString("_uid");
            _replayMsg = extras.getString("replayMsg");
            _clarificationIn = extras.getString("clarificationIn");
            _clarificationMsg = extras.getString("clarificationMsg");


            _reportType = extras.getString("_reportType");
            _namePd = extras.getString("_namePd");
            _phonePd = extras.getString("_phonePd");
            _genderPd = extras.getString("_genderPd");
            _dobPd = extras.getString("_dobPd");
            _tobPd = extras.getString("_tobPd");
            _pobPd = extras.getString("_pobPd");
            _pobcityPd = extras.getString("_pobcityPd");
            _pobstatePd = extras.getString("_pobstatePd");
            _pobcountryPd = extras.getString("_pobcountryPd");
            _martialstatusPd = extras.getString("_martialstatusPd");
            _occupationPd = extras.getString("_occupationPd");
            _topicConcrenPd = extras.getString("_topicConcrenPd");


            _pnamePd = extras.getString("_pnamePd");
            _pdobPd =  extras.getString("_pdobPd");
            _ptobPd = extras.getString("_ptobPd");
            _ppobPd = extras.getString("_ppobPd");
            _ppobcityPd = extras.getString("_ppobcityPd");
            _ppobstatePd = extras.getString("_ppobstatePd");
            _ppobscountryPd = extras.getString("_ppobscountryPd");

            _pcommentPd = extras.getString("_pcommentPd");

        }


        clarificationQ = findViewById(R.id.clarificationQ);
        claTitle = findViewById(R.id.claTitle);

        if(_clarificationIn!=null) {

            if (!_clarificationIn.isEmpty()) {
                clarificationQ.setText(_clarificationIn);
            } else {
                clarificationQ.setVisibility(View.GONE);
                claTitle.setVisibility(View.GONE);
            }

        }


        claTitleA = findViewById(R.id.claTitleA);
        clarificationAns = findViewById(R.id.clarificationA);

        if(_clarificationMsg!=null) {

            if (!_clarificationMsg.isEmpty()) {
                clarificationAns.setText(_clarificationMsg);
            } else {
                clarificationAns.setVisibility(View.GONE);
                claTitleA.setVisibility(View.GONE);
            }
        }

        reportViewTV = findViewById(R.id.reportViewTV);

        if(_replayMsg!=null){

            reportViewTV.setText(_replayMsg);

        }

        cusDetails = (LinearLayout) findViewById(R.id.cusDetails);
        show = (TextView) findViewById(R.id.show);
        hide = (TextView) findViewById(R.id.hide);

      intlize();

    }

    public void intlize(){

        _reportType_TVPD = (TextView) findViewById(R.id.reportType_TVPD);
        _reportType_TVPD.setText(_reportType);
        _input_namer = (TextView) findViewById(R.id.input_namePD);
        _input_namer.setText(_namePd);
        _input_mobiler = (TextView) findViewById(R.id.input_mobilePD);
        _input_mobiler.setText(_phonePd);
        _genderP = (TextView) findViewById(R.id.genderPD);
        _genderP.setText(_genderPd);
        _input_dobr = (TextView) findViewById(R.id.input_dobPD);
        _input_dobr.setText(_dobPd);
        _input_timedobr = (TextView) findViewById(R.id.input_timedobPD);
        _input_timedobr.setText(_tobPd);
        _input_placedobr = (TextView) findViewById(R.id.input_placedobPD);
        _input_placedobr.setText(_pobcityPd);
        _input_statedobr = (TextView) findViewById(R.id.input_statedobPD);
        _input_statedobr.setText(_pobstatePd);
        _input_countryedobr = (TextView) findViewById(R.id.input_countryedobPD);
        _input_countryedobr.setText(_pobcountryPd);
        _input_occupationr = (TextView) findViewById(R.id.input_occupationPD);
        _input_occupationr.setText(_occupationPd);
        _input_anyCommentsr = (TextView) findViewById(R.id.input_anyCommentsPD);
        _input_anyCommentsr.setText(_pcommentPd);
        _marriage_StatusPD = (TextView) findViewById(R.id.marriage_StatusPD);
        _marriage_StatusPD.setText(_martialstatusPd);
        _topicOfConcrenPD = (TextView) findViewById(R.id.topicOfConcrenPD);
        _parnerdetails = (LinearLayout) findViewById(R.id.parnerdetails);
        _topicOfConcrenPD.setText(_topicConcrenPd);


        if(_pnamePd.isEmpty()){

            _parnerdetails.setVisibility(View.GONE);

        }


        _input_pnamer = (TextView) findViewById(R.id.input_pnamePD);
        _input_pnamer.setText(_pnamePd);
        _input_partnerdobr = (TextView) findViewById(R.id.input_partnerdobPD);
        _input_partnerdobr.setText(_pdobPd);
        _input_partnertimedobr = (TextView) findViewById(R.id.input_partnertimedobPD);
        _input_partnertimedobr.setText(_ptobPd);
        _input_parnerplacedobr = (TextView) findViewById(R.id.input_parnerplacedobPD);
        _input_parnerplacedobr.setText(_ppobPd);
        _input_partnerstatedobPD = (TextView) findViewById(R.id.input_partnerstatedobPD);
        _input_partnerstatedobPD.setText(_ppobstatePd);
        _input_partnercountryedobr = (TextView) findViewById(R.id.input_partnercountryedobPD);
        _input_partnercountryedobr.setText(_ppobscountryPd);


        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Please Wait Loading");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);

    }

    public void showdHide(View view) {

        cusDetails.setVisibility(View.VISIBLE);
        show.setVisibility(View.GONE);
        hide.setVisibility(View.VISIBLE);

        Animation hyperspaceJump = AnimationUtils.loadAnimation(this, R.anim.push_left_in);

        cusDetails.startAnimation(hyperspaceJump);


    }

    public void Hide(View view) {

        Animation hyperspaceJump = AnimationUtils.loadAnimation(this, R.anim.push_left_out);

        cusDetails.startAnimation(hyperspaceJump);

        cusDetails.setVisibility(View.GONE);
        show.setVisibility(View.VISIBLE);
        hide.setVisibility(View.GONE);


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
