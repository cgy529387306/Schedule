package com.android.mb.schedule.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.mb.schedule.R;
import com.android.mb.schedule.entitys.ReportAdminBean;
import com.android.mb.schedule.entitys.ReportData;
import com.android.mb.schedule.entitys.ReportNormalBean;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.ImageUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class WeekReportAdapter extends BaseQuickAdapter<ReportData, BaseViewHolder> {
    public static final String mTimeFormat = "MM-dd";
    public static final String mDateFormat = "MM.dd";
    public WeekReportAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReportData item) {
        LinearLayout llyReportAdmin = helper.getView(R.id.lly_report_admin);
        if (item.getIs_admin()==1){
            llyReportAdmin.setVisibility(View.VISIBLE);
            ReportAdminBean reportAdminBean = item.getAdmin();
            if (reportAdminBean!=null){
                String dateStr = Helper.long2DateString(reportAdminBean.getEnd_time_stamp()*1000,mTimeFormat);
                String timeRange = "企业使用小结 "+Helper.long2DateString(reportAdminBean.getEnd_time_stamp()*1000,mDateFormat)
                        +"-"+Helper.long2DateString(reportAdminBean.getEnd_time_stamp()*1000,mDateFormat);

                String useCount = "使用人数"+reportAdminBean.getUse_num()+"人";
                String unUseCount = "未使用人数"+reportAdminBean.getUn_use_num()+"人";
                String useRatio = "使用率"+reportAdminBean.getUse_pre()+" 环比上一周有"+reportAdminBean.getRing_ratio();
                helper.setText(R.id.tv_date_admin,dateStr);
                helper.setText(R.id.tv_time_range_admin,timeRange);
                helper.setText(R.id.tv_use_people,useCount);
                helper.setText(R.id.tv_unUse_people,unUseCount);
                helper.setText(R.id.tv_use_ratio,useRatio);
            }
        }else{
            llyReportAdmin.setVisibility(View.GONE);
        }
        ReportNormalBean normalBean = item.getNormal();
        if (normalBean!=null){
            String dateStr = Helper.long2DateString(normalBean.getUsed_time()*1000,mTimeFormat);
            String startTime = Helper.date2String(Helper.string2Date(normalBean.getS_time(),"yyyy-MM-dd"),mDateFormat);
            String endTime = Helper.date2String(Helper.string2Date(normalBean.getE_time(),"yyyy-MM-dd"),mDateFormat);
            String timeRange = "一周小结 "+startTime +"-"+endTime;
            String useNum = "处理工作会话  "+normalBean.getUsed_num()+"次";
            String useTime = "共花"+normalBean.getUsed_time()+"分钟";

            helper.setText(R.id.tv_date,dateStr);
            helper.setText(R.id.tv_time_range,timeRange);
            helper.setText(R.id.tv_use_num,useNum);
            helper.setText(R.id.tv_use_time,useTime);
            ImageUtils.displayAvatar(mContext,normalBean.getNickname(), (ImageView) helper.getView(R.id.iv_avatar));

        }
    }

}