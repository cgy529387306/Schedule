package com.android.mb.schedule.adapter;

import com.android.mb.schedule.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class ScheduleShareAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ScheduleShareAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_date,item);
    }

}