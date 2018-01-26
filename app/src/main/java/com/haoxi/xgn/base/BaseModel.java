package com.haoxi.xgn.base;

import com.haoxi.xgn.net.INetService;
import com.haoxi.xgn.net.RetrofitManager;
import retrofit2.Retrofit;

public class BaseModel {
    public RetrofitManager retrofitManager;
    protected final Retrofit retrofit;
    protected INetService netService;

    public BaseModel() {
        retrofitManager = RetrofitManager.builder();
        retrofit = retrofitManager.getOurRetrofit();
        netService = retrofitManager.getNetService();
    }
}
