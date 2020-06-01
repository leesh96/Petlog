package com.swp.petlog.mypet;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.bumptech.glide.Glide;
import com.swp.petlog.PreferenceManager;
import com.swp.petlog.R;
import com.swp.petlog.diary.DiaryListActivity;
import com.swp.petlog.diary.WriteDiaryActivity;

public class AddMypetInfoActivity extends AppCompatActivity {
    private EditText editTextName, editTextSex, editTextSpecies, editTextAge, editTextBday;
    private Button btn_add;
    private AlertDialog dialog;
    private ImageView imageViewFace;
    private ImageButton btn_back;
    private String imgpath;
    private static String PHPURL = "http://128.199.106.86/addMypet.php";
    private static String mPHPURL = "http://128.199.106.86/modifyMypet.php";
    private static String TAG = "mypet";


    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        EditText et_date = (EditText) findViewById(R.id.et_petBday);
        et_date.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypet_registinfo);
        btn_back = (ImageButton) findViewById(R.id.btn_back);
        editTextName = (EditText) findViewById(R.id.et_petName);
        editTextSex = (EditText) findViewById(R.id.et_petSex);
        editTextSpecies = (EditText) findViewById(R.id.et_petSpecie);
        editTextAge = (EditText) findViewById(R.id.et_petAge);
        editTextBday = (EditText) findViewById(R.id.et_petBday);
        imageViewFace = (ImageView) findViewById(R.id.mypet_image);

        final String PetOwner = PreferenceManager.getString(AddMypetInfoActivity.this, "userID");

        final int getId = getIntent().getIntExtra("petId", 0);
        String getName = getIntent().getStringExtra("petName");
        String getSex = getIntent().getStringExtra("petSex");
        String getSpecie = getIntent().getStringExtra("petSpecie");
        String getAge = getIntent().getStringExtra("petAge");
        String getBday = getIntent().getStringExtra("petBday");
        final boolean isModify = getIntent().getBooleanExtra("ismodify", false);
        String getFace = getIntent().getStringExtra("petFace");

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(isModify) {
            Glide.with(AddMypetInfoActivity.this).load(getFace).into(imageViewFace);
            editTextName.setText(getName);
            editTextSex.setText(getSex);
            editTextSpecies.setText(getSpecie);
            editTextAge.setText(getAge);
            editTextBday.setText(getBday);
        }

        editTextBday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddMypetInfoActivity.this, android.R.style.Theme_Holo_Light_Dialog, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        imageViewFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 10);
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

        btn_add = (Button) findViewById(R.id.btn_regist);
        if(isModify){
            btn_add.setText("수정하기");
        }
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isModify) {
                    String PetName = editTextName.getText().toString();
                    String PetSex = editTextSex.getText().toString();
                    String PetSpecies = editTextSpecies.getText().toString();
                    String PetAge = editTextAge.getText().toString();
                    String PetBday = editTextBday.getText().toString();
                    String PetId = Integer.toString(getId);

                    //ModifyData task = new ModifyData();
                    //task.execute(mPHPURL, PetName, PetSex, PetSpecies, PetAge, PetBday, PetId);

                    modify(PetName, PetSex, PetSpecies, PetAge, PetBday, PetId);
                }
                else {
                    String PetName = editTextName.getText().toString();
                    String PetSex = editTextSex.getText().toString();
                    String PetSpecies = editTextSpecies.getText().toString();
                    String PetAge = editTextAge.getText().toString();
                    String PetBday = editTextBday.getText().toString();

                    upload(PetName, PetSex, PetSpecies, PetAge, PetBday, PetOwner);
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
                        imageViewFace.setImageURI(uri);
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

    public void upload(String petname, String petsex, String petspecie, String petage, String petbday, String userid) {
        SimpleMultiPartRequest smpr= new SimpleMultiPartRequest(Request.Method.POST, PHPURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(AddMypetInfoActivity.this, "성공" + response, Toast.LENGTH_SHORT).show();
                Log.d("TAG", response);
                Intent intent = new Intent(AddMypetInfoActivity.this, MypetMainActivity.class);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddMypetInfoActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                Log.d("TAG", error.toString());
            }
        });
        //요청 객체에 보낼 데이터를 추가
        smpr.addStringParam("petName", petname);
        smpr.addStringParam("petSex", petsex);
        smpr.addStringParam("petSpecies", petspecie);
        smpr.addStringParam("petAge", petage);
        smpr.addStringParam("petBday", petbday);
        smpr.addStringParam("petOwner", userid);
        smpr.addFile("petFace", imgpath);

        //요청객체를 서버로 보낼 우체통 같은 객체 생성
        RequestQueue requestQueue= Volley.newRequestQueue(AddMypetInfoActivity.this);
        requestQueue.add(smpr);
    }

    public void modify(String petname, String petsex, String petspecie, String petage, String petbday, String petid) {
        SimpleMultiPartRequest smpr= new SimpleMultiPartRequest(Request.Method.POST, mPHPURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(AddMypetInfoActivity.this, "성공" + response, Toast.LENGTH_SHORT).show();
                Log.d("TAG", response);
                Intent intent = new Intent(AddMypetInfoActivity.this, MypetMainActivity.class);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddMypetInfoActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                Log.d("TAG", error.toString());
            }
        });
        //요청 객체에 보낼 데이터를 추가
        smpr.addStringParam("petName", petname);
        smpr.addStringParam("petSex", petsex);
        smpr.addStringParam("petSpecie", petspecie);
        smpr.addStringParam("petAge", petage);
        smpr.addStringParam("petBday", petbday);
        smpr.addStringParam("petid", petid);
        smpr.addFile("petFace", imgpath);

        //요청객체를 서버로 보낼 우체통 같은 객체 생성
        RequestQueue requestQueue= Volley.newRequestQueue(AddMypetInfoActivity.this);
        requestQueue.add(smpr);
    }

    /*class ModifyData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(AddMypetInfoActivity.this,
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

            String name = (String)params[1];
            String sex = (String)params[2];
            String specie = (String)params[3];
            String age = (String)params[4];
            String bday = (String)params[5];
            String petid = (String)params[6];

            String serverURL = (String)params[0];
            String postParameters = "name=" + name + "&sex=" + sex + "&specie=" + specie + "&age=" + age + "&bday=" + bday + "&petid=" + petid;

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

