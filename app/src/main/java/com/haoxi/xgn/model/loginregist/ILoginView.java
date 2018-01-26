package com.haoxi.xgn.model.loginregist;

import com.haoxi.xgn.base.BaseView;
import com.haoxi.xgn.bean.EaseDataBean;

public interface ILoginView extends BaseView{
    void toGetDetail(EaseDataBean user);
    void loginFail(String msg);
}
