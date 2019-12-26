package com.example.appstore.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.appstore.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends SingleFragmentActivity
{

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;


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
        setContentView(R.layout.activity_main);

        initUi();
        ViewCompat.setLayoutDirection(mDrawerLayout,ViewCompat.LAYOUT_DIRECTION_RTL);
        setNavigationDrawer();

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.menu_home:{
                        break;
                    }
                    case R.id.menu_categories:{
                        startActivity(ProductListActivity.newIntent(MainActivity.this,numOfCategories));
                        break;
                    }
                    case R.id.menu_cart:{
                        break;
                    }
                    case R.id.menu_most_visited:{
                        break;
                    }
                    case R.id.menu_recent_products:{
                        break;
                    }
                    case R.id.menu_best_products:{
                        break;
                    }
                    case R.id.menu_recommended_products:{
                        break;
                    }


                }
                return true;
            }
        });
    }

    private void setNavigationDrawer() {
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initUi() {
        mToolbar = findViewById(R.id.toolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
         mDrawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }
}
