package com.swp.petlog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.swp.petlog.setting.ChangeInfoActivity;
import com.swp.petlog.app.LoginActivity;
import com.swp.petlog.setting.NoticeActivity;
import com.swp.petlog.setting.PushActivity;
import com.swp.petlog.diary.DiaryListActivity;
import com.swp.petlog.mypet.MypetMainActivity;
import com.swp.petlog.petsta.PetstaMain;
import com.swp.petlog.talktalk.TalktalkActivity;

public class MainActivity extends AppCompatActivity {
    private ImageButton imgbtn_mypet, imgbtn_dairy, imgbtn_petsta, imgbtn_talktalk, imgbtn_setting;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_main);

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog);

        dialog.setMessage("현재 버전은 V 1.0 입니다.") // 버전업데이트 할때마다 수정
                .setTitle("버전 정보")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setCancelable(false)
                .create();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.

        imgbtn_setting = (ImageButton) findViewById(R.id.btn_setting);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);

        TextView textViewNick = (TextView) header.findViewById(R.id.tv_nickname);
        textViewNick.setText(PreferenceManager.getString(MainActivity.this, "userNick"));

        TextView textViewEmail = (TextView) header.findViewById(R.id.tv_email);
        textViewEmail.setText(PreferenceManager.getString(MainActivity.this, "userID"));

        ImageView imageViewFace = (ImageView) header.findViewById(R.id.nav_profile);

        if (!PreferenceManager.getString(MainActivity.this, "userFace").equals("http://128.199.106.86/null")) {
            Glide.with(MainActivity.this).load(PreferenceManager.getString(MainActivity.this, "userFace")).into(imageViewFace);
        }


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                int id = menuItem.getItemId();
                String title = menuItem.getTitle().toString();

                switch (id) {
                    case R.id.version:
                        dialog.show();
                        menuItem.setChecked(false);
                        break;
                    case R.id.noti:
                        Intent intent = new Intent(MainActivity.this, NoticeActivity.class);
                        startActivity(intent);
                        menuItem.setChecked(false);
                        break;
                    case R.id.setalert:
                        Intent intent2 = new Intent(MainActivity.this, PushActivity.class);
                        startActivity(intent2);
                        menuItem.setChecked(false);
                        break;
                    case R.id.account:
                        Intent intent3 = new Intent(MainActivity.this, ChangeInfoActivity.class);
                        startActivity(intent3);
                        menuItem.setChecked(false);
                        break;
                    case R.id.logout:
                        Intent intent4 = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent4);
                        PreferenceManager.clear(MainActivity.this);
                        finish();
                        menuItem.setChecked(false);
                        break;
                }

                return true;
            }
        });

        imgbtn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        imgbtn_mypet = (ImageButton) findViewById(R.id.btn_mypetin);
        imgbtn_mypet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MypetMainActivity.class);
                startActivity(intent);
            }
        });

        imgbtn_dairy = (ImageButton) findViewById(R.id.btn_diaryin);
        imgbtn_dairy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DiaryListActivity.class);
                startActivity(intent);
            }
        });

        imgbtn_petsta = (ImageButton) findViewById(R.id.btn_petstain);
        imgbtn_petsta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PetstaMain.class);
                startActivity(intent);
            }
        });

        imgbtn_talktalk = (ImageButton) findViewById(R.id.btn_talktalkin);
        imgbtn_talktalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TalktalkActivity.class);
                startActivity(intent);
            }
        });
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ // 왼쪽 상단 버튼 눌렀을 때
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }*/
}
