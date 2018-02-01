package com.haoxi.xgn.update;

import com.haoxi.xgn.base.BaseView;
import com.haoxi.xgn.bean.UpdateBean;


public interface IUpdateView extends BaseView{
    void toJudgeVer(UpdateBean updateBean);
    void showProgressDialog();
    void hideProgressDialog();
    void setProgressMax(int max);
    void setCuProgress(int progress);
    void installApk(String path, int install);
}
