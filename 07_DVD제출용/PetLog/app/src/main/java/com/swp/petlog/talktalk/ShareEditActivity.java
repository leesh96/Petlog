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
import com.bumptech.glide.Glide;
import com.swp.petlog.MainActivity;
import com.swp.petlog.R;

public class ShareEditActivity extends AppCompatActivity {
    private static String TAG = "test02";
    private EditText mId,mTitle, mContent;
    private ImageView mImg;
    private String imgpath = "";
    private AlertDialog nullcheck;
    //private Button mInsertButton;

    private ImageButton btn_back, btn_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.talktalk_share_edit);

        mTitle=(EditText)findViewById(R.id.editText_main_title);
        mContent=(EditText)findViewById(R.id.editText_main_content);
        mImg=(ImageView)findViewById(R.id.share_image);

        btn_back = (ImageButton) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_home = (ImageButton) findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShareEditActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Intent intent = getIntent(); //데이터를 받기위해 선언

        //final int getId = intent.getIntExtra("id", 0);
        final String ShareId=intent.getStringExtra("id");
        final String ShareTitle = intent.getStringExtra("title");
        final String ShareContent = intent.getStringExtra("content");
        final boolean isModify = intent.getBooleanExtra("ismodify", false);
        final String ShareImg = intent.getStringExtra("defimg");

        if(isModify){
            mTitle.setText(ShareTitle);
            mContent.setText(ShareContent);
            Glide.with(ShareEditActivity.this).load(ShareImg).into(mImg);
        }

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            int permissionResult= checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permissionResult== PackageManager.PERMISSION_DENIED){
                String[] permissions= new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions,10);
            }
        }else{
            //cv.setVisibility(View.VISIBLE);
        }

        mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 10);
            }
        });

        Button btnedit = (Button) findViewById(R.id.btn_edit);
        btnedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String ShareTitle=mTitle.getText().toString();
                    String ShareContent=mContent.getText().toString();

                    if (ShareTitle.equals("")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ShareEditActivity.this);
                        nullcheck = builder.setMessage("제목을 입력하세요.").setNegativeButton("확인", null).create();
                        nullcheck.show();
                    } else if (ShareContent.equals("")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ShareEditActivity.this);
                        nullcheck = builder.setMessage("내용을 입력하세요.").setNegativeButton("확인", null).create();
                        nullcheck.show();
                    } else {
                        if (imgpath.equals("")) {
                            modify(ShareTitle, ShareContent, ShareId, "false");
                        } else {
                            modify(ShareTitle, ShareContent, ShareId, "true");
                        }
                    }
                    /*EditData task = new EditData();
                    task.execute("http://128.199.106.86/modifyShare.php",ShareId,ShareTitle,ShareContent);*/

                    /*Intent intent = new Intent(ShareEditActivity.this, ShareActivity.class);
                    startActivity(intent);
                    finish();*/
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
                        mImg.setImageURI(uri);
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

    public void modify(String title, String contents, String id, String picchanged) {
        SimpleMultiPartRequest smpr= new SimpleMultiPartRequest(Request.Method.POST, "http://128.199.106.86/modifyShare.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(ShareEditActivity.this, "수정성공", Toast.LENGTH_SHORT).show();
                Log.d("TAG", response);
                Intent intent = new Intent(ShareEditActivity.this, ShareActivity.class);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ShareEditActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                Log.d("TAG", error.toString());
            }
        });
        //요청 객체에 보낼 데이터를 추가
        smpr.addStringParam("title", title);
        smpr.addStringParam("contents", contents);
        smpr.addStringParam("id", id);
        smpr.addStringParam("picchanged", picchanged);
        smpr.addFile("image", imgpath);

        //요청객체를 서버로 보낼 우체통 같은 객체 생성
        RequestQueue requestQueue= Volley.newRequestQueue(ShareEditActivity.this);
        requestQueue.add(smpr);
    }

        /*class EditData extends AsyncTask<String, Void, String> {
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(ShareEditActivity.this,
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
        }*/
}
