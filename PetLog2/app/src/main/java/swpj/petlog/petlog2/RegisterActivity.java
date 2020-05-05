package swpj.petlog.petlog2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class RegisterActivity extends Activity {
    private EditText editTextId;
    private EditText editTextPw;
    private EditText editTextName;
    private EditText editTextNick;
    private EditText editTextBdy;
    private EditText pwcheck;
    private Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        editTextId = (EditText) findViewById(R.id.et_userID);
        editTextPw = (EditText) findViewById(R.id.et_userPassword);
        editTextName = (EditText) findViewById(R.id.et_userName);
        editTextNick = (EditText) findViewById(R.id.et_userNickname);
        editTextBdy = (EditText) findViewById(R.id.et_userBdy);
        pwcheck = (EditText) findViewById(R.id.et_pwCheck);
        btn_submit = findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Id = editTextId.getText().toString();
                String Pw = editTextPw.getText().toString();
                String Name = editTextName.getText().toString();
                String Nick = editTextNick.getText().toString();
                String Bdy = editTextBdy.getText().toString();
                String Pwcheck = pwcheck.getText().toString();

                insertToDatabase(Id, Pw, Name, Nick, Bdy);

                Intent intent = new Intent(RegisterActivity.this, SignupDoneActivity.class);
                startActivity(intent);
            }
        });
    }

    private void insertToDatabase(String Id, String Pw, String Name, String Nick, String Bdy) {
        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RegisterActivity.this, "Please Wait", null, true, true);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }
            @Override
            protected String doInBackground(String... params) {

                try {
                    String Id = (String) params[0];
                    String Pw = (String) params[1];
                    String Name = (String) params[2];
                    String Nick = (String) params[3];
                    String Bdy = (String) params[4];

                    String link = "http://128.199.106.86/SignUp.php";
                    //String link = "http://192.168.1.2/test2.php";
                    String data = URLEncoder.encode("Id", "UTF-8") + "=" + URLEncoder.encode(Id, "UTF-8");
                    data += "&" + URLEncoder.encode("Pw", "UTF-8") + "=" + URLEncoder.encode(Pw, "UTF-8");
                    data += "&" + URLEncoder.encode("Name", "UTF-8") + "=" + URLEncoder.encode(Name, "UTF-8");
                    data += "&" + URLEncoder.encode("Nick", "UTF-8") + "=" + URLEncoder.encode(Nick, "UTF-8");
                    data += "&" + URLEncoder.encode("Bdy", "UTF-8") + "=" + URLEncoder.encode(Bdy, "UTF-8");

                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();
                } catch (Exception e) {
                    return new String("Exception: " + e.getMessage());
                }
            }
        }
        InsertData task = new InsertData();
        task.execute(Id, Pw, Name, Nick, Bdy);
    }
}
