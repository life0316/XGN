package com.haoxi.xgn.base;


public interface BaseView {

    String getMethod();
    String getTime();
    String getSign();
    String getVersion();

    //显示进度条
    void showProgress();

    //隐藏进度条或对话框
    void hideProgress();

//    //显示错误信息
//    void showErrorMsg(String errorMsg);

}
