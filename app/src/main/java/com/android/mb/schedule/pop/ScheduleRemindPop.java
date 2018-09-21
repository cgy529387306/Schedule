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
public class ScheduleRemindPop extends PopupWindow implements View.OnClickListener {
    private View mContentView;
    private Activity mContext;
    private TextView mBtnChoose;

    private LinearLayout mLlyBeforeTen; //10分钟前
    private TextView mTvBeforeTen;
    private ImageView mIvBeforeTen;

    private LinearLayout mLlyBeforeFifteen; //15分钟前
    private TextView mTvBeforeFifteen;
    private ImageView mIvBeforeFifteen;

    private LinearLayout mLlyBeforeHalfHour; //30分钟前
    private TextView mTvBeforeHalfHour;
    private ImageView mIvBeforeHalfHour;

    private LinearLayout mLlyBeforeOneHour; //1小时前
    private TextView mTvBeforeOneHour;
    private ImageView mIvBeforeOneHour;

    private LinearLayout mLlyBeforeTwoHour; //2小时前
    private TextView mTvBeforeTwoHour;
    private ImageView mIvBeforeTwoHour;

    private LinearLayout mLlyBeforeOneDay; //24小时前
    private TextView mTvBeforeOneDay;
    private ImageView mIvBeforeOneDay;

    private LinearLayout mLlyBeforeTwoDay; //2天前
    private TextView mTvBeforeTwoDay;
    private ImageView mIvBeforeTwoDay;

    private LinearLayout mLlyNoRemind; //不在提醒
    private TextView mTvNoRemind;
    private ImageView mIvNoRemind;
    private SelectListener mSelectListener;
    private int mType = 1;//0 不提醒,1 10分钟前，2 15分钟前，3 30分钟前，4 1小时前，5 2小时前，6 24小时前，7 2天前

    public void setSelectListener(SelectListener selectListener) {
        this.mSelectListener = selectListener;
    }

    public interface SelectListener {
        void onSelected(int type);
    }

    public ScheduleRemindPop(Activity context, int type,SelectListener selectListener) {
        this.mContext = context;
        this.mType = type;
        this.mSelectListener = selectListener;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContentView = inflater.inflate(R.layout.pop_schedule_remind, null);
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
        mLlyBeforeTen = mContentView.findViewById(R.id.lly_before_ten);
        mTvBeforeTen = mContentView.findViewById(R.id.tv_before_ten);
        mIvBeforeTen = mContentView.findViewById(R.id.iv_before_ten);

        mLlyBeforeFifteen = mContentView.findViewById(R.id.lly_before_fifteen);
        mTvBeforeFifteen = mContentView.findViewById(R.id.tv_before_fifteen);
        mIvBeforeFifteen = mContentView.findViewById(R.id.iv_before_fifteen);

        mLlyBeforeHalfHour = mContentView.findViewById(R.id.lly_before_half_hour);
        mTvBeforeHalfHour = mContentView.findViewById(R.id.tv_before_half_hour);
        mIvBeforeHalfHour = mContentView.findViewById(R.id.iv_before_half_hour);

        mLlyBeforeOneHour = mContentView.findViewById(R.id.lly_before_one_hour);
        mTvBeforeOneHour = mContentView.findViewById(R.id.tv_before_one_hour);
        mIvBeforeOneHour = mContentView.findViewById(R.id.iv_before_one_hour);

        mLlyBeforeTwoHour = mContentView.findViewById(R.id.lly_before_two_hour);
        mTvBeforeTwoHour = mContentView.findViewById(R.id.tv_before_two_hour);
        mIvBeforeTwoHour = mContentView.findViewById(R.id.iv_before_two_hour);

        mLlyBeforeOneDay = mContentView.findViewById(R.id.lly_before_one_day);
        mTvBeforeOneDay = mContentView.findViewById(R.id.tv_before_one_day);
        mIvBeforeOneDay = mContentView.findViewById(R.id.iv_before_one_day);

        mLlyBeforeOneDay = mContentView.findViewById(R.id.lly_before_one_day);
        mTvBeforeOneDay = mContentView.findViewById(R.id.tv_before_one_day);
        mIvBeforeOneDay = mContentView.findViewById(R.id.iv_before_one_day);

        mLlyBeforeTwoDay = mContentView.findViewById(R.id.lly_before_two_day);
        mTvBeforeTwoDay = mContentView.findViewById(R.id.tv_before_two_day);
        mIvBeforeTwoDay = mContentView.findViewById(R.id.iv_before_two_day);

        mLlyNoRemind = mContentView.findViewById(R.id.lly_no_remind);
        mTvNoRemind = mContentView.findViewById(R.id.tv_no_remind);
        mIvNoRemind = mContentView.findViewById(R.id.iv_no_remind);
        refresh(mType);
    }

