package com.haoxi.xgn.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.haoxi.xgn.R;
import com.haoxi.xgn.base.BaseLazyFragment;
import com.haoxi.xgn.bean.HistoryBean;
import com.haoxi.xgn.bean.WeekShoesData;
import com.haoxi.xgn.net.MethodConstant;
import com.haoxi.xgn.net.MethodParams;
import com.haoxi.xgn.utils.ContentKey;
import com.haoxi.xgn.weekdatas.IWeekDataView;
import com.haoxi.xgn.weekdatas.WeekDataPresenter;
import com.haoxi.xgn.widget.TenHistroyView;
import com.haoxi.xgn.zxing.android.Intents;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StatisticsFragment2 extends BaseLazyFragment implements IWeekDataView {

    // 标志位，标志已经初始化完成。
    private boolean isPrepared;

    @BindView(R.id.custom_toolbar_tv)
    TextView titleTv;
    @BindView(R.id.weakMouthLl)
    LinearLayout weakMouthLl;
    @BindView(R.id.tenview)
    TenHistroyView histroyView;

    @BindView(R.id.timeTv)
    TextView timeTv;
    @BindView(R.id.totalStep)
    TextView totalStepTv;
    @BindView(R.id.totalKm)
    TextView totalKmTv;
    @BindView(R.id.aveStep)
    TextView aveStepTv;
    @BindView(R.id.aveKm)
    TextView aveKmTv;

    private WeekDataPresenter weekDataPresenter;
    private List<HistoryBean> eachList = new ArrayList<>();

    private int weekTotalStep;
    private double weekKm;
    private int aveStep;
    private double aveKm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics2, container, false);
        ButterKnife.bind(this,view);
        weekDataPresenter = new WeekDataPresenter();
        weekDataPresenter.attachView(this);
        //XXX初始化view的各控件
        isPrepared = true;
        lazyLoad();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleTv.setText("步数统计");
        weekDataPresenter.getDataFromNets(getParaMap());
    }

    @Override
    protected void lazyLoad() {
        if(!isPrepared || !isVisible) {
            return;
        }
        SPUtils.getInstance().put(ContentKey.MAIN_PAGE,2);
        //填充各控件的数据
        Log.e("jiazai","预加载----ProfileFragment-------1");

//        eachList.add(new HistoryBean(300,"1月1日"));
//        eachList.add(new HistoryBean(500,"1月2日"));
//        eachList.add(new HistoryBean(0,"1月3日"));
//        eachList.add(new HistoryBean(200,"1月4日"));
//        eachList.add(new HistoryBean(900,"1月5日"));
//        eachList.add(new HistoryBean(0,"1月6日"));
//        eachList.add(new HistoryBean(100,"1月7日"));
//        histroyView.setEachList(eachList);

    }

    @Override
    public String getMethod() {
        return MethodConstant.GET_WEEK_DATA;
    }

    @Override
    protected Map<String, String> getParaMap() {
        Map<String,String> map = super.getParaMap();
        map.put(MethodParams.PARAMS_USERID,mUserObjId);
        map.put(MethodParams.PARAMS_TOKEN,mToken);
        return map;
    }

    @Override
    public void getDataSuccess(List<WeekShoesData> weekDataList) {
        Log.e("ooooooooo",weekDataList.size()+"-----size");
        eachList.clear();
        weekTotalStep = 0;
        for (int i = 0; i < weekDataList.size(); i++) {
            WeekShoesData weekShoesData = weekDataList.get(i);
            weekTotalStep += weekShoesData.getSteps();
            Log.e("ooooooooo",weekShoesData.getSteps()+"-----");
            Log.e("ooooooooo",weekShoesData.getDate());
            String[] date = weekShoesData.getDate().split("-");
            Log.e("ooooooooo",(date[1]+"日"+date[2]+"月"));
            HistoryBean historyBean = new HistoryBean(weekShoesData.getSteps(),Integer.parseInt(date[1])+"月"+Integer.parseInt(date[2])+"日");
            eachList.add(historyBean);
        }

        java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#0.0");
         weekKm = weekTotalStep  * 0.7 / 1000;

        aveStep = weekTotalStep / weekDataList.size();
        aveKm = weekKm * 1000 / weekDataList.size();

        totalStepTv.setText(String.valueOf(weekTotalStep));
        aveStepTv.setText("日均步数为"+aveStep+"步");
        totalKmTv.setText(df.format(weekKm));
        aveKmTv.setText("日均里程为"+df.format(aveKm / 1000)+"千米");

        histroyView.setEachList(eachList);
    }

    @Override
    public void onFailure(String msg) {

    }
}
