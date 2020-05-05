package swpj.petlog.petlog2;

import android.annotation.SuppressLint;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

import androidx.annotation.NonNull;

public class LoadingActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
                finish();
            }
        };
        handler.sendEmptyMessageDelayed(0, 3000);
    }
}
