package swpj.petlog.petlog2.petsta;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import swpj.petlog.petlog2.R;


public class Petsta_profile_fragment extends Fragment{
    private View view;
    int PICK_IMAGE_FROM_ALBUM = 1;
    private String petsta_profile_image;
    private Bitmap bitmap;
    private EditText profile_text_me;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.petsta_profile_fragment, container, false);

        /*profile_text_me = (EditText) v.findViewById(R.id.profile_text_me) ;
        ImageView petsta_profile_image = (ImageView) v.findViewById(R.id.petsta_profile_image);
        petsta_profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 1);
            }
        });
        */

        return v;
    }

    /*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_FROM_ALBUM){
            if(resultCode == Activity.RESULT_OK){
                try {
                    InputStream is = getActivity().getContentResolver().openInputStream(data.getData());
                    bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                    bitmap = resize(bitmap);
                    petsta_profile_image.setImageBitmap(bitmap);
                    petsta_profile_image = BitmapToString(bitmap);
                    try {
                        petsta_profile_image = URLEncoder.encode(petsta_profile_image, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
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
    }*/
}
