package com.haoxi.xgn.bean;

/**
 * Created by Administrator on 2018\1\19 0019.
 *
 * 当前固件最新版本
 */

public class FirmwareData {

    String firmware_url;
    String firmware_info;
    String firmware_version;

    public FirmwareData() {
    }

    public FirmwareData(String firmware_url, String firmware_info, String firmware_version) {
        this.firmware_url = firmware_url;
        this.firmware_info = firmware_info;
        this.firmware_version = firmware_version;
    }

    public String getFirmware_url() {
        return firmware_url;
    }

    public void setFirmware_url(String firmware_url) {
        this.firmware_url = firmware_url;
    }

    public String getFirmware_info() {
        return firmware_info;
    }

    public void setFirmware_info(String firmware_info) {
        this.firmware_info = firmware_info;
    }

    public String getFirmware_version() {
        return firmware_version;
    }

    public void setFirmware_version(String firmware_version) {
        this.firmware_version = firmware_version;
    }
}
