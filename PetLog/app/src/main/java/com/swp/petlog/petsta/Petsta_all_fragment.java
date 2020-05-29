package com.swp.petlog.petsta;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.swp.petlog.R;

public class Petsta_all_fragment extends Fragment {
    private View view;
    //private ArrayList<Content> contents;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.petsta_all_fragment, container, false);

        return view;
    }

    }

