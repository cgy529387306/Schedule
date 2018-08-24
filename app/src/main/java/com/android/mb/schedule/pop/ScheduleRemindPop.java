package com.android.mb.schedule.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.mb.schedule.R;

/**
 * Created by chenqm
 */
public class ScheduleRemindPop extends PopupWindow implements View.OnClickListener {
    private View conentView;
    private Activity mContext;
    private TextView mBtnChoose;
    private LinearLayout mLlyBeforeFive; //5分钟前
    private TextView mTvBeforeFive;
    private ImageView mIvBeforeFive;
    private LinearLayout mLlyBeforeTen; //10分钟前
    private TextView mTvBeforeTen;
    private ImageView mIvBeforeTen;
    private LinearLayout mLlyBeforeOneHour; //1小时前
    private TextView mTvBeforeOneHour;
    private ImageView mIvBeforeOneHour;
    private LinearLayout mLlyBeforeTwoHour; //2小时前
    private TextView mTvBeforeTwoHour;
    private ImageView mIvBeforeTwoHour;
    private LinearLayout mLlyBeforeOneDay; //24小时前
    private TextView mTvBeforeOneDay;
    private ImageView mIvBeforeOneDay;
    private LinearLayout mLlyNoRemind; //不在提醒
    private TextView mTvNoRemind;
    private ImageView mIvNoRemind;
    private SelectListener mSelectListener;
    private int type = 0;

    public void setSelectListener(SelectListener selectListener) {
        this.mSelectListener = selectListener;
    }

    public interface SelectListener {
        void onSelected(int type);
    }

    public ScheduleRemindPop(Activity context, SelectListener selectListener) {
        mContext = context;
        this.mSelectListener = selectListener;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.pop_scheduleremind, null);
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        setFocusable(false);
        setOutsideTouchable(false);
        setWidth(ViewPager.LayoutParams.MATCH_PARENT);
        setHeight(ViewPager.LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        initView();
        initListener();
    }

    private void initView() {
        mBtnChoose = conentView.findViewById(R.id.tv_choose);
        mLlyBeforeFive = conentView.findViewById(R.id.lly_beforefive);
        mTvBeforeFive = conentView.findViewById(R.id.tv_beforefive);
        mIvBeforeFive = conentView.findViewById(R.id.iv_beforefive);
        mLlyBeforeTen = conentView.findViewById(R.id.lly_beforeten);
        mTvBeforeTen = conentView.findViewById(R.id.tv_beforeten);
        mIvBeforeTen = conentView.findViewById(R.id.iv_beforeten);
        mLlyBeforeOneHour = conentView.findViewById(R.id.lly_beforeonehour);
        mTvBeforeOneHour = conentView.findViewById(R.id.tv_beforeonehour);
        mIvBeforeOneHour = conentView.findViewById(R.id.iv_beforeonehour);
        mLlyBeforeTwoHour = conentView.findViewById(R.id.lly_beforetwohour);
        mTvBeforeTwoHour = conentView.findViewById(R.id.tv_beforetwohour);
        mIvBeforeTwoHour = conentView.findViewById(R.id.iv_beforetwohour);
        mLlyBeforeOneDay = conentView.findViewById(R.id.lly_beforeoneday);
        mTvBeforeOneDay = conentView.findViewById(R.id.tv_beforeoneday);
        mIvBeforeOneDay = conentView.findViewById(R.id.iv_beforeoneday);
        mLlyNoRemind = conentView.findViewById(R.id.lly_noremind);
        mTvNoRemind = conentView.findViewById(R.id.tv_noremind);
        mIvNoRemind = conentView.findViewById(R.id.iv_noremind);
        refresh(0);
    }

    private void initListener() {
        mBtnChoose.setOnClickListener(this);
        mLlyBeforeFive.setOnClickListener(this);
        mLlyBeforeTen.setOnClickListener(this);
        mLlyBeforeOneHour.setOnClickListener(this);
        mLlyBeforeTwoHour.setOnClickListener(this);
        mLlyBeforeOneDay.setOnClickListener(this);
        mLlyNoRemind.setOnClickListener(this);
    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAtLocation(parent, Gravity.BOTTOM | Gravity.LEFT, 0, 0);
        } else {
            this.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_choose){
            mSelectListener.onSelected(type);
            dismiss();
        }else if (id == R.id.lly_beforefive){
            type = 0;
            refresh(type);
        }else if (id == R.id.lly_beforeten){
            type = 1;
            refresh(type);
        }else if (id == R.id.lly_beforeonehour){
            type = 2;
            refresh(type);
        }else if (id == R.id.lly_beforetwohour){
            type = 3;
            refresh(type);
        }else if (id == R.id.lly_beforeoneday){
            type = 4;
            refresh(type);
        }else if (id == R.id.lly_noremind){
            type = 5;
            refresh(type);
        }
    }
    private void refresh(int type){
        mTvBeforeFive.setTextColor(type == 0?ContextCompat.getColor(mContext, R.color.base_blue):ContextCompat.getColor(mContext, R.color.black));
        mTvBeforeTen.setTextColor(type == 1?ContextCompat.getColor(mContext, R.color.base_blue):ContextCompat.getColor(mContext, R.color.black));
        mTvBeforeOneHour.setTextColor(type == 2?ContextCompat.getColor(mContext, R.color.base_blue):ContextCompat.getColor(mContext, R.color.black));
        mTvBeforeTwoHour.setTextColor(type == 3?ContextCompat.getColor(mContext, R.color.base_blue):ContextCompat.getColor(mContext, R.color.black));
        mTvBeforeOneDay.setTextColor(type == 4?ContextCompat.getColor(mContext, R.color.base_blue):ContextCompat.getColor(mContext, R.color.black));
        mTvNoRemind.setTextColor(type == 5?ContextCompat.getColor(mContext, R.color.base_blue):ContextCompat.getColor(mContext, R.color.black));

        mIvBeforeFive.setVisibility(type == 0?View.VISIBLE:View.GONE);
        mIvBeforeTen.setVisibility(type == 1?View.VISIBLE:View.GONE);
        mIvBeforeOneHour.setVisibility(type == 2?View.VISIBLE:View.GONE);
        mIvBeforeTwoHour.setVisibility(type == 3?View.VISIBLE:View.GONE);
        mIvBeforeOneDay.setVisibility(type == 4?View.VISIBLE:View.GONE);
        mIvNoRemind.setVisibility(type == 5?View.VISIBLE:View.GONE);
    }
}
