package com.android.mb.schedule.adapter;

import com.android.mb.schedule.R;
import com.android.mb.schedule.utils.Helper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class WeekReportAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public WeekReportAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_date, Helper.date2String(Helper.string2Date(item),"MM-dd"));
//        helper.setText(R.id.tv_time_range,"");
////        helper.setText(R.id.tv_use_people,"");
////        helper.setText(R.id.tv_unUse_people,"");
////        helper.setText(R.id.tv_use_ratio,"");
    }

}