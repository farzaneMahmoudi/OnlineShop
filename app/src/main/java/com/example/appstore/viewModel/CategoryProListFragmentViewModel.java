package com.example.appstore.viewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appstore.model.CategoriesItem;
import com.example.appstore.model.Category;
import com.example.appstore.network.AppRepository;
import java.io.IOException;
import java.util.List;

public class CategoryProListFragmentViewModel extends AndroidViewModel  {
    private AppRepository mAppRepository;
    private MutableLiveData<List<Category>> mProList;

    public LiveData<List<Category>> getProList() {
        return mProList;
    }

    public CategoryProListFragmentViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = AppRepository.getInstance();
       // mProList = mAppRepository.getListCategory();
    }

    public LiveData<List<Category>> loadCatProList(int id) throws IOException {
        return mAppRepository.getSubCatList(id);
    }
   /* public void loadCatProList(int id) throws IOException {
         mAppRepository.getSubCatList(id);
    }*/
}
