package com.haoxi.xgn.model;

import android.support.v7.widget.AppCompatSeekBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.haoxi.xgn.R;
import com.haoxi.xgn.base.BaseActivity;
import com.haoxi.xgn.utils.ActivityFragmentInject;
import com.haoxi.xgn.utils.CalorieTool;

import butterknife.BindView;

@ActivityFragmentInject(contentViewId = R.layout.activity_goal,menuId = 1,toolbarTitle = R.string.sport_target)
public class GoalActivity extends BaseActivity implements SeekBar.OnSeekBarChangeListener {

    @BindView(R.id.sport_seekbar)
    AppCompatSeekBar sportSeekbar;
    @BindView(R.id.goalStep)
    TextView stepTv;
    @BindView(R.id.kilometre)
    TextView kilometreTv;
    @BindView(R.id.kcal)
    TextView kcalTv;
    @BindView(R.id.time)
    TextView timeTv;

    @Override
    protected void init() {
        stepTv.setText(String.valueOf(10000));
        sportSeekbar.setProgress(9000);
        sportSeekbar.setOnSeekBarChangeListener(this);
        updateUI(10000);
    }
    private int curProgress;

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

        curProgress = i;

//        if (i%100 >= 50){
//            stepTv.setText(String.valueOf((i/100+1)*100 + 1000));
//        }else {
//            stepTv.setText(String.valueOf(i/100*100 + 1000));
//        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

        if (curProgress%100 >= 50){
            stepTv.setText(String.valueOf((curProgress/100+1)*100 + 1000));

            int step = (curProgress/100+1)*100 + 1000;

            updateUI(step);
        }else {
            stepTv.setText(String.valueOf(curProgress/100*100 + 1000));

            int step = curProgress/100*100 + 1000;
            updateUI(step);
        }
    }


    private void updateUI(int step){

        java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#0.0");
//        df.format(你要格式化的数字);

        double km = step  * 0.7 / 1000;
        double calorie = CalorieTool.calculateCalorie(58,km,CalorieTool.K_WALKING);
        int time = (int) Math.floor(km * 1000 / 60);
        kilometreTv.setText(df.format(km));
        kcalTv.setText(df.format(calorie));
        timeTv.setText(String.valueOf(time));
    }
}
