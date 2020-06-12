package com.uma.astropandith.Activitys;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.uma.astropandith.Model.AllCertificateDetails;
import com.uma.astropandith.R;
import com.uma.astropandith.Retrofit.GlobalVariables;
import com.uma.astropandith.Retrofit.MyCallback;
import com.uma.astropandith.Retrofit.RestCallback;
import com.uma.astropandith.Retrofit.RestService;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import static com.uma.astropandith.Retrofit.GlobalVariables.BASE_URL;

public class EduActivity extends AppCompatActivity implements RestCallback {

    private ImageView _certificate_first;
    private ImageView _certificate_first2;
    private ImageView _certificate_first3;
    private EditText _certificatename;
    private EditText _certificatename2;
    private EditText _certificatename3;
    private SharedPreferences sharedPreferences;
    private String _name;
    private String _id;
    private String _mail;
    private String _phone;
    private Uri filePath;
    private String certificateImage;
    private Bitmap resizeBitmap;
    private Uri filePath2;
    private ProgressDialog progressBar;
    private String certificateName;
    private Button _UploadBT;
    private Button _UploadBT2;
    private String certificateName2;
    private View _UploadBT3;
    private String certificateName3;
    private Uri filePath3;
    private RequestQueue requestQueue;
    private JsonObjectRequest mJsonRequest;
    private AllCertificateDetails _allcertificateDetails;
    private String _CrtIMAGE1;
    private String _CrtName1;
    private String _CrtIMAGE2;
    private String _CrtName2;
    private String _CrtIMAGE3;
    private String _CrtName3;
    private String _Crt1Sattus;
    private String _Crt2Sattus;
    private String _Crt3Sattus;
    private ImageView _verify_certificate1;
    private ImageView _verify_certificate2;
    private ImageView _verify_certificate3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edu);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences("save", Context.MODE_PRIVATE);
        _name = sharedPreferences.getString("name", null); // getting String
        _id = sharedPreferences.getString("id", null); // getting String
        _mail = sharedPreferences.getString("email", null); // getting String
        _phone = sharedPreferences.getString("phone", null); // getting String

        _certificate_first = findViewById(R.id.certificate_first);
        _certificate_first2 = findViewById(R.id.certificate_first2);
        _certificate_first3 = findViewById(R.id.certificate_first3);
        _UploadBT = findViewById(R.id.Upload);
        _UploadBT2 = findViewById(R.id.Upload2);
        _UploadBT3 = findViewById(R.id.Upload3);

        _verify_certificate1 = (ImageView) findViewById(R.id.verify_certificate1);
        _verify_certificate2 = (ImageView) findViewById(R.id.verify_certificate2);
        _verify_certificate3 = (ImageView) findViewById(R.id.verify_certificate3);



        requestQueue = Volley.newRequestQueue(this);


        _certificatename =  findViewById(R.id.certificatename);
        _certificatename2 =  findViewById(R.id.certificatename2);
        _certificatename3 =  findViewById(R.id.certificatename3);

        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Loading");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);

        CallCertificateAPI();


        _UploadBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                certificateName = _certificatename.getText().toString();

                if (certificateName.isEmpty()) {
                    _certificatename.setError("enter a name");
                } else if(resizeBitmap!=null){

                    getUploadAPI();

                }else {

                    Toast.makeText(EduActivity.this, "Select Certificate", Toast.LENGTH_SHORT).show();
                }
            }
        });

        _UploadBT2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                certificateName2 = _certificatename2.getText().toString();

                if (certificateName2.isEmpty()) {
                    _certificatename2.setError("enter a name");
                } else if(resizeBitmap!=null){

                    getUploadAPI2();

                }else {

                    Toast.makeText(EduActivity.this, "Select Certificate", Toast.LENGTH_SHORT).show();

                }
            }
        });
        _UploadBT3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                certificateName3 = _certificatename3.getText().toString();

                if (certificateName3.isEmpty()) {
                    _certificatename3.setError("enter a name");
                } else if(resizeBitmap!=null){

                    getUploadAPI3();

                }else {

                    Toast.makeText(EduActivity.this, "Select Certificate", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void getUploadAPI() {

        progressBar.show();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("pId", _id);
        map.put("cTitle", certificateName);
        map.put("image", certificateImage);
        RestService.getInstance(EduActivity.this).getUploadCertficate(map, new MyCallback<ResponseBody>(EduActivity.this,
                EduActivity.this, GlobalVariables.SERVICE_MODE.UPLOAD_CERTIFICATE));

    }

    private void getUploadAPI2() {

        progressBar.show();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("pId", _id);
        map.put("cTitle", certificateName2);
        map.put("image", certificateImage);
        RestService.getInstance(EduActivity.this).getUploadCertficate2(map, new MyCallback<ResponseBody>(EduActivity.this,
                EduActivity.this, GlobalVariables.SERVICE_MODE.UPLOAD_CERTIFICATE2));

    }
    private void getUploadAPI3() {

        progressBar.show();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("pId", _id);
        map.put("cTitle", certificateName3);
        map.put("image", certificateImage);
        RestService.getInstance(EduActivity.this).getUploadCertficate3(map, new MyCallback<ResponseBody>(EduActivity.this,
                EduActivity.this, GlobalVariables.SERVICE_MODE.UPLOAD_CERTIFICATE3));

    }

    public void selectCertificate(View view) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 7);

    }

    public void selectCertificate1(View view) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 8);

    }

    public void selectCertificate3(View view) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 9);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 7 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);


                resizeBitmap = resize(bitmap, bitmap.getWidth() / 2, bitmap.getHeight() / 2);


                _certificate_first.setImageBitmap(resizeBitmap);

                    convertImageToBase64(resizeBitmap);

                //imageName = bitmap.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else

        if (requestCode == 8 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath2 = data.getData();

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath2);


                resizeBitmap = resize(bitmap, bitmap.getWidth() / 2, bitmap.getHeight() / 2);


                _certificate_first2.setImageBitmap(resizeBitmap);

                convertImageToBase64(resizeBitmap);

                //imageName = bitmap.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else   if (requestCode == 9 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath3 = data.getData();

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath3);


                resizeBitmap = resize(bitmap, bitmap.getWidth() / 2, bitmap.getHeight() / 2);


                _certificate_first3.setImageBitmap(resizeBitmap);

                convertImageToBase64(resizeBitmap);

                //imageName = bitmap.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void convertImageToBase64(Bitmap bm) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 60, baos); //bm is the bitmap object
        byte[] byteArrayImage = baos.toByteArray();

        certificateImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);

    }

    private static Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > ratioBitmap) {
                finalWidth = (int) ((float) maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float) maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }




    @Override
    public void onFailure(Call call, Throwable t, GlobalVariables.SERVICE_MODE mode) {

        switch (mode) {


            case UPLOAD_CERTIFICATE:

                Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();

                progressBar.dismiss();

                break;

            case UPLOAD_CERTIFICATE2:

                Toast.makeText(this, "fail ", Toast.LENGTH_SHORT).show();
                progressBar.dismiss();

                break;
                case UPLOAD_CERTIFICATE3:

                Toast.makeText(this, " fail ", Toast.LENGTH_SHORT).show();
                progressBar.dismiss();

                break;

        }
    }

    @Override
    public void onSuccess(Response response, GlobalVariables.SERVICE_MODE mode) {

        switch (mode) {

            case UPLOAD_CERTIFICATE:

                Toast.makeText(this, "Upload Completed", Toast.LENGTH_SHORT).show();

                _UploadBT.setEnabled(false);


                progressBar.dismiss();

                break;
            case UPLOAD_CERTIFICATE2:

                Toast.makeText(this, "Upload Completed", Toast.LENGTH_SHORT).show();

                _UploadBT2.setEnabled(false);

                progressBar.dismiss();

                break;

                case UPLOAD_CERTIFICATE3:

                Toast.makeText(this, "Upload Completed", Toast.LENGTH_SHORT).show();

                _UploadBT3.setEnabled(false);

                progressBar.dismiss();

                break;

        }

    }

    public void CallCertificateAPI(){

        mJsonRequest = new JsonObjectRequest(BASE_URL+"pandit/allCertificatesDetails.php?pId="+_id+"", null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();

                _allcertificateDetails = gson.fromJson(response.toString(), AllCertificateDetails.class);

                _CrtIMAGE1 = _allcertificateDetails.getCrtf1();
                _CrtName1 = _allcertificateDetails.getCrtf1t();
                _Crt1Sattus = _allcertificateDetails.getCrtf1vv();

                _CrtIMAGE2 = _allcertificateDetails.getCrtf2();
                _CrtName2 = _allcertificateDetails.getCrtf2t();
                _Crt2Sattus = _allcertificateDetails.getCrtf2vv();


                _CrtIMAGE3 = _allcertificateDetails.getCrtf3();
                _CrtName3 = _allcertificateDetails.getCrtf3t();
                _Crt3Sattus = _allcertificateDetails.getCrtf3vv();



                if(_Crt1Sattus.equals("0")){

                    Toast.makeText(EduActivity.this, "Upload certificate", Toast.LENGTH_SHORT).show();


                }else if(_Crt1Sattus.equals("1")){

                    Toast.makeText(EduActivity.this, "certificates uploaded", Toast.LENGTH_SHORT).show();

                    if(_CrtIMAGE1!=null)
                        Picasso.get().load(BASE_URL+"pandit/"+ _CrtIMAGE1).into(_certificate_first);
                    else _certificate_first.setImageResource(R.drawable.ic_image_black_24dp);

                    _UploadBT.setVisibility(View.GONE);
                    _certificatename.setText(_CrtName1);
                    _certificatename.setKeyListener(null);
                    _certificate_first.setEnabled(false);


                } else if(_Crt1Sattus.equals("4")){

                    Toast.makeText(EduActivity.this, " Verification failed ", Toast.LENGTH_SHORT).show();


                    if(_CrtIMAGE1!=null)
                        Picasso.get().load(BASE_URL+"pandit/"+  _CrtIMAGE1).into(_certificate_first);
                    else _certificate_first.setImageResource(R.drawable.ic_image_black_24dp);


                    _verify_certificate1.setImageResource(R.drawable.red_cross_mark);
                    _verify_certificate1.setVisibility(View.VISIBLE);

                    _UploadBT.setVisibility(View.VISIBLE);
                    _certificatename.setText(_CrtName1);

                }  else if(_Crt1Sattus.equals("5")){

                    Toast.makeText(EduActivity.this, "Verified", Toast.LENGTH_SHORT).show();

                    if(_CrtIMAGE1!=null)
                        Picasso.get().load(BASE_URL+"pandit/"+  _CrtIMAGE1).into(_certificate_first);
                    else _certificate_first.setImageResource(R.drawable.ic_image_black_24dp);

                    _verify_certificate1.setImageResource(R.drawable.green_rightmark);
                    _verify_certificate1.setVisibility(View.VISIBLE);

                    _UploadBT.setVisibility(View.GONE);
                    _certificatename.setText(_CrtName1);
                    _certificatename.setKeyListener(null);
                    _certificate_first.setEnabled(false);
                }



                if(_Crt2Sattus.equals("0")){

                    Toast.makeText(EduActivity.this, "Upload certificate2", Toast.LENGTH_SHORT).show();


                }else if(_Crt2Sattus.equals("1")){

                    Toast.makeText(EduActivity.this, "certificates uploaded2", Toast.LENGTH_SHORT).show();

                    if(_CrtIMAGE2!=null)
                        Picasso.get().load(BASE_URL+"pandit/"+ _CrtIMAGE2).into(_certificate_first2);
                    else _certificate_first2.setImageResource(R.drawable.ic_image_black_24dp);

                    _UploadBT2.setVisibility(View.GONE);
                    _certificatename2.setText(_CrtName2);
                    _certificatename2.setKeyListener(null);
                    _certificate_first2.setEnabled(false);


                } else if(_Crt2Sattus.equals("4")){

                    Toast.makeText(EduActivity.this, " Verification failed 2", Toast.LENGTH_SHORT).show();


                    if(_CrtIMAGE2!=null)
                        Picasso.get().load(BASE_URL+"pandit/"+  _CrtIMAGE2).into(_certificate_first2);
                    else _certificate_first2.setImageResource(R.drawable.ic_image_black_24dp);


                    _verify_certificate2.setImageResource(R.drawable.red_cross_mark);
                    _verify_certificate2.setVisibility(View.VISIBLE);

                    _UploadBT2.setVisibility(View.VISIBLE);
                    _certificatename2.setText(_CrtName2);

                }  else if(_Crt2Sattus.equals("5")){

                    Toast.makeText(EduActivity.this, "Verified 2", Toast.LENGTH_SHORT).show();

                    if(_CrtIMAGE2!=null)
                        Picasso.get().load(BASE_URL+"pandit/"+ _CrtIMAGE2).into(_certificate_first2);
                    else _certificate_first2.setImageResource(R.drawable.ic_image_black_24dp);

                    _verify_certificate2.setImageResource(R.drawable.green_rightmark);
                    _verify_certificate2.setVisibility(View.VISIBLE);

                    _UploadBT2.setVisibility(View.GONE);
                    _certificatename2.setText(_CrtName2);
                    _certificatename2.setKeyListener(null);
                    _certificate_first2.setEnabled(false);
                }





                if(_Crt3Sattus.equals("0")){

                    Toast.makeText(EduActivity.this, "Upload certificate3", Toast.LENGTH_SHORT).show();


                }else if(_Crt3Sattus.equals("1")){

                    Toast.makeText(EduActivity.this, "certificates uploaded3", Toast.LENGTH_SHORT).show();

                    if(_CrtIMAGE3!=null)
                        Picasso.get().load(BASE_URL+"pandit/"+ _CrtIMAGE3).into(_certificate_first3);
                    else _certificate_first3.setImageResource(R.drawable.ic_image_black_24dp);

                    _UploadBT3.setVisibility(View.GONE);
                    _certificatename3.setText(_CrtName3);
                    _certificatename3.setKeyListener(null);
                    _certificate_first3.setEnabled(false);


                } else if(_Crt3Sattus.equals("4")){

                    Toast.makeText(EduActivity.this, " Verification failed 3", Toast.LENGTH_SHORT).show();


                    if(_CrtIMAGE3!=null)
                        Picasso.get().load(BASE_URL+"pandit/"+ _CrtIMAGE3).into(_certificate_first3);
                    else _certificate_first3.setImageResource(R.drawable.ic_image_black_24dp);


                    _verify_certificate3.setImageResource(R.drawable.red_cross_mark);
                    _verify_certificate3.setVisibility(View.VISIBLE);

                    _UploadBT3.setVisibility(View.VISIBLE);
                    _certificatename3.setText(_CrtName3);

                }  else if(_Crt3Sattus.equals("5")){

                    Toast.makeText(EduActivity.this, "Verified 3", Toast.LENGTH_SHORT).show();

                    if(_CrtIMAGE3!=null)
                        Picasso.get().load(BASE_URL+"pandit/"+  _CrtIMAGE3).into(_certificate_first3);
                    else _certificate_first3.setImageResource(R.drawable.ic_image_black_24dp);

                    _verify_certificate3.setImageResource(R.drawable.green_rightmark);
                    _verify_certificate3.setVisibility(View.VISIBLE);

                    _UploadBT3.setVisibility(View.GONE);
                    _certificatename3.setText(_CrtName3);
                    _certificatename3.setKeyListener(null);
                    _certificate_first3.setEnabled(false);
                }




            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EduActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
