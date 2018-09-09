package com.android.mb.schedule.fragment;

import android.graphics.RectF;
import android.view.View;
import android.widget.Toast;

import com.android.mb.schedule.R;
import com.android.mb.schedule.app.MBApplication;
import com.android.mb.schedule.base.BaseFragment;
import com.android.mb.schedule.widget.ScheduleView;
import com.android.mb.schedule.widget.ScheduleViewEvent;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * 周视图
 * Created by cgy on 16/7/18.
 */
public class WeekFragment extends BaseFragment {
//    private CalendarLayout mCalendarLayout;
//    private CalendarView mCalendarView;
    private ScheduleView mScheduleView;
    @Override
    protected int getLayoutId() {
        return R.layout.frg_week;
    }

    @Override
    protected void bindViews(View view) {
//        mCalendarLayout = view.findViewById(R.id.calendarLayout);
//        mCalendarView = view.findViewById(R.id.calendarView);
        mScheduleView = view.findViewById(R.id.scheduleView);
    }

    @Override
    protected void processLogic() {
//        mCalendarLayout.shrink();
    }

    @Override
    protected void setListener() {
        mScheduleView.setOnEventAddClickListener(new ScheduleView.OnEventAddClickListener() {
            @Override
            public void onEventAddClicked(Calendar time) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd E hh:mm");
                String date = dateFormat.format(time.getTime());
                Toast.makeText(MBApplication.getInstance(),date, Toast.LENGTH_LONG).show();
            }
        });
        mScheduleView.setOnEventClickListener(new ScheduleView.OnEventClickListener() {
            @Override
            public void onEventClick(ScheduleViewEvent event, RectF eventRectF) {
                Toast.makeText(MBApplication.getInstance(), event.getContent(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
