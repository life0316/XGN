package com.haoxi.xgn.weekdatas;

import android.util.Log;

import com.haoxi.xgn.base.BasePresenter;
import com.haoxi.xgn.base.BaseSubscriber;
import com.haoxi.xgn.bean.WeekBean;

import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class WeekDataPresenter extends BasePresenter<IWeekDataView,WeekBean> {

    public void getDataFromNets(Map<String,String> map){
        checkViewAttached();
        Log.e("LoginActivity",map+"-------map");
        netService.getWeekData(map)
                .subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.newThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        WeekDataPresenter.this.beforeRequest();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<WeekBean, Boolean>() {
                    @Override
                    public Boolean call(WeekBean user) {
                        int codes = user.getCode();
                        Log.e("LoginActivity",codes+"-------"+user.getMsg());
                        Log.e("LoginActivity",(user.getData() == null)+"-------"+user.getMsg());
                        if (codes != 200){
                            WeekDataPresenter.this.requestError(user.getMsg());
                        }
                        return 200 == user.getCode();
                    }
                }).subscribe(new BaseSubscriber<>(WeekDataPresenter.this));
    }

    @Override
    public void requestSuccess(WeekBean data) {
        super.requestSuccess(data);
        for (int i = 0; i < data.getData().size(); i++) {
            Log.e("oooooooo",data.getData().get(i).getDate());
            Log.e("oooooooo",data.getData().get(i).getSteps()+"----steps");
        }

        if (isViewAttached()) {
            getMvpView().hideProgress();
            if (data.getData() != null){
                getMvpView().getDataSuccess(data.getData());
            }
        }
    }

    @Override
    public void requestError(String msg) {
        super.requestError("600");
        Log.e("oooooooo",msg+"-------1--steps");
        if (!"".equals(msg)){
            getMvpView().onFailure(msg);
        }
    }
}
