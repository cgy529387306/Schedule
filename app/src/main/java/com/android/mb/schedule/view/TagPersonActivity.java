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
import com.android.mb.schedule.adapter.AllTagAdapter;
import com.android.mb.schedule.adapter.TagAdapter;
import com.android.mb.schedule.base.BaseMvpActivity;
import com.android.mb.schedule.constants.ProjectConstants;
import com.android.mb.schedule.entitys.RelatedBean;
import com.android.mb.schedule.entitys.UserBean;
import com.android.mb.schedule.presenter.RelatedPresenter;
import com.android.mb.schedule.presenter.TagPersonPresenter;
import com.android.mb.schedule.rxbus.Events;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.view.interfaces.IRelatedView;
import com.android.mb.schedule.view.interfaces.ITagPersonView;
import com.android.mb.schedule.widget.MyDividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.functions.Action1;

/**
 * 标签
 * Created by cgy on 2018\8\20 0020.
 */

public class TagPersonActivity extends BaseMvpActivity<TagPersonPresenter,ITagPersonView> implements ITagPersonView,
        View.OnClickListener,SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener,BaseQuickAdapter.OnItemClickListener{
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private TagAdapter mAdapter;
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
        setTitleText("标签");
    }

    @Override
    protected void bindViews() {
        mSwipeRefreshLayout = findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimaryDark);
        mRecyclerView =  findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(mContext, LinearLayoutManager.VERTICAL,10));
        mAdapter = new TagAdapter(R.layout.item_tag_person,new ArrayList());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mSwipeRefreshLayout.setRefreshing(true);
        getListFormServer();
    }

    private void getListFormServer(){
//        Map<String, Object> requestMap = new HashMap<>();
//        requestMap.put("page",mCurrentPage);
//        mPresenter.getRelated(requestMap);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int count = mAdapter.getItemCount();
                List<String> dataList = new ArrayList<>();
                for (int i=count;i<count+20;i++){
                    dataList.add(Helper.date2String(new Date()));
                }
                if (mCurrentPage == 1){
                    //首页
                    mSwipeRefreshLayout.setRefreshing(false);
                    mAdapter.setNewData(dataList);
                    mAdapter.setEmptyView(R.layout.empty_data, (ViewGroup) mRecyclerView.getParent());
                }else{
                    if (Helper.isEmpty(dataList)){
                        mAdapter.loadMoreEnd();
                    }else{
                        mAdapter.addData(dataList);
                        mAdapter.loadMoreComplete();
                    }
                }
            }
        },1000);
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
    protected TagPersonPresenter createPresenter() {
        return new TagPersonPresenter();
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
    public void getPersonSuccess(List<UserBean> result) {

    }
}
