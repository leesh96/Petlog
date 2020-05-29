package swpj.petlog.petlog2.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import swpj.petlog.petlog2.PreferenceManager;
import swpj.petlog.petlog2.R;

public class ResetpwActivity extends AppCompatActivity {
    private EditText editTextPw;
    private EditText editTextPwcheck;
    private Button btn_changePw;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);

        editTextPw = (EditText) findViewById(R.id.et_resetPw);
        editTextPwcheck = (EditText) findViewById(R.id.et_resetPwcheck);
        btn_changePw = (Button) findViewById(R.id.btn_resetPw);

        btn_changePw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = PreferenceManager.getString(ResetpwActivity.this, "findpwID");
                final String Pw = editTextPw.getText().toString();
                final String Pwcheck = editTextPwcheck.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if(Pw.equals(Pwcheck)) {
                                if (success) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ResetpwActivity.this);
                                    dialog = builder.setMessage("비밀번호 변경에 성공했습니다.\n다시 로그인 해주세요.").setPositiveButton("확인", null).create();
                                    dialog.show();
                                    editTextPw.setEnabled(false);
                                    editTextPwcheck.setEnabled(false);
                                    btn_changePw.setText("로그인 하러가기");
                                    btn_changePw.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent toLogin = new Intent(ResetpwActivity.this, LoginActivity.class);
                                            startActivity(toLogin);
                                            PreferenceManager.removeKey(ResetpwActivity.this, "findpwID");
                                            finish();
                                        }
                                    });
                                }
                                else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ResetpwActivity.this);
                                    dialog = builder.setMessage("비밀번호 변경 실패!").setNegativeButton("확인", null).create();
                                    dialog.show();
                                    return;
                                }
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(ResetpwActivity.this);
                                dialog = builder.setMessage("비밀번호 확인이 일치하지 않습니다!").setNegativeButton("확인", null).create();
                                dialog.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ResetpwRequest resetpwRequest = new ResetpwRequest(userID, Pw, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ResetpwActivity.this);
                queue.add(resetpwRequest);
            }
        });

    }
}
