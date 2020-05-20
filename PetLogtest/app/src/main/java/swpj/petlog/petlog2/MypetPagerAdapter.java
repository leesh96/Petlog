package swpj.petlog.petlog2;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

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

public class MypetPagerAdapter extends PagerAdapter {
    private Context mContext = null;
    private ArrayList<MypetData> mList = null;
    private String jsonString;


    public MypetPagerAdapter(Context mContext, ArrayList<MypetData> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = null;

        if (mContext != null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.mypet_showinfo, container, false);

            ImageView imageViewface = (ImageView) view.findViewById(R.id.petface);
            TextView textViewname = (TextView) view.findViewById(R.id.tv_petName);
            TextView textViewsex = (TextView) view.findViewById(R.id.tv_petSex);
            TextView textViewspecie = (TextView) view.findViewById(R.id.tv_petSpecie);
            TextView textViewage = (TextView) view.findViewById(R.id.tv_petAge);
            TextView textViewbday = (TextView) view.findViewById(R.id.tv_petBday);
            ImageButton imageButtonMemoApply = (ImageButton) view.findViewById(R.id.btn_memoapply);
            ImageButton imageButtonMemoDel = (ImageButton) view.findViewById(R.id.btn_memodel);
            final EditText editTextMemo = (EditText) view.findViewById(R.id.mypet_memo);

            imageViewface.setImageResource(R.drawable.ic_gallary);
            textViewname.setText(mList.get(position).getMember_name());
            textViewsex.setText(mList.get(position).getMember_sex());
            textViewspecie.setText(mList.get(position).getMember_specie());
            textViewage.setText(mList.get(position).getMember_age());
            textViewbday.setText(mList.get(position).getMember_bday());
            int petid = mList.get(position).getMember_id();
            String stringId = Integer.toString(petid);

            imageButtonMemoApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            imageButtonMemoDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // 뷰페이저에서 삭제.
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        // 전체 페이지 수는 10개로 고정.
        return (null != mList ? mList.size() : 0);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (View)object);
    }
}
