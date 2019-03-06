package com.android.mb.schedule.view;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.mb.schedule.R;
import com.android.mb.schedule.adapter.SectionPeopleAdapter;
import com.android.mb.schedule.base.BaseMvpActivity;
import com.android.mb.schedule.constants.ProjectConstants;
import com.android.mb.schedule.entitys.PeopleSection;
import com.android.mb.schedule.entitys.SearchBean;
import com.android.mb.schedule.entitys.UserBean;
import com.android.mb.schedule.presenter.SearchPresenter;
import com.android.mb.schedule.utils.AppHelper;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.ProjectHelper;
import com.android.mb.schedule.view.interfaces.ISearchView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我（或者他人）的日程
 * Created by cgy on 2018\8\20 0020.
 */

public class SearchPeopleActivity extends BaseMvpActivity<SearchPresenter,ISearchView> implements ISearchView,
        View.OnClickListener{

    private EditText mEtSearch;
    private RecyclerView mRecyclerView;
    private SectionPeopleAdapter mAdapter;
    @Override
    protected void loadIntent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_people;
    }

    @Override
    protected void initTitle() {
        setTitleText("人员搜索");
        setRightImage(R.mipmap.icon_checked);
    }

    @Override
    protected void onRightAction(View view) {
        super.onRightAction(view);
        AppHelper.hideSoftInputFromWindow(mEtSearch);
        sendMsg(ProjectConstants.EVENT_UPDATE_SELECT,null);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void bindViews() {
        mEtSearch = findViewById(R.id.et_search);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new SectionPeopleAdapter(R.layout.item_section_content_people,R.layout.item_section_header_people,new ArrayList());
        mRecyclerView.setAdapter(mAdapter);
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
        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyword = mEtSearch.getText().toString().trim();
                if (Helper.isEmpty(keyword)){
                    mAdapter.setNewData(new ArrayList<PeopleSection>());
                    mAdapter.setEmptyView(R.layout.empty_data, (ViewGroup) mRecyclerView.getParent());
                }else{
                    doSearch();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PeopleSection mySection = mAdapter.getItem(position);
                if (!mySection.isHeader){
                    UserBean userBean = mySection.t;
                    userBean.setSelect(!userBean.isSelect());
                    mAdapter.notifyDataSetChanged();
                    if (userBean.isSelect()){
                        if (!ProjectHelper.getSelectIdList().contains(userBean.getId())){
                            SelectPersonActivity.mSelectList.add(userBean);
                        }
                    }else{
                        removeSelect(userBean.getId());
                    }
                }
            }
        });
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
        mAdapter.setNewData(getPeopleSectionData(result));
        mAdapter.setEmptyView(R.layout.empty_data, (ViewGroup) mRecyclerView.getParent());
    }

    private List<PeopleSection> getPeopleSectionData(List<SearchBean> dataList) {
        List<PeopleSection> sectionList = new ArrayList<>();
        for (SearchBean searchBean:dataList) {
            sectionList.add(new PeopleSection(true, searchBean.getName()));
            for (UserBean userBean:searchBean.getList()){
                userBean.setSelect(ProjectHelper.getSelectIdList().contains(userBean.getId()));
                sectionList.add(new PeopleSection(userBean));
            }
        }
        return sectionList;
    }

    private void removeSelect(long id){
        if (Helper.isNotEmpty(SelectPersonActivity.mSelectList)){
            for (UserBean userBean:SelectPersonActivity.mSelectList) {
                if (id == userBean.getId()){
                    SelectPersonActivity.mSelectList.remove(userBean);
                    break;
                }
            }
        }
    }
}
