package com.swp.petlog.petsta;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
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
import com.bumptech.glide.Glide;
import com.swp.petlog.MainActivity;
import com.swp.petlog.PreferenceManager;
import com.swp.petlog.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ControlPost_fragment extends Fragment {
    private static String mPHPURL = "http://128.199.106.86/modifyPetstaPost.php";
    private static String dPHPURL = "http://128.199.106.86/deletePetstaPost.php";
    private static String TAG = "petsta";

    private ImageButton btn_back, btn_home;

    private Button btn_edit, btn_delete, btn_tag;
    private ImageView addphoto_image;
    private EditText editContent;
    private String imgpath = "";
    private AlertDialog isdialog;
    private AlertDialog speciedialog;
    private int specie_id;

    private String[] iswhat = {"강아지", "고양이"};
    private String[] dogspecie = {"푸들", "말티즈", "웰시코기", "폼피츠", "포메라니안", "비숑", "치와와"};
    private String[] catspecie = {"샴", "페르시안", "러시안블루", "스코티쉬폴드", "뱅갈", "노르웨이 숲", "아메리칸 숏헤어"};

    public static ControlPost_fragment newInstance() {
        return new ControlPost_fragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.petsta_postdetail, container, false);

        int post_id = 0;
        btn_back = (ImageButton) rootView.findViewById(R.id.btn_back);
        btn_home = (ImageButton) rootView.findViewById(R.id.btn_home);
        editContent = (EditText) rootView.findViewById(R.id.petsta_content);
        addphoto_image = (ImageView) rootView.findViewById(R.id.addphoto_image);
        btn_tag = (Button) rootView.findViewById(R.id.btn_tag);

        if(getArguments() != null) {
            int id = getArguments().getInt("id");
            post_id = id;
            int tag = getArguments().getInt("tag");
            String image = getArguments().getString("image");
            String content = getArguments().getString("content");

            Glide.with(getActivity()).load(image).into(addphoto_image);
            editContent.setText(content);
            switch (tag) {
                case 10:
                    btn_tag.setText("#푸들");
                    break;
                case 11:
                    btn_tag.setText("#말티즈");
                    break;
                case 12:
                    btn_tag.setText("#웰시코기");
                    break;
                case 13:
                    btn_tag.setText("#폼피츠");
                    break;
                case 14:
                    btn_tag.setText("#포메라니안");
                    break;
                case 15:
                    btn_tag.setText("#비숑");
                    break;
                case 16:
                    btn_tag.setText("#치와와");
                    break;
                case 20:
                    btn_tag.setText("#샴");
                    break;
                case 21:
                    btn_tag.setText("#페르시안");
                    break;
                case 22:
                    btn_tag.setText("#러시안블루");
                    break;
                case 23:
                    btn_tag.setText("#스코티쉬폴드");
                    break;
                case 24:
                    btn_tag.setText("#뱅갈");
                    break;
                case 25:
                    btn_tag.setText("#노르웨이 숲");
                    break;
                case 26:
                    btn_tag.setText("#아메리칸 숏헤어");
                    break;
                default:
                    break;
            }
        }

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().remove(ControlPost_fragment.this).commit();
                fragmentManager.popBackStack();
                Log.d("count", Integer.toString(fragmentManager.getBackStackEntryCount()));
                if (fragmentManager.getBackStackEntryCount() == 0) {
                    getActivity().finish();
                }
            }
        });

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionResult = getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permissionResult == PackageManager.PERMISSION_DENIED) {
                String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, 10);
            }
        } else {
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

        btn_edit = (Button) rootView.findViewById(R.id.btn_edit);
        final int finalPost_id = post_id;
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contents = editContent.getText().toString();
                String tag = Integer.toString(specie_id);
                String postid = Integer.toString(finalPost_id);

                if (imgpath.equals("")) {
                    modify(tag, contents, postid, "false");
                } else {
                    modify(tag, contents, postid, "true");
                }

            }
        });

        btn_delete = (Button) rootView.findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postid = Integer.toString(finalPost_id);

                DeleteData task = new DeleteData();
                task.execute(dPHPURL, postid);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().remove(ControlPost_fragment.this).commit();
                fragmentManager.popBackStack();
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

    public void modify(String tag, String contents, String postid, String picchanged) {
        SimpleMultiPartRequest smpr= new SimpleMultiPartRequest(Request.Method.POST, mPHPURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity(), "수정성공" + response, Toast.LENGTH_SHORT).show();
                Log.d("TAG", response);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().remove(ControlPost_fragment.this).commit();
                fragmentManager.popBackStack();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
                Log.d("TAG", error.toString());
            }
        });
        //요청 객체에 보낼 데이터를 추가
        smpr.addStringParam("contents", contents);
        smpr.addStringParam("tag", tag);
        smpr.addStringParam("id", postid);
        smpr.addStringParam("picchanged", picchanged);
        smpr.addFile("image", imgpath);

        //요청객체를 서버로 보낼 우체통 같은 객체 생성
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(smpr);
    }

    class DeleteData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(getActivity(),
                    "Please Wait...", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            Log.d(TAG, "POST response  - " + result);
        }

        @Override
        protected String doInBackground(String... params) {

            String serverURL = (String)params[0];
            String id = (String)params[1];

            String postParameters = "id=" + id;

            try {
                URL url = new URL(serverURL);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("utf-8"));
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString();

            } catch (Exception e) {
                Log.d(TAG, "InsertData: Error ", e);
                return new String("Error: " + e.getMessage());
            }
        }
    }
}
