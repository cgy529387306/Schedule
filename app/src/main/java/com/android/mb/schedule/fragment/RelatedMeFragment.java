package com.android.mb.schedule.fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.android.mb.schedule.R;
import com.android.mb.schedule.adapter.ScheduleRelateAdapter;
import com.android.mb.schedule.base.BaseFragment;
import com.android.mb.schedule.utils.Helper;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 与我相关
 * Created by cgy on 16/7/18.
 */
public class RelatedMeFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private ScheduleRelateAdapter mAdapter;
    private int mCurrentPage = 0;
    @Override
    protected int getLayoutId() {
        return R.layout.frg_me;
    }

    @Override
    protected void bindViews(View view) {
        mRecyclerView =  view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new ScheduleRelateAdapter(R.layout.item_schedule_relate,getData());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void processLogic() {

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


    public List getData() {
        List<String> dataList = new ArrayList<>();
        for (int i=0;i<10;i++){
            dataList.add(Helper.date2String(new Date(),"MM-dd"));
        }
        return dataList;
    }
}
