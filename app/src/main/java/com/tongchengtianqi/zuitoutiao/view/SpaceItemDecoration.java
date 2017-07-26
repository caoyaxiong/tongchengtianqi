package com.tongchengtianqi.zuitoutiao.view;


import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
//设置recycler view的条目分割线
public class SpaceItemDecoration extends RecyclerView.ItemDecoration{
    private int mSpace;
    public SpaceItemDecoration(int space) {
        this.mSpace = space;
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = mSpace;
        outRect.right = mSpace;
    }
}
