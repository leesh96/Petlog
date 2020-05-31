package swpj.petlog.petlog2.petsta;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import swpj.petlog.petlog2.PreferenceManager;
import swpj.petlog.petlog2.R;

import static android.app.Activity.RESULT_OK;

public class Petsta_write_fragment extends Fragment{
    int PICK_IMAGE_FROM_ALBUM = 1;
    private Button addphoto_btn_upload;
    private Uri photoUri;
    private ImageView addphoto_image;
    private String timestamp, imageFileName;
    private Date date = new Date();
    private String petsta_image;
    private Bitmap bitmap;
    private EditText editContent;
    private AlertDialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.petsta_write_fragment, container, false);

        editContent = (EditText) rootView.findViewById(R.id.petsta_content);
        addphoto_image = (ImageView) rootView.findViewById(R.id.addphoto_image);

        addphoto_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_FROM_ALBUM);
            }
        });

        final String nickname = PreferenceManager.getString(getActivity(), "userNick");

        Toast.makeText(getActivity(), "nickname=" + nickname, Toast.LENGTH_SHORT).show();

        addphoto_btn_upload = (Button) rootView.findViewById(R.id.btn_share);
        addphoto_btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contents = editContent.getText().toString();

                Toast.makeText(getActivity(), "contents=" + contents, Toast.LENGTH_SHORT).show();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                dialog = builder.setMessage("데이터 업로드 성공").setNegativeButton("확인", null).create();
                                dialog.show();
                                Intent intent = new Intent(getContext(), PetstaMain.class);
                                startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                dialog = builder.setMessage("데이터 업로드 실패").setNegativeButton("확인", null).create();
                                dialog.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                AddPhotoRequest addPhotoRequest = new AddPhotoRequest(nickname, contents, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                queue.add(addPhotoRequest);
            }
        });

        return rootView;
    }

    public static String BitmapToString(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();
        String temp= Base64.encodeToString(bytes, Base64.DEFAULT);

        return temp;
    }
    private  Bitmap resize(Bitmap bm){
        Configuration config=getResources().getConfiguration();
        if(config.smallestScreenWidthDp>=600)
            bm = Bitmap.createScaledBitmap(bm, 300, 180, true);
        else if(config.smallestScreenWidthDp>=400)
            bm = Bitmap.createScaledBitmap(bm, 200, 120, true);
        else if(config.smallestScreenWidthDp>=360)
            bm = Bitmap.createScaledBitmap(bm, 180, 108, true);
        else
            bm = Bitmap.createScaledBitmap(bm, 160, 96, true);
        return bm;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_FROM_ALBUM){
            if(resultCode == RESULT_OK){
                try {
                    InputStream is = getActivity().getContentResolver().openInputStream(data.getData());
                    bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                    bitmap = resize(bitmap);
                    addphoto_image.setImageBitmap(bitmap);
                    petsta_image = BitmapToString(bitmap);
                    try {
                        petsta_image = URLEncoder.encode(petsta_image, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}


