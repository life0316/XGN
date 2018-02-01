package com.haoxi.xgn.base;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.haoxi.xgn.inject.AppComponent;
import com.haoxi.xgn.inject.AppMoudle;
import com.haoxi.xgn.inject.DaggerAppComponent;
import com.haoxi.xgn.net.RetrofitManager;
import com.haoxi.xgn.openBle.BluetoothLeService;


public class XgnApp extends Application {

    AppComponent appComponent;
    private static XgnApp myApplication;
    private BluetoothLeService bluetoothLeService;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        Utils.init(this);
        RetrofitManager.initRetrofit();
        initInjector();
    }
    public static XgnApp getInstance(){
        return myApplication;
    }


    private void initInjector() {
        appComponent = DaggerAppComponent.builder()
                .appMoudle(new AppMoudle(myApplication)).build();
    }

    public AppComponent getAppComponent(){
        return appComponent;
    }

    public BluetoothLeService getBluetoothLeService(){
        return bluetoothLeService;
    }

    public void setBluetoothLeService(BluetoothLeService bluetoothLeService){
        this.bluetoothLeService = bluetoothLeService;
    }

}
