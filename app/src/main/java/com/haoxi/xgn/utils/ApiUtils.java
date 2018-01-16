package com.haoxi.xgn.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.haoxi.xgn.R;

import java.util.ArrayList;
import java.util.List;

public class ApiUtils {
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

}
