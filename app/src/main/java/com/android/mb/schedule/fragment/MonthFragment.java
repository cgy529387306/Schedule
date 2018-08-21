package com.android.mb.schedule.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.adapter.TestAdapter;
import com.android.mb.schedule.base.BaseFragment;
import com.android.mb.schedule.view.MainActivity;
import com.necer.ncalendar.calendar.NCalendar;
import com.necer.ncalendar.listener.OnCalendarChangedListener;

import org.joda.time.LocalDate;


/**
 * 月视图
 * Created by cgy on 16/7/18.
 */
public class MonthFragment extends BaseFragment implements OnCalendarChangedListener {
    private NCalendar mNCalendar;
    private RecyclerView mRecyclerView;
    @Override
    protected int getLayoutId() {
        return R.layout.frg_month;
    }

    @Override
    protected void bindViews(View view) {
        mNCalendar = view.findViewById(R.id.myCalendar);
        mRecyclerView = view.findViewById(R.id.recyclerView);
    }

    @Override
    protected void processLogic() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        TestAdapter testAdapter = new TestAdapter(getActivity());
        mRecyclerView.setAdapter(testAdapter);
        mNCalendar.setOnCalendarChangedListener(this);
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onCalendarChanged(LocalDate date) {
        if (getActivity()!=null && getActivity() instanceof MainActivity){
            ((MainActivity)getActivity()).setTitle(date.getYear() + "年" + date.getMonthOfYear() + "月" + date.getDayOfMonth() + "日");
        }
    }
}
