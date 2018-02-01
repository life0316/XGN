package com.haoxi.xgn.model;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v7.app.AlertDialog.Builder;
import com.haoxi.xgn.R;
import com.haoxi.xgn.adapter.MyShoesAdapter;
import com.haoxi.xgn.base.BaseActivity;
import com.haoxi.xgn.base.XgnApp;
import com.haoxi.xgn.model.mvp.EasePresenter;
import com.haoxi.xgn.model.mvp.IEaseView;
import com.haoxi.xgn.net.MethodConstant;
import com.haoxi.xgn.net.MethodParams;
import com.haoxi.xgn.openBle.BleDevice;
import com.haoxi.xgn.openBle.BluetoothLeService;
import com.haoxi.xgn.openBle.utils.ParseLeAdvData;
import com.haoxi.xgn.openBle.utils.SampleGattAttributes;
import com.haoxi.xgn.openBle.utils.SortComparator;
import com.haoxi.xgn.utils.ActivityFragmentInject;
import com.haoxi.xgn.utils.ApiUtils;
import com.haoxi.xgn.utils.BeepManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import butterknife.BindView;

@ActivityFragmentInject(contentViewId = R.layout.activity_myshoes,menuId = 1,toolbarTitle = R.string.my_shoes)
public class MyShoesActivity extends BaseActivity implements View.OnClickListener,IEaseView{

    @BindView(R.id.shoesRv)
    RecyclerView mShoesRv;
    @BindView(R.id.showAdd)
    LinearLayout mShowAddLl;
    @BindView(R.id.custom_toolbar_tv)
    TextView mTitleRv;

    @BindView(R.id.deviceid)
    TextView mDeviceNameTv;
    @BindView(R.id.btmac)
    TextView mMacTv;
    @BindView(R.id.connect)
    Button mConnectBtn;

    private MyShoesAdapter shoesAdapter;
    private EasePresenter easePresenter;
    private boolean isRefreshing = false;
    private Comparator comp;


    private List<BluetoothDevice> bluetoothDeviceList = new ArrayList<>();
    private List<BleDevice> bleDeviceList = new ArrayList<>();
    private List<BleDevice> adapterList = new ArrayList<>();
    private BleDevice bleDevice;

    private Handler handler  = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
//                case 0://更新设备
//                    Collections.sort(adapterList, comp);
//                    if (adapter!=null){
//                        adapter.notifyDataSetChanged();
//                    }
//                    break;
            }
        }
    };

    @Override
    protected void init() {
//        BluetoothAdapter bluetoothAdapter
        registerReceiver(broadcastReceiver, SampleGattAttributes.makeGattUpdateIntentFilter());
        easePresenter = new EasePresenter();
        easePresenter.attachView(this);
        comp = new SortComparator();
        mConnectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (curDevice != null) {

                    if (mConnectBtn.getText().equals("连接")){
//                        XgnApp.getInstance().getBluetoothLeService().connect(curDevice.getAddress());
                        XgnApp.getInstance().getBluetoothLeService().connect("30:BB:10:27:00:03");
                    }else if(mConnectBtn.getText().equals("断开")){
//                        XgnApp.getInstance().getBluetoothLeService().connect(curDevice.getAddress());
                        XgnApp.getInstance().getBluetoothLeService().disconnect();
                    }
//                }
            }
        });
        initBLE();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mShoesRv.setLayoutManager(linearLayoutManager);

        shoesAdapter = new MyShoesAdapter();
        mShoesRv.setAdapter(shoesAdapter);
        shoesAdapter.setRecyclerViewOnItemClickListener(new MyShoesAdapter.RecyclerViewOnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position, BleDevice deviceBean) {
                if (deviceBean != null){
//                    easePresenter.getDataFromNets(getParaMap(deviceBean));
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
    }

    protected Map<String, String> getParaMap(BleDevice deviceBean) {
        Map<String,String> map = super.getParaMap();
        map.put(MethodParams.PARAMS_USERID,mUserObjId);
        map.put(MethodParams.PARAMS_TOKEN,mToken);
//        map.put(MethodParams.PARAMS_DEVICE_ID,deviceBean.getDeviceid());
//        map.put(MethodParams.PARAMS_DEVICE_NAME,deviceBean.getDevicename());
//        map.put(MethodParams.PARAMS_STATUS,String.valueOf(deviceBean.getStatus()));
//        map.put(MethodParams.PARAMS_SUBMIT,deviceBean.getSubmit());
//        map.put(MethodParams.PARAMS_BTMAC,deviceBean.getBtmac());
        return map;
    }

    @Override
    public String getMethod() {
        return MethodConstant.SHOES_ADD;
    }

    @Override
    public void todo() {
        ApiUtils.showToast(this,"添加成功");
    }

    @Override
    public void failture(String msg) {
        ApiUtils.showToast(this,msg);
    }

    private void initBLE() {
        boolean bindService = bindService(new Intent(this, BluetoothLeService.class), connection, Context.BIND_AUTO_CREATE);
        if (bindService) {
            Log.w(MyShoesActivity.class.getSimpleName(), "蓝牙初始化成功");
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshDevice();
            }
        },500);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        if (connection!=null){
            unbindService(connection);
            connection = null;
        }
