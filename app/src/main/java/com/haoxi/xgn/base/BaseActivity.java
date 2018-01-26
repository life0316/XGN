package com.haoxi.xgn.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.haoxi.xgn.R;
import com.haoxi.xgn.net.MethodParams;
import com.haoxi.xgn.utils.ActivityFragmentInject;
import com.haoxi.xgn.utils.ApiUtils;
import com.haoxi.xgn.utils.ContentKey;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements BaseView{

    public static final String APP_SECRET = "99fcf7399865105573df904f72888f19";

    protected String mToken        = "";
    protected String mUserObjId    = "";;
    private int mToolbarTitle;
    private int mToolbarIndicator;
    protected Dialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int mContentViewId;
        int mMenuId;
        if (getClass().isAnnotationPresent(ActivityFragmentInject.class)){
            ActivityFragmentInject annotation = getClass().getAnnotation(ActivityFragmentInject.class);
            mContentViewId = annotation.contentViewId();
            mMenuId = annotation.menuId();
            mToolbarTitle = annotation.toolbarTitle();
            mToolbarIndicator = annotation.toolbarIndicator();
        }else {
            throw new RuntimeException("类没有进行注解异常");
        }
        setContentView(mContentViewId);
        ButterKnife.bind(this);
        init();
        if (mMenuId != -1) {
            initToolbar();
        }
        mToken = SPUtils.getInstance().getString(ContentKey.USER_TOKEN);
        mUserObjId = SPUtils.getInstance().getString(ContentKey.USER_OBJ_ID);

        mDialog = new Dialog(this, R.style.DialogTheme);
        mDialog.setCancelable(false);//设置对话框不能消失
        View view = getLayoutInflater().inflate(R.layout.progressdialog, null);
        mDialog.setContentView(view, new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

    }

    private void initToolbar() {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        TextView mTitle=findViewById(R.id.custom_toolbar_tv);
        if (mToolbar != null) {
            mToolbar.setContentInsetStartWithNavigation(0);
            setSupportActionBar(mToolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
            if (mToolbarTitle != -1) {
//                getSupportActionBar().setTitle(mToolbarTitle);
                getSupportActionBar().setTitle("");
                mTitle.setText(mToolbarTitle);
            }else {
                getSupportActionBar().setTitle("");
            }
            if (mToolbarIndicator != -1) {
                getSupportActionBar().setHomeAsUpIndicator(mToolbarIndicator);
            }else {
//                getSupportActionBar().setHomeAsUpIndicator(R.mipmap.btn_back_normal);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    protected abstract void init();


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
        String versionName = ApiUtils.getVersionName(this);
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

    private void setDialogWindow(Dialog mDialog) {
        Window window = mDialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.CENTER;
            window.setAttributes(params);
        }
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
}
