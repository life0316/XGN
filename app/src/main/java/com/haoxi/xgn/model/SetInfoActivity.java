package com.haoxi.xgn.model;


import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.haoxi.xgn.MainActivity;
import com.haoxi.xgn.R;
import com.haoxi.xgn.base.BaseActivity;
import com.haoxi.xgn.bean.RegistBean;
import com.haoxi.xgn.model.loginregist.IRegistView;
import com.haoxi.xgn.model.loginregist.RegistPresenter;
import com.haoxi.xgn.net.MethodConstant;
import com.haoxi.xgn.net.MethodParams;
import com.haoxi.xgn.net.MethodType;
import com.haoxi.xgn.utils.ActivityFragmentInject;
import com.haoxi.xgn.utils.ApiUtils;
import com.haoxi.xgn.utils.ContentKey;
import com.haoxi.xgn.widget.WheelView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

@ActivityFragmentInject(contentViewId = R.layout.activity_setinfo,menuId = 1,toolbarTitle = R.string.setting_siger)
public class SetInfoActivity extends BaseActivity implements View.OnClickListener,IRegistView {

    private static final List<String> PLANETS = new ArrayList<>();
    private static final List<String> ageLists = new ArrayList<>();
    private static final List<String> weightLists = new ArrayList<>();
    private static final List<String> highLists = new ArrayList<>();
    private static final int SEX_TYPE = 1;
    private static final int WEIGHT_TYPE = 2;
    private static final int AGE_TYPE = 3;
    private static final int HEIGHT_TYPE = 4;

    @BindView(R.id.protocol)
    TextView mProtocolTv;

    @BindView(R.id.sexRl)
    RelativeLayout mSexRl;
    @BindView(R.id.ageRl)
    RelativeLayout mAgeRl;
    @BindView(R.id.weightRl)
    RelativeLayout mWeightRl;
    @BindView(R.id.highRl)
    RelativeLayout mHeightRl;
    @BindView(R.id.nicknameRl)
    RelativeLayout mNicknameRv;
    @BindView(R.id.weightTv)
    TextView mWeightTv;
    @BindView(R.id.sexTv)
    TextView mSexTv;
    @BindView(R.id.ageTv)
    TextView mAgeTv;
    @BindView(R.id.higtTv)
    TextView mHeightTv;
    @BindView(R.id.nicknameTv)
    TextView mNicknameTv;
    @BindView(R.id.regist_btn)
    Button mRegistBtn;

    private Dialog mDialogEt;
    private String changeStr;
    private boolean isChange;
    private String mPhoneStr;
    private String mSendCodeStr;
    private String mPasswordStr;

    NumberPicker dayPicker;
    private String mUserBirth;
    private int updatePamas = 0;
    private final int UPDATE_BIRTH = 0;
    private final int UPDATE_NAME = 1;
    private final int UPDATE_GENDER = 2;
    private final int UPDATE_HEADPIC = 3;
    private String updateName = "";
    private String updateGender = "";
    private String updateHeadpic = "";
    private String updateBirthday = "";

    private int pigeonYear, newAgeYear, newAgeMonth, newAgeDay, age, maxFeedAge;

    private RegistPresenter registPresenter;
    @Override
    protected void init() {
        mProtocolTv.setOnClickListener(this);
        mSexRl.setOnClickListener(this);
        mAgeRl.setOnClickListener(this);
        mWeightRl.setOnClickListener(this);
        mHeightRl.setOnClickListener(this);
        mNicknameRv.setOnClickListener(this);
        mRegistBtn.setOnClickListener(this);

        registPresenter = new RegistPresenter();
        registPresenter.attachView(this);

        for (int i = 1; i < 200; i++) {
            if (i<=130){
                ageLists.add(String.valueOf(i));
            }
            highLists.add(String.valueOf(i));
            weightLists.add(String.valueOf(i));
        }

        PLANETS.add("男");
        PLANETS.add("女");

        Intent intent = getIntent();
        if (intent != null) {
            mPhoneStr = intent.getStringExtra("telephone");
            mSendCodeStr = intent.getStringExtra("sendCode");
            mPasswordStr = intent.getStringExtra("password");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.nicknameRl:
                showMyDialog();
                break;
            case R.id.protocol:
                startActivity(new Intent(SetInfoActivity.this,ProtocolActivity.class));
                break;
            case R.id.sexRl:
                showSexDialog(false,SEX_TYPE,"男",PLANETS,"");
                break;
            case R.id.ageRl:
                showAgeDialog();
                break;
            case R.id.weightRl:
                showSexDialog(true,WEIGHT_TYPE,"63",weightLists,"公斤");
                break;
            case R.id.highRl:
                showSexDialog(true,HEIGHT_TYPE,"178",highLists,"cm");
                break;
            case R.id.regist_btn:
                registPresenter.getDataFromNets(getParaMap());
                Log.e("regist",getParaMap().toString()+"-----map");
                break;
        }
    }

