package com.haoxi.xgn.bean;

/**
 * Created by Administrator on 2018\1\19 0019.
 *
 * app更新版本判断
 *
 */

public class AppVersionData {

    String android_info;
    String android_url;
    String android_version;

    public AppVersionData() {
    }

    public AppVersionData(String android_info, String android_url, String android_version) {
        this.android_info = android_info;
        this.android_url = android_url;
        this.android_version = android_version;
    }

    public String getAndroid_info() {
        return android_info;
    }

    public void setAndroid_info(String android_info) {
        this.android_info = android_info;
    }

    public String getAndroid_url() {
        return android_url;
    }

    public void setAndroid_url(String android_url) {
        this.android_url = android_url;
    }

    public String getAndroid_version() {
        return android_version;
    }

    public void setAndroid_version(String android_version) {
        this.android_version = android_version;
    }
}
