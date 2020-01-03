package com.example.appstore.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appstore.model.ResponseModel;
import com.example.appstore.network.AppRepository;

import java.io.IOException;
import java.util.List;


import static com.example.appstore.view.NavProListFragment.BEST_PRODUCT;
import static com.example.appstore.view.NavProListFragment.MOST_VISITED;
import static com.example.appstore.view.NavProListFragment.RECENT_PRODUCT;

public class NavProListFragmentViewModel extends AndroidViewModel {
    private AppRepository mAppRepository;

    private MutableLiveData<List<ResponseModel>> mRecentProList;
    private MutableLiveData<List<ResponseModel>> mBestProList;
    private MutableLiveData<List<ResponseModel>> mMostVisitedProList;

    public LiveData<List<ResponseModel>> getRecentProList() {
        return mRecentProList;
    }

    public LiveData<List<ResponseModel>> getBestProList() {
        return mBestProList;
    }

    public LiveData<List<ResponseModel>> getMostVisitedProList() {
        return mMostVisitedProList;
    }

    public NavProListFragmentViewModel(@NonNull Application application) throws IOException {
        super(application);
        mAppRepository = AppRepository.getInstance();
        mBestProList = (MutableLiveData<List<ResponseModel>>) mAppRepository.getListBestPro();
        mRecentProList = (MutableLiveData<List<ResponseModel>>) mAppRepository.getListRecentPro();
        mMostVisitedProList = (MutableLiveData<List<ResponseModel>>) mAppRepository.getListMostVisitedPro();
    }

    public void loadProList(String whichPro) throws IOException {
        if (whichPro.equals(BEST_PRODUCT))
            mAppRepository.getBestProducts();
        else if (whichPro.equals(RECENT_PRODUCT))
            mAppRepository.getRecentProducts();
        else if (whichPro.equals(MOST_VISITED))
            mAppRepository.getMostVisitedProducts();
    }
}
