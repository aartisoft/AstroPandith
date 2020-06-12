package com.uma.astropandith.Activitys;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.uma.astropandith.Model.MobileUpdateModel;
import com.uma.astropandith.R;

import org.json.JSONObject;

import static com.uma.astropandith.Retrofit.GlobalVariables.BASE_URL;

public class UpdatephoneActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private String _name;
    private String _id;
    private String _mail;
    private String _phone;
    private Button _number_update;
    private EditText _primary_number;
    private EditText _new_number;
    private RequestQueue requestQueue;
    private ProgressDialog progressBar;
    private JsonObjectRequest mJsonRequest;
    private Integer statusId;
    private String _number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatephone);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        sharedPreferences = getSharedPreferences("save", Context.MODE_PRIVATE);
        _name = sharedPreferences.getString("name", null); // getting String
        _id = sharedPreferences.getString("id", null); // getting String
        _mail = sharedPreferences.getString("email", null); // getting String
        _phone = sharedPreferences.getString("phone", null); // getting String


        requestQueue = Volley.newRequestQueue(this);



        _number_update = (Button) findViewById(R.id.number_update);
        _primary_number = (EditText) findViewById(R.id.primary_number);
        _new_number = (EditText) findViewById(R.id.new_number);

        _primary_number.setText(_phone);
        _primary_number.setEnabled(false);


        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Please Wait Loading");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);

        _number_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              _number =  _new_number.getText().toString();

                if (_number.isEmpty() || _number.length()!=10) {

                    _new_number.setError("Enter Valid Mobile Number");

                } else {

                    progressBar.show();

                    _new_number.setError(null);

                    CallUpdateNumberAPI();

                }


            }
        });


    }


    public void CallUpdateNumberAPI(){


        mJsonRequest = new JsonObjectRequest(BASE_URL+"pandit/updatePanditPhone.php?pId="+_id+"&phone="+_phone+"&newPhone="+_number+"", null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();

                MobileUpdateModel mobileUpdateModel = gson.fromJson(response.toString(), MobileUpdateModel.class);

                statusId = mobileUpdateModel.getUpdateStatus();


                if(statusId.equals(1)) {

                    Toast.makeText(UpdatephoneActivity.this, "Number update Successful", Toast.LENGTH_SHORT).show();

                    progressBar.dismiss();


                    sharedPreferences = getSharedPreferences("save",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("phone",_number);

                    editor.commit();


                    finish();


                } else {

                    Toast.makeText(UpdatephoneActivity.this, "Invalid Number ", Toast.LENGTH_SHORT).show();



                    progressBar.dismiss();

                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UpdatephoneActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.dismiss();
            }
        });

        requestQueue.add(mJsonRequest);

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
