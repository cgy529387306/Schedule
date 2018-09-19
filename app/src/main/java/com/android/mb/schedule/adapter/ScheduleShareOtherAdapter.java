package com.android.mb.schedule.adapter;

import android.widget.ImageView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.entitys.RelatedBean;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.ImageUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class ScheduleShareOtherAdapter extends BaseQuickAdapter<RelatedBean, BaseViewHolder> {

    public ScheduleShareOtherAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RelatedBean item) {
        ImageUtils.displayAvatar(mContext,item.getUser_avatar(), (ImageView) helper.getView(R.id.iv_head));
        helper.setText(R.id.tv_date, Helper.date2String(Helper.string2Date(item.getCreate_date()),"MM-dd"));
        helper.setText(R.id.tv_name,item.getUser_name());
        helper.setText(R.id.tv_department,item.getUser_office());
        helper.setText(R.id.tv_title,item.getTitle());
        helper.setText(R.id.tv_address,item.getAddress());
        helper.setText(R.id.tv_time,item.getTime());
    }

}