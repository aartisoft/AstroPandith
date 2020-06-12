package com.uma.astropandith.Activitys;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.uma.astropandith.Adapters.CompletedReportAdapter;
import com.uma.astropandith.Adapters.PendingReportAdapter;
import com.uma.astropandith.Model.ReportCompletedList;
import com.uma.astropandith.Model.ReportPendingList;
import com.uma.astropandith.R;
import com.uma.astropandith.Retrofit.GlobalVariables;
import com.uma.astropandith.Retrofit.MyCallback;
import com.uma.astropandith.Retrofit.RestCallback;
import com.uma.astropandith.Retrofit.RestService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

public class ReportsActivity extends AppCompatActivity implements RestCallback {

    private RecyclerView recyclerRV;
    private SharedPreferences sharedPreferences;
    private String _name;
    private String _id;
    private String _mail;
    private String _phone;
    private ProgressDialog progressBar;
    private ArrayList<ReportPendingList> _reportPendingList;
    private PendingReportAdapter adapter;
    private RecyclerView recyclerCompletedRV;
    private Button _pendingBT;
    private Button _completedBT;
    private ArrayList<ReportCompletedList> _repoertCompletedLIST;
    private CompletedReportAdapter adapter1;
    private TextView _report_types;
    private String st;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences("save", Context.MODE_PRIVATE);
        _name = sharedPreferences.getString("name", null); // getting String
        _id = sharedPreferences.getString("id", null); // getting String
        _mail = sharedPreferences.getString("email", null); // getting String
        _phone = sharedPreferences.getString("phone", null); // getting String


        recyclerRV = findViewById(R.id.pendingreport_rv);
        recyclerCompletedRV = findViewById(R.id.completedreport_rv);

        _pendingBT = findViewById(R.id.pending_bt);
        _completedBT = findViewById(R.id.complete_bt);
        _report_types = findViewById(R.id.report_types);

        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Loading");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);

