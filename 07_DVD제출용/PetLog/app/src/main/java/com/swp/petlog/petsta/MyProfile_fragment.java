package com.swp.petlog.petsta;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.content.CursorLoader;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.swp.petlog.MainActivity;
import com.swp.petlog.PreferenceManager;
import com.swp.petlog.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyProfile_fragment extends Fragment {
    private static String PHPURL = "http://128.199.106.86/petstaIntro.php";
    private static String dPHPURL = "http://128.199.106.86/deleteIntro.php";
    private static String gPHPURL = "http://128.199.106.86/getProfile.php";
    private static String fPHPURL = "http://128.199.106.86/profileUpload.php";
    private static String dpPHPURL = "http://128.199.106.86/deleteUserFace.php";
    private static String TAG = "petsta";

    private ImageButton btn_home, btn_search;

    private ImageView profilePic;
    private TextView textViewNick, textViewFollowcnt;
    private EditText editTextIntro;
    private Button btn_controlFollow, btn_changepic, btn_controlPost, btn_deletepic;
    private ImageButton imageButtonApply, imageButtonDel;

    private int follow_cnt;
    private String jsonString, profileIntro, imgpath, profileimgurl;

    public static MyProfile_fragment newInstance() {
        return new MyProfile_fragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.petsta_profile, container, false);

        final String nickname = PreferenceManager.getString(getActivity(), "userNick");

        GetProfile getProfile = new GetProfile();
        getProfile.execute(gPHPURL, nickname);

        btn_home = (ImageButton) v.findViewById(R.id.btn_home);
        btn_search = (ImageButton) v.findViewById(R.id.btn_petsta_search);

        profilePic = (ImageView) v.findViewById(R.id.petsta_profile_image);
        textViewNick = (TextView) v.findViewById(R.id.profile_text);
        textViewFollowcnt = (TextView) v.findViewById(R.id.follow_cnt);
        editTextIntro = (EditText) v.findViewById(R.id.profile_text_me);
        btn_controlFollow = (Button) v.findViewById(R.id.btn_control_follow);
        imageButtonApply = (ImageButton) v.findViewById(R.id.btn_introapply);
        imageButtonDel = (ImageButton) v.findViewById(R.id.btn_introdel);
        btn_changepic = (Button) v.findViewById(R.id.btn_changepic);
        btn_controlPost = (Button) v.findViewById(R.id.btn_controlpost);
        btn_deletepic = (Button) v.findViewById(R.id.btn_deletepic);

        textViewNick.setText(nickname);

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PetstaMain)getActivity()).replaceFragment(UserSearch_fragment.newInstance());
            }
        });

        btn_deletepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeletePic deletePic = new DeletePic();
                deletePic.execute(dpPHPURL, nickname);
                PreferenceManager.removeKey(getActivity(), "userFace");
            }
        });

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            int permissionResult = getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permissionResult== PackageManager.PERMISSION_DENIED){
                String[] permissions= new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions,10);
            }
        }else{
            //cv.setVisibility(View.VISIBLE);
        }

        btn_changepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 10);
            }
        });

        imageButtonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String intro = editTextIntro.getText().toString();
                InsertIntro task1 = new InsertIntro();
                task1.execute(PHPURL, nickname, intro);

            }
        });

        imageButtonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextIntro.setText(null);
                DeleteIntro task2 = new DeleteIntro();
                task2.execute(dPHPURL, nickname);
            }
        });

        btn_controlPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PetstaMain)getActivity()).replaceFragment(Mypost_fragment.newInstance());
            }
        });

        btn_controlFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PetstaMain)getActivity()).replaceFragment(MyFollow_fragment.newInstance());
            }
        });

        return v;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 10 :
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED) //사용자가 허가 했다면
                {
                    Toast.makeText(getActivity(), "외부 메모리 읽기/쓰기 사용 가능", Toast.LENGTH_SHORT).show();

                }else{//거부했다면
                    Toast.makeText(getActivity(), "외부 메모리 읽기/쓰기 제한", Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 10:
                if(resultCode == -1){
                    //선택한 사진의 경로(Uri)객체 얻어오기
                    Uri uri= data.getData();
                    if(uri!=null){
                        profilePic.setImageURI(uri);
                        imgpath = getRealPathFromUri(uri);
                        String nickname = textViewNick.getText().toString();
                        upload(nickname);
                        btn_changepic.setText("사진변경");
                    }

                }else
                {
                    Toast.makeText(getActivity(), "이미지 선택을 하지 않았습니다.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public String getRealPathFromUri(Uri uri){
        String[] proj= {MediaStore.Images.Media.DATA};
        CursorLoader loader= new CursorLoader(getActivity(), uri, proj, null, null, null);
        Cursor cursor= loader.loadInBackground();
        int column_index= ((Cursor) cursor).getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result= cursor.getString(column_index);
        cursor.close();
        return result;
    }

    public void upload(String nickname) {
        SimpleMultiPartRequest smpr= new SimpleMultiPartRequest(Request.Method.POST, fPHPURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity(), "사진변경성공" + response, Toast.LENGTH_SHORT).show();
                Log.d("TAG", response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String updateface = jsonObject.getString("userFace");
                    PreferenceManager.setString(getActivity(), "userFace", "http://128.199.106.86/" + updateface);
                    Log.d("faceurl", updateface);

                } catch (JSONException e) {
                    Log.d(TAG, "showResult : ", e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
                Log.d("TAG", error.toString());
            }
        });
        //요청 객체에 보낼 데이터를 추가
        smpr.addStringParam("nickname", nickname);
        smpr.addFile("image", imgpath);

        //요청객체를 서버로 보낼 우체통 같은 객체 생성
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(smpr);
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
            String nickname = (String)params[1];

            String postParameters = "nickname=" + nickname;

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
        try {
            String TAG_INTRO = "intro";
            String TAG_IMG = "imgurl";
            String TAG_FOLLOW = "followercnt";

            JSONObject jsonObject = new JSONObject(jsonString);

            profileIntro = jsonObject.getString(TAG_INTRO);
            profileimgurl = "http://128.199.106.86/" + jsonObject.getString(TAG_IMG);
            if (profileimgurl.equals("http://128.199.106.86/null")) {
                btn_changepic.setText("사진추가");
            } else {
                Glide.with(getActivity()).load(profileimgurl).into(profilePic);
            }
            if(profileIntro != "null") {
                editTextIntro.setText(profileIntro);
            }
            follow_cnt = jsonObject.getInt(TAG_FOLLOW);
            textViewFollowcnt.setText("구독자수" + "\n" + follow_cnt + "명");

        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }

        /*FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(MyProfile_fragment.this).attach(MyProfile_fragment.this).commit();*/
    }

    class InsertIntro extends AsyncTask<String, Void, String> {
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
            Log.d(TAG, "POST response  - " + result);
        }

        @Override
        protected String doInBackground(String... params) {

            String nickname = (String)params[1];
            String intro = (String)params[2];

            String serverURL = (String)params[0];
            String postParameters = "nickname=" + nickname + "&intro=" + intro;

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

    class DeleteIntro extends AsyncTask<String, Void, String> {
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
            Log.d(TAG, "POST response  - " + result);
        }

        @Override
        protected String doInBackground(String... params) {

            String nickname = (String)params[1];

            String serverURL = (String)params[0];
            String postParameters = "nickname=" + nickname;

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

    class DeletePic extends AsyncTask<String, Void, String> {
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
            Log.d(TAG, "POST response  - " + result);

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(MyProfile_fragment.this).attach(MyProfile_fragment.this).commit();
        }

        @Override
        protected String doInBackground(String... params) {

            String serverURL = (String)params[0];
            String nickname = (String)params[1];

            String postParameters = "nickname=" + nickname;

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
