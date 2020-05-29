package com.swp.petlog.talktalk.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.swp.petlog.R;
import com.swp.petlog.talktalk.data.ShareCommentData;
import com.swp.petlog.talktalk.navigation.ShareCommentActivity;


public class ShareCommentAdapter extends RecyclerView.Adapter<ShareCommentAdapter.ViewHolder>{

    private ArrayList<ShareCommentData> mList = null;
    private Activity context = null;

    public ShareCommentAdapter(Activity context,ArrayList<ShareCommentData> mList) {
        this.mList = mList;
        this.context= context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.share_item_comment,null);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nickname.setText(mList.get(position).getShare_nickname());
        //holder.profile.setImageBitmap(mList.get(position).getShare_profile());
        holder.comment.setText(mList.get(position).getShare_comment());
    }

    @Override
    public int getItemCount() {
        return (mList.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        protected TextView nickname;
        protected TextView comment;
        protected ImageView profile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
            itemView.setOnClickListener(this);
            //this.profile=(ImageView)itemView.findViewById(R.id.profile);
            this.nickname=(TextView) itemView.findViewById(R.id.nickname);
            this.comment=(TextView) itemView.findViewById(R.id.comment);
        }
        @Override
        public void onClick(View v){
            System.out.println(getPosition());
            Intent intent = new Intent(v.getContext(), ShareCommentActivity.class);
            v.getContext().startActivity(intent);
        }
    }
}