package com.android.mb.schedule.view;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.mb.schedule.R;
import com.android.mb.schedule.adapter.AddressAdapter;
import com.android.mb.schedule.adapter.RingAdapter;
import com.android.mb.schedule.base.BaseActivity;
import com.android.mb.schedule.entitys.RingBean;
import com.android.mb.schedule.view.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018\8\20 0020.
 */

public class AddressActivity extends BaseActivity {

    private List<String> mList;
    private AddressAdapter mAddressAdapter;
    private RecyclerView mRecyclerViewAddress;

    @Override
    protected void loadIntent() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address;
    }

    @Override
    protected void initTitle() {
        setTitleText("地址选择");
        setRightText("保存");
    }

    @Override
    protected void bindViews() {
        mRecyclerViewAddress = findViewById(R.id.recyclerView_address);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this );
        //设置布局管理器
        mRecyclerViewAddress.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper. VERTICAL);
        //设置增加或删除条目的动画
        mRecyclerViewAddress.setItemAnimator( new DefaultItemAnimator());
        mList = new ArrayList<>();
        mList.add("A");
        mList.add("B");
        mList.add("C");
        mList.add("D");
        mAddressAdapter = new AddressAdapter(AddressActivity.this,mList);
        mRecyclerViewAddress.setAdapter(mAddressAdapter);
        mAddressAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                mAddressAdapter.setCurrentIndex(postion);
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
    }

    @Override
    protected void setListener() {
    }

}
