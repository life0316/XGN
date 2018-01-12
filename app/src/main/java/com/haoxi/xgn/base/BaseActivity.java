package com.haoxi.xgn.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.haoxi.xgn.R;
import com.haoxi.xgn.utils.ActivityFragmentInject;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    private int mToolbarTitle;
    private int mToolbarIndicator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int mContentViewId;
        int mMenuId;
        if (getClass().isAnnotationPresent(ActivityFragmentInject.class)){
            ActivityFragmentInject annotation = getClass().getAnnotation(ActivityFragmentInject.class);
            mContentViewId = annotation.contentViewId();
            mMenuId = annotation.menuId();
            mToolbarTitle = annotation.toolbarTitle();
            mToolbarIndicator = annotation.toolbarIndicator();
        }else {
            throw new RuntimeException("类没有进行注解异常");
        }
        setContentView(mContentViewId);
        ButterKnife.bind(this);
        init();
        if (mMenuId != -1) {
            initToolbar();
        }
    }

    private void initToolbar() {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        TextView mTitle=findViewById(R.id.custom_toolbar_tv);
        if (mToolbar != null) {
            mToolbar.setContentInsetStartWithNavigation(0);
            setSupportActionBar(mToolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
            if (mToolbarTitle != -1) {
//                getSupportActionBar().setTitle(mToolbarTitle);
                getSupportActionBar().setTitle("");
                mTitle.setText(mToolbarTitle);
            }else {
                getSupportActionBar().setTitle("");
            }
            if (mToolbarIndicator != -1) {
                getSupportActionBar().setHomeAsUpIndicator(mToolbarIndicator);
            }else {
//                getSupportActionBar().setHomeAsUpIndicator(R.mipmap.btn_back_normal);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    protected abstract void init();

}
