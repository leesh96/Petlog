package swproject.petlog.petlog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SignUpActivity extends AppCompatActivity {
    private TextInputLayout editTextId;
    private TextInputLayout editTextPw;
    private TextInputLayout editTextName;
    private TextInputLayout editTextNickname;
    private TextInputLayout editTextBday;

    private AppCompatEditText appCompatEditTextId;
    private AppCompatEditText appCompatEditTextPw;
    private AppCompatEditText appCompatEditTextName;
    private AppCompatEditText appCompatEditTextNickname;
    private AppCompatEditText appCompatEditTextBday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        editTextId = (TextInputLayout) findViewById(R.id.textInputLayout3);
        editTextName = (TextInputLayout) findViewById(R.id.textInputLayout7);
        editTextPw = (TextInputLayout) findViewById(R.id.textInputLayout4);
        editTextNickname = (TextInputLayout) findViewById(R.id.textInputLayout6);
        editTextBday = (TextInputLayout) findViewById(R.id.textInputLayout8);

        appCompatEditTextId = (AppCompatEditText) findViewById(R.id.inputemail);
        appCompatEditTextName = (AppCompatEditText) findViewById(R.id.inputname);
        appCompatEditTextPw = (AppCompatEditText) findViewById(R.id.inputpswd);
        appCompatEditTextNickname = (AppCompatEditText) findViewById(R.id.input_nickname);
        appCompatEditTextBday = (AppCompatEditText) findViewById(R.id.input_bday);

    }
    public void insert(View view) {
        Editable email = appCompatEditTextId.getText();
        String Id = email.toString();
        Editable pass = appCompatEditTextPw.getText();
        String Pw = pass.toString();
        Editable pername = appCompatEditTextName.getText();
        String Name = pername.toString();
        Editable nickn = appCompatEditTextNickname.getText();
        String Nick = nickn.toString();
        Editable birth = appCompatEditTextBday.getText();
        String Bdy = birth.toString();

        insertToDb(Id, Pw, Name, Nick, Bdy);
    }

    private void insertToDb(String Id, String Pw, String Name, String Nick, String Bdy) {
        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected  void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SignUpActivity.this, "Please Wait", null, true, true);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }
            @Override
            protected String doInBackground(String... params) {

                try{
                    String Id = (String) params[0];
                    String Pw = (String) params[1];
                    String Name = (String) params[2];
                    String Nick = (String) params[3];
                    String Bdy = (String) params[4];

                    String link = "http://128.199.106.86/SignUptest.php";
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
