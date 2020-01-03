package com.example.appstore.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.appstore.model.ResponseModel;
import com.example.appstore.network.AppRepository;

import java.io.IOException;


public class DetailProductFragmentViewModel extends AndroidViewModel implements AppRepository.CallBack {

    private AppRepository mAppRepository;
    private ResponseModel mData;

    public DetailProductFragmentViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = AppRepository.getInstance();
        mData = mAppRepository.getResponseModel();
        mAppRepository.setCallBack(this);
    }

    public ResponseModel getData() {
        return mData;
    }

    public void loadData(int id) throws IOException {
         mAppRepository.getSpecificProducts(id);
    }

    @Override
    public void detailProCallBack(ResponseModel model) {

    }
}
