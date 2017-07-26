package com.tongchengtianqi.zuitoutiao.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.tongchengtianqi.zuitoutiao.R;
import com.tongchengtianqi.zuitoutiao.base.BaseActivity;


public class HotpageActivity extends BaseActivity {

    private LinearLayout mHotPageBack;
    private View mHotpageRecyclerview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotpage);
        initTitleView();
        initView();
        initListener();
    }

    @Override
    protected void initView() {
        mHotPageBack = (LinearLayout) findViewById(R.id.hot_page_back);
        mHotpageRecyclerview = findViewById(R.id.hotpage_recyclerview);
    }

    @Override
    protected void initListener() {
        mHotPageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {


    }
}
