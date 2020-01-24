package com.example.appstore.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.appstore.R;

public class ProSubCatActivity extends AppCompatActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, ProSubCatActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prosub_cat);
    }
}
