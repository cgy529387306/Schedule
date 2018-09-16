package com.android.mb.schedule.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.android.mb.schedule.R;
import com.android.mb.schedule.adapter.AddressAdapter;
import com.android.mb.schedule.base.BaseMvpActivity;
import com.android.mb.schedule.presenter.AddressPresenter;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.view.interfaces.IAddressView;
import com.android.mb.schedule.view.interfaces.OnItemClickListener;

import java.util.List;

/**
 * Created by Administrator on 2018\8\20 0020.
 */

public class SelectAddressActivity extends BaseMvpActivity<AddressPresenter,IAddressView> implements IAddressView {

    private EditText mEtAddress;
    private AddressAdapter mAddressAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void loadIntent() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_address;
    }

    @Override
    protected void initTitle() {
        setTitleText("地点选择");
        setRightText("保存");
    }

    @Override
    protected void onRightAction() {
        super.onRightAction();
        String address = mEtAddress.getText().toString().trim();
        if (Helper.isEmpty(address)){
            showToastMessage("请输入地址");
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("address",address);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    protected void bindViews() {
        mEtAddress = findViewById(R.id.et_address);
        mRecyclerView = findViewById(R.id.recyclerView_address);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this ));
        mRecyclerView.setItemAnimator( new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mAddressAdapter = new AddressAdapter(SelectAddressActivity.this);
        mRecyclerView.setAdapter(mAddressAdapter);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mPresenter.getAddress();
    }

    @Override
    protected void setListener() {
        mAddressAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                if (mAddressAdapter.getDataList().size()>postion){
                    String address = mAddressAdapter.getDataList().get(postion);
                    Intent intent = new Intent();
                    intent.putExtra("address",address);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }

    @Override
    protected AddressPresenter createPresenter() {
        return new AddressPresenter();
    }

    @Override
    public void getSuccess(List<String> result) {
        if (result!=null){
            mAddressAdapter.setDataList(result);
        }
    }
}
