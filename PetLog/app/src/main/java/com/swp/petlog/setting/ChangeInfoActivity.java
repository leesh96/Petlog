package com.swp.petlog.setting;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
import com.swp.petlog.MainActivity;
import com.swp.petlog.PreferenceManager;
import com.swp.petlog.R;
import com.swp.petlog.app.ResetpwActivity;
import com.swp.petlog.app.ValidateRequestNick;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangeInfoActivity extends AppCompatActivity {
    private static String PHPURL = "http://128.199.106.86/changeAccount.php";

    private EditText editTextNick;
    private Button btn_change,btn_changepw, btn_validatenick;
    private ImageButton btn_back;
    private AlertDialog dialog;
    private boolean validatenick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_editaccount);

        editTextNick = (EditText) findViewById(R.id.et_changeNick);

        btn_change = (Button) findViewById(R.id.btn_change);
        btn_changepw = (Button) findViewById(R.id.btn_changepw);
        btn_back = (ImageButton) findViewById(R.id.btn_back);
        btn_validatenick = (Button) findViewById(R.id.btn_checknick);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final String userid = PreferenceManager.getString(ChangeInfoActivity.this, "userID");
        final String nickname = PreferenceManager.getString(ChangeInfoActivity.this, "userNick");

        btn_validatenick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Nick = editTextNick.getText().toString();
                if (validatenick)
                    return;
                if (Nick.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChangeInfoActivity.this);
                    dialog = builder.setMessage("닉네임을 입력하세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }
                if (Nick.equals(nickname)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChangeInfoActivity.this);
                    dialog = builder.setMessage("현재 닉네임과 다른 닉네임을 입력하세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ChangeInfoActivity.this);
                                dialog = builder.setMessage("사용할 수 있는 닉네임입니다.").setPositiveButton("확인", null).create();
                                dialog.show();
                                editTextNick.setEnabled(false);
                                validatenick = true;
                                btn_validatenick.setText("완료");
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ChangeInfoActivity.this);
                                dialog = builder.setMessage("사용할 수 없는 닉네임입니다!").setPositiveButton("확인", null).create();
                                dialog.show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequestNick validateRequest = new ValidateRequestNick(Nick, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ChangeInfoActivity.this);
                queue.add(validateRequest);
            }
        });

        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickname = editTextNick.getText().toString();
                if(nickname.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChangeInfoActivity.this);
                    dialog = builder.setMessage("닉네임을 입력하세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }
                else if(validatenick) {
                    PreferenceManager.setString(ChangeInfoActivity.this, "userNick", nickname);
                    modify(userid, nickname);
                }
                else if (!validatenick) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChangeInfoActivity.this);
                    dialog = builder.setMessage("닉네임 중복확인을 해주세요!").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChangeInfoActivity.this);
                    dialog = builder.setMessage("닉네임 변경 실패").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }
            }
        });

        btn_changepw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChangePw = true;
                Intent intent = new Intent(ChangeInfoActivity.this, ResetpwActivity.class);
                intent.putExtra("isChangePw", isChangePw);
                startActivity(intent);
            }
        });
    }

    public void modify(String userid, String nickname) {
        SimpleMultiPartRequest smpr= new SimpleMultiPartRequest(Request.Method.POST, PHPURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(ChangeInfoActivity.this, "닉네임 변경 성공", Toast.LENGTH_SHORT).show();
                Log.d("TAG", response);
                Intent intent = new Intent(ChangeInfoActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ChangeInfoActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                Log.d("TAG", error.toString());
            }
        });
        //요청 객체에 보낼 데이터를 추가
        smpr.addStringParam("userid", userid);
        smpr.addStringParam("nickname", nickname);

        //요청객체를 서버로 보낼 우체통 같은 객체 생성
        RequestQueue requestQueue= Volley.newRequestQueue(ChangeInfoActivity.this);
        requestQueue.add(smpr);
    }
}
