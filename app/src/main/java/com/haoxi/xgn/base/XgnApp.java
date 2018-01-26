package com.haoxi.xgn.base;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.haoxi.xgn.inject.AppComponent;
import com.haoxi.xgn.inject.AppMoudle;
import com.haoxi.xgn.inject.DaggerAppComponent;
import com.haoxi.xgn.net.RetrofitManager;


public class XgnApp extends Application {

    AppComponent appComponent;
    private static XgnApp myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);

        RetrofitManager.initRetrofit();
        initInjector();
    }

    private void initInjector() {
        appComponent = DaggerAppComponent.builder()
                .appMoudle(new AppMoudle(myApplication)).build();
    }

    public AppComponent getAppComponent(){
        return appComponent;
    }
}
