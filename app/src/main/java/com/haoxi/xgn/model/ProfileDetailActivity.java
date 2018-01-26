package com.haoxi.xgn.model;

import android.Manifest;
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
import com.haoxi.xgn.R;
import com.haoxi.xgn.base.BaseActivity;
import com.haoxi.xgn.bean.EaseDataBean;
import com.haoxi.xgn.model.loginregist.EasePresenter;
import com.haoxi.xgn.model.loginregist.IEaseView;
import com.haoxi.xgn.model.loginregist.ILoginView;
import com.haoxi.xgn.model.loginregist.LoginModel;
import com.haoxi.xgn.model.loginregist.LoginPresenter;
import com.haoxi.xgn.net.MethodConstant;
import com.haoxi.xgn.net.MethodParams;
import com.haoxi.xgn.net.MethodType;
import com.haoxi.xgn.utils.ActivityFragmentInject;
import com.haoxi.xgn.utils.ApiUtils;
import com.haoxi.xgn.utils.ContentKey;
import com.haoxi.xgn.widget.WheelView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

@ActivityFragmentInject(contentViewId = R.layout.act_prodetail,menuId = 1,toolbarTitle = R.string.siger)
public class ProfileDetailActivity extends BaseActivity implements View.OnClickListener,ILoginView, IEaseView {

    private static final List<String> PLANETS = new ArrayList<>();
    private static final List<String> ageLists = new ArrayList<>();
    private static final List<String> highLists = new ArrayList<>();
    private static final List<String> weightLists = new ArrayList<>();

    @BindView(R.id.profile_civ)
    CircleImageView mHeadIv;
    @BindView(R.id.update_pwd)
    RelativeLayout mUpdatePwd;
    @BindView(R.id.sexRl)
    RelativeLayout mSexRl;
    @BindView(R.id.ageRl)
    RelativeLayout mAgeRl;
    @BindView(R.id.highRl)
    RelativeLayout mHighRl;
    @BindView(R.id.weightRl)
    RelativeLayout mWeightRl;
    @BindView(R.id.nicknameRl)
    RelativeLayout mNickNameRl;
    @BindView(R.id.nicknameTv)
    TextView mNicknameTv;
    @BindView(R.id.sexTv)
    TextView mSexTv;
    @BindView(R.id.ageTv)
    TextView mAgeTv;
    @BindView(R.id.higtTv)
    TextView mHeightTv;
    @BindView(R.id.weightTv)
    TextView mWeightTv;
    @BindView(R.id.loginout)
    Button mLoginoutBtn;

    NumberPicker dayPicker;
    private String mUserBirth;
    private int updatePamas = 0;
    private final int UPDATE_BIRTH = 0;
    private final int UPDATE_NICKNAME = 1;
    private final int UPDATE_GENDER = 2;
    private final int UPDATE_HEIGHT = 3;
    private final int UPDATE_WEIGHT = 4;
    private final int UPDATE_TARGET = 5;
    private String updateName = "";
    private String updateGender = "";
    private String updateHeight= "";
    private String updateWeight = "";
    private String updateBirthday = "";

    private int newAgeYear, newAgeMonth, newAgeDay, age, maxFeedAge;
    private LoginPresenter presenter;
    private EasePresenter easePresenter;

