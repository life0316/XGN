package com.haoxi.xgn.bean;

/**
 * Created by Administrator on 2017\12\22 0022.
 */

public class HistoryBean {

    int stepNum;
    String time;

    public HistoryBean() {
    }

    public HistoryBean(int stepNum, String time) {
        this.stepNum = stepNum;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStepNum() {
        return stepNum;
    }

    public void setStepNum(int stepNum) {
        this.stepNum = stepNum;
    }
}
