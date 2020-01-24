package com.example.appstore.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.appstore.R;
import com.example.appstore.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import static java.security.AccessController.getContext;

public class MainActivity extends SingleFragmentActivity {

    private ActivityMainBinding mBinding;

    private int numOfCategories;

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }


    @Override
    public Fragment createFragment() {
        return MainFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        ViewCompat.setLayoutDirection(mBinding.drawerLayout, ViewCompat.LAYOUT_DIRECTION_RTL);
        setNavigationDrawer();

        mBinding.navView.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.menu_home: {
                    break;
                }
                case R.id.menu_categories: {
                    startActivity(CategoryProductListActivity.newIntent(MainActivity.this, numOfCategories));
                    break;
                }
                case R.id.menu_cart: {
                    break;
                }
                case R.id.menu_most_visited: {
                    startActivity(NavProListActivity.newIntent(MainActivity.this, NavProListFragment.MOST_VISITED));
                    break;
                }
                case R.id.menu_recent_products: {
                    startActivity(NavProListActivity.newIntent(MainActivity.this, NavProListFragment.RECENT_PRODUCT));
                    break;
                }
                case R.id.menu_best_products: {
                    startActivity(NavProListActivity.newIntent(MainActivity.this, NavProListFragment.BEST_PRODUCT));
                    break;
                }
                case R.id.menu_recommended_products: {
                    break;
                }
            }
            return true;
        });
    }

    private void setNavigationDrawer() {
        setSupportActionBar(mBinding.toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mBinding.drawerLayout, mBinding.toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mBinding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.home_menu, menu);

        MenuItem search = menu.findItem(R.id.search_home);
        search.setOnMenuItemClickListener(item -> {
            Intent intent = SearchActivity.newIntent(MainActivity.this);
            startActivity(intent);
            return true;
        });
         return true;
    }
}
