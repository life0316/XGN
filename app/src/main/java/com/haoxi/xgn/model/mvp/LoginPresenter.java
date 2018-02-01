package com.haoxi.xgn.model.mvp;

import android.util.Log;
import com.haoxi.xgn.base.BasePresenter;
import com.haoxi.xgn.bean.EaseDataBean;
import java.util.Map;

public class LoginPresenter extends BasePresenter<ILoginView,EaseDataBean>{
    private LoginModel loginModel;
    public LoginPresenter(LoginModel loginModel){
        this.loginModel = loginModel;
    }
    public void getDataFromNets(Map<String,String> map){
        checkViewAttached();

        Log.e("LoginActivity",map.toString()+"------3");
        loginModel.getDatasFromNets(map,this);
    }

    @Override
    public void requestSuccess(EaseDataBean data) {
        super.requestSuccess(data);
        if (isViewAttached()) {
            getMvpView().hideProgress();
            getMvpView().toGetDetail(data);
        }
    }

    @Override
    public void requestError(String msg) {
        super.requestError("600");
        if ("".equals(msg)){
//            getMvpView().showErrorMsg("网络连接异常");
        }else {
            getMvpView().loginFail(msg);
        }
    }
}
