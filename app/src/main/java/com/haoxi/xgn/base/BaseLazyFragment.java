package com.haoxi.xgn.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.util.SPUtils;
import com.haoxi.xgn.R;
import com.haoxi.xgn.net.MethodParams;
import com.haoxi.xgn.utils.ApiUtils;
import com.haoxi.xgn.utils.ContentKey;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017\12\21 0021.
 */

public abstract class BaseLazyFragment extends Fragment implements BaseView {
    protected boolean isVisible;

    public static final String APP_SECRET = "99fcf7399865105573df904f72888f19";
    protected String mToken        = "";
    protected String mUserObjId    = "";
    protected Dialog mDialog;
    /**
     * 在这里实现Fragment数据的缓加载.
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToken = SPUtils.getInstance().getString(ContentKey.USER_TOKEN);
        mUserObjId = SPUtils.getInstance().getString(ContentKey.USER_OBJ_ID);
        mDialog = new Dialog(getActivity(), R.style.DialogTheme);
        mDialog.setCancelable(false);//设置对话框不能消失
        View view = getLayoutInflater().inflate(R.layout.progressdialog, null);
        mDialog.setContentView(view, new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

    }

    protected void onVisible() {
        lazyLoad();
    }

    protected abstract void lazyLoad();

    protected void onInvisible() {
    }
    @Override
    public String getTime() {
        long time = ApiUtils.tsToString(ApiUtils.getNowTimestamp());
        return String.valueOf(time);
    }

    @Override
    public String getSign() {
        String sign = "";
        if (getMethod() != null) {
            sign = ApiUtils.MD5(getMethod()+getTime()+getVersion()+ APP_SECRET);
        }
        return sign;
    }

    @Override
    public String getVersion() {
        String versionName = ApiUtils.getVersionName(getActivity());
        if (versionName != null) {
            return "1.0";
        }
        return "1.0";
    }

    protected Map<String,String> getParaMap(){
        Map<String,String> map = new HashMap<>();
        map.put(MethodParams.PARAMS_METHOD,getMethod());
        map.put(MethodParams.PARAMS_SIGEN,getSign());
        map.put(MethodParams.PARAMS_TIME,getTime());
        map.put(MethodParams.PARAMS_VERSION,getVersion());
        return map;
    }

    @Override
    public String getMethod() {
        return "";
    }
    @Override
    public void showProgress() {
        if (mDialog != null) {
            mDialog.show();
        }
        setDialogWindow(mDialog);
    }

    @Override
    public void hideProgress() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }
    private void setDialogWindow(Dialog mDialog) {
        Window window = mDialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.CENTER;
            window.setAttributes(params);
        }
    }
}

