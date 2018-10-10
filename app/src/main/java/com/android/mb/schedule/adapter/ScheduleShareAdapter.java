package com.android.mb.schedule.adapter;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.entitys.ShareBean;
import com.android.mb.schedule.entitys.UserBean;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.ProjectHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class ScheduleShareAdapter extends BaseQuickAdapter<ShareBean, BaseViewHolder> {
    public ScheduleShareAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShareBean item) {
        TextView tvTitle = helper.getView(R.id.tv_title);
        Drawable drawableLeft = item.getImportant()==1?mContext.getResources().getDrawable(
                R.mipmap.ic_warn):mContext.getResources().getDrawable(R.drawable.shape_circle_dot_blue);
        tvTitle.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                null, null, null);
        tvTitle.setText(item.getTitle());
        helper.setText(R.id.tv_date, Helper.date2String(Helper.string2Date(item.getCreate_date()),"MM-dd"));
        helper.setText(R.id.tv_address,item.getAddress());
        helper.setText(R.id.tv_time,item.getTime());
        if (Helper.isEmpty(item.getShare())){
            helper.setGone(R.id.tv_share,true);
        }else{
            helper.setGone(R.id.tv_share,false);
            helper.setText(R.id.tv_share,String.format(mContext.getString(R.string.share_person), ProjectHelper.getSharePersonStr(item.getShare()),item.getShare().size()));
        }

    }

}