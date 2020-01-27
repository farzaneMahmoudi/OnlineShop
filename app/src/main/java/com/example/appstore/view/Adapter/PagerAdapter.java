package com.example.appstore.view.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.appstore.view.SubCategoryListFragment;
import com.example.appstore.viewModel.CategoryActivityViewModel;

public class PagerAdapter extends FragmentStateAdapter {

   private CategoryActivityViewModel mCategoryActivityViewModel;

    public PagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        mCategoryActivityViewModel = ViewModelProviders.of(fragmentActivity).get(CategoryActivityViewModel.class);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return SubCategoryListFragment.newInstance(mCategoryActivityViewModel
                .getParentCategoriesLiveData().getValue().get(position).getId());
    }

    @Override
    public int getItemCount() {
        return mCategoryActivityViewModel.getParentCategoriesLiveData().getValue().size();
    }
}
