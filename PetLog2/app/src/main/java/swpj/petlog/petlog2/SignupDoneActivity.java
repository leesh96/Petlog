package swpj.petlog.petlog2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignupDoneActivity extends AppCompatActivity {
    private Button btn_gotologin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupdone);

        btn_gotologin = findViewById(R.id.btn_gotologin);

        btn_gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupDoneActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

}
