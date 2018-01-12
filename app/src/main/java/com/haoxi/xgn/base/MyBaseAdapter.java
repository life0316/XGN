package com.haoxi.xgn.base;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class MyBaseAdapter extends FragmentPagerAdapter {

    private final Context context;

    ArrayList<Class<?>> fragments=new ArrayList<Class<?>>();
    ArrayList<Bundle> bundles = new ArrayList<Bundle>();
    public MyBaseAdapter(FragmentManager fm,Context context) {
        super(fm);
        this.context = context;
    }

    public void addFragment(Class<?> fragment,Bundle bundle){
        fragments.add(fragment);
        bundles.add(bundle);
        notifyDataSetChanged();
    }

    public void clearFragment(){
        fragments.clear();
        bundles.clear();
    }

    @Override
    public Fragment getItem(int position) {
        Class<?> clazz=fragments.get(position);
        return Fragment.instantiate(context,clazz.getName(), bundles.get(position));
    }

    @Override
    public int getCount() {
        return fragments.size()==0?0:fragments.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
