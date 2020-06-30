package com.swp.petlog.setting;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.swp.petlog.PreferenceManager;
import com.swp.petlog.R;

public class PushActivity extends AppCompatActivity {
    private Switch switchComment, switchLike, switchFollow, switchDiary, switchBday;
    private ImageButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_pushalert);

        String nickname = PreferenceManager.getString(PushActivity.this, "userNick");

        switchComment = (Switch) findViewById(R.id.push_comment);
        switchLike = (Switch) findViewById(R.id.push_like);
        switchFollow = (Switch) findViewById(R.id.push_follow);
        switchDiary = (Switch) findViewById(R.id.push_diary);
        switchBday = (Switch) findViewById(R.id.push_bdy);
        btn_back = (ImageButton) findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        switchComment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(PushActivity.this, "댓글알림On", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(PushActivity.this, "댓글알림Off", Toast.LENGTH_SHORT).show();
                }
            }
        });

        switchLike.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(PushActivity.this, "좋아요알림On", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(PushActivity.this, "좋아요알림Off", Toast.LENGTH_SHORT).show();
                }
            }
        });

        switchFollow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(PushActivity.this, "구독알림On", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(PushActivity.this, "구독알림Off", Toast.LENGTH_SHORT).show();
                }
            }
        });

        switchDiary.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(PushActivity.this, "일기리마인더On", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(PushActivity.this, "일기리마인더Off", Toast.LENGTH_SHORT).show();
                }
            }
        });

        switchBday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(PushActivity.this, "반려동물생일알림On", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(PushActivity.this, "반려동물생일알림Off", Toast.LENGTH_SHORT).show();
                }
            }
        });

    };
}