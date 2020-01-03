package com.example.appstore.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.appstore.network.AppRepository;

public class CategoryActivityViewModel extends AndroidViewModel {
    private AppRepository mAppRepository;

    public CategoryActivityViewModel(@NonNull Application application) {
        super(application);
        mAppRepository =AppRepository.getInstance();

    }
}
