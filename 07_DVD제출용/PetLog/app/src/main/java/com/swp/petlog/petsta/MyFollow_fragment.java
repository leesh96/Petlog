package com.swp.petlog.petsta;

import android.app.ProgressDialog;
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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.swp.petlog.PreferenceManager;
import com.swp.petlog.R;
import com.swp.petlog.petsta.adapter.MyFollowAdapter;
import com.swp.petlog.petsta.data.MyFollowData;

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

public class MyFollow_fragment extends Fragment {
    private static String IP_ADDRESS = "128.199.106.86";
    private static String TAG = "getFollower_nickname";

    private RecyclerView myfollow_rv;
    private ArrayList<MyFollowData> arrayList;
    private MyFollowAdapter myFollowAdapter;
    private String jsonString;
    private ImageButton btn_back;

    public static MyFollow_fragment newInstance() {
        return new MyFollow_fragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.petsta_myfollow, container, false);

        final String nickname = PreferenceManager.getString(getActivity(), "userNick");

        btn_back = (ImageButton) v.findViewById(R.id.btn_back);

        myfollow_rv = (RecyclerView) v.findViewById(R.id.myfollow_rv);
        myfollow_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        myfollow_rv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        arrayList = new ArrayList<>();
        myFollowAdapter = new MyFollowAdapter(getActivity(), arrayList);
        myfollow_rv.setAdapter(myFollowAdapter);
        arrayList.clear();
        myFollowAdapter.notifyDataSetChanged();

        GetData task = new GetData();
        task.execute("http://" + IP_ADDRESS + "/getfollow.php", nickname);

        myFollowAdapter.setOnItemClickListener(new MyFollowAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                MyFollowData myFollowData = arrayList.get(pos);

                Fragment fragment = new UserProfile_fragment();

                Bundle bundle = new Bundle();
                bundle.putString("nickname", myFollowData.getFollowerNickname());
                fragment.setArguments(bundle);

                ((PetstaMain)getActivity()).replaceFragment(fragment);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().remove(MyFollow_fragment.this).commit();
                fragmentManager.popBackStack();
                Log.d("count", Integer.toString(fragmentManager.getBackStackEntryCount()));
                if (fragmentManager.getBackStackEntryCount() == 0) {
                    getActivity().finish();
                }
            }
        });

        return v;
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

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "response - " + result);

            if (result == null) {

            } else {
                jsonString = result;
                showResult();
            }
        }

        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String nickname =params[1];

            String postParameters = "nickname="+nickname;

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

    private void showResult () {

        String TAG_JSON = "following";
        String TAG_FOLLOWER_NICKNAME = "follower_nickname";
        String TAG_FACE = "follower_face";

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                String follower_user = item.getString(TAG_FOLLOWER_NICKNAME);

                String follower_face = "http://128.199.106.86/" + item.getString(TAG_FACE);

                MyFollowData myFollowData = new MyFollowData(follower_user);

                myFollowData.setFollowerNickname(follower_user);
                myFollowData.setFollower_face(follower_face);

                arrayList.add(myFollowData);
                myFollowAdapter.notifyDataSetChanged();
            }

        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }
    }
}
