package com.haoxi.xgn.base;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.haoxi.xgn.model.BandShoesActivity;
import com.haoxi.xgn.model.MyShoesActivity;
import com.haoxi.xgn.net.MethodParams;
import com.haoxi.xgn.openBle.BluetoothLeService;
import com.haoxi.xgn.openBle.utils.MPermissionsActivity;
import com.haoxi.xgn.openBle.utils.SampleGattAttributes;
import com.haoxi.xgn.utils.ActivityFragmentInject;
import com.haoxi.xgn.utils.ApiUtils;
import com.haoxi.xgn.utils.BeepManager;
import com.haoxi.xgn.utils.ContentKey;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;

public abstract class BaseActivity extends MPermissionsActivity implements BaseView{

    public static final String APP_SECRET = "99fcf7399865105573df904f72888f19";

    protected String mToken        = "";
    protected String mUserObjId    = "";
    private int mToolbarTitle;
    private int mToolbarIndicator;
    protected Dialog mDialog;
    protected boolean tsPhone = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //将Activity实例添加到AppManager的堆栈
        AppManager.getAppManager().addActivity(this);

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

        if (mMenuId != -1) {
            initToolbar();
        }
        mToken = SPUtils.getInstance().getString(ContentKey.USER_TOKEN);
        mUserObjId = SPUtils.getInstance().getString(ContentKey.USER_OBJ_ID);

        mDialog = new Dialog(this, R.style.DialogTheme);
        mDialog.setCancelable(false);//设置对话框不能消失
        View view = getLayoutInflater().inflate(R.layout.progressdialog, null);
        mDialog.setContentView(view, new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        initBLE();
        registerReceiver(broadcastReceiver, SampleGattAttributes.makeGattUpdateIntentFilter());
        init();

    }

    private void initBLE() {
        boolean bindService = bindService(new Intent(this, BluetoothLeService.class), connection, Context.BIND_AUTO_CREATE);
        if (bindService) {
            Log.w(BandShoesActivity.class.getSimpleName(), "蓝牙初始化成功");
        }
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BluetoothLeService bluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (bluetoothLeService.initBluetooth()) {
                XgnApp.getInstance().setBluetoothLeService(bluetoothLeService);
                BluetoothAdapter bluetoothAdapter = bluetoothLeService.getmBluetoothAdapter();
                if (bluetoothAdapter!=null&&!bluetoothAdapter.isEnabled()){
                    Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBT, 1);
                }
            }
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            XgnApp.getInstance().setBluetoothLeService(null);
        }
    };

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

    /**
     * BLE通讯广播
     */
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case SampleGattAttributes.ACTION_GATT_CONNECTED:
                    //链接
                    btConnect();
                    SPUtils.getInstance().put(ContentKey.BT_CONNECT,true);
                    break;
                case SampleGattAttributes.ACTION_GATT_DISCONNECTED:
                    //断开
                    btDisconnect();
                    SPUtils.getInstance().put(ContentKey.BT_CONNECT,false);
                    showDialog();
                    break;
                case SampleGattAttributes.ACTION_GATT_SERVICES_DISCOVERED:
                    //发现服务
//                    handler.sendEmptyMessageDelayed(0, 2000);
                    break;
                case SampleGattAttributes.ACTION_BLE_REAL_DATA:
//                    parseData(intent.getStringExtra("data"));
                    break;
                case BluetoothAdapter.ACTION_STATE_CHANGED:
                    Log.e(MyShoesActivity.class.getSimpleName(),"state_changed");
                    break;
            }
        }
    };

    protected void btDisconnect() {
    }

    protected void btConnect() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //将Activity实例从AppManager的堆栈中移除
        AppManager.getAppManager().finishActivity(this);
        unregisterReceiver(broadcastReceiver);
        if (connection!=null){
            unbindService(connection);
            connection = null;
        }
    }

    private void showDialog() {

        if (SPUtils.getInstance().getBoolean(ContentKey.SHOW_DIALOG,false)){
            return;
        }

        Log.e("builerd",AppManager.getAppManager().currentActivity().getLocalClassName()+"---------activity");
        final AlertDialog.Builder builder = new AlertDialog.Builder( AppManager.getAppManager().currentActivity());
        builder.setMessage("鞋子连接断开，请注意！");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                BeepManager.cancle();
                SPUtils.getInstance().put(ContentKey.SHOW_DIALOG,false);
                dialogInterface.dismiss();
            }
        });
        SPUtils.getInstance().put(ContentKey.SHOW_DIALOG,true);
        builder.show();
    }
}
