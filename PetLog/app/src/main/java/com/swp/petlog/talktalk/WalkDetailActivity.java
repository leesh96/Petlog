package com.swp.petlog.talktalk;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import com.swp.petlog.talktalk.data.WalkData;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

//아이템클릭시 상세페이지!
public class WalkDetailActivity extends AppCompatActivity {
    private ArrayList<WalkData>mList;
    private ImageButton imgbtn_walkmenu, btn_back, btn_home;
    private Button btn_look_pos;
    private static String TAG = "TEST02";
    private boolean isModify = false;
    private static String dPHPURL = "http://128.199.106.86/deleteWalk.php";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.talktalk_walk_detail);


        btn_back=(ImageButton)findViewById(R.id.btn_back);
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
                Intent intent = new Intent(WalkDetailActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        String id="";
        String title="";
        String content="";
        String nickname="";
        String img="";
        String walkpos="";

        Bundle extras = getIntent().getExtras();


        //여기에 값 지정 해주면 디테일 액티비티에 해당데이터 넘어감!!////
        id=extras.getString("id");
        title=extras.getString("title");
        content=extras.getString("content");
        nickname=extras.getString("nickname");
        img=extras.getString("walkimg");
        walkpos=extras.getString("walktitle");



        TextView idView = (TextView) findViewById(R.id.id);
        TextView contentView = (TextView) findViewById(R.id.item_detail_content);
        TextView titleView =(TextView) findViewById(R.id.item_detail_title);
        TextView nickView = (TextView) findViewById(R.id.item_detail_nickname);
        ImageView imageView = (ImageView) findViewById(R.id.item_detail_image);

        Glide.with(WalkDetailActivity.this).load(img).into(imageView);

        //데이터 넘겨주는 값
        String idview=id; //나중엔 작성자로 바꿀예정
        String titleview = title; //제목
        String contentview=content; //내용
        String nicknameview=nickname;
        String imgview=img;
        //추가할거 -> 이미지, 댓글

        //클릭시 화면에 보여주는 곳//
        nickView.setText(nicknameview);
        titleView.setText(titleview);
        contentView.setText(contentview);
////////////////////
        Intent intent = getIntent(); //데이터를 받기위해 선언
        final String walktitle = intent.getStringExtra("walktitle");
        final String posx = intent.getStringExtra("posx");
        final String posy = intent.getStringExtra("posy");
////////////////////
        //위치보기버튼클릭시
        btn_look_pos=(Button)findViewById(R.id.btn_look_position);
        btn_look_pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //db에서 위치정보를 받아 넘김 지도가 열리고 산책로위치에 마커가찍힘.
                //아직 데이터못들고옴
                Intent intent= new Intent(getApplicationContext(), ResultGoogleMapActivity.class);
                intent.putExtra("walktitle",walktitle);
                intent.putExtra("posx",posx);
                intent.putExtra("posy",posy);
                startActivity(intent);
            }
        });

        imgbtn_walkmenu = (ImageButton) findViewById(R.id.btn_walkmenu);
        final String finalContent = content;
        final String finalTitle = title;
        final String finalId=id;
        final String finalImg=img;
        final String finalNick=nickname;

        final String PetLogNick= PreferenceManager.getString(WalkDetailActivity.this,"userNick");

        if (PetLogNick.equals(finalNick)) {
            imgbtn_walkmenu.setVisibility(View.VISIBLE);
            //.makeText(getApplicationContext(),"같은 닉네임입니다",Toast.LENGTH_LONG).show();

        }
        else if(!PetLogNick.equals(finalNick)){
            imgbtn_walkmenu.setVisibility(View.INVISIBLE);
            //Toast.makeText(getApplicationContext(),"닉네임이 다릅니다",Toast.LENGTH_LONG).show();

        }

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
                                editintent.putExtra("walkimage", finalImg);
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
}
