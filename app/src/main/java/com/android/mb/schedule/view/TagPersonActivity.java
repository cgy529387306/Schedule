package com.android.mb.schedule.view;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.mb.schedule.R;
import com.android.mb.schedule.adapter.TagAdapter;
import com.android.mb.schedule.base.BaseActivity;
import com.android.mb.schedule.entitys.UserBean;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.NavigationHelper;
import com.android.mb.schedule.widget.MyDividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 标签
 * Created by cgy on 2018\8\20 0020.
 */

public class TagPersonActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener{
    private RecyclerView mRecyclerView;
    private TagAdapter mAdapter;
    private List<UserBean> mDataList = new ArrayList<>();
    private String mTagName;
    @Override
    protected void loadIntent() {
        mTagName = getIntent().getStringExtra("tagName");
        mDataList = (List<UserBean>) getIntent().getSerializableExtra("manList");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tag_person;
    }

    @Override
    protected void initTitle() {
        setTitleText(Helper.isEmpty(mTagName)?"标签":mTagName);
    }

    @Override
    protected void bindViews() {
        mRecyclerView =  findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        mAdapter = new TagAdapter(R.layout.item_tag_person,mDataList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.empty_data, (ViewGroup) mRecyclerView.getParent());
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
    }



    @Override
    protected void setListener() {
        mAdapter.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (mAdapter.getItem(position)!=null){
            UserBean userBean = mAdapter.getItem(position);
            Bundle bundle = new Bundle();
            bundle.putLong("id",userBean.getId());
            bundle.putString("name",userBean.getNickname());
            NavigationHelper.startActivity(TagPersonActivity.this, ScheduleUnderActivity.class,bundle,false);
        }
    }

}
