package com.example.test02;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class RegisterActivity extends AppCompatActivity {
    private EditText editTextId;
    private EditText editTextPw;
    private EditText editTextName;
    private EditText editTextNick;
    private EditText editTextBdy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editTextId = (EditText) findViewById(R.id.userID);
        editTextPw = (EditText) findViewById(R.id.userPassword);
        editTextName = (EditText) findViewById(R.id.userName);
        editTextNick = (EditText) findViewById(R.id.userNick);
        editTextBdy = (EditText) findViewById(R.id.userBdy);

    }
    public void insert(View view) {
        String Id = editTextId.getText().toString();
        String Pw = editTextPw.getText().toString();
        String Name = editTextName.getText().toString();
        String Nick = editTextNick.getText().toString();
        String Bdy = editTextBdy.getText().toString();

        insertoToDatabase(Id, Pw, Name, Nick, Bdy);
    }
    private void insertoToDatabase(String Id, String Pw, String Name,String Nick, String Bdy) {
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

                    String link = "http://128.199.106.86/SignUptest.php";
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
        task.execute(Id, Pw,Name,Nick,Bdy);
    }

}
