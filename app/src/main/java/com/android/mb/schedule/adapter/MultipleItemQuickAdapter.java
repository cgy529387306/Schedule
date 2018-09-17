package com.android.mb.schedule.adapter;

import android.content.Context;

import com.android.mb.schedule.R;
import com.android.mb.schedule.entitys.ScheduleBean;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 * modify by AllenCoder
 */
public class MultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<ScheduleBean, BaseViewHolder> {

    public MultipleItemQuickAdapter(Context context, List<ScheduleBean> data) {
        super(data);
        addItemType(1, R.layout.item_schedule_day);
        addItemType(0, R.layout.item_schedule_day_half);
    }


    @Override
    protected void convert(BaseViewHolder helper, ScheduleBean item) {
        switch (helper.getItemViewType()) {
            //半天
            case 0:
                helper.setText(R.id.tv_name, item.getTitle());
                helper.setText(R.id.tv_address, item.getAddress());
                helper.setText(R.id.tv_time,item.getStartTime()+"-"+item.getEndTime());
                break;
            //全天
            case 1:
                helper.setText(R.id.tv_name, item.getTitle());
                helper.setText(R.id.tv_address, item.getAddress());
                break;

        }
    }
}
