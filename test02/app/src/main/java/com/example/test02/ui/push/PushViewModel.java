package com.example.test02.ui.push;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PushViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PushViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("PUSH");
    }

    public LiveData<String> getText() {
        return mText;
    }
}