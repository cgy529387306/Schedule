package com.android.mb.schedule.adapter;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.entitys.ScheduleSection;
import com.android.mb.schedule.entitys.ScheduleBean;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class SectionMyAdapter extends BaseSectionQuickAdapter<ScheduleSection, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param sectionHeadResId The section head layout id for each item
     * @param layoutResId      The layout resource id of each item.
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public SectionMyAdapter(int layoutResId, int sectionHeadResId, List data) {
        super(layoutResId, sectionHeadResId, data);
    }

    //section 头部
    @Override
    protected void convertHead(BaseViewHolder helper, final ScheduleSection item) {
//        helper.itemView.setVisibility("全天".equals(item.header)? View.GONE:View.VISIBLE);
        helper.setText(R.id.tvHeader, item.header);
    }


    //item 项
    @Override
    protected void convert(BaseViewHolder helper, ScheduleSection item) {
        ScheduleBean scheduleBean = item.t;
        TextView tvTitle = helper.getView(R.id.tv_title);
        Drawable drawableLeft = scheduleBean.getImportant()==1?mContext.getResources().getDrawable(
                R.mipmap.ic_warn):mContext.getResources().getDrawable(R.drawable.shape_circle_dot_blue);
        tvTitle.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                null, null, null);
        tvTitle.setText(scheduleBean.getTitle());

        helper.setText(R.id.tv_address, scheduleBean.getAddress());
        if (scheduleBean.getTimeType()==0){
            helper.setTextColor(R.id.tv_time,mContext.getResources().getColor(R.color.base_orange));
            helper.setText(R.id.tv_time,"全天");
        }else{
            helper.setTextColor(R.id.tv_time,mContext.getResources().getColor(R.color.gray_a5));
            helper.setText(R.id.tv_time,scheduleBean.getStartTime()+"-"+scheduleBean.getEndTime());
        }

    }
}
