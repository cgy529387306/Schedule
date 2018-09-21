package com.android.mb.schedule.entitys;


import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class ScheduleSection extends SectionEntity<ScheduleBean> {
    private boolean isMore;
    public ScheduleSection(boolean isHeader, String header) {
        super(isHeader, header);
        this.isMore = isMore;
    }

    public ScheduleSection(ScheduleBean t) {
        super(t);
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean mroe) {
        isMore = mroe;
    }
}
