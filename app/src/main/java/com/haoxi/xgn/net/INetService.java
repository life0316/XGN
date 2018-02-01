package com.haoxi.xgn.net;

import com.alibaba.fastjson.JSON;
import com.haoxi.xgn.bean.DeviceBean;
import com.haoxi.xgn.bean.EaseBean;
import com.haoxi.xgn.bean.EaseDataBean;
import com.haoxi.xgn.bean.OurUser;
import com.haoxi.xgn.bean.OutDeviceBean;
import com.haoxi.xgn.bean.RegistBean;
import com.haoxi.xgn.bean.UpdateBean;
import com.haoxi.xgn.bean.WeekBean;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface INetService {


    //3.1用户申请验证码
    @POST("shoes/")
    @FormUrlEncoded
    Observable<EaseBean> getRequestVerCode(@FieldMap Map<String,String> map);

    //3.2验证码验证
    @POST("shoes/")
    @FormUrlEncoded
    Observable<EaseBean> getValidVerCode(@FieldMap Map<String,String> map);

    //3.3用户注册
    @POST("shoes/")
    @FormUrlEncoded
    Observable<RegistBean> getRegister(@FieldMap Map<String,String> map);

    //3.4用户登录
    @POST("shoes/")
    @FormUrlEncoded
    Observable<EaseDataBean> getOurLogin(@FieldMap Map<String,String> map);

    //3.5.用户退出登录
    @POST("shoes/")
    @FormUrlEncoded
    Observable<EaseBean>  exitApp(@FieldMap Map<String,String> map);

    //3.6获取用户信息
    @POST("shoes/")
    @FormUrlEncoded
    Observable<JSONObject> getDetailInfo(@FieldMap Map<String,String> map);


    //3.6.设置用户信息
    @POST("shoes/")
    @FormUrlEncoded
    Observable<JSONObject> getUpdateInfo(@FieldMap Map<String,String> map);

    //3.7.用户添加定位鞋
    @POST("shoes/")
    @FormUrlEncoded
    Observable<EaseBean> addShoes(@FieldMap Map<String,String> map);

    //3.8.获取定位鞋
    @POST("shoes/")
    @FormUrlEncoded
    Observable<OutDeviceBean> getShoesInfo(@FieldMap Map<String,String> map);

    //3.9.修改定位鞋信息
    @POST("shoes/")
    @FormUrlEncoded
    Observable<JSONObject> setShoesInfo(@FieldMap Map<String,String> map);

    //3.10.删除定位鞋信息
    @POST("shoes/")
    @FormUrlEncoded
    Observable<EaseBean> deleteShoesInfo(@FieldMap Map<String,String> map);

    //3.11.提交定位数据
    @POST("shoes/")
    @FormUrlEncoded
    Observable<EaseBean> submitData(@FieldMap Map<String,String> map);

    //3.12.获取当前最新版本
    @POST("shoes/")
    @FormUrlEncoded
    Observable<UpdateBean> getAppVersion(@FieldMap Map<String,String> map);

    //3.13.获取当前固件最新版本
    @POST("shoes/")
    @FormUrlEncoded
    Observable<JSONObject> getFirmwareVersion(@FieldMap Map<String,String> map);

    //3.13.获取最近一周定位数据
    @POST("shoes/")
    @FormUrlEncoded
    Observable<WeekBean> getWeekData(@FieldMap Map<String,String> map);

}
