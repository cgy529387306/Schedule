package com.android.mb.schedule.widget.tree;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.entitys.UserBean;
import com.android.mb.schedule.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class PersonAdapter extends BaseAdapter {

	private Context mContext;
	private List<UserBean> mDataList = new ArrayList<>();

	public PersonAdapter(Context context,List<UserBean> list) {
		this.mContext = context;
		this.mDataList = list;
	}


	@Override
	public int getCount() {
		return mDataList==null?0:mDataList.size();
	}

	@Override
	public UserBean getItem(int position) {
		return mDataList==null?null:mDataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(mContext, R.layout.item_person, null);
			holder.ivAvatar = convertView.findViewById(R.id.iv_avatar);
			holder.tvName = convertView.findViewById(R.id.tv_name);
			holder.ivCheck = convertView.findViewById(R.id.iv_check);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final UserBean userBean = getItem(position);
		ImageUtils.displayAvatar(mContext,userBean.getAvatar(),holder.ivAvatar);
		holder.tvName.setText(userBean.getNickname());
		return convertView;
	}

	private static class ViewHolder {
		private ImageView ivAvatar;
		private TextView tvName;
		private ImageView ivCheck;
	}

}
