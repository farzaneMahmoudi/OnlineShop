package com.example.appstore.view;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.example.appstore.network.AppRepository;
import com.example.appstore.R;
import com.example.appstore.utils.NetworkHelper;

import java.io.IOException;

public class SplashActivity extends AppCompatActivity {

    private AppRepository mAppRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAppRepository = AppRepository.getInstance();
        if(NetworkHelper.isConnected(this)) {
            try {
                mAppRepository.getRecentProducts();
                mAppRepository.getBestProducts();
                mAppRepository.getMostVisitedProducts();
            } catch (IOException e) {
                e.printStackTrace();
            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            },5000);
        }
        else
        {
           startActivity(NoConnectionActivity.newIntent(this));
            finish();
        }
    }

}
