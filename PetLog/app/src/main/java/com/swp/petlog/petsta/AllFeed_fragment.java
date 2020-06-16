package com.swp.petlog.petsta;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.swp.petlog.MainActivity;
import com.swp.petlog.PreferenceManager;
import com.swp.petlog.R;
import com.swp.petlog.petsta.adapter.PetstaPostAdapter;
import com.swp.petlog.petsta.data.PetstaPostData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AllFeed_fragment extends Fragment {
    private static String PHPURL = "http://128.199.106.86/getAllFeedPost.php";
    private static String TAG = "petstapost";

    private ImageButton btn_home, btn_search, btn_align;

    private RecyclerView recyclerViewpetsta;
    private String jsonString;
    private ArrayList<PetstaPostData> arrayList;
    private PetstaPostAdapter adapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private String align = "";

    private AlertDialog isdialog;
    private AlertDialog speciedialog;

    private String[] iswhat = {"강아지", "고양이"};
    private String[] dogspecie = {"푸들", "말티즈", "웰시코기", "폼피츠", "포메라니안", "비숑", "치와와"};
    private String[] catspecie = {"샴", "페르시안", "러시안블루", "스코티쉬폴드", "뱅갈", "노르웨이 숲", "아메리칸 숏헤어"};

    public static AllFeed_fragment newInstance() {
        return new AllFeed_fragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.petsta_allfeed, container, false);

        final String nickname = PreferenceManager.getString(getActivity(), "userNick");
        btn_home = (ImageButton) rootView.findViewById(R.id.btn_home);
        btn_align = (ImageButton) rootView.findViewById(R.id.btn_align);
        btn_search = (ImageButton) rootView.findViewById(R.id.btn_search);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_allfeed);

        /*btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().remove(AllFeed_fragment.this).commit();
                fragmentManager.popBackStack();
                Log.d("count", Integer.toString(fragmentManager.getBackStackEntryCount()));
                if (fragmentManager.getBackStackEntryCount() == 0) {
                    getActivity().finish();
                }
            }
        });*/

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        btn_align.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final GetData task = new GetData();

                Context wrapper = new ContextThemeWrapper(getActivity(), R.style.mypetmenustyle);
                PopupMenu popupMenu = new PopupMenu(getActivity(), v);
                final MenuInflater inflater = popupMenu.getMenuInflater();
                Menu menu = popupMenu.getMenu();

                inflater.inflate(R.menu.petsta_align, menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.align_time:
                                align = "";
                                arrayList.clear();
                                adapter.notifyDataSetChanged();
                                task.execute(PHPURL, align, nickname);
                                break;
                            case R.id.align_like:
                                align = "like";
                                arrayList.clear();
                                adapter.notifyDataSetChanged();
                                task.execute(PHPURL, align, nickname);
                                break;
                            case R.id.align_tag:
                                isdialog = new AlertDialog.Builder(getActivity())
                                        .setItems(iswhat, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (which == 0) {
                                                    speciedialog = new AlertDialog.Builder(getActivity())
                                                            .setItems(dogspecie, new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    if (which == 0) {
                                                                        align = "10";
                                                                    }
                                                                    if (which == 1) {
                                                                        align = "11";
                                                                    }
                                                                    if (which == 2) {
                                                                        align = "12";
                                                                    }
                                                                    if (which == 3) {
                                                                        align = "13";
                                                                    }
                                                                    if (which == 4) {
                                                                        align = "14";
                                                                    }
                                                                    if (which == 5) {
                                                                        align = "15";
                                                                    }
                                                                    if (which == 6) {
                                                                        align = "16";
                                                                    }
                                                                    arrayList.clear();
                                                                    adapter.notifyDataSetChanged();
                                                                    task.execute(PHPURL, align, nickname);
                                                                }
                                                            })
                                                            .setTitle("강아지의 종이 무엇인가요?")
                                                            .setPositiveButton("확인", null)
                                                            .setNegativeButton("취소", null)
                                                            .create();
                                                    speciedialog.show();
                                                }
                                                if (which == 1) {
                                                    speciedialog = new AlertDialog.Builder(getActivity())
                                                            .setItems(catspecie, new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    if (which == 0) {
                                                                        align = "20";
                                                                    }
                                                                    if (which == 1) {
                                                                        align = "21";
                                                                    }
                                                                    if (which == 2) {
                                                                        align = "22";
                                                                    }
                                                                    if (which == 3) {
                                                                        align = "23";
                                                                    }
                                                                    if (which == 4) {
                                                                        align = "24";
                                                                    }
                                                                    if (which == 5) {
                                                                        align = "25";
                                                                    }
                                                                    if (which == 6) {
                                                                        align = "26";
                                                                    }
                                                                    arrayList.clear();
                                                                    adapter.notifyDataSetChanged();
                                                                    task.execute(PHPURL, align, nickname);
                                                                }
                                                            })
                                                            .setTitle("고양이의 종이 무엇인가요?")
                                                            .setPositiveButton("확인", null)
                                                            .setNegativeButton("취소", null)
                                                            .create();
                                                    speciedialog.show();
                                                }
                                            }
                                        })
                                        .setTitle("강아지인가요 고양이인가요?")
                                        .setPositiveButton("확인", null)
                                        .setNegativeButton("취소", null)
                                        .create();
                                isdialog.show();
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();

            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PetstaMain)getActivity()).replaceFragment(UserSearch_fragment.newInstance());
            }
        });

        recyclerViewpetsta = (RecyclerView) rootView.findViewById(R.id.allfeed_rcview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewpetsta.setLayoutManager(layoutManager);

        arrayList = new ArrayList<>();

        adapter = new PetstaPostAdapter(getActivity(), arrayList);
        recyclerViewpetsta.setAdapter(adapter);



        GetData task = new GetData();
        task.execute(PHPURL, align, nickname);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(AllFeed_fragment.this).attach(AllFeed_fragment.this).commit();
                getActivity().overridePendingTransition(R.anim.slide_out_down, R.anim.slide_out_down);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        return rootView;
    }

    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(getActivity(),
                    "Please Wait...", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            //mTextViewResult.setText(result);
            Log.d(TAG, "response - " + result);

            if (result == null){
                //mTextViewResult.setText(errorString);
            }
            else {
                jsonString = result;
                showResult();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String align = params[1];
            String nickname = params[2];
            String postParameters = "align=" + align + "&nickname=" + nickname;

            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

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
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }

    private void showResult() {
        String TAG_JSON = "allpost";
        String TAG_ID = "id";
        String TAG_NICK = "nickname";
        String TAG_IMG = "imgurl";
        String TAG_CON = "contents";
        String TAG_TAG = "tag";
        String TAG_TIME = "time";
        String TAG_LIKE = "likecnt";
        String TAG_COMMENT = "commentcnt";
        String TAG_FACE = "writerface";
        String TAG_ISLIKE = "islike";

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                int id = Integer.parseInt(item.getString(TAG_ID));
                String nickname = item.getString(TAG_NICK);
                String imgurl = "http://128.199.106.86/" + item.getString(TAG_IMG);
                String contents = item.getString(TAG_CON);
                int tag = Integer.parseInt(item.getString(TAG_TAG));
                String writetime = item.getString(TAG_TIME);
                int likecnt = Integer.parseInt(item.getString(TAG_LIKE));
                int commentcnt = Integer.parseInt(item.getString(TAG_COMMENT));
                String writerface = "http://128.199.106.86/" + item.getString(TAG_FACE);
                String islike = item.getString(TAG_ISLIKE);
                int userliked;

                Log.d("userliked", islike);

                if (islike.equals("1")) {
                        userliked = 1;
                } else if (islike.equals("0")) {
                    userliked = 0;
                } else {
                    userliked = 2;
                }

                PetstaPostData petstaPostData = new PetstaPostData();

                petstaPostData.setMember_id(id);
                petstaPostData.setMember_nickname(nickname);
                petstaPostData.setMember_image(imgurl);
                petstaPostData.setMember_content(contents);
                petstaPostData.setMember_tag(tag);
                petstaPostData.setMember_writetime(writetime);
                petstaPostData.setMember_likecnt(likecnt);
                petstaPostData.setMember_commentcnt(commentcnt);
                petstaPostData.setMember_face(writerface);
                petstaPostData.setMember_liked(userliked);

                arrayList.add(petstaPostData);
                adapter.notifyDataSetChanged();

            }

        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }
    }

}

