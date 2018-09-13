package com.android.mb.schedule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.mb.schedule.R;
import com.android.mb.schedule.entitys.RingBean;
import com.android.mb.schedule.utils.ToastHelper;
import com.android.mb.schedule.view.interfaces.OnItemClickListener;

import java.util.List;


/**
 * Created by necer on 2017/6/7.
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyViewHolder> {

    private Context context;
    private List<String> mList;
    private OnItemClickListener mListener;
    private int index;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public AddressAdapter(Context context, List<String> mList) {
        this.context = context;
        this.mList = mList;
    }
    public void setCurrentIndex(int index) {
        this.index = index;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_address, parent,false);
        return new MyViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.mTvAddress.setText(mList.get(position));
        if (index == position){
            holder.mIvDelete.setImageResource(R.mipmap.icon_checked);
        }else {
            holder.mIvDelete.setImageResource(R.mipmap.icon_delete);
        }
        holder.mIvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastHelper.showLongToast("position"+ position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTvAddress;
        ImageView mIvDelete;
        public MyViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            mListener = onItemClickListener;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(view,getPosition());
                }
            });
            mTvAddress =itemView.findViewById(R.id.tv_address);
            mIvDelete = itemView.findViewById(R.id.iv_delete);
        }
    }
}




