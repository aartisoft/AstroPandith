package com.uma.astropandith.Activitys;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.uma.astropandith.Model.LoginModel;
import com.uma.astropandith.Model.OtpModel;
import com.uma.astropandith.R;
import com.uma.astropandith.Retrofit.GlobalVariables;

import org.json.JSONObject;

import static com.uma.astropandith.Retrofit.GlobalVariables.BASE_URL;

public class LoginActivity extends AppCompatActivity {

    private EditText _passwordText;
    private Button _btn_Login;
    private RequestQueue requestQueue;
    private JsonObjectRequest mJsonRequest;
    private LoginModel _loginData;
    private Integer _status;
    private ProgressDialog progressBar;
    private SharedPreferences sharedPreferences;
    private String password;
    private EditText _input_lagnumber;
    private String mobile;
    private Button _btn_register;
    private EditText _read_otp;
    private EditText _enter_otp;
    private Button _resend_otp;
    private LinearLayout login_ui;
    private LinearLayout otp_ui;
    private String mOTP;
    private String id;
    private String name;
    private String email;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        requestQueue = Volley.newRequestQueue(this);

        _input_lagnumber = (EditText) findViewById(R.id.input_lagnumber);
        _passwordText = (EditText) findViewById(R.id.input_logpassword);
        _btn_Login = (Button) findViewById(R.id.btn_loglogin);

        _read_otp = (EditText) findViewById(R.id.read_otp);
        _enter_otp = (EditText) findViewById(R.id.enter_otp);


        _resend_otp = (Button) findViewById(R.id.resend_otp);

        login_ui = (LinearLayout) findViewById(R.id.login_ui);
        otp_ui = (LinearLayout) findViewById(R.id.otp_ui);


        _btn_register = (Button) findViewById(R.id.bt_Register);
        _btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this,WebViewActivity.class);
                intent.putExtra("url","https://meraastro.com/pandit-registrationphone.php");
                startActivity(intent);
                finish();

            }
        });



        _btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!isNetworkAvailable()) {

                    Toast.makeText(LoginActivity.this, "No Network", Toast.LENGTH_SHORT).show();

                } else if (validate()) {

                    CallLoginAPI();

                    progressBar.show();

                } else {

                    Toast.makeText(LoginActivity.this, "Cant be empty", Toast.LENGTH_SHORT).show();
                }


            }
        });

        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Please Wait");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);


        _resend_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // CallOTP(phone);

            }
        });

    }


    public boolean validate() {
        boolean valid = true;

         mobile = _input_lagnumber.getText().toString();
         password = _passwordText.getText().toString();

        if (mobile.isEmpty()) {
            _input_lagnumber.setError("enter a valid number");
            valid = false;
        } else {
            _input_lagnumber.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }

    public void CallLoginAPI(){

        mJsonRequest = new JsonObjectRequest(BASE_URL+"pandit/login.php?phone=91"+mobile+"&password="+password+"", null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();

                _loginData = gson.fromJson(response.toString(), LoginModel.class);

                _status = _loginData.getLoginStatus();


                if(_status == 0) {

                    progressBar.dismiss();

                    AlertDialog.Builder dialog=new AlertDialog.Builder(LoginActivity.this);
                    dialog.setMessage("Wrong Details");
                    dialog.setTitle("Alert");
                    dialog.setPositiveButton("Okay",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                }
                            });

                    AlertDialog alertDialog=dialog.create();
                    alertDialog.show();

                    progressBar.dismiss();

                } else {

                    progressBar.dismiss();

                    id = _loginData.getId();
                     name = _loginData.getName();
                     email = _loginData.getEmail();
                     phone = _loginData.getPhone();

                   //  CallOTP(phone);

                    sharedPreferences = getSharedPreferences("save",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("id",id);
                    editor.putString("name",name);
                    editor.putString("phone",phone);
                    editor.putString("email",email);

                    GlobalVariables.PID = id;

                    editor.commit();


                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("name", name);
                    intent.putExtra("phone", phone);
                    intent.putExtra("email", email);

                    startActivity(intent);
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    finish();


                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(mJsonRequest);

    }


    public void CallOTP(String phone){

        login_ui.setVisibility(View.GONE);
        otp_ui.setVisibility(View.VISIBLE);

        mJsonRequest = new JsonObjectRequest(BASE_URL+"pandit/panditLoginOTP.php?phone="+phone+"", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                OtpModel otpModel = gson.fromJson(response.toString(), OtpModel.class);


                Integer Status = otpModel.getStatus();
                Integer Otp = otpModel.getOtp();
                mOTP = String.valueOf(Otp);


                if(Status==null){


                    Toast.makeText(LoginActivity.this, "Wrong Details", Toast.LENGTH_SHORT).show();


                }else

                if(Status==0) {

                    Toast.makeText(LoginActivity.this, "Invalid Input", Toast.LENGTH_LONG).show();

                }else if(Status==2) {

                    Toast.makeText(LoginActivity.this, "Server Problem", Toast.LENGTH_LONG).show();

                }else {

                    _read_otp.setText(mOTP);
                    progressBar.dismiss();

                    getOtp();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        requestQueue.add(mJsonRequest);

    }


    public void getOtp() {



        _enter_otp.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                String ReadOtp = _read_otp.getText().toString();
                String EnterOtp = _enter_otp.getText().toString();

                if (ReadOtp.equals(EnterOtp)) {


                    sharedPreferences = getSharedPreferences("save",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("id",id);
                    editor.putString("name",name);
                    editor.putString("phone",phone);
                    editor.putString("email",email);
                    editor.commit();


                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("name", name);
                    intent.putExtra("phone", phone);
                    intent.putExtra("email", email);

                    startActivity(intent);
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    finish();


                } else {

                    // Toast.makeText(Register.this, "INCORRECT OTP", Toast.LENGTH_SHORT).show();

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

    }
}
