package com.android.mb.schedule.binder;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.app.MBApplication;
import com.android.mb.schedule.entitys.UserBean;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.ImageUtils;
import com.android.mb.schedule.utils.NavigationHelper;
import com.android.mb.schedule.utils.ProjectHelper;
import com.android.mb.schedule.utils.ToastHelper;
import com.android.mb.schedule.view.ScheduleUnderActivity;
import com.android.mb.schedule.view.SelectPersonActivity;
import com.android.mb.schedule.widget.treeview.TreeNode;
import com.android.mb.schedule.widget.treeview.TreeViewBinder;


/**
 * Created by tlh on 2016/10/1 :)
 */

public class UserNodeBinder extends TreeViewBinder<UserNodeBinder.ViewHolder> {

    private int mType;

    private Activity mActivity;

    public UserNodeBinder(Activity activity,int type) {
        mType = type;
        mActivity = activity;
    }

    @Override
    public ViewHolder provideViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void bindView(final ViewHolder holder, int position, TreeNode node) {
        final UserBean userNode = (UserBean) node.getContent();
        ImageUtils.displayAvatar(holder.ivAvatar.getContext(),userNode.getAvatar(),holder.ivAvatar);
        holder.tvName.setText(userNode.getNickname());
        if (mType==1){
            holder.ivCheck.setVisibility(View.GONE);
        }else{
            holder.ivCheck.setVisibility(View.VISIBLE);
            holder.ivCheck.setImageResource(userNode.isSelect()?R.mipmap.ic_item_checked:R.mipmap.ic_item_check);
        }
        if (mType==1){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putLong("id",userNode.getId());
                    bundle.putString("name",userNode.getNickname());
                    NavigationHelper.startActivity(mActivity, ScheduleUnderActivity.class,bundle,false);
                }
            });
        }else{
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userNode.setSelect(!userNode.isSelect());
                    holder.ivCheck.setImageResource(userNode.isSelect()?R.mipmap.ic_item_checked:R.mipmap.ic_item_check);
                    if (userNode.isSelect()){
                        if (!ProjectHelper.getSelectIdList().contains(userNode.getId())){
                            SelectPersonActivity.mSelectList.add(userNode);
                        }
                    }else{
                        removeSelect(userNode.getId());
                    }
                }
            });
        }

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
