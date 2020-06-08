package com.example.test02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.kyleduo.switchbutton.SwitchButton;

public class PushActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);

        @SuppressLint("WrongViewCast")
        SwitchButton switchButton = (SwitchButton) findViewById(R.id.push_comment);
        //switchButton.setOnCh

            NotificationCompat.Builder mBilder= new NotificationCompat.Builder(PushActivity.this)
                .setSmallIcon(R.drawable.ic_logo2)
                .setContentTitle("알림제목")
                .setContentText("알림 내용")
                .setDefaults(Notification.DEFAULT_VIBRATE)
                //.setLargeIcon(mLargeIconForNoti)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManager mNotificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.notify(0,mBilder.build());
            };
    }

