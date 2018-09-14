package com.android.mb.schedule.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.adapter.MultipleItemQuickAdapter;
import com.android.mb.schedule.base.BaseFragment;
import com.android.mb.schedule.entitys.MultipleItem;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.LunarUtil;
import com.android.mb.schedule.view.MainActivity;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 月视图
 * Created by cgy on 16/7/18.
 */
public class MonthFragment extends BaseFragment {
    private CalendarLayout mCalendarLayout;
    private CalendarView mCalendarView;
    private RecyclerView mRecyclerView;
    private MultipleItemQuickAdapter mAdapter;
    private TextView mTvDate;
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
        initData();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<MultipleItem> data = getMultipleItemData();
        mAdapter = new MultipleItemQuickAdapter(getActivity(), data);
        mAdapter.addHeaderView(getHeaderView());
        mAdapter.addFooterView(LayoutInflater.from(getActivity()).inflate(R.layout.footer_home, null));
        mRecyclerView.setAdapter(mAdapter);
    }

    private View getHeaderView(){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.header_home, null);
        mTvDate = view.findViewById(R.id.tv_date);
        return view;
    }

    protected void initData() {
        List<Calendar> schemes = new ArrayList<>();
        int year = mCalendarView.getCurYear();
        int month = mCalendarView.getCurMonth();

        schemes.add(getSchemeCalendar(year, month, 3, 0xFF40db25, "假"));
        schemes.add(getSchemeCalendar(year, month, 6, 0xFFe69138, "事"));
        schemes.add(getSchemeCalendar(year, month, 10, 0xFFdf1356, "议"));
        schemes.add(getSchemeCalendar(year, month, 11, 0xFFedc56d, "记"));
        schemes.add(getSchemeCalendar(year, month, 14, 0xFFedc56d, "记"));
        schemes.add(getSchemeCalendar(year, month, 15, 0xFFaacc44, "假"));
        schemes.add(getSchemeCalendar(year, month, 18, 0xFFbc13f0, "记"));
        schemes.add(getSchemeCalendar(year, month, 25, 0xFF13acf0, "假"));
        schemes.add(getSchemeCalendar(year, month, 27, 0xFF13acf0, "多"));


        /*
         * 此方法现在弃用，但不影响原来的效果，原因：数据量大时 size()>10000 ，遍历性能太差，超过Android限制的16ms响应，造成卡顿
         * 现在推荐使用 setSchemeDate(Map<String, Calendar> mSchemeDates)，Map查找性能非常好，经测试，50000以上数据，1ms解决
         */
        mCalendarView.setSchemeDate(schemes);


        Map<String, Calendar> map = new HashMap<>();
        map.put(getSchemeCalendar(year, month, 3, 0xFF40db25, "假").toString(),
                getSchemeCalendar(year, month, 3, 0xFF40db25, "假"));
        map.put(getSchemeCalendar(year, month, 6, 0xFFe69138, "事").toString(),
                getSchemeCalendar(year, month, 6, 0xFFe69138, "事"));
        map.put(getSchemeCalendar(year, month, 9, 0xFFdf1356, "议").toString(),
                getSchemeCalendar(year, month, 9, 0xFFdf1356, "议"));
        map.put(getSchemeCalendar(year, month, 13, 0xFFedc56d, "记").toString(),
                getSchemeCalendar(year, month, 13, 0xFFedc56d, "记"));
        map.put(getSchemeCalendar(year, month, 14, 0xFFedc56d, "记").toString(),
                getSchemeCalendar(year, month, 14, 0xFFedc56d, "记"));
        map.put(getSchemeCalendar(year, month, 15, 0xFFaacc44, "假").toString(),
                getSchemeCalendar(year, month, 15, 0xFFaacc44, "假"));
        map.put(getSchemeCalendar(year, month, 18, 0xFFbc13f0, "记").toString(),
                getSchemeCalendar(year, month, 18, 0xFFbc13f0, "记"));
        map.put(getSchemeCalendar(year, month, 25, 0xFF13acf0, "假").toString(),
                getSchemeCalendar(year, month, 25, 0xFF13acf0, "假"));
        map.put(getSchemeCalendar(year, month, 27, 0xFF13acf0, "多").toString(),
                getSchemeCalendar(year, month, 27, 0xFF13acf0, "多"));
        //此方法在巨大的数据量上不影响遍历性能，推荐使用
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
        calendar.addScheme(0xFF008800, "假");
        calendar.addScheme(0xFF008800, "节");
        return calendar;
    }

    @Override
    protected void setListener() {
        mCalendarView.setOnMonthChangeListener(new CalendarView.OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month) {
                String title = year+"年"+month+"月";
                ((MainActivity)getActivity()).setTitle(title);
            }
        });
        mCalendarView.setOnCalendarSelectListener(new CalendarView.OnCalendarSelectListener() {
            @Override
            public void onCalendarOutOfRange(Calendar calendar) {

            }

            @Override
            public void onCalendarSelect(Calendar calendar, boolean isClick) {
                java.util.Calendar calendar1 = java.util.Calendar.getInstance();
                Date date = Helper.string2Date(calendar.toString(),"yyyyMMdd");
                calendar1.setTime(date);
                LunarUtil lunar = new LunarUtil(calendar1);
                if (calendar.isCurrentDay()){
                    mTvDate.setText("今天  农历  "+ lunar.toString());
                }else{
                    mTvDate.setText("农历  "+lunar.toString());
                }
            }
        });
    }

    public static List<MultipleItem> getMultipleItemData() {
        List<MultipleItem> list = new ArrayList<>();
        for (int i = 0; i <= 4; i++) {
            list.add(new MultipleItem(MultipleItem.DAY));
            list.add(new MultipleItem(MultipleItem.AM));
        }
        return list;
    }

    public void toToday(){
        if (mCalendarView!=null){
            mCalendarView.scrollToCurrent();
        }
    }

}
