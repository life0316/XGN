package com.haoxi.xgn.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haoxi.xgn.R;
import com.haoxi.xgn.base.BaseLazyFragment;

import butterknife.ButterKnife;

public class StatisticsFragment extends BaseLazyFragment {

    // 标志位，标志已经初始化完成。
    private boolean isPrepared;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        ButterKnife.bind(this,view);
        //XXX初始化view的各控件
        isPrepared = true;
        lazyLoad();
        return view;
    }

    @Override
    protected void lazyLoad() {
        if(!isPrepared || !isVisible) {
            return;
        }
        //填充各控件的数据
        Log.e("jiazai","预加载----ProfileFragment-------1");
    }
}
