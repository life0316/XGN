package com.haoxi.xgn.bean;

/**
 * Created by lifei on 2018/1/27.
 */

public class OutDeviceBean {
    String msg;
    int code;
    DeviceBean data;

    public OutDeviceBean() {
    }

    public OutDeviceBean(String msg, int code, DeviceBean data) {
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

    public DeviceBean getData() {
        return data;
    }

    public void setData(DeviceBean data) {
        this.data = data;
    }
}
