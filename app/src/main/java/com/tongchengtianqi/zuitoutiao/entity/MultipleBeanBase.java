package com.tongchengtianqi.zuitoutiao.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;


public class MultipleBeanBase implements MultiItemEntity {
    int ItemType;
    Object object;

    public MultipleBeanBase(int itemType, Object object) {
        this.ItemType = itemType;
        this.object=object;
    }
    @Override
    public int getItemType() {
        return ItemType;
    }

    public Object getObject() {
        return object;
    }
}
