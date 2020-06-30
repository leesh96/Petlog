package com.swp.petlog.petsta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.swp.petlog.PreferenceManager;
import com.swp.petlog.R;
import com.swp.petlog.petsta.adapter.PetstaCommentAdapter;
import com.swp.petlog.petsta.data.PetstaCommentData;

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

public class Comment_fragment extends Fragment {
    private View view;

    private static String mPHPURL="http://128.199.106.86/petstaComment.php";
    private static String mGetPHPURL="http://128.199.106.86/getPetstaComment.php";
    private static String TAG = "petsta comment";

    private EditText editTextContents;
    private ArrayList<PetstaCommentData> arrayList;
    private PetstaCommentAdapter adapter;
    private RecyclerView recyclerView;
    private String jsonString;
    private ImageButton btn_back;

    public static Comment_fragment newInstance() {
        return new Comment_fragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.petsta_comment, container, false);

        final String nickname = PreferenceManager.getString(getActivity(), "userNick");
        final String postid = getArguments().getString("postid");

        editTextContents = (EditText)view.findViewById(R.id.comment_edit_message);
        recyclerView = (RecyclerView)view.findViewById(R.id.comment_recyclerview);
        btn_back = (ImageButton)view.findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().remove(Comment_fragment.this).commit();
                fragmentManager.popBackStack();
                Log.d("count", Integer.toString(fragmentManager.getBackStackEntryCount()));
                if (fragmentManager.getBackStackEntryCount() == 0) {
                    getActivity().finish();
                }
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));//구분선*/

        arrayList = new ArrayList<>();
        adapter = new PetstaCommentAdapter(arrayList, getActivity());
        recyclerView.setAdapter(adapter);
        arrayList.clear();
        adapter.notifyDataSetChanged();

        GetData getData = new GetData();
        getData.execute(mGetPHPURL, postid);

        Button btn_send=(Button)view.findViewById(R.id.comment_btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = editTextContents.getText().toString();

                SendData sendData = new SendData();
                sendData.execute(mPHPURL, postid, comment, nickname);
                editTextContents.setText(null);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(Comment_fragment.this).attach(Comment_fragment.this).commit();
            }
        });

        return view;
    }

    class SendData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(getContext(),
                    "Please Wait...", null, true, true);
        }



        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            //    mTextViewResult.setText(result);
            Log.d(TAG, "POST response  - " + result);
            if (result == null) {

                //  mTextViewResult.setText(errorString);
            } else {
                jsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {
            String postid = (String)params[1];
            String comment = (String)params[2];
            String nickname = (String)params[3];
            String serverURL = (String)params[0];
            String postParameters = "postid="+postid+"&comment=" + comment+"&nickname=" + nickname;

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

    private void showResult() {

        //이 부분이 php 변수명과 동일해야함(헷갈리지 않기위해 컬럼명과 똑같이한다) 똑같지않으면 출력오류
        String TAG_JSON = "dongmin";
        String TAG_ID = "id";
        String TAG_COMMENT = "comment";
        String TAG_NICKNAME ="nickname";


        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                //여기 잘못넣으면 리사이클러뷰안뜸 또는 php array확인할것!
                String id = item.getString(TAG_ID);
                String comment = item.getString(TAG_COMMENT);
                String nickname =item.getString(TAG_NICKNAME);

                PetstaCommentData petstaCommentData = new PetstaCommentData(id, comment, nickname);

                petstaCommentData.setPost_id(id);
                petstaCommentData.setMember_comment(comment);
                petstaCommentData.setMember_nick(nickname);

                arrayList.add(petstaCommentData);
                adapter.notifyDataSetChanged();
            }


        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }
    //
    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(getContext(),
                    "Please Wait...", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            /// mTextViewResult.setText(result);
            Log.d(TAG, "response - " + result);

            if (result == null) {

                //   mTextViewResult.setText(errorString);
            } else {
                jsonString = result;
                showResult1();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postid = (String)params[1];

            String postParameters = "postid="+postid;


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

    private void showResult1() {

        //이 부분이 php 변수명과 동일해야함(헷갈리지 않기위해 컬럼명과 똑같이한다) 똑같지않으면 출력오류
        String TAG_JSON = "dongmin";
        String TAG_COMID = "id";
        String TAG_ID="postid";
        String TAG_CONTENT = "comment";
        String TAG_NICKNAME ="nickname";


        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                //여기 잘못넣으면 리사이클러뷰안뜸 또는 php array확인할것!
                String comid = item.getString(TAG_COMID);
                String id = item.getString(TAG_ID);
                String comment = item.getString(TAG_CONTENT);
                String nickname =item.getString(TAG_NICKNAME);

                PetstaCommentData petstaCommentData = new PetstaCommentData(id, comment, nickname);

                petstaCommentData.setMember_id(comid);
                petstaCommentData.setPost_id(id);
                petstaCommentData.setMember_comment(comment);
                petstaCommentData.setMember_nick(nickname);

                arrayList.add(petstaCommentData);
                adapter.notifyDataSetChanged();
            }


        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }
}
