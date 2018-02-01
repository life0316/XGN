package com.haoxi.xgn.fragment;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.haoxi.xgn.R;
import com.haoxi.xgn.base.BaseLazyFragment;
import com.haoxi.xgn.model.mvp.EasePresenter;
import com.haoxi.xgn.model.mvp.IEaseView;
import com.haoxi.xgn.net.MethodConstant;
import com.haoxi.xgn.net.MethodParams;
import com.haoxi.xgn.net.MethodType;
import com.haoxi.xgn.openBle.utils.SampleGattAttributes;
import com.haoxi.xgn.utils.ApiUtils;
import com.haoxi.xgn.utils.CalorieTool;
import com.haoxi.xgn.utils.ContentKey;
import com.haoxi.xgn.widget.StepsView;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsFragment extends BaseLazyFragment implements IEaseView {

    private int methodType = MethodType.METHOD_TYPE_USER_UPDATE;

    // 标志位，标志已经初始化完成。
    private boolean isPrepared;
    @BindView(R.id.tasks_view)
    StepsView stepsView;
    @BindView(R.id.connectDevice)
    TextView mConnectDeviceTv;
    @BindView(R.id.needGoalTv)
    TextView mNeedGoalTv;
    @BindView(R.id.kilometre)
    TextView kilometreTv;
    @BindView(R.id.kcal)
    TextView kcalTv;

    private EasePresenter easePresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_counter, container, false);
        ButterKnife.bind(this,view);
        getActivity().registerReceiver(broadcastReceiver, SampleGattAttributes.makeGattUpdateIntentFilter());
        isPrepared = true;
        easePresenter = new EasePresenter();
        easePresenter.attachView(this);

        lazyLoad();
        return view;
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
                    mConnectDeviceTv.setText("设备已连接");
                    break;
                case SampleGattAttributes.ACTION_GATT_DISCONNECTED:
                    //断开
                    mConnectDeviceTv.setText("设备没有连接");
                    break;
                case SampleGattAttributes.ACTION_GATT_SERVICES_DISCOVERED:
                    //发现服务
//                    handler.sendEmptyMessageDelayed(0, 2000);
                    break;
                case SampleGattAttributes.ACTION_BLE_REAL_DATA:
//                    parseData(intent.getStringExtra("data"));
                    break;
                case BluetoothAdapter.ACTION_STATE_CHANGED:
                    break;
            }
        }
    };

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        stepsView.setmTotalProgress(20000);
        new Thread(new ProgressRunable()).start();
    }

    private int mTotalProgress = 20000;
    private int mCurrentProgress = 0;

    @Override
    public void todo() {

    }

    @Override
    public void failture(String msg) {
        ApiUtils.showToast(getActivity(),msg);
    }

    class ProgressRunable implements Runnable {
        @Override
        public void run() {
            while (mCurrentProgress < 1000) {
                mCurrentProgress += 10;
                stepsView.setProgress(mCurrentProgress);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateUI(mCurrentProgress);
                    }
                });
                try {
                    Thread.sleep(30);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void lazyLoad() {
        if(!isPrepared || !isVisible) {
            return;
        }
        SPUtils.getInstance().put(ContentKey.MAIN_PAGE,1);
        //填充各控件的数据
        Log.e("jiazai","预加载----ProfileFragment-------1");
    }

    private void updateUI(int step){
        mNeedGoalTv.setText("距离你的目标还有"+(mTotalProgress - step)+"步");
        java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#0.0");
        double km = step  * 0.7 / 1000;
        double calorie = CalorieTool.calculateCalorie(58,km,CalorieTool.K_WALKING);
        kilometreTv.setText(df.format(km));
        kcalTv.setText(df.format(calorie));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected Map<String, String> getParaMap() {
        Map<String,String> map = super.getParaMap();
        map.put(MethodParams.PARAMS_TOKEN,mToken);
        map.put(MethodParams.PARAMS_USERID,mUserObjId);

        if (methodType == MethodType.METHOD_TYPE_RECORD_SUBMIT){
            map.put(MethodParams.PARAMS_DEVICE_ID, SPUtils.getInstance().getString(ContentKey.DEVICE_ID));
            map.put(MethodParams.PARAMS_DEVICE_LON,  String.valueOf(SPUtils.getInstance().getFloat(ContentKey.LON)));
            map.put(MethodParams.PARAMS_DEVICE_LAT,  String.valueOf(SPUtils.getInstance().getFloat(ContentKey.LAT)));
            map.put(MethodParams.PARAMS_DEVICE_STEPS, String.valueOf(SPUtils.getInstance().getInt(ContentKey.STEPS)));
        }
        return map;
    }

    @Override
    public String getMethod() {
        String method = "";
        switch (methodType){
            case MethodType.METHOD_TYPE_RECORD_SUBMIT:
                method = MethodConstant.RECORD_SUBMIT;
                break;
        }
        return method;
    }
}
