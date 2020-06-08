package com.example.test02.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test02.R;
import com.example.test02.ShareDetailActivity;
import com.example.test02.data.ShareData;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class ShareAdapter extends RecyclerView.Adapter<ShareAdapter.ViewHolder> {

    private ArrayList<ShareData> mList = null;
    private Activity context = null;


    public ShareAdapter(Activity context, ArrayList<ShareData> list) {
        this.context = context;
        this.mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.share_item_list, null);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }
    Bitmap bitmap;

    @Override //리사이클러뷰 리스트 표시하는곳
    public void onBindViewHolder(@NonNull ViewHolder viewholder, final int position) {

        //사이즈,위치 정할수있음.
        viewholder.title.setText(mList.get(position).getShare_title());
        viewholder.nickname.setText(mList.get(position).getShare_nickname());
        viewholder.date.setText(mList.get(position).getShare_date());

        viewholder.title.setTextSize(20);
        viewholder.nickname.setTextSize(15);
        //viewholder.date.setGravity(Gravity.RIGHT);
        //viewholder.nickname.setGravity(Gravity.LEFT);
        //viewholder.title.setGravity(Gravity.RIGHT);

        viewholder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context= v.getContext();

                Intent intent = new Intent(v.getContext(), ShareDetailActivity.class);

                //여기 name에 있는 값과 detail액티비티의 key값과 동일해야함!!! ----Data에 있는 값이 name 으로 넘어감
                intent.putExtra("id",mList.get(position).getShare_id());
                intent.putExtra("title",mList.get(position).getShare_title());
                intent.putExtra("content",mList.get(position).getShare_content());
                intent.putExtra("nickname",mList.get(position).getShare_nickname());
                intent.putExtra("date",mList.get(position).getShare_date());

                //intent.putExtra("img",mList.get(position).getShare_img());


                context.startActivity(intent);
               // Toast.makeText(context,position +"번째 아이템 클릭",Toast.LENGTH_LONG).show(); //몇번째 아이템 클릭됐는지 토스트로 알려줌
            }
        });
    }
    //

    public void setImageSrc(ImageView imageView, final int position) {
        //ImageView url 설정
        Thread mThread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(mList.get(position).getShare_img());

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        mThread.start();

        try {
            mThread.join();
            imageView.setImageBitmap(bitmap);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ///////////

        public final View mView;
        ///////////
       // protected TextView id;
        protected TextView title;
        protected TextView content;
        protected TextView nickname;
        protected TextView date;

        protected ImageView img;


        public ViewHolder(View view) {
            super(view);
            //
            mView = view;
            //
            view.setOnClickListener(this);

            this.title = (TextView) view.findViewById(R.id.textView_list_title);
            this.nickname = (TextView) view.findViewById(R.id.textView_list_nickname);
            this.date =(TextView)view.findViewById(R.id.textView_list_date);
            // this.img=(ImageView) view.findViewById(R.id.textView_list_img);
        }
        @Override
        public void onClick(View v){
            System.out.println(getPosition());
            Intent intent = new Intent(v.getContext(), ShareDetailActivity.class);
            v.getContext().startActivity(intent);
        }
    }
    @Override
    public int getItemCount() {
        return (mList.size());
    }

}