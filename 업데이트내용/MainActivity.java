package com.example.test02;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test02.ui.push.PushFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private Context context = this;
    private DrawerLayout drawer;
    private TextView user_email,user_nick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_push, R.id.nav_notice,R.id.change_info)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



        final String nickname= PreferenceManager.getString(MainActivity.this,"userNick");

        final String email= PreferenceManager.getString(MainActivity.this,"userID");

        //여기에 값 지정 해주면 디테일 액티비티에 해당데이터 넘어감!!////

         user_email =findViewById(R.id.user_email);
         user_nick =findViewById(R.id.user_nick);

        //데이터 넘겨주는 값
        String idview=email; //이메일
        String nicknameview=nickname;

        View header = navigationView.getHeaderView(0); //네비게이션 헤더불러오기
        //헤더 부분 로그인한 계정의 이메일과 닉네임 표시
        user_email = (TextView) header.findViewById(R.id.user_email);
        user_email.setText(nicknameview);
        user_nick = (TextView) header.findViewById(R.id.user_nick);
        user_nick.setText(idview);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                drawer.closeDrawers();

                int id = menuItem.getItemId();
                String title = menuItem.getTitle().toString();




                if(id == R.id.change_info){

                    Toast.makeText(context, title + ": 설정 정보를 확인합니다.", Toast.LENGTH_SHORT).show();
                }
                 else if(id==R.id.nav_push){
                     Intent intent=new Intent(MainActivity.this, PushActivity.class);
                     startActivity(intent);
                 }
                 else if(id==R.id.nav_notice){
                     Intent intent=new Intent(MainActivity.this, NoticeActivity.class);
                     startActivity(intent);
                 }
                else if(id == R.id.nav_logout){
                    Toast.makeText(context,  ": 로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                     Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                     startActivity(intent);
                }
                else if(id==R.id.nav_version){
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("PetLog Version")
                            .setMessage("1.v")
                            .setNeutralButton("닫기", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dlg, int sumthin) {
                                }
                            })
                            .show(); // 팝업창 보여줌
                }

                return true;
            }
        });
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false); // 기존 title 지우기
       // actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼 만들기
        //actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);


        //FIRE BASE
      /**  FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if(!task.isSuccessful()){
                            Log.w("FCM Log","getInstanceId failed", task.getException());
                            return;
                        }
                        String token = task.getResult().getToken();
                        Log.d("FCM Log","FCM 토큰: "+token);
                        Toast.makeText(MainActivity.this,token,Toast.LENGTH_SHORT).show();
                    }
                });*/
        //
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ // 왼쪽 상단 버튼 눌렀을 때
                drawer.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
