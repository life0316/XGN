package com.haoxi.xgn.weekdatas;

import com.haoxi.xgn.base.BaseView;
import com.haoxi.xgn.bean.WeekShoesData;

import java.util.List;

public interface IWeekDataView extends BaseView{

    void getDataSuccess(List<WeekShoesData> weekDataList);
    void onFailure(String msg);
}
