package swpj.petlog.petlog2.mypet;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.Locale;

import swpj.petlog.petlog2.PreferenceManager;
import swpj.petlog.petlog2.R;

public class AddMypetInfoActivity extends AppCompatActivity {
    private EditText editTextName, editTextSex, editTextSpecies, editTextAge, editTextBday;
    private Button btn_add;
    private AlertDialog dialog;
    private ImageView imageViewFace;
    private Bitmap bitmapFace;
    private ImageButton btn_back;
    private String image;
    private static String PHPURL = "http://128.199.106.86/modifyMypet.php";
    private static String TAG = "mypet";

    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        EditText et_date = (EditText) findViewById(R.id.et_petBday);
        et_date.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypet_registinfo);
        btn_back = (ImageButton) findViewById(R.id.btn_back);
        editTextName = (EditText) findViewById(R.id.et_petName);
        editTextSex = (EditText) findViewById(R.id.et_petSex);
        editTextSpecies = (EditText) findViewById(R.id.et_petSpecie);
        editTextAge = (EditText) findViewById(R.id.et_petAge);
        editTextBday = (EditText) findViewById(R.id.et_petBday);

        final String PetOwner = PreferenceManager.getString(AddMypetInfoActivity.this, "userID");

        final int getId = getIntent().getIntExtra("petId", 0);
        String getName = getIntent().getStringExtra("petName");
        String getSex = getIntent().getStringExtra("petSex");
        String getSpecie = getIntent().getStringExtra("petSpecie");
        String getAge = getIntent().getStringExtra("petAge");
        String getBday = getIntent().getStringExtra("petBday");
        final boolean isModify = getIntent().getBooleanExtra("ismodify", false);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(isModify) {
            editTextName.setText(getName);
            editTextSex.setText(getSex);
            editTextSpecies.setText(getSpecie);
            editTextAge.setText(getAge);
            editTextBday.setText(getBday);
        }

        editTextBday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddMypetInfoActivity.this, android.R.style.Theme_Holo_Light_Dialog, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        imageViewFace = (ImageView) findViewById(R.id.mypet_image);
        imageViewFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent load_image = new Intent();
                load_image.setType("image/*");
                load_image.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(load_image, 1);
            }
        });

        btn_add = (Button) findViewById(R.id.btn_regist);
        if(isModify){
            btn_add.setText("수정하기");
        }
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isModify) {
                    String PetName = editTextName.getText().toString();
                    String PetSex = editTextSex.getText().toString();
                    String PetSpecies = editTextSpecies.getText().toString();
                    String PetAge = editTextAge.getText().toString();
                    String PetBday = editTextBday.getText().toString();
                    String PetId = Integer.toString(getId);

                    ModifyData task = new ModifyData();
                    task.execute(PHPURL, PetName, PetSex, PetSpecies, PetAge, PetBday, PetId);

                    Intent intent = new Intent(AddMypetInfoActivity.this, MypetMainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    String PetName = editTextName.getText().toString();
                    String PetSex = editTextSex.getText().toString();
                    String PetSpecies = editTextSpecies.getText().toString();
                    String PetAge = editTextAge.getText().toString();
                    String PetBday = editTextBday.getText().toString();

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");

                                if (success) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(AddMypetInfoActivity.this);
                                    dialog = builder.setMessage("데이터 업로드 성공").setNegativeButton("확인", null).create();
                                    dialog.show();
                                    Intent intent = new Intent(AddMypetInfoActivity.this, MypetMainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(AddMypetInfoActivity.this);
                                    dialog = builder.setMessage("데이터 업로드 실패").setNegativeButton("확인", null).create();
                                    dialog.show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    AddMypetInfoRequest addMypetInfoRequest = new AddMypetInfoRequest(PetName, PetSex, PetSpecies, PetAge, PetBday, image, PetOwner, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(AddMypetInfoActivity.this);
                    queue.add(addMypetInfoRequest);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream is = getContentResolver().openInputStream(data.getData());
                    bitmapFace = BitmapFactory.decodeStream(is);
                    is.close();
                    bitmapFace = resize(bitmapFace);
                    imageViewFace.setImageBitmap(bitmapFace);
                    image = BitmapToString(bitmapFace);
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

    class ModifyData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(AddMypetInfoActivity.this,
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

            String name = (String)params[1];
            String sex = (String)params[2];
            String specie = (String)params[3];
            String age = (String)params[4];
            String bday = (String)params[5];
            String petid = (String)params[6];

            String serverURL = (String)params[0];
            String postParameters = "name=" + name + "&sex=" + sex + "&specie=" + specie + "&age=" + age + "&bday=" + bday + "&petid=" + petid;

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

