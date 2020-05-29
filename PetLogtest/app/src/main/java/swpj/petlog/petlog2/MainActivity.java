package swpj.petlog.petlog2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import swpj.petlog.petlog2.app.LoginActivity;
import swpj.petlog.petlog2.diary.DiaryListActivity;
import swpj.petlog.petlog2.mypet.MypetMainActivity;
import swpj.petlog.petlog2.petsta.PetstaMain;
import swpj.petlog.petlog2.talktalk.TalktalkActivity;

public class MainActivity extends AppCompatActivity {
    private ImageButton imgbtn_mypet, imgbtn_dairy, imgbtn_petsta, imgbtn_talktalk;
    private Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_logout = (Button) findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                PreferenceManager.clear(MainActivity.this);
                finish();
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
}
