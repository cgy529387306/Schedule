package com.android.mb.schedule.adapter;

import com.android.mb.schedule.R;
import com.android.mb.schedule.entitys.RingBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


/**
 * Created by necer on 2017/6/7.
 */

public class RingAdapter extends BaseQuickAdapter<RingBean, BaseViewHolder> {

    private int mIndex;

    public void setCurrentIndex(int index) {
        this.mIndex = index;
        notifyDataSetChanged();
    }

    public RingAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, RingBean item) {
        helper.setText(R.id.tv_ring_name,item.getName());
        helper.setTextColor(R.id.tv_ring_name,helper.getAdapterPosition()==mIndex?mContext.getResources().getColor(R.color.base_blue):mContext.getResources().getColor(R.color.text_color));
        helper.setVisible(R.id.iv_ring_choice,helper.getAdapterPosition()==mIndex);
    }


    public int getIndex() {
        return mIndex;
    }
}




