package com.android.mb.schedule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.entitys.ChooseBean;
import com.android.mb.schedule.entitys.RingBean;
import com.android.mb.schedule.view.interfaces.OnItemClickListener;

import java.util.List;


/**
 * Created by necer on 2017/6/7.
 */

public class ScheduleRepeatAdapter extends RecyclerView.Adapter<ScheduleRepeatAdapter.MyViewHolder> {

    private Context context;
    private List<ChooseBean> mList;

    public OnItemClickListener getClickListener() {
        return mClickListener;
    }

    private OnItemClickListener mClickListener;

    public ScheduleRepeatAdapter(Context context, List<ChooseBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_choose, parent,false),mClickListener);
    }

    public void setClickListener(OnItemClickListener clickListener) {
        mClickListener = clickListener;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.mTvName.setText(mList.get(position).getItemPop());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mTvName;
        ImageView mIvChoose;
        private OnItemClickListener mOnItemClickListener;

        public MyViewHolder(View itemView,OnItemClickListener mOnItemClickListener) {
            super(itemView);
            mOnItemClickListener = mOnItemClickListener;
            itemView.setOnClickListener(this);
            mTvName =itemView.findViewById(R.id.tv_name);
            mIvChoose = itemView.findViewById(R.id.iv_choose);
        }

        @Override
        public void onClick(View view) {
            mOnItemClickListener.onItemClick(view,getPosition());
        }
    }
}




