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
import java.util.ArrayList;
import java.util.List;

public class CategoryActivityViewModel extends AndroidViewModel {
    private AppRepository mAppRepository;
   private MutableLiveData<List<CategoriesItem>> mCategoriesList;

   //add
   private LiveData<List<CategoriesItem>> mCategoriesListLiveData=new MutableLiveData<List<CategoriesItem>>();
    private MutableLiveData<List<CategoriesItem>> mParentCategoriesLiveData = new MutableLiveData<List<CategoriesItem>>();
    private MutableLiveData<List<CategoriesItem>> mSubCategoriesLiveData = new MutableLiveData<>();
    private MutableLiveData<List<CategoriesItem>> mSubCategoriesLiveData2 = new MutableLiveData<>();


    public MutableLiveData<List<CategoriesItem>> getCategoriesList() {
        return mCategoriesList;
    }

    public CategoryActivityViewModel(@NonNull Application application) throws IOException {
        super(application);
        mAppRepository =AppRepository.getInstance();
    /*    mAppRepository.getCategories();
        mCategoriesList = (MutableLiveData<List<CategoriesItem>>) mAppRepository.getListCategories();*/


        //add
        mCategoriesListLiveData = mAppRepository.getCategoryItemsLiveData();
        setParentCategoriesLiveData();
        setSubCategoriesLiveData();
        mSubCategoriesLiveData2 = mAppRepository.getSubCategoryItemsLiveData();

    }

//add
public LiveData<List<CategoriesItem>> getCategoriesListLiveData() {
    return mCategoriesListLiveData;
}

    public MutableLiveData<List<CategoriesItem>> getParentCategoriesLiveData() {
        return mParentCategoriesLiveData;
    }

    public MutableLiveData<List<CategoriesItem>> getSubCategoriesLiveData() {
        return mSubCategoriesLiveData;
    }

    public MutableLiveData<List<CategoriesItem>> getSubCategoriesLiveData2() {
        return mSubCategoriesLiveData2;
    }

    private void setParentCategoriesLiveData() {
        List<CategoriesItem> parentCategories = new ArrayList<>();
        for (int i = 0; i < mCategoriesListLiveData.getValue().size(); i++) {
            if (mCategoriesListLiveData.getValue().get(i).getParent() == 0) {
                if(mCategoriesListLiveData.getValue().get(i).getName().equals("فروش ویژه"))
                    continue;
                else
                parentCategories.add(mCategoriesListLiveData.getValue().get(i));
            }
        }
        mParentCategoriesLiveData.setValue(parentCategories);
    }

    private void setSubCategoriesLiveData() {
        List<CategoriesItem> subCategories = new ArrayList<>();

        for (int j = 0; j < mParentCategoriesLiveData.getValue().size(); j++) {
            for (int i = 0; i < mCategoriesListLiveData.getValue().size(); i++) {
                if (mCategoriesListLiveData.getValue().get(i).getParent() == mParentCategoriesLiveData.getValue().get(j).getId())
                    subCategories.add(mCategoriesListLiveData.getValue().get(i));
            }
        }
        mSubCategoriesLiveData.setValue(subCategories);
    }


    public MutableLiveData<List<CategoriesItem>> getSubCategoriesByParentIDLiveData(int parentId) {
        List<CategoriesItem> subCategories = new ArrayList<>();
        for(int i =0; i<mSubCategoriesLiveData2.getValue().size(); i++)
        {
            if(mSubCategoriesLiveData2.getValue().get(i).getParent()== parentId)
                subCategories.add(mSubCategoriesLiveData2.getValue().get(i));
        }
        MutableLiveData<List<CategoriesItem>> subCategoriesLiveData = new MutableLiveData<>();
        subCategoriesLiveData.setValue(subCategories);
        return subCategoriesLiveData;
    }


}
