package com.android.mb.schedule.entitys;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class MultipleItem implements MultiItemEntity {
    public static final int DAY = 1;
    public static final int AM = 2;//上午
    public static final int PM = 3;//下午
    private int itemType;
    public MultipleItem(int itemType) {
        this.itemType = itemType;
    }


    @Override
    public int getItemType() {
        return itemType;
    }
}
