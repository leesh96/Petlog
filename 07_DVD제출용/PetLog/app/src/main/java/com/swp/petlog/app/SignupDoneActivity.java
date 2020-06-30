package com.swp.petlog.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.swp.petlog.PreferenceManager;
import com.swp.petlog.R;

public class SignupDoneActivity extends AppCompatActivity {
    private Button btn_gotologin;
    private TextView tv_15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_signupdone);

        tv_15 = (TextView) findViewById(R.id.tv15);
        tv_15.setText(PreferenceManager.getString(SignupDoneActivity.this, "signupNick")+"님 \n회원가입을 축하합니다!");

        btn_gotologin = findViewById(R.id.btn_gotologin);

        btn_gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupDoneActivity.this, LoginActivity.class);
                startActivity(intent);
                PreferenceManager.removeKey(SignupDoneActivity.this, "signupNick");
            }
        });
    }

}
