package com.example.appstore.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.appstore.R;

public class LoginActivity extends SingleFragmentActivityToolbar {
    private Toolbar mToolbar;

    public static Intent newIntent(Context context){
        return new Intent(context,LoginActivity.class);
    }

    @Override
    public Fragment createFragment() {
        getToolbar().setTitle(R.string.enter);
        return LoginFragment.newInstance();
    }


}