//        Process.killProcess(Process.myPid());
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
            broadcastReceiver = null;
        }
        XgnApp.getInstance().getBluetoothLeService().close();
    }

    private void refreshDevice(){
        requestPermission(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},101);
    }

    @Override
    public void permissionSuccess(int requestCode) {
        super.permissionSuccess(requestCode);
        if (requestCode==101){
            startScanDevice();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                System.out.println("蓝牙已打开");
                refreshDevice();
            } else if (resultCode == RESULT_CANCELED) {
                System.out.println("取消打开");
            }
        }
    }


    private void startScanDevice() {
        if (isRefreshing)return;
        isRefreshing = true;
        BluetoothLeService bluetoothLeService = XgnApp.getInstance().getBluetoothLeService();
        if (bluetoothLeService!=null){
            final BluetoothAdapter bluetoothAdapter = bluetoothLeService.getmBluetoothAdapter();
            if (bluetoothAdapter==null){
                isRefreshing = false;
                return;
            }
            mTitleRv.setText("正在扫描...");
            bluetoothDeviceList.clear();
            adapterList.clear();
            bleDeviceList.clear();
//            bluetoothAdapter.startLeScan(new UUID[]{SampleGattAttributes.bltServerUUID},leScanCallback);
            bluetoothAdapter.startLeScan(leScanCallback);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mTitleRv.setText("我的鞋子");
                    isRefreshing = false;
                    bluetoothAdapter.stopLeScan(leScanCallback);
                }
            },30000);
        }else {
            isRefreshing = false;
        }
    }

    private BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            if (!bluetoothDeviceList.contains(device)) {
                bluetoothDeviceList.add(device);
                bleDevice = new BleDevice(device, scanRecord, rssi);
                bleDeviceList.add(bleDevice);
                Log.e("fafsf",device.getAddress()+"-------1");
                Log.e("fafsf",device.getName()+"-------2");
                if (device.getName() != null && "Smart Shoe".equals(device.getName())){
//                    adapterList.add(bleDevice);
                    shoesAdapter.addBleDevice(bleDevice);
//                    handler.sendEmptyMessage(0);

                    mDeviceNameTv.setText(device.getName());
                    mMacTv.setText(device.getAddress());
                    curDevice = device;
                }
            }
        }
    };

    private BluetoothDevice curDevice = null;


    private boolean parseAdvData(int rssi, byte[] scanRecord) {
        if (rssi < -75) return false;
        byte[] bytes = ParseLeAdvData.adv_report_parse(ParseLeAdvData.BLE_GAP_AD_TYPE_MANUFACTURER_SPECIFIC_DATA, scanRecord);
        if (null == bytes || bytes.length < 2) {
            return false;
        }
        if (bytes[0] == 0x01 && bytes[1] == 0x02) {
            return true;
        }
        return false;
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
                    mTitleRv.setText("连接状态：已连接");
                    mConnectBtn.setText("断开");
                    break;
                case SampleGattAttributes.ACTION_GATT_DISCONNECTED:
                    //断开
                    mTitleRv.setText("连接状态：已断开");
                    mConnectBtn.setText("连接");
                    BeepManager.playBeepSoundAndVibrate(MyShoesActivity.this);
                    showDialog();
                    break;
                case SampleGattAttributes.ACTION_GATT_SERVICES_DISCOVERED:
                    //发现服务
                    handler.sendEmptyMessageDelayed(0, 2000);
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
    /**
     * 这是兼容的 AlertDialog
     */
    private void showDialog() {
        final Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("鞋子连接断开，请注意！");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                BeepManager.cancle();
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

}
