package com.example.appstore.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.appstore.model.ResponseModel;
import com.example.appstore.network.AppRepository;
import java.io.IOException;

public class DetailProductFragmentViewModel extends AndroidViewModel implements AppRepository.CallBack {

    private AppRepository mAppRepository;
    private MutableLiveData<ResponseModel> mResponseModel=new MutableLiveData<>();

    public DetailProductFragmentViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = AppRepository.getInstance();
        mAppRepository.setCallBack(this);

    }

    public MutableLiveData<ResponseModel> getResponseModel() {
        return mResponseModel;
    }

    public void loadData(int id) throws IOException {
         mAppRepository.getSpecificProducts(id);
    }

    @Override
    public void detailProCallBack(ResponseModel model) {
        mResponseModel.setValue(model);
    }

}
