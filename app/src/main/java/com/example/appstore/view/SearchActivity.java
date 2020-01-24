package com.example.appstore.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.appstore.view.SingleFragmentActivity;

public class SearchActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context){
        return new Intent(context,SearchActivity.class);
    }
    @Override
    public Fragment createFragment() {
        return SearchFragment.newInstance();
    }

}
