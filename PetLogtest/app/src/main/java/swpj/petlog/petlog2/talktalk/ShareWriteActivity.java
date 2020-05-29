package swpj.petlog.petlog2.talktalk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import swpj.petlog.petlog2.PreferenceManager;
import swpj.petlog.petlog2.R;

public class ShareWriteActivity extends AppCompatActivity {

    private static String IP_ADDRESS = "128.199.106.86";
    private static String TAG = "test02";
    //private static String mPHPURL="http://128.199.106.86/";
    private EditText mEditTextTitle;
    private EditText mEditTextContent;
    private ImageView mImageView;
    private Bitmap bitmapshareimage;
    private String image;
    // private TextView mTextViewResult;
    /**  //20.05.11 추가
     private ArrayList<ShareData> mArrayList;
     private ShareAdapter mAdapter;
     private RecyclerView mRecyclerView;
     private String mJsonString;
     **/
    Calendar calendar = Calendar.getInstance();

    int year = calendar.get(Calendar.YEAR);
    int month=calendar.get(Calendar.MONTH);
    int day= calendar.get(Calendar.DATE);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_write);

        mEditTextTitle = (EditText)findViewById(R.id.editText_main_title);
        mEditTextContent = (EditText)findViewById(R.id.editText_main_content);

        // mTextViewResult = (TextView)findViewById(R.id.textView_main_result);
        // mTextViewResult.setMovementMethod(new ScrollingMovementMethod());



         //20.05.22 추가*//
        mImageView =(ImageView) findViewById(R.id.btn_share_image);
        mImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent load_image = new Intent();
                load_image.setType("image/*");
                load_image.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(load_image, 1);
            }
        });


        /////////////////////////
        ImageButton buttonBack =(ImageButton)findViewById(R.id.btn_back);
        buttonBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){ //쓰기화면에서 뒤로가기버튼클릭시 나눔게시판메인으로이동
                Intent intent=new Intent(getApplicationContext(),ShareActivity.class);
                startActivity(intent);
            }
        });
        final String nickname= PreferenceManager.getString(ShareWriteActivity.this,"userNick");

        Button buttonInsert = (Button)findViewById(R.id.board_insert);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = mEditTextTitle.getText().toString();
                String content = mEditTextContent.getText().toString();

                InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS + "/shareinsert.php",title,content,nickname); //
                //task.execute("http://" + IP_ADDRESS + "/shareinsert.php",title,content); // 디비에 집어너음

                Intent intent=new Intent(getApplicationContext(),ShareActivity.class);
                startActivity(intent);

                mEditTextTitle.setText("");
                mEditTextContent.setText("");

            }
        });
    }
    /////비트맵   uri->bitmap->file

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream is = getContentResolver().openInputStream(data.getData());
                    bitmapshareimage = BitmapFactory.decodeStream(is);
                    is.close();
                    bitmapshareimage = resize(bitmapshareimage);
                    mImageView.setImageBitmap(bitmapshareimage);
                    image = BitmapToString(bitmapshareimage);
                    try {
                        image = URLEncoder.encode(image, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String BitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();
        String temp= Base64.encodeToString(bytes, Base64.DEFAULT);

        return temp;
    }

    private Bitmap resize(Bitmap bm){
        Configuration config=getResources().getConfiguration();
        if(config.smallestScreenWidthDp>=600)
            bm = Bitmap.createScaledBitmap(bm, 300, 180, true);
        else if(config.smallestScreenWidthDp>=400)
            bm = Bitmap.createScaledBitmap(bm, 200, 120, true);
        else if(config.smallestScreenWidthDp>=360)
            bm = Bitmap.createScaledBitmap(bm, 180, 108, true);
        else
            bm = Bitmap.createScaledBitmap(bm, 160, 96, true);
        return bm;
    }
    public static Bitmap StringToBitMap(String image){
        Log.e("StringToBitMap","StringToBitMap");
        try{
            byte [] encodeByte=Base64.decode(image,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            Log.e("StringToBitMap","good");
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }
    /**
    public Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(uri,"r");
        FileDescriptor fileDescriptor=parcelFileDescriptor.getFileDescriptor();
        Bitmap image= BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }


    public File createFileFromBitmap(Bitmap bitmap) throws IOException{
        File newFile = new File(getFilesDir(), makeImageFilePath());
        FileOutputStream fileOutputStream=new FileOutputStream(newFile);

        bitmap.compress(Bitmap.CompressFormat.PNG,100, fileOutputStream);
        fileOutputStream.close();
        return newFile;
    }**/
    public static String makeImageFilePath(){

        long now = System.currentTimeMillis();
        // 현재시간을 date 변수에 저장한다.
        Date date = new Date(now);
        // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        // nowDate 변수에 값을 저장한다.
        String formatDate = sdfNow.format(date);
        return formatDate+".png";
    }

    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(ShareWriteActivity.this,
                    "Please Wait", null, true, true);
        }



        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            //    mTextViewResult.setText(result);
            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String title = (String)params[1];
            String content = (String)params[2];
            String nickname = (String)params[3];
            //String image=(String)params[4];
            //String shareid=(String)params[4];
            String serverURL = (String)params[0];
            String postParameters = "title=" + title + "&content=" + content +"&nickname=" +nickname; //유저정보닉네임 외래키로 받아서 넣음!


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