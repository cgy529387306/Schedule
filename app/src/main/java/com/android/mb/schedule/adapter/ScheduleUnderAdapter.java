package com.android.mb.schedule.adapter;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.entitys.RelatedBean;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.ImageUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class ScheduleUnderAdapter extends BaseQuickAdapter<RelatedBean, BaseViewHolder> {

    public ScheduleUnderAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RelatedBean item) {
        TextView tvTitle = helper.getView(R.id.tv_title);
        Drawable drawableLeft = item.getImportant()==1?mContext.getResources().getDrawable(
                R.mipmap.ic_warn):mContext.getResources().getDrawable(R.drawable.shape_circle_dot_blue);
        tvTitle.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                null, null, null);
        tvTitle.setText(item.getTitle());


        ImageUtils.displayAvatar(mContext,item.getUser_avatar(), (ImageView) helper.getView(R.id.iv_head));
        helper.setText(R.id.tv_date, Helper.date2String(Helper.string2Date(item.getCreate_date()),"MM-dd"));
        helper.setText(R.id.tv_name,item.getUser_name());
        helper.setText(R.id.tv_department,item.getUser_office());
        helper.setText(R.id.tv_address,item.getAddress());
        helper.setText(R.id.tv_time,item.getTime());
    }

}