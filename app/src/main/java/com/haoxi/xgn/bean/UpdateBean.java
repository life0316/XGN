package com.haoxi.xgn.bean;


public class UpdateBean {
    String msg;
    int code;
    UpdateData data;

    public UpdateBean(String msg, int code, UpdateData data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public UpdateData getData() {
        return data;
    }

    public void setData(UpdateData data) {
        this.data = data;
    }

    public class UpdateData{
        String android_info;
        String android_url;
        String android_version;

        public UpdateData(String android_info, String android_url, String android_version) {
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
}
