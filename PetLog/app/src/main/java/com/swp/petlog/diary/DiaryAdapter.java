package com.swp.petlog.diary;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.swp.petlog.R;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.CustomViewHolder> {
    private ArrayList<DiarylistData> mList = null;
    private Activity context = null;

    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }

    private OnItemClickListener mListener = null;

    public void setOnItemClickListener (OnItemClickListener listener) {
        this.mListener = listener;
    }

    public DiaryAdapter(Activity context, ArrayList<DiarylistData> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewMood;
        private TextView textViewTitle, textViewDate;

        public CustomViewHolder(View view) {
            super(view);
            this.imageViewMood = (ImageView) view.findViewById(R.id.item_mood);
            this.textViewTitle = (TextView) view.findViewById(R.id.item_title);
            this.textViewDate = (TextView) view.findViewById(R.id.item_date);

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

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.diary_list, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewHolder, int position) {
        viewHolder.textViewTitle.setText(mList.get(position).getMember_title());
        viewHolder.textViewDate.setText(mList.get(position).getMember_date());
        int mood = mList.get(position).getMember_mood();
        if (mood == 0) {
            viewHolder.imageViewMood.setImageResource(R.drawable.ic_happy);
        }
        if (mood == 1) {
            viewHolder.imageViewMood.setImageResource(R.drawable.ic_sad);
        }
        if (mood == 2) {
            viewHolder.imageViewMood.setImageResource(R.drawable.ic_angry);
        }
        if (mood == 3) {
            viewHolder.imageViewMood.setImageResource(R.drawable.ic_sick);
        }
        if (mood == 4) {
            viewHolder.imageViewMood.setImageResource(R.drawable.ic_none);
        }
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}