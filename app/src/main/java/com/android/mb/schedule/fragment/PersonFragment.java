package com.android.mb.schedule.fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.mb.schedule.R;
import com.android.mb.schedule.adapter.PersonAdapter;
import com.android.mb.schedule.adapter.ScheduleRelateAdapter;
import com.android.mb.schedule.base.BaseFragment;
import com.android.mb.schedule.entitys.UserBean;
import com.android.mb.schedule.utils.Helper;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 常用联系人
 * Created by cgy on 16/7/18.
 */
public class PersonFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private PersonAdapter mAdapter;
    private List<UserBean> mDataList = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.frg_person;
    }

    @Override
    protected void bindViews(View view) {
        mRecyclerView =  view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        mAdapter = new PersonAdapter(R.layout.item_person,mDataList);
//        mAdapter.setEmptyView(R.layout.empty_data, (ViewGroup) mRecyclerView.getParent());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void processLogic() {

    }

    @Override
    protected void setListener() {

    }

    public void setDataList(List<UserBean> list){
        mDataList = list;
        mAdapter.setNewData(mDataList);
    }


}
