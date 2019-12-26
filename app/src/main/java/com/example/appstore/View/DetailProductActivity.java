package com.example.appstore.View;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;

public class DetailProductActivity extends SingleFragmentActivity  {
    private static final String EXTRA_PRODUCT_MODEL = "EXTRA_PRODUCT_MODEL";
    private static int mProductId;

    public static Intent newIntent(Context context,int id){
        Intent intent =new Intent(context,DetailProductActivity.class);
        mProductId =id;
        intent.putExtra(EXTRA_PRODUCT_MODEL,id);

        return intent;
    }

    @Override
    public Fragment createFragment() {
        return DetailProductFragment.newInstance(mProductId);
    }

}
