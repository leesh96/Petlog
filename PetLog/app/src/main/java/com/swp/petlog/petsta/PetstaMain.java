package com.swp.petlog.petsta;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.swp.petlog.PreferenceManager;
import com.swp.petlog.R;

public class PetstaMain extends AppCompatActivity {
    public BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.petsta_main);

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

    public void setPetsta(int n){
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n){
            case 0:
                ft.replace(R.id.main_frame, new MyFeed_fragment());
                ft.addToBackStack(null);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.main_frame, new AllFeed_fragment());
                ft.addToBackStack(null);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.main_frame, new PostWrite_fragment());
                ft.addToBackStack(null);
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.main_frame, new MyProfile_fragment());
                ft.addToBackStack(null);
                ft.commit();
                break;
        }
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment).addToBackStack(null).commit();      // Fragment로 사용할 MainActivity내의 layout공간을 선택합니다.
    }
}
