package com.swp.petlog.petsta.adapter;

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
import com.swp.petlog.petsta.data.MyFollowData;

import java.util.ArrayList;

public class MyFollowAdapter extends RecyclerView.Adapter<MyFollowAdapter.ViewHolder>{

    private ArrayList<MyFollowData> mList = null;
    private Activity context = null;

    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }

    private OnItemClickListener mListener = null;

    public void setOnItemClickListener (OnItemClickListener listener) {
        this.mListener = listener;
    }

    public MyFollowAdapter(Activity context, ArrayList<MyFollowData> list) {
        this.context = context;
        this.mList = list;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.petsta_followerlistitem, null);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        if (!mList.get(position).getFollower_face().equals("http://128.199.106.86/null")) {
            Glide.with(context).load(mList.get(position).getFollower_face()).into(viewHolder.follower_face);
        }
        viewHolder.follower_nickname.setText(mList.get(position).getFollowerNickname());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        protected TextView follower_nickname;
        protected ImageView follower_face;

        public ViewHolder(View view){
            super(view);
            mView = view;
            this.follower_nickname = (TextView)view.findViewById(R.id.item_follower_nickname);
            this.follower_face = (ImageView)view.findViewById(R.id.follow_face);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        if (mListener != null) mListener.onItemClick(v, pos);
                    }
                }
            });
        }

    }

    public int getItemCount() {
        return (mList.size());
    }
}