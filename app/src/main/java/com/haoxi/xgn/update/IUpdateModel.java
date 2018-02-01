package com.haoxi.xgn.update;


import com.haoxi.xgn.base.RequestCallback;

import java.util.Map;

public interface IUpdateModel <T> {
    //获取新版本
    void downloadApk(String apkUrl, MyProgressCallback progressCallback);
    void updateVar(Map<String,String> map, RequestCallback<T> requestCallback);
}
