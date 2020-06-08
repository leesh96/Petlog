package com.example.test02;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Change_info_Activity extends AppCompatActivity {
    private EditText editTextNick;
    private Button btn_change,btn_change_pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);

        editTextNick=(EditText)findViewById(R.id.et_changeNick);//닉네임치는곳
        btn_change_pw=(Button)findViewById(R.id.btn_change_pw); //비밀번호 변경버튼
        btn_change = (Button) findViewById(R.id.btn_save); //변경하기 버튼



        final boolean isModify = getIntent().getBooleanExtra("ismodify", false);
        final String userID = PreferenceManager.getString(Change_info_Activity.this, "userID");
        //final String userNick = PreferenceManager.getString(Change_info_Activity.this, "userNick");
        String userNick = getIntent().getStringExtra("userNick");

        if(isModify){
            editTextNick.setText(userNick);
        }

        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userNick = editTextNick.getText().toString();
                EditData task = new EditData();
                task.execute("http://128.199.106.86/change_info.php",userID,userNick);

                new AlertDialog.Builder(Change_info_Activity.this)
                        .setTitle("변경완료")
                        .setMessage("변경된 정보를 적용하려면 재로그인해주세요...")
                        .setNeutralButton("닫기", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int sumthin) {
                                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                            }
                        })
                        .show(); // 팝업창 보여줌
            }
        });

        btn_change_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ResetpwActivity.class);
                startActivity(intent);
            }
        });

    }

    class EditData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(Change_info_Activity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
        }


        @Override
        protected String doInBackground(String... params) {

            String ID=(String)params[1];
            String Nickname=(String)params[2];
            // String walkimage = (String)params[4];
            String serverURL = (String)params[0];
            String postParameters = "userID="+ID+"&userNick=" + Nickname; //유저정보닉네임 외래키로 받아서 넣음!


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

                return new String("Error: " + e.getMessage());
            }

        }
    }
}
