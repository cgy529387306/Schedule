package com.android.mb.schedule.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
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
    private View conentView;
    private Activity mContext;
    private SelectListener mSelectListener;
    private Calendar calendar;
    private String selectDate,selectTime;
    //选择时间与当前时间，用于判断用户选择的是否是以前的时间
    private int currentHour,currentMinute,currentDay,selectHour,selectMinute,selectDay;
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
        conentView = inflater.inflate(R.layout.pop_select_time, null);
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
        calendar = Calendar.getInstance();
//        selectDate = calendar.get(Calendar.YEAR) + "年" + calendar.get(Calendar.MONTH) + "月"
//                + calendar.get(Calendar.DAY_OF_MONTH) + "日"
//                + DatePicker.getDayOfWeekCN(calendar.get(Calendar.DAY_OF_WEEK));
        selectDate = calendar.get(Calendar.YEAR) + "年" + calendar.get(Calendar.MONTH) + "月"
                + calendar.get(Calendar.DAY_OF_MONTH) + "日";
        //选择时间与当前时间的初始化，用于判断用户选择的是否是以前的时间，如果是，弹出toss提示不能选择过去的时间
        selectDay = currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        selectMinute = currentMinute = calendar.get(Calendar.MINUTE);
        selectHour = currentHour = calendar.get(Calendar.HOUR_OF_DAY);

//        selectTime = currentHour + "点" + ((currentMinute < 10)?("0"+currentMinute):currentMinute) + "分";
        selectTime = currentHour + ":" + ((currentMinute < 10)?("0"+currentMinute):currentMinute);
        mDpDate = conentView.findViewById(R.id.dp_date);
        mTpTime = conentView.findViewById(R.id.tp_time);
        mBtnConfirm = conentView.findViewById(R.id.tv_confirm);
        mBtnCancel = conentView.findViewById(R.id.tv_cancel);
    }

    private void initListener() {
        mDpDate.setOnChangeListener(dp_onchanghelistener);
        mTpTime.setOnChangeListener(tp_onchanghelistener);
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
            // 以下拉方式显示popupwindow
            this.showAtLocation(parent, Gravity.BOTTOM | Gravity.LEFT, 0, 0);
        } else {
            this.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_confirm){
            if(selectDay == currentDay ){	//在当前日期情况下可能出现选中过去时间的情况
                if(selectHour < currentHour){
                    Toast.makeText(mContext, "不能选择过去的时间\n        请重新选择", Toast.LENGTH_SHORT).show();
                }else if( (selectHour == currentHour) && (selectMinute < currentMinute) ){
                    Toast.makeText(mContext, "不能选择过去的时间\n        请重新选择", Toast.LENGTH_SHORT).show();
                }else{
                    mSelectListener.onSelected(selectDate,selectTime);
                    dismiss();
                }
            }else{
                mSelectListener.onSelected(selectDate,selectTime);
                dismiss();
            }
        }else if (id == R.id.tv_cancel){
            dismiss();
        }
    }
    //listeners
    DatePicker.OnChangeListener dp_onchanghelistener = new DatePicker.OnChangeListener() {
        @Override
        public void onChange(int year, int month, int day, int day_of_week) {
            selectDay = day;
//            selectDate = year + "年" + month + "月" + day + "日" + DatePicker.getDayOfWeekCN(day_of_week);
            selectDate = year + "年" + month + "月" + day + "日";
        }
    };
    TimePicker.OnChangeListener tp_onchanghelistener = new TimePicker.OnChangeListener() {
        @Override
        public void onChange(int hour, int minute) {
//            selectTime = hour + "点" + ((minute < 10)?("0"+minute):minute) + "分";
            selectTime = hour + ":" + ((minute < 10)?("0"+minute):minute);
            selectHour = hour;
            selectMinute = minute;
        }
    };
}
