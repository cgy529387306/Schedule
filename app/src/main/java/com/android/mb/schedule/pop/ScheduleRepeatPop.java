package com.android.mb.schedule.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.mb.schedule.R;

/**
 * Created by chenqm
 */
public class ScheduleRepeatPop extends PopupWindow implements View.OnClickListener {
    private View mContentView;
    private Activity mContext;
    private TextView mBtnChoose;
    private LinearLayout mLlyOnlyOne;
    private TextView mTvOnlyOne;
    private ImageView mIvOnlyOne;
    private LinearLayout mLlyEveryDay;
    private TextView mTvEveryDay;
    private ImageView mIvEveryDay;
    private LinearLayout mLlyEveryWeek;
    private TextView mTvEveryWeek;
    private ImageView mIvEveryWeek;

    private LinearLayout mLlyEveryMonth;
    private TextView mTvEveryMonth;
    private ImageView mIvEveryMonth;
    private SelectListener mSelectListener;
    private int mType = 1;//1 - 一次性活动，2 - 每天重复，3 - 周重复，4 月重复

    public void setSelectListener(SelectListener selectListener) {
        this.mSelectListener = selectListener;
    }

    public interface SelectListener {
        void onSelected(int type);
    }

    public ScheduleRepeatPop(Activity context,int type,SelectListener selectListener) {
        this.mContext = context;
        this.mType = type;
        this.mSelectListener = selectListener;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContentView = inflater.inflate(R.layout.pop_schedule_repeat, null);
        // 设置SelectPicPopupWindow的View
        this.setContentView(mContentView);
        setFocusable(false);
        setOutsideTouchable(false);
        setWidth(ViewPager.LayoutParams.MATCH_PARENT);
        setHeight(ViewPager.LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        initView();
        initListener();
    }

    private void initView() {
        mBtnChoose = mContentView.findViewById(R.id.tv_choose);
        mLlyOnlyOne = mContentView.findViewById(R.id.lly_only_one);
        mTvOnlyOne = mContentView.findViewById(R.id.tv_only_one);
        mIvOnlyOne = mContentView.findViewById(R.id.iv_only_one);
        mLlyEveryDay = mContentView.findViewById(R.id.lly_every_day);
        mTvEveryDay = mContentView.findViewById(R.id.tv_every_day);
        mIvEveryDay = mContentView.findViewById(R.id.iv_every_day);
        mLlyEveryWeek = mContentView.findViewById(R.id.lly_every_week);
        mTvEveryWeek = mContentView.findViewById(R.id.tv_every_week);
        mIvEveryWeek = mContentView.findViewById(R.id.iv_every_week);
        mLlyEveryMonth = mContentView.findViewById(R.id.lly_every_month);
        mTvEveryMonth = mContentView.findViewById(R.id.tv_every_month);
        mIvEveryMonth = mContentView.findViewById(R.id.iv_every_month);
        refresh(mType);
    }

    private void initListener() {
        mContentView.findViewById(R.id.lly_all).setOnClickListener(this);
        mBtnChoose.setOnClickListener(this);
        mLlyOnlyOne.setOnClickListener(this);
        mLlyEveryDay.setOnClickListener(this);
        mLlyEveryWeek.setOnClickListener(this);
        mLlyEveryMonth.setOnClickListener(this);
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
        if (id == R.id.lly_all){
            dismiss();
        }else if (id == R.id.tv_choose){
            mSelectListener.onSelected(mType);
            dismiss();
        }else if (id == R.id.lly_only_one){
            mType = 1;
            refresh(mType);
        }else if (id == R.id.lly_every_day){
            mType = 2;
            refresh(mType);
        }else if (id == R.id.lly_every_week){
            mType = 3;
            refresh(mType);
        }else if (id == R.id.lly_every_month){
            mType = 4;
            refresh(mType);
        }
    }
    private void refresh(int type){
        mTvOnlyOne.setTextColor(type == 1?ContextCompat.getColor(mContext, R.color.base_blue):ContextCompat.getColor(mContext, R.color.black));
        mTvEveryDay.setTextColor(type == 2?ContextCompat.getColor(mContext, R.color.base_blue):ContextCompat.getColor(mContext, R.color.black));
        mTvEveryWeek.setTextColor(type == 3?ContextCompat.getColor(mContext, R.color.base_blue):ContextCompat.getColor(mContext, R.color.black));
        mTvEveryMonth.setTextColor(type == 4?ContextCompat.getColor(mContext, R.color.base_blue):ContextCompat.getColor(mContext, R.color.black));

        mIvOnlyOne.setVisibility(type == 1?View.VISIBLE:View.GONE);
        mIvEveryDay.setVisibility(type == 2?View.VISIBLE:View.GONE);
        mIvEveryWeek.setVisibility(type == 3?View.VISIBLE:View.GONE);
        mIvEveryMonth.setVisibility(type == 4?View.VISIBLE:View.GONE);
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        this.mType = type;
    }
}
