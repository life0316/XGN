package com.haoxi.xgn.model;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.haoxi.xgn.MainActivity;
import com.haoxi.xgn.R;
import com.haoxi.xgn.utils.ApiUtils;

public class SplashActivity extends AppCompatActivity {

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ApiUtils.isNetworkConnected(SplashActivity.this)) {
                    boolean isAutoCb = false;
                    Intent intent = null;
                    if (isAutoCb) {
                        intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                } else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                SplashActivity.this.finish();
            }
        }, 2000);
    }
}
