package com.example.appstore.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.appstore.R;

public abstract class SingleFragmentActivityToolbar extends AppCompatActivity {
    private Toolbar mToolbar;

    public abstract Fragment createFragment();

    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment_toolbar);

        setToolbar();
        setFragment();

    }

    private void setFragment() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container_single_activity_toolbar);
        if (fragment == null)
            fm.beginTransaction().replace(R.id.fragment_container_single_activity_toolbar, createFragment()).commit();
    }

    private void setToolbar() {
        mToolbar = findViewById(R.id.toolbar_single_fragment_activity);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.home_menu, menu);

        MenuItem search = menu.findItem(R.id.search_home);
        MenuItem cart = menu.findItem(R.id.cart);

        cart.setOnMenuItemClickListener(item -> {
            Intent intent = CartActivity.newIntent(SingleFragmentActivityToolbar.this);
            startActivity(intent);
            return true;
        });

        search.setOnMenuItemClickListener(item -> {
            Intent intent = SearchActivity.newIntent(SingleFragmentActivityToolbar.this);
            startActivity(intent);
            return true;
        });
        return true;

    }
}
