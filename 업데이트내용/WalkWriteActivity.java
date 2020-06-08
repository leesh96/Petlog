package com.example.test02;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class WalkWriteActivity extends AppCompatActivity {
    static final int getimagesetting=1001;//for request intent

    private static String IP_ADDRESS = "128.199.106.86";
    private static String TAG = "test02";
    private EditText mEditTextTitle;
    private EditText mEditTextContent;
    //20.05.11 추가
    private ImageView imageViewWalkimage;
    private Bitmap bitmapWalkimage;
    private String image;

    //ImageView image;
    //ImageButton get, send;
    //String temp="";
    //static ProgressDialog pd;
    private static final int REQUEST_CAMERA = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_write);
        mEditTextTitle = (EditText)findViewById(R.id.editText_main_title);
        mEditTextContent = (EditText)findViewById(R.id.editText_main_content);
        //image=(String)findViewById(R.id.btn_walk_image);

        ImageButton buttonBack =(ImageButton)findViewById(R.id.btn_back);
        buttonBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){ //쓰기화면에서 뒤로가기버튼클릭시 게시판메인으로이동
                Intent intent=new Intent(getApplicationContext(), WalkActivity.class);
                startActivity(intent);
            }
        });

        ImageButton ButtonGps =(ImageButton)findViewById(R.id.btn_gps);
        ButtonGps.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(getApplicationContext(), MapViewActivity.class);
                startActivity(intent);
            }
        });


        final String nickname= PreferenceManager.getString(WalkWriteActivity.this,"userNick");

        Button buttonInsert = (Button)findViewById(R.id.board_insert);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = mEditTextTitle.getText().toString();
                String content = mEditTextContent.getText().toString();
                //String walkimage = image.getBytes().toString();

                InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS + "/walkInsert.php", title,content,nickname);


                mEditTextTitle.setText("");
                mEditTextContent.setText("");
                Intent intent=new Intent(getApplicationContext(), WalkActivity.class);
                startActivity(intent);

            }
        });
        ImageButton buttonImage=(ImageButton)findViewById(R.id.btn_walk_image);
        buttonImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent load_image = new Intent();
                load_image.setType("image/*");
                load_image.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(load_image, 1);
                permission_init();
                      //  addImage();
                }
        });
    }
    ////



    void permission_init(){
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {	//권한 거절
            // Request missing location permission.
            // Check Permissions Now

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.CAMERA)) {
                // 이전에 권한거절
                // Toast.makeText(this,getString(R.string.limit),Toast.LENGTH_SHORT).show();

            } else {
                ActivityCompat.requestPermissions(
                        this, new String[]{android.Manifest.permission.CAMERA},
                        REQUEST_CAMERA);
            }

        } else {	//권한승인
            //Log.e("onConnected","else");
            // mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        }

    }



/**
    void addImage(){
        Intent intent=new Intent(getApplicationContext(),SetWalkImageActivity.class);
        startActivityForResult(intent, getimagesetting);
    }**/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream is = getContentResolver().openInputStream(data.getData());
                    bitmapWalkimage = BitmapFactory.decodeStream(is);
                    is.close();
                    bitmapWalkimage = resize(bitmapWalkimage);
                    imageViewWalkimage.setImageBitmap(bitmapWalkimage);
                    image = BitmapToString(bitmapWalkimage);
                    try {
                        image = URLEncoder.encode(image, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String BitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();
        String temp= Base64.encodeToString(bytes, Base64.DEFAULT);

        return temp;
    }

    private Bitmap resize(Bitmap bm){
        Configuration config=getResources().getConfiguration();
        if(config.smallestScreenWidthDp>=600)
            bm = Bitmap.createScaledBitmap(bm, 300, 180, true);
        else if(config.smallestScreenWidthDp>=400)
            bm = Bitmap.createScaledBitmap(bm, 200, 120, true);
        else if(config.smallestScreenWidthDp>=360)
            bm = Bitmap.createScaledBitmap(bm, 180, 108, true);
        else
            bm = Bitmap.createScaledBitmap(bm, 160, 96, true);
        return bm;
    }
    public static Bitmap StringToBitMap(String image){
        Log.e("StringToBitMap","StringToBitMap");
        try{
            byte [] encodeByte=Base64.decode(image,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            Log.e("StringToBitMap","good");
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    class InsertData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(WalkWriteActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String title = (String)params[1];
            String content = (String)params[2];
            String nickname = (String)params[3];
            //String walkimage = (String)params[4];
            String serverURL = (String)params[0];
            String postParameters = "title=" + title + "&content=" + content +"&nickname=" +nickname; //유저정보닉네임 외래키로 받아서 넣음!


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }
}