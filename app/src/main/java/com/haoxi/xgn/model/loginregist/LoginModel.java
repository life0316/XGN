package com.haoxi.xgn.model.loginregist;

import android.util.Log;

import com.haoxi.xgn.base.BaseModel;
import com.haoxi.xgn.base.BaseSubscriber;
import com.haoxi.xgn.base.RequestCallback;
import com.haoxi.xgn.bean.EaseDataBean;
import com.haoxi.xgn.bean.OurUser;

import java.util.Map;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class LoginModel extends BaseModel{
    public void getDatasFromNets(Map<String,String> map, final RequestCallback<EaseDataBean> requestCallback){
        netService.getOurLogin(map)
                .subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.newThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        requestCallback.beforeRequest();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<EaseDataBean, Boolean>() {
            @Override
            public Boolean call(EaseDataBean user) {
                int codes = user.getCode();
                Log.e("LoginActivity",user.getMsg()+"------4");
                if (codes != 200){
                    requestCallback.requestError(user.getMsg());
                }

                Log.e("LoginActivity",user.getMsg()+"------3");
                return 200 == user.getCode();
            }
        }).subscribe(new BaseSubscriber<>(requestCallback));
    }
}
