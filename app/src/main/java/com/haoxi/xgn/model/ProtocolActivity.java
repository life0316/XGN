package com.haoxi.xgn.model;

import android.webkit.WebView;

import com.haoxi.xgn.R;
import com.haoxi.xgn.base.BaseActivity;
import com.haoxi.xgn.utils.ActivityFragmentInject;

import butterknife.BindView;


@ActivityFragmentInject(contentViewId = R.layout.layout_webview,menuId = 1,toolbarTitle = R.string.about)
public class ProtocolActivity extends BaseActivity {

    @BindView(R.id.webview)
    WebView mWebView;
    @Override
    protected void init() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("file:///android_asset/agreement.html");
    }
}
