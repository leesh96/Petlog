package com.swp.petlog.app;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.swp.petlog.PreferenceManager;
import com.swp.petlog.R;

public class FindpwActivity extends AppCompatActivity {
    private Button btn_getvn;
    private ImageButton btn_back;
    private EditText et_userIdforPw, et_verifynum;
    private String code = SendMail.getCode();
    private boolean check = false;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_findpassword);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitDiskReads().permitDiskWrites().permitNetwork().build());

        et_userIdforPw = findViewById(R.id.et_userIDforPw);
        et_verifynum = findViewById(R.id.et_verifynum);
        btn_getvn = findViewById(R.id.btn_getvn);
        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnclickevent();
    }

    private void btnclickevent() {
        btn_getvn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Id = et_userIdforPw.getText().toString();
                final SendMail mailServer = new SendMail();
                mailServer.sendSecurityCode(getApplicationContext(), Id);
                btn_getvn.setText("인증하기");

                btn_getvn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String VerifyNum = et_verifynum.getText().toString();
                        if (VerifyNum.equals("")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(FindpwActivity.this);
                            dialog = builder.setMessage("인증번호를 입력하세요.").setPositiveButton("확인", null).create();
                            dialog.show();
                        } else {
                            if(code.equals(VerifyNum)) {
                                check = true;
                                AlertDialog.Builder builder = new AlertDialog.Builder(FindpwActivity.this);
                                dialog = builder.setMessage("인증번호가 맞습니다.").setPositiveButton("확인", null).create();
                                dialog.show();
                                et_verifynum.setText("인증완료");
                                et_verifynum.setEnabled(false);
                                btn_getvn.setText("비밀번호 변경하기");
                                btn_getvn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        PreferenceManager.setString(FindpwActivity.this, "findpwID", Id);
                                        Intent intent = new Intent(FindpwActivity.this, ResetpwActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(FindpwActivity.this);
                                dialog = builder.setMessage("인증번호가 틀립니다!").setPositiveButton("확인", null).create();
                                dialog.show();
                                et_verifynum.setText(null);
                                btn_getvn.setText("인증번호 다시받기");
                                btnclickevent();
                            }
                        }
                    }
                });
            }
        });
    }
}
