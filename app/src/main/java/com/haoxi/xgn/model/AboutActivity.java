package com.haoxi.xgn.model;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.haoxi.xgn.R;
import com.haoxi.xgn.base.BaseActivity;
import com.haoxi.xgn.utils.ActivityFragmentInject;
import butterknife.BindView;


@ActivityFragmentInject(contentViewId = R.layout.activity_about,menuId = 1,toolbarTitle = R.string.about_xgn)
public class AboutActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.service_support)
    TextView mSupportTv;
    @BindView(R.id.company_profile)
    TextView mProfileTv;


    @Override
    protected void init() {
        mSupportTv.setOnClickListener(this);
        mProfileTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.service_support:
                intent = new Intent(this, SupportActivity.class);
                break;
            case R.id.company_profile:
                intent = new Intent(this, ComProfileActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
