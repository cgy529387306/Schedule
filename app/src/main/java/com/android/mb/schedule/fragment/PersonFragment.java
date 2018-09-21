package com.android.mb.schedule.fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.mb.schedule.R;
import com.android.mb.schedule.adapter.PersonAdapter;
import com.android.mb.schedule.base.BaseFragment;
import com.android.mb.schedule.constants.ProjectConstants;
import com.android.mb.schedule.entitys.UserBean;
import com.android.mb.schedule.rxbus.Events;
import com.android.mb.schedule.utils.ProjectHelper;
import com.android.mb.schedule.view.SelectPersonActivity;
import com.android.mb.schedule.widget.MyDividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;


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
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        mAdapter = new PersonAdapter(R.layout.item_person,mDataList);
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
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                UserBean userBean = mAdapter.getItem(position);
                userBean.setSelect(!userBean.isSelect());
                mAdapter.setData(position,userBean);
                if (userBean.isSelect()){
                    SelectPersonActivity.mSelectList.add(userBean);
                }else{
                    SelectPersonActivity.mSelectList.remove(userBean);
                }
            }
        });
    }

    public void setDataList(List<UserBean> list){
        mDataList = list;
        mAdapter.setEmptyView(R.layout.empty_data, (ViewGroup) mRecyclerView.getParent());
        refreshData();
    }


    private void refreshData() {
        if (mAdapter!=null && mDataList!=null){
            for (UserBean userBean:mDataList) {
                userBean.setSelect(ProjectHelper.getSelectIdList().contains(userBean.getId()));
            }
            mAdapter.setNewData(mDataList);
        }
    }
}