        getReportPendingListAPI();

    }

    @Override
    protected void onRestart() {


        if(st!=null){

            if(st.equals("PENDING")){

                getReportPendingListAPI();

            }if(st.equals("COMPLETE")){

                getReportCompletedListAPI();
            }

        } else {

            getReportPendingListAPI();

        }
        super.onRestart();
    }

    private void getReportPendingListAPI() {

        st = "PENDING";

        _report_types.setText("Pending Reports".toUpperCase());

        progressBar.show();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("pid", _id);
        RestService.getInstance(ReportsActivity.this).getPadingReportList(map, new MyCallback<ArrayList<ReportPendingList>>(ReportsActivity.this,
                ReportsActivity.this, GlobalVariables.SERVICE_MODE.REPORT_PENDING_LIST));

    }

 private void getReportCompletedListAPI() {

     st = "COMPLETE";


     _report_types.setText("Completed Reports".toUpperCase());


     progressBar.show();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("pid", _id);
        RestService.getInstance(ReportsActivity.this).getCompletedReportList(map, new MyCallback<ArrayList<ReportCompletedList>>(ReportsActivity.this,
                ReportsActivity.this, GlobalVariables.SERVICE_MODE.REPORT_COMPLETED_LIST));

    }


    @Override
    public void onFailure(Call call, Throwable t, GlobalVariables.SERVICE_MODE mode) {

        switch (mode) {

            case REPORT_PENDING_LIST:

                Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();

                recyclerRV.setAdapter(null);

                recyclerRV.setVisibility(View.VISIBLE);
                recyclerCompletedRV.setVisibility(View.GONE);

                progressBar.dismiss();

                break;

                case REPORT_COMPLETED_LIST:

                Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();

                recyclerCompletedRV.setAdapter(null);

                recyclerRV.setVisibility(View.GONE);
                recyclerCompletedRV.setVisibility(View.VISIBLE);

                progressBar.dismiss();

                break;
        }

    }

    @Override
    public void onSuccess(Response response, GlobalVariables.SERVICE_MODE mode) {

        switch (mode) {

            case REPORT_PENDING_LIST:

                _reportPendingList = (ArrayList<ReportPendingList>) response.body();

                Collections.reverse(_reportPendingList);

                if(_reportPendingList!=null){

                    adapter = new PendingReportAdapter(ReportsActivity.this, _reportPendingList,_id);
                    recyclerRV.setAdapter(adapter);
                    recyclerRV.setLayoutManager(new LinearLayoutManager(this));

                    recyclerRV.setVisibility(View.VISIBLE);
                    recyclerCompletedRV.setVisibility(View.GONE);

//                    recyclerRV.addOnItemTouchListener(new RecyclerTouchListener(ReportsActivity.this, recyclerRV, new RecyclerTouchListener.ClickListener() {
//                        @Override
//                        public void onClick(View view, int position) {
//
//
//                            _reportType = _reportPendingList.get(position).getRtype();
//                            _namePd = _reportPendingList.get(position).getName();
//                            _phonePd = _reportPendingList.get(position).getPhone();
//                            _genderPd = _reportPendingList.get(position).getGender();
//                            _dobPd = _reportPendingList.get(position).getDob();
//                            _tobPd = _reportPendingList.get(position).getTob();
//                            _pobPd = _reportPendingList.get(position).getPdob();
//                            _pobcityPd = _reportPendingList.get(position).getPobcity();
//                            _pobstatePd = _reportPendingList.get(position).getPobsate();
//                            _pobcountryPd = _reportPendingList.get(position).getPobcountry();
//                            _martialstatusPd = _reportPendingList.get(position).getMaritalstatus();
//                            _occupationPd = _reportPendingList.get(position).getOccupation();
//                            _topicConcrenPd = _reportPendingList.get(position).getTopicofconcern();
//
//                            _pnamePd = _reportPendingList.get(position).getPname();
//                            _pdobPd = _reportPendingList.get(position).getPdob();
//                            _ptobPd = _reportPendingList.get(position).getPtob();
//                            _ppobPd = _reportPendingList.get(position).getPpob();
//                            _ppobcityPd = _reportPendingList.get(position).getPpobcity();
//                            _ppobstatePd = _reportPendingList.get(position).getPpobsate();
//                            _ppobscountryPd = _reportPendingList.get(position).getPpobcountry();
//
//                            _pcommentPd = _reportPendingList.get(position).getAnycomments();
//                            _pcommentPd = _reportPendingList.get(position).getAnycomments();
//
//
//                            _rID = _reportPendingList.get(position).getId();
//                            _userID = _reportPendingList.get(position).getUserid();
//                            _pID = _id;
//
//
//                            Intent intent = new Intent(getApplicationContext(), PendingReportDetailsActivity.class);
//
//                            intent.putExtra("_reportType", _reportType);
//                            intent.putExtra("_namePd", _namePd);
//                            intent.putExtra("_phonePd", _phonePd);
//                            intent.putExtra("_genderPd", _genderPd);
//                            intent.putExtra("_dobPd", _dobPd);
//                            intent.putExtra("_tobPd", _tobPd);
//                            intent.putExtra("_pobPd", _pobPd);
//                            intent.putExtra("_pobcityPd", _pobcityPd);
//                            intent.putExtra("_pobstatePd", _pobstatePd);
//                            intent.putExtra("_pobcountryPd", _pobcountryPd);
//                            intent.putExtra("_martialstatusPd", _martialstatusPd);
//                            intent.putExtra("_occupationPd", _occupationPd);
//                            intent.putExtra("_topicConcrenPd", _topicConcrenPd);
//
//
//                            intent.putExtra("_pnamePd", _pnamePd);
//                            intent.putExtra("_pdobPd", _pdobPd);
//                            intent.putExtra("_ptobPd", _ptobPd);
//                            intent.putExtra("_ppobPd", _ppobPd);
//                            intent.putExtra("_ppobcityPd", _ppobcityPd);
//                            intent.putExtra("_ppobstatePd", _ppobstatePd);
//                            intent.putExtra("_ppobscountryPd", _ppobscountryPd);
//
//                            intent.putExtra("_pcommentPd", _pcommentPd);
//
//
//                            intent.putExtra("_rID", _rID);
//                            intent.putExtra("_userID", _userID);
//                            intent.putExtra("_pID", _pID);
//
//
//                            startActivity(intent);
//                            finish();
//
//
//                        }
//
//                        @Override
//                        public void onLongClick(View view, int position) {
//
//                        }
//                    }));


                    progressBar.dismiss();

                } else {

                    recyclerRV.setAdapter(null);
                }

                break;

                case REPORT_COMPLETED_LIST:

                _repoertCompletedLIST = (ArrayList<ReportCompletedList>) response.body();

                    Collections.reverse(_repoertCompletedLIST);

                if(_repoertCompletedLIST!=null){

                    adapter1 = new CompletedReportAdapter(ReportsActivity.this, _repoertCompletedLIST);
                    recyclerCompletedRV.setAdapter(adapter1);
                    recyclerCompletedRV.setLayoutManager(new LinearLayoutManager(this));


                    recyclerRV.setVisibility(View.GONE);
                    recyclerCompletedRV.setVisibility(View.VISIBLE);

                    progressBar.dismiss();

                } else {

                    recyclerCompletedRV.setAdapter(null);
                }

                break;
        }

    }

    public void pendingBT(View view) {

        getReportPendingListAPI();

//        _pendingBT.setBackgroundColor(Color.BLUE);
//        _pendingBT.setTextColor(Color.WHITE);
//
//
//        _completedBT.setBackgroundColor(Color.WHITE);
//        _completedBT.setTextColor(Color.BLACK);




    }

    public void CompletedBT(View view) {

        getReportCompletedListAPI();


//        _pendingBT.setBackgroundColor(Color.WHITE);
//        _pendingBT.setTextColor(Color.BLACK);
//
//        _completedBT.setBackgroundColor(Color.BLUE);
//        _completedBT.setTextColor(Color.WHITE);




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
