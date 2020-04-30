package com.example.test02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class WalkActivity extends AppCompatActivity {

    private ListView listView;
    private WalkListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);

        //Adapter생성

        adapter = new WalkListViewAdapter();

        //리스트뷰 객체 생성 및 adapter 설정
        listView =(ListView)findViewById(R.id.listview);
        listView.setAdapter(adapter);

        //리스트 뷰 아이템 추가.
        adapter.addItem();
        adapter.addItem();
        adapter.addItem();
        adapter.addItem();
        adapter.addItem();
        adapter.addItem();
        adapter.addItem();
        adapter.addItem();
        adapter.addItem();
        adapter.addItem();
        adapter.addItem();


    }
}
