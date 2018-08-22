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

import java.util.List;


/**
 * Created by necer on 2017/6/7.
 */

public class RingAdapter extends RecyclerView.Adapter<RingAdapter.MyViewHolder> {

    private Context context;
    private List<RingBean> mList;

    public RingAdapter(Context context,List<RingBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ring, parent,false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.mTvRingName.setText(mList.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mIvRingChoice.setImageResource(R.mipmap.icon_back);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mLlyRing;
        TextView mTvRingName;
        ImageView mIvRingChoice;
        public MyViewHolder(View itemView) {
            super(itemView);
            mLlyRing = itemView.findViewById(R.id.lly_ring);
            mTvRingName =itemView.findViewById(R.id.tv_ringname);
            mIvRingChoice = itemView.findViewById(R.id.iv_ringchoice);
        }
    }
}




