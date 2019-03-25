package com.android.mb.schedule.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.mb.schedule.R;
import com.android.mb.schedule.adapter.AddressAdapter;
import com.android.mb.schedule.base.BaseMvpActivity;
import com.android.mb.schedule.constants.ProjectConstants;
import com.android.mb.schedule.entitys.CurrentUser;
import com.android.mb.schedule.entitys.TreeData;
import com.android.mb.schedule.presenter.AddressPresenter;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.JsonHelper;
import com.android.mb.schedule.utils.PreferencesHelper;
import com.android.mb.schedule.utils.ProjectHelper;
import com.android.mb.schedule.view.interfaces.IAddressView;
import com.android.mb.schedule.widget.MyDividerItemDecoration;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择地点
 * Created by cgy on 2018\8\20 0020.
 */

public class SelectAddressActivity extends BaseMvpActivity<AddressPresenter,IAddressView> implements IAddressView {

    private EditText mEtAddress;
    private AddressAdapter mAdapter;
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
    protected void onRightAction(View view) {
        super.onRightAction(view);
        ProjectHelper.disableViewDoubleClick(view);
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
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        mAdapter = new AddressAdapter(R.layout.item_address,new ArrayList());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        getDataFromLocal();
        mPresenter.getAddress();
    }

    @Override
    protected void setListener() {
        mAdapter.setOnMyItemClickListener(new AddressAdapter.OnMyItemClickListener() {
            @Override
            public void onItemClick(String item) {
                Intent intent = new Intent();
                intent.putExtra("address",item);
                setResult(RESULT_OK,intent);
                finish();
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
            PreferencesHelper.getInstance().putString(ProjectConstants.KEY_ADDRESS_LIST+ CurrentUser.getInstance().getId(), JsonHelper.toJson(result));
            mAdapter.setNewData(result);
            mAdapter.setEmptyView(R.layout.empty_data, (ViewGroup) mRecyclerView.getParent());
        }
    }

    private void getDataFromLocal(){
        String addressListStr = PreferencesHelper.getInstance().getString(ProjectConstants.KEY_ADDRESS_LIST+CurrentUser.getInstance().getId());
        List<String> addressList = JsonHelper.fromJson(addressListStr,new TypeToken<List<String>>(){}.getType());
        if (addressList!=null){
            mAdapter.setNewData(addressList);
            mAdapter.setEmptyView(R.layout.empty_data, (ViewGroup) mRecyclerView.getParent());
        }
    }
}
