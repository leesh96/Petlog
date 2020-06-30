package com.swp.petlog;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.swp.petlog.app.LoginActivity;

public class LoadingActivity extends AppCompatActivity {
    private Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_launch);
        Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if(PreferenceManager.getString(LoadingActivity.this, "userID").length() == 0) {
                    intent = new Intent(LoadingActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    intent = new Intent(LoadingActivity.this, MainActivity.class);
                    intent.putExtra("STD_NUM", PreferenceManager.getString(LoadingActivity.this, "userID").toString());
                    startActivity(intent);
                    finish();
                }
            }
        };
        handler.sendEmptyMessageDelayed(0, 3000);
    }
}


