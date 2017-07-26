package com.tongchengtianqi.zuitoutiao.activity;


import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebViewClient;
import com.tongchengtianqi.zuitoutiao.R;
import com.tongchengtianqi.zuitoutiao.base.BaseActivity;
import com.tongchengtianqi.zuitoutiao.http.Urls;

public class DetailPageActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout mDetailPageBack;
    private LinearLayout mDetailPageShare;

    private com.tencent.smtt.sdk.WebView mTencentWebView;

   private String url;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        Intent intent = getIntent();
        url = Urls.DETAIL_PAGES + intent.getStringExtra("url");
        initTitleView();
        initView();
        loadUrl(url);
        initListener();
    }

    private void loadUrl(String url) {
        WebSettings webSettings = mTencentWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mTencentWebView.loadUrl(url);
        mTencentWebView.setWebViewClient(new WebViewClient());

    }

    @Override
    protected void initView() {
        mDetailPageBack = (LinearLayout) findViewById(R.id.detail_page_back);
        mDetailPageShare = (LinearLayout) findViewById(R.id.detail_page_share);
        mTencentWebView = (com.tencent.smtt.sdk.WebView)
                findViewById(R.id.tencent_web_view);
    }

    @Override
    protected void initListener() {
        mDetailPageBack.setOnClickListener(this);
        mDetailPageShare.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.detail_page_back:
                finish();
                break;
            case R.id.detail_page_share:
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTencentWebView != null) {
            mTencentWebView.destroy();
        }
    }
}
