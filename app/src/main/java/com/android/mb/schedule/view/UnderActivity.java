package com.android.mb.schedule.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.base.BaseMvpActivity;
import com.android.mb.schedule.binder.OrgNodeBinder;
import com.android.mb.schedule.binder.UserNodeBinder;
import com.android.mb.schedule.constants.ProjectConstants;
import com.android.mb.schedule.entitys.CurrentUser;
import com.android.mb.schedule.entitys.TreeBean;
import com.android.mb.schedule.entitys.TreeData;
import com.android.mb.schedule.entitys.UserBean;
import com.android.mb.schedule.presenter.UnderPresenter;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.JsonHelper;
import com.android.mb.schedule.utils.NavigationHelper;
import com.android.mb.schedule.utils.PreferencesHelper;
import com.android.mb.schedule.view.interfaces.IUnderView;
import com.android.mb.schedule.widget.MyDividerItemDecoration;
import com.android.mb.schedule.widget.treeview.TreeNode;
import com.android.mb.schedule.widget.treeview.TreeViewAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 我的下属
 * Created by cgy on 2018\8\20 0020.
 */

public class UnderActivity extends BaseMvpActivity<UnderPresenter,IUnderView> implements IUnderView, View.OnClickListener {

    private TextView mTvEmpty;
    private RecyclerView mRecyclerView;
    private TreeViewAdapter mTreeAdapter;
    private List<TreeNode> mTreeNodes = new ArrayList<>();
    private List<TreeBean> mDataList = new ArrayList<>();
    @Override
    protected void loadIntent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_under;
    }

    @Override
    protected void initTitle() {
        setTitleText("下属日程");
    }

    @Override
    protected void bindViews() {
        mTvEmpty = findViewById(R.id.tv_empty);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        getDataFromLocal();
        mPresenter.getOfficeList();
    }

    @Override
    protected void setListener() {
        findViewById(R.id.lly_tag).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.lly_tag){
            NavigationHelper.startActivity(UnderActivity.this, TagAllActivity.class, null, false);
        }
    }


    @Override
    protected UnderPresenter createPresenter() {
        return new UnderPresenter();
    }


    @Override
    public void getOrgSuccess(TreeData result) {
        mDataList = result.getTree();
        PreferencesHelper.getInstance().putString(ProjectConstants.KEY_UNDER_LIST+ CurrentUser.getInstance().getId(), JsonHelper.toJson(result));
        refreshData();
    }

    private void getDataFromLocal(){
        String orgListStr = PreferencesHelper.getInstance().getString(ProjectConstants.KEY_UNDER_LIST+CurrentUser.getInstance().getId());
        TreeData orgList = JsonHelper.fromJson(orgListStr,TreeData.class);
        if (orgList!=null){
            mDataList = orgList.getTree();
            refreshData();
        }
    }

    private void refreshData(){
        mTreeNodes.clear();
        if (Helper.isEmpty(mDataList)){
            mTvEmpty.setVisibility(View.VISIBLE);
            return;
        }
        mTvEmpty.setVisibility(View.GONE);
        for (TreeBean treeBean:mDataList) {
            TreeNode<TreeBean> root = new TreeNode<>(treeBean);
            mTreeNodes.add(root);
            addChild(treeBean,root);
            root.expand();
        }
        mTreeAdapter = new TreeViewAdapter(mTreeNodes, Arrays.asList(new UserNodeBinder(this,1), new OrgNodeBinder()));
        mRecyclerView.setAdapter(mTreeAdapter);
        mTreeAdapter.setOnTreeNodeListener(new TreeViewAdapter.OnTreeNodeListener() {
            @Override
            public boolean onClick(TreeNode node, RecyclerView.ViewHolder holder) {
                if (!node.isLeaf()) {
                    onToggle(!node.isExpand(), holder);
                }
                return false;
            }

            @Override
            public void onToggle(boolean isExpand, RecyclerView.ViewHolder holder) {
                OrgNodeBinder.ViewHolder dirViewHolder = (OrgNodeBinder.ViewHolder) holder;
                final ImageView ivArrow = dirViewHolder.getIvArrow();
                int rotateDegree = isExpand ? 90 : -90;
                ivArrow.animate().rotationBy(rotateDegree)
                        .start();
            }
        });
    }

    private void addChild(TreeBean treeBean,TreeNode<TreeBean> parent){
        for (UserBean userBean:treeBean.getMan()){
            TreeNode<UserBean> userNode = new TreeNode(userBean);
            parent.addChild(userNode);
        }
        for (TreeBean child:treeBean.getChildlist()){
            TreeNode<TreeBean> childNode = new TreeNode<>(child);
            parent.addChild(childNode);
            addChild(child,childNode);
        }
    }
}
