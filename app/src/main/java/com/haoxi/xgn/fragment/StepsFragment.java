package com.haoxi.xgn.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.haoxi.xgn.R;
import com.haoxi.xgn.base.BaseLazyFragment;
import com.haoxi.xgn.widget.StepsView;
import com.haoxi.xgn.widget.WheelView;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsFragment extends BaseLazyFragment {


    // 标志位，标志已经初始化完成。
    private boolean isPrepared;

    @BindView(R.id.tasks_view)
    StepsView stepsView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_counter, container, false);
        ButterKnife.bind(this,view);
        //XXX初始化view的各控件
        isPrepared = true;
        lazyLoad();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        stepsView.setmTotalProgress(20000);

        new Thread(new ProgressRunable()).start();
    }

    private int mTotalProgress = 20000;
    private int mCurrentProgress = 0;

    class ProgressRunable implements Runnable {
        @Override
        public void run() {
            while (mCurrentProgress < mTotalProgress) {
                mCurrentProgress += 100;
                stepsView.setProgress(mCurrentProgress);
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
        //填充各控件的数据
        Log.e("jiazai","预加载----ProfileFragment-------1");
    }
}
