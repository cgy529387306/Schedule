package com.android.mb.schedule.view;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.mb.schedule.R;
import com.android.mb.schedule.adapter.ScheduleRelateAdapter;
import com.android.mb.schedule.adapter.ScheduleUnderAdapter;
import com.android.mb.schedule.base.BaseMvpActivity;
import com.android.mb.schedule.constants.ProjectConstants;
import com.android.mb.schedule.entitys.RelatedBean;
import com.android.mb.schedule.presenter.MyUnderPresenter;
import com.android.mb.schedule.presenter.RelatedPresenter;
import com.android.mb.schedule.rxbus.Events;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.NavigationHelper;
import com.android.mb.schedule.view.interfaces.IMyUnderView;
import com.android.mb.schedule.view.interfaces.IRelatedView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.functions.Action1;

/**
 * 下属日程
 * Created by cgy on 2018\8\20 0020.
 */

public class ScheduleUnderActivity extends BaseMvpActivity<MyUnderPresenter,IMyUnderView> implements IMyUnderView,
        View.OnClickListener,SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener,BaseQuickAdapter.OnItemClickListener{
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private ScheduleUnderAdapter mAdapter;
    private int mCurrentPage = 1;
    private long mUserId;
    private String mUserName;
    @Override
    protected void loadIntent() {
        mUserId = getIntent().getLongExtra("id",0);
        mUserName = getIntent().getStringExtra("name");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.common_recycleview;
    }

    @Override
    protected void initTitle() {
        setTitleText(mUserName+"的日程");
    }

    @Override
    protected void bindViews() {
        mSwipeRefreshLayout = findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimaryDark);
        mRecyclerView =  findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new ScheduleUnderAdapter(R.layout.item_schedule_under,new ArrayList());
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
        requestMap.put("uid",mUserId);
        mPresenter.getUnder(requestMap);
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
    public void getSuccess(List<RelatedBean> result) {
        if (result!=null){
            if (mCurrentPage == 1){
                //首页
                mSwipeRefreshLayout.setRefreshing(false);
                mAdapter.setNewData(result);
                mAdapter.setEmptyView(R.layout.empty_schedule, (ViewGroup) mRecyclerView.getParent());
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

    @Override
    protected MyUnderPresenter createPresenter() {
        return new MyUnderPresenter();
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
        if (mAdapter.getItem(position)!=null){
            Bundle bundle = new Bundle();
            bundle.putLong("id",mAdapter.getItem(position).getId());
            NavigationHelper.startActivity(ScheduleUnderActivity.this,ScheduleDetailActivity.class,bundle,false);
        }
    }
}
