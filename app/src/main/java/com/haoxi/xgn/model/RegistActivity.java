package com.haoxi.xgn.model;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.haoxi.xgn.MainActivity;
import com.haoxi.xgn.R;
import com.haoxi.xgn.base.BaseActivity;
import com.haoxi.xgn.model.loginregist.EasePresenter;
import com.haoxi.xgn.model.loginregist.IEaseView;
import com.haoxi.xgn.net.MethodConstant;
import com.haoxi.xgn.net.MethodParams;
import com.haoxi.xgn.net.MethodType;
import com.haoxi.xgn.utils.ActivityFragmentInject;
import com.haoxi.xgn.utils.ApiUtils;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

@ActivityFragmentInject(contentViewId = R.layout.activity_regist,menuId = 1,toolbarTitle = R.string.regist)
public class RegistActivity extends BaseActivity implements IEaseView {

    @BindView(R.id.regist_phone)
    EditText mPhoneEt;
    @BindView(R.id.regist_pwd)
    EditText mPwdEt;
    @BindView(R.id.regist_compwd)
    EditText mComPwdEt;
    @BindView(R.id.regist_code)
    EditText mCodeEt;
    @BindView(R.id.regist_sendcode)
    TextView mSendCodeTv;


    private int type = MethodType.METHOD_TYPE_REQUEST_VER_CODE;
    public  boolean isChange = false;
    private boolean tag      = true;
    private int     i        = 60;
    Thread mThread = null;
    private static Handler mHandler = new Handler();
    private EasePresenter mEasePresenter;

    private boolean isGetCode = false;

    @Override
    protected void init() {
        mEasePresenter = new EasePresenter();
        mEasePresenter.attachView(this);
    }

    @OnClick(R.id.next_btn)
    void nextBtn(){
        if (!isGetCode){
            ApiUtils.showToast(this,"请先获取验证码");
            return;
        }

        if (commitValidate()) {
            Intent intent = new Intent(RegistActivity.this,SetInfoActivity.class);

            intent.putExtra("telephone",mPhoneEt.getText().toString().trim());
            intent.putExtra("sendCode",mCodeEt.getText().toString().trim());
            intent.putExtra("password",mPwdEt.getText().toString().trim());

            startActivity(intent);
        }
    }

    private boolean commitValidate(){
        String phone = mPhoneEt.getText().toString().trim();
        String code = mCodeEt.getText().toString().trim();
        String newPwd = mPwdEt.getText().toString().trim();
        String comNewPwd = mComPwdEt.getText().toString().trim();
        if (phone.equals("")) {
            ApiUtils.showToast(this,"请输入手机号");
            return false;
        } else if (!ApiUtils.isPhoneNumberValid(phone)) {
            ApiUtils.showToast(this,"输入的手机号格式不对");
            return false;
        }
        if (code.equals("")) {
            ApiUtils.showToast(this,"请获取验证码");
            return false;
        }
        if (newPwd.equals("")) {
            ApiUtils.showToast(this,getResources().getString(R.string.input_new_pwd1));
            return false;
        }
        if (comNewPwd.equals("")) {
            ApiUtils.showToast(this,getResources().getString(R.string.input_conform_pwd));
            return false;
        }
        if (!comNewPwd.equals(newPwd)) {
            ApiUtils.showToast(this,getResources().getString(R.string.input_def_pwd));
            return false;
        }
        return true;
    }

    @OnClick(R.id.regist_sendcode)
    void sendCode(){
        if (!isValidate()) return;
        mSendCodeTv.setText("获取验证码");
        mSendCodeTv.setClickable(true);
        isChange = true;
        changeBtnGetCode();
        getValidateCode();
    }
    private boolean isValidate(){
        String mUserPhone = mPhoneEt.getText().toString().trim();
        if (mUserPhone.isEmpty()){
            ApiUtils.showToast(RegistActivity.this,"请输入手机号");
            return false;
        }
        if (!ApiUtils.isPhoneNumberValid(mUserPhone)){
            ApiUtils.showToast(this, getResources().getString(R.string.user_phone_valid));
            return false;
        }
        return true;
    }

    private void changeBtnGetCode() {
        mThread = new Thread(){
            @Override
            public void run() {
                if (tag){
                    while (i > 0){
                        i--;
                        RegistActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mSendCodeTv.setText("重新获取("+i+")");
                                mSendCodeTv.setClickable(false);
                            }
                        });
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    tag = false;
                }
                i = 60;
                tag = true;
                RegistActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mSendCodeTv.setText("获取验证码");
                        mSendCodeTv.setClickable(true);
                    }
                });
            }
        };
        mThread.start();
    }
    private void getValidateCode() {
        String phone = mPhoneEt.getText().toString().trim();
        if (phone.equals("")){
            ApiUtils.showToast(this, "请输入手机号!");
        }else {
            type = MethodType.METHOD_TYPE_REQUEST_VER_CODE;
            mEasePresenter.setNeedShowProgess(false);
            mEasePresenter.getDataFromNets(getParaMap(MethodType.METHOD_TYPE_REQUEST_VER_CODE));
        }
    }
    protected Map<String, String> getParaMap(int type) {
        Map<String,String> map = super.getParaMap();
        map.put(MethodParams.USER_TELEPHONE,mPhoneEt.getText().toString().trim());
        return map;
    }

    @Override
    public String getMethod() {
        return MethodConstant.REQUEST_VER_CODE;
    }

    @Override
    public void todo() {
        isGetCode = true;
    }


    @Override
    public void failture(String msg) {
        ApiUtils.showToast(this,msg);
    }
}
