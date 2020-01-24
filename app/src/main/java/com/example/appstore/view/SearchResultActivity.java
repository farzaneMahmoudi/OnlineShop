package com.example.appstore.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.appstore.R;

public class SearchResultActivity extends AppCompatActivity {

    public static final String EXTRA_QUERY_SEARCH = "EXTRA_QUERY_SEARCH";

    public static Intent newIntent(Context context, String query){
        Intent intent =  new Intent(context,SearchResultActivity.class);
        intent.putExtra(EXTRA_QUERY_SEARCH,query);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null)
            fm.beginTransaction().replace(R.id.fragment_container,SearchResultFragment.
                    newInstance(getIntent().getStringExtra(EXTRA_QUERY_SEARCH))).commit();
    }


 /*   @Override
    public Fragment createFragment() {
        return SearchResultFragment.newInstance(getIntent().getStringExtra(EXTRA_QUERY_SEARCH));
    }*/
}
