package com.android.mb.schedule.fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.base.BaseFragment;
import com.android.mb.schedule.binder.OrgNodeBinder;
import com.android.mb.schedule.binder.UserNodeBinder;
import com.android.mb.schedule.entitys.TreeBean;
import com.android.mb.schedule.entitys.UserBean;
import com.android.mb.schedule.widget.treeview.TreeNode;
import com.android.mb.schedule.widget.treeview.TreeViewAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 组织树
 * Created by cgy on 16/7/18.
 */
public class OrgFragment1 extends BaseFragment {

    private RecyclerView mRecyclerView;
    private TreeViewAdapter mTreeAdapter;
    private List<TreeNode> mTreeNodes = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.frg_org1;
    }

    @Override
    protected void bindViews(View view) {
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void processLogic() {

    }

    @Override
    protected void setListener() {


    }

    public void setDataList(List<TreeBean> list){
        for (TreeBean treeBean:list) {
            TreeNode<TreeBean> root = new TreeNode<>(treeBean);
            mTreeNodes.add(root);
            addChild(treeBean,root);
        }
        mTreeAdapter = new TreeViewAdapter(mTreeNodes, Arrays.asList(new UserNodeBinder(), new OrgNodeBinder()));
        mRecyclerView.setAdapter(mTreeAdapter);
        mTreeAdapter.setOnTreeNodeListener(new TreeViewAdapter.OnTreeNodeListener() {
            @Override
            public boolean onClick(TreeNode node, RecyclerView.ViewHolder holder) {
                if (!node.isLeaf()) {
                    onToggle(!node.isExpand(), holder);
//                    if (!node.isExpand())
//                        adapter.collapseBrotherNode(node);
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
