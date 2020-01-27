package com.example.appstore.view;

import androidx.fragment.app.Fragment;
import android.content.Context;
import android.content.Intent;

public class ProSubCatActivity extends SingleFragmentActivityToolbar {

    public static final String EXTRA_SUB_CATEGORY_ID = "EXTRA_CATEGORY_ID";
    public static final String EXTRA_SUB_CATEGORY_NAME = "EXTRA_SUB_CATEGORY_NAME";

    public static Intent newIntent(Context context, int categoryID,String name) {
        Intent intent =  new Intent(context, ProSubCatActivity.class);

        intent.putExtra(EXTRA_SUB_CATEGORY_NAME,name);
        intent.putExtra(EXTRA_SUB_CATEGORY_ID,categoryID);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        getToolbar().setTitle(getIntent().getStringExtra(EXTRA_SUB_CATEGORY_NAME));
        return ProSubCatFragment.newInstance(getIntent().getIntExtra(EXTRA_SUB_CATEGORY_ID,0));
    }

}
