package com.uma.astropandith.Activitys;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.uma.astropandith.Adapters.TutorialAdapter;
import com.uma.astropandith.Model.TutorialModel;
import com.uma.astropandith.R;
import com.uma.astropandith.Retrofit.GlobalVariables;
import com.uma.astropandith.Retrofit.MyCallback;
import com.uma.astropandith.Retrofit.RestCallback;
import com.uma.astropandith.Retrofit.RestService;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

public class TutorialStepsActivity extends AppCompatActivity implements RestCallback {

    private SharedPreferences sharedPreferences;
    private String _name;
    private String _id;
    private String _mail;
    private String _phone;
    private RecyclerView recyclerRV;
    private ProgressDialog progressBar;
    private ArrayList<TutorialModel> _tutorialSteps;
    private TutorialAdapter adapter;
    private String cat_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_steps);


        sharedPreferences = getSharedPreferences("save", Context.MODE_PRIVATE);
        _name = sharedPreferences.getString("name", null); // getting String
        _id = sharedPreferences.getString("id", null); // getting String
        _mail = sharedPreferences.getString("email", null); // getting String
        _phone = sharedPreferences.getString("phone", null); // getting String

        recyclerRV = findViewById(R.id.tutorialTitleList);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            cat_id = extras.getString("cat_id");


        }

        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Loading");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);

        getTutorialAPI();
    }

    private void getTutorialAPI() {

        progressBar.show();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("id", cat_id);
        RestService.getInstance(TutorialStepsActivity.this).getTutorialSteps(map, new MyCallback<ArrayList<TutorialModel>>(TutorialStepsActivity.this,
                TutorialStepsActivity.this, GlobalVariables.SERVICE_MODE.TUTORIAL_STEPS));

    }
    @Override
    public void onFailure(Call call, Throwable t, GlobalVariables.SERVICE_MODE mode) {

        switch (mode) {

            case TUTORIAL_STEPS:

                Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();

                recyclerRV.setAdapter(null);

                progressBar.dismiss();

                break;


        }

    }

    @Override
    public void onSuccess(Response response, GlobalVariables.SERVICE_MODE mode) {


        switch (mode) {

            case TUTORIAL_STEPS:

                _tutorialSteps = (ArrayList<TutorialModel>) response.body();


                if (_tutorialSteps != null) {

                    adapter = new TutorialAdapter( _tutorialSteps);
                    recyclerRV.setAdapter(adapter);
                    recyclerRV.setLayoutManager(new LinearLayoutManager(this));

                    progressBar.dismiss();

                } else {

                    recyclerRV.setAdapter(null);
                }

                break;

        }
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
