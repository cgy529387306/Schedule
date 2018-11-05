package com.android.mb.schedule.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class TagAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public TagAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
//        helper.setText(R.id.tv_name,item.getNickname());
//        ImageUtils.displayAvatar(mContext,item.getAvatar(), (ImageView) helper.getView(R.id.iv_avatar));
//        helper.setImageResource(R.id.iv_check,item.isSelect()?R.mipmap.ic_item_checked:R.mipmap.ic_item_check);
    }

}