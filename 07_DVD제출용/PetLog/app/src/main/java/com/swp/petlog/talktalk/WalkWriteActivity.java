package com.swp.petlog.talktalk;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
import com.swp.petlog.MainActivity;
import com.swp.petlog.PreferenceManager;
import com.swp.petlog.R;

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
    private static String IP_ADDRESS = "128.199.106.86";
    private static String TAG = "test02";
    private EditText mEditTextTitle;
    private EditText mEditTextContent;
    //20.05.11 추가
    private ImageView imageViewWalkimage;
    private String imgpath = "";
    private String putimg;

    private int checklocate = 0;

    private AlertDialog nullcheck;

    private Uri imguri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.talktalk_walk_write);
        mEditTextTitle = (EditText)findViewById(R.id.editText_main_title);
        mEditTextContent = (EditText)findViewById(R.id.editText_main_content);
        imageViewWalkimage = (ImageView)findViewById(R.id.walk_image);

        final String nickname= PreferenceManager.getString(WalkWriteActivity.this,"userNick");

        //작성한 데이터값을 gps페이지에 넘긴후 다시 그대로 돌려받음
        Intent writeintent = getIntent(); //데이터를 받기위해 선언
        String title = writeintent.getStringExtra("title");
        String content = writeintent.getStringExtra("content");
        final String image = writeintent.getStringExtra("image");

        String titleview = title; //제목
        String contentview=content; //내용

        if(image != null) {
            imguri = Uri.parse(image);
            imgpath = getRealPathFromUri(imguri);
            imageViewWalkimage.setImageURI(imguri);
        }

        //클릭시 화면에 보여주는 곳//
        mEditTextTitle.setText(titleview);
        mEditTextContent.setText(contentview);

        ImageButton buttonBack =(ImageButton)findViewById(R.id.btn_back);
        buttonBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){ //쓰기화면에서 뒤로가기버튼클릭시 게시판메인으로이동
                finish();
            }
        });

        ImageButton btn_home = (ImageButton) findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WalkWriteActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button ButtonGps =(Button)findViewById(R.id.btn_gps);
        ButtonGps.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(getApplicationContext(), GoogleMapActivity.class);
                if (imguri != null) {
                    putimg = imguri.toString();
                    intent.putExtra("image", putimg);
                }
                String title = mEditTextTitle.getText().toString();
                String content = mEditTextContent.getText().toString();

                //200609 입력값 그대로 전달
                intent.putExtra("title",title);
                intent.putExtra("content",content);

                startActivity(intent);
                checklocate = 1;
            }
        });

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            int permissionResult= checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permissionResult== PackageManager.PERMISSION_DENIED){
                String[] permissions= new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions,10);
            }
        }else{
            //cv.setVisibility(View.VISIBLE);
        }

        imageViewWalkimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 10);
            }
        });

        Intent intent = getIntent(); //데이터를 받기위해 선언
        final String position = intent.getStringExtra("walktitle");
        final Double posx=intent.getDoubleExtra("posx",0);  //위도 받아옴
        final Double posy=intent.getDoubleExtra("posy",0);  //경도 받아옴
        // final LatLng position1= intent.get("pos1");
        final String posx1=Double.toString(posx);
        final String posy1=Double.toString(posy);

        Button buttonInsert = (Button)findViewById(R.id.board_insert);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mEditTextTitle.getText().toString();
                String content = mEditTextContent.getText().toString();
                //String walkimage = image.getBytes().toString();

                /*InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS + "/walkInsert.php", title,content,nickname,position,posx1,posy1);*/

                if (imgpath.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(WalkWriteActivity.this);
                    nullcheck = builder.setMessage("사진은 필수항목입니다.").setNegativeButton("확인", null).create();
                    nullcheck.show();
                } else if (title.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(WalkWriteActivity.this);
                    nullcheck = builder.setMessage("제목을 입력하세요.").setNegativeButton("확인", null).create();
                    nullcheck.show();
                } else if (content.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(WalkWriteActivity.this);
                    nullcheck = builder.setMessage("내용을 입력하세요.").setNegativeButton("확인", null).create();
                    nullcheck.show();
                } else if (posx == 0.0 || posy == 0.0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(WalkWriteActivity.this);
                    nullcheck = builder.setMessage("위치선택은 필수입니다.").setNegativeButton("확인", null).create();
                    nullcheck.show();
                } else {
                    upload(title, content, nickname, position, posx1, posy1);
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 10 :
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED) //사용자가 허가 했다면
                {
                    Toast.makeText(this, "외부 메모리 읽기/쓰기 사용 가능", Toast.LENGTH_SHORT).show();

                }else{//거부했다면
                    Toast.makeText(this, "외부 메모리 읽기/쓰기 제한", Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 10:
                if(resultCode==RESULT_OK){
                    //선택한 사진의 경로(Uri)객체 얻어오기
                    Uri uri= data.getData();
                    if(uri!=null){
                        imageViewWalkimage.setImageURI(uri);
                        imguri = uri;
                        imgpath = getRealPathFromUri(uri);
                    }

                }else
                {
                    Toast.makeText(this, "이미지 선택을 하지 않았습니다.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public String getRealPathFromUri(Uri uri){
        String[] proj= {MediaStore.Images.Media.DATA};
        CursorLoader loader= new CursorLoader(this, uri, proj, null, null, null);
        Cursor cursor= loader.loadInBackground();
        int column_index= ((Cursor) cursor).getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result= cursor.getString(column_index);
        cursor.close();
        return result;
    }

    public void upload(String title, String content, String nickname, String position, String posx, String posy) {
        SimpleMultiPartRequest smpr= new SimpleMultiPartRequest(Request.Method.POST, "http://" + IP_ADDRESS + "/walkInsert.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(WalkWriteActivity.this, "작성완료", Toast.LENGTH_SHORT).show();
                Log.d("TAG", response);
                Intent intent = new Intent(WalkWriteActivity.this, WalkActivity.class);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(WalkWriteActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                Log.d("TAG", error.toString());
            }
        });
        //요청 객체에 보낼 데이터를 추가
        smpr.addStringParam("title", title);
        smpr.addStringParam("content", content);
        smpr.addStringParam("nickname", nickname);
        smpr.addStringParam("position", position);
        smpr.addStringParam("posx", posx);
        smpr.addStringParam("posy", posy);
        smpr.addFile("image", imgpath);

        //요청객체를 서버로 보낼 우체통 같은 객체 생성
        RequestQueue requestQueue= Volley.newRequestQueue(WalkWriteActivity.this);
        requestQueue.add(smpr);
    }

    /*class InsertData extends AsyncTask<String, Void, String>{
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
            String position=(String)params[4];
            String posx=(String)params[5];
            String posy=(String)params[6];
            String serverURL = (String)params[0];
            String postParameters = "title=" + title + "&content=" + content +"&nickname=" +nickname+"&position="+position+"&posx="+posx+"&posy="+posy; //유저정보닉네임 외래키로 받아서 넣음!


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
    }*/
}