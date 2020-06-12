package com.uma.astropandith.Activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.uma.astropandith.R;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private String _name;
    private String _id;
    private String _mail;
    private String _phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPreferences = getSharedPreferences("save", Context.MODE_PRIVATE);
        _name = sharedPreferences.getString("name", null); // getting String
        _id = sharedPreferences.getString("id", null); // getting String
        _mail = sharedPreferences.getString("email", null); // getting String
        _phone = sharedPreferences.getString("phone", null); // getting String


        if (isNetworkConnected()) {


            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {

                            if(_id==null){

                               // Toast.makeText(SplashActivity.this, _id, Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                                finish();

                            }
                            else {

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("id", _id);
                                intent.putExtra("name", _name);
                                intent.putExtra("phone", _phone);
                                intent.putExtra("email", _mail);
                                startActivity(intent);
                                finish();
                            }


                        }
                    },
                    1000
            );

        } else {

            Toast.makeText(this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();



        }





    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
