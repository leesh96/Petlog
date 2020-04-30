package com.example.test02.ui.push;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.test02.R;

public class PushFragment extends Fragment {

    private PushViewModel pushViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        pushViewModel =
                ViewModelProviders.of(this).get(PushViewModel.class);
        View root = inflater.inflate(R.layout.fragment_push, container, false);
        final TextView textView = root.findViewById(R.id.text_push);
        pushViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
