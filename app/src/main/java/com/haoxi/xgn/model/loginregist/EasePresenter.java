package com.haoxi.xgn.model.loginregist;

import android.util.Log;

import com.haoxi.xgn.base.BasePresenter;
import com.haoxi.xgn.base.BaseSubscriber;
import com.haoxi.xgn.bean.EaseBean;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class EasePresenter extends BasePresenter<IEaseView,EaseBean> {

    private static final String TAG = "EasePresenter";

    public void getDataFromNets(Map<String,String> map){
        checkViewAttached();
        Log.e("LoginActivity",map+"-------map");

        netService.getRequestVerCode(map)
                .subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.newThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        EasePresenter.this.beforeRequest();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<EaseBean, Boolean>() {
                    @Override
                    public Boolean call(EaseBean user) {
                        int codes = user.getCode();
                        Log.e("LoginActivity",codes+"-------"+user.getMsg());
                        if (codes != 200){
                            EasePresenter.this.requestError(user.getMsg());
                        }
                        return 200 == user.getCode();
                    }
                }).subscribe(new BaseSubscriber<>(EasePresenter.this));
    }

    @Override
    public void requestSuccess(EaseBean data) {
        super.requestSuccess(data);
        if (isViewAttached()) {
            getMvpView().hideProgress();
            getMvpView().todo();
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
