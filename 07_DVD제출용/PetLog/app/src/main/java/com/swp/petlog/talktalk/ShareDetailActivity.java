package com.swp.petlog.talktalk;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.swp.petlog.MainActivity;
import com.swp.petlog.PreferenceManager;
import com.swp.petlog.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

//아이템클릭시 상세페이지!
public class ShareDetailActivity extends AppCompatActivity{
    private ImageButton btn_back, btn_home, imgbtn_sharemenu;
    private Button btn_comment;
    private static String TAG = "TEST02";
    private boolean isModify = false;
    private static String dPHPURL = "http://128.199.106.86/deleteShare.php"; //삭제php
    private static String cPHPURL = "http://128.199.106.86/completionShare.php"; //거래완료php


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.talktalk_share_detail);

        btn_back=(ImageButton)findViewById(R.id.btn_back);

        //좌측상단 뒤로가기버튼 클릭시 나눔게시판으로 이동
        btn_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });

        btn_home = (ImageButton) findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShareDetailActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        String id="";
        String title="";
        String content="";
        String nickname="";
        String img="";

        Bundle extras = getIntent().getExtras();

        //여기에 값 지정 해주면 디테일 액티비티에 해당데이터 넘어감//
        id=extras.getString("id");
        title=extras.getString("title");
        content=extras.getString("content");
        nickname=extras.getString("nickname");
        img=extras.getString("img");

        TextView idView = (TextView) findViewById(R.id.id);
        TextView contentView = (TextView) findViewById(R.id.item_detail_content);
        final TextView titleView =(TextView) findViewById(R.id.item_detail_title);
        TextView nickView = (TextView) findViewById(R.id.item_detail_nickname);
        ImageView imageView = (ImageView) findViewById(R.id.item_detail_image);

        Glide.with(ShareDetailActivity.this).load(img).into(imageView);


        //데이터 넘겨주는 값
         String idview=id; //게시글id
         final String titleview = title; //제목
         String contentview=content; //내용
         final String nicknameview=nickname;//게시글작성자
         String imgview = img;


        //클릭시 화면에 보여주는 곳//
        nickView.setText("판매자 : " + nicknameview); //닉네임표시
        titleView.setText(titleview);   //제목표시
        contentView.setText(contentview);//내용표시

        //댓글버튼
        btn_comment=(Button)findViewById(R.id.btn_comment);

        final String finalTitle1 = title;
        final String finalId1=id;


        //댓글버튼클릭시 해당게시물의 id와 제목데이터가 댓글액티비티로 이동
        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), ShareCommentActivity.class);
                intent.putExtra("title", finalTitle1);
                intent.putExtra("id", finalId1);
                startActivity(intent);
            }
        });


        final String finalContent = content;
        final String finalTitle = title;
        final String finalId=id;
        final String finalNick=nickname;
        final String finalimg=img;

        imgbtn_sharemenu = (ImageButton) findViewById(R.id.btn_sharemenu);

        final String PetLogNick= PreferenceManager.getString(ShareDetailActivity.this,"userNick");
        //String user_nick=PetLogNick; //현재로그인된 닉네임
        if (PetLogNick.equals(finalNick)) {
            imgbtn_sharemenu.setVisibility(View.VISIBLE);
            //.makeText(getApplicationContext(),"같은 닉네임입니다",Toast.LENGTH_LONG).show();

        }
        else if(!PetLogNick.equals(finalNick)){
            imgbtn_sharemenu.setVisibility(View.INVISIBLE);
            //Toast.makeText(getApplicationContext(),"닉네임이 다릅니다",Toast.LENGTH_LONG).show();

        }

        //우측상단메뉴
        imgbtn_sharemenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context wrapper = new ContextThemeWrapper(ShareDetailActivity.this, R.style.mypetmenustyle);
                PopupMenu popupMenu = new PopupMenu(ShareDetailActivity.this, v);
                MenuInflater inflater = popupMenu.getMenuInflater();
                Menu menu = popupMenu.getMenu();

                inflater.inflate(R.menu.sharedetail_menu, menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.completion:
                                String complete="거래완료된 게시물입니다.";
                                titleView.setText(complete);
                                CompleteData completeData=new CompleteData();
                                completeData.execute(cPHPURL,finalId,complete);
                                Intent intent=new Intent(getApplicationContext(),ShareActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.sharemodify:
                                isModify=true;
                                Intent editintent= new Intent(ShareDetailActivity.this, ShareEditActivity.class);
                                editintent.putExtra("id",finalId);
                                editintent.putExtra("title", finalTitle); //편집화면에 제목 내용 불러옴
                                editintent.putExtra("content", finalContent);
                                editintent.putExtra("ismodify", isModify);
                                editintent.putExtra("defimg", finalimg);
                                startActivity(editintent);

                                //여기에 수정코드 짜기

                                break;
                            case R.id.sharedelete:
                                //여기에 삭제코드짜기
                                String delId=finalId;
                                DeleteData task=new DeleteData();
                                task.execute(dPHPURL,delId);
                                Intent deleteintent = new Intent(ShareDetailActivity.this, ShareActivity.class);
                                startActivity(deleteintent);
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });


    }

    //삭제데이터
    class DeleteData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(ShareDetailActivity.this,
                    "Please Wait...", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            Log.d(TAG, "POST response  - " + result);
        }

        @Override
        protected String doInBackground(String... params) {

            String id = (String)params[1];

            String serverURL = (String)params[0];
            String postParameters = "id=" + id;

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

    //거래완료데이터
    class CompleteData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(ShareDetailActivity.this,
                    "Please Wait...", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
        }

        @Override
        protected String doInBackground(String... params) {

            String id = (String)params[1];
            String title=(String)params[2];
            String serverURL = (String)params[0];
            String postParameters = "id=" + id +"&title="+title;

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
                Log.d(TAG, "InsertData: Error ", e);
                return new String("Error: " + e.getMessage());
            }

        }
    }
}
