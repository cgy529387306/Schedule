package com.android.mb.schedule.adapter;

import android.view.View;
import android.widget.ImageView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.entitys.UserBean;
import com.android.mb.schedule.utils.ImageUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class SelectAdapter extends BaseQuickAdapter<UserBean, BaseViewHolder> {

    public SelectAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserBean item) {
        helper.setText(R.id.tv_name,item.getNickname());
        ImageUtils.displayAvatar(mContext,item.getAvatar(), (ImageView) helper.getView(R.id.iv_avatar));
        helper.setImageResource(R.id.iv_check,R.mipmap.ic_detail_delete);
        helper.getView(R.id.iv_check).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}