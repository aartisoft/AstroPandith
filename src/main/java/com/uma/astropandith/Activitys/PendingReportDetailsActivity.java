package com.uma.astropandith.Activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uma.astropandith.R;

public class PendingReportDetailsActivity extends AppCompatActivity {

    private ProgressDialog progressBar;
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
    private TextView _reportType_TVPD;
    private TextView _genderP;
    private TextView _topicOfConcrenPD;
    private TextView _input_partnerstatedobPD;
    private String _rID;
    private String _userID;
    private String _pID;
    private LinearLayout _parnerdetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peending_report_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle extras = getIntent().getExtras();

        if (extras != null) {

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


            _rID = extras.getString("_rID");
            _userID = extras.getString("_userID");
            _pID = extras.getString("_pID");

        }

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



    public void reply(View view) {

        Intent intent = new Intent(getApplicationContext(), ReplyActivity.class);


        intent.putExtra("_reportType", _reportType);
        intent.putExtra("_namePd", _namePd);
        intent.putExtra("_phonePd", _phonePd);
        intent.putExtra("_genderPd", _genderPd);
        intent.putExtra("_dobPd", _dobPd);
        intent.putExtra("_tobPd", _tobPd);
        intent.putExtra("_pobPd", _pobPd);
        intent.putExtra("_pobcityPd", _pobcityPd);
        intent.putExtra("_pobstatePd", _pobstatePd);
        intent.putExtra("_pobcountryPd", _pobcountryPd);
        intent.putExtra("_martialstatusPd", _martialstatusPd);
        intent.putExtra("_occupationPd", _occupationPd);
        intent.putExtra("_topicConcrenPd", _topicConcrenPd);


        intent.putExtra("_pnamePd", _pnamePd);
        intent.putExtra("_pdobPd", _pdobPd);
        intent.putExtra("_ptobPd", _ptobPd);
        intent.putExtra("_ppobPd", _ppobPd);
        intent.putExtra("_ppobcityPd", _ppobcityPd);
        intent.putExtra("_ppobstatePd", _ppobstatePd);
        intent.putExtra("_ppobscountryPd", _ppobscountryPd);

        intent.putExtra("_pcommentPd", _pcommentPd);

        intent.putExtra("_rID", _rID);
        intent.putExtra("_userID", _userID);
        intent.putExtra("_pID", _pID);

        startActivity(intent);

        finish();


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
