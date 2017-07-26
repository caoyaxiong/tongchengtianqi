package com.tongchengtianqi.zuitoutiao.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zhy.autolayout.AutoLayoutActivity;

import com.tongchengtianqi.zuitoutiao.utils.SystemTintBarUtils;


public abstract class BaseActivity extends AutoLayoutActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /*给系统状态栏进行着色*/
    protected void initTitleView() {
        SystemTintBarUtils.setSystemBarColor(this);
    }

    /*初始化控件*/
    protected abstract void initView();

    /*初始化监听器*/
    protected abstract void initListener();

    /*初始化数据*/
    protected void initData() {

    }

}
