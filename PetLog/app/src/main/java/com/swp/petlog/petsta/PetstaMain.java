package com.swp.petlog.petsta;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.swp.petlog.R;

public class PetstaMain extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private MyFeed_fragment myFeed_fragment;
    private AllFeed_fragment allFeed_fragment;
    private MyProfile_fragment myProfile_fragment;
    private PostWrite_fragment postWrite_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.petsta_main);
        myFeed_fragment = new MyFeed_fragment();
        allFeed_fragment = new AllFeed_fragment();
        postWrite_fragment = new PostWrite_fragment();
        myProfile_fragment = new MyProfile_fragment();
        setPetsta(0);
        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.action_mylist:
                        setPetsta(0);
                        break;
                    case R.id.action_all:
                        setPetsta(1);
                        break;
                    case R.id.action_write:
                        setPetsta(2);
                        break;
                    case R.id.action_myprofile:
                        setPetsta(3);
                        break;
                }
                return true;
            }
        });


    }

    private void setPetsta(int n){
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n){
            case 0:
                ft.replace(R.id.main_frame, myFeed_fragment);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.main_frame, allFeed_fragment);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.main_frame, postWrite_fragment);
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.main_frame, myProfile_fragment);
                ft.commit();
                break;
        }
    }
}
