package com.example.appstore.viewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appstore.model.CategoriesItem;
import com.example.appstore.model.Category;
import com.example.appstore.model.ResponseModel;
import com.example.appstore.network.AppRepository;
import java.io.IOException;
import java.util.List;

public class ProductsOfCategoryFragmentViewModel extends AndroidViewModel  {
    private AppRepository mAppRepository;
    private MutableLiveData<List<ResponseModel>> mProList;

    public ProductsOfCategoryFragmentViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = AppRepository.getInstance();
    }

    public LiveData<List<ResponseModel>> getCatProList(int id) throws IOException {
        return mAppRepository.getProductPerCategory(id);
    }


    public LiveData<List<ResponseModel>> getCatProListPerPage(int id,int pageNum) throws IOException {
        return mAppRepository.getProductPerCategoryPerPage(id,pageNum);
    }

}
