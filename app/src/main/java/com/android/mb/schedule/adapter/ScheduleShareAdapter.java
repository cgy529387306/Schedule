package com.android.mb.schedule.adapter;

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
        helper.setText(R.id.tv_date, Helper.date2String(Helper.string2Date(item.getCreate_date()),"MM-dd"));
        helper.setText(R.id.tv_title,item.getTitle());
        helper.setText(R.id.tv_address,item.getAddress());
        helper.setText(R.id.tv_time,item.getTime());
        if (Helper.isEmpty(item.getShare())){
            helper.setVisible(R.id.tv_share,false);
        }else{
            helper.setVisible(R.id.tv_share,true);
            helper.setText(R.id.tv_share,String.format(mContext.getString(R.string.share_person), ProjectHelper.getSharePersonStr(item.getShare()),item.getShare().size()));
        }

    }

}