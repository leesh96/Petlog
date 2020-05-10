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
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MypetMainActivity extends AppCompatActivity {
    private ImageButton imgbtn_home, imgbtn_mypetmenu, imgbtn_memoapply, imgbtn_memo;
    private AlertDialog dialog;
    private EditText editTextName, editTextSex, editTextSpecies, editTextAge, editTextBday, editTextMemo;
    private String Petname, Petsex, Petage, Petspecies, PetBday, PetFace;
    private Bitmap bitmap;
    private ImageView imageViewFace;
    private int datalength;
    private ArrayList<String> petname, petsex, petspecies, petage, petbday;
    private ViewPager viewPager;
    private

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypet_main);

        editTextName = (EditText) findViewById(R.id.et_petName);
        editTextSex = (EditText) findViewById(R.id.et_petSex);
        editTextSpecies = (EditText) findViewById(R.id.et_petSpecie);
        editTextAge = (EditText) findViewById(R.id.et_petAge);
        editTextBday = (EditText) findViewById(R.id.et_petBday);
        imageViewFace = (ImageView) findViewById(R.id.petface);

        String userID = PreferenceManager.getString(MypetMainActivity.this, "userID");

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    datalength = jsonArray.length();
                    petname = new ArrayList<String>();
                    petsex = new ArrayList<String>();
                    petage = new ArrayList<String>();
                    petspecies = new ArrayList<String>();
                    petbday = new ArrayList<String>();

                    for (int i = 0; i < datalength; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        boolean success = jsonObject.getBoolean("success");

                        if (success) {
                            Petname = jsonObject.getString("petName");
                            Petsex = jsonObject.getString("petSex");
                            Petspecies = jsonObject.getString("petSpecies");
                            Petage = jsonObject.getString("petAge");
                            PetBday = jsonObject.getString("petBday");
                            PetFace = jsonObject.getString("petFace");
                            petname.add(Petname);
                            petsex.add(Petsex);
                            petspecies.add(Petspecies);
                            petage.add(Petage);
                            petbday.add(PetBday);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "데이터 로드 실패", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    /*JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");

                    if (success) { //로드성공

                        Petname = jsonObject.getString("petName");
                        Petsex = jsonObject.getString("petSex");
                        Petage = jsonObject.getString("petSpecies");
                        Petspecies = jsonObject.getString("petAge");
                        PetBday = jsonObject.getString("petBday");
                        PetFace = jsonObject.getString("petFace");
                        bitmap = StringToBitMap(PetFace);
                        imageViewFace.setImageBitmap(bitmap);

                        Toast.makeText(getApplicationContext(), "데이터 로드 성공", Toast.LENGTH_SHORT).show();

                        editTextName.setText(Petname);
                        editTextName.setEnabled(false);
                        editTextSex.setText(Petsex);
                        editTextSex.setEnabled(false);
                        editTextSpecies.setText(Petspecies);
                        editTextSpecies.setEnabled(false);
                        editTextAge.setText(Petage);
                        editTextAge.setEnabled(false);
                        editTextBday.setText(PetBday);
                        editTextBday.setEnabled(false);

                    } else { //로드실패
                        Toast.makeText(getApplicationContext(), "데이터 로드 실패", Toast.LENGTH_SHORT).show();
                        return;
                    }*/
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ShowMypetInfoRequest showMypetInfoRequest = new ShowMypetInfoRequest(userID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MypetMainActivity.this);
        queue.add(showMypetInfoRequest);

        ViewPager viewPager = findViewById(R.id.mypetinfo);
        viewPager.setClipToPadding(false);
        viewPager.setAdapter(new ViewPagerAdapter(MypetMainActivity.this, petname, petsex, petspecies, petage, petbday));

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
