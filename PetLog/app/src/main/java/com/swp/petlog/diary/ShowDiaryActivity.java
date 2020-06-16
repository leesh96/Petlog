package com.swp.petlog.diary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.swp.petlog.MainActivity;
import com.swp.petlog.R;

public class ShowDiaryActivity extends AppCompatActivity {
    private ImageButton imageButtonback, imageButtonhome, imageButtonModify;
    private TextView textViewdate, textViewtitle, textViewcontents;
    private ImageView imageViewmood, imageViewpic;
    private boolean isModify = false;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_show);

        imageButtonModify = (ImageButton) findViewById(R.id.btn_dairymodify);
        imageButtonback = (ImageButton) findViewById(R.id.btn_back);
        imageButtonhome = (ImageButton) findViewById(R.id.btn_home);
        textViewdate = (TextView) findViewById(R.id.diary_date);
        textViewtitle = (TextView) findViewById(R.id.diary_title);
        textViewcontents = (TextView) findViewById(R.id.diary_content);
        imageViewmood = (ImageView) findViewById(R.id.diary_mood);
        imageViewpic = (ImageView) findViewById(R.id.diary_pic);

        final int getId = getIntent().getIntExtra("diaryid", 1);
        final String getTitle = getIntent().getStringExtra("diarytitle");
        final String getContents = getIntent().getStringExtra("diarycontents");
        final String getDate = getIntent().getStringExtra("diarydate");
        final int getMood = getIntent().getIntExtra("diarymood", 0);
        final String getImgurl = getIntent().getStringExtra("diaryimg");

        Glide.with(ShowDiaryActivity.this).load(getImgurl).into(imageViewpic);

        imageButtonhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowDiaryActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        imageButtonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (getMood == 0) {
            imageViewmood.setImageResource(R.drawable.ic_happy);
        }
        if (getMood == 1) {
            imageViewmood.setImageResource(R.drawable.ic_sad);
        }
        if (getMood == 2) {
            imageViewmood.setImageResource(R.drawable.ic_angry);
        }
        if (getMood == 3) {
            imageViewmood.setImageResource(R.drawable.ic_sick);
        }
        if (getMood == 4) {
            imageViewmood.setImageResource(R.drawable.ic_none);
        }
        textViewdate.setText(getDate);
        textViewtitle.setText("제목 : " + getTitle);
        textViewcontents.setText("내용 : " + getContents);


        imageButtonModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isModify = true;
                Intent intent = new Intent(ShowDiaryActivity.this, WriteDiaryActivity.class);
                intent.putExtra("m_diaryid", getId);
                intent.putExtra("m_diarytitle", getTitle);
                intent.putExtra("m_diarycontents", getContents);
                intent.putExtra("m_diarydate", getDate);
                intent.putExtra("m_diarymood", getMood);
                intent.putExtra("m_diaryPic", getImgurl);
                intent.putExtra("ismodify", isModify);
                startActivity(intent);
            }
        });
    }
}
