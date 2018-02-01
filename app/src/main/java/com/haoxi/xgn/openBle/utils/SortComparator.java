package com.haoxi.xgn.openBle.utils;


import com.haoxi.xgn.openBle.BleDevice;
import java.util.Comparator;


public class SortComparator implements Comparator {
    @Override
    public int compare(Object o, Object t1) {
        BleDevice a = (BleDevice) o;
        BleDevice b = (BleDevice) t1;
        return (b.getRiss()- a.getRiss());
    }
}
