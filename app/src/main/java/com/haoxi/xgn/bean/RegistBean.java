package com.haoxi.xgn.bean;


public class RegistBean {

    int code;
    String msg;
    InnerData data;

    public RegistBean() {
    }

    public RegistBean(int code, String msg, InnerData data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
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

    public InnerData getData() {
        return data;
    }

    public void setData(InnerData data) {
        this.data = data;
    }

    public class InnerData{
        String userid;
        String token;

        public InnerData() {
        }

        public InnerData(String userid, String token) {
            this.userid = userid;
            this.token = token;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
