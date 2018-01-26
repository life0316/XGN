package com.haoxi.xgn.bean;

/**
 * Created by Administrator on 2018\1\19 0019.
 * 定位鞋信息
 */

public class DeviceInfoData {

    String submit;
    String btmac;
    String devicename;
    String deviceid;
    String userid;
    String status;

    public DeviceInfoData() {
    }

    public DeviceInfoData(String submit, String btmac, String devicename, String deviceid, String userid, String status) {
        this.submit = submit;
        this.btmac = btmac;
        this.devicename = devicename;
        this.deviceid = deviceid;
        this.userid = userid;
        this.status = status;
    }

    public String getSubmit() {
        return submit;
    }

    public void setSubmit(String submit) {
        this.submit = submit;
    }

    public String getBtmac() {
        return btmac;
    }

    public void setBtmac(String btmac) {
        this.btmac = btmac;
    }

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
