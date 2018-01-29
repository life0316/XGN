package com.haoxi.xgn.model;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.SPUtils;
import com.haoxi.xgn.MainActivity;
import com.haoxi.xgn.R;
import com.haoxi.xgn.base.BaseActivity;
import com.haoxi.xgn.bean.EaseDataBean;
import com.haoxi.xgn.bean.UserInfoData;
import com.haoxi.xgn.model.loginregist.ILoginView;
import com.haoxi.xgn.model.loginregist.LoginModel;
import com.haoxi.xgn.model.loginregist.LoginPresenter;
import com.haoxi.xgn.net.MethodConstant;
import com.haoxi.xgn.net.MethodParams;
import com.haoxi.xgn.net.MethodType;
import com.haoxi.xgn.utils.ActivityFragmentInject;
import com.haoxi.xgn.utils.ApiUtils;
import com.haoxi.xgn.utils.ContentKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.logging.SimpleFormatter;

import butterknife.BindView;

@ActivityFragmentInject(contentViewId = R.layout.activity_login)
public class LoginActivity extends BaseActivity implements View.OnClickListener,ILoginView{

    private static final String TAG = "LoginActivity";

    @BindView(R.id.login_phone)
    EditText phoneEt;
    @BindView(R.id.login_pwd)
    EditText mPwdEt;
    @BindView(R.id.login_btn)
    Button mLoginBtn;
    @BindView(R.id.forget_tv)
    TextView mForgetTv;
    @BindView(R.id.regist_tv)
    TextView mRegistTv;

    private LoginPresenter presenter;

    @Override
    protected void init() {

        presenter = new LoginPresenter(new LoginModel());
        presenter.attachView(this);

        mLoginBtn.setOnClickListener(this);
        mForgetTv.setOnClickListener(this);
        mRegistTv.setOnClickListener(this);
        String phone = SPUtils.getInstance().getString(ContentKey.PHONE_KEY);
        if (!phone.isEmpty()) phoneEt.setText(phone);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String pwd = SPUtils.getInstance().getString(ContentKey.PWD_KEY);
        if (!pwd.isEmpty()) mPwdEt.setText(pwd);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.login_btn:
                if ("".equals(getUserPhone()) || "".equals(getUserPwd())) {
                    return;
                }
                presenter.getDataFromNets(getParaMap());
                break;
            case R.id.forget_tv:
                intent = new Intent(LoginActivity.this,ForgetActivity.class);
                break;
            case R.id.regist_tv:
                intent = new Intent(LoginActivity.this,RegistActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    @Override
    public void toGetDetail(EaseDataBean user) {

        UserInfoData infoData = user.getData();
        SPUtils.getInstance().put(ContentKey.PHONE_KEY,infoData.getTelephone());
        SPUtils.getInstance().put(ContentKey.PWD_KEY,mPwdEt.getText().toString().trim());
        SPUtils.getInstance().put(ContentKey.USER_OBJ_ID,infoData.getUserid());
        SPUtils.getInstance().put(ContentKey.USER_TOKEN,infoData.getToken());

        Log.e("LoginActivity",infoData.getUserid());
        Log.e("LoginActivity",infoData.getToken());

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        if (infoData.getBirthday().equals("")) infoData.setBirthday(sdf.format(date));
        ApiUtils.sp(infoData);

        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void loginFail(String msg) {
        ApiUtils.showToast(this, msg);
    }
    @Override
    public String getMethod() {
        return MethodConstant.LOGIN;
    }
    @Override
    protected Map<String, String> getParaMap() {
        Map<String,String> map = super.getParaMap();
        map.put(MethodParams.PARAMS_PWD,getUserPwd());
        map.put(MethodParams.USER_TELEPHONE,getUserPhone());
        return map;
    }

    public String getUserPhone() {
        String mUsername = phoneEt.getText().toString().trim();
        if ("".equals(mUsername) || mUsername.isEmpty()) {
            ApiUtils.showToast(this, getResources().getString(R.string.input_phone));
            return "";
        }
        if (!ApiUtils.isPhoneNumberValid(mUsername)) {
            ApiUtils.showToast(this, getResources().getString(R.string.user_phone_valid));
            return "";
        }
        return mUsername;
    }

    public String getUserPwd() {
        String mPwd = mPwdEt.getText().toString().trim();
        if ("".equals(mPwd) || mPwd.isEmpty()) {
            ApiUtils.showToast(this, getResources().getString(R.string.input_pwd));
            return "";
        }
        return ApiUtils.MD5(mPwd);
    }

    @Override
    protected void onDestroy() {
        //p 与 v 断开连接
        presenter.detachView();
        super.onDestroy();
    }
}
