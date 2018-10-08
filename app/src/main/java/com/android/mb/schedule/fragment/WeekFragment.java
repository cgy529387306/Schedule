package com.android.mb.schedule.fragment;

import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;

import com.android.mb.schedule.R;
import com.android.mb.schedule.base.BaseMvpFragment;
import com.android.mb.schedule.entitys.ScheduleBean;
import com.android.mb.schedule.entitys.ScheduleData;
import com.android.mb.schedule.presenter.WeekPresenter;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.NavigationHelper;
import com.android.mb.schedule.view.MainActivity;
import com.android.mb.schedule.view.ScheduleAddActivity;
import com.android.mb.schedule.view.interfaces.IWeekView;
import com.android.mb.schedule.widget.ScheduleView;
import com.android.mb.schedule.widget.ScheduleViewEvent;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
        if (Helper.isNotEmpty(mCalendarView.getCurrentWeekCalendars())){
            String firstDay = mCalendarView.getCurrentWeekCalendars().get(0).toString();
            Date firstDate = Helper.string2Date(firstDay,"yyyyMMdd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(firstDate);
            mScheduleView.setFirstDay(calendar);
            getWeekData(firstDate);
        }
    }

    @Override
    protected void setListener() {
        mScheduleView.setOnEventAddClickListener(new ScheduleView.OnEventAddClickListener() {
            @Override
            public void onEventAddClicked(Calendar time) {
                String date = Helper.date2String(time.getTime());
                Bundle bundle = new Bundle();
                bundle.putInt("type",0);
                if (Helper.isNotEmpty(date)){
                    bundle.putString("date",date);
                }
                NavigationHelper.startActivity(mContext,ScheduleAddActivity.class,bundle,false);
            }
        });
        mScheduleView.setOnEventClickListener(new ScheduleView.OnEventClickListener() {
            @Override
            public void onEventClick(ScheduleViewEvent event, RectF eventRectF) {
//                Toast.makeText(MBApplication.getInstance(), event.getContent(), Toast.LENGTH_LONG).show();
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
                if (Helper.isNotEmpty(weekCalendars)){
                    String firstDay = weekCalendars.get(0).toString();
                    Date firstDate = Helper.string2Date(firstDay,"yyyyMMdd");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(firstDate);
                    mScheduleView.setFirstDay(calendar);
                    getWeekData(firstDate);
                }
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
    public void getSuccess(List<ScheduleData> result) {
        if (Helper.isNotEmpty(result)){
            setData(result);
        }
    }

    private void getWeekData(Date firstDate){
        mWeekDate = Helper.date2String(firstDate,"yyyy-MM-dd");
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("date",mWeekDate);
        mPresenter.getWeekSchedule(requestMap);
        mScheduleView.setEvents(new ArrayList<ScheduleViewEvent>());
    }

    private void setData(List<ScheduleData> result){
        List<ScheduleBean> scheduleEvents = new ArrayList<>();
        List<ScheduleBean> allDayEvents = new ArrayList<>();
        for (ScheduleData scheduleData:result){
            for (ScheduleBean scheduleBean : scheduleData.getList()){
                if (scheduleBean.getAllDay()==1){
                    allDayEvents.add(scheduleBean);
                }else{
                    scheduleEvents.add(scheduleBean);
                }
            }
        }
        setEvent(scheduleEvents);
        setAllDayEvents(allDayEvents);
    }

    private void setEvent(List<ScheduleBean> dataList){
        List<ScheduleViewEvent> scheduleViewEvents = new ArrayList<>();
        for (ScheduleBean scheduleBean:dataList) {
            Calendar startCal = (Calendar) Calendar.getInstance().clone();
            Calendar endCal = (Calendar) Calendar.getInstance().clone();
            Date startDate = Helper.long2Date(scheduleBean.getTime_s()/1000);
            Date endDate = Helper.long2Date(scheduleBean.getTime_e()/1000);
            startCal.setTime(startDate);
            endCal.setTime(endDate);
            ScheduleViewEvent scheduleViewEvent = new ScheduleViewEvent();
            scheduleViewEvent.setAllDayEvent(false);
            scheduleViewEvent.setColor(Color.BLUE);
            scheduleViewEvent.setContent(scheduleBean.getTitle());
            scheduleViewEvent.setTextColor(Color.BLACK);
            scheduleViewEvent.setStartTime(startCal);
            scheduleViewEvent.setEndTime(endCal);
        }
        mScheduleView.setEvents(scheduleViewEvents);
    }

    private void setAllDayEvents(List<ScheduleBean> dataList){
        List<ScheduleViewEvent> scheduleViewEvents = new ArrayList<>();
        for (ScheduleBean scheduleBean:dataList) {
            Calendar startCal = (Calendar) Calendar.getInstance().clone();
            Calendar endCal = (Calendar) Calendar.getInstance().clone();
            Date startDate = Helper.long2Date(scheduleBean.getTime_s()/1000);
            Date endDate = Helper.long2Date(scheduleBean.getTime_e()/1000);
            startCal.setTime(startDate);
            endCal.setTime(endDate);
            ScheduleViewEvent scheduleViewEvent = new ScheduleViewEvent();
            scheduleViewEvent.setAllDayEvent(true);
            scheduleViewEvent.setColor(Color.YELLOW);
            scheduleViewEvent.setContent(scheduleBean.getTitle());
            scheduleViewEvent.setTextColor(Color.RED);
            scheduleViewEvent.setStartTime(startCal);
            scheduleViewEvent.setEndTime(endCal);
        }
        mScheduleView.setAllDayEvents(scheduleViewEvents);
    }
    


}
