package com.haoxi.xgn.bean;

/**
 * Created by Administrator on 2018\1\19 0019.
 * 用户信息
 */

public class UserInfoData {

    String birthday;
    String gender; //性别
    long create_timestamp;
    String nickname;
    int weight;
    String telephone;
    String userid;
    int target;
    int height;
    String token;

    public UserInfoData() {
    }
    public UserInfoData(String birthday, String gender, long create_timestamp, String nickname, int weight, String telephone, String userid, int target, int height, String token) {
        this.birthday = birthday;
        this.gender = gender;
        this.create_timestamp = create_timestamp;
        this.nickname = nickname;
        this.weight = weight;
        this.telephone = telephone;
        this.userid = userid;
        this.target = target;
        this.height = height;
        this.token = token;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setCreate_timestamp(long create_timestamp) {
        this.create_timestamp = create_timestamp;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getCreate_timestamp() {
        return create_timestamp;
    }

    public void setCreate_timestamp(int create_timestamp) {
        this.create_timestamp = create_timestamp;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
