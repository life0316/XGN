package com.haoxi.xgn.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.haoxi.xgn.R;
import com.haoxi.xgn.bean.UserInfoData;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApiUtils {

    private static Toast mToast;

    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    /**
     * 判断是否有网络连接
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context){
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null) {
                return networkInfo.isAvailable();
            }
        }
        return false;
    }

    public static void setDialogWindow(Dialog mDialog) {
        Window window = mDialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        window.setWindowAnimations(R.style.mystyle);
    }

    /**
     * 检查权限
     * @param needPermissions
     */
    public static boolean checkPermissions(Activity activity, int requestCode, String... needPermissions) {
        List<String> needRequestPermissonList = findDeniedPermissions(activity,needPermissions);
        if (null != needRequestPermissonList&& needRequestPermissonList.size() > 0 ){
            ActivityCompat.requestPermissions(activity,needRequestPermissonList.toArray(new String[needRequestPermissonList.size()]),requestCode);
            return true;
        }
        return false;
    }

    /**
     *
     * 获取权限集中需要申请权限的列表
     *
     * @param needPermissions
     * @return
     */
    public static List<String> findDeniedPermissions(Activity context,String[] needPermissions) {

        List<String> needRequestPermissonList = new ArrayList<>();
        for (String perm : needPermissions){
            if (ContextCompat.checkSelfPermission(context,perm) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.shouldShowRequestPermissionRationale(context,perm)){
                needRequestPermissonList.add(perm);
            }
        }
        return needRequestPermissonList;
    }
    /**
     * 检测是否有的权限已经授权
     * @param grantResults
     * @return
     */
    public static boolean verifyPermissions(int[] grantResults){
        for (int result:grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }


    /**
     * 获取应用程序名称
     * @param context
     * @return
     */
    public static String getAppName(Context context){
        int labelRes = getPackageInfo(context).applicationInfo.labelRes;
        String appName = context.getResources().getString(labelRes);
        return appName;
    }


    /**
     * 获取版本名
     * @param context
     * @return
     */
    public static String getVersionName(Context context){
        return getPackageInfo(context).versionName;
    }

    public static int getVersionCode(Context context){
        return getPackageInfo(context).versionCode;
    }

    /**
     * 获取版本信息
     * @param context
     * @return
     */
    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo info = null;
        PackageManager pm = context.getPackageManager();
        try {
            info = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            return info;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }


    public static Timestamp getNowTimestamp(){
        return new Timestamp(System.currentTimeMillis());
    }

    public static long tsToString(Timestamp ts){
        DateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
        String str = format.format(ts);
        DateFormat dformat = new SimpleDateFormat("yyyyMMddhhmmss");
        try {
            long millionS = dformat.parse(str).getTime();
            return millionS;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public final static String MD5(String pwd){
        char md5String[] = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
        byte[] btInput = pwd.getBytes();
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(btInput);
            byte[] md = digest.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = md5String[byte0 >>>4 & 0xf];
                str[k++] = md5String[byte0 & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static Toast showToast(Context context, String content){
        if (mToast == null){
            mToast = Toast.makeText(context,content, Toast.LENGTH_SHORT);
        }else{
            mToast.setText(content);
        }
        mToast.show();
        return mToast;
    }
    public static boolean isPhoneNumberValid(String phoneNumber){
        boolean isValid = false;

        String expression = "((^(13|14|15|17|18)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";

        CharSequence inputStr = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()){
            isValid = true;
        }
        return isValid;
    }

    public static void sp( UserInfoData infoData){
        SPUtils.getInstance().put(ContentKey.USER_BIRTHDAY,infoData.getBirthday());
        SPUtils.getInstance().put(ContentKey.USER_GENDER,infoData.getGender());
        SPUtils.getInstance().put(ContentKey.USER_NICKNAME,infoData.getNickname());
        SPUtils.getInstance().put(ContentKey.USER_WEIGHT,infoData.getWeight());
        SPUtils.getInstance().put(ContentKey.USER_TARGET,infoData.getTarget());
        SPUtils.getInstance().put(ContentKey.USER_HEIGHT,infoData.getHeight());
    }

}
