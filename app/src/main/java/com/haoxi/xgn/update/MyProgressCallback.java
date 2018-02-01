package com.haoxi.xgn.update;


public interface MyProgressCallback {
    void onProgressMax(int max);
    void onProgressCurrent(int progress);
    void onSuccess(String path);
    void onFailure(String msg);
    void onError(Throwable e);
}
