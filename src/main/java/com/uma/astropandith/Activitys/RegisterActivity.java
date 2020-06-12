package com.uma.astropandith.Activitys;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.uma.astropandith.Model.RegistrationModel;
import com.uma.astropandith.R;

import org.json.JSONObject;

import static com.uma.astropandith.Retrofit.GlobalVariables.BASE_URL;

public class RegisterActivity extends AppCompatActivity {

    private EditText _nameText;
    private EditText _addressText;
    private EditText _emailText;
    private EditText _mobileText;
    private EditText _passwordText;
    private EditText _reEnterPasswordText;
    private RequestQueue requestQueue;
    private ProgressDialog progressBar;
    private JsonObjectRequest mJsonRequest;
    private Integer statusId;
    private String name;
    private String address;
    private String email;
    private String mobile;
    private String password;
    private String reEnterPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        intlize();

        requestQueue = Volley.newRequestQueue(this);


    }

    public void Create(View view) {


        if (!isNetworkAvailable()) {

            Toast.makeText(RegisterActivity.this, "No Network", Toast.LENGTH_SHORT).show();

        } else if (validate()) {

            CallRegisterAPI();
            progressBar.show();

        } else {

            Toast.makeText(RegisterActivity.this, "Fill Form", Toast.LENGTH_SHORT).show();
        }

    }


    public void intlize() {

        _nameText = (EditText) findViewById(R.id.input_name);
        _addressText = (EditText) findViewById(R.id.input_address);
        _emailText = (EditText) findViewById(R.id.input_email);
        _mobileText = (EditText) findViewById(R.id.input_mobile);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _reEnterPasswordText = (EditText) findViewById(R.id.input_reEnterPassword);

        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Please Wait Loading");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);

    }


    public boolean validate() {
        boolean valid = true;

        name = _nameText.getText().toString();
        address = _addressText.getText().toString();
        email = _emailText.getText().toString();
        mobile = _mobileText.getText().toString();
        password = _passwordText.getText().toString();
        reEnterPassword = _reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (address.isEmpty()) {
            _addressText.setError("Enter Experience");
            valid = false;
        } else {
            _addressText.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("Enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (mobile.isEmpty() || mobile.length() != 10) {
            _mobileText.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            _mobileText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("Between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }

    public boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }


    public void CallRegisterAPI() {


        mJsonRequest = new JsonObjectRequest(BASE_URL+"pandit/Registration.php?name=" + name + "&email=" + email + "&phone=" + mobile + "&password=" + reEnterPassword + "&experiance=" + address + "", null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();

                RegistrationModel registration = gson.fromJson(response.toString(), RegistrationModel.class);

                statusId = registration.getRegStatus();


                if (statusId.equals("0")) {

                    progressBar.dismiss();

                    AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
                    dialog.setMessage("Account Already Exist");
                    dialog.setTitle("Alert");
                    dialog.setPositiveButton("Okay",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                }
                            });

                    AlertDialog alertDialog = dialog.create();
                    alertDialog.show();

                } else {

                    progressBar.dismiss();

                    AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
                    dialog.setMessage("Account Created Successfully");
                    dialog.setTitle("Success");
                    dialog.setPositiveButton("Okay",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                                    finish();

                                }
                            });

                    AlertDialog alertDialog = dialog.create();
                    alertDialog.setCancelable(false);
                    alertDialog.show();

                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(mJsonRequest);

    }
}
