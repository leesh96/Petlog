package com.swp.petlog.talktalk.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.swp.petlog.R;
import com.swp.petlog.talktalk.data.ShareCommentData;

import java.util.ArrayList;


public class ShareCommentAdapter extends RecyclerView.Adapter<ShareCommentAdapter.ViewHolder>{

    private ArrayList<ShareCommentData> mList;
    private Context mContext;

    public ShareCommentAdapter(ArrayList<ShareCommentData> mList, Context mContext) {
        this.mList = mList;
        this.mContext=mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.talktalk_share_comment_item,null);
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        protected TextView id;
        protected TextView nickname;
        protected TextView comment;
        protected ImageView profile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //this.profile=(ImageView)itemView.findViewById(R.id.profile);
            this.nickname=(TextView) itemView.findViewById(R.id.nickname);
            this.comment=(TextView) itemView.findViewById(R.id.comment);

            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem Edit = menu.add(Menu.NONE,1001,1,"편집");
            MenuItem Delete = menu.add(Menu.NONE,1002,2,"삭제");
            Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);
        }
        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()){
                    case 1001: //편집선택
                        AlertDialog.Builder builder= new AlertDialog.Builder(mContext);

                        View view = LayoutInflater.from(mContext)
                            .inflate(R.layout.talktalk_editcomment,null,false);
                         builder.setView(view);

                         final Button ButtonSubmit =(Button) view.findViewById(R.id.btn_dialog_submit);
                         final TextView editTextID=(TextView)view.findViewById(R.id.et_comment_dialog_id);
                         final TextView editTextNickname=(TextView)view.findViewById(R.id.et_comment_dialog_nickname);
                         final EditText editTextCommnet=(EditText)view.findViewById(R.id.et_comment_dialog_comment);

                        editTextID.setText(mList.get(getAdapterPosition()).getShare_id());
                        editTextCommnet.setText(mList.get(getAdapterPosition()).getShare_comment());
                        editTextNickname.setText(mList.get(getAdapterPosition()).getShare_nickname());

                        final AlertDialog dialog = builder.create();
                        ButtonSubmit.setOnClickListener(new View.OnClickListener(){
                            public void onClick(View v){
                                String strID=editTextID.getText().toString();
                                String strComment=editTextCommnet.getText().toString();
                                String strNickname=editTextNickname.getText().toString();

                                ShareCommentData scd =new ShareCommentData(strID,strComment,strNickname);
                                mList.set(getAdapterPosition(),scd);
                                notifyItemChanged(getAdapterPosition());

                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                        break;
                    case 1002:
                        mList.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(),mList.size());
                }
                return true;
            }};
        }
    }
