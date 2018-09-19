package com.android.mb.schedule.view;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.mb.schedule.R;
import com.android.mb.schedule.adapter.ScheduleRelateAdapter;
import com.android.mb.schedule.adapter.ScheduleShareAdapter;
import com.android.mb.schedule.base.BaseActivity;
import com.android.mb.schedule.base.BaseMvpActivity;
import com.android.mb.schedule.entitys.ShareData;
import com.android.mb.schedule.presenter.MySharePresenter;
import com.android.mb.schedule.presenter.RelatedPresenter;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.NavigationHelper;
import com.android.mb.schedule.view.interfaces.IMyShareView;
import com.android.mb.schedule.view.interfaces.IRelatedView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018\8\20 0020.
 */

public class ScheduleShareActivity extends BaseMvpActivity<MySharePresenter,IMyShareView> implements IMyShareView, View.OnClickListener{
    private RecyclerView mRecyclerView;
    private ScheduleShareAdapter mAdapter;
    private int mCurrentPage = 0;

    @Override
    protected void loadIntent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_relate_schedule;
    }

    @Override
    protected void initTitle() {
        setTitleText("我分享的日程");
    }

    @Override
    protected void bindViews() {
        mRecyclerView =  findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new ScheduleShareAdapter(R.layout.item_schedule_share,getData());
        mAdapter.setEmptyView(R.layout.empty_share, (ViewGroup) mRecyclerView.getParent());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mPresenter.getMyShare();
    }

    @Override
    protected void setListener() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                NavigationHelper.startActivity(ScheduleShareActivity.this,ScheduleDetailActivity.class,null,false);
            }
        });
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mCurrentPage >= 10) {
                            //数据全部加载完毕
                            mAdapter.loadMoreEnd();
                        } else {
                            mAdapter.addData(getData());
                            mAdapter.loadMoreComplete();
                            mCurrentPage++;
                        }
                    }

                }, 1000);

            }
        },mRecyclerView);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
    }

    public List getData() {
        List<String> dataList = new ArrayList<>();
        for (int i=0;i<10;i++){
            dataList.add(Helper.date2String(new Date(),"MM-dd"));
        }
        return dataList;
    }

    @Override
    public void getSuccess(List<ShareData> result) {

    }

    @Override
    protected MySharePresenter createPresenter() {
        return new MySharePresenter();
    }
}
