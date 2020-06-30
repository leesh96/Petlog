package com.swp.petlog.talktalk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.swp.petlog.MainActivity;
import com.swp.petlog.R;

public class TalktalkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.talktalk_main);

        ImageButton walkbutton=(ImageButton)findViewById(R.id.walkButton);
        ImageButton sharebutton=(ImageButton)findViewById(R.id.shareButton);
        ImageButton btn_home= (ImageButton)findViewById(R.id.btn_home);

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TalktalkActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        walkbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(getApplicationContext(),WalkActivity.class);
                startActivity(intent);
            }
        });
        sharebutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(getApplicationContext(), ShareActivity.class);
                startActivity(intent);
            }
        });
    }
}
