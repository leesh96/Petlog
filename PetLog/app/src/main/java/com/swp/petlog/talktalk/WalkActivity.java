package com.swp.petlog.talktalk;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Date;

import com.swp.petlog.MainActivity;
import com.swp.petlog.R;
import com.swp.petlog.talktalk.Adapter.WalkAdapter;
import com.swp.petlog.talktalk.data.WalkData;

public class WalkActivity extends AppCompatActivity {
    private static String IP_ADDRESS = "128.199.106.86";  //php ip주소
    private static String TAG = "phptest";

    private TextView mTextViewResult;
    private ArrayList<WalkData> mArrayList;
    private WalkAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private String mJsonString;
    private ImageButton imgbtn_walkmenu,btn_back;


    //////////200517 날짜표시 구현

    // 현재시간을 msec 으로 구한다.
    long now = System.currentTimeMillis();
    // 현재시간을 date 변수에 저장한다.
    Date date = new Date(now);
    // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
    SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    // nowDate 변수에 값을 저장한다.
    String formatDate = sdfNow.format(date);
    TextView dateNow;

    //////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);

        mTextViewResult = (TextView) findViewById(R.id.textView_main_result);
        mRecyclerView = (RecyclerView) findViewById(R.id.listView_main_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));//구분선
        mTextViewResult.setMovementMethod(new ScrollingMovementMethod()); //스크롤메소드

        mArrayList = new ArrayList<>();
        mAdapter = new WalkAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);
        mArrayList.clear();
        mAdapter.notifyDataSetChanged();


        WalkActivity.GetData task = new WalkActivity.GetData();
        task.execute("http://" + IP_ADDRESS + "/getwalk.php", "");


        //////200516////////
        mRecyclerView.addOnItemTouchListener(new WalkActivity.RecyclerTouchListener(getApplicationContext(), mRecyclerView, new WalkActivity.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                WalkData SD = mArrayList.get(position);
                Toast.makeText(getApplicationContext(), SD.getWalk_id() + ' ' + SD.getWalk_title() + ' ' + SD.getWalk_content(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
        btn_back=(ImageButton)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(getApplicationContext(),TalktalkActivity.class);
                startActivity(intent);
            }
        });
        imgbtn_walkmenu = (ImageButton) findViewById(R.id.btn_walkmenu);
        imgbtn_walkmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context wrapper = new ContextThemeWrapper(WalkActivity.this, R.style.mypetmenustyle);
                PopupMenu popupMenu = new PopupMenu(WalkActivity.this, v);
                MenuInflater inflater = popupMenu.getMenuInflater();
                Menu menu = popupMenu.getMenu();

                inflater.inflate(R.menu.walk_menu, menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.walkadd:
                                Intent addintent = new Intent(WalkActivity.this, WalkWriteActivity.class);
                                startActivity(addintent);
                                break;
                            case R.id.home:
                                Intent homeintent = new Intent(WalkActivity.this, MainActivity.class);
                                startActivity(homeintent);
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private WalkActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final WalkActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }

    ////////200516////////

    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(WalkActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);
            Log.d(TAG, "response - " + result);

            if (result == null) {

                mTextViewResult.setText(errorString);
            } else {

                mJsonString = result;
                showResult();
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
    private void showResult() {

        //이 부분이 php 변수명과 동일해야함(헷갈리지 않기위해 컬럼명과 똑같이한다) 똑같지않으면 출력오류
        String TAG_JSON = "dongmin";
        String TAG_ID = "id";
        String TAG_TITLE = "title";
        String TAG_CONTENT = "content";
        String TAG_NICKNAME ="nickname";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                //여기 잘못넣으면 리사이클러뷰안뜸 또는 php array확인할것!
                String id = item.getString(TAG_ID);
                String title = item.getString(TAG_TITLE);
                String content = item.getString(TAG_CONTENT);
                String nickname =item.getString(TAG_NICKNAME);

                WalkData walkData = new WalkData(title,content,nickname,id);
                walkData.setWalk_id(id);
                walkData.setWalk_title(title);
                walkData.setWalk_content(content);
                walkData.setWalk_nickname(nickname);

                mArrayList.add(walkData);
                mAdapter.notifyDataSetChanged();
            }


        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }
}

