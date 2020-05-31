package com.swp.petlog.diary;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;

import com.swp.petlog.MainActivity;
import com.swp.petlog.PreferenceManager;
import com.swp.petlog.R;

public class WriteDiaryActivity extends AppCompatActivity {
    private static String PHPURL = "http://128.199.106.86/writeDiary.php";
    private static String mPHPURL = "http://128.199.106.86/modifyDiary.php";
    private static String TAG = "diary";

    private ImageView imageViewMood, imageViewPic;
    private ImageButton imageButtonback, imageButtonhome, imageButtonUpload;
    private EditText editTextTitle, editTextContent;
    private TextView textViewDate;
    private AlertDialog dialog;
    private String[] mood = {"기쁨", "슬픔", "화남", "아픔", "무표정"};
    public int inputmood;

    private String imgpath;

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

        TextView et_date = (TextView) findViewById(R.id.diary_showdate);
        et_date.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_write);

        imageViewMood = (ImageView) findViewById(R.id.diary_inputmood);
        imageViewPic = (ImageView) findViewById(R.id.diary_inputpic);
        editTextTitle = (EditText) findViewById(R.id.diary_ettitle);
        editTextContent = (EditText) findViewById(R.id.diary_etcontent);
        textViewDate = (TextView) findViewById(R.id.diary_showdate);
        imageButtonUpload = (ImageButton) findViewById(R.id.btn_dairy_upload);
        imageButtonback = (ImageButton) findViewById(R.id.btn_back);
        imageButtonhome = (ImageButton) findViewById(R.id.btn_home);

        final int getId = getIntent().getIntExtra("m_diaryid", 1);
        String getTitle = getIntent().getStringExtra("m_diarytitle");
        String getContents = getIntent().getStringExtra("m_diarycontents");
        String getDate = getIntent().getStringExtra("m_diarydate");
        final int getMood = getIntent().getIntExtra("m_diarymood", 0);
        final boolean isModify = getIntent().getBooleanExtra("ismodify", false);

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        textViewDate.setText(simpleDateFormat.format(currentTime));

        if (isModify) {
            textViewDate.setText(getDate);
            textViewDate.setEnabled(false);
            editTextTitle.setText(getTitle);
            editTextContent.setText(getContents);
            if (getMood == 0) {
                imageViewMood.setImageResource(R.drawable.ic_happy);
                inputmood = 0;
            }
            if (getMood == 1) {
                imageViewMood.setImageResource(R.drawable.ic_sad);
                inputmood = 1;
            }
            if (getMood == 2) {
                imageViewMood.setImageResource(R.drawable.ic_angry);
                inputmood = 2;
            }
            if (getMood == 3) {
                imageViewMood.setImageResource(R.drawable.ic_sick);
                inputmood = 3;
            }
            if (getMood == 4) {
                imageViewMood.setImageResource(R.drawable.ic_none);
                inputmood = 4;
            }
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

        imageViewPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 10);
            }
        });

        imageButtonhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WriteDiaryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        imageButtonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(WriteDiaryActivity.this, android.R.style.Theme_Holo_Light_Dialog, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        imageViewMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        dialog = new AlertDialog.Builder(WriteDiaryActivity.this)
                .setItems(mood, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            imageViewMood.setImageResource(R.drawable.ic_happy);
                            inputmood = 0;
                        }
                        if (which == 1) {
                            imageViewMood.setImageResource(R.drawable.ic_sad);
                            inputmood = 1;
                        }
                        if (which == 2) {
                            imageViewMood.setImageResource(R.drawable.ic_angry);
                            inputmood = 2;
                        }
                        if (which == 3) {
                            imageViewMood.setImageResource(R.drawable.ic_sick);
                            inputmood = 3;
                        }
                        if (which == 4) {
                            imageViewMood.setImageResource(R.drawable.ic_none);
                            inputmood = 4;
                        }
                    }
                })
                .setTitle("감정선택")
                .setPositiveButton("확인", null)
                .setNegativeButton("취소", null)
                .create();

        imageButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isModify) {
                    String title = editTextTitle.getText().toString();
                    String contents = editTextContent.getText().toString();
                    String mood = Integer.toString(inputmood);
                    String diaryid = Integer.toString(getId);

                    ModifyData task = new ModifyData();
                    task.execute(mPHPURL, title, contents, mood, diaryid);

                    Intent intent = new Intent(WriteDiaryActivity.this, DiaryListActivity.class);
                    startActivity(intent);
                }
                else {
                    String title = editTextTitle.getText().toString();
                    String contents = editTextContent.getText().toString();
                    String userid = PreferenceManager.getString(WriteDiaryActivity.this, "userID");
                    String writedate = textViewDate.getText().toString();
                    String mood = Integer.toString(inputmood);

                    //InsertData task = new InsertData();
                    //task.execute(PHPURL, title, contents, userid, writedate, mood, imgpath);

                    upload(title, contents, userid, writedate, mood);
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
                        imageViewPic.setImageURI(uri);
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

    public void upload(String title, String contents, String userid, String writedate, String mood) {
        SimpleMultiPartRequest smpr= new SimpleMultiPartRequest(Request.Method.POST, PHPURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(WriteDiaryActivity.this, "성공" + response, Toast.LENGTH_SHORT).show();
                Log.d("TAG", response);
                Intent intent = new Intent(WriteDiaryActivity.this, DiaryListActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(WriteDiaryActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                Log.d("TAG", error.toString());
            }
        });
        //요청 객체에 보낼 데이터를 추가
        smpr.addStringParam("title", title);
        smpr.addStringParam("contents", contents);
        smpr.addStringParam("writedate", writedate);
        smpr.addStringParam("mood", mood);
        smpr.addStringParam("userid", userid);
        smpr.addFile("image", imgpath);

        //요청객체를 서버로 보낼 우체통 같은 객체 생성
        RequestQueue requestQueue= Volley.newRequestQueue(WriteDiaryActivity.this);
        requestQueue.add(smpr);
    }

    /* class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(WriteDiaryActivity.this,
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
            String contents = (String)params[2];
            String userid = (String)params[3];
            String writedate = (String)params[4];
            String mood = (String)params[5];
            String image = (String)params[6];

            File file = new File(image);

            String serverURL = (String)params[0];
            String postParameters = "title=" + title + "&contents=" + contents + "&userid=" + userid + "&image" + file + "&writedate=" + writedate + "&mood=" + mood;

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

    class ModifyData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(WriteDiaryActivity.this,
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
            String contents = (String)params[2];
            String mood = (String)params[3];
            String diaryid = (String)params[4];

            String serverURL = (String)params[0];
            String postParameters = "title=" + title + "&contents=" + contents + "&mood=" + mood + "&diaryid=" + diaryid;

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
