package com.swp.petlog.talktalk.adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
import com.swp.petlog.PreferenceManager;
import com.swp.petlog.R;
import com.swp.petlog.mypet.AddMypetInfoActivity;
import com.swp.petlog.mypet.MypetMainActivity;
import com.swp.petlog.talktalk.data.ShareCommentData;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class ShareCommentAdapter extends RecyclerView.Adapter<ShareCommentAdapter.ViewHolder>{
    private static String mPHPURL = "http://128.199.106.86/modifyShareComment.php";
    private static String dPHPURL = "http://128.199.106.86/deleteShareComment.php";
    private static String TAG = "sharecomment";

    private ArrayList<ShareCommentData> mList;
    private Context mContext;

    public ShareCommentAdapter(ArrayList<ShareCommentData> mList, Context mContext) {
        this.mList = mList;
        this.mContext=mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.talktalk_share_commentitem,null);
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
            this.nickname = (TextView) itemView.findViewById(R.id.nickname);
            this.comment = (TextView) itemView.findViewById(R.id.comment);

            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            String usernick = PreferenceManager.getString(mContext, "userNick");
            String nickname = mList.get(getAdapterPosition()).getShare_nickname();

            if (usernick.equals(nickname)) {
                MenuItem Edit = menu.add(Menu.NONE, 1001, 1, "편집");
                MenuItem Delete = menu.add(Menu.NONE, 1002, 2, "삭제");
                Edit.setOnMenuItemClickListener(onEditMenu);
                Delete.setOnMenuItemClickListener(onEditMenu);
            }
            else return;
        }

        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case 1001: //편집선택
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                        View view = LayoutInflater.from(mContext)
                                .inflate(R.layout.talktalk_editcomment, null, false);
                        builder.setView(view);

                        final Button ButtonSubmit = (Button) view.findViewById(R.id.btn_dialog_submit);
                        final TextView editTextID = (TextView) view.findViewById(R.id.et_comment_dialog_id);
                        final TextView editTextNickname = (TextView) view.findViewById(R.id.et_comment_dialog_nickname);
                        final EditText editTextCommnet = (EditText) view.findViewById(R.id.et_comment_dialog_comment);

                        editTextID.setText(mList.get(getAdapterPosition()).getShare_id());
                        editTextCommnet.setText(mList.get(getAdapterPosition()).getShare_comment());
                        editTextNickname.setText(mList.get(getAdapterPosition()).getShare_nickname());

                        final AlertDialog dialog = builder.create();
                        ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                String strID = editTextID.getText().toString();
                                String strComment = editTextCommnet.getText().toString();
                                String strNickname = editTextNickname.getText().toString();
                                String postid = mList.get(getAdapterPosition()).getId();

                                modify(postid, strComment);

                                ShareCommentData scd = new ShareCommentData(strID, strComment, strNickname);
                                scd.setId(postid);
                                mList.set(getAdapterPosition(), scd);
                                notifyItemChanged(getAdapterPosition());

                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                        break;
                    case 1002:
                        String postid = mList.get(getAdapterPosition()).getId();

                        DeleteCom deleteCom = new DeleteCom();
                        deleteCom.execute(dPHPURL, postid);

                        mList.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(), mList.size());
                }
                return true;
            }
        };
    }

    class DeleteCom extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(mContext,
                    "Please Wait...", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            Log.d(TAG, "POST response  - " + result);
        }

        @Override
        protected String doInBackground(String... params) {

            String id = (String)params[1];

            String serverURL = (String)params[0];
            String postParameters = "id=" + id;

            try {
                URL url = new URL(serverURL);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString();

            } catch (Exception e) {
                Log.d(TAG, "InsertData: Error ", e);
                return new String("Error: " + e.getMessage());
            }
        }
    }

    public void modify(String postid, String comment) {
        SimpleMultiPartRequest smpr= new SimpleMultiPartRequest(Request.Method.POST, mPHPURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(mContext, "댓글수정", Toast.LENGTH_SHORT).show();
                Log.d("TAG", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "ERROR", Toast.LENGTH_SHORT).show();
                Log.d("TAG", error.toString());
            }
        });
        //요청 객체에 보낼 데이터를 추가
        smpr.addStringParam("id", postid);
        smpr.addStringParam("comment", comment);

        //요청객체를 서버로 보낼 우체통 같은 객체 생성
        RequestQueue requestQueue= Volley.newRequestQueue(mContext);
        requestQueue.add(smpr);
    }
}
