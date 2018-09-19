package com.android.mb.schedule.adapter;

import android.widget.ImageView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.entitys.UserBean;
import com.android.mb.schedule.utils.ImageUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class PersonAdapter extends BaseQuickAdapter<UserBean, BaseViewHolder> {

    public PersonAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserBean item) {
        helper.setText(R.id.tv_name,item.getNickname());
        ImageUtils.displayAvatar(mContext,item.getAvatar(), (ImageView) helper.getView(R.id.iv_avatar));
    }

}