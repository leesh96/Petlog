package com.swp.petlog.petsta;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.swp.petlog.R;

import java.util.ArrayList;

public class PetstaPostAdapter extends RecyclerView.Adapter<PetstaPostAdapter.CustomViewHolder> {
    private ArrayList<PetstaPostData> mList = null;
    private Activity context = null;

    public PetstaPostAdapter(Activity context, ArrayList<PetstaPostData> list) {
        this.mList = list;
        this.context = context;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewface, imageViewpostimg, imageViewlike;
        private TextView textViewwriter, textViewtime, textViewlikecnt, textViewcommentcnt, textViewtag, textViewcontent;

        public CustomViewHolder(View view) {
            super(view);

            this.imageViewface = (ImageView) view.findViewById(R.id.writer_face);
            this.imageViewpostimg = (ImageView) view.findViewById(R.id.post_image);
            this.imageViewlike = (ImageView) view.findViewById(R.id.post_like);

            this.textViewwriter = (TextView) view.findViewById(R.id.writer_name);
            this.textViewtime = (TextView) view.findViewById(R.id.post_time);
            this.textViewlikecnt = (TextView) view.findViewById(R.id.post_likecnt);
            this.textViewcommentcnt = (TextView) view.findViewById(R.id.post_commentcnt);
            this.textViewtag = (TextView) view.findViewById(R.id.post_tag);
            this.textViewcontent = (TextView) view.findViewById(R.id.post_contents);

        }
    }

    @NonNull
    @Override
    public PetstaPostAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.petsta_post, null);
        PetstaPostAdapter.CustomViewHolder viewHolder = new PetstaPostAdapter.CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PetstaPostAdapter.CustomViewHolder viewHolder, int position) {
        Glide.with(context).load(mList.get(position).getMember_image()).into(viewHolder.imageViewpostimg);

        viewHolder.textViewwriter.setText(mList.get(position).getMember_nickname());
        viewHolder.textViewtime.setText(mList.get(position).getMember_writetime());
        viewHolder.textViewlikecnt.setText(Integer.toString(mList.get(position).getMember_likecnt()));
        viewHolder.textViewcommentcnt.setText(Integer.toString(mList.get(position).getMember_commentcnt()));
        viewHolder.textViewcontent.setText(mList.get(position).getMember_content());
        int tag = mList.get(position).getMember_tag();

        switch (tag) {
            case 10:
                viewHolder.textViewtag.setText("푸들");
                break;
            case 11:
                viewHolder.textViewtag.setText("말티즈");
                break;
            case 12:
                viewHolder.textViewtag.setText("웰시코기");
                break;
            case 13:
                viewHolder.textViewtag.setText("폼피츠");
                break;
            case 14:
                viewHolder.textViewtag.setText("포메라니안");
                break;
            case 15:
                viewHolder.textViewtag.setText("비숑");
                break;
            case 16:
                viewHolder.textViewtag.setText("치와와");
                break;
            case 20:
                viewHolder.textViewtag.setText("샴");
                break;
            case 21:
                viewHolder.textViewtag.setText("페르시안");
                break;
            case 22:
                viewHolder.textViewtag.setText("러시안블루");
                break;
            case 23:
                viewHolder.textViewtag.setText("스코티쉬폴드");
                break;
            case 24:
                viewHolder.textViewtag.setText("뱅갈");
                break;
            case 25:
                viewHolder.textViewtag.setText("노르웨이 숲");
                break;
            case 26:
                viewHolder.textViewtag.setText("아메리칸 숏헤어");
                break;
            default:
                viewHolder.textViewtag.setText("");
                break;
        }

    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}
