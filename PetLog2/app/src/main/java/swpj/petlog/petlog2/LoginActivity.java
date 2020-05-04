package swpj.petlog.petlog2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private EditText et_loginemail, et_loginpw;
    private Button btn_signin, btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_loginemail = findViewById(R.id.et_loginemail);
        et_loginpw = findViewById(R.id.et_loginpw);
        btn_signin = findViewById(R.id.btn_signin);
        btn_signup = findViewById(R.id.btn_signup);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String u_id = et_loginemail.getText().toString();
                String u_pw = et_loginpw.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jasonObject = new JSONObject(response);
                            boolean success = jasonObject.getBoolean("success");
                            if (success) {
                                String u_id = jasonObject.getString("u_id");
                                String u_pw = jasonObject.getString("u_pw");
                                Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();

                                Intent intent  = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("log", "user_info");
                                intent.putExtra("u_id", u_id);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(u_id, u_pw, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }
}
