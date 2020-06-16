package com.swp.petlog.app;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.swp.petlog.PreferenceManager;
import com.swp.petlog.R;

public class RegisterActivity extends AppCompatActivity {
    private EditText editTextId;
    private EditText editTextPw;
    private EditText editTextName;
    private EditText editTextNick;
    private EditText editTextBdy;
    private EditText pwcheck;
    private Button btn_submit;
    private Button btn_validateid;
    private Button btn_validatenick;
    private AlertDialog dialog;
    private boolean validateid = false;
    private boolean validatenick = false;
    private ImageButton btn_back;

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

        EditText et_date = (EditText) findViewById(R.id.et_userBdy);
        et_date.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_signup);

        editTextId = (EditText) findViewById(R.id.et_userID);
        editTextPw = (EditText) findViewById(R.id.et_userPassword);
        editTextName = (EditText) findViewById(R.id.et_userName);
        editTextNick = (EditText) findViewById(R.id.et_userNickname);
        editTextBdy = (EditText) findViewById(R.id.et_userBdy);
        pwcheck = (EditText) findViewById(R.id.et_pwCheck);
        btn_back = (ImageButton) findViewById(R.id.btn_back);
        btn_validateid = findViewById(R.id.btn_checkemail);
        btn_validatenick = findViewById(R.id.btn_checknick);
        btn_submit = findViewById(R.id.btn_submit);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_validateid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Id = editTextId.getText().toString();
                if (validateid)
                    return;
                if (Id.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("아이디를 입력하세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("가입할 수 있는 아이디입니다.").setPositiveButton("확인", null).create();
                                dialog.show();
                                editTextId.setEnabled(false);
                                validateid = true;
                                btn_validateid.setText("완료");
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("가입할 수 없는 아이디입니다!").setPositiveButton("확인", null).create();
                                dialog.show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequestId validateRequest = new ValidateRequestId(Id, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);
            }
        });

        btn_validatenick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Nick = editTextNick.getText().toString();
                if (validatenick)
                    return;
                if (Nick.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("닉네임을 입력하세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("사용할 수 있는 닉네임입니다.").setPositiveButton("확인", null).create();
                                dialog.show();
                                editTextNick.setEnabled(false);
                                validatenick = true;
                                btn_validatenick.setText("완료");
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("사용할 수 없는 닉네임입니다!").setPositiveButton("확인", null).create();
                                dialog.show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequestNick validateRequest = new ValidateRequestNick(Nick, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);
            }
        });

        editTextBdy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RegisterActivity.this, android.R.style.Theme_Holo_Light_Dialog, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Id = editTextId.getText().toString();
                final String Pw = editTextPw.getText().toString();
                final String Name = editTextName.getText().toString();
                final String Nick = editTextNick.getText().toString();
                final String Bdy = editTextBdy.getText().toString();
                final String Pwcheck = pwcheck.getText().toString();

                if (Id.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("이메일을 입력하세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                } else if (Nick.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("닉네임을 입력하세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                } else if (Name.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("이름을 입력하세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                } else if (Bdy.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("생년월일을 입력하세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                } else if (Pw.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("비밀번호를 입력하세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                } else if (Pwcheck.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("비밀번호확인을 입력하세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                }
                else {
                    if(validateid & validatenick) {
                        if (Pw.equals(Pwcheck)) {
                            insertToDatabase(Id, Pw, Name, Nick, Bdy);
                            PreferenceManager.setString(RegisterActivity.this, "signupNick", Nick);
                            Intent intent = new Intent(RegisterActivity.this, SignupDoneActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                            dialog = builder.setMessage("비밀번호 확인이 일치하지 않습니다!").setNegativeButton("확인", null).create();
                            dialog.show();
                            return;
                        }
                    } else if ((validateid == false) & (validatenick == true)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        dialog = builder.setMessage("아이디 중복확인을 해주세요!").setNegativeButton("확인", null).create();
                        dialog.show();
                        return;
                    } else if ((validatenick == false) & (validateid == true)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        dialog = builder.setMessage("닉네임 중복확인을 해주세요!").setNegativeButton("확인", null).create();
                        dialog.show();
                        return;
                    } else if ((validatenick == false) & (validateid == false)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        dialog = builder.setMessage("아이디, 닉네임 중복확인 필수!").setNegativeButton("확인", null).create();
                        dialog.show();
                        return;
                    }
                }
            }
        });
    }

    private void insertToDatabase(String Id, String Pw, String Name, String Nick, String Bdy) {
        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RegisterActivity.this, "Please Wait...", null, true, true);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
            }
            @Override
            protected String doInBackground(String... params) {

                try {
                    String Id = (String) params[0];
                    String Pw = (String) params[1];
                    String Name = (String) params[2];
                    String Nick = (String) params[3];
                    String Bdy = (String) params[4];

                    String link = "http://128.199.106.86/SignUp.php";
                    String data = URLEncoder.encode("Id", "UTF-8") + "=" + URLEncoder.encode(Id, "UTF-8");
                    data += "&" + URLEncoder.encode("Pw", "UTF-8") + "=" + URLEncoder.encode(Pw, "UTF-8");
                    data += "&" + URLEncoder.encode("Name", "UTF-8") + "=" + URLEncoder.encode(Name, "UTF-8");
                    data += "&" + URLEncoder.encode("Nick", "UTF-8") + "=" + URLEncoder.encode(Nick, "UTF-8");
                    data += "&" + URLEncoder.encode("Bdy", "UTF-8") + "=" + URLEncoder.encode(Bdy, "UTF-8");

                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();
                } catch (Exception e) {
                    return new String("Exception: " + e.getMessage());
                }
            }
        }
        InsertData task = new InsertData();
        task.execute(Id, Pw, Name, Nick, Bdy);
    }
}