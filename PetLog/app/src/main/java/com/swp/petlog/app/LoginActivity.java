package com.swp.petlog.app;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import com.swp.petlog.MainActivity;
import com.swp.petlog.PreferenceManager;
import com.swp.petlog.R;

public class LoginActivity extends AppCompatActivity {
    private EditText et_email, et_pass;
    private Button btn_login, btn_register, btn_findpw;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_login);

        et_email = findViewById(R.id.et_email);
        et_pass = findViewById(R.id.et_pass);

        btn_register = findViewById(R.id.btn_signup);

        btn_register.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btn_findpw = findViewById(R.id.btn_findpw);

        btn_findpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, FindpwActivity.class);
                startActivity(intent);
            }
        });

        btn_login = findViewById(R.id.btn_signin);

        btn_login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String u_id = et_email.getText().toString();
                String u_pw = et_pass.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {//로그인 성공시
                                String u_id = jsonObject.getString("userID");
                                String u_pw = jsonObject.getString("userPassword");
                                String u_name = jsonObject.getString("userName");
                                String u_nickname = jsonObject.getString("userNick");
                                String profileimg = "http://128.199.106.86/" + jsonObject.getString("profileImg");

                                Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                PreferenceManager.setString(LoginActivity.this, "userID", u_id);
                                PreferenceManager.setString(LoginActivity.this, "userPass", u_pw);
                                PreferenceManager.setString(LoginActivity.this, "userName", u_name);
                                PreferenceManager.setString(LoginActivity.this, "userNick", u_nickname);
                                PreferenceManager.setString(LoginActivity.this, "userFace", profileimg);
                                finish();

                            } else {//로그인 실패시
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                dialog = builder.setMessage("로그인 실패!\n이메일과 비밀번호를 확인하세요.").setNegativeButton("확인", null).create();
                                dialog.show();
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(u_id, u_pw, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }
}
