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

    public static final String EXTRA_PRODUCT_LIST_ACTIVITY = "EXTRA_PRODUCT_LIST_ACTIVITY";
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private LinearLayout mLinearLayout;

    private List<CategoriesItem> mCategoriesList = new ArrayList<>();
    private PagerAdapter mAdapter;
    private CategoryActivityViewModel mViewModel;

    public static Intent newIntent(Context context, int numOfCategories) {
        Intent intent = new Intent(context, CategoryProductListActivity.class);
        intent.putExtra(EXTRA_PRODUCT_LIST_ACTIVITY, numOfCategories);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_product_list);

        mViewModel = ViewModelProviders.of(this).get(CategoryActivityViewModel.class);
        mViewModel.getCategoriesList().observe(this, categoriesItems -> {
            setPagerAdapter();
            setTabs(categoriesItems);
          //  setPagerAdapterTabs(mCategoriesList);
        });

        setUpToolBar();
        initUi();
    }

    private void setTabs(List<CategoriesItem> categoriesList) {
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.removeAllTabs();
        for (int i = 0; i < categoriesList.size() ; i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(categoriesList.get(i).getName()));
        }
        if (mTabLayout.getTabCount() == 2) {
            mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        } else {
            mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }

    }

    private void setPagerAdapter() {
        mTabLayout.setupWithViewPager(mViewPager);

        if (mAdapter == null) {
          //  mTabLayout.setupWithViewPager(mViewPager);
            mAdapter = new PagerAdapter(getSupportFragmentManager(), mCategoriesList);

     /*       TabLayoutMediator tb = new TabLayoutMediator(mTabLayout, mViewPager,true,
                    new TabLayoutMediator.TabConfigurationStrategy() {
                        @Override
                        public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                            for (int k = 0; k < mCategoriesList.size(); k++) {
                                tab.setText(mCategoriesList.get(position).getName());
                            }
                        }
                    });
            tb.attach();*/
           // mTabLayout.setupWithViewPager(mViewPager);
            mViewPager.setAdapter(mAdapter);
            mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        } else {
            mAdapter.setList(mCategoriesList);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void initUi() {
        mTabLayout = findViewById(R.id.tabs);
        mViewPager = findViewById(R.id.frameLayout_view_pager);
  //      mViewPager.setOffscreenPageLimit(1);
       // mTabLayout.setupWithViewPager(mViewPager);
        mLinearLayout = findViewById(R.id.linear_activity_pro_list);
        ViewCompat.setLayoutDirection(mLinearLayout, ViewCompat.LAYOUT_DIRECTION_RTL);
    }

    private void setUpToolBar() {
        mToolbar = findViewById(R.id.toolbar_product_list_activity);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.category);
    }
}
