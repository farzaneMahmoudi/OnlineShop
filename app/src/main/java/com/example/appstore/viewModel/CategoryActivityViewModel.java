package com.example.appstore.viewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.appstore.model.CategoriesItem;
import com.example.appstore.model.Category;
import com.example.appstore.network.AppRepository;
import java.io.IOException;
import java.util.List;

public class CategoryActivityViewModel extends AndroidViewModel {
    private AppRepository mAppRepository;
   private MutableLiveData<List<CategoriesItem>> mCategoriesList;

    public MutableLiveData<List<CategoriesItem>> getCategoriesList() {
        return mCategoriesList;
    }

    public CategoryActivityViewModel(@NonNull Application application) throws IOException {
        super(application);
        mAppRepository =AppRepository.getInstance();
        mAppRepository.getCategories();
        mCategoriesList = (MutableLiveData<List<CategoriesItem>>) mAppRepository.getListCategories();

    }

}