    private void showSexDialog(boolean showDw, final int type, String curStr, List PLANETS, String danwei){
        changeStr = "";
        final Dialog mDialog = new Dialog(this, R.style.DialogTheme);
        mDialog.setCancelable(false);
        View view = getLayoutInflater().inflate(R.layout.sex_dialog,null);
        mDialog.setContentView(view,new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView mCancle = view.findViewById(R.id.dialog_cancle);
        TextView mConform = view.findViewById(R.id.dialog_conform);
        TextView mDanwei = view.findViewById(R.id.dialog_danwei);
        WheelView wheelView = view.findViewById(R.id.dialog_wheelview);

        if (showDw){
            mDanwei.setVisibility(View.VISIBLE);
            mDanwei.setText(danwei);
        }else {
            mDanwei.setVisibility(View.GONE);
        }

        int index = PLANETS.indexOf(curStr);

        wheelView.setOffset(1);
        wheelView.setItems(PLANETS);
        wheelView.setSeletion(index);
        wheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                changeStr = item;
                isChange = true;
            }
        });

        mCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        mConform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isChange) {
                    switch (type){
                        case SEX_TYPE:
                            mSexTv.setText(changeStr);
                            break;
                        case HEIGHT_TYPE:
                            mHeightTv.setText(changeStr);
                            break;
                        case WEIGHT_TYPE:
                            mWeightTv.setText(changeStr);
                            break;
                    }
                    isChange = false;
                }
                mDialog.dismiss();
            }
        });
        ApiUtils.setDialogWindow(mDialog);
        mDialog.show();
    }
    public void showAgeDialog() {

        final Dialog mDialog = new Dialog(this, R.style.DialogTheme);
        mDialog.setCancelable(false);
        View view = getLayoutInflater().inflate(R.layout.personal_birth_dialog, null);
        mDialog.setContentView(view, new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        NumberPicker yearPicker = view.findViewById(R.id.birth_dialog_np_year);
        NumberPicker monthPicker = view.findViewById(R.id.birth_dialog_np_month);
        dayPicker = view.findViewById(R.id.birth_dialog_np_day);

        TextView birthCancle = view.findViewById(R.id.birth_dialog_cancle);
        TextView birthConfirm = view.findViewById(R.id.birth_dialog_confirm);

        birthCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        birthConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempMonth = String.valueOf(newAgeMonth);
                String tempDay = String.valueOf(newAgeDay);

                if (newAgeMonth < 10) tempMonth = "0"+newAgeMonth;
                if (newAgeDay < 10) tempDay = "0"+newAgeDay;

                updateBirthday = newAgeYear+"-"+tempMonth+"-"+tempDay;
                mAgeTv.setText(updateBirthday);
                mDialog.dismiss();
            }
        });

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH ) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        yearPicker.setMinValue(year - 100);
        yearPicker.setMaxValue(year);
        yearPicker.setValue(year);

        monthPicker.setMaxValue(12);
        monthPicker.setMinValue(1);
        monthPicker.setValue(month);

        newAgeYear = yearPicker.getValue();
        newAgeMonth = monthPicker.getValue();
        initDay(String.valueOf(day));
        newAgeDay = dayPicker.getValue();

        yearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                newAgeYear = newVal;
                initDay("");
            }
        });

        monthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                newAgeMonth = newVal;
                initDay("");
            }
        });

        dayPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                newAgeDay = newVal;
            }
        });

        ApiUtils.setDialogWindow(mDialog);
        mDialog.show();

        if (month + 12 - newAgeMonth == 12) {
            maxFeedAge = year - newAgeYear + 1;
        } else if (month + 12 - newAgeMonth < 12) {
            maxFeedAge = year - newAgeYear;
        }
    }

    private void initDay(String dayValue) {

        if (newAgeMonth == 1 || newAgeMonth == 3 || newAgeMonth == 5 || newAgeMonth == 7 || newAgeMonth == 8 || newAgeMonth == 10 || newAgeMonth == 12) {
            dayPicker.setMaxValue(31);
            dayPicker.setMinValue(1);
            if (!"".equals(dayValue)) {
                dayPicker.setValue(Integer.parseInt(dayValue));
            }
        } else if (newAgeMonth == 4 || newAgeMonth == 6 || newAgeMonth == 9 || newAgeMonth == 11) {
            dayPicker.setMaxValue(30);
            dayPicker.setMinValue(1);
            if (!"".equals(dayValue)) {
                dayPicker.setValue(Integer.parseInt(dayValue));
            }
        } else if (newAgeMonth == 2 && newAgeYear % 4 == 0) {
            dayPicker.setMaxValue(29);
            dayPicker.setMinValue(1);
            if (!"".equals(dayValue)) {
                dayPicker.setValue(Integer.parseInt(dayValue));
            }
        } else if (newAgeMonth == 2 && newAgeYear % 4 != 0) {
            dayPicker.setMaxValue(28);
            dayPicker.setMinValue(1);
            if (!"".equals(dayValue)) {
                dayPicker.setValue(Integer.parseInt(dayValue));
            }
        }
    }

    public void showMyDialog() {
        mDialogEt = new Dialog(this, R.style.DialogTheme);
        mDialogEt.setCancelable(false);
        View view = getLayoutInflater().inflate(R.layout.pigeon_name_dialog, null);
        final EditText mEtDialog = view.findViewById(R.id.pigeon_name_dialog_et);
        TextView nameCancle = view.findViewById(R.id.name_dialog_cancle);
        TextView nameConfirm = view.findViewById(R.id.name_dialog_confirm);
        mEtDialog.setSelection(mEtDialog.getText().length());
        nameCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogEt.dismiss();
            }
        });
        nameConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateName = mEtDialog.getText().toString().trim();

                if (updateName.equals("")){
                    ApiUtils.showToast(SetInfoActivity.this,"请输入内容");
                }else {
                    mNicknameTv.setText(updateName);
                    mDialogEt.dismiss();
                }
            }
        });
        mDialogEt.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        mDialogEt.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        mDialogEt.setContentView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ApiUtils.setDialogWindow(mDialogEt);
        mDialogEt.show();
    }

    @Override
    protected Map<String, String> getParaMap() {
        Map<String,String> map = super.getParaMap();

        map.put(MethodParams.PARAMS_NICKNAME,mNicknameTv.getText().toString().trim());
        map.put(MethodParams.PARAMS_GENDER,"女".equals(mSexTv.getText().toString().trim())?"1":"0");
        map.put(MethodParams.PARAMS_BIRTHDAY,mAgeTv.getText().toString().trim());
        map.put(MethodParams.PARAMS_HEIGHT,mHeightTv.getText().toString().trim());
        map.put(MethodParams.PARAMS_WEIGHT,mWeightTv.getText().toString().trim());
        map.put(MethodParams.USER_TELEPHONE,mPhoneStr);
        map.put(MethodParams.PARAMS_PWD,ApiUtils.MD5(mPasswordStr));
        map.put(MethodParams.PARAMS_VER_CODE,mSendCodeStr);

        return map;
    }

    @Override
    public String getMethod() {
        return MethodConstant.REGISTER;
    }

    @Override
    public void registSuccess(RegistBean registBean) {
        SPUtils.getInstance().put(ContentKey.PHONE_KEY,mPhoneStr);
        SPUtils.getInstance().put(ContentKey.PWD_KEY,mPasswordStr);
        SPUtils.getInstance().put(ContentKey.USER_OBJ_ID,registBean.getData().getUserid());
        SPUtils.getInstance().put(ContentKey.USER_TOKEN,registBean.getData().getToken());
        Intent intent = new Intent(SetInfoActivity.this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void failture(String msg) {
        ApiUtils.showToast(this,msg);
    }
}