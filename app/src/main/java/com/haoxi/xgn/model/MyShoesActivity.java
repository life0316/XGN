package com.haoxi.xgn.model;

import android.view.View;
import android.widget.TextView;

import com.haoxi.xgn.R;
import com.haoxi.xgn.base.BaseActivity;
import com.haoxi.xgn.utils.ActivityFragmentInject;

import butterknife.BindView;

@ActivityFragmentInject(contentViewId = R.layout.activity_myshoes,menuId = 1,toolbarTitle = R.string.band_shoes)
public class MyShoesActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.custom_toolbar_right)
    TextView mAddShoesTv;

    @Override
    protected void init() {
        mAddShoesTv.setVisibility(View.VISIBLE);
        mAddShoesTv.setText("添加");
    }

    @Override
    public void onClick(View view) {

    }
}
