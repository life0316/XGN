package com.haoxi.xgn.inject;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = AppMoudle.class)
public interface AppComponent {
    Context getContext();
}
