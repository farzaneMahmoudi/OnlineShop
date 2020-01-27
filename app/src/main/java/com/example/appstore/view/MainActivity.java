package com.example.appstore.view;

import androidx.appcompat.app.ActionBarDrawerToggle;

import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.appstore.R;
import com.example.appstore.databinding.ActivityMainBinding;
import com.example.appstore.viewModel.CategoryActivityViewModel;

public class MainActivity extends SingleFragmentActivity {

    private ActivityMainBinding mBinding;
    private Button mButton;

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

        initUI();

        mButton.setOnClickListener(v -> {
            Intent intent = LoginActivity.newIntent(MainActivity.this);
            startActivity(intent);
        });

        mBinding.navView.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.menu_home: {
                    break;
                }
                case R.id.menu_categories: {
                    startActivity(CategoryProductListActivity.newIntent(MainActivity.this));
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

    private void initUI() {
        View hView = mBinding.navView.inflateHeaderView(R.layout.nav_header);
        mButton = hView.findViewById(R.id.nav_button_login_or_sign_up);
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
        MenuItem cart = menu.findItem(R.id.cart);

        cart.setOnMenuItemClickListener(item -> {
            Intent intent = CartActivity.newIntent(MainActivity.this);
            startActivity(intent);
            return true;
        });

        search.setOnMenuItemClickListener(item -> {
            Intent intent = SearchActivity.newIntent(MainActivity.this);
            startActivity(intent);
            return true;
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mBinding.drawerLayout.isDrawerOpen(GravityCompat.START))
            mBinding.drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }
}
