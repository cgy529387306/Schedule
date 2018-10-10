package com.android.mb.schedule.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.adapter.MultipleItemQuickAdapter;
import com.android.mb.schedule.adapter.SectionAdapter;
import com.android.mb.schedule.adapter.SectionMyAdapter;
import com.android.mb.schedule.base.BaseMvpFragment;
import com.android.mb.schedule.constants.ProjectConstants;
import com.android.mb.schedule.entitys.ScheduleBean;
import com.android.mb.schedule.entitys.ScheduleData;
import com.android.mb.schedule.entitys.ScheduleSection;
import com.android.mb.schedule.presenter.MonthPresenter;
import com.android.mb.schedule.rxbus.Events;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.LunarUtil;
import com.android.mb.schedule.utils.NavigationHelper;
import com.android.mb.schedule.utils.ProjectHelper;
import com.android.mb.schedule.view.MainActivity;
import com.android.mb.schedule.view.ScheduleDetailActivity;
import com.android.mb.schedule.view.ScheduleUserActivity;
import com.android.mb.schedule.view.interfaces.IMonthView;
import com.android.mb.schedule.widget.MyDividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarUtil;
import com.haibin.calendarview.CalendarView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.functions.Action1;


/**
 * 月视图
 * Created by cgy on 16/7/18.
 */
public class MonthFragment extends BaseMvpFragment<MonthPresenter,IMonthView> implements IMonthView {
    private CalendarLayout mCalendarLayout;
    private CalendarView mCalendarView;
    private RecyclerView mRecyclerView;
    private SectionMyAdapter mAdapter;
    private TextView mTvDate;
    private String mMonthDate;
    public static String mSelectDate;
    private List<ScheduleBean> mDataList = new ArrayList<>();
    private List<ScheduleData> mScheduleDataList = new ArrayList<>();
    private List<String> mSchemeList = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.frg_month;
    }

    @Override
    protected void bindViews(View view) {
        mCalendarLayout = view.findViewById(R.id.calendarLayout);
        mCalendarView = view.findViewById(R.id.calendarView);
        mRecyclerView = view.findViewById(R.id.recyclerView);
    }

    @Override
    protected void processLogic() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new SectionMyAdapter(R.layout.item_section_content_my,R.layout.item_section_header_my,new ArrayList());
        mAdapter.addHeaderView(getHeaderView());
        mRecyclerView.setAdapter(mAdapter);
    }

    private View getHeaderView(){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.header_home, null);
        mTvDate = view.findViewById(R.id.tv_date);
        return view;
    }

    private void addSchemeList(List<String> dataList){
        Map<String, Calendar> map = new HashMap<>();
        for (String date:dataList) {
            Date date1 = Helper.string2Date(date,"yyyy-MM-dd");
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.setTime(date1);
            map.put(getSchemeCalendar(calendar.get(java.util.Calendar.YEAR), calendar.get(java.util.Calendar.MONTH)+1, calendar.get(java.util.Calendar.DAY_OF_MONTH), 0x2aaeff, "议").toString(),
                    getSchemeCalendar(calendar.get(java.util.Calendar.YEAR), calendar.get(java.util.Calendar.MONTH)+1, calendar.get(java.util.Calendar.DAY_OF_MONTH), 0x2aaeff, "议"));

        }
        mCalendarView.setSchemeDate(map);
    }


    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        calendar.addScheme(new Calendar.Scheme());
        return calendar;
    }

    private void getScheduleList(){
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("date",mMonthDate);
        mPresenter.getMonthSchedule(requestMap);
    }

    @Override
    protected void setListener() {
        regiestEvent(ProjectConstants.EVENT_UPDATE_SCHEDULE_LIST, new Action1<Events<?>>() {
            @Override
            public void call(Events<?> events) {
                getScheduleList();
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ScheduleSection mySection = mAdapter.getItem(position);
                if (!mySection.isHeader){
                    Bundle bundle = new Bundle();
                    bundle.putLong("id",mySection.t.getId());
                    bundle.putString("date",Helper.date2String(Helper.string2Date(mCalendarView.getSelectedCalendar().toString(),"yyyyMMdd"),"yyyy-MM-dd"));
                    NavigationHelper.startActivity(mContext,ScheduleDetailActivity.class,bundle,false);
                }
            }
        });
        mCalendarView.setOnMonthChangeListener(new CalendarView.OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month) {
                String monthStr = month<10?"0"+month:month+"";
                String title = year+"年"+monthStr+"月";
                assert ((MainActivity)getActivity()) != null;
                ((MainActivity)getActivity()).setTitle(title);
                mMonthDate = year+"-"+monthStr+"-"+"01";
                getScheduleList();
            }
        });
        mCalendarView.setOnCalendarSelectListener(new CalendarView.OnCalendarSelectListener() {
            @Override
            public void onCalendarOutOfRange(Calendar calendar) {

            }

            @Override
            public void onCalendarSelect(Calendar calendar, boolean isClick) {
                CalendarUtil.mLastCalendar = calendar;
                String dateStr = calendar.toString();
                mSelectDate = Helper.date2String(Helper.string2Date(dateStr,"yyyyMMdd"),"yyyy-MM-dd");
                java.util.Calendar calendar1 = java.util.Calendar.getInstance();
                Date date = Helper.string2Date(calendar.toString(),"yyyyMMdd");
                calendar1.setTime(date);
                LunarUtil lunar = new LunarUtil(calendar1);
                if (calendar.isCurrentDay()){
                    mTvDate.setText("今天  农历  "+ lunar.toString());
                }else{
                    mTvDate.setText("农历  "+lunar.toString());
                }
                if (Helper.isNotEmpty(mScheduleDataList)){
                    mDataList = ProjectHelper.getScheduleList(mSelectDate,mScheduleDataList);
                    mAdapter.setNewData(ProjectHelper.getSectionData(mDataList));
                }else{
                    mDataList = new ArrayList<ScheduleBean>();
                    mAdapter.setNewData( ProjectHelper.getSectionData(mDataList));
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
    public void getSuccess(List<ScheduleData> result) {
        mDataList = new ArrayList<>();
        mSchemeList = new ArrayList<>();
        mScheduleDataList = result;
        if (Helper.isNotEmpty(mScheduleDataList)){
            for (int i=0; i<mScheduleDataList.size();i++){
                ScheduleData scheduleData = mScheduleDataList.get(i);
                String date = scheduleData.getDate();
                if (Helper.isNotEmpty(scheduleData.getList())){
                    mSchemeList.add(date);
                }
                if (mSelectDate.equals(date)){
                    mDataList = scheduleData.getList();
                }
            }
        }
        addSchemeList(mSchemeList);
        mAdapter.setNewData(ProjectHelper.getSectionData(mDataList));
        mAdapter.setEmptyView(R.layout.empty_schedule, (ViewGroup) mRecyclerView.getParent());
    }

    @Override
    protected MonthPresenter createPresenter() {
        return new MonthPresenter();
    }


    public void scrollToSelectDate(){
        if (mCalendarView!=null && Helper.isNotEmpty(mSelectDate) && Helper.string2Date(mSelectDate,"yyyy-MM-dd")!=null){
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.setTime(Helper.string2Date(mSelectDate,"yyyy-MM-dd"));
            mCalendarView.scrollToCalendar(calendar.get(java.util.Calendar.YEAR),calendar.get(java.util.Calendar.MONTH)+1,calendar.get(java.util.Calendar.DAY_OF_MONTH));
        }
    }
}
