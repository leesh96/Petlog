package swpj.petlog.petlog2.talktalk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import swpj.petlog.petlog2.R;

public class TalktalkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talktalk);

        Button walkbutton=(Button)findViewById(R.id.walkButton);
        Button sharebutton=(Button)findViewById(R.id.shareButton);

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
