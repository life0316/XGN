package com.haoxi.xgn.net;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Administrator on 2018\1\23 0023.
 */

public class MethodType {
    /**
     * 获取验证码
     */
    @MethodTypeChecked
    public static final int METHOD_TYPE_REQUEST_VER_CODE = 31;
    /**
     * 验证验证码
     */
    @MethodTypeChecked
    public static final int METHOD_TYPE_VALID_VER_CODE = 32;
    /**
     * 退出
     */
    @MethodTypeChecked
    public static final int METHOD_TYPE_EXIT = 0;
    /**
     * 用户注册
     */
    @MethodTypeChecked
    public static final int METHOD_TYPE_REGISTER = 33;

    /**
     * 用户登录
     */
    @MethodTypeChecked
    public static final int METHOD_TYPE_LOGIN = 34;

    /**
     * 忘记密码
     */
    @MethodTypeChecked
    public static final int METHOD_TYPE_RESET_PWD = 35;

    /**
     * 获取用户信息
     */
    @MethodTypeChecked
    public static final int METHOD_TYPE_USER_DETAIL = 36;
    /**
     * 修改用户信息
     */
    @MethodTypeChecked
    public static final int METHOD_TYPE_USER_UPDATE = 37;

    @IntDef({METHOD_TYPE_REQUEST_VER_CODE,METHOD_TYPE_VALID_VER_CODE,METHOD_TYPE_EXIT,
            METHOD_TYPE_REGISTER,METHOD_TYPE_LOGIN,METHOD_TYPE_RESET_PWD, METHOD_TYPE_USER_DETAIL
            ,METHOD_TYPE_USER_UPDATE
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface MethodTypeChecked{}
}