    private int methodType = MethodType.METHOD_TYPE_USER_UPDATE;
    private int updateType = 0;
    private Dialog mDialogEt;

    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA
    };
    private String changeStr;
    private Dialog mDialog;

    @Override
    protected void init() {

        presenter = new LoginPresenter(new LoginModel());
        presenter.attachView(this);
        easePresenter = new EasePresenter();
        easePresenter.attachView(this);

        mHeadIv.setOnClickListener(this);
        mUpdatePwd.setOnClickListener(this);
        mSexRl.setOnClickListener(this);
        mAgeRl.setOnClickListener(this);
        mHighRl.setOnClickListener(this);
        mWeightRl.setOnClickListener(this);
        mWeightRl.setOnClickListener(this);
        mLoginoutBtn.setOnClickListener(this);
        mNickNameRl.setOnClickListener(this);

        for (int i = 1; i < 200; i++) {
            if (i<=130){
                ageLists.add(String.valueOf(i));
            }
            highLists.add(String.valueOf(i));
            weightLists.add(String.valueOf(i));
        }
        PLANETS.add("男");
        PLANETS.add("女");

        updateUi();
    }

    private void updateUi(){

        String birthday = SPUtils.getInstance().getString(ContentKey.USER_BIRTHDAY);
        String gender = SPUtils.getInstance().getString(ContentKey.USER_GENDER);
        String nickname = SPUtils.getInstance().getString(ContentKey.USER_NICKNAME);
        int weight = SPUtils.getInstance().getInt(ContentKey.USER_WEIGHT);
        int height = SPUtils.getInstance().getInt(ContentKey.USER_HEIGHT);

        if (!nickname.equals("")) {
            mNicknameTv.setText(nickname);
        }
        if (!birthday.equals("")) {
            mAgeTv.setText(birthday);
        }else {
            mAgeTv.setText("未设置");
        }
        if (weight != 0) {
           mWeightTv.setText(String.valueOf(weight));
        }
        if (height != 0) {
            mHeightTv.setText(String.valueOf(height));
        }

        if (gender.equals("2")) {
            mSexTv.setText("女");
        }else {
            mSexTv.setText("男");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.profile_civ:
                showHeadDialog();
                break;
            case R.id.nicknameRl:
                updateType = UPDATE_NICKNAME;
                showMyDialog();
                break;
            case R.id.sexRl:
                updateType = UPDATE_GENDER;
                showSexDialog(false,"sex","男",PLANETS,"");
                break;
            case R.id.ageRl:
                updateType = UPDATE_BIRTH;
                showAgeDialog();
                break;
            case R.id.highRl:
                updateType = UPDATE_HEIGHT;
                showSexDialog(true,"high","168",highLists,"cm");
                break;
            case R.id.weightRl:
                updateType = UPDATE_WEIGHT;
                showSexDialog(true,"weight","63",weightLists,"公斤");
                break;
            case R.id.loginout:
                methodType = MethodType.METHOD_TYPE_EXIT;
                easePresenter.getDataFromNets(getParaMap());
                break;
        }
    }

    private void showHeadDialog(){
        final Dialog mDialog = new Dialog(this, R.style.DialogTheme);
        mDialog.setCancelable(false);
        View view = getLayoutInflater().inflate(R.layout.personal_headciv_dialog,null);
        mDialog.setContentView(view,new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView mTakePhoto = view.findViewById(R.id.headciv_dialog_takephoto);
        TextView mFromPhoto = view.findViewById(R.id.headciv_dialog_fromphoto);
        TextView mCancle = view.findViewById(R.id.headciv_dialog_cancle);
        mCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (isNeedCheck){
//                    if (!ApiUtils.checkPermissions(ProfileDetailActivity.this, ConstantUtils.PERMISSION_REQUESTCODE_1,needPermissions)){
//                        takePhoto();
//                    }
//                }else {
//                    takePhoto();
//                }
                mDialog.dismiss();
            }
        });
        mFromPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (isNeedCheck){
//                    if (!ApiUtils.checkPermissions(ProfileDetailActivity.this, ConstantUtils.PERMISSION_REQUESTCODE_2,needPermissions)){
//                        choosePhoto();
//                    }
//                }else {
//                    choosePhoto();
//                }
                mDialog.dismiss();
            }
        });
        ApiUtils.setDialogWindow(mDialog);
        mDialog.show();
    }

    private void showSexDialog(boolean showDw,String type,String curStr,List PLANETS,String danwei){
        changeStr = "";
        mDialog = new Dialog(this, R.style.DialogTheme);
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
        }

        int index = PLANETS.indexOf(curStr);

        wheelView.setOffset(1);
        wheelView.setItems(PLANETS);
        wheelView.setSeletion(index);
        wheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                changeStr = item;
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

                methodType = MethodType.METHOD_TYPE_USER_UPDATE;
                presenter.getDataFromNets(getParaMap());
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

                Log.e("fafwwww",newAgeYear+"----"+newAgeMonth+"----"+newAgeDay);
                String tempMonth = String.valueOf(newAgeMonth);
                String tempDay = String.valueOf(newAgeDay);

                if (newAgeMonth < 10) tempMonth = "0"+newAgeMonth;
                if (newAgeDay < 10) tempDay = "0"+newAgeDay;

                updateBirthday = newAgeYear+"-"+tempMonth+"-"+tempDay;
                methodType = MethodType.METHOD_TYPE_USER_UPDATE;
                presenter.getDataFromNets(getParaMap());

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
                    ApiUtils.showToast(ProfileDetailActivity.this,"请输入内容");
                }else {
                    methodType = MethodType.METHOD_TYPE_USER_UPDATE;
                    presenter.getDataFromNets(getParaMap());
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
        map.put(MethodParams.PARAMS_TOKEN,mToken);
        map.put(MethodParams.PARAMS_USERID,mUserObjId);

        if (methodType == MethodType.METHOD_TYPE_USER_UPDATE){

            map.put(MethodParams.PARAMS_NICKNAME,SPUtils.getInstance().getString(ContentKey.USER_NICKNAME));
            map.put(MethodParams.PARAMS_GENDER,SPUtils.getInstance().getString(ContentKey.USER_GENDER));
            map.put(MethodParams.PARAMS_BIRTHDAY,SPUtils.getInstance().getString(ContentKey.USER_BIRTHDAY));
            map.put(MethodParams.PARAMS_HEIGHT,String.valueOf(SPUtils.getInstance().getInt(ContentKey.USER_HEIGHT)));
            map.put(MethodParams.PARAMS_WEIGHT,String.valueOf(SPUtils.getInstance().getInt(ContentKey.USER_WEIGHT)));
            map.put(MethodParams.PARAMS_TARGET,String.valueOf(SPUtils.getInstance().getInt(ContentKey.USER_TARGET)));

            switch (updateType){
                case UPDATE_NICKNAME:
                    map.put(MethodParams.PARAMS_NICKNAME,updateName);
                    break;
                case UPDATE_GENDER:
                    map.put(MethodParams.PARAMS_GENDER,changeStr.equals("女")?"2":"1");
                    break;
                case UPDATE_BIRTH:
                    map.put(MethodParams.PARAMS_BIRTHDAY,updateBirthday);
                    break;
                case UPDATE_HEIGHT:
                    map.put(MethodParams.PARAMS_HEIGHT,changeStr);
                    break;
                case UPDATE_WEIGHT:
                    map.put(MethodParams.PARAMS_WEIGHT,changeStr);
                    break;
            }
        }
        return map;
    }

    @Override
    public String getMethod() {
        String method = "";
        switch (methodType){
            case MethodType.METHOD_TYPE_USER_UPDATE:
                method = MethodConstant.USER_INFO_UPDATE;
                break;
            case MethodType.METHOD_TYPE_EXIT:
                method = MethodConstant.LOGIN_OUT;
                break;
        }
        return method;
    }

    @Override
    public void toGetDetail(EaseDataBean user) {
        ApiUtils.sp(user.getData());
        switch (updateType){
            case UPDATE_NICKNAME:
                mNicknameTv.setText(updateName);
                break;
            case UPDATE_GENDER:
                mSexTv.setText(changeStr);
                break;
            case UPDATE_BIRTH:
                mAgeTv.setText(updateBirthday);
                break;
            case UPDATE_HEIGHT:
                mHeightTv.setText(changeStr);
                break;
            case UPDATE_WEIGHT:
                mWeightTv.setText(changeStr);
                break;
        }
    }

    @Override
    public void loginFail(String msg) {

    }

    @Override
    public void todo() {
        SPUtils.getInstance().put(ContentKey.USER_OBJ_ID,"");
        SPUtils.getInstance().put(ContentKey.USER_TOKEN,"");
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void failture(String msg) {

    }
}
