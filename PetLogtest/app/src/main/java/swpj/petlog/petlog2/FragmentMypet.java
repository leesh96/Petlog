package swpj.petlog.petlog2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class FragmentMypet extends Fragment {
    private TextView petname, petsex, petspecies, petage, petbday;
    private EditText editTextpetmemo;
    private ImageView imageViewpetface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.mypet_showinfo, container, false);

        petname = (TextView)view.findViewById(R.id.tv_petName);
        petsex = (TextView)view.findViewById(R.id.tv_petSex);
        petspecies = (TextView)view.findViewById(R.id.tv_petSpecie);
        petage = (TextView)view.findViewById(R.id.tv_petAge);
        petbday = (TextView)view.findViewById(R.id.tv_petBday);

        editTextpetmemo = (EditText)view.findViewById(R.id.mypet_memo);

        imageViewpetface = (ImageView)view.findViewById(R.id.petface);

        return view;
    }

    public void setPetnameValue(String string) {
        petname.setText(string);
    }

    public void setPetsexValue(String string) {
        petsex.setText(string);
    }

    public void setPetspeciesValue(String string) {
        petspecies.setText(string);
    }

    public void setPetageValue(String string) {
        petage.setText(string);
    }

    public void setPetbdayValue(String string) {
        petbday.setText(string);
    }
}
