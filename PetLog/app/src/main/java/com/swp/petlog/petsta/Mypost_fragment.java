package com.swp.petlog.petsta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.swp.petlog.MainActivity;
import com.swp.petlog.PreferenceManager;
import com.swp.petlog.R;
import com.swp.petlog.petsta.adapter.PetstaPostAdapter;
import com.swp.petlog.petsta.data.PetstaPostData;

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

public class Mypost_fragment extends Fragment {
    private View view;
    private static String PHPURL = "http://128.199.106.86/getMyPost.php";
    private static String TAG = "petstapost";

    private ImageButton btn_back, btn_search;

    private RecyclerView recyclerViewpetsta;
    private String jsonString;
    private ArrayList<PetstaPostData> arrayList;
    private PetstaPostAdapter adapter;

    private ArrayList<String> Follower;
    private String nickname;

    public static Mypost_fragment newInstance() {
        return new Mypost_fragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.petsta_mypost, container, false);
        btn_back = (ImageButton) view.findViewById(R.id.btn_back);
        btn_search = (ImageButton) view.findViewById(R.id.btn_search);
        btn_search.setVisibility(View.INVISIBLE);

        nickname = PreferenceManager.getString(getActivity(), "userNick");

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().remove(Mypost_fragment.this).commit();
                fragmentManager.popBackStack();
                Log.d("count", Integer.toString(fragmentManager.getBackStackEntryCount()));
                if (fragmentManager.getBackStackEntryCount() == 0) {
                    getActivity().finish();
                }
            }
        });

        recyclerViewpetsta = (RecyclerView) view.findViewById(R.id.myfeed_rcview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewpetsta.setLayoutManager(layoutManager);

        arrayList = new ArrayList<>();

        adapter = new PetstaPostAdapter(getActivity(), arrayList);
        recyclerViewpetsta.setAdapter(adapter);

        GetData task = new GetData();
        task.execute(PHPURL, nickname);

        adapter.setOnItemClickListener(new PetstaPostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                PetstaPostData petstaPostData = arrayList.get(pos);

                Fragment fragment = new ControlPost_fragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id", petstaPostData.getMember_id());
                bundle.putString("image", petstaPostData.getMember_image());
                bundle.putInt("tag", petstaPostData.getMember_tag());
                bundle.putString("content", petstaPostData.getMember_content());
                fragment.setArguments(bundle);

                ((PetstaMain)getActivity()).replaceFragment(fragment);
            }
        });

        return view;
    }

    private class GetData extends AsyncTask<String, Void, String> {
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
            String nickname = params[1];
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
        String TAG_JSON = "mypost";
        String TAG_ID = "id";
        String TAG_NICK = "nickname";
        String TAG_IMG = "imgurl";
        String TAG_CON = "contents";
        String TAG_TAG = "tag";
        String TAG_TIME = "time";
        String TAG_LIKE = "likecnt";
        String TAG_COMMENT = "commentcnt";
        String TAG_FACE = "writerface";
        String TAG_ISLIKE = "islike";

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                int id = Integer.parseInt(item.getString(TAG_ID));
                String nickname = item.getString(TAG_NICK);
                String imgurl = "http://128.199.106.86/" + item.getString(TAG_IMG);
                String contents = item.getString(TAG_CON);
                int tag = Integer.parseInt(item.getString(TAG_TAG));
                String writetime = item.getString(TAG_TIME);
                int likecnt = Integer.parseInt(item.getString(TAG_LIKE));
                int commentcnt = Integer.parseInt(item.getString(TAG_COMMENT));
                String writerface = "http://128.199.106.86/" + item.getString(TAG_FACE);
                String islike = item.getString(TAG_ISLIKE);
                int userliked;

                Log.d("userliked", islike);

                if (islike.equals("1")) {
                    userliked = 1;
                } else if (islike.equals("0")) {
                    userliked = 0;
                } else {
                    userliked = 2;
                }

                PetstaPostData petstaPostData = new PetstaPostData();

                petstaPostData.setMember_id(id);
                petstaPostData.setMember_nickname(nickname);
                petstaPostData.setMember_image(imgurl);
                petstaPostData.setMember_content(contents);
                petstaPostData.setMember_tag(tag);
                petstaPostData.setMember_writetime(writetime);
                petstaPostData.setMember_likecnt(likecnt);
                petstaPostData.setMember_commentcnt(commentcnt);
                petstaPostData.setMember_face(writerface);
                petstaPostData.setMember_liked(userliked);

                arrayList.add(petstaPostData);
                adapter.notifyDataSetChanged();

            }

        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }
    }
}
