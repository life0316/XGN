package com.haoxi.xgn;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.haoxi.xgn.base.BaseActivity;
import com.haoxi.xgn.base.MyBaseAdapter;
import com.haoxi.xgn.bean.EaseDataBean;
import com.haoxi.xgn.bean.UserInfoData;
import com.haoxi.xgn.fragment.ProfileFragment;
import com.haoxi.xgn.fragment.StatisticsFragment;
import com.haoxi.xgn.fragment.StatisticsFragment2;
import com.haoxi.xgn.fragment.StepsFragment;
import com.haoxi.xgn.model.mvp.ILoginView;
import com.haoxi.xgn.model.mvp.LoginModel;
import com.haoxi.xgn.model.mvp.LoginPresenter;
import com.haoxi.xgn.net.MethodConstant;
import com.haoxi.xgn.net.MethodParams;
import com.haoxi.xgn.utils.ActivityFragmentInject;
import com.haoxi.xgn.utils.ApiUtils;
import com.haoxi.xgn.utils.BeepManager;
import com.haoxi.xgn.utils.ContentKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import butterknife.BindView;

@ActivityFragmentInject(contentViewId = R.layout.activity_main)
public class MainActivity extends BaseActivity implements ILoginView {
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    private MenuItem menuItem;
    private LoginPresenter presenter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mViewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    mViewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_notifications:
                    mViewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

    protected Bundle getBundle(String arg) {
        Bundle bundle = new Bundle();
        bundle.putString("key", arg);
        return bundle;
    }

    @Override
    protected void init() {

        presenter = new LoginPresenter(new LoginModel());
        presenter.attachView(this);

        SPUtils.getInstance().put(ContentKey.MAIN_PAGE,1);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_dashboard);

        MyBaseAdapter adapter = new MyBaseAdapter(getSupportFragmentManager(),this);
        mViewPager.setAdapter(adapter);
        adapter.addFragment(ProfileFragment.class,getBundle(getString(R.string.title_profile)));
        adapter.addFragment(StepsFragment.class,getBundle(getString(R.string.title_jibu)));
        adapter.addFragment(StatisticsFragment2.class,getBundle(getString(R.string.title_tongji)));
//        mViewPager.setCurrentItem(1);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    navigation.getMenu().getItem(0).setChecked(false);
                }
                menuItem = navigation.getMenu().getItem(position);
                menuItem.setChecked(true);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        if (SPUtils.getInstance().getBoolean(ContentKey.IS_REGIST,false)){
            presenter.getDataFromNets(getParaMap());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewPager.setCurrentItem(SPUtils.getInstance().getInt(ContentKey.MAIN_PAGE,1));
    }

    @Override
    protected void btDisconnect() {
        super.btDisconnect();
        if (SPUtils.getInstance().getBoolean("isOpenLose",false)){
            BeepManager.playBeepSoundAndVibrate(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SPUtils.getInstance().put(ContentKey.MAIN_PAGE,1);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public String getMethod() {
        return MethodConstant.USER_INFO_DETAIL;
    }
    @Override
    protected Map<String, String> getParaMap() {
        Map<String,String> map = super.getParaMap();
        map.put(MethodParams.PARAMS_TOKEN,mToken);
        map.put(MethodParams.PARAMS_USERID,mUserObjId);
        return map;
    }

    @Override
    public void toGetDetail(EaseDataBean user) {
        SPUtils.getInstance().put(ContentKey.IS_REGIST,false);
        UserInfoData infoData = user.getData();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (infoData.getBirthday().equals("")) infoData.setBirthday(sdf.format(date));
        ApiUtils.sp(infoData);
    }

    @Override
    public void loginFail(String msg) {

    }
}
