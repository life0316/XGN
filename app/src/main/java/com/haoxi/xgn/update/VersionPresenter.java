package com.haoxi.xgn.update;

import com.haoxi.xgn.base.BasePresenter;
import com.haoxi.xgn.bean.UpdateBean;

import java.util.Map;


public class VersionPresenter  extends BasePresenter<IUpdateView,UpdateBean> {

    private UpdateModel updateModel;

    public VersionPresenter() {
        updateModel = new UpdateModel();
    }

    public void updateVar(Map<String,String> map) {
        checkViewAttached();
        updateModel.updateVar(map,this);
    }

    @Override
    public void requestSuccess(UpdateBean data) {
        super.requestSuccess(data);
        if (isViewAttached()){
            getMvpView().toJudgeVer(data);
        }
    }
    public void downloadApk(String apkUrl) {
        checkViewAttached();
        getMvpView().showProgressDialog();
        updateModel.downloadApk(apkUrl, new MyProgressCallback() {
            @Override
            public void onProgressMax(int max) {
                if (isViewAttached()){
                    getMvpView().setProgressMax(max);
                }
            }
            @Override
            public void onProgressCurrent(int progress) {
                if (isViewAttached()){
                    getMvpView().setCuProgress(progress);
                }
            }
            @Override
            public void onSuccess(String path) {
                getMvpView().installApk(path,100);
                getMvpView().hideProgressDialog();
            }
            @Override
            public void onFailure(String msg) {
                getMvpView().hideProgressDialog();
            }
            @Override
            public void onError(Throwable e) {
                getMvpView().hideProgressDialog();
            }
        });
    }
}
