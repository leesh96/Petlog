package swpj.petlog.petlog2;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class WriteDiaryActivity extends AppCompatActivity {
    private static String PHPURL = "http://128.199.106.86/writeDiary.php";
    private static String TAG = "diary";

    private ImageView imageViewMood, imageViewPic;
    private ImageButton imageButtonback, imageButtonhome, imageButtonUpload;
    private EditText editTextTitle, editTextContent;
    private TextView textViewDate;
    private AlertDialog dialog;
    private String[] mood = {"기쁨", "슬픔", "화남", "아픔", "무표정"};
    public int inputmood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_write);

        imageViewMood = (ImageView) findViewById(R.id.diary_inputmood);
        imageViewPic = (ImageView) findViewById(R.id.diary_inputpic);
        editTextTitle = (EditText) findViewById(R.id.diary_ettitle);
        editTextContent = (EditText) findViewById(R.id.diary_etcontent);
        textViewDate = (TextView) findViewById(R.id.diary_showdate);
        imageButtonUpload = (ImageButton) findViewById(R.id.btn_dairy_upload);
        imageButtonback = (ImageButton) findViewById(R.id.btn_back);
        imageButtonhome = (ImageButton) findViewById(R.id.btn_home);

        imageButtonhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WriteDiaryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        imageButtonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);
        textViewDate.setText(simpleDateFormat.format(currentTime));

        imageViewMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        dialog = new AlertDialog.Builder(WriteDiaryActivity.this)
                .setItems(mood, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            imageViewMood.setImageResource(R.drawable.ic_happy);
                            inputmood = 0;
                        }
                        if (which == 1) {
                            imageViewMood.setImageResource(R.drawable.ic_sad);
                            inputmood = 1;
                        }
                        if (which == 2) {
                            imageViewMood.setImageResource(R.drawable.ic_angry);
                            inputmood = 2;
                        }
                        if (which == 3) {
                            imageViewMood.setImageResource(R.drawable.ic_sick);
                            inputmood = 3;
                        }
                        if (which == 4) {
                            imageViewMood.setImageResource(R.drawable.ic_none);
                            inputmood = 4;
                        }
                    }
                })
                .setTitle("감정선택")
                .setPositiveButton("확인", null)
                .setNegativeButton("취소", null)
                .create();

        imageButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTitle.getText().toString();
                String contents = editTextContent.getText().toString();
                String userid = PreferenceManager.getString(WriteDiaryActivity.this, "userID");
                String writedate = textViewDate.getText().toString();
                String mood = Integer.toString(inputmood);

                InsertData task = new InsertData();
                task.execute(PHPURL, title, contents, userid, writedate, mood);

                Intent intent = new Intent(WriteDiaryActivity.this, DiaryListActivity.class);
                startActivity(intent);
            }
        });
    }

    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(WriteDiaryActivity.this,
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

            String title = (String)params[1];
            String contents = (String)params[2];
            String userid = (String)params[3];
            String writedate = (String)params[4];
            String mood = (String)params[5];

            String serverURL = (String)params[0];
            String postParameters = "title=" + title + "&contents=" + contents + "&userid=" + userid + "&writedate=" + writedate + "&mood=" + mood;

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
