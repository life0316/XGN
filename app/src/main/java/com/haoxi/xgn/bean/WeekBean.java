package com.haoxi.xgn.bean;

import java.util.List;

public class WeekBean {
    String msg;
    int code;
    List<WeekShoesData> data;

    public WeekBean() {
    }

    public WeekBean(String msg, int code, List<WeekShoesData> data) {
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

    public List<WeekShoesData> getData() {
        return data;
    }

    public void setData(List<WeekShoesData> data) {
        this.data = data;
    }


}
