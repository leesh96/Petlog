package com.swp.petlog.talktalk.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.swp.petlog.R;
import com.swp.petlog.talktalk.WalkDetailActivity;
import com.swp.petlog.talktalk.data.WalkData;

import java.util.ArrayList;


public class WalkAdapter extends RecyclerView.Adapter<WalkAdapter.ViewHolder> {

    private ArrayList<WalkData> mList = null;
    private Activity context = null;


    public WalkAdapter(Activity context, ArrayList<WalkData> list) {
        this.context = context;
        this.mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.talktalk_walk_item, null);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override //리사이클러뷰 리스트 표시하는곳
    public void onBindViewHolder(@NonNull ViewHolder viewholder, final int position) {

        Glide.with(context).load(mList.get(position).getWalk_img()).into(viewholder.img);
        //사이즈,위치 정할수있음.
        //viewholder.id.setText(mList.get(position).getShare_id());
        viewholder.title.setText(mList.get(position).getWalk_title());
        viewholder.nickname.setText(mList.get(position).getWalk_nickname());
        viewholder.date.setText(mList.get(position).getWalk_date());

        viewholder.title.setTextSize(20);
        viewholder.nickname.setTextSize(15);
        viewholder.nickname.setGravity(Gravity.LEFT);

        viewholder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context= v.getContext();

                Intent intent = new Intent(v.getContext(), WalkDetailActivity.class);

                //여기 name에 있는 값과 detail액티비티의 key값과 동일해야함!!! ----Data에 있는 값이 name 으로 넘어감
                intent.putExtra("id",mList.get(position).getWalk_id());
                intent.putExtra("title",mList.get(position).getWalk_title());
                intent.putExtra("content",mList.get(position).getWalk_content());
                intent.putExtra("nickname",mList.get(position).getWalk_nickname());
                intent.putExtra("walkimg", mList.get(position).getWalk_img());
                intent.putExtra("date",mList.get(position).getWalk_date());
                //산책로주소이름
                intent.putExtra("walktitle",mList.get(position).getWalk_positiontitle());
                intent.putExtra("posx",mList.get(position).getWalk_posx());
                intent.putExtra("posy",mList.get(position).getWalk_posy());



                //intent.putExtra("img",mList.get(position).getShare_img());


                context.startActivity(intent);
              //  Toast.makeText(context,position +"번째 아이템 클릭",Toast.LENGTH_LONG).show(); //몇번째 아이템 클릭됐는지 토스트로 알려줌
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
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
            this.img=(ImageView) view.findViewById(R.id.walklist_img);
        }
        @Override
        public void onClick(View v){
            System.out.println(getPosition());
            Intent intent = new Intent(v.getContext(), WalkDetailActivity.class);
            v.getContext().startActivity(intent);
        }
    }
    @Override
    public int getItemCount() {
        return (mList.size());
    }

}