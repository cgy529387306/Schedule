package com.android.mb.schedule.view;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.mb.schedule.R;
import com.android.mb.schedule.adapter.ScheduleRelateAdapter;
import com.android.mb.schedule.adapter.SectionAdapter;
import com.android.mb.schedule.adapter.UserScheduleAdapter;
import com.android.mb.schedule.base.BaseMvpActivity;
import com.android.mb.schedule.entitys.MySection;
import com.android.mb.schedule.entitys.RelatedBean;
import com.android.mb.schedule.entitys.ScheduleBean;
import com.android.mb.schedule.entitys.ScheduleData;
import com.android.mb.schedule.presenter.RelatedPresenter;
import com.android.mb.schedule.presenter.WeekPresenter;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.NavigationHelper;
import com.android.mb.schedule.view.interfaces.IRelatedView;
import com.android.mb.schedule.view.interfaces.IWeekView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我（或者他人）的日程
 * Created by cgy on 2018\8\20 0020.
 */

public class ScheduleUserActivity extends BaseMvpActivity<WeekPresenter,IWeekView> implements IWeekView,
        View.OnClickListener,SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener,BaseQuickAdapter.OnItemClickListener{
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private SectionAdapter mAdapter;
    private Calendar mCurCalendar;
    private boolean mIsFirst = true;
    @Override
    protected void loadIntent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.common_recycleview;
    }

    @Override
    protected void initTitle() {
        setTitleText("我的日程");
    }

    @Override
    protected void bindViews() {
        mCurCalendar = (Calendar) Calendar.getInstance().clone();
        mCurCalendar.set(Calendar.DAY_OF_WEEK, 1);
        mCurCalendar.getTime();
        mSwipeRefreshLayout = findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimaryDark);
        mRecyclerView =  findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mAdapter = new SectionAdapter(R.layout.item_section_content,R.layout.item_section_head,new ArrayList());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mSwipeRefreshLayout.setRefreshing(true);
        getListFormServer();
    }

    private void getListFormServer(){
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("date",Helper.date2String(mCurCalendar.getTime(),"yyyy-MM-dd"));
        mPresenter.getWeekSchedule(requestMap);
    }

    @Override
    protected void setListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MySection mySection = mAdapter.getItem(position);
                if (!mySection.isHeader){
                    Bundle bundle = new Bundle();
                    bundle.putLong("id",mySection.t.getId());
                    NavigationHelper.startActivity(ScheduleUserActivity.this,ScheduleDetailActivity.class,bundle,false);
                }
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(ScheduleUserActivity.this, "onItemChildClick" + position, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
    }


    @Override
    protected WeekPresenter createPresenter() {
        return new WeekPresenter();
    }

    @Override
    public void onRefresh() {
        mIsFirst = true;
        mCurCalendar = (Calendar) Calendar.getInstance().clone();
        mCurCalendar.set(Calendar.DAY_OF_WEEK, 1);
        mCurCalendar.getTime();
        getListFormServer();
    }

    @Override
    public void onLoadMoreRequested() {
        mCurCalendar = Calendar.getInstance();
        mCurCalendar.add(Calendar.DATE, - 7);
        getListFormServer();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
    }

    @Override
    public void getSuccess(List<ScheduleData> result) {
        if (result!=null){
            if (mIsFirst){
                //首页
                mSwipeRefreshLayout.setRefreshing(false);
                mAdapter.setNewData(getSectionData(result));
                mAdapter.setEmptyView(R.layout.empty_schedule, (ViewGroup) mRecyclerView.getParent());
            }else{
                if (Helper.isEmpty(result)){
                    mAdapter.loadMoreEnd();
                }else{
                    mAdapter.addData(getSectionData(result));
                    mAdapter.loadMoreComplete();
                }
            }
        }
    }

    public static List<MySection> getSectionData(List<ScheduleData> dataList) {
        List<MySection> list = new ArrayList<>();
        for (ScheduleData scheduleData:dataList) {
            list.add(new MySection(true, scheduleData.getDate()));
            for (ScheduleBean scheduleBean:scheduleData.getList()){
                list.add(new MySection(scheduleBean));
            }
        }
        return list;
    }
}
