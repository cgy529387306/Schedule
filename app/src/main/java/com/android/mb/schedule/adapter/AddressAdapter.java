package com.android.mb.schedule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.view.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by chenqm on 2017/6/7.
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyViewHolder> {

    private Context mContext;
    private List<String> mDataList = new ArrayList<>();
    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public List<String> getDataList() {
        if (mDataList == null) {
            return new ArrayList<>();
        }
        return mDataList;
    }

    public AddressAdapter(Context context) {
        this.mContext = context;
    }
    public void setDataList(List<String> list){
        if (list!=null){
            mDataList = list;
            notifyDataSetChanged();
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_address, parent,false);
        return new MyViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.mTvAddress.setText(mDataList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.mIvChecked.setVisibility(View.VISIBLE);
                holder.mTvAddress.setTextColor(mContext.getResources().getColor(R.color.base_blue));
                mListener.onItemClick(view,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList==null?0:mDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTvAddress;
        ImageView mIvChecked;
        public MyViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            mListener = onItemClickListener;
            mTvAddress =itemView.findViewById(R.id.tv_address);
            mIvChecked = itemView.findViewById(R.id.iv_checked);
        }
    }
}




