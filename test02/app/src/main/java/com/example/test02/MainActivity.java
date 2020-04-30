package com.example.test02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    //네비게이션바
    private AppBarConfiguration mAppBarConfiguration;
    Button bt;
    NotificationManager manager;
    NotificationCompat.Builder builder;
    private static String CHANNEL_ID = "channel1";
    private static String CHANEL_NAME = "Channel1";
   //네비게이션바 설정끝

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.d("ACTIVITY_LC","onCreate호출");
        Toast.makeText(getApplicationContext(), "onCreate호출", Toast.LENGTH_SHORT).show();
//버튼클릭시 화면이동 코드~~~~~~
        Button talktalkbutton=(Button)findViewById(R.id.talktalkButton);
        Button diarybutton=(Button)findViewById(R.id.diaryButton);
        Button mypetbutton=(Button)findViewById(R.id.mypetButton);
        Button petstarbutton=(Button)findViewById(R.id.petstarButton);


        talktalkbutton.setOnClickListener(new View.OnClickListener(){ //톡톡화면으로 이동
            @Override
            public void onClick(View v){
                Intent intent=new Intent(getApplicationContext(),TalktalkActivity.class);
                startActivity(intent);
            }
        });
       diarybutton.setOnClickListener(new View.OnClickListener(){ //일기화면으로 이동
            @Override
            public void onClick(View v){
                Intent intent=new Intent(getApplicationContext(),DiaryActivity.class);
                startActivity(intent);
            }
        });
        mypetbutton.setOnClickListener(new View.OnClickListener(){//마이펫면으로 이동
            @Override
            public void onClick(View v){
                Intent intent=new Intent(getApplicationContext(),MypetActivity.class);
                startActivity(intent);
            }
        });
        petstarbutton.setOnClickListener(new View.OnClickListener(){//펫스타화면으로 이동
            @Override
            public void onClick(View v){
                Intent intent=new Intent(getApplicationContext(),PetstarActivity.class);
                startActivity(intent);
            }
        });
//버튼클릭시 화면이동 코드 끝~~~

        //네비게이션바~~

/**        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_push, R.id.nav_notice, R.id.nav_version)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();**/
    }
}
