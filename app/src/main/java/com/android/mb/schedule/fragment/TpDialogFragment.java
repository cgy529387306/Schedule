package com.android.mb.schedule.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.ProjectHelper;
import com.android.mb.schedule.utils.ToastHelper;
import com.android.mb.schedule.view.ScheduleAddActivity;
import com.bigkoo.pickerview.adapter.MinuteWheelAdapter;
import com.bigkoo.pickerview.adapter.NumericWheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by cgy on 19/1/6.
 */

public class TpDialogFragment extends DialogFragment implements View.OnClickListener{

    private TextView mTvBack,mTvFinish;
    private WheelView mHour,mMin;
    public static TimeSelectListener mTimeSelectListener;

    public interface TimeSelectListener {
        void onTimeSelect(String time);
    }

    public static TpDialogFragment newInstance(String time,TimeSelectListener timeSelectListener) {
        mTimeSelectListener = timeSelectListener;
        TpDialogFragment f = new TpDialogFragment();
        Bundle args = new Bundle();
        args.putString("time", time);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        int theme = android.R.style.Theme_Holo_Light_Dialog;
        setStyle(DialogFragment.STYLE_NO_TITLE,theme);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_time_picker, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initListener();
        initData();
    }

    private void initView(View view){
        mHour = view.findViewById(R.id.hour);
        mMin = view.findViewById(R.id.min);
        mTvBack = view.findViewById(R.id.tv_back);
        mTvFinish = view.findViewById(R.id.tv_finish);
    }

    private void initListener(){
        mTvBack.setOnClickListener(this);
        mTvFinish.setOnClickListener(this);
        mHour.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {

            }
        });
        mMin.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {

            }
        });
    }

    private void initData(){
        Bundle bundle = getArguments();
        int hour = 0;
        int min = 0;
        if (bundle != null) {
            try {
                String time = bundle.getString("time");
                Date date = Helper.string2Date(time, ScheduleAddActivity.mTimeFormat);
                Calendar calendar = (Calendar) Calendar.getInstance().clone();
                calendar.setTime(date);
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                min = calendar.get(Calendar.MINUTE)/5;
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        //时
        mHour.setTextColorCenter(getResources().getColor(R.color.base_blue));
        mHour.setTextColorOut(getResources().getColor(R.color.text_color));
        mHour.setTextSize(38);
        mHour.setAdapter(new NumericWheelAdapter(0, 23));
        mHour.setCurrentItem(hour>23?0:hour);
        mHour.setGravity(Gravity.CENTER);
        //分
        mMin.setTextColorCenter(getResources().getColor(R.color.base_blue));
        mMin.setTextColorOut(getResources().getColor(R.color.text_color));
        mMin.setTextSize(38);
        mMin.setAdapter(new MinuteWheelAdapter(0, 55));
        mMin.setCurrentItem(min>11?0:min);
        mMin.setGravity(Gravity.CENTER);


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_back){
            dismiss();
        }else if (id == R.id.tv_finish){
            doConfirm();
        }
    }

    private void doConfirm(){
        try {
            Integer hour = (Integer) mHour.getAdapter().getItem(mHour.getCurrentItem());
            Integer min = (Integer) mMin.getAdapter().getItem(mMin.getCurrentItem());
            String hourStr = ProjectHelper.getContentText(hour);
            String minStr = ProjectHelper.getContentText(min);
            String timeStr = hourStr+":"+minStr;
            if (mTimeSelectListener!=null){
                mTimeSelectListener.onTimeSelect(timeStr);
                dismiss();
            }
        }catch (Exception e){
            e.printStackTrace();
            dismiss();
        }
    }


}
