package com.haoxi.xgn.net;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static final String BASE_OUR_URL = "http://111.231.54.111/";
    private static Retrofit ourRetrofit;
    private static INetService netService;

    public static void initRetrofit(){
        Gson gson = new GsonBuilder()
                .setLenient().create();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(50, TimeUnit.SECONDS)
                .build();

        ourRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_OUR_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        netService = ourRetrofit.create(INetService.class);

    }

    public static RetrofitManager builder(){
        return new RetrofitManager();
    }

    public INetService getNetService() {
        return netService;
    }

    public Retrofit getOurRetrofit() {
        return ourRetrofit;
    }

}
