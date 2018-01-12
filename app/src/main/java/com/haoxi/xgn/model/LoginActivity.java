package com.haoxi.xgn.model;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.haoxi.xgn.MainActivity;
import com.haoxi.xgn.R;
import com.haoxi.xgn.base.BaseActivity;
import com.haoxi.xgn.utils.ActivityFragmentInject;

import butterknife.BindView;

@ActivityFragmentInject(contentViewId = R.layout.activity_login)
public class LoginActivity extends BaseActivity implements View.OnClickListener {

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

    @Override
    protected void init() {
        mLoginBtn.setOnClickListener(this);
        mForgetTv.setOnClickListener(this);
        mRegistTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Intent intent = null;

        switch (view.getId()){
            case R.id.login_btn:
                intent = new Intent(LoginActivity.this,MainActivity.class);
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
}
