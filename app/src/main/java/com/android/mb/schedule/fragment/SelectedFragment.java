package com.android.mb.schedule.fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.mb.schedule.R;
import com.android.mb.schedule.adapter.SelectAdapter;
import com.android.mb.schedule.base.BaseFragment;
import com.android.mb.schedule.constants.ProjectConstants;
import com.android.mb.schedule.rxbus.Events;
import com.android.mb.schedule.view.SelectPersonActivity;
import com.android.mb.schedule.widget.MyDividerItemDecoration;

import rx.functions.Action1;


/**
 * 已选联系人
 * Created by cgy on 16/7/18.
 */
public class SelectedFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private SelectAdapter mAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.frg_person;
    }

    @Override
    protected void bindViews(View view) {
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        mAdapter = new SelectAdapter(R.layout.item_person,SelectPersonActivity.mSelectList);
        mAdapter.setEmptyView(R.layout.empty_data, (ViewGroup) mRecyclerView.getParent());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void processLogic() {

    }

    @Override
    protected void setListener() {
        regiestEvent(ProjectConstants.EVENT_UPDATE_SELECT, new Action1<Events<?>>() {
            @Override
            public void call(Events<?> events) {
                refreshData();
            }
        });
    }


    private void refreshData() {
        if (mAdapter!=null){
            mAdapter.setNewData(SelectPersonActivity.mSelectList);
        }
    }
}
