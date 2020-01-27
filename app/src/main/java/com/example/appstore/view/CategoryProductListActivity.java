package com.example.appstore.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.appstore.model.Category;
import com.example.appstore.view.Adapter.PagerAdapter;
import com.example.appstore.model.CategoriesItem;
import com.example.appstore.R;
import com.example.appstore.viewModel.CategoryActivityViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class CategoryProductListActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager2 mViewPager;

    private PagerAdapter mAdapter;
    private CategoryActivityViewModel mViewModel;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, CategoryProductListActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_product_list);

        mViewModel = ViewModelProviders.of(this).get(CategoryActivityViewModel.class);
        mViewModel.getSubCategoriesLiveData2().getValue();
        setUpToolBar();
        initUi();

        mAdapter= new PagerAdapter(this);
        mViewPager.setAdapter(mAdapter);
        new TabLayoutMediator(mTabLayout, mViewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        tab.setText(mViewModel.getParentCategoriesLiveData().getValue().get(position).getName() + "");
                    }
                }).attach();
    }

    private void initUi() {
        mTabLayout = findViewById(R.id.tabs);
        mViewPager = findViewById(R.id.view_pager_category);
    }

    private void setUpToolBar() {
        mToolbar = findViewById(R.id.toolbar_product_list_activity);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.category);
    }
}
