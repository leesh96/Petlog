package com.swp.petlog.talktalk;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.swp.petlog.R;

public class SetWalkImageActivity extends Activity implements OnClickListener {

    static final int camera=2001;
    static final int gallery=2002;
    Button ClosepopBtn,camerapopBtn,gallerypopBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_walk_image);
        init();



    }

    private void init() {
        camerapopBtn=(Button)findViewById(R.id.camerapopBtn);
        gallerypopBtn=(Button)findViewById(R.id.gallerypopBtn);
        ClosepopBtn=(Button)findViewById(R.id.ClosepopBtn);

        gallerypopBtn.setOnClickListener(this);
        camerapopBtn.setOnClickListener(this);
        ClosepopBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        switch(v.getId()){
            case R.id.camerapopBtn:
                intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent, camera);
                break;
            case R.id.gallerypopBtn:

                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/-");
                startActivityForResult(intent, gallery);
                break;

            case R.id.ClosepopBtn:
                setResult(RESULT_CANCELED, intent);
                Toast.makeText(getApplicationContext(), "취소하였습니다.", Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:
                break;
        }

    }
    @SuppressLint("NewApi")
    private Bitmap resize(Bitmap bm){

        Configuration config=getResources().getConfiguration();
		/*if(config.smallestScreenWidthDp>=800)
			bm = Bitmap.createScaledBitmap(bm, 400, 240, true);//이미지 크기로 인해 용량초과
		else */if(config.smallestScreenWidthDp>=600)
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent=new Intent();
        Bitmap bm;
        if(resultCode==RESULT_OK){
            switch(requestCode){
                case camera:
				/*
				try {

					bm= Images.Media.getBitmap(getContentResolver(), data.getData());

					bm=resize(bm);
					intent.putExtra("bitmap",bm);
				} catch (FileNotFoundException e) {

					e.printStackTrace();
				} catch (IOException e) {

					e.printStackTrace();
				}*/
                    bm=(Bitmap) data.getExtras().get("data");
                    bm=resize(bm);
                    intent.putExtra("bitmap",bm);
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
                case gallery:

                    try {

                        bm = Images.Media.getBitmap( getContentResolver(), data.getData());
                        bm=resize(bm);
                        intent.putExtra("bitmap",bm);
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }catch(OutOfMemoryError e){
                        Toast.makeText(getApplicationContext(), "이미지 용량이 너무 큽니다.", Toast.LENGTH_SHORT).show();
                    }
                    setResult(RESULT_OK, intent);
                    finish();
                    break;

                default:
                    setResult(RESULT_CANCELED, intent);
                    finish();
                    break;

            }
        }else{
            setResult(RESULT_CANCELED, intent);
            finish();
        }
    }



}
