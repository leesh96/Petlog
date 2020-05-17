package swpj.petlog.petlog2;

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

public class MypetMainActivity extends AppCompatActivity {
    private AlertDialog dialog;
    private ImageButton imgbtn_home, imgbtn_mypetmenu;

    private static String PHPURL = "http://128.199.106.86/showMypet.php";
    private static String TAG = "getmypet";
    private String jsonString;
    private ArrayList<MypetData> arrayList;
    private MypetAdapter mypetAdapter;
    private RecyclerView recyclerViewMypet;

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

        imgbtn_mypetmenu = (ImageButton) findViewById(R.id.btn_mypetmenu);
        imgbtn_mypetmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context wrapper = new ContextThemeWrapper(MypetMainActivity.this, R.style.mypetmenustyle);
                PopupMenu popupMenu = new PopupMenu(MypetMainActivity.this, v);
                MenuInflater inflater = popupMenu.getMenuInflater();
                Menu menu = popupMenu.getMenu();

                inflater.inflate(R.menu.mypet_menu, menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.mypetadd:
                                Intent intent = new Intent(MypetMainActivity.this, AddMypetInfoActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.mypetmodify:
                                break;
                            case R.id.mypetdelete:
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        recyclerViewMypet = (RecyclerView) findViewById(R.id.mypet_rcview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewMypet.setLayoutManager(layoutManager);

        arrayList = new ArrayList<>();

        mypetAdapter = new MypetAdapter(this, arrayList);
        recyclerViewMypet.setAdapter(mypetAdapter);
        arrayList.clear();
        mypetAdapter.notifyDataSetChanged();

        GetData task = new GetData();
        task.execute(PHPURL, userID);

        mypetAdapter.setOnItemClickListener(new MypetAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                MypetData mypetData = arrayList.get(position);

                Intent intent = new Intent(MypetMainActivity.this, ShowMypetActivity.class );
                intent.putExtra("petid", mypetData.getMember_id());
                intent.putExtra("petname", mypetData.getMember_name());
                intent.putExtra("petsex", mypetData.getMember_sex());
                intent.putExtra("petspecie", mypetData.getMember_specie());
                intent.putExtra("petage", mypetData.getMember_age());
                intent.putExtra("petbday", mypetData.getMember_bday());
                startActivity(intent);
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

                MypetData mypetData = new MypetData();

                mypetData.setMember_id(id);
                mypetData.setMember_name(Name);
                mypetData.setMember_sex(Sex);
                mypetData.setMember_specie(Specie);
                mypetData.setMember_age(Age);
                mypetData.setMember_bday(Bday);

                arrayList.add(mypetData);
                mypetAdapter.notifyDataSetChanged();

            }

        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }
    }
}
