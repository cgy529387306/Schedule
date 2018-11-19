package com.android.mb.schedule.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.mb.schedule.R;
import com.android.mb.schedule.adapter.WeekReportAdapter;
import com.android.mb.schedule.base.BaseMvpActivity;
import com.android.mb.schedule.constants.ProjectConstants;
import com.android.mb.schedule.entitys.ReportData;
import com.android.mb.schedule.presenter.WeekReportPresenter;
import com.android.mb.schedule.rxbus.Events;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.view.interfaces.IWeekReprtView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.functions.Action1;

/**
 * 周报告
 * Created by cgy on 2018\8\20 0020.
 */

public class WeekReportActivity extends BaseMvpActivity<WeekReportPresenter,IWeekReprtView> implements IWeekReprtView,
        View.OnClickListener,SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener,BaseQuickAdapter.OnItemClickListener{
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private WeekReportAdapter mAdapter;
    private int mCurrentPage = 1;
    @Override
    protected void loadIntent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.common_recycleview;
    }

    @Override
    protected void initTitle() {
        setTitleText("一周小结");
    }

    @Override
    protected void bindViews() {
        mSwipeRefreshLayout = findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimaryDark);
        mRecyclerView =  findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new WeekReportAdapter(R.layout.item_week_report,new ArrayList());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mSwipeRefreshLayout.setRefreshing(true);
        getListFormServer();
    }

    private void getListFormServer(){
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("page",mCurrentPage);
        mPresenter.getReport(requestMap);
    }


    @Override
    protected void setListener() {
        regiestEvent(ProjectConstants.EVENT_UPDATE_SCHEDULE_LIST, new Action1<Events<?>>() {
            @Override
            public void call(Events<?> events) {
                mCurrentPage = 1;
                getListFormServer();
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnLoadMoreListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
    }


    @Override
    protected WeekReportPresenter createPresenter() {
        return new WeekReportPresenter();
    }

    @Override
    public void onRefresh() {
        mCurrentPage = 1;
        getListFormServer();
    }

    @Override
    public void onLoadMoreRequested() {
        mCurrentPage++;
        getListFormServer();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void getSuccess(List<ReportData> result) {
        if (result!=null){
            if (mCurrentPage == 1){
                //首页
                mSwipeRefreshLayout.setRefreshing(false);
                mAdapter.setNewData(result);
                mAdapter.setEmptyView(R.layout.empty_schedule, (ViewGroup) mRecyclerView.getParent());
                if (result.size()<ProjectConstants.PAGE_SIZE){
                    mAdapter.loadMoreEnd();
                }
            }else{
                if (Helper.isEmpty(result)){
                    mAdapter.loadMoreEnd();
                }else{
                    mAdapter.addData(result);
                    mAdapter.loadMoreComplete();
                }
            }
        }
    }
}
