package com.example.appstore.view.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.appstore.model.CategoriesItem;
import com.example.appstore.view.SubCategoryListFragment;

import java.util.List;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private List<CategoriesItem> mCategoriesItems;

    public PagerAdapter(@NonNull FragmentManager fm,  List<CategoriesItem> categoriesItems) {
        super(fm);
        this.mCategoriesItems = categoriesItems;
    }

    /*public PagerAdapter(@NonNull FragmentActivity fragmentActivity,  List<CategoriesItem> categoriesItems) {
        super(fragmentActivity);
        this.mCategoriesItems = categoriesItems;
    }*/

    public void setList(List<CategoriesItem> list) {
        mCategoriesItems = list;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return SubCategoryListFragment.newInstance(mCategoriesItems.get(position).getId());
    }

    @Override
    public int getCount() {
        return  mCategoriesItems.size();
    }
}
