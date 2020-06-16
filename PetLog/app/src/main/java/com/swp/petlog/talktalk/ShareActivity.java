package com.swp.petlog.talktalk;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.swp.petlog.MainActivity;
import com.swp.petlog.R;
import com.swp.petlog.talktalk.adapter.ShareAdapter;
import com.swp.petlog.talktalk.data.ShareData;

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

public class ShareActivity extends AppCompatActivity {

    private static String IP_ADDRESS = "http://128.199.106.86/getshare.php";  //php ip주소
    private static String TAG = "phptest";


    private ArrayList<ShareData> mArrayList;
    private ShareAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private String mJsonString;
    private ImageButton btn_back, btn_home, btn_write, btn_mypost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.talktalk_sharemain);

        mRecyclerView = (RecyclerView) findViewById(R.id.listView_main_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));//구분선
        /*LinearLayoutManager mLayoutManager=new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);*/
        mArrayList = new ArrayList<>();
        mAdapter = new ShareAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);
        mArrayList.clear();
        mAdapter.notifyDataSetChanged();

        GetData task = new GetData();
        task.execute(IP_ADDRESS, "");

        //////200516////////
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ShareData SD = mArrayList.get(position);
                //Toast.makeText(getApplicationContext(), SD.getShare_id()+' '+SD.getShare_title()+' '+SD.getShare_content(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
        btn_back=(ImageButton)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
        btn_home=(ImageButton)findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeintent = new Intent(ShareActivity.this, MainActivity.class);
                startActivity(homeintent);
                finish();
            }
        });
        btn_write=(ImageButton)findViewById(R.id.btn_sharewrite);
        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addintent = new Intent(ShareActivity.this, ShareWriteActivity.class);
                startActivity(addintent);
            }
        });

        btn_mypost = (ImageButton) findViewById(R.id.btn_myboard);
        btn_mypost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myboard = new Intent(ShareActivity.this, ShareMypostActivity.class);
                startActivity(myboard);
            }
        });

        /*imgbtn_sharemenu = (ImageButton) findViewById(R.id.btn_sharemenu);
        imgbtn_sharemenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context wrapper = new ContextThemeWrapper(ShareActivity.this, R.style.mypetmenustyle);
                PopupMenu popupMenu = new PopupMenu(ShareActivity.this, v);
                MenuInflater inflater = popupMenu.getMenuInflater();
                Menu menu = popupMenu.getMenu();

                inflater.inflate(R.menu.share_menu, menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.shareadd:
                                Intent addintent = new Intent(ShareActivity.this, ShareWriteActivity.class);
                                startActivity(addintent);
                                break;
                            case R.id.home:
                                Intent homeintent = new Intent(ShareActivity.this, MainActivity.class);
                                startActivity(homeintent);
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });*/
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
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

            progressDialog = ProgressDialog.show(ShareActivity.this,
                    "Please Wait...", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
          //  mTextViewResult.setText(result);
            Log.d(TAG, "response - " + result);

            if (result == null) {

              //  mTextViewResult.setText(errorString);
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
        String TAG_IMG = "imgurl";
        String TAG_DATE ="date";

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
                String postimg ="http://128.199.106.86/"+ item.getString(TAG_IMG);
                String date =item.getString(TAG_DATE);

                ShareData shareData = new ShareData(id,title,content,nickname,date);

                shareData.setShare_id(id);
                shareData.setShare_title(title);
                shareData.setShare_content(content);
                shareData.setShare_nickname(nickname);
                shareData.setShare_img(postimg);
                shareData.setShare_date(date);

                mArrayList.add(shareData);
                mAdapter.notifyDataSetChanged();
            }


        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }
}