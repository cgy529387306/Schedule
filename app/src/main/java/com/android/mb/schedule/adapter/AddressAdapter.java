package com.android.mb.schedule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.entitys.UserBean;
import com.android.mb.schedule.utils.ImageUtils;
import com.android.mb.schedule.view.interfaces.OnItemClickListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by chenqm on 2017/6/7.
 */

public class AddressAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public AddressAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_address,item);
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper.setVisible(R.id.iv_checked,true);
                helper.setTextColor(R.id.tv_address,mContext.getResources().getColor(R.color.base_blue));
            }
        });
    }
}