    private void initListener() {
        mContentView.findViewById(R.id.lly_all).setOnClickListener(this);
        mBtnChoose.setOnClickListener(this);
        mLlyBeforeTen.setOnClickListener(this);
        mLlyBeforeFifteen.setOnClickListener(this);
        mLlyBeforeHalfHour.setOnClickListener(this);
        mLlyBeforeOneHour.setOnClickListener(this);
        mLlyBeforeTwoHour.setOnClickListener(this);
        mLlyBeforeOneDay.setOnClickListener(this);
        mLlyBeforeTwoDay.setOnClickListener(this);
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
        if (id == R.id.lly_all){
            dismiss();
        }else if (id == R.id.tv_choose){
            mSelectListener.onSelected(mType);
            dismiss();
        }else if (id == R.id.lly_no_remind){
            mType = 0;
            refresh(mType);
        }else if (id == R.id.lly_before_ten){
            mType = 1;
            refresh(mType);
        }else if (id == R.id.lly_before_fifteen){
            mType = 2;
            refresh(mType);
        }else if (id == R.id.lly_before_half_hour){
            mType = 3;
            refresh(mType);
        }else if (id == R.id.lly_before_one_hour){
            mType = 4;
            refresh(mType);
        }else if (id == R.id.lly_before_two_hour){
            mType = 5;
            refresh(mType);
        }else if (id == R.id.lly_before_one_day){
            mType = 6;
            refresh(mType);
        }else if (id == R.id.lly_before_two_day){
            mType = 7;
            refresh(mType);
        }
    }
    private void refresh(int type){
        mTvNoRemind.setTextColor(type == 0?ContextCompat.getColor(mContext, R.color.base_blue):ContextCompat.getColor(mContext, R.color.black));
        mTvBeforeTen.setTextColor(type == 1?ContextCompat.getColor(mContext, R.color.base_blue):ContextCompat.getColor(mContext, R.color.black));
        mTvBeforeFifteen.setTextColor(type == 2?ContextCompat.getColor(mContext, R.color.base_blue):ContextCompat.getColor(mContext, R.color.black));
        mTvBeforeHalfHour.setTextColor(type == 3?ContextCompat.getColor(mContext, R.color.base_blue):ContextCompat.getColor(mContext, R.color.black));
        mTvBeforeOneHour.setTextColor(type == 4?ContextCompat.getColor(mContext, R.color.base_blue):ContextCompat.getColor(mContext, R.color.black));
        mTvBeforeTwoHour.setTextColor(type == 5?ContextCompat.getColor(mContext, R.color.base_blue):ContextCompat.getColor(mContext, R.color.black));
        mTvBeforeOneDay.setTextColor(type == 6?ContextCompat.getColor(mContext, R.color.base_blue):ContextCompat.getColor(mContext, R.color.black));
        mTvBeforeTwoDay.setTextColor(type == 7?ContextCompat.getColor(mContext, R.color.base_blue):ContextCompat.getColor(mContext, R.color.black));

        mIvNoRemind.setVisibility(type == 0?View.VISIBLE:View.GONE);
        mIvBeforeTen.setVisibility(type == 1?View.VISIBLE:View.GONE);
        mIvBeforeFifteen.setVisibility(type == 2?View.VISIBLE:View.GONE);
        mIvBeforeHalfHour.setVisibility(type == 3?View.VISIBLE:View.GONE);
        mIvBeforeOneHour.setVisibility(type == 4?View.VISIBLE:View.GONE);
        mIvBeforeTwoHour.setVisibility(type == 5?View.VISIBLE:View.GONE);
        mIvBeforeOneDay.setVisibility(type == 6?View.VISIBLE:View.GONE);
        mIvBeforeTwoDay.setVisibility(type == 7?View.VISIBLE:View.GONE);
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        this.mType = type;
    }
}
