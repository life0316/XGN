package com.haoxi.xgn.bean;


public class EaseDataBean {

    public int code;
    public String msg;
    public UserInfoData data;

    public EaseDataBean() {
    }

    public EaseDataBean(int code, String msg,UserInfoData data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public UserInfoData getData() {
        return data;
    }

    public void setData(UserInfoData data) {
        this.data = data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
