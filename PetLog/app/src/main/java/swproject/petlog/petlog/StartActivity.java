package swproject.petlog.petlog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

import androidx.annotation.NonNull;

public class StartActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                //startActivity(intent);
                startActivity(new Intent(StartActivity.this, LoginActivity.class));
                finish();
            }
        };
        handler.sendEmptyMessageDelayed(0, 3000);
        }
    }