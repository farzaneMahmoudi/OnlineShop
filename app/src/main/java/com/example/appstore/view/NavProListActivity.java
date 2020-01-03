package com.example.appstore.view;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.appstore.R;

public class NavProListActivity extends SingleFragmentActivity {

    public static final String EXTRA_WHICH_PRO = "EXTRA_WHICH_PRO";
    private String mWhichPro;
    private Toolbar mToolbar;
    private LinearLayout mLinearLayout;

    public static Intent newIntent(Context context, String whichPro) {
        Intent intent = new Intent(context, NavProListActivity.class);
        intent.putExtra(EXTRA_WHICH_PRO, whichPro);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        mWhichPro = getIntent().getStringExtra(EXTRA_WHICH_PRO);
        return NavProListFragment.newInstance(mWhichPro);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_pro_list);
        setUpToolBar(mWhichPro);
        mLinearLayout = findViewById(R.id.linear_nav_pro_list);
        ViewCompat.setLayoutDirection(mLinearLayout, ViewCompat.LAYOUT_DIRECTION_RTL);
    }

    private void setUpToolBar(String str) {
        mToolbar = findViewById(R.id.toolbar_nav_product_list_activity);
        setSupportActionBar(mToolbar);
        if (str.equals(NavProListFragment.BEST_PRODUCT))
            getSupportActionBar().setTitle(R.string.best_products);
        if (str.equals(NavProListFragment.MOST_VISITED))
            getSupportActionBar().setTitle(R.string.most_visited_products);
        if (str.equals(NavProListFragment.RECENT_PRODUCT))
            getSupportActionBar().setTitle(R.string.recent_products);
    }
}
