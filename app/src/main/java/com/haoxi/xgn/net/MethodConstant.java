package com.haoxi.xgn.net;

public class MethodConstant {


    /**
     * 3.1 用户申请验证码
     */
    public static final String REQUEST_VER_CODE = "/app/user/request_ver_code";

    /**
     * 3.2 验证码验证
     */
    public static final String VALID_VER_CODE = "/app/user/valid_ver_code";

    /**
     * 3.3 用户注册
     */
    public static final String REGISTER = "/app/user/register";

    /**
     * 3.4 用户登录
     */
    public static final String LOGIN = "/app/user/login";

    /**
     * 3.5 忘记密码
     */
    public static final String RESET_PASSWORD = "/app/user/reset";

    /**
     * 3.6 获取用户信息
     */
    public static final String USER_INFO_DETAIL = "/app/user/get";

    /**
     * 3.7 上传图片
     */
    public static final String IMAGE_UPLOAD = "/app/image/upload";

    /**
     * 3.8 修改用户信息
     */
    public static final String USER_INFO_UPDATE = "/app/user/set";


    /**
     * 3.9退出登录
     */
    public static final String LOGIN_OUT = "/app/user/logout";

    /**
     * 3.9添加定位鞋
     */
    public static final String SHOES_ADD = "/app/device/add";

    /**
     * 3.10获取定位鞋信息
     */
    public static final String SHOES_INFO = "/app/device/get_binded";

    /**
     * 3.11提交定位数据
     */
    public static final String RECORD_SUBMIT = "/app/record/submit";

    /**
     * 3.12获取当前最新版本
     */
    public static final String GET_CURRENT_VERSION = "/app/version/get";

    /**
     * 3.12获取最近一周定位数据
     */
    public static final String GET_WEEK_DATA = "/app/record/get_week";

    /**
     * 3.10绑定设备
     */
    public static final String SHOES_BIND = "/app/device/bind";

    /**
     * 3.10修改定位鞋信息
     */
    public static final String SHOES_SET = "/app/device/set";

    /**
     * 3.10解绑设备
     */
    public static final String SHOES_UNBIND = "/app/device/unbind";
}
