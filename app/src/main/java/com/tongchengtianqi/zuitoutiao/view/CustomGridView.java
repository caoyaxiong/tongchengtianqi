package com.tongchengtianqi.zuitoutiao.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;


public class CustomGridView extends GridView{
    public CustomGridView(Context context) {
        super(context);
    }

    public CustomGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**
         * Integer.MAX_VALUE >> 2的含义是Int类型的最大值的二进制向右移动2位的值
         *
         */
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
