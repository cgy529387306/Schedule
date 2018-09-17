package com.android.mb.schedule.fragment;

import android.graphics.RectF;
import android.view.View;
import android.widget.Toast;

import com.android.mb.schedule.R;
import com.android.mb.schedule.app.MBApplication;
import com.android.mb.schedule.base.BaseFragment;
import com.android.mb.schedule.base.BaseMvpFragment;
import com.android.mb.schedule.presenter.MonthPresenter;
import com.android.mb.schedule.presenter.WeekPresenter;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.view.MainActivity;
import com.android.mb.schedule.view.interfaces.IMonthView;
import com.android.mb.schedule.view.interfaces.IWeekView;
import com.android.mb.schedule.widget.ScheduleView;
import com.android.mb.schedule.widget.ScheduleViewEvent;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 周视图
 * Created by cgy on 16/7/18.
 */
public class WeekFragment  extends BaseMvpFragment<WeekPresenter,IWeekView> implements IWeekView {
    private CalendarLayout mCalendarLayout;
    private CalendarView mCalendarView;
    private ScheduleView mScheduleView;
    private String mWeekDate;
    @Override
    protected int getLayoutId() {
        return R.layout.frg_week;
    }

    @Override
    protected void bindViews(View view) {
        mCalendarLayout = view.findViewById(R.id.calendarLayout);
        mCalendarView = view.findViewById(R.id.calendarView);
        mScheduleView = view.findViewById(R.id.scheduleView);
    }

    @Override
    protected void processLogic() {
        getWeekData(mCalendarView.getCurrentWeekCalendars());
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
        mCalendarView.setOnMonthChangeListener(new CalendarView.OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month) {
                String title = year+"年"+month+"月";
                ((MainActivity)getActivity()).setTitle(title);
            }
        });
        mCalendarView.setOnWeekChangeListener(new CalendarView.OnWeekChangeListener() {
            @Override
            public void onWeekChange(List<com.haibin.calendarview.Calendar> weekCalendars) {
                getWeekData(weekCalendars);
            }
        });
    }

    public void toToday(){
        if (mCalendarView!=null){
            mCalendarView.scrollToCurrent();
        }
    }

    @Override
    protected WeekPresenter createPresenter() {
        return new WeekPresenter();
    }

    @Override
    public void getSuccess() {

    }

    private void getWeekData(List<com.haibin.calendarview.Calendar> weekCalendars){
        if (Helper.isNotEmpty(weekCalendars)){
            String firstDay = weekCalendars.get(0).toString();
            mWeekDate = Helper.date2String(Helper.string2Date(firstDay,"yyyyMMdd"),"yyyy-MM-dd");
            Map<String,Object> requestMap = new HashMap<>();
            requestMap.put("date",mWeekDate);
            mPresenter.getWeekSchedule(requestMap);
        }
    }
}
