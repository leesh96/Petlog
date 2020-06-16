package com.swp.petlog.mypet;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.bumptech.glide.Glide;
import com.swp.petlog.R;

public class MypetPagerAdapter extends PagerAdapter {
    private Context mContext = null;
    private ArrayList<MypetData> mList = null;
    private String jsonString;
    private static String PHPURL = "http://128.199.106.86/mypetMemo.php";
    private static String dPHPURL= "http://128.199.106.86/deleteMemo.php";
    private static String TAG = "memo";

    public MypetPagerAdapter(Context mContext, ArrayList<MypetData> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = null;

        if (mContext != null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.mypet_showinfo, container, false);

            ImageView imageViewface = (ImageView) view.findViewById(R.id.petface);
            TextView textViewname = (TextView) view.findViewById(R.id.tv_petName);
            TextView textViewsex = (TextView) view.findViewById(R.id.tv_petSex);
            TextView textViewspecie = (TextView) view.findViewById(R.id.tv_petSpecie);
            TextView textViewage = (TextView) view.findViewById(R.id.tv_petAge);
            TextView textViewbday = (TextView) view.findViewById(R.id.tv_petBday);
            ImageButton imageButtonMemoApply = (ImageButton) view.findViewById(R.id.btn_memoapply);
            ImageButton imageButtonMemoDel = (ImageButton) view.findViewById(R.id.btn_memodel);
            final EditText editTextMemo = (EditText) view.findViewById(R.id.mypet_memo);

            Glide.with(mContext).load(mList.get(position).getMember_face()).into(imageViewface);
            textViewname.setText(mList.get(position).getMember_name());
            textViewsex.setText(mList.get(position).getMember_sex());
            textViewspecie.setText(mList.get(position).getMember_specie());
            textViewage.setText(mList.get(position).getMember_age());
            textViewbday.setText(mList.get(position).getMember_bday());
            editTextMemo.setText(mList.get(position).getMember_memo());
            int petid = mList.get(position).getMember_id();
            final String petId = Integer.toString(petid);

            imageButtonMemoApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String memo = editTextMemo.getText().toString();

                    InsertMemo task = new InsertMemo();
                    task.execute(PHPURL, petId, memo);
                }
            });

            imageButtonMemoDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editTextMemo.setText(null);
                    DeleteMemo task = new DeleteMemo();
                    task.execute(dPHPURL, petId);
                }
            });
        }

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // 뷰페이저에서 삭제.
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        // 전체 페이지 수는 10개로 고정.
        return (null != mList ? mList.size() : 0);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (View)object);
    }

    class InsertMemo extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(mContext,
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

            String petid = (String)params[1];
            String memo = (String)params[2];

            String serverURL = (String)params[0];

            String postParameters = "petid=" + petid + "&memo=" + memo;

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

    class DeleteMemo extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(mContext,
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

            String petid = (String)params[1];

            String serverURL = (String)params[0];
            String postParameters = "petid=" + petid;

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
