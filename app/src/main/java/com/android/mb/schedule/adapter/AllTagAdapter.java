package com.android.mb.schedule.adapter;

import com.android.mb.schedule.R;
import com.android.mb.schedule.entitys.TagBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class AllTagAdapter extends BaseQuickAdapter<TagBean, BaseViewHolder> {

    public AllTagAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TagBean item) {
        helper.setText(R.id.tv_tag, item.getName()+"("+item.getMan().size()+")");
    }

}