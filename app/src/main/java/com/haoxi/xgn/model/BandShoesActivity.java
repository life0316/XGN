package com.haoxi.xgn.model;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.haoxi.xgn.R;
import com.haoxi.xgn.base.BaseActivity;
import com.haoxi.xgn.base.XgnApp;
import com.haoxi.xgn.bean.DeviceBean;
import com.haoxi.xgn.model.mvp.DevicePresenter;
import com.haoxi.xgn.model.mvp.EasePresenter;
import com.haoxi.xgn.model.mvp.IDeviceView;
import com.haoxi.xgn.model.mvp.IEaseView;
import com.haoxi.xgn.net.MethodConstant;
import com.haoxi.xgn.net.MethodParams;
import com.haoxi.xgn.net.MethodType;
import com.haoxi.xgn.utils.ActivityFragmentInject;
import com.haoxi.xgn.utils.ApiUtils;
import com.haoxi.xgn.utils.ContentKey;
import com.haoxi.xgn.widget.CustomDialog;
import com.haoxi.xgn.zxing.android.CaptureActivity;

import java.util.Map;

import butterknife.BindView;

@ActivityFragmentInject(contentViewId = R.layout.activity_bandshoes,menuId = 1,toolbarTitle = R.string.band_shoes)
public class BandShoesActivity extends BaseActivity implements View.OnClickListener,IDeviceView,IEaseView {

    @BindView(R.id.custom_toolbar_right)
    TextView mAddToolbarTv;
    @BindView(R.id.addshoes_btn)
    TextView mAddShoesTv;

    @BindView(R.id.deviceEdit)
    Button mEditBtn;
    @BindView(R.id.shoesUnbind)
    Button mUnbindBtn;
    @BindView(R.id.shoesConnect)
    Button mShoesConnectBtn;

    @BindView(R.id.deviceName)
    TextView mDeviceNameTv;
    @BindView(R.id.deviceId)
    TextView mDeviceIdTv;
    @BindView(R.id.deviceMac)
    TextView mDeviceMacTv;

    @BindView(R.id.addShoesLl)
    LinearLayout mAddShoesLl;
    @BindView(R.id.shoes_show)
    LinearLayout mShoesShowLl;

    private DevicePresenter devicePresenter;
    private EasePresenter easePresenter;
    private String deviceId = "";
    private CustomDialog dialog;
    private String address = "30:BB:10:27:00:03";

    private int methodType = MethodType.METHOD_TYPE_DEVICE_GETBINDED;

    private static final int REQUEST_CODE_SCAN = 0x0000;
    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";

