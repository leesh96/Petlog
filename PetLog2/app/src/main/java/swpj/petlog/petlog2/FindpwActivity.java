package swpj.petlog.petlog2;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;

public class FindpwActivity extends AppCompatActivity {
    Button btn_getvn;
    EditText et_userIdforPw, et_verifynum;
    String code = SendMail.getCode();
    boolean check = false;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpassword);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitDiskReads().permitDiskWrites().permitNetwork().build());

        et_userIdforPw = findViewById(R.id.et_userIDforPw);
        et_verifynum = findViewById(R.id.et_verifynum);
        btn_getvn = findViewById(R.id.btn_getvn);

        btn_getvn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Id = et_userIdforPw.getText().toString();
                final SendMail mailServer = new SendMail();
                mailServer.sendSecurityCode(getApplicationContext(), Id);
                btn_getvn.setText("인증하기");

                btn_getvn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String VerifyNum = et_verifynum.getText().toString();
                        if(code.equals(VerifyNum)) {
                            check = true;
                            AlertDialog.Builder builder = new AlertDialog.Builder(FindpwActivity.this);
                            dialog = builder.setMessage("인증번호가 맞습니다.").setPositiveButton("확인", null).create();
                            dialog.show();
                            //Intent intent = new Intent(FindpwActivity.this, ResetpwActivity.class);
                            //startActivity(intent);
                            et_verifynum.setText("인증완료");
                            et_verifynum.setEnabled(false);
                            btn_getvn.setText("비밀번호 변경하기");
                            btn_getvn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(FindpwActivity.this, ResetpwActivity.class);
                                    startActivity(intent);
                                }
                            });
                        }
                        else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(FindpwActivity.this);
                            dialog = builder.setMessage("인증번호가 틀립니다!").setPositiveButton("확인", null).create();
                            dialog.show();
                            et_verifynum.setText(null);
                            btn_getvn.setText("인증번호 다시받기");
                            btnclickevent();
                        }
                    }
                });
            }
        });
    }

    private void btnclickevent() {
        btn_getvn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Id = et_userIdforPw.getText().toString();
                final SendMail mailServer = new SendMail();
                mailServer.sendSecurityCode(getApplicationContext(), Id);
                btn_getvn.setText("인증하기");

                btn_getvn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String VerifyNum = et_verifynum.getText().toString();
                        if(code.equals(VerifyNum)) {
                            check = true;
                            AlertDialog.Builder builder = new AlertDialog.Builder(FindpwActivity.this);
                            dialog = builder.setMessage("인증번호가 맞습니다.").setPositiveButton("확인", null).create();
                            dialog.show();
                            //Intent intent = new Intent(FindpwActivity.this, ResetpwActivity.class);
                            //startActivity(intent);
                            et_verifynum.setText("인증완료");
                            et_verifynum.setEnabled(false);
                            btn_getvn.setText("비밀번호 변경하기");
                            btn_getvn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(FindpwActivity.this, ResetpwActivity.class);
                                    startActivity(intent);
                                }
                            });
                        }
                        else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(FindpwActivity.this);
                            dialog = builder.setMessage("인증번호가 틀립니다!").setPositiveButton("확인", null).create();
                            dialog.show();
                            et_verifynum.setText(null);
                            btn_getvn.setText("인증번호 다시받기");
                            btnclickevent();
                        }
                    }
                });
            }
        });
    }
}
