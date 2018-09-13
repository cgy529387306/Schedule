package com.android.mb.schedule.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.mb.schedule.R;
import com.android.mb.schedule.widget.DatePicker;
import com.android.mb.schedule.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by chenqm
 */
public class ScheduleTimePop extends PopupWindow implements View.OnClickListener {
    private View mContentView;
    private Activity mContext;
    private SelectListener mSelectListener;
    private Calendar mCalendar;
    private String mSelectDate,mSelectTime;
    //选择时间与当前时间，用于判断用户选择的是否是以前的时间
    private DatePicker mDpDate;
    private TimePicker mTpTime;
    private TextView mBtnConfirm;
    private TextView mBtnCancel;

    public void setSelectListener(SelectListener selectListener) {
        this.mSelectListener = selectListener;
    }

    public interface SelectListener {
        void onSelected(String selectDate, String selectTime);
    }

    public ScheduleTimePop(Activity context, SelectListener selectListener) {
        mContext = context;
        this.mSelectListener = selectListener;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContentView = inflater.inflate(R.layout.pop_select_time, null);
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
        mCalendar = Calendar.getInstance();
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH) + 1;
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        int hour = mCalendar.get(Calendar.HOUR);
        int minute = mCalendar.get(Calendar.MINUTE);


        String hourStr = hour < 10?("0"+hour):hour+"";
        String minuteStr = minute < 10?("0"+minute):minute+"";
        String monthStr = month < 10?("0"+month):month+"";
        String dayStr = day < 10?("0"+day):day+"";

        mSelectTime = hourStr + ":" + minuteStr;
        mSelectDate =  year+ "年" + monthStr + "月" +  dayStr + "日";
        mDpDate = mContentView.findViewById(R.id.dp_date);
        mTpTime = mContentView.findViewById(R.id.tp_time);
        mBtnConfirm = mContentView.findViewById(R.id.tv_confirm);
        mBtnCancel = mContentView.findViewById(R.id.tv_cancel);


    }

    private void initListener() {
        mDpDate.setOnChangeListener(mOnDateChangeLister);
        mTpTime.setOnChangeListener(mOnTimeChangeLister);
        mBtnConfirm.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);
    }


    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            this.showAtLocation(parent, Gravity.BOTTOM | Gravity.LEFT, 0, 0);
        } else {
            this.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_confirm){
            mSelectListener.onSelected(mSelectDate,mSelectTime);
            dismiss();
        }else if (id == R.id.tv_cancel){
            dismiss();
        }
    }

    //listeners
    private DatePicker.OnChangeListener mOnDateChangeLister = new DatePicker.OnChangeListener() {
        @Override
        public void onChange(int year, int month, int day, int day_of_week) {
            String monthStr = month < 10?("0"+month):month+"";
            String dayStr = day < 10?("0"+day):day+"";
            mSelectDate = year + "年" + monthStr + "月" + dayStr + "日";
        }
    };
    private TimePicker.OnChangeListener mOnTimeChangeLister = new TimePicker.OnChangeListener() {
        @Override
        public void onChange(int hour, int minute) {
            String hourStr = hour < 10?("0"+hour):hour+"";
            String minuteStr = minute < 10?("0"+minute):minute+"";
            mSelectTime = hourStr + ":" + minuteStr;
        }
    };

    /**
     * @param type 0：开始时间  1：结束时间
     */
    public void setType(int type){
        Calendar calendar = Calendar.getInstance();
        if (type == 1){
            calendar.add(Calendar.HOUR_OF_DAY,1);
        }
        mTpTime.setCurrentTime(calendar);
    }
}
