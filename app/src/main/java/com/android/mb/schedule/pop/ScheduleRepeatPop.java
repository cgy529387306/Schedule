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
public class ScheduleRepeatPop extends PopupWindow implements View.OnClickListener {
    private View conentView;
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
    private SelectListener mSelectListener;
    private int type = 0;

    public void setSelectListener(SelectListener selectListener) {
        this.mSelectListener = selectListener;
    }

    public interface SelectListener {
        void onSelected(int type);
    }

    public ScheduleRepeatPop(Activity context,SelectListener selectListener) {
        mContext = context;
        this.mSelectListener = selectListener;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.pop_schedule_repeat, null);
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
        mLlyOnlyOne = conentView.findViewById(R.id.lly_onlyone);
        mTvOnlyOne = conentView.findViewById(R.id.tv_onlyone);
        mIvOnlyOne = conentView.findViewById(R.id.iv_onlyone);
        mLlyEveryDay = conentView.findViewById(R.id.lly_everyday);
        mTvEveryDay = conentView.findViewById(R.id.tv_everyday);
        mIvEveryDay = conentView.findViewById(R.id.iv_everyday);
        mLlyEveryWeek = conentView.findViewById(R.id.lly_everyweek);
        mTvEveryWeek = conentView.findViewById(R.id.tv_everyweek);
        mIvEveryWeek = conentView.findViewById(R.id.iv_everyweek);
        refresh(0);
    }

    private void initListener() {
        mBtnChoose.setOnClickListener(this);
        mLlyOnlyOne.setOnClickListener(this);
        mLlyEveryDay.setOnClickListener(this);
        mLlyEveryWeek.setOnClickListener(this);
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
        }else if (id == R.id.lly_onlyone){
            type = 0;
            refresh(type);
        }else if (id == R.id.lly_everyday){
            type = 1;
            refresh(type);
        }else if (id == R.id.lly_everyweek){
            type = 2;
            refresh(type);
        }
    }
    private void refresh(int type){
        mTvOnlyOne.setTextColor(type == 0?ContextCompat.getColor(mContext, R.color.base_blue):ContextCompat.getColor(mContext, R.color.black));
        mTvEveryDay.setTextColor(type == 1?ContextCompat.getColor(mContext, R.color.base_blue):ContextCompat.getColor(mContext, R.color.black));
        mTvEveryWeek.setTextColor(type == 2?ContextCompat.getColor(mContext, R.color.base_blue):ContextCompat.getColor(mContext, R.color.black));

        mIvOnlyOne.setVisibility(type == 0?View.VISIBLE:View.GONE);
        mIvEveryDay.setVisibility(type == 1?View.VISIBLE:View.GONE);
        mIvEveryWeek.setVisibility(type == 2?View.VISIBLE:View.GONE);
    }
}
