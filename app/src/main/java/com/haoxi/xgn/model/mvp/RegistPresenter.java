package com.haoxi.xgn.model.mvp;

import android.util.Log;

import com.haoxi.xgn.base.BasePresenter;
import com.haoxi.xgn.base.BaseSubscriber;
import com.haoxi.xgn.bean.RegistBean;

import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RegistPresenter extends BasePresenter<IRegistView,RegistBean> {

    private static final String TAG = "EasePresenter";

    public void getDataFromNets(Map<String,String> map){
        checkViewAttached();
        Log.e("LoginActivity",map+"-------map");

        netService.getRegister(map)
                .subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.newThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        RegistPresenter.this.beforeRequest();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<RegistBean, Boolean>() {
                    @Override
                    public Boolean call(RegistBean user) {
                        int codes = user.getCode();
                        Log.e("LoginActivity",codes+"-------"+user.getMsg());
                        if (codes != 200){
                            RegistPresenter.this.requestError(user.getMsg());
                        }
                        return 200 == user.getCode();
                    }
                }).subscribe(new BaseSubscriber<>(RegistPresenter.this));
    }

    @Override
    public void requestSuccess(RegistBean data) {
        super.requestSuccess(data);
        if (isViewAttached()) {
            getMvpView().hideProgress();
            getMvpView().registSuccess(data);
        }
    }

    @Override
    public void requestError(String msg) {
        super.requestError("600");
        if (!"".equals(msg)){
            getMvpView().failture(msg);
        }
    }
}
