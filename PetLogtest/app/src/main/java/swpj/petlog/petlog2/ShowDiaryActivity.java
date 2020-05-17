package swpj.petlog.petlog2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShowDiaryActivity extends AppCompatActivity {
    private ImageButton imageButtonback, imageButtonhome, imageButtonModify;
    private TextView textViewdate, textViewtitle, textViewcontents;
    private ImageView imageViewmood;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_show);

        imageButtonModify = (ImageButton) findViewById(R.id.btn_dairymodify);
        imageButtonback = (ImageButton) findViewById(R.id.btn_back);
        imageButtonhome = (ImageButton) findViewById(R.id.btn_home);
        textViewdate = (TextView) findViewById(R.id.diary_date);
        textViewtitle = (TextView) findViewById(R.id.diary_title);
        textViewcontents = (TextView) findViewById(R.id.diary_content);
        imageViewmood = (ImageView) findViewById(R.id.diary_mood);

        int getId = getIntent().getIntExtra("diaryid", 1);
        String getTitle = getIntent().getStringExtra("diarytitle");
        String getContents = getIntent().getStringExtra("diarycontents");
        String getDate = getIntent().getStringExtra("diarydate");
        int getMood = getIntent().getIntExtra("diarymood", 0);

        imageButtonhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowDiaryActivity.this, MainActivity.class);
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

        if (getMood == 0) {
            imageViewmood.setImageResource(R.drawable.ic_happy);
        }
        if (getMood == 1) {
            imageViewmood.setImageResource(R.drawable.ic_sad);
        }
        if (getMood == 2) {
            imageViewmood.setImageResource(R.drawable.ic_angry);
        }
        if (getMood == 3) {
            imageViewmood.setImageResource(R.drawable.ic_sick);
        }
        if (getMood == 4) {
            imageViewmood.setImageResource(R.drawable.ic_none);
        }
        textViewdate.setText(getDate);
        textViewtitle.setText("제목 : " + getTitle);
        textViewcontents.setText("내용 : " + getContents);

        imageButtonModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
