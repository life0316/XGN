package com.haoxi.xgn.model.loginregist;

import android.util.Log;

import com.haoxi.xgn.base.BasePresenter;
import com.haoxi.xgn.base.BaseSubscriber;
import com.haoxi.xgn.bean.DeviceBean;
import com.haoxi.xgn.bean.EaseBean;
import com.haoxi.xgn.bean.OutDeviceBean;

import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

//

public class DevicePresenter extends BasePresenter<IDeviceView,OutDeviceBean> {

    private static final String TAG = "EasePresenter";

    public void getDataFromNets(Map<String,String> map){
        checkViewAttached();
        Log.e("LoginActivity",map+"-------map");

        netService.getShoesInfo(map)
                .subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.newThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        DevicePresenter.this.beforeRequest();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<OutDeviceBean, Boolean>() {
                    @Override
                    public Boolean call(OutDeviceBean user) {
                        int codes = user.getCode();
                        Log.e("LoginActivity",codes+"-------"+user.getMsg());
                        if (codes != 200){
                            DevicePresenter.this.requestError(user.getMsg());
                        }
                        return 200 == user.getCode();
                    }
                }).subscribe(new BaseSubscriber<>(DevicePresenter.this));
    }

    @Override
    public void requestSuccess(OutDeviceBean data) {
        super.requestSuccess(data);
        if (isViewAttached()) {
            getMvpView().hideProgress();
            getMvpView().getSuccess(data.getData());
        }
    }

    @Override
    public void requestError(String msg) {
        super.requestError("600");
        if (!"".equals(msg)){
            getMvpView().getFailture(msg);
        }
    }
}
