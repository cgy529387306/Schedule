package com.android.mb.schedule.binder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.entitys.UserBean;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.ImageUtils;
import com.android.mb.schedule.utils.ProjectHelper;
import com.android.mb.schedule.utils.ToastHelper;
import com.android.mb.schedule.view.SelectPersonActivity;
import com.android.mb.schedule.widget.treeview.TreeNode;
import com.android.mb.schedule.widget.treeview.TreeViewBinder;


/**
 * Created by tlh on 2016/10/1 :)
 */

public class UserNode1Binder extends TreeViewBinder<UserNode1Binder.ViewHolder> {
    @Override
    public ViewHolder provideViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void bindView(final ViewHolder holder, int position, TreeNode node) {
        final UserBean userNode = (UserBean) node.getContent();
        ImageUtils.displayAvatar(holder.ivAvatar.getContext(),userNode.getAvatar(),holder.ivAvatar);
        holder.tvName.setText(userNode.getNickname());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastHelper.showLongToast(userNode.getNickname());
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_tree_user1;
    }

    public class ViewHolder extends TreeViewBinder.ViewHolder {
        private ImageView ivAvatar;
        private TextView tvName;

        private ViewHolder(View rootView) {
            super(rootView);
            this.ivAvatar = rootView.findViewById(R.id.iv_avatar);
            this.tvName = rootView.findViewById(R.id.tv_name);
        }

    }
}
