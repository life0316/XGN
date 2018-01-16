package com.haoxi.xgn;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.widget.TextView;
import com.haoxi.xgn.base.BaseActivity;
import com.haoxi.xgn.base.MyBaseAdapter;
import com.haoxi.xgn.fragment.ProfileFragment;
import com.haoxi.xgn.fragment.StatisticsFragment;
import com.haoxi.xgn.fragment.StepsFragment;
import com.haoxi.xgn.utils.ActivityFragmentInject;
import butterknife.BindView;

@ActivityFragmentInject(contentViewId = R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    private MenuItem menuItem;

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
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_dashboard);

        MyBaseAdapter adapter = new MyBaseAdapter(getSupportFragmentManager(),this);
        mViewPager.setAdapter(adapter);
        adapter.addFragment(ProfileFragment.class,getBundle(getString(R.string.title_profile)));
        adapter.addFragment(StepsFragment.class,getBundle(getString(R.string.title_jibu)));
        adapter.addFragment(StatisticsFragment.class,getBundle(getString(R.string.title_tongji)));
        mViewPager.setCurrentItem(1);

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
    }

    @Override
    public void onBackPressed() {
    }
}
