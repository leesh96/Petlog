package com.example.test02;

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
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test02.data.WalkData;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

//import navigation.WalkCommentActivity;


//아이템클릭시 상세페이지!
public class WalkDetailActivity extends AppCompatActivity {
    private ArrayList<WalkData>mList;
    private ImageButton imgbtn_walkmenu,btn_back,btn_comment;
    private static String TAG = "TEST02";
    private boolean isModify = false;
    private static String dPHPURL = "http://128.199.106.86/deleteWalk.php";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_detail);


        btn_back=(ImageButton)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(getApplicationContext(), WalkActivity.class);
                startActivity(intent);
            }
        });

        String id="";
        String title="";
        String content="";
        String nickname="";
        String img="";

        Bundle extras = getIntent().getExtras();


        //여기에 값 지정 해주면 디테일 액티비티에 해당데이터 넘어감!!////
        id=extras.getString("id");
        title=extras.getString("title");
        content=extras.getString("content");
        nickname=extras.getString("nickname");
        img=extras.getString("img");


        TextView idView = (TextView) findViewById(R.id.id);
        TextView contentView = (TextView) findViewById(R.id.item_detail_content);
        TextView titleView =(TextView) findViewById(R.id.item_detail_title);
        TextView nickView = (TextView) findViewById(R.id.item_detail_nickname);


        //데이터 넘겨주는 값
        String idview=id; //나중엔 작성자로 바꿀예정
        String titleview = title; //제목
        String contentview=content; //내용
        String nicknameview=nickname;
        //추가할거 -> 이미지, 댓글

        //클릭시 화면에 보여주는 곳//
        nickView.setText(nicknameview);
        titleView.setText(titleview);
        contentView.setText(contentview);

        btn_comment=(ImageButton)findViewById(R.id.btn_comment);
        final String finalTitle1 = title;
        final String finalId1=id;
       /**btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), WalkCommentActivity.class);
                intent.putExtra("title", finalTitle1);
                intent.putExtra("id", finalId1);

                startActivity(intent);

            }
        });**/

        imgbtn_walkmenu = (ImageButton) findViewById(R.id.btn_walkmenu);
        final String finalContent = content;
        final String finalTitle = title;
        final String finalId=id;
        imgbtn_walkmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context wrapper = new ContextThemeWrapper(WalkDetailActivity.this, R.style.mypetmenustyle);
                PopupMenu popupMenu = new PopupMenu(WalkDetailActivity.this, v);
                MenuInflater inflater = popupMenu.getMenuInflater();
                Menu menu = popupMenu.getMenu();

                inflater.inflate(R.menu.walkdetail_menu, menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.walkmodify:
                                isModify=true;
                                Intent editintent = new Intent(WalkDetailActivity.this, walkEditActivity.class);
                                editintent.putExtra("id",finalId);
                                editintent.putExtra("title", finalTitle); //편집화면에 제목 내용 불러옴
                                editintent.putExtra("content", finalContent);
                                editintent.putExtra("ismodify", isModify);
                                startActivity(editintent);

                                break;
                            case R.id.walkdelete:
                                String delId=finalId;
                                DeleteData task=new DeleteData();
                                task.execute(dPHPURL,delId);
                                Intent deleteintent = new Intent(WalkDetailActivity.this, WalkActivity.class);
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
    class DeleteData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(WalkDetailActivity.this,
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
}
