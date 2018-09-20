package com.android.mb.schedule.adapter;

import com.android.mb.schedule.R;
import com.android.mb.schedule.entitys.MySection;
import com.android.mb.schedule.entitys.ScheduleBean;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class SectionAdapter extends BaseSectionQuickAdapter<MySection, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param sectionHeadResId The section head layout id for each item
     * @param layoutResId      The layout resource id of each item.
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public SectionAdapter(int layoutResId, int sectionHeadResId, List data) {
        super(layoutResId, sectionHeadResId, data);
    }

    //section 头部
    @Override
    protected void convertHead(BaseViewHolder helper, final MySection item) {
        helper.setText(R.id.header, item.header);
    }


    //item 项
    @Override
    protected void convert(BaseViewHolder helper, MySection item) {
        ScheduleBean scheduleBean = item.t;
        helper.setText(R.id.tv_name, scheduleBean.getTitle());
        helper.setText(R.id.tv_time, scheduleBean.getEndTime());
        helper.setText(R.id.tv_address, scheduleBean.getAddress());
    }
}
