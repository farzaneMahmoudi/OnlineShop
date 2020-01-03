package com.example.appstore.view;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.appstore.R;

public class NoConnectionActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context){
        Intent intent =new Intent(context,NoConnectionActivity.class);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return NoConnectionFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);
    }
}
