package com.android.mb.schedule.adapter;

import android.content.Context;

import com.android.mb.schedule.R;
import com.android.mb.schedule.entitys.MultipleItem;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 * modify by AllenCoder
 */
public class MultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> {

    public MultipleItemQuickAdapter(Context context, List data) {
        super(data);
        addItemType(MultipleItem.DAY, R.layout.item_schedule_day);
        addItemType(MultipleItem.AM, R.layout.item_schedule_day_half);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {
        switch (helper.getItemViewType()) {
            case MultipleItem.DAY:
//                helper.setText(R.id.tv, item.getContent());
                break;
            case MultipleItem.AM:
//                helper.setText(R.id.tv, item.getContent());
                break;
        }
    }

}
