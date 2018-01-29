package com.haoxi.xgn.bean;



public class DeviceBean {

    String deviceid;
    String devicename;
    String btmac;
    String submit;
    int status;
    String userid;

    public DeviceBean() {
    }

    public DeviceBean(String deviceid, String devicename, String btmac, String submit, int status,String userid) {
        this.deviceid = deviceid;
        this.devicename = devicename;
        this.btmac = btmac;
        this.submit = submit;
        this.status = status;
        this.userid = userid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    public String getBtmac() {
        return btmac;
    }

    public void setBtmac(String btmac) {
        this.btmac = btmac;
    }

    public String getSubmit() {
        return submit;
    }

    public void setSubmit(String submit) {
        this.submit = submit;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
