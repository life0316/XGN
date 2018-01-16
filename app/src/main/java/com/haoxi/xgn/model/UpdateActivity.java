package com.haoxi.xgn.model;

import android.view.View;
import android.widget.TextView;

import com.haoxi.xgn.R;
import com.haoxi.xgn.base.BaseActivity;
import com.haoxi.xgn.utils.ActivityFragmentInject;

import butterknife.BindView;

@ActivityFragmentInject(contentViewId = R.layout.activity_update_pwd,menuId = 1,toolbarTitle = R.string.update_pwd)
public  class UpdateActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.custom_toolbar_right)
    TextView mRightTv;
    @Override
    protected void init() {
        mRightTv.setVisibility(View.VISIBLE);
        mRightTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.custom_toolbar_right:
                break;
        }
    }
}
