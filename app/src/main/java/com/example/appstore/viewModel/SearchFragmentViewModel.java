package com.example.appstore.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.appstore.model.ResponseModel;
import com.example.appstore.network.AppRepository;

import java.util.List;

public class SearchFragmentViewModel extends AndroidViewModel {

    private AppRepository mAppRepository;
    private MutableLiveData<List<ResponseModel>> mData;

    public MutableLiveData<List<ResponseModel>> getData() {
        return mData;
    }

    public SearchFragmentViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = AppRepository.getInstance();
        mData = new MutableLiveData<>();
    }

    public void getSearchQuery(String query){
     mData =    mAppRepository.FetchSearchProduct(query);
    }

}
