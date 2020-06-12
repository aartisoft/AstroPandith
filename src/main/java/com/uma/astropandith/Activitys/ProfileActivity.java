package com.uma.astropandith.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uma.astropandith.R;

public class ProfileActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private String _name;
    private String _id;
    private String _mail;
    private String _phone;
    private TextView name_profile;
    private TextView mail_profile;
    private LinearLayout certificate;
    private LinearLayout update_number;
    private LinearLayout tutorial;
    private LinearLayout support;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences("save", Context.MODE_PRIVATE);
        _name = sharedPreferences.getString("name", null); // getting String
        _id = sharedPreferences.getString("id", null); // getting String
        _mail = sharedPreferences.getString("email", null); // getting String
        _phone = sharedPreferences.getString("phone", null); // getting String


        support = (LinearLayout) findViewById(R.id.support);
        tutorial = (LinearLayout) findViewById(R.id.tutorial);
        certificate = (LinearLayout) findViewById(R.id.certificate);
        update_number = (LinearLayout) findViewById(R.id.update_number);
         name_profile = (TextView) findViewById(R.id.name_profile);
         mail_profile = (TextView) findViewById(R.id.mail_profile);
         name_profile.setText(_name);
         mail_profile.setText(_mail);



         certificate.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {


                 Intent intent = new Intent(ProfileActivity.this, EduActivity.class);
                 startActivity(intent);


             }
         });
        support.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {


                 Intent intent = new Intent(ProfileActivity.this, SupportChatActivity.class);
                 startActivity(intent);


             }
         });

        update_number.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {


                 Intent intent = new Intent(ProfileActivity.this, UpdatephoneActivity.class);
                 startActivity(intent);


             }
         });

      tutorial.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {


                 Intent intent = new Intent(ProfileActivity.this, TutorialListActivity.class);
                 startActivity(intent);


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
