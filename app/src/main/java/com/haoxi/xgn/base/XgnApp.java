package com.haoxi.xgn.base;

import android.app.Application;

import com.blankj.utilcode.util.Utils;


public class XgnApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
