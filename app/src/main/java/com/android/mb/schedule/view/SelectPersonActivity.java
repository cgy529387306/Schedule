package com.android.mb.schedule.view;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.android.mb.schedule.R;
import com.android.mb.schedule.adapter.AddressAdapter;
import com.android.mb.schedule.base.BaseMvpActivity;
import com.android.mb.schedule.presenter.AddressPresenter;
import com.android.mb.schedule.presenter.PersonPresenter;
import com.android.mb.schedule.view.interfaces.IAddressView;
import com.android.mb.schedule.view.interfaces.IPersonView;
import com.android.mb.schedule.view.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018\8\20 0020.
 */

public class SelectPersonActivity extends BaseMvpActivity<PersonPresenter,IPersonView> implements IPersonView {

    private EditText mEtAddress;
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
        setTitleText("人员选择");
        setRightText("保存");
    }

    @Override
    protected void bindViews() {
        mEtAddress = findViewById(R.id.et_address);
        mRecyclerViewAddress = findViewById(R.id.recyclerView_address);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this );
        mRecyclerViewAddress.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerViewAddress.setItemAnimator( new DefaultItemAnimator());
        mList = new ArrayList<>();
        mList.add("A");
        mList.add("B");
        mList.add("C");
        mList.add("D");
        mAddressAdapter = new AddressAdapter(SelectPersonActivity.this,mList);
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
        mPresenter.getOfficeList();
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected PersonPresenter createPresenter() {
        return new PersonPresenter();
    }

    @Override
    public void getSuccess() {

    }
}
