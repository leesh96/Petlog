package swpj.petlog.petlog2.mypet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

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

import swpj.petlog.petlog2.MainActivity;
import swpj.petlog.petlog2.PreferenceManager;
import swpj.petlog.petlog2.R;

public class MypetMainActivity extends AppCompatActivity {
    private AlertDialog dialog;
    private ImageButton imgbtn_home, imgbtn_mypetmenu;

    private static String PHPURL = "http://128.199.106.86/showMypet.php";
    private static String TAG = "mypet";
    private static String dPHPURL = "http://128.199.106.86/deleteMypet.php";
    private String jsonString;
    private ArrayList<MypetData> arrayList;

    private ViewPager viewPager;
    private MypetPagerAdapter pagerAdapter;

    private boolean isModify = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypet_main);

        final String userID = PreferenceManager.getString(MypetMainActivity.this, "userID");

        imgbtn_home = (ImageButton) findViewById(R.id.btn_home);
        imgbtn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MypetMainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        viewPager = (ViewPager) findViewById(R.id.vp_mypet);
        arrayList = new ArrayList<>();
        pagerAdapter = new MypetPagerAdapter(this, arrayList);
        viewPager.setAdapter(pagerAdapter);
        arrayList.clear();
        pagerAdapter.notifyDataSetChanged();

        GetData task = new GetData();
        task.execute(PHPURL, userID);

        imgbtn_mypetmenu = (ImageButton) findViewById(R.id.btn_mypetmenu);
        imgbtn_mypetmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context wrapper = new ContextThemeWrapper(MypetMainActivity.this, R.style.mypetmenustyle);
                PopupMenu popupMenu = new PopupMenu(MypetMainActivity.this, v);
                final MenuInflater inflater = popupMenu.getMenuInflater();
                Menu menu = popupMenu.getMenu();

                inflater.inflate(R.menu.mypet_menu, menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.mypetadd:
                                Intent intent1 = new Intent(MypetMainActivity.this, AddMypetInfoActivity.class);
                                startActivity(intent1);
                                break;
                            case R.id.mypetmodify:
                                isModify = true;

                                int curitem = viewPager.getCurrentItem();
                                MypetData mypetData = arrayList.get(curitem);

                                Intent intent2 = new Intent(MypetMainActivity.this, AddMypetInfoActivity.class);
                                intent2.putExtra("petId", mypetData.getMember_id());
                                intent2.putExtra("petName", mypetData.getMember_name());
                                intent2.putExtra("petSex", mypetData.getMember_sex());
                                intent2.putExtra("petSpecie", mypetData.getMember_specie());
                                intent2.putExtra("petAge", mypetData.getMember_age());
                                intent2.putExtra("petBday", mypetData.getMember_bday());
                                intent2.putExtra("ismodify", isModify);
                                startActivity(intent2);
                                break;
                            case R.id.mypetdelete:
                                int delitem = viewPager.getCurrentItem();
                                MypetData delData = arrayList.get(delitem);
                                String delId = Integer.toString(delData.getMember_id());

                                DeleteData task = new DeleteData();
                                task.execute(dPHPURL, delId);

                                finish();
                                startActivity(getIntent());
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

    }

    public static Bitmap StringToBitMap(String image){
        try{
            byte [] encodeByte= Base64.decode(image,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MypetMainActivity.this,
                    "Please Wait", null, true, true);
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

        String TAG_JSON = "mypet";
        String TAG_ID = "id";
        String TAG_NAME = "name";
        String TAG_SEX = "sex";
        String TAG_SPECIE = "specie";
        String TAG_AGE = "age";
        String TAG_BDAY = "bday";
        String TAG_MEMO = "memo";

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                int id = Integer.parseInt(item.getString(TAG_ID));
                String Name = item.getString(TAG_NAME);
                String Sex = item.getString(TAG_SEX);
                String Specie = item.getString(TAG_SPECIE);
                String Age = item.getString(TAG_AGE);
                String Bday = item.getString(TAG_BDAY);
                String Memo = null;
                if (item.getString(TAG_MEMO) != "null") {
                    Memo = item.getString(TAG_MEMO);
                }

                MypetData mypetData = new MypetData();

                mypetData.setMember_id(id);
                mypetData.setMember_name(Name);
                mypetData.setMember_sex(Sex);
                mypetData.setMember_specie(Specie);
                mypetData.setMember_age(Age);
                mypetData.setMember_bday(Bday);
                mypetData.setMember_memo(Memo);

                arrayList.add(mypetData);

                pagerAdapter.notifyDataSetChanged();
            }

        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }
    }

    class DeleteData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MypetMainActivity.this,
                    "Please Wait", null, true, true);
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