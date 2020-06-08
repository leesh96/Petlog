package com.example.test02;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class walkEditActivity extends AppCompatActivity {
    private static String TAG = "test02";
    private EditText mId,mTitle, mContent;
    //private Button mInsertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_edit);

        mTitle=(EditText)findViewById(R.id.editText_main_title);
        mContent=(EditText)findViewById(R.id.editText_main_content);

        Intent intent = getIntent(); //데이터를 받기위해 선언

        //final int getId = intent.getIntExtra("id", 0);
        final String ShareId=intent.getStringExtra("id");
        final String ShareTitle = intent.getStringExtra("title");
        final String ShareContent = intent.getStringExtra("content");
        final boolean isModify = intent.getBooleanExtra("ismodify", false);

        if(isModify){
            mTitle.setText(ShareTitle);
            mContent.setText(ShareContent);
        }

        Button btnedit = (Button) findViewById(R.id.btn_edit);
        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ShareTitle=mTitle.getText().toString();
                String ShareContent=mContent.getText().toString();

                EditData task = new EditData();
                task.execute("http://128.199.106.86/modifyWalk.php",ShareId,ShareTitle,ShareContent);

                Intent intent = new Intent(walkEditActivity.this, WalkActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    class EditData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(walkEditActivity.this,
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

            String title = (String)params[2];
            String content = (String)params[3];
            String id=(String)params[1];
            // String walkimage = (String)params[4];
            String serverURL = (String)params[0];
            String postParameters = "id=" + id + "&title="+title+"&content=" + content; //유저정보닉네임 외래키로 받아서 넣음!


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
