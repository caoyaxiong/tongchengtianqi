package com.tongchengtianqi.zuitoutiao.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;


public class CustomViewPager extends ViewPager{
    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item,false);
    }

}
