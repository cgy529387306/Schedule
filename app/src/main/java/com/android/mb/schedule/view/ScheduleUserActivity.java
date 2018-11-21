package com.android.mb.schedule.view;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.mb.schedule.R;
import com.android.mb.schedule.adapter.ScheduleMineAdapter;
import com.android.mb.schedule.base.BaseMvpActivity;
import com.android.mb.schedule.constants.ProjectConstants;
import com.android.mb.schedule.entitys.CurrentUser;
import com.android.mb.schedule.entitys.MyScheduleBean;
import com.android.mb.schedule.presenter.MinePresenter;
import com.android.mb.schedule.rxbus.Events;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.NavigationHelper;
import com.android.mb.schedule.utils.PreferencesHelper;
import com.android.mb.schedule.view.interfaces.IMineView;
import com.android.mb.schedule.widget.MyDividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.functions.Action1;

/**
 * 我（或者他人）的日程
 * Created by cgy on 2018\8\20 0020.
 */

public class ScheduleUserActivity extends BaseMvpActivity<MinePresenter,IMineView> implements IMineView,
        View.OnClickListener,SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener,BaseQuickAdapter.OnItemClickListener{
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private ScheduleMineAdapter mAdapter;
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
        setTitleText("我的日程");
        setRightText("历史日程");
    }

    @Override
    protected void onRightAction() {
        super.onRightAction();
        NavigationHelper.startActivity(ScheduleUserActivity.this,ScheduleUserHistoryActivity.class,null,false);
    }

    @Override
    protected void bindViews() {
        PreferencesHelper.getInstance().putBoolean(ProjectConstants.KEY_HAS_NEW_SCHEDULE+CurrentUser.getInstance().getId(), false);
        mSwipeRefreshLayout = findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimaryDark);
        mRecyclerView =  findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        mAdapter = new ScheduleMineAdapter(R.layout.item_schedule_mine,new ArrayList());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mSwipeRefreshLayout.setRefreshing(true);
        getListFormServer();
    }

    private void getListFormServer(){
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("type",1);
        requestMap.put("page",mCurrentPage);
        mPresenter.getMine(requestMap);
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
    protected MinePresenter createPresenter() {
        return new MinePresenter();
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
            NavigationHelper.startActivity(ScheduleUserActivity.this,ScheduleDetailActivity.class,bundle,false);
        }
    }



    @Override
    public void getSuccess(List<MyScheduleBean> result) {
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
