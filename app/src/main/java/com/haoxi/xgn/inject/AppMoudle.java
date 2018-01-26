package com.haoxi.xgn.inject;

import android.content.Context;

import com.haoxi.xgn.base.XgnApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppMoudle {

    private XgnApp xgnApp;

    public AppMoudle(XgnApp xgnApp){
        this.xgnApp = xgnApp;
    }

    @Singleton
    @Provides
    public Context provideApplicationContext(){
        return xgnApp.getApplicationContext();
    }
}
