package com.swp.petlog.setting;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.swp.petlog.PreferenceManager;
import com.swp.petlog.R;

public class PushActivity extends AppCompatActivity {
    private Switch switchComment, switchLike, switchFollow, switchDiary, switchBday;

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

        switchComment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(PushActivity.this, "On", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(PushActivity.this, "Off", Toast.LENGTH_SHORT).show();
                }
            }
        });

        switchLike.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(PushActivity.this, "On", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(PushActivity.this, "Off", Toast.LENGTH_SHORT).show();
                }
            }
        });

        switchFollow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(PushActivity.this, "On", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(PushActivity.this, "Off", Toast.LENGTH_SHORT).show();
                }
            }
        });

        switchDiary.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(PushActivity.this, "On", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(PushActivity.this, "Off", Toast.LENGTH_SHORT).show();
                }
            }
        });

        switchBday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(PushActivity.this, "On", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(PushActivity.this, "Off", Toast.LENGTH_SHORT).show();
                }
            }
        });

    };
}