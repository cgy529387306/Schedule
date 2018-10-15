package com.android.mb.schedule.binder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.entitys.TreeBean;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.widget.treeview.TreeNode;
import com.android.mb.schedule.widget.treeview.TreeViewBinder;


/**
 * Created by tlh on 2016/10/1 :)
 */

public class OrgNode1Binder extends TreeViewBinder<OrgNode1Binder.ViewHolder> {
    @Override
    public ViewHolder provideViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void bindView(ViewHolder holder, int position, TreeNode node) {
        holder.ivArrow.setRotation(0);
        holder.ivArrow.setImageResource(R.mipmap.ic_item_right);
        int rotateDegree = node.isExpand() ? 90 : 0;
        holder.ivArrow.setRotation(rotateDegree);
        TreeBean orgNode = (TreeBean) node.getContent();
        String number = Helper.isEmpty(node.getChildList())?"":"("+node.getChildList().size()+")";
        holder.tvName.setText(orgNode.getName()+number);
        if (node.isLeaf())
            holder.ivArrow.setVisibility(View.INVISIBLE);
        else holder.ivArrow.setVisibility(View.VISIBLE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_tree_org;
    }

    public static class ViewHolder extends TreeViewBinder.ViewHolder {
        private ImageView ivArrow;
        private TextView tvName;

        private ViewHolder(View rootView) {
            super(rootView);
            this.ivArrow = (ImageView) rootView.findViewById(R.id.iv_arrow);
            this.tvName = (TextView) rootView.findViewById(R.id.tv_name);
        }

        public ImageView getIvArrow() {
            return ivArrow;
        }

        public TextView getTvName() {
            return tvName;
        }
    }
}
