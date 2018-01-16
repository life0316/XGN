package com.haoxi.xgn.model;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.haoxi.xgn.MainActivity;
import com.haoxi.xgn.R;
import com.haoxi.xgn.base.BaseActivity;
import com.haoxi.xgn.utils.ActivityFragmentInject;

import butterknife.BindView;

@ActivityFragmentInject(contentViewId = R.layout.activity_regist,menuId = 1,toolbarTitle = R.string.regist)
public class RegistActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.protocol)
    TextView mProtocolTv;

    @Override
    protected void init() {
        mProtocolTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.protocol:
                intent = new Intent(RegistActivity.this,ProtocolActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
