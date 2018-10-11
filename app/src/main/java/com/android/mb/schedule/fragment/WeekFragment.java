package com.android.mb.schedule.fragment;

import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;

import com.android.mb.schedule.R;
import com.android.mb.schedule.base.BaseMvpFragment;
import com.android.mb.schedule.constants.ProjectConstants;
import com.android.mb.schedule.entitys.ScheduleBean;
import com.android.mb.schedule.entitys.ScheduleData;
import com.android.mb.schedule.presenter.WeekPresenter;
import com.android.mb.schedule.rxbus.Events;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.NavigationHelper;
import com.android.mb.schedule.utils.ProjectHelper;
import com.android.mb.schedule.view.MainActivity;
import com.android.mb.schedule.view.ScheduleAddActivity;
import com.android.mb.schedule.view.ScheduleDetailActivity;
import com.android.mb.schedule.view.interfaces.IWeekView;
import com.android.mb.schedule.widget.ScheduleView;
import com.android.mb.schedule.widget.ScheduleViewEvent;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarUtil;
import com.haibin.calendarview.CalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.functions.Action1;


/**
 * 周视图
 * Created by cgy on 16/7/18.
 */
public class WeekFragment  extends BaseMvpFragment<WeekPresenter,IWeekView> implements IWeekView {
    private CalendarLayout mCalendarLayout;
    private CalendarView mCalendarView;
    private ScheduleView mScheduleView;
    private String mWeekDate;
    private List<String> mSchemeList = new ArrayList<>();
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
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        getScheduleList();
    }

    private void getScheduleList(){
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
        regiestEvent(ProjectConstants.EVENT_UPDATE_SCHEDULE_LIST, new Action1<Events<?>>() {
            @Override
            public void call(Events<?> events) {
                getScheduleList();
            }
        });
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
                Bundle bundle = new Bundle();
                bundle.putLong("id",event.getId());
                bundle.putString("date",Helper.date2String(Helper.string2Date(mCalendarView.getSelectedCalendar().toString(),"yyyyMMdd"),"yyyy-MM-dd"));
                NavigationHelper.startActivity(mContext,ScheduleDetailActivity.class,bundle,false);
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
        mCalendarView.setOnCalendarSelectListener(new CalendarView.OnCalendarSelectListener() {
            @Override
            public void onCalendarOutOfRange(com.haibin.calendarview.Calendar calendar) {

            }

            @Override
            public void onCalendarSelect(com.haibin.calendarview.Calendar calendar, boolean isClick) {
                CalendarUtil.mLastCalendar = calendar;
                String dateStr = calendar.toString();
                MonthFragment.mSelectDate = Helper.date2String(Helper.string2Date(dateStr,"yyyyMMdd"),"yyyy-MM-dd");
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
            setSchemeList(result);
            setData(result);
        }else{
            mScheduleView.setAllDayEvents(new ArrayList<ScheduleViewEvent>());
            mScheduleView.setEvents(new ArrayList<ScheduleViewEvent>());
        }
    }

    private void getWeekData(Date firstDate){
        mWeekDate = Helper.date2String(firstDate,"yyyy-MM-dd");
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("date",mWeekDate);
        mPresenter.getWeekSchedule(requestMap);
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
        setEvent(scheduleEvents,false);
        setEvent(allDayEvents,true);
    }

    //D4EFFF、BFE7FF、AADFFF、94D6FF
    private void setEvent(List<ScheduleBean> dataList,boolean isAllDay){
        List<ScheduleViewEvent> scheduleViewEvents = new ArrayList<>();
        for (ScheduleBean scheduleBean:dataList) {
            Calendar startCal = (Calendar) Calendar.getInstance().clone();
            Calendar endCal = (Calendar) Calendar.getInstance().clone();
            Date startDate = Helper.long2Date(scheduleBean.getTime_s()*1000);
            Date endDate = Helper.long2Date(scheduleBean.getTime_e()*1000);
            startCal.setTime(startDate);
            endCal.setTime(endDate);
            ScheduleViewEvent scheduleViewEvent = new ScheduleViewEvent();
            scheduleViewEvent.setId(scheduleBean.getId());
            scheduleViewEvent.setAllDayEvent(isAllDay);
            scheduleViewEvent.setStartTime(startCal);
            scheduleViewEvent.setEndTime(endCal);
            scheduleViewEvent.setContent(scheduleBean.getTitle());

            scheduleViewEvent.setColor(0xFFD4EFFF);
            scheduleViewEvent.setHeadLineColor(0xFF2aaeff);
            scheduleViewEvent.setTextColor(getResources().getColor(R.color.text_color));
            scheduleViewEvents.add(scheduleViewEvent);
        }
        if (isAllDay){
            mScheduleView.setAllDayEvents(scheduleViewEvents);
        }else{
            mScheduleView.setEvents(scheduleViewEvents);
        }
    }

    private void setSchemeList(List<ScheduleData> result){
        mSchemeList = new ArrayList<>();
        if (Helper.isNotEmpty(result)){
            for (int i=0; i<result.size();i++){
                ScheduleData scheduleData = result.get(i);
                String date = scheduleData.getDate();
                if (Helper.isNotEmpty(scheduleData.getList())){
                    mSchemeList.add(date);
                }
            }
        }
        addSchemeList(mSchemeList);
    }

    private void addSchemeList(List<String> dataList){
        Map<String, com.haibin.calendarview.Calendar> map = new HashMap<>();
        for (String date:dataList) {
            Date date1 = Helper.string2Date(date,"yyyy-MM-dd");
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.setTime(date1);
            map.put(getSchemeCalendar(calendar.get(java.util.Calendar.YEAR), calendar.get(java.util.Calendar.MONTH)+1, calendar.get(java.util.Calendar.DAY_OF_MONTH), 0x2aaeff, "议").toString(),
                    getSchemeCalendar(calendar.get(java.util.Calendar.YEAR), calendar.get(java.util.Calendar.MONTH)+1, calendar.get(java.util.Calendar.DAY_OF_MONTH), 0x2aaeff, "议"));

        }
        mCalendarView.setSchemeDate(map);
    }

    private com.haibin.calendarview.Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        com.haibin.calendarview.Calendar calendar = new com.haibin.calendarview.Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        calendar.addScheme(new com.haibin.calendarview.Calendar.Scheme());
        return calendar;
    }

    public void scrollToSelectDate(){
        if (mCalendarView!=null && Helper.isNotEmpty(MonthFragment.mSelectDate) && Helper.string2Date(MonthFragment.mSelectDate,"yyyy-MM-dd")!=null){
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.setTime(Helper.string2Date(MonthFragment.mSelectDate,"yyyy-MM-dd"));
            mCalendarView.scrollToCalendar(calendar.get(java.util.Calendar.YEAR),calendar.get(java.util.Calendar.MONTH)+1,calendar.get(java.util.Calendar.DAY_OF_MONTH));
            mScheduleView.setFirstDay(calendar);
            Map<String,Object> requestMap = new HashMap<>();
            requestMap.put("date",ProjectHelper.getMonday(calendar));
            mPresenter.getWeekSchedule(requestMap);
        }
    }

}
