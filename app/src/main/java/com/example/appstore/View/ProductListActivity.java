package com.example.appstore.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;


import com.example.appstore.Adapter.PagerAdapter;
import com.example.appstore.Model.CategoriesItem;
import com.example.appstore.Model.ResponseModel;
import com.example.appstore.Network.AppRepository;
import com.example.appstore.R;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity implements AppRepository.AppRepoCallBack {

    public static final String EXTRA_PRODUCT_LIST_ACTIVITY = "EXTRA_PRODUCT_LIST_ACTIVITY";
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private AppRepository mAppRepository;
    private LinearLayout mLinearLayout;

    private List<CategoriesItem> mCategoriesList = new ArrayList<>();
    private int numOfTab;
    private PagerAdapter mAdapterdapter;

    public static Intent newIntent(Context context, int numOfCategories) {
        Intent intent = new Intent(context, ProductListActivity.class);
        intent.putExtra(EXTRA_PRODUCT_LIST_ACTIVITY, numOfCategories);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        setUpToolBar();

        initUi();

        mAppRepository = AppRepository.getInstance(this);
        try {
            mAppRepository.getCategories();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //setPagerAdapter();
    }

    private void setPagerAdapter() {
        if(mAdapterdapter == null) {
            mAdapterdapter = new PagerAdapter(getSupportFragmentManager(), mTabLayout.getTabCount(), mCategoriesList);
            mViewPager.setAdapter(mAdapterdapter);
            mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        }
        else{
            mAdapterdapter.setList(mCategoriesList);
            mAdapterdapter.notifyDataSetChanged();
        }
    }

    private void initUi() {
        mTabLayout = findViewById(R.id.tabs);
        mViewPager = findViewById(R.id.frameLayout_view_pager);
        mViewPager.setOffscreenPageLimit(1);
        mTabLayout.setupWithViewPager(mViewPager);
        mLinearLayout = findViewById(R.id.linear_activity_pro_list);
        ViewCompat.setLayoutDirection(mLinearLayout, ViewCompat.LAYOUT_DIRECTION_RTL);
    }

    private void setUpToolBar() {
        mToolbar = findViewById(R.id.toolbar_product_list_activity);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.category);
    }

    @Override
    public void onListCategoryResponse(List<CategoriesItem> listCategory) {
        mCategoriesList = listCategory;
        numOfTab = mCategoriesList.size();

        for (int k = 0; k < numOfTab; k++) {
            mTabLayout.addTab(mTabLayout.newTab().setText("" + mCategoriesList.get(k).getName()));
        }
        if (mTabLayout.getTabCount() == 2) {
            mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        } else {
            mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
        setPagerAdapter();
    }

    @Override
    public void onProListSubCategoryResponse(List<ResponseModel> proSubCatList) {

    }


    @Override
    public void onResponseRecent(List<ResponseModel> list) {

    }

    @Override
    public void onResponseBest(List<ResponseModel> list) {

    }

    @Override
    public void onResponsePopular(List<ResponseModel> list) {

    }

    @Override
    public void onResponseProduct(ResponseModel prodect) {

    }
}
