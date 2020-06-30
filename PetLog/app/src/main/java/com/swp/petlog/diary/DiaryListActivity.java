package com.swp.petlog.diary;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.swp.petlog.MainActivity;
import com.swp.petlog.PreferenceManager;
import com.swp.petlog.R;

public class DiaryListActivity extends AppCompatActivity {
    private static String PHPURL = "http://128.199.106.86/getDiary.php";
    private static String TAG = "getdiary";

    private ImageButton imageButtonhome, imageButtondel, imageButtonwrite;
    private TextView textViewdate;
    private RecyclerView recyclerViewDiary;
    private String jsonString;
    private ArrayList<DiarylistData> arrayList;
    private DiaryAdapter diaryAdapter;

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
        String myFormat = "yyyy-MM-dd"; //포맷수정하기
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        TextView et_date = (TextView) findViewById(R.id.diary_date);
        et_date.setText(sdf.format(myCalendar.getTime()));

        final String userid = PreferenceManager.getString(DiaryListActivity.this, "userID");
        arrayList.clear();
        diaryAdapter.notifyDataSetChanged();
        GetData task = new GetData();

        String setdate = et_date.getText().toString();
        final Date currentTime = Calendar.getInstance().getTime();
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);

        if (setdate.equals(simpleDateFormat.format(currentTime))) {
            task.execute(PHPURL, userid, "");
        }
        else {
            task.execute(PHPURL, userid, et_date.getText().toString());
        }
    }
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_main);

        final String userid = PreferenceManager.getString(DiaryListActivity.this, "userID");
        imageButtonhome = (ImageButton) findViewById(R.id.btn_home);
        imageButtonwrite = (ImageButton) findViewById(R.id.btn_dairywrite);
        imageButtondel = (ImageButton) findViewById(R.id.btn_diarydel);

        imageButtonhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiaryListActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        imageButtonwrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiaryListActivity.this, WriteDiaryActivity.class);
                startActivity(intent);
            }
        });

        textViewdate = (TextView) findViewById(R.id.diary_date);

        final Date currentTime = Calendar.getInstance().getTime();
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        textViewdate.setText(simpleDateFormat.format(currentTime));

        textViewdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(DiaryListActivity.this, android.R.style.Theme_Holo_Light_Dialog, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        recyclerViewDiary = (RecyclerView) findViewById(R.id.diary_rcview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewDiary.setLayoutManager(layoutManager);
        recyclerViewDiary.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));

        arrayList = new ArrayList<>();

        diaryAdapter = new DiaryAdapter(this, arrayList);
        recyclerViewDiary.setAdapter(diaryAdapter);
        arrayList.clear();
        diaryAdapter.notifyDataSetChanged();

        GetData task = new GetData();
        task.execute(PHPURL, userid, "");

        diaryAdapter.setOnItemClickListener(new DiaryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                DiarylistData diarylistData = arrayList.get(position);

                Intent intent = new Intent(DiaryListActivity.this, ShowDiaryActivity.class);
                intent.putExtra("diaryid", diarylistData.getMember_id());
                intent.putExtra("diarytitle", diarylistData.getMember_title());
                intent.putExtra("diarycontents", diarylistData.getMember_contents());
                intent.putExtra("diarymood", diarylistData.getMember_mood());
                intent.putExtra("diarydate", diarylistData.getMember_date());
                intent.putExtra("diaryimg", diarylistData.getMember_img());

                startActivity(intent);

            }
        });

        imageButtondel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiaryListActivity.this, DiaryDeleteActivity.class);
                startActivity(intent);
            }
        });
    }

    private class GetData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(DiaryListActivity.this,
                    "Please Wait...", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            //mTextViewResult.setText(result);
            Log.d(TAG, "response - " + result);

            if (result == null){
                //mTextViewResult.setText(errorString);
            }
            else {
                jsonString = result;
                showResult();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String userid = (String)params[1];
            String picdate = (String)params[2];

            String postParameters = "userid=" + userid + "&picdate=" + picdate;

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
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
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

    private void showResult() {

        String TAG_JSON = "diary";
        String TAG_ID = "id";
        String TAG_TITLE = "title";
        String TAG_CONTENTS = "contents";
        String TAG_IMG = "imgurl";
        String TAG_DATE = "date";
        String TAG_MOOD = "mood";

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                int id = Integer.parseInt(item.getString(TAG_ID));
                String Title = item.getString(TAG_TITLE);
                String Date = item.getString(TAG_DATE);
                String Moodwhat = item.getString(TAG_MOOD);
                String Contents = item.getString(TAG_CONTENTS);
                String Imgurl = "http://128.199.106.86/" + item.getString(TAG_IMG);
                int mood = Integer.parseInt(Moodwhat);

                DiarylistData diarylistData = new DiarylistData(Title, Date);

                diarylistData.setMember_id(id);
                diarylistData.setMember_title(Title);
                diarylistData.setMember_contents(Contents);
                diarylistData.setMember_mood(mood);
                diarylistData.setMember_date(Date);
                diarylistData.setMember_img(Imgurl);

                arrayList.add(diarylistData);
                diaryAdapter.notifyDataSetChanged();

            }

        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }
    }

}