package com.swp.petlog.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.swp.petlog.R;

public class NoticeDetailActivity extends AppCompatActivity {
    private ImageButton btn_back;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);

        btn_back=(ImageButton)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(getApplicationContext(), NoticeActivity.class);
                startActivity(intent);
            }
        });

        String id="";
        String title="";
        String content="";
        String date="";
        String img="";

        Bundle extras = getIntent().getExtras();

        //여기에 값 지정 해주면 디테일 액티비티에 해당데이터 넘어감!!////
        id=extras.getString("id");
        title=extras.getString("title");
        content=extras.getString("content");
        date=extras.getString("date");



        TextView idView = (TextView) findViewById(R.id.id);
        TextView contentView = (TextView) findViewById(R.id.item_detail_content);
        TextView titleView =(TextView) findViewById(R.id.item_detail_title);
        TextView dateView = (TextView) findViewById(R.id.item_detail_date);


        //데이터 넘겨주는 값
        String idview=id; //나중엔 작성자로 바꿀예정
        String titleview = title; //제목
        String contentview=content; //내용
        String dateview=date;


        //클릭시 화면에 보여주는 곳//
        dateView.setText(dateview);
        titleView.setText(titleview);
        contentView.setText(contentview);

    }
}
