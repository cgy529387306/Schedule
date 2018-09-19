package com.android.mb.schedule.view;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.mb.schedule.R;
import com.android.mb.schedule.adapter.ScheduleShareAdapter;
import com.android.mb.schedule.adapter.ScheduleShareOtherAdapter;
import com.android.mb.schedule.base.BaseMvpActivity;
import com.android.mb.schedule.entitys.RelatedBean;
import com.android.mb.schedule.entitys.ShareBean;
import com.android.mb.schedule.presenter.MySharePresenter;
import com.android.mb.schedule.presenter.OtherSharePresenter;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.NavigationHelper;
import com.android.mb.schedule.view.interfaces.IMyShareView;
import com.android.mb.schedule.view.interfaces.IOtherShareView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分享给我的日程
 * Created by cgy on 2018\8\20 0020.
 */

public class ScheduleShareOtherActivity extends BaseMvpActivity<OtherSharePresenter,IOtherShareView> implements IOtherShareView, View.OnClickListener
        ,SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener,BaseQuickAdapter.OnItemClickListener{
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private ScheduleShareOtherAdapter mAdapter;
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
        setTitleText("他人分享的日程");
    }

    @Override
    protected void bindViews() {
        mSwipeRefreshLayout = findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimaryDark);
        mRecyclerView =  findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new ScheduleShareOtherAdapter(R.layout.item_schedule_share_other,new ArrayList());
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
        mPresenter.getOtherShare(requestMap);
    }

    @Override
    protected void setListener() {
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
                mAdapter.setEmptyView(R.layout.empty_share, (ViewGroup) mRecyclerView.getParent());
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
    protected OtherSharePresenter createPresenter() {
        return new OtherSharePresenter();
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
            NavigationHelper.startActivity(ScheduleShareOtherActivity.this,ScheduleDetailActivity.class,bundle,false);
        }
    }
}
