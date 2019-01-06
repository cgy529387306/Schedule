package com.android.mb.schedule.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.LunarUtil;
import com.android.mb.schedule.view.ScheduleAddActivity;
import com.haibin.calendarview.CalendarUtil;
import com.haibin.calendarview.CalendarView;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by cgy on 19/1/6.
 */

public class DtpDialogFrament extends DialogFragment implements View.OnClickListener{

    private ImageView mIvBack,mIvNext;
    private TextView mTvMonth,mTvStart,mTvEnd;
    private TextView mTvBack,mTvFinish;
    private CalendarView mCalendarView;
    private String mDateStr,mStartTime,mEndTime;

    public static DtpDialogFrament newInstance(String dateStr, String startTime, String endTime) {
        DtpDialogFrament f = new DtpDialogFrament();
        Bundle args = new Bundle();
        args.putString("dateStr", dateStr);
        args.putString("startTime", startTime);
        args.putString("endTime", endTime);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        int style = DialogFragment.STYLE_NO_TITLE;
        int theme =android.R.style.Theme_Holo_Light_Dialog;
        setStyle(style,theme);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_date_time_picker, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initListener();
        initData();
    }

    private void initView(View view){
        mCalendarView = view.findViewById(R.id.calendarView);
        mIvBack = view.findViewById(R.id.iv_back);
        mIvNext = view.findViewById(R.id.iv_next);
        mTvMonth = view.findViewById(R.id.tv_month);
        mTvStart = view.findViewById(R.id.tv_start);
        mTvEnd = view.findViewById(R.id.tv_end);
        mTvBack = view.findViewById(R.id.tv_back);
        mTvFinish = view.findViewById(R.id.tv_finish);
    }

    private void initListener(){
        mIvBack.setOnClickListener(this);
        mIvNext.setOnClickListener(this);
        mTvStart.setOnClickListener(this);
        mTvEnd.setOnClickListener(this);
        mTvBack.setOnClickListener(this);
        mTvFinish.setOnClickListener(this);
        mCalendarView.setOnCalendarSelectListener(new CalendarView.OnCalendarSelectListener() {
            @Override
            public void onCalendarOutOfRange(com.haibin.calendarview.Calendar calendar) {

            }

            @Override
            public void onCalendarSelect(com.haibin.calendarview.Calendar calendar, boolean isClick) {
                CalendarUtil.mLastCalendar = calendar;
                String date = LunarUtil.getMonthStr(calendar.getMonth())+" "+calendar.getYear();
                mTvMonth.setText(date);
            }
        });
    }

    private void initData(){
        Bundle bundle = getArguments();
        if (bundle != null) {
            mDateStr = bundle.getString("dateStr");
            mStartTime = bundle.getString("startTime");
            mEndTime = bundle.getString("endTime");

            Date date = Helper.string2Date(mDateStr, ScheduleAddActivity.mDateFormat);
            Calendar calendar = (Calendar) Calendar.getInstance().clone();
            calendar.setTime(date);
            mCalendarView.scrollToCalendar(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.DAY_OF_MONTH));
            mTvStart.setText(mStartTime);
            mTvEnd.setText(mEndTime);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_back){
            mCalendarView.scrollToPre();
        }else if (id == R.id.iv_next){
            mCalendarView.scrollToNext();
        }else if (id == R.id.tv_start){
            String startTime = mTvStart.getText().toString().trim();
            showDialog(startTime);
        }else if (id == R.id.tv_end){
            String endTime = mTvEnd.getText().toString().trim();
            showDialog(endTime);
        }else if (id == R.id.tv_back){
            dismiss();
        }else if (id == R.id.tv_finish){
            dismiss();
        }
    }

    private void showDialog(String time) {
        TpDialogFrament newFragment = TpDialogFrament.newInstance(time);
        newFragment.show(getFragmentManager(), "tpDialog");

    }
}
