package swpj.petlog.petlog2.talktalk.navigation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import swpj.petlog.petlog2.talktalk.Adapter.ShareCommentAdapter;
import swpj.petlog.petlog2.PreferenceManager;
import swpj.petlog.petlog2.R;
import swpj.petlog.petlog2.talktalk.data.ShareCommentData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ShareCommentActivity extends AppCompatActivity {
    private static String mPHPURL="http://128.199.106.86/shareComment.php";
    private static String mGetPHPURL="http://128.199.106.86/GetshareComment.php";

    private static String TAG = "dongmin";
    private EditText mComment_content;
    private ArrayList<ShareCommentData> mArrayList;
    private ShareCommentAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private String mJsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_comment);

        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        mComment_content=(EditText)findViewById(R.id.comment_edit_message);
        mRecyclerView = (RecyclerView) findViewById(R.id.comment_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));//구분선

        mArrayList = new ArrayList<>();
        mAdapter = new ShareCommentAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);
        mArrayList.clear();
        mAdapter.notifyDataSetChanged();

//DB불러오는 PHP
        GetData show=new GetData();
        show.execute(mGetPHPURL,"");
//
        Intent intent = getIntent(); //데이터를 받기위해 선언
        final String Title = intent.getStringExtra("title"); //현재 댓글 작성중인 게시글제목 불러옴

        final String nickname= PreferenceManager.getString(ShareCommentActivity.this,"userNick");


        Button btn_send=(Button)findViewById(R.id.comment_btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = mComment_content.getText().toString();
                //final String title= PreferenceManager.getString(ShareCommentActivity.this,"title");
                sendData task = new sendData();
                task.execute(mPHPURL,comment,nickname,Title);//

                Intent intent=new Intent(getApplicationContext(), ShareCommentActivity.class);
                startActivity(intent);

            }
        });


    }
    class sendData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(ShareCommentActivity.this,
                    "Please Wait", null, true, true);
        }



        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            //    mTextViewResult.setText(result);
            Log.d(TAG, "POST response  - " + result);
            if (result == null) {

                //  mTextViewResult.setText(errorString);
            } else {

                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String comment = (String)params[1];
            String nickname = (String)params[2];
            String title = (String)params[3];
            String serverURL = (String)params[0];
            String postParameters ="comment=" + comment+"&nickname=" + nickname+"&title="+title;

            //String postParameters = "nickname=" + nickname + "&comment=" + comment+"&title="+title;
            //String postParameters = "nickname=" + nickname + "&comment=" + comment +"&title=" +title; //유저정보닉네임 외래키로 받아서 넣음!


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

    private void showResult() {

        //이 부분이 php 변수명과 동일해야함(헷갈리지 않기위해 컬럼명과 똑같이한다) 똑같지않으면 출력오류
        String TAG_JSON = "dongmin";
        String TAG_ID = "id";
        String TAG_PROFILE = "profile";
        String TAG_COMMENT = "comment";
        String TAG_NICKNAME ="nickname";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                //여기 잘못넣으면 리사이클러뷰안뜸 또는 php array확인할것!
                String id = item.getString(TAG_ID);
                //String profile = item.getString(TAG_PROFILE);
                String comment = item.getString(TAG_COMMENT);
                String nickname =item.getString(TAG_NICKNAME);

                ShareCommentData shareCommentData = new ShareCommentData(id,comment,nickname);

                shareCommentData.setShare_id(id);
                //shareCommentData.setShare_profile(profile);
                shareCommentData.setShare_comment(comment);
                shareCommentData.setShare_nickname(nickname);

                mArrayList.add(shareCommentData);
                mAdapter.notifyDataSetChanged();
            }


        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }
    //
    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(ShareCommentActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
           /// mTextViewResult.setText(result);
            Log.d(TAG, "response - " + result);

            if (result == null) {

             //   mTextViewResult.setText(errorString);
            } else {

                mJsonString = result;
                showResult1();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = params[1];


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }


    private void showResult1() {

        //이 부분이 php 변수명과 동일해야함(헷갈리지 않기위해 컬럼명과 똑같이한다) 똑같지않으면 출력오류
        String TAG_JSON = "dongmin";
        String TAG_ID = "id";
       // String TAG_TITLE = "title";
        String TAG_CONTENT = "comment";
        String TAG_NICKNAME ="nickname";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                //여기 잘못넣으면 리사이클러뷰안뜸 또는 php array확인할것!
                String id = item.getString(TAG_ID);
              //  String title = item.getString(TAG_TITLE);
                String comment = item.getString(TAG_CONTENT);
                String nickname =item.getString(TAG_NICKNAME);

                ShareCommentData shareCommentData = new ShareCommentData(id,comment,nickname);

                shareCommentData.setShare_id(id);
               // shareCommentData.setShare_title(title);
                shareCommentData.setShare_comment(comment);
                shareCommentData.setShare_nickname(nickname);

                mArrayList.add(shareCommentData);
                mAdapter.notifyDataSetChanged();
            }


        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }
    //
}