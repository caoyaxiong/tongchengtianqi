package com.tongchengtianqi.zuitoutiao.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class BaseFragment extends Fragment {

    protected Activity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getContentViewLayoutID() != 0) {
            View view = inflater.inflate(getContentViewLayoutID(), null);
            initView(view, savedInstanceState);
            initListener();
            return view;
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    /**
     * 初始化fragment
     */
    protected abstract int getContentViewLayoutID();

    /**
     * 初始化控件
     */
    protected abstract void initView(View view, Bundle savedInstanceState);

    /**
     * 初始化监听器的代码写在这个方法中
     */
    protected void initListener() {
    }

    /**
     * 初始化数据的代码写在这个方法中
     */
    public void initData() {
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }
}
