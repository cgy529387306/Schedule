package com.android.mb.schedule.view;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.mb.schedule.R;
import com.android.mb.schedule.adapter.ScheduleShareAdapter;
import com.android.mb.schedule.adapter.SectionAdapter;
import com.android.mb.schedule.adapter.UserScheduleAdapter;
import com.android.mb.schedule.base.BaseMvpActivity;
import com.android.mb.schedule.entitys.MySection;
import com.android.mb.schedule.entitys.ScheduleRequest;
import com.android.mb.schedule.presenter.MySharePresenter;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.view.interfaces.IMyShareView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018\8\20 0020.
 */

public class UserScheduleActivity extends BaseMvpActivity<MySharePresenter,IMyShareView> implements IMyShareView,  View.OnClickListener{
    private RecyclerView mRecyclerView;
    private SectionAdapter mAdapter;
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
        setTitleText("我的日程");
    }

    @Override
    protected void bindViews() {
        mRecyclerView =  findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new SectionAdapter(R.layout.item_section_content,R.layout.item_section_head,getData());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mPresenter.getMyShare();
    }

    @Override
    protected void setListener() {
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
        List<MySection> list = new ArrayList<>();
        for (int i=0;i<10;i++){
            list.add(new MySection(true, "Section"+i, true));
            list.add(new MySection(new ScheduleRequest()));
            list.add(new MySection(new ScheduleRequest()));
        }
        return list;
    }

    @Override
    public void getSuccess() {

    }

    @Override
    protected MySharePresenter createPresenter() {
        return new MySharePresenter();
    }
}
