package com.haoxi.xgn.base;

import rx.Subscriber;

/**
 * Created by Administrator on 2018\1\23 0023.
 */

public class BaseSubscriber<T> extends Subscriber<T> {

    private RequestCallback<T> mRequestCallBack;
    public BaseSubscriber(RequestCallback<T> callback) {
        mRequestCallBack = callback;
    }

    @Override
    public void onCompleted() {
        if (mRequestCallBack != null) {
            mRequestCallBack.requestComplete();
        }
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onNext(T t) {
        if (mRequestCallBack != null) {
            mRequestCallBack.requestSuccess(t);
        }
    }
}
