package com.android.mb.schedule.binder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.entitys.UserBean;
import com.android.mb.schedule.utils.ImageUtils;
import com.android.mb.schedule.widget.treeview.TreeNode;
import com.android.mb.schedule.widget.treeview.TreeViewBinder;


/**
 * Created by tlh on 2016/10/1 :)
 */

public class UserNodeBinder extends TreeViewBinder<UserNodeBinder.ViewHolder> {
    @Override
    public ViewHolder provideViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void bindView(final ViewHolder holder, int position, TreeNode node) {
        final UserBean userNode = (UserBean) node.getContent();
        ImageUtils.displayAvatar(holder.ivAvatar.getContext(),userNode.getAvatar(),holder.ivAvatar);
        holder.tvName.setText(userNode.getNickname());
        holder.ivCheck.setImageResource(userNode.isSelect()?R.mipmap.ic_item_checked:R.mipmap.ic_item_check);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userNode.setSelect(!userNode.isSelect());
                holder.ivCheck.setImageResource(userNode.isSelect()?R.mipmap.ic_item_checked:R.mipmap.ic_item_check);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_tree_user;
    }

    public class ViewHolder extends TreeViewBinder.ViewHolder {
        private ImageView ivAvatar;
        private TextView tvName;
        private ImageView ivCheck;

        private ViewHolder(View rootView) {
            super(rootView);
            this.ivAvatar = rootView.findViewById(R.id.iv_avatar);
            this.tvName = rootView.findViewById(R.id.tv_name);
            this.ivCheck = rootView.findViewById(R.id.iv_check);
        }

    }
}
