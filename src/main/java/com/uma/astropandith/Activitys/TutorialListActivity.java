package com.uma.astropandith.Activitys;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.uma.astropandith.Adapters.TutorialListAdapter;
import com.uma.astropandith.Model.TutorialListModel;
import com.uma.astropandith.R;
import com.uma.astropandith.RecyclerTouchListener;
import com.uma.astropandith.Retrofit.GlobalVariables;
import com.uma.astropandith.Retrofit.MyCallback;
import com.uma.astropandith.Retrofit.RestCallback;
import com.uma.astropandith.Retrofit.RestService;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

public class TutorialListActivity extends AppCompatActivity implements RestCallback {

    private SharedPreferences sharedPreferences;
    private String _name;
    private String _id;
    private String _mail;
    private String _phone;
    private RecyclerView recyclerRV;
    private ProgressDialog progressBar;
    private ArrayList<TutorialListModel> _tutorialListTitle;
    private TutorialListAdapter adapter;
    private String cat_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_list);

        sharedPreferences = getSharedPreferences("save", Context.MODE_PRIVATE);
        _name = sharedPreferences.getString("name", null); // getting String
        _id = sharedPreferences.getString("id", null); // getting String
        _mail = sharedPreferences.getString("email", null); // getting String
        _phone = sharedPreferences.getString("phone", null); // getting String

        recyclerRV = findViewById(R.id.tutorialTitleList);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Loading");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);

        getTutorialAPI();

    }

    private void getTutorialAPI() {

        progressBar.show();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("pId", _id);
        RestService.getInstance(TutorialListActivity.this).getTutorialList(map, new MyCallback<ArrayList<TutorialListModel>>(TutorialListActivity.this,
                TutorialListActivity.this, GlobalVariables.SERVICE_MODE.TUTORIAL_LIST));

    }
    @Override
    public void onFailure(Call call, Throwable t, GlobalVariables.SERVICE_MODE mode) {

        switch (mode) {

            case TUTORIAL_LIST:

                Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();

                recyclerRV.setAdapter(null);

                progressBar.dismiss();

                break;


        }

    }

    @Override
    public void onSuccess(Response response, GlobalVariables.SERVICE_MODE mode) {


        switch (mode) {

            case TUTORIAL_LIST:

                _tutorialListTitle = (ArrayList<TutorialListModel>) response.body();


                if (_tutorialListTitle != null) {

                    adapter = new TutorialListAdapter( _tutorialListTitle);
                    recyclerRV.setAdapter(adapter);
                    recyclerRV.setLayoutManager(new LinearLayoutManager(this));

                    progressBar.dismiss();

                    recyclerRV.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerRV, new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {

                            cat_id = _tutorialListTitle.get(position).getId();


                            Intent intent = new Intent(TutorialListActivity.this,TutorialStepsActivity.class);
                            intent.putExtra("cat_id",cat_id);
                            startActivity(intent);


                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    }));

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
