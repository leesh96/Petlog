package swpj.petlog.petlog2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShowMypetActivity extends AppCompatActivity {
    private ImageButton imageButtonback, imageButtonhome, imageButtonModify;
    private TextView textViewname, textViewsex, textViewspecie, textViewage, textViewbday;
    private ImageView imageViewface;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypet_showinfo);

        imageButtonModify = (ImageButton) findViewById(R.id.btn_mypetmodify);
        imageButtonback = (ImageButton) findViewById(R.id.btn_back);
        imageButtonhome = (ImageButton) findViewById(R.id.btn_home);
        imageViewface = (ImageView) findViewById(R.id.petface);
        textViewname = (TextView) findViewById(R.id.tv_petName);
        textViewsex = (TextView) findViewById(R.id.tv_petSex);
        textViewspecie = (TextView) findViewById(R.id.tv_petSpecie);
        textViewage = (TextView) findViewById(R.id.tv_petAge);
        textViewbday = (TextView) findViewById(R.id.tv_petBday);

        int getId = getIntent().getIntExtra("petid", 1);
        String getName = getIntent().getStringExtra("petname");
        String getSex = getIntent().getStringExtra("petsex");
        String getSpecie = getIntent().getStringExtra("petspecie");
        String getAge = getIntent().getStringExtra("petage");
        String getBday = getIntent().getStringExtra("petbday");

        imageButtonhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowMypetActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });

        imageButtonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageViewface.setImageResource(R.drawable.ic_gallary);
        textViewname.setText("이름 : " + getName);
        textViewsex.setText("성별 : " + getSex);
        textViewspecie.setText("종 : " + getSpecie);
        textViewage.setText("나이 : " + getAge);
        textViewbday.setText("생일 : " + getBday);

    }
}
