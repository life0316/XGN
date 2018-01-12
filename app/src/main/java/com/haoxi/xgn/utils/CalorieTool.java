package com.haoxi.xgn.utils;

public class CalorieTool {

    public static final double K_WALKING = 0.8214;
    public static final double K_RUNNING = 1.036;
    public static final double K_BICYCLE = 0.6142;
    public static final double K_SKATING = 0.518;
    public static final double K_SKIING = 0.888;

    public static double calculateCalorie(double weight,double distance,double ratio){

//        体重（kg）* 距离（km）* 运动系数（k）
        if (ratio != K_WALKING || ratio != K_RUNNING || ratio != K_BICYCLE || ratio != K_SKATING || ratio != K_SKIING){
            return -1;
        }
        if (weight < 0){
            return -1;
        }
        if (distance < 0 ){
            return -1;
        }
        return weight * distance * ratio;
    }
}
