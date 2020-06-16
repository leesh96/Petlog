package com.swp.petlog.petsta.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.swp.petlog.PreferenceManager;
import com.swp.petlog.R;
import com.swp.petlog.petsta.Comment_fragment;
import com.swp.petlog.petsta.PetstaMain;
import com.swp.petlog.petsta.UserProfile_fragment;
import com.swp.petlog.petsta.data.PetstaPostData;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PetstaPostAdapter extends RecyclerView.Adapter<PetstaPostAdapter.CustomViewHolder> {
    private ArrayList<PetstaPostData> mList = null;
    private Activity context = null;

    private static String mPHPURL = "http://128.199.106.86/makeLike.php";
    private static String uPHPURL = "http://128.199.106.86/updateLike.php";

    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }

    private OnItemClickListener mListener = null;

    public void setOnItemClickListener (OnItemClickListener listener) {
        this.mListener = listener;
    }

    public PetstaPostAdapter(Activity context, ArrayList<PetstaPostData> list) {
        this.mList = list;
        this.context = context;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewface, imageViewpostimg, imageViewlike, imageViewcomment;
        private TextView textViewwriter, textViewtime, textViewlikecnt, textViewcommentcnt, textViewtag, textViewcontent;

        public CustomViewHolder(View view) {
            super(view);

            this.imageViewface = (ImageView) view.findViewById(R.id.writer_face);
            this.imageViewpostimg = (ImageView) view.findViewById(R.id.post_image);
            this.imageViewlike = (ImageView) view.findViewById(R.id.post_like);
            this.imageViewcomment = (ImageView) view.findViewById(R.id.post_comment);

            this.textViewwriter = (TextView) view.findViewById(R.id.writer_name);
            this.textViewtime = (TextView) view.findViewById(R.id.post_time);
            this.textViewlikecnt = (TextView) view.findViewById(R.id.post_likecnt);
            this.textViewcommentcnt = (TextView) view.findViewById(R.id.post_commentcnt);
            this.textViewtag = (TextView) view.findViewById(R.id.post_tag);
            this.textViewcontent = (TextView) view.findViewById(R.id.post_contents);

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
    public PetstaPostAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.petsta_post, viewGroup, false);
        PetstaPostAdapter.CustomViewHolder viewHolder = new PetstaPostAdapter.CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PetstaPostAdapter.CustomViewHolder viewHolder, final int position) {

        Glide.with(context).load(mList.get(position).getMember_image()).into(viewHolder.imageViewpostimg);

        if (!mList.get(position).getMember_face().equals("http://128.199.106.86/null")) {
            Glide.with(context).load(mList.get(position).getMember_face()).into(viewHolder.imageViewface);
        }

        switch (mList.get(position).getMember_liked()) {
            case 1:
                viewHolder.imageViewlike.setImageResource(R.drawable.ic_good);
                break;
            case 0:
                viewHolder.imageViewlike.setImageResource(R.drawable.ic_like);
                break;
            default:
                viewHolder.imageViewlike.setImageResource(R.drawable.ic_like);
                break;
        }

        final String nickname = PreferenceManager.getString(context, "userNick");

        viewHolder.imageViewlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mList.get(position).getMember_liked()) {
                    case 1:
                        int int_like_cnt = mList.get(position).getMember_likecnt() - 1;
                        String str_like_cnt = Integer.toString(int_like_cnt);
                        mList.get(position).setMember_liked(0);
                        mList.get(position).setMember_likecnt(int_like_cnt);
                        viewHolder.imageViewlike.setImageResource(R.drawable.ic_like);
                        viewHolder.textViewlikecnt.setText(str_like_cnt);
                        UpdateLike task1 = new UpdateLike();
                        task1.execute(uPHPURL, nickname, Integer.toString(mList.get(position).getMember_id()), str_like_cnt, Integer.toString(mList.get(position).getMember_liked()));
                        break;
                    case 0:
                        int_like_cnt = mList.get(position).getMember_likecnt() + 1;
                        str_like_cnt = Integer.toString(int_like_cnt);
                        mList.get(position).setMember_liked(1);
                        mList.get(position).setMember_likecnt(int_like_cnt);
                        viewHolder.imageViewlike.setImageResource(R.drawable.ic_good);
                        viewHolder.textViewlikecnt.setText(str_like_cnt);
                        UpdateLike task2 = new UpdateLike();
                        task2.execute(uPHPURL, nickname, Integer.toString(mList.get(position).getMember_id()), str_like_cnt, Integer.toString(mList.get(position).getMember_liked()));
                        break;
                    case 2:
                        int_like_cnt = mList.get(position).getMember_likecnt() + 1;
                        str_like_cnt = Integer.toString(int_like_cnt);
                        mList.get(position).setMember_liked(1);
                        mList.get(position).setMember_likecnt(int_like_cnt);
                        viewHolder.imageViewlike.setImageResource(R.drawable.ic_good);
                        viewHolder.textViewlikecnt.setText(str_like_cnt);
                        MakeLike makeLike = new MakeLike();
                        makeLike.execute(mPHPURL, nickname, Integer.toString(mList.get(position).getMember_id()), str_like_cnt, Integer.toString(mList.get(position).getMember_liked()));
                        break;
                }
            }
        });

        viewHolder.imageViewcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Comment_fragment();

                Bundle bundle = new Bundle();
                bundle.putString("postid", Integer.toString(mList.get(position).getMember_id()));
                fragment.setArguments(bundle);

                ((PetstaMain)context).replaceFragment(fragment);
            }
        });

        viewHolder.textViewwriter.setText(mList.get(position).getMember_nickname());
        viewHolder.textViewwriter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nickname.equals(mList.get(position).getMember_nickname())) {
                    //((PetstaMain)context).setPetsta(3);
                    BottomNavigationView bottomNavigationView = ((PetstaMain) context).bottomNavigationView;
                    bottomNavigationView.setSelectedItemId(R.id.action_myprofile);
                }
                else {
                    Fragment fragment = new UserProfile_fragment();

                    Bundle bundle = new Bundle();
                    bundle.putString("nickname", mList.get(position).getMember_nickname());
                    fragment.setArguments(bundle);

                    ((PetstaMain)context).replaceFragment(fragment);
                }
            }
        });
        viewHolder.imageViewface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nickname.equals(mList.get(position).getMember_nickname())) {
                    //((PetstaMain)context).setPetsta(3);
                    BottomNavigationView bottomNavigationView = ((PetstaMain) context).bottomNavigationView;
                    bottomNavigationView.setSelectedItemId(R.id.action_myprofile);
                }
                else {
                    Fragment fragment = new UserProfile_fragment();

                    Bundle bundle = new Bundle();
                    bundle.putString("nickname", mList.get(position).getMember_nickname());
                    fragment.setArguments(bundle);

                    ((PetstaMain)context).replaceFragment(fragment);
                }
            }
        });

        viewHolder.textViewtime.setText(mList.get(position).getMember_writetime());
        viewHolder.textViewlikecnt.setText(Integer.toString(mList.get(position).getMember_likecnt()));
        viewHolder.textViewcommentcnt.setText(Integer.toString(mList.get(position).getMember_commentcnt()));
        viewHolder.textViewcontent.setText(mList.get(position).getMember_content());
        int tag = mList.get(position).getMember_tag();

        switch (tag) {
            case 10:
                viewHolder.textViewtag.setText("#푸들");
                break;
            case 11:
                viewHolder.textViewtag.setText("#말티즈");
                break;
            case 12:
                viewHolder.textViewtag.setText("#웰시코기");
                break;
            case 13:
                viewHolder.textViewtag.setText("#폼피츠");
                break;
            case 14:
                viewHolder.textViewtag.setText("#포메라니안");
                break;
            case 15:
                viewHolder.textViewtag.setText("#비숑");
                break;
            case 16:
                viewHolder.textViewtag.setText("#치와와");
                break;
            case 20:
                viewHolder.textViewtag.setText("#샴");
                break;
            case 21:
                viewHolder.textViewtag.setText("#페르시안");
                break;
            case 22:
                viewHolder.textViewtag.setText("#러시안블루");
                break;
            case 23:
                viewHolder.textViewtag.setText("#스코티쉬폴드");
                break;
            case 24:
                viewHolder.textViewtag.setText("#뱅갈");
                break;
            case 25:
                viewHolder.textViewtag.setText("#노르웨이 숲");
                break;
            case 26:
                viewHolder.textViewtag.setText("#아메리칸 숏헤어");
                break;
            default:
                viewHolder.textViewtag.setText("태그없음");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

    class MakeLike extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            /*progressDialog = ProgressDialog.show(context,
                    "Please Wait", null, true, true);*/
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //progressDialog.dismiss();
            Log.d("like", "POST response  - " + result);
        }

        @Override
        protected String doInBackground(String... params) {

            String serverURL = (String)params[0];
            String nickname = (String)params[1];
            String id = (String)params[2];
            String like_cnt = (String)params[3];
            String isliked = (String)params[4];

            String postParameters = "nickname=" + nickname + "&postid=" + id + "&likecnt=" + like_cnt + "&isliked=" + isliked;

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
                Log.d("like", "POST response code - " + responseStatusCode);

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
                Log.d("like", "InsertData: Error ", e);
                return new String("Error: " + e.getMessage());
            }
        }
    }

    class UpdateLike extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            /*progressDialog = ProgressDialog.show(context,
                    "Please Wait", null, true, true);*/
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //progressDialog.dismiss();
            Log.d("like", "POST response  - " + result);
        }

        @Override
        protected String doInBackground(String... params) {

            String serverURL = (String)params[0];
            String nickname = (String)params[1];
            String id = (String)params[2];
            String like_cnt = (String)params[3];
            String isliked = (String)params[4];

            String postParameters = "nickname=" + nickname + "&postid=" + id + "&likecnt=" + like_cnt + "&isliked=" + isliked;

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
                Log.d("like", "POST response code - " + responseStatusCode);

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
                Log.d("like", "InsertData: Error ", e);
                return new String("Error: " + e.getMessage());
            }
        }
    }
}
