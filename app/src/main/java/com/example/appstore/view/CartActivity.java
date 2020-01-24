package com.example.appstore.view;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;

public class CartActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context){
     Intent intent =  new Intent(context,CartActivity.class);
     return intent;
 }

    @Override
    public Fragment createFragment() {
        return CartFragment.newInstance();
    }
}
