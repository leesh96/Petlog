package swpj.petlog.petlog2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SignupActivity extends AppCompatActivity {
    private EditText et_email, et_pass, et_name, et_nickname, et_bday;
    private ImageButton btn_signupDone;

    public ImageButton getBtn_signupDone() {
        return btn_signupDone;
    }

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        et_email = findViewById(R.id.et_inputemail);
        et_pass = findViewById(R.id.et_inputpswd);
        et_name = findViewById(R.id.et_inputname);
        et_nickname = findViewById(R.id.et_inputnickname);
        et_bday = findViewById(R.id.et_inputbday);

        btn_signupDone = (ImageButton) findViewById(R.id.btn_signupDone);
        btn_signupDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u_id = et_email.getText().toString();
                final String u_pw = et_pass.getText().toString();
                String u_name = et_name.getText().toString();
                String u_nickname = et_nickname.getText().toString();
                String u_bdy = et_bday.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jasonObject = new JSONObject(response);
                            boolean success = jasonObject.getBoolean("success");
                            if (success) {
                                Toast.makeText(getApplicationContext(), "회원 가입 성공", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "회원 등록 실패", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch ( JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                SignupRequest signupRequest = new SignupRequest(u_id, u_pw, u_name, u_nickname, u_bdy, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SignupActivity.this);
                queue.add(signupRequest);
            }
        });
    }
}
