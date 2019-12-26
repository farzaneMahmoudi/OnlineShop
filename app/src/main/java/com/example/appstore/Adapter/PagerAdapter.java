package com.example.appstore.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.appstore.Model.CategoriesItem;
import com.example.appstore.Model.ResponseModel;
import com.example.appstore.View.ProductListFragment;

import java.util.List;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int mNumOfTabs;
    private List<CategoriesItem> mCategoriesItems;

    public void setList(List<CategoriesItem> list) {
        mCategoriesItems = list;
    }

    public PagerAdapter(@NonNull FragmentManager fm, int numOfTabs, List<CategoriesItem> categoriesItems) {
        super(fm);
        this.mNumOfTabs = numOfTabs;
        this.mCategoriesItems = categoriesItems;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return ProductListFragment.newInstance(mCategoriesItems.get(position).getId());
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
