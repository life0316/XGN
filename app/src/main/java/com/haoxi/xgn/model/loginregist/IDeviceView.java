package com.haoxi.xgn.model.loginregist;

import com.haoxi.xgn.base.BaseView;
import com.haoxi.xgn.bean.DeviceBean;

public interface IDeviceView extends BaseView {
    void getSuccess(DeviceBean deviceBean);
    void getFailture(String msg);
}
