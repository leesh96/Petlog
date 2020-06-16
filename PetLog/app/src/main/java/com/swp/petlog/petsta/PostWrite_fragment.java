package com.swp.petlog.petsta;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.loader.content.CursorLoader;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.swp.petlog.MainActivity;
import com.swp.petlog.PreferenceManager;
import com.swp.petlog.R;
import com.swp.petlog.diary.WriteDiaryActivity;

public class PostWrite_fragment extends Fragment {
    private static String PHPURL = "http://128.199.106.86/addPetstaPost.php";

    private ImageButton btn_home;

    private Button addphoto_btn_upload, btn_tag;
    private ImageView addphoto_image;
    private EditText editContent;
    private String imgpath = "";
    private AlertDialog nullcheck;
    private AlertDialog isdialog;
    private AlertDialog speciedialog;
    private int specie_id;

    private String[] iswhat = {"강아지", "고양이"};
    private String[] dogspecie = {"푸들", "말티즈", "웰시코기", "폼피츠", "포메라니안", "비숑", "치와와"};
    private String[] catspecie = {"샴", "페르시안", "러시안블루", "스코티쉬폴드", "뱅갈", "노르웨이 숲", "아메리칸 숏헤어"};

    public static PostWrite_fragment newInstance() {
        return new PostWrite_fragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.petsta_write, container, false);

        btn_home = (ImageButton) rootView.findViewById(R.id.btn_home);
        editContent = (EditText) rootView.findViewById(R.id.petsta_content);
        addphoto_image = (ImageView) rootView.findViewById(R.id.addphoto_image);
        btn_tag = (Button) rootView.findViewById(R.id.btn_tag);

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            int permissionResult = getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permissionResult== PackageManager.PERMISSION_DENIED){
                String[] permissions= new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions,10);
            }
        }else{
            //cv.setVisibility(View.VISIBLE);
        }

        addphoto_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 10);
            }
        });

        btn_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isdialog.show();
            }
        });

        isdialog = new AlertDialog.Builder(getActivity())
                .setItems(iswhat, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            speciedialog = new AlertDialog.Builder(getActivity())
                                    .setItems(dogspecie, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (which == 0) {
                                                btn_tag.setText("#" + dogspecie[which]);
                                                specie_id = 10;
                                            }
                                            if (which == 1) {
                                                btn_tag.setText("#" + dogspecie[which]);
                                                specie_id = 11;
                                            }
                                            if (which == 2) {
                                                btn_tag.setText("#" + dogspecie[which]);
                                                specie_id = 12;
                                            }
                                            if (which == 3) {
                                                btn_tag.setText("#" + dogspecie[which]);
                                                specie_id = 13;
                                            }
                                            if (which == 4) {
                                                btn_tag.setText("#" + dogspecie[which]);
                                                specie_id = 14;
                                            }
                                            if (which == 5) {
                                                btn_tag.setText("#" + dogspecie[which]);
                                                specie_id = 15;
                                            }
                                            if (which == 6) {
                                                btn_tag.setText("#" + dogspecie[which]);
                                                specie_id = 16;
                                            }
                                        }
                                    })
                                    .setTitle("강아지의 종이 무엇인가요?")
                                    .setPositiveButton("확인", null)
                                    .setNegativeButton("취소", null)
                                    .create();
                            speciedialog.show();
                        }
                        if (which == 1) {
                            speciedialog = new AlertDialog.Builder(getActivity())
                                    .setItems(catspecie, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (which == 0) {
                                                btn_tag.setText("#" + catspecie[which]);
                                                specie_id = 20;
                                            }
                                            if (which == 1) {
                                                btn_tag.setText("#" + catspecie[which]);
                                                specie_id = 21;
                                            }
                                            if (which == 2) {
                                                btn_tag.setText("#" + catspecie[which]);
                                                specie_id = 22;
                                            }
                                            if (which == 3) {
                                                btn_tag.setText("#" + catspecie[which]);
                                                specie_id = 23;
                                            }
                                            if (which == 4) {
                                                btn_tag.setText("#" + catspecie[which]);
                                                specie_id = 24;
                                            }
                                            if (which == 5) {
                                                btn_tag.setText("#" + catspecie[which]);
                                                specie_id = 25;
                                            }
                                            if (which == 6) {
                                                btn_tag.setText("#" + catspecie[which]);
                                                specie_id = 26;
                                            }
                                        }
                                    })
                                    .setTitle("고양이의 종이 무엇인가요?")
                                    .setPositiveButton("확인", null)
                                    .setNegativeButton("취소", null)
                                    .create();
                            speciedialog.show();
                        }
                    }
                })
                .setTitle("강아지인가요 고양이인가요?")
                .setPositiveButton("확인", null)
                .setNegativeButton("취소", null)
                .create();

        final String nickname = PreferenceManager.getString(getActivity(), "userNick");

        addphoto_btn_upload = (Button) rootView.findViewById(R.id.btn_share);
        addphoto_btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contents = editContent.getText().toString();
                String tag = Integer.toString(specie_id);

                if (imgpath.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    nullcheck = builder.setMessage("사진을 선택해주세요.").setNegativeButton("확인", null).create();
                    nullcheck.show();
                } else {
                    upload(nickname, contents, tag);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 10 :
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED) //사용자가 허가 했다면
                {
                    Toast.makeText(getActivity(), "외부 메모리 읽기/쓰기 사용 가능", Toast.LENGTH_SHORT).show();

                }else{//거부했다면
                    Toast.makeText(getActivity(), "외부 메모리 읽기/쓰기 제한", Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 10:
                if(resultCode == -1){
                    //선택한 사진의 경로(Uri)객체 얻어오기
                    Uri uri= data.getData();
                    if(uri!=null){
                        addphoto_image.setImageURI(uri);
                        imgpath = getRealPathFromUri(uri);
                    }

                }else
                {
                    Toast.makeText(getActivity(), "이미지 선택을 하지 않았습니다.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public String getRealPathFromUri(Uri uri){
        String[] proj= {MediaStore.Images.Media.DATA};
        CursorLoader loader= new CursorLoader(getActivity(), uri, proj, null, null, null);
        Cursor cursor= loader.loadInBackground();
        int column_index= ((Cursor) cursor).getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result= cursor.getString(column_index);
        cursor.close();
        return result;
    }

    public void upload(String nickname, String contents, String tag) {
        SimpleMultiPartRequest smpr= new SimpleMultiPartRequest(Request.Method.POST, PHPURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity(), "공유성공", Toast.LENGTH_SHORT).show();
                Log.d("TAG", response);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().remove(PostWrite_fragment.this).commit();
                fragmentManager.popBackStack();
                BottomNavigationView bottomNavigationView = ((PetstaMain) getActivity()).bottomNavigationView;
                bottomNavigationView.setSelectedItemId(R.id.action_mylist);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
                Log.d("TAG", error.toString());
            }
        });
        //요청 객체에 보낼 데이터를 추가
        smpr.addStringParam("nickname", nickname);
        smpr.addStringParam("contents", contents);
        smpr.addStringParam("tag", tag);
        smpr.addFile("image", imgpath);

        //요청객체를 서버로 보낼 우체통 같은 객체 생성
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(smpr);
    }

}


