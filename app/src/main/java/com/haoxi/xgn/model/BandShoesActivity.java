package com.haoxi.xgn.model;


import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.haoxi.xgn.R;
import com.haoxi.xgn.base.BaseActivity;
import com.haoxi.xgn.bean.DeviceBean;
import com.haoxi.xgn.model.loginregist.DevicePresenter;
import com.haoxi.xgn.model.loginregist.EasePresenter;
import com.haoxi.xgn.model.loginregist.IDeviceView;
import com.haoxi.xgn.net.MethodConstant;
import com.haoxi.xgn.net.MethodParams;
import com.haoxi.xgn.utils.ActivityFragmentInject;

import java.util.Map;

import butterknife.BindView;

@ActivityFragmentInject(contentViewId = R.layout.activity_bandshoes,menuId = 1,toolbarTitle = R.string.band_shoes)
public class BandShoesActivity extends BaseActivity implements View.OnClickListener,IDeviceView{

    @BindView(R.id.custom_toolbar_right)
    TextView mAddToolbarTv;
    @BindView(R.id.addshoes_btn)
    TextView mAddShoesTv;

    private DevicePresenter devicePresenter;
    private EasePresenter easePresenter;

    @Override
    protected void init() {

        devicePresenter = new DevicePresenter();
        devicePresenter.attachView(this);

        mAddToolbarTv.setVisibility(View.VISIBLE);
        mAddToolbarTv.setText("添加");
        mAddToolbarTv.setOnClickListener(this);
        mAddShoesTv.setOnClickListener(this);


        devicePresenter.getDataFromNets(getParaMap());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.custom_toolbar_right:
            case R.id.addshoes_btn:
                startActivity(new Intent(this,MyShoesActivity.class));
                break;
        }
    }

    @Override
    protected Map<String, String> getParaMap() {

        Map<String,String> map = super.getParaMap();

        map.put(MethodParams.PARAMS_USERID,mUserObjId);
        map.put(MethodParams.PARAMS_TOKEN,mToken);
        map.put(MethodParams.PARAMS_DEVICE_ID,"1111");
        return map;
    }

    @Override
    public String getMethod() {
        return MethodConstant.SHOES_INFO;
    }

    @Override
    public void getSuccess(DeviceBean deviceBean) {

        Log.e("fa22222",deviceBean.getBtmac()+"----mac");
        Log.e("fa22222",deviceBean.getDeviceid()+"----deviceid");
        Log.e("fa22222",deviceBean.getDevicename()+"----name");

    }

    @Override
    public void getFailture(String msg) {

    }
}
