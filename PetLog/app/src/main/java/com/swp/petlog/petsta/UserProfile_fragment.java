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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.swp.petlog.MainActivity;
import com.swp.petlog.PreferenceManager;
import com.swp.petlog.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserProfile_fragment extends Fragment {
    private static String gPHPURL = "http://128.199.106.86/getUserProfile.php";
    private static String mPHPURL = "http://128.199.106.86/makeFollow.php";
    private static String uPHPURL = "http://128.199.106.86/updateFollow.php";
    private static String TAG = "petsta";

    private ImageButton btn_back, btn_search;
    private Button btn_follow;
    private ImageView profilePic;
    private TextView textViewNick, textViewFollowcnt, textViewIntro;

    private int follow_cnt, isfollow;
    private String jsonString, profileIntro, profileimgurl;

    public static UserProfile_fragment newInstance() {
        return new UserProfile_fragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.petsta_userprofile, container, false);

        final String mynick = PreferenceManager.getString(getActivity(), "userNick");
        final String usernick = getArguments().getString("nickname");

        GetProfile task = new GetProfile();
        task.execute(gPHPURL, usernick, mynick);

        btn_back = (ImageButton) v.findViewById(R.id.btn_back);
        btn_search = (ImageButton) v.findViewById(R.id.btn_petsta_search);

        profilePic = (ImageView) v.findViewById(R.id.petsta_profile_image);
        textViewNick = (TextView) v.findViewById(R.id.profile_text);
        textViewFollowcnt = (TextView) v.findViewById(R.id.follow_cnt);
        textViewIntro = (TextView) v.findViewById(R.id.profile_text_me);
        btn_follow = (Button) v.findViewById(R.id.btn_follow);

        textViewNick.setText(usernick);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().remove(UserProfile_fragment.this).commit();
                fragmentManager.popBackStack();
                Log.d("count", Integer.toString(fragmentManager.getBackStackEntryCount()));
                if (fragmentManager.getBackStackEntryCount() == 0) {
                    getActivity().finish();
                }
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PetstaMain)getActivity()).replaceFragment(UserSearch_fragment.newInstance());
            }
        });

        btn_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("after task : ", profileIntro + profileimgurl + follow_cnt + isfollow);
                switch (isfollow) {
                    case 1:
                        follow_cnt -= 1;
                        btn_follow.setText("구독하기");
                        isfollow = 0;
                        UpdateFollow updateFollow = new UpdateFollow();
                        updateFollow.execute(uPHPURL, usernick, mynick, Integer.toString(follow_cnt), Integer.toString(isfollow));
                        break;
                    case 0:
                        follow_cnt += 1;
                        btn_follow.setText("구독취소");
                        isfollow = 1;
                        UpdateFollow updateFollow2 = new UpdateFollow();
                        updateFollow2.execute(uPHPURL, usernick, mynick, Integer.toString(follow_cnt), Integer.toString(isfollow));
                        break;
                    case 2:
                        follow_cnt += 1;
                        btn_follow.setText("구독취소");
                        isfollow = 1;
                        MakeFollow makeFollow = new MakeFollow();
                        makeFollow.execute(mPHPURL, usernick, mynick, Integer.toString(follow_cnt), Integer.toString(isfollow));
                        break;
                }
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(UserProfile_fragment.this).attach(UserProfile_fragment.this).commit();
            }
        });

        return v;
    }

    private class GetProfile extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(getActivity(),
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
            String usernick = (String)params[1];
            String mynick = (String)params[2];

            String postParameters = "usernick=" + usernick + "&mynick=" + mynick;

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
        String TAG_INTRO = "intro";
        String TAG_IMG = "imgurl";
        String TAG_FOLLOW = "followercnt";
        String TAG_ISFOLLOW = "isfollow";

        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            profileIntro = jsonObject.getString(TAG_INTRO);
            profileimgurl = "http://128.199.106.86/" + jsonObject.getString(TAG_IMG);
            follow_cnt = jsonObject.getInt(TAG_FOLLOW);
            String temp = jsonObject.getString(TAG_ISFOLLOW);
            textViewFollowcnt.setText("구독자수" + "\n" + follow_cnt + "명");
            if (temp.equals("1")) {
                isfollow = 1;
            } else if (temp.equals("0")) {
                isfollow = 0;
            } else  isfollow = 2;

            switch (isfollow) {
                case 1:
                    btn_follow.setText("구독취소");
                    break;
                default:
                    btn_follow.setText("구독하기");
            }
            if(profileIntro != "null") {textViewIntro.setText(profileIntro);}
            if (!profileimgurl.equals("http://128.199.106.86/null")) {
                Glide.with(getActivity()).load(profileimgurl).into(profilePic);
            }


            Log.d("getprofile - ", profileIntro + profileimgurl + isfollow + follow_cnt);

        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }
    }

    class MakeFollow extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(getActivity(),
                    "Please Wait...", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            Log.d("like", "POST response  - " + result);
        }

        @Override
        protected String doInBackground(String... params) {

            String serverURL = (String)params[0];
            String usernick = (String)params[1];
            String mynick = (String)params[2];
            String followcnt = (String)params[3];
            String isfollowed = (String)params[4];

            String postParameters = "usernick=" + usernick + "&mynick=" + mynick + "&followcnt=" + followcnt + "&isfollowed=" + isfollowed;

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
                Log.d("like", "POST response code - " + responseStatusCode);

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
                Log.d("like", "InsertData: Error ", e);
                return new String("Error: " + e.getMessage());
            }
        }
    }

    class UpdateFollow extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(getActivity(),
                    "Please Wait...", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            Log.d("like", "POST response  - " + result);
        }

        @Override
        protected String doInBackground(String... params) {

            String serverURL = (String)params[0];
            String usernick = (String)params[1];
            String mynick = (String)params[2];
            String followcnt = (String)params[3];
            String isfollowed = (String)params[4];

            String postParameters = "usernick=" + usernick + "&mynick=" + mynick + "&followcnt=" + followcnt + "&isfollowed=" + isfollowed;

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
                Log.d("like", "POST response code - " + responseStatusCode);

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
                Log.d("like", "InsertData: Error ", e);
                return new String("Error: " + e.getMessage());
            }
        }
    }

}