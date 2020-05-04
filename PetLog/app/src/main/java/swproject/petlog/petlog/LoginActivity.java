package swproject.petlog.petlog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout inputem, inputpw;
    private AppCompatEditText inputemail, inputpswd;
    private Button sign_up, sign_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputem = (TextInputLayout) findViewById(R.id.textInputLayout);
        inputpw = (TextInputLayout) findViewById(R.id.textInputLayout2);

        inputemail = (AppCompatEditText) findViewById(R.id.inputemail);
        inputpswd = (AppCompatEditText) findViewById(R.id.inputpswd);

        sign_up = findViewById(R.id.sign_up);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        sign_in = findViewById(R.id.sign_in);

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u_id = inputemail.getText().toString();
                String u_pw = inputpswd.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if(success) {

                                String u_id = jsonObject.getString( "userID" );
                                String u_pw = jsonObject.getString( "userPassword" );
                                String u_name = jsonObject.getString( "userName" );
                                String u_nickname = jsonObject.getString( "userNick" );

                                Toast.makeText( getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT ).show();
                                Intent intent = new Intent( LoginActivity.this, MainActivity.class );
                                startActivity( intent );
                                intent.putExtra( "userID", u_id );
                                intent.putExtra( "userPass", u_pw );
                                intent.putExtra( "userName", u_name );
                                intent.putExtra( "userNick", u_nickname );
                            } else {
                                Toast.makeText( getApplicationContext(), "로그인 실패 다시 시도하세요", Toast.LENGTH_SHORT ).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest (u_id, u_pw, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this );
                queue.add(loginRequest);

            }
        });
    }
}
