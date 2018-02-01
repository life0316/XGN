package com.haoxi.xgn.bean;


public class WeekShoesData {
    String recordid;
    String date;
    String userid;
    String deviceid;
    int steps;

    public WeekShoesData() {
    }

    public WeekShoesData(String recordid, String date, String userid, String deviceid, int steps) {
        this.recordid = recordid;
        this.date = date;
        this.userid = userid;
        this.deviceid = deviceid;
        this.steps = steps;
    }

    public String getRecordid() {
        return recordid;
    }

    public void setRecordid(String recordid) {
        this.recordid = recordid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }
}
