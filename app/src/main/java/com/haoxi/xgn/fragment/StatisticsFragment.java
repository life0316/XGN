package com.haoxi.xgn.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.haoxi.xgn.R;
import com.haoxi.xgn.base.BaseLazyFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatisticsFragment extends BaseLazyFragment {

    // 标志位，标志已经初始化完成。
    private boolean isPrepared;

    @BindView(R.id.custom_toolbar_tv)
    TextView titleTv;

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.dayRb)
    RadioButton dayRb;
    @BindView(R.id.weakRb)
    RadioButton weakRb;
    @BindView(R.id.mouthRb)
    RadioButton mouthRb;

    @BindView(R.id.dayConsumeLl)
    LinearLayout dayConsumeLl;

    @BindView(R.id.weakMouthLl)
    LinearLayout weakMouthLl;

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleTv.setText("步数统计");
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId){
                    case R.id.dayRb:
                        dayRb.setTextColor(getResources().getColor(android.R.color.white));
                        weakRb.setTextColor(getResources().getColor(R.color.colorPrimary));
                        mouthRb.setTextColor(getResources().getColor(R.color.colorPrimary));
                        dayConsumeLl.setVisibility(View.VISIBLE);
                        weakMouthLl.setVisibility(View.GONE);
                        break;
                    case R.id.weakRb:
                        weakRb.setTextColor(getResources().getColor(android.R.color.white));
                        dayRb.setTextColor(getResources().getColor(R.color.colorPrimary));
                        mouthRb.setTextColor(getResources().getColor(R.color.colorPrimary));
                        dayConsumeLl.setVisibility(View.GONE);
                        weakMouthLl.setVisibility(View.VISIBLE);
                        break;
                    case R.id.mouthRb:
                        mouthRb.setTextColor(getResources().getColor(android.R.color.white));
                        weakRb.setTextColor(getResources().getColor(R.color.colorPrimary));
                        dayRb.setTextColor(getResources().getColor(R.color.colorPrimary));
                        dayConsumeLl.setVisibility(View.GONE);
                        weakMouthLl.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
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
