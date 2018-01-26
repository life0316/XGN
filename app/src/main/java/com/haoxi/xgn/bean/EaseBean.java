package com.haoxi.xgn.bean;

/**
 * Created by Administrator on 2018\1\19 0019.
 *
 * 最基础的bean
 *
 */

public class EaseBean {
    int code;
    String msg;

    public EaseBean() {
    }

    public EaseBean(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
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