    protected String[] needPermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA
    };

    private boolean isNeedCheck = true;

    @Override
    protected void init() {
//        initBLE();
//        registerReceiver(broadcastReceiver, SampleGattAttributes.makeGattUpdateIntentFilter());
        devicePresenter = new DevicePresenter();
        devicePresenter.attachView(this);

        easePresenter = new EasePresenter();
        easePresenter.attachView(this);

        mAddToolbarTv.setVisibility(View.VISIBLE);
        mAddToolbarTv.setText("添加");
        mAddToolbarTv.setOnClickListener(this);
        mAddShoesTv.setOnClickListener(this);
        mEditBtn.setOnClickListener(this);
        mUnbindBtn.setOnClickListener(this);
        mShoesConnectBtn.setOnClickListener(this);

        mShoesConnectBtn.setText(SPUtils.getInstance().getBoolean(ContentKey.BT_CONNECT)?"断开":"连接");

        devicePresenter.getDataFromNets(getParaMap());
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.custom_toolbar_right:
            case R.id.addshoes_btn:
                if (isNeedCheck) {
                    if (!ApiUtils.checkPermissions(this, ContentKey.PERMISSION_REQUESTCODE_1, needPermissions)) {
                        toScan();
                    }
                } else {
                    toScan();
                }
                break;
            case R.id.deviceEdit:
                break;
            case R.id.shoesUnbind:
                deleteOnCli();
                break;
            case R.id.shoesConnect:
                if (mShoesConnectBtn.getText().equals("连接")){
                    XgnApp.getInstance().getBluetoothLeService().connect(address);
                }else if(mShoesConnectBtn.getText().equals("断开")){
                    XgnApp.getInstance().getBluetoothLeService().disconnect();
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case ContentKey.PERMISSION_REQUESTCODE_1:
                //没有授权
                if (!ApiUtils.verifyPermissions(grantResults)) {
                    isNeedCheck = true;
                } else {
                    isNeedCheck = false;
                    toScan();
                }
                break;
            case ContentKey.PERMISSION_REQUESTCODE_2:
                //没有授权
                if (!ApiUtils.verifyPermissions(grantResults)) {
                    isNeedCheck = false;
                } else {
                    isNeedCheck = false;
                }
                break;
        }
    }

    public void toScan(){
        Intent intent = new Intent(this,CaptureActivity.class);
        startActivityForResult(intent,REQUEST_CODE_SCAN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == CaptureActivity.RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(DECODED_CONTENT_KEY);
                Bitmap bitmap = data.getParcelableExtra(DECODED_BITMAP_KEY);
                if (!"Scan failed!".equals(content)) {
                    deviceId = content;
                    methodType = MethodType.METHOD_TYPE_DEVICE_BIND;
                    devicePresenter.getDataFromNets(getParaMap());
                }
            }
        }
    }

    private void deleteOnCli() {
            dialog = new CustomDialog(this, "解除绑定", "确定要解除绑定?", "确定", "取消");
            dialog.setCancelable(true);
            dialog.show();
            dialog.setClickListenerInterface(new CustomDialog.ClickListenerInterface() {
                @Override
                public void doConfirm() {
                    methodType = MethodType.METHOD_TYPE_DEVICE_UNBIND;
                    easePresenter.getDataFromNets(getParaMap());
                    dialog.dismiss();
                }
                @Override
                public void doCancel() {
                    dialog.dismiss();
                }
            });
    }

    @Override
    protected Map<String, String> getParaMap() {
        Map<String,String> map = super.getParaMap();
        map.put(MethodParams.PARAMS_USERID,mUserObjId);
        map.put(MethodParams.PARAMS_TOKEN,mToken);
        switch (methodType){
            case MethodType.METHOD_TYPE_DEVICE_BIND:
            case MethodType.METHOD_TYPE_DEVICE_UNBIND:
                map.put(MethodParams.PARAMS_DEVICE_ID,deviceId);
                break;
        }
        return map;
    }

    @Override
    public String getMethod() {
        String method ="";
        switch (methodType){
            case MethodType.METHOD_TYPE_DEVICE_GETBINDED:
                method = MethodConstant.SHOES_INFO;
                break;
            case MethodType.METHOD_TYPE_DEVICE_BIND:
                method = MethodConstant.SHOES_BIND;
                break;
            case MethodType.METHOD_TYPE_DEVICE_UNBIND:
                method = MethodConstant.SHOES_UNBIND;
                break;
        }
        return method;
    }

    @Override
    public void getSuccess(DeviceBean deviceBean) {

        mAddShoesLl.setVisibility(View.GONE);
        mShoesShowLl.setVisibility(View.VISIBLE);
        mAddToolbarTv.setVisibility(View.GONE);

        mDeviceNameTv.setText(deviceBean.getDevicename());
        mDeviceIdTv.setText(deviceBean.getDeviceid());
        mDeviceMacTv.setText(deviceBean.getBtmac());
        deviceId = deviceBean.getDeviceid();
        SPUtils.getInstance().put(ContentKey.DEVICEID,deviceId);
    }

    @Override
    public void getFailture(String msg) {
        mAddShoesLl.setVisibility(View.VISIBLE);
        ApiUtils.showToast(this,msg);
    }

    @Override
    public void todo() {
        mAddShoesLl.setVisibility(View.VISIBLE);
        mShoesShowLl.setVisibility(View.GONE);
        mAddToolbarTv.setVisibility(View.VISIBLE);
        SPUtils.getInstance().put(ContentKey.DEVICEID,"");
    }

    @Override
    public void failture(String msg) {
        ApiUtils.showToast(this,msg);
    }

    @Override
    protected void btConnect() {
        super.btConnect();
        mShoesConnectBtn.setText("断开");
        BandShoesActivity.this.finish();
        SPUtils.getInstance().put(ContentKey.MAIN_PAGE,1);
    }

    @Override
    protected void btDisconnect() {
        super.btDisconnect();
        mShoesConnectBtn.setText("连接");
    }
}
