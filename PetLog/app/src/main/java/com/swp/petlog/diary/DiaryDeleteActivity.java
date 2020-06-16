package com.swp.petlog.diary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;

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
import java.util.ArrayList;

import com.swp.petlog.MainActivity;
import com.swp.petlog.PreferenceManager;
import com.swp.petlog.R;

public class DiaryDeleteActivity extends AppCompatActivity {

    private static String PHPURL = "http://128.199.106.86/deleteDiaryList.php";
    private static String dPHPURL = "http://128.199.106.86/deleteDiary.php";
    private static String TAG = "diary";

    private ImageButton imageButtonhome, imageButtondel, btn_back;
    private RecyclerView recyclerViewDiary;
    private String jsonString;
    private ArrayList<DiarylistData> arrayList;
    private DiaryDeleteAdapter diaryDeleteAdapter;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_delete);

        final String userid = PreferenceManager.getString(DiaryDeleteActivity.this, "userID");
        imageButtonhome = (ImageButton) findViewById(R.id.btn_home);
        imageButtondel = (ImageButton) findViewById(R.id.btn_diarydel);
        btn_back = (ImageButton) findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageButtonhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiaryDeleteActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        recyclerViewDiary = (RecyclerView) findViewById(R.id.diarydel_rcview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewDiary.setLayoutManager(layoutManager);
        recyclerViewDiary.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));

        arrayList = new ArrayList<>();

        diaryDeleteAdapter = new DiaryDeleteAdapter(this, arrayList);
        recyclerViewDiary.setAdapter(diaryDeleteAdapter);
        arrayList.clear();
        diaryDeleteAdapter.notifyDataSetChanged();

        GetData task = new GetData();
        task.execute(PHPURL, userid);

        diaryDeleteAdapter.setOnCheckedChangeListener(new DiaryDeleteAdapter.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            }
        });

        imageButtondel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteData task1 = new DeleteData();
                ArrayList<String> delList = new ArrayList<>();

                for(int i = 0; i < arrayList.size(); i++) {
                    if (arrayList.get(i).isSelected()){
                        String diaryId = Integer.toString(arrayList.get(i).getMember_id());
                        delList.add(diaryId);
                    }
                }
                task1.execute(delList);

                Intent intent = new Intent(DiaryDeleteActivity.this, DiaryListActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private class GetData extends AsyncTask<String, Void, String>{

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(DiaryDeleteActivity.this,
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

            String postParameters = "userid=" + userid;

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
                int mood = Integer.parseInt(Moodwhat);

                DiarylistData diarylistData = new DiarylistData(Title, Date);

                diarylistData.setMember_id(id);
                diarylistData.setMember_title(Title);
                diarylistData.setMember_contents(Contents);
                diarylistData.setMember_mood(mood);
                diarylistData.setMember_date(Date);

                arrayList.add(diarylistData);
                diaryDeleteAdapter.notifyDataSetChanged();

            }

        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }
    }

    class DeleteData extends AsyncTask<ArrayList<String>, Void, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(DiaryDeleteActivity.this,
                    "Please Wait...", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            Log.d(TAG, "POST response  - " + result);
        }

        @Override
        protected String doInBackground(ArrayList<String>... params) {

            ArrayList<String> diaryid = (ArrayList<String>)params[0];

            JSONArray jsonArray = new JSONArray();
            try {
                for(int i = 0; i < diaryid.size(); i++) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("diaryid", diaryid.get(i));
                    jsonArray.put(jsonObject);
                }

                Log.d("ArrayList to JSONArray", jsonArray.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String serverURL = "http://128.199.106.86/deleteDiary.php";
            String postParameters = "diaryid=" + jsonArray.toString();

            try {
                URL url = new URL(serverURL);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("utf-8"));
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
