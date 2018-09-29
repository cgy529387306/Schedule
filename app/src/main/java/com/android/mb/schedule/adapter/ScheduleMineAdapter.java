package com.android.mb.schedule.adapter;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.entitys.MyScheduleBean;
import com.android.mb.schedule.entitys.RelatedBean;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.ImageUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.Date;
import java.util.List;

public class ScheduleMineAdapter extends BaseQuickAdapter<MyScheduleBean, BaseViewHolder> {
    public static final String mDateFormat = "yyyy年MM月dd日";
    public ScheduleMineAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyScheduleBean item) {
        TextView tvDate = helper.getView(R.id.tv_date);
        tvDate.setVisibility(isSameWithLastDate(item,helper.getLayoutPosition())? View.GONE:View.VISIBLE);
        tvDate.setText(getDateFormat(item.getDate()));
        TextView tvTitle = helper.getView(R.id.tv_title);
        Drawable drawableLeft = item.getImportant()==1?mContext.getResources().getDrawable(
                R.mipmap.ic_warn):mContext.getResources().getDrawable(R.drawable.shape_circle_dot_blue);
        tvTitle.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                null, null, null);
        tvTitle.setText(item.getTitle());
        if (item.getAllDay()==1){
            helper.setTextColor(R.id.tv_time,mContext.getResources().getColor(R.color.base_orange));
            helper.setText(R.id.tv_time,"全天");
        }else{
            helper.setTextColor(R.id.tv_time,mContext.getResources().getColor(R.color.gray_a5));
            helper.setText(R.id.tv_time,item.getStartTime()+"-"+item.getEndTime());
        }
        helper.setText(R.id.tv_address, item.getAddress());
    }

    private boolean isSameWithLastDate(MyScheduleBean item,int position){
        boolean isSame = false;
        try {
            if (Helper.isNotEmpty(getData()) && position>0){
                MyScheduleBean lastSchedule = getData().get(position-1);
                isSame = item.getDate().equals(lastSchedule.getDate());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return isSame;
    }

    private String getDateFormat(String date){
       Date date1 = Helper.string2Date(date,"yyyy-MM-dd");
       String dateStr =  Helper.date2String(date1,mDateFormat);
       String weekStr = Helper.date2String(date1,"EEEE");
       return dateStr+"  " +weekStr;
    }



}