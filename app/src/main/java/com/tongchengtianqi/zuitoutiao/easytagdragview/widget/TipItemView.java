package com.tongchengtianqi.zuitoutiao.easytagdragview.widget;

import android.content.ClipData;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tongchengtianqi.zuitoutiao.R;
import com.tongchengtianqi.zuitoutiao.easytagdragview.adapter.AbsTipAdapter;
import com.tongchengtianqi.zuitoutiao.easytagdragview.bean.SimpleTitleTip;
import com.tongchengtianqi.zuitoutiao.easytagdragview.bean.Tip;

public class TipItemView extends RelativeLayout {
    private final static String TAG = TipItemView.class.getSimpleName();
    private static final ClipData EMPTY_CLIP_DATA = ClipData.newPlainText("", "");
    protected OnSelectedListener mListener;
    protected OnDeleteClickListener mDeleteListener;
    private Tip mIDragEntity;
    private TextView title;
    private ImageView delete;
    private int position;

    public TipItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setOnClickListener(createClickListener());
        title =(TextView)findViewById(R.id.tagview_title);
        delete =(ImageView)findViewById(R.id.tag_view_delete);
       /* delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDeleteListener != null) {
                    mDeleteListener.onDeleteClick(mIDragEntity, position, TipItemView.this);
                }

            }
        });*/
    }

    protected OnClickListener createClickListener() {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(delete.isShown()){
                    //编辑模式下删除item
                    if (mDeleteListener != null) {
                        mDeleteListener.onDeleteClick(mIDragEntity, position, TipItemView.this);
                    }
                    return ;
                }
                //非编辑模式下回调点击item事件
                if (mListener != null) {
                    mListener.onTileSelected(mIDragEntity,position, TipItemView.this);
                }
            }
        };
    }
    //获取拖拽的实体
    public Tip getDragEntity() {
        return mIDragEntity;
    }
    /*提出数据*/
    public void renderData(Tip entity) {
        mIDragEntity = entity;

        if (entity != null && entity != AbsTipAdapter.BLANK_ENTRY) {

            if(entity instanceof SimpleTitleTip) {
                title.setText(((SimpleTitleTip) mIDragEntity).getTip());

            }
            setVisibility(View.VISIBLE);
        } else {
            setVisibility(View.INVISIBLE);
        }
    }
   /* public void setDragDropListener(){
        setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                // NOTE The drag shadow is handled in the ListView.
                v.startDrag(EMPTY_CLIP_DATA, new View.DragShadowBuilder(),
                        DragDropGirdView.DRAG_FAVORITE_TILE, 0);
                return true;
            }
        });
    }*/
    public void setItemListener(int position, OnSelectedListener listener) {
        mListener = listener;
        this.position =position;
    }

    public void setDeleteClickListener(int position, OnDeleteClickListener listener){
        this.position =position;
        this.mDeleteListener =listener;
    }



    /*选择栏目的监听*/
    public interface OnSelectedListener {
        /**
         * Notification that the tile was selected; no specific action is dictated.
         * 通知选择了Tile，没有指定具体的操作
         */
        void onTileSelected(Tip entity, int position, View view);

    }
    /*删除栏目的监听*/
    public interface OnDeleteClickListener{
        void onDeleteClick(Tip entity, int position, View view);
    }
    /*展示删除的栏目*/
    public void showDeleteImg(){
        delete.setVisibility(View.VISIBLE);
    }
    /*隐藏删除的栏目*/
    public void hideDeleteImg(){
        delete.setVisibility(View.GONE);
    }
}
