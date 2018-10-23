package com.android.mb.schedule.view;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.mb.schedule.R;
import com.android.mb.schedule.adapter.ScheduleMineAdapter;
import com.android.mb.schedule.base.BaseMvpActivity;
import com.android.mb.schedule.constants.ProjectConstants;
import com.android.mb.schedule.entitys.MyScheduleBean;
import com.android.mb.schedule.entitys.SearchBean;
import com.android.mb.schedule.entitys.UserBean;
import com.android.mb.schedule.presenter.MinePresenter;
import com.android.mb.schedule.presenter.SearchPresenter;
import com.android.mb.schedule.rxbus.Events;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.NavigationHelper;
import com.android.mb.schedule.view.interfaces.IMineView;
import com.android.mb.schedule.view.interfaces.ISearchView;
import com.android.mb.schedule.widget.MyDividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.functions.Action1;

/**
 * 我（或者他人）的日程
 * Created by cgy on 2018\8\20 0020.
 */

public class SearchPeopleActivity extends BaseMvpActivity<SearchPresenter,ISearchView> implements ISearchView,
        View.OnClickListener{

    private EditText mEtSearch;

    @Override
    protected void loadIntent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_people;
    }

    @Override
    protected void initTitle() {
        setTitleText("搜索");
        setRightText("确定");
    }

    @Override
    protected void onRightAction() {
        super.onRightAction();
        doSearch();
    }

    @Override
    protected void bindViews() {
        mEtSearch = findViewById(R.id.et_search);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
    }

    private void doSearch(){
        String keyword = mEtSearch.getText().toString().trim();
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("kw",keyword);
        mPresenter.searchPeople(requestMap);
    }

    @Override
    protected void setListener() {
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
    }


    @Override
    protected SearchPresenter createPresenter() {
        return new SearchPresenter();
    }


    @Override
    public void searchPeople(List<SearchBean> result) {

    }
}
