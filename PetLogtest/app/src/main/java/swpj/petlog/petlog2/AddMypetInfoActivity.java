package swpj.petlog.petlog2;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.sun.mail.util.BASE64EncoderStream;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddMypetInfoActivity extends AppCompatActivity {
    private EditText editTextName, editTextSex, editTextSpecies, editTextAge, editTextBday;
    private Button btn_add;
    private AlertDialog dialog;
    private ImageView imageViewFace;
    private Bitmap bitmapFace;
    static Context mContext;
    static ProgressDialog pd;

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

        editTextName = (EditText) findViewById(R.id.et_petName);
        editTextSex = (EditText) findViewById(R.id.et_petSex);
        editTextSpecies = (EditText) findViewById(R.id.et_petSpecie);
        editTextAge = (EditText) findViewById(R.id.et_petAge);
        editTextBday = (EditText) findViewById(R.id.et_petBday);

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
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String PetName = editTextName.getText().toString();
                String PetSex = editTextSex.getText().toString();
                String PetSpecies = editTextSpecies.getText().toString();
                String PetAge = editTextAge.getText().toString();
                String PetBday = editTextBday.getText().toString();
                String PetFace = BitmapToString(bitmapFace);
                try {
                    PetFace = URLEncoder.encode(PetFace, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

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
                                Intent intent = new Intent(AddMypetInfoActivity.this, MainActivity.class);
                                startActivity(intent);
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
                AddMypetInfoRequest addMypetInfoRequest = new AddMypetInfoRequest(PetName, PetSex, PetSpecies, PetAge, PetBday, responseListener);
                RequestQueue queue = Volley.newRequestQueue(AddMypetInfoActivity.this);
                queue.add(addMypetInfoRequest);
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
        String temp = Base64.encodeToString(bytes, Base64.DEFAULT);
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

    static public void add_image(String result){   //이미지 추가 결과
        if(result!=null)
            Log.e("result",result);
        AddMypetInfoRequest.active=false;
        if(result.contains("true")){
            Toast.makeText(mContext, "이미지가 DB에 추가되었습니다..", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(mContext, result+" 이미지가 DB에 추가되지 못했습니다.", Toast.LENGTH_SHORT).show();
        }
        pd.cancel();
    }
}

