package com.swp.petlog.diary;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.swp.petlog.R;

public class DiaryDeleteAdapter extends RecyclerView.Adapter<DiaryDeleteAdapter.CustomViewHolder> {

    private ArrayList<DiarylistData> mList = null;
    private Activity context = null;

    public interface OnCheckedChangeListener {
        void onCheckedChanged(CompoundButton compoundButton, boolean isChecked);
    }

    private OnCheckedChangeListener mListener = null;

    public void setOnCheckedChangeListener (OnCheckedChangeListener listener) {
        this.mListener = listener;
    }

    public DiaryDeleteAdapter(Activity context, ArrayList<DiarylistData> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewMood;
        private TextView textViewTitle, textViewDate;
        private CheckBox checkBox;

        public CustomViewHolder(View view) {
            super(view);
            this.imageViewMood = (ImageView) view.findViewById(R.id.item_mood);
            this.textViewTitle = (TextView) view.findViewById(R.id.item_title);
            this.textViewDate = (TextView) view.findViewById(R.id.item_date);
            this.checkBox = (CheckBox) view.findViewById(R.id.checkbox_del);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        if (mListener != null) mListener.onCheckedChanged(checkBox, isChecked);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public DiaryDeleteAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.diary_dellist, null);
        DiaryDeleteAdapter.CustomViewHolder viewHolder = new DiaryDeleteAdapter.CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryDeleteAdapter.CustomViewHolder viewHolder, final int position) {
        viewHolder.textViewTitle.setText(mList.get(position).getMember_title());
        viewHolder.textViewDate.setText(mList.get(position).getMember_date());
        viewHolder.checkBox.setChecked(mList.get(position).isSelected());

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

        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    mList.get(position).setSelected(true);
                }
                else {
                    mList.get(position).setSelected(false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}
