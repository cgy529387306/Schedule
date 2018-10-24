package com.android.mb.schedule.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;

import com.android.mb.schedule.R;
import com.android.mb.schedule.adapter.MyTabPagerAdapter;
import com.android.mb.schedule.base.BaseMvpActivity;
import com.android.mb.schedule.constants.ProjectConstants;
import com.android.mb.schedule.entitys.CurrentUser;
import com.android.mb.schedule.entitys.TreeData;
import com.android.mb.schedule.entitys.UserBean;
import com.android.mb.schedule.entitys.UserData;
import com.android.mb.schedule.fragment.OrgFragment;
import com.android.mb.schedule.fragment.PersonFragment;
import com.android.mb.schedule.fragment.SelectedFragment;
import com.android.mb.schedule.presenter.PersonPresenter;
import com.android.mb.schedule.utils.FileUtils;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.JsonHelper;
import com.android.mb.schedule.utils.NavigationHelper;
import com.android.mb.schedule.utils.PreferencesHelper;
import com.android.mb.schedule.view.interfaces.IPersonView;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 选择人员
 * Created by cgy on 2018\8\20 0020.
 */

public class SelectPersonActivity extends BaseMvpActivity<PersonPresenter,IPersonView> implements IPersonView {

    private EditText mEtSearch;
    private TabLayout mTb;
    private ViewPager mVp;
    private List<Fragment> mFragmentList;
    private List<String> mTitleList;
    public static List<UserBean> mSelectList;
    @Override
    protected void loadIntent() {
        mSelectList = (List<UserBean>) getIntent().getSerializableExtra("selectPerson");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_person;
    }

    @Override
    protected void initTitle() {
        setTitleText("选择人员");
        setRightImage(R.mipmap.icon_checked);
    }

    @Override
    protected void onRightAction() {
        super.onRightAction();
        Intent intent = new Intent();
        intent.putExtra("selectPerson", (Serializable) (mSelectList==null?new ArrayList<UserBean>():mSelectList));
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    protected void bindViews() {
        if (mSelectList==null){
            mSelectList = new ArrayList<>();
        }
        mEtSearch = findViewById(R.id.et_search);
        mTb = (TabLayout) findViewById(R.id.tabLayout);
        mVp = (ViewPager) findViewById(R.id.viewPager);
    }

    private void initTabPager(){
        mTitleList = new ArrayList<>();
        mTitleList.add("常用");
        mTitleList.add("机构");
        mTitleList.add("已选");
        mTb.addTab(mTb.newTab().setText(mTitleList.get(0)));
        mTb.addTab(mTb.newTab().setText(mTitleList.get(1)));
        mTb.addTab(mTb.newTab().setText(mTitleList.get(2)));

        mFragmentList = new ArrayList<>();
        mFragmentList.add(new PersonFragment());
        mFragmentList.add(new OrgFragment());
        mFragmentList.add(new SelectedFragment());

        mVp.setAdapter(new MyTabPagerAdapter(getSupportFragmentManager(), mFragmentList,mTitleList));
        mTb.setupWithViewPager(mVp);
    }

    private void getDataFromLocal(){
        String orgListStr = PreferencesHelper.getInstance().getString(ProjectConstants.KEY_ORG_LIST+CurrentUser.getInstance().getId());
        String personListStr = PreferencesHelper.getInstance().getString(ProjectConstants.KEY_PERSON_LIST+CurrentUser.getInstance().getId());
        TreeData orgList = JsonHelper.fromJson(orgListStr,TreeData.class);
        List<UserBean> personList = JsonHelper.fromJson(personListStr,new TypeToken<List<UserBean>>(){}.getType());
        if (Helper.isNotEmpty(orgList)){
            OrgFragment orgFragment = (OrgFragment) mFragmentList.get(1);
            orgFragment.setDataList(orgList.getTree());
        }
        if (personList!=null){
            PersonFragment personFragment = (PersonFragment) mFragmentList.get(0);
            personFragment.setDataList(personList,false);
        }

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        initTabPager();
        getDataFromLocal();
        mPresenter.getOfficeList();
        mPresenter.getPersons();
    }

    @Override
    protected void setListener() {
        mEtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationHelper.startActivityForResult(SelectPersonActivity.this,SearchPeopleActivity.class,null,ProjectConstants.REQUEST_SEARCH_PEOPLE);
            }
        });
        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                sendMsg(ProjectConstants.EVENT_UPDATE_SELECT,null);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected PersonPresenter createPresenter() {
        return new PersonPresenter();
    }


    @Override
    public void getOrgSuccess(TreeData result) {
        if (result!=null){
            PreferencesHelper.getInstance().putString(ProjectConstants.KEY_ORG_LIST+CurrentUser.getInstance().getId(), JsonHelper.toJson(result));
            OrgFragment orgFragment = (OrgFragment) mFragmentList.get(1);
            orgFragment.setDataList(result.getTree());
        }
    }

    @Override
    public void getPersonSuccess(List<UserBean> result) {
        if (result!=null){
            PreferencesHelper.getInstance().putString(ProjectConstants.KEY_PERSON_LIST+CurrentUser.getInstance().getId(), JsonHelper.toJson(result));
            PersonFragment personFragment = (PersonFragment) mFragmentList.get(0);
            personFragment.setDataList(result,true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (ProjectConstants.REQUEST_SEARCH_PEOPLE == requestCode) {
            if (mVp!=null && mVp.getAdapter()!=null && mVp.getAdapter().getCount()>=3){
                mVp.setCurrentItem(2);
            }
        }
    }
}
