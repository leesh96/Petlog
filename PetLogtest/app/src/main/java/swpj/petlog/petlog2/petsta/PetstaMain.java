package swpj.petlog.petlog2.petsta;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import swpj.petlog.petlog2.R;

public class PetstaMain extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Petsta_my_fragment petsta_my_fragment;
    private Petsta_all_fragment petsta_all_fragment;
    private Petsta_profile_fragment petsta_profile_fragment;
    private Petsta_write_fragment petsta_write_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.petsta_main);


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
        petsta_my_fragment = new Petsta_my_fragment();
        petsta_all_fragment = new Petsta_all_fragment();
        petsta_write_fragment = new Petsta_write_fragment();
        petsta_profile_fragment = new Petsta_profile_fragment();

    }

    private void setPetsta(int n){
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n){
            case 0:
                ft.replace(R.id.main_frame, petsta_my_fragment);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.main_frame, petsta_all_fragment);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.main_frame, petsta_write_fragment);
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.main_frame, petsta_profile_fragment);
                ft.commit();
                break;
        }
    }
}
