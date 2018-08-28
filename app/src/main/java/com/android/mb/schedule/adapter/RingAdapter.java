package com.android.mb.schedule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.entitys.RingBean;
import com.android.mb.schedule.view.interfaces.OnItemClickListener;

import java.util.List;


/**
 * Created by necer on 2017/6/7.
 */

public class RingAdapter extends RecyclerView.Adapter<RingAdapter.MyViewHolder> {

    private Context context;
    private List<RingBean> mList;
    private OnItemClickListener mListener;
    private int index;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public RingAdapter(Context context, List<RingBean> mList) {
        this.context = context;
        this.mList = mList;
    }
    public void setCurrentIndex(int index) {
        this.index = index;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ring, parent,false);
        return new MyViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.mTvRingName.setText(mList.get(position).getName());
        if (position == 0){
            holder.mIvRingChoice.setVisibility(View.VISIBLE);
        }
        if (index == position){
            holder.mIvRingChoice.setVisibility(View.VISIBLE);
        }else {
            holder.mIvRingChoice.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mLlyRing;
        TextView mTvRingName;
        ImageView mIvRingChoice;
        public MyViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            mListener = onItemClickListener;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(view,getPosition());
                }
            });
            mLlyRing = itemView.findViewById(R.id.lly_ring);
            mTvRingName =itemView.findViewById(R.id.tv_ringname);
            mIvRingChoice = itemView.findViewById(R.id.iv_ringchoice);
        }
    }
}




