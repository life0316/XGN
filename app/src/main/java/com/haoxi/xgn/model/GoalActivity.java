package com.haoxi.xgn.model;

import android.support.v7.widget.AppCompatSeekBar;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.haoxi.xgn.R;
import com.haoxi.xgn.base.BaseActivity;
import com.haoxi.xgn.bean.EaseDataBean;
import com.haoxi.xgn.model.mvp.ILoginView;
import com.haoxi.xgn.model.mvp.LoginModel;
import com.haoxi.xgn.model.mvp.LoginPresenter;
import com.haoxi.xgn.net.MethodConstant;
import com.haoxi.xgn.net.MethodParams;
import com.haoxi.xgn.utils.ActivityFragmentInject;
import com.haoxi.xgn.utils.ApiUtils;
import com.haoxi.xgn.utils.CalorieTool;
import com.haoxi.xgn.utils.ContentKey;

import java.util.Map;

import butterknife.BindView;

@ActivityFragmentInject(contentViewId = R.layout.activity_goal,menuId = 1,toolbarTitle = R.string.sport_target)
public class GoalActivity extends BaseActivity implements SeekBar.OnSeekBarChangeListener,ILoginView {

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
    @BindView(R.id.setGoal)
    Button mSetGoalBtn;

    private LoginPresenter presenter;
    @Override
    protected void init() {
        presenter = new LoginPresenter(new LoginModel());
        presenter.attachView(this);
        //stepTv.setText(String.valueOf(10000));
        sportSeekbar.setProgress(9000);
        sportSeekbar.setOnSeekBarChangeListener(this);
        int curStep = SPUtils.getInstance().getInt(ContentKey.USER_TARGET);
        updateUI(curStep == 0?10000:curStep);
        sportSeekbar.setProgress(curStep == 0?9000:curStep - 1000);

        mSetGoalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.getDataFromNets(getParaMap());
            }
        });
    }
    private int curProgress;

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        curProgress = i;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

        if (curProgress%100 >= 50){
            //stepTv.setText(String.valueOf((curProgress/100+1)*100 + 1000));
            int step = (curProgress/100+1)*100 + 1000;
            updateStep = step;
            updateUI(step);
        }else {
            //stepTv.setText(String.valueOf(curProgress/100*100 + 1000));
            int step = curProgress/100*100 + 1000;
            updateStep = step;
            updateUI(step);
        }
    }

    private int updateStep;

    private void updateUI(int step){
        stepTv.setText(String.valueOf(step));
        java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#0.0");
//        df.format(你要格式化的数字);

        double km = step  * 0.7 / 1000;
        double calorie = CalorieTool.calculateCalorie(58,km,CalorieTool.K_WALKING);
        int time = (int) Math.floor(km * 1000 / 60);
        kilometreTv.setText(df.format(km));
        kcalTv.setText(df.format(calorie));
        timeTv.setText(String.valueOf(time));
    }

    @Override
    protected Map<String, String> getParaMap() {
        Map<String,String> map = super.getParaMap();
        map.put(MethodParams.PARAMS_TOKEN,mToken);
        map.put(MethodParams.PARAMS_USERID,mUserObjId);
        map.put(MethodParams.PARAMS_NICKNAME, SPUtils.getInstance().getString(ContentKey.USER_NICKNAME));
        map.put(MethodParams.PARAMS_GENDER,SPUtils.getInstance().getString(ContentKey.USER_GENDER));
        map.put(MethodParams.PARAMS_BIRTHDAY,SPUtils.getInstance().getString(ContentKey.USER_BIRTHDAY));
        map.put(MethodParams.PARAMS_HEIGHT,String.valueOf(SPUtils.getInstance().getInt(ContentKey.USER_HEIGHT)));
        map.put(MethodParams.PARAMS_WEIGHT,String.valueOf(SPUtils.getInstance().getInt(ContentKey.USER_WEIGHT)));
        map.put(MethodParams.PARAMS_TARGET,String.valueOf(updateStep));
        return map;
    }

    @Override
    public String getMethod() {
        return MethodConstant.USER_INFO_UPDATE;
    }

    @Override
    public void toGetDetail(EaseDataBean user) {
        SPUtils.getInstance().put(ContentKey.USER_TARGET,updateStep);
        SPUtils.getInstance().put(ContentKey.CHANGE_TARGET,true);
//        updateUI(updateStep);
    }

    @Override
    public void loginFail(String msg) {
        ApiUtils.showToast(this,msg);
    }
}
