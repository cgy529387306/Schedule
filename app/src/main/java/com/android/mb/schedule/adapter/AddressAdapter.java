package com.android.mb.schedule.adapter;

import android.view.View;

import com.android.mb.schedule.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


/**
 * Created by chenqm on 2017/6/7.
 */

public class AddressAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    private OnMyItemClickListener mOnMyItemClickListener;
    public interface OnMyItemClickListener {
        void onItemClick(String item);
    }

    public void setOnMyItemClickListener(OnMyItemClickListener onMyItemClickListener) {
        mOnMyItemClickListener = onMyItemClickListener;
    }

    public AddressAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final String item) {
        helper.setText(R.id.tv_address,item);
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper.setVisible(R.id.iv_checked,true);
                helper.setTextColor(R.id.tv_address,mContext.getResources().getColor(R.color.base_blue));
                if (mOnMyItemClickListener!=null){
                    mOnMyItemClickListener.onItemClick(item);
                }
            }
        });
    }
}




