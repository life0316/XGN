package com.haoxi.xgn.base;

import com.haoxi.xgn.net.INetService;
import com.haoxi.xgn.net.RetrofitManager;

import retrofit2.Retrofit;

public class BasePresenter<V extends BaseView,T> implements RequestCallback<T> {
    private V mvpView;
    protected boolean needShowProgess = true;

    protected Retrofit retrofit = RetrofitManager.builder().getOurRetrofit();
    protected INetService netService = RetrofitManager.builder().getNetService();

    public void attachView(V mvpView) {
        this.mvpView = mvpView;
    }

    public void detachView() {
        this.mvpView = null;
    }

    public V getMvpView() {
        return mvpView;
    }

    public boolean isViewAttached() {
        return mvpView != null;
    }

    public void setNeedShowProgess(boolean needShowProgess) {
        this.needShowProgess = needShowProgess;
    }

    protected void checkViewAttached() {
        if (!isViewAttached()) {
            throw new MvpViewNotAttachedException();
        }
    }

    @Override
    public void beforeRequest() {
        checkViewAttached();
        if (isViewAttached()&&needShowProgess) {
            mvpView.showProgress();
        }
    }

    @Override
    public void requestError(String msg) {
        checkViewAttached();
        if (isViewAttached()) {
            mvpView.hideProgress();
//            if (!"600".equals(msg)){
//                mvpView.showErrorMsg(msg);
//            }
//            mvpView.setNetTag(false);
        }
    }

    @Override
    public void requestComplete() {
        checkViewAttached();
        if (isViewAttached()) {
            mvpView.hideProgress();
        }
    }

    @Override
    public void requestSuccess(T data) {
        checkViewAttached();
        if (isViewAttached()) {
            mvpView.hideProgress();
        }
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("请求数据前请先调用 attachView(MvpView) 方法与view建立连接");
        }
    }
}
