package com.haoxi.xgn.model;

import android.app.Application;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.haoxi.xgn.R;
import com.haoxi.xgn.adapter.MyShoesAdapter;
import com.haoxi.xgn.base.BaseActivity;
import com.haoxi.xgn.bean.DeviceBean;
import com.haoxi.xgn.model.loginregist.EasePresenter;
import com.haoxi.xgn.model.loginregist.IEaseView;
import com.haoxi.xgn.net.MethodConstant;
import com.haoxi.xgn.net.MethodParams;
import com.haoxi.xgn.utils.ActivityFragmentInject;
import com.haoxi.xgn.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

@ActivityFragmentInject(contentViewId = R.layout.activity_myshoes,menuId = 1,toolbarTitle = R.string.my_shoes)
public class MyShoesActivity extends BaseActivity implements View.OnClickListener,IEaseView{

    @BindView(R.id.shoesRv)
    RecyclerView mShoesRv;
    @BindView(R.id.showAdd)
    LinearLayout mShowAddLl;

    private MyShoesAdapter shoesAdapter;
    private EasePresenter easePresenter;

    @Override
    protected void init() {

        easePresenter = new EasePresenter();
        easePresenter.attachView(this);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mShoesRv.setLayoutManager(linearLayoutManager);

        DeviceBean bean = new DeviceBean("1111","设备1","00:00:00:00","1111111111",0,mUserObjId);
        DeviceBean bean1 = new DeviceBean("2222","设备2","00:00:00:11","1111111111",0,mUserObjId);

        List<DeviceBean> beanList = new ArrayList<>();
        beanList.add(bean);
        beanList.add(bean1);

        shoesAdapter = new MyShoesAdapter(beanList);

        mShoesRv.setAdapter(shoesAdapter);

        if (beanList.size() > 0 ){
            mShowAddLl.setVisibility(View.GONE);
        }else {
            mShowAddLl.setVisibility(View.VISIBLE);
        }

        shoesAdapter.setRecyclerViewOnItemClickListener(new MyShoesAdapter.RecyclerViewOnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position, DeviceBean deviceBean) {
                if (deviceBean != null){
                    easePresenter.getDataFromNets(getParaMap(deviceBean));
                }
            }
        });
    }

    @Override
    public void onClick(View view) {

    }

    protected Map<String, String> getParaMap(DeviceBean deviceBean) {

        Map<String,String> map = super.getParaMap();
        map.put(MethodParams.PARAMS_USERID,mUserObjId);
        map.put(MethodParams.PARAMS_TOKEN,mToken);
        map.put(MethodParams.PARAMS_DEVICE_ID,deviceBean.getDeviceid());
        map.put(MethodParams.PARAMS_DEVICE_NAME,deviceBean.getDevicename());
        map.put(MethodParams.PARAMS_STATUS,String.valueOf(deviceBean.getStatus()));
        map.put(MethodParams.PARAMS_SUBMIT,deviceBean.getSubmit());
        map.put(MethodParams.PARAMS_BTMAC,deviceBean.getBtmac());

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

    }

}
