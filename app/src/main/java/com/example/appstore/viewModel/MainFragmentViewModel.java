package com.example.appstore.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.appstore.model.ResponseModel;
import com.example.appstore.network.AppRepository;

import java.io.IOException;
import java.util.List;

public class MainFragmentViewModel extends AndroidViewModel {

    private AppRepository mAppRepository;
    private MutableLiveData<List<ResponseModel>> mRecentPro;
    private MutableLiveData<List<ResponseModel>> mMostVisitedPro;
    private MutableLiveData<List<ResponseModel>> mBestPro;
    private List<String> mPhotoUrls;

    public List<String> getPhotoUrls() {
        return mPhotoUrls;
    }

    public MainFragmentViewModel(@NonNull Application application) {
        super(application);

        mAppRepository = AppRepository.getInstance();
        mBestPro = (MutableLiveData<List<ResponseModel>>) mAppRepository.getListBestPro();
        mMostVisitedPro = (MutableLiveData<List<ResponseModel>>) mAppRepository.getListMostVisitedPro();
        mRecentPro = (MutableLiveData<List<ResponseModel>>) mAppRepository.getListRecentPro();
        mPhotoUrls = mAppRepository.getUrls();
    }

    public MutableLiveData<List<ResponseModel>> getRecentPro() {
        return mRecentPro;
    }

    public MutableLiveData<List<ResponseModel>> getMostVisitedPro() {
        return mMostVisitedPro;
    }

    public MutableLiveData<List<ResponseModel>> getBestPro() {
        return mBestPro;
    }

    public void loadBestPro() throws IOException {
        mAppRepository.getBestProducts();
    }

    public void loadRecentPro() throws IOException {
        mAppRepository.getRecentProducts();
    }

    public void loadMostVisitedPro() throws IOException {
        mAppRepository.getMostVisitedProducts();
    }

}
