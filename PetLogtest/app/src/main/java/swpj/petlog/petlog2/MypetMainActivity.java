package swpj.petlog.petlog2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class MypetMainActivity extends AppCompatActivity {
    private ImageButton imgbtn_home, imgbtn_mypetmenu;
    private AlertDialog dialog;
    private TextView textViewPetName, textViewPetSex, textViewPetSpecie, textViewPetAge, textViewPetBday;
    private int datalength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypet_main);

        String userID = PreferenceManager.getString(MypetMainActivity.this, "userID");

        textViewPetName = (TextView) findViewById(R.id.tv_petName);
        textViewPetSex = (TextView) findViewById(R.id.tv_petSex);
        textViewPetSpecie = (TextView) findViewById(R.id.tv_petSpecie);
        textViewPetAge = (TextView) findViewById(R.id.tv_petAge);
        textViewPetBday = (TextView) findViewById(R.id.tv_petBday);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    textViewPetName.setText(jsonObject.getString("petName"));
                    textViewPetSex.setText(jsonObject.getString("petSex"));
                    textViewPetSpecie.setText(jsonObject.getString("petSpecies"));
                    textViewPetAge.setText(jsonObject.getString("petAge"));
                    textViewPetBday.setText(jsonObject.getString("petBday"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ShowMypetInfoRequest showMypetInfoRequest = new ShowMypetInfoRequest(userID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MypetMainActivity.this);
        queue.add(showMypetInfoRequest);

        imgbtn_home = (ImageButton) findViewById(R.id.btn_home);
        imgbtn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MypetMainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        imgbtn_mypetmenu = (ImageButton) findViewById(R.id.btn_mypetmenu);
        imgbtn_mypetmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context wrapper = new ContextThemeWrapper(MypetMainActivity.this, R.style.mypetmenustyle);
                PopupMenu popupMenu = new PopupMenu(MypetMainActivity.this, v);
                MenuInflater inflater = popupMenu.getMenuInflater();
                Menu menu = popupMenu.getMenu();

                inflater.inflate(R.menu.mypet_menu, menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.mypetadd:
                                Intent intent = new Intent(MypetMainActivity.this, AddMypetInfoActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.mypetmodify:
                                break;
                            case R.id.mypetdelete:
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    public static Bitmap StringToBitMap(String image){
        try{
            byte [] encodeByte= Base64.decode(image,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }
}
