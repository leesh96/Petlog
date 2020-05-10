package swpj.petlog.petlog2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<String> nameList;
    private ArrayList<String> sexList;
    private ArrayList<String> speciesList;
    private ArrayList<String> ageList;
    private ArrayList<String> bdayList;

    public ViewPagerAdapter(Context context, ArrayList<String> nameList, ArrayList<String> sexList, ArrayList<String> speciesList, ArrayList<String> ageList, ArrayList<String> bdayList) {
        this.mContext = context;
        this.nameList = nameList;
        this.sexList = sexList;
        this.speciesList = speciesList;
        this.ageList = ageList;
        this.bdayList = bdayList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.mypet_showinfo, container, false);

        EditText editTextName = view.findViewById(R.id.et_petName);
        EditText editTextSex = view.findViewById(R.id.et_petSex);
        EditText editTextSpecies = view.findViewById(R.id.et_petSpecie);
        EditText editTextAge = view.findViewById(R.id.et_petAge);
        EditText editTextBday = view.findViewById(R.id.et_petBday);

        editTextName.setText(nameList.get(position));
        editTextSex.setText(sexList.get(position));
        editTextSpecies.setText(speciesList.get(position));
        editTextAge.setText(ageList.get(position));
        editTextBday.setText(bdayList.get(position));

        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return nameList.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view == (View)o);
    }
}
