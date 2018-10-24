package com.android.mb.schedule.adapter;

import android.widget.ImageView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.entitys.PeopleSection;
import com.android.mb.schedule.entitys.UserBean;
import com.android.mb.schedule.utils.ImageUtils;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class SectionPeopleAdapter extends BaseSectionQuickAdapter<PeopleSection, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param sectionHeadResId The section head layout id for each item
     * @param layoutResId      The layout resource id of each item.
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public SectionPeopleAdapter(int layoutResId, int sectionHeadResId, List data) {
        super(layoutResId, sectionHeadResId, data);
    }

    //section 头部
    @Override
    protected void convertHead(BaseViewHolder helper, final PeopleSection item) {
        helper.setText(R.id.tvHeader, item.header);
    }


    //item 项
    @Override
    protected void convert(BaseViewHolder helper, PeopleSection item) {
        UserBean userBean = item.t;
        helper.setText(R.id.tv_name,userBean.getNickname());
        ImageUtils.displayAvatar(mContext,userBean.getAvatar(), (ImageView) helper.getView(R.id.iv_avatar));
        helper.setImageResource(R.id.iv_check,userBean.isSelect()?R.mipmap.ic_item_checked:R.mipmap.ic_item_check);
    }
}
