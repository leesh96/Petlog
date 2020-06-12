package com.swp.petlog.app;

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

public class ChangeInfoActivity extends AppCompatActivity {
    private static String PHPURL = "http://128.199.106.86/changeAccount.php";

    private EditText editTextNick;
    private Button btn_change,btn_changepw;
    private ImageButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_editaccount);

        editTextNick = (EditText) findViewById(R.id.et_changeNick);

        btn_change = (Button) findViewById(R.id.btn_change);
        btn_changepw = (Button) findViewById(R.id.btn_changepw);
        btn_back = (ImageButton) findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final String userid = PreferenceManager.getString(ChangeInfoActivity.this, "userID");

        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickname = editTextNick.getText().toString();
                if (nickname.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChangeInfoActivity.this);
                    AlertDialog dialog = builder.setMessage("닉네임을 입력하세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                }
                else {
                    modify(userid, nickname);
                    PreferenceManager.setString(ChangeInfoActivity.this, "userNick", nickname);
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
                Toast.makeText(ChangeInfoActivity.this, "성공" + response, Toast.LENGTH_SHORT).show();
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
