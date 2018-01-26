package com.haoxi.xgn.model;

import android.os.Handler;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.haoxi.xgn.R;
import com.haoxi.xgn.base.BaseActivity;
import com.haoxi.xgn.model.loginregist.EasePresenter;
import com.haoxi.xgn.model.loginregist.IEaseView;
import com.haoxi.xgn.net.MethodConstant;
import com.haoxi.xgn.net.MethodParams;
import com.haoxi.xgn.net.MethodType;
import com.haoxi.xgn.utils.ActivityFragmentInject;
import com.haoxi.xgn.utils.ApiUtils;
import com.haoxi.xgn.utils.ContentKey;

import org.w3c.dom.ProcessingInstruction;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

@ActivityFragmentInject(contentViewId = R.layout.activity_forget,menuId = 1,toolbarTitle = R.string.forget_pwd)
public class ForgetActivity extends BaseActivity implements IEaseView{

    @BindView(R.id.sendCode)
    TextView mSendCodeTv;
    @BindView(R.id.phoneEt)
    EditText mPhoneEt;
    @BindView(R.id.codeEt)
    EditText mCodeEt;
    @BindView(R.id.newpwdEt)
    EditText mNewpwdEt;
    @BindView(R.id.comNewPwdEt)
    EditText mComNewPwdEt;

    private int type = MethodType.METHOD_TYPE_REQUEST_VER_CODE;

    public  boolean isChange = false;
    private boolean tag      = true;
    private int     i        = 60;
    Thread mThread = null;
    private static Handler mHandler = new Handler();
    private EasePresenter mEasePresenter;
    @Override
    protected void init() {
        mEasePresenter = new EasePresenter();
        mEasePresenter.attachView(this);
    }

    @OnClick(R.id.sendCode)
    void sendCode(){
        if (!isValidate()) return;
        mSendCodeTv.setText("获取验证码");
        mSendCodeTv.setClickable(true);
        isChange = true;
        changeBtnGetCode();
        getValidateCode();
    }

    @OnClick(R.id.reset_btn)
    void resetBtn(){
        if (commitValidate()){
            type = MethodType.METHOD_TYPE_RESET_PWD;
            mEasePresenter.setNeedShowProgess(true);
            mEasePresenter.getDataFromNets(getParaMap(type));
        }
    }

    private boolean isValidate(){
        String mUserPhone = mPhoneEt.getText().toString().trim();
        if (mUserPhone.isEmpty()){
            ApiUtils.showToast(ForgetActivity.this,"请输入手机号");
            return false;
        }
        if (!ApiUtils.isPhoneNumberValid(mUserPhone)){
            ApiUtils.showToast(this, getResources().getString(R.string.user_phone_valid));
            return false;
        }
        return true;
    }

    private boolean commitValidate(){
        String phone = mPhoneEt.getText().toString().trim();
        String code = mCodeEt.getText().toString().trim();
        String newPwd = mNewpwdEt.getText().toString().trim();
        String comNewPwd = mComNewPwdEt.getText().toString().trim();
        if (phone.equals("")) {
            ApiUtils.showToast(ForgetActivity.this,"请输入手机号");
            return false;
        } else if (!ApiUtils.isPhoneNumberValid(phone)) {
            ApiUtils.showToast(ForgetActivity.this,"输入的手机号格式不对");
            return false;
        }
        if (code.equals("")) {
            ApiUtils.showToast(ForgetActivity.this,"请获取验证码");
            return false;
        }
        if (newPwd.equals("")) {
            ApiUtils.showToast(ForgetActivity.this,getResources().getString(R.string.input_new_pwd1));
            return false;
        }
        if (comNewPwd.equals("")) {
            ApiUtils.showToast(ForgetActivity.this,getResources().getString(R.string.input_conform_pwd));
            return false;
        }
        if (!comNewPwd.equals(newPwd)) {
            ApiUtils.showToast(ForgetActivity.this,getResources().getString(R.string.input_def_pwd));
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
                        ForgetActivity.this.runOnUiThread(new Runnable() {
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
                ForgetActivity.this.runOnUiThread(new Runnable() {
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
        switch (type){
            case MethodType.METHOD_TYPE_REQUEST_VER_CODE:
                break;
            case MethodType.METHOD_TYPE_RESET_PWD:
                map.put(MethodParams.PARAMS_VER_CODE,mCodeEt.getText().toString().trim());
                map.put(MethodParams.PARAMS_PWD,ApiUtils.MD5(mNewpwdEt.getText().toString().trim()));
                break;
        }
        return map;
    }

    @Override
    public String getMethod() {
        String method = "";
        switch (type){
            case MethodType.METHOD_TYPE_REQUEST_VER_CODE:
                method = MethodConstant.REQUEST_VER_CODE;
                break;
            case MethodType.METHOD_TYPE_RESET_PWD:
                method = MethodConstant.RESET_PASSWORD;
                break;
        }
        return method;
    }

    @Override
    public void todo() {
        if (type == MethodType.METHOD_TYPE_RESET_PWD){
            SPUtils.getInstance().put(ContentKey.PWD_KEY,"");
            finish();
        }
    }

    @Override
    public void failture(String msg) {
        ApiUtils.showToast(this,msg);
    }
}
