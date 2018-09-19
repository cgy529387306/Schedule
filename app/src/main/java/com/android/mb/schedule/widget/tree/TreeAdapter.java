package com.android.mb.schedule.widget.tree;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.entitys.TreeBean;
import com.android.mb.schedule.utils.AppHelper;
import com.android.mb.schedule.widget.NestListView;

import java.util.ArrayList;
import java.util.List;

public class TreeAdapter extends BaseAdapter {

	private Context context;
	/** 当前需要显示的数据 */
	private List<TreeBean> mDataList = new ArrayList<>();
	/** 节点点击事件 */
	private NodeOnClickListener mNodeOnClickListener;
	/** 选择的类型 */
	private TreeBean typeBean;

	public TreeAdapter(Context context) {
		this.context = context;
		mNodeOnClickListener = new NodeOnClickListener();
	}

	public void updateList(List<TreeBean> list) {
		mDataList.clear();
		if (list != null){
			this.mDataList = list;
			notifyDataSetChanged();
		}
	}

	public void setTypeBean(TreeBean typeBean) {
		this.typeBean = typeBean;
		notifyDataSetChanged();
	}

	public TreeBean getTypeBean() {
		return typeBean;
	}

	@Override
	public int getCount() {
		return mDataList.size();
	}

	@Override
	public TreeBean getItem(int position) {
		return mDataList.get(position);
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
			convertView = View.inflate(context, R.layout.item_product_tree, null);
			holder.icon = convertView.findViewById(R.id.icon);
			holder.label = convertView.findViewById(R.id.label);
			holder.personListView = convertView.findViewById(R.id.personListView);
			convertView.setTag(holder);
			convertView.setOnClickListener(mNodeOnClickListener);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final TreeBean node = getItem(position);
		// 设置深度,层级越深距离左边越远
		convertView.setPadding(AppHelper.calDpi2px(30 * (node.getLevelCnt())+10), AppHelper.calDpi2px(12), AppHelper.calDpi2px(12), AppHelper.calDpi2px(12));
		if (node.getChildCount() == 0) {// 如果沒有子节点说明为叶子节点,去掉icon
			holder.icon.setVisibility(View.INVISIBLE);
		} else {
			holder.icon.setVisibility(View.VISIBLE);
			if (node.isExpand()) {//如果需要显示判断一下是否是需要显示展开的样式
				holder.icon.setImageResource(R.mipmap.ic_item_down);
			} else {
				holder.icon.setImageResource(R.mipmap.ic_item_right);
			}
		}
		holder.label.setTag(node);//label的tag里面存放Node,方便点击事件处理
		holder.label.setText(node.getName());
		holder.personListView.setAdapter(new PersonAdapter(context,node.getMan()));
		return convertView;
	}

	/** 目录点击事件 */
	class NodeOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			ViewHolder holder = (ViewHolder) v.getTag();
			TreeBean node = (TreeBean) holder.label.getTag();
			// 如果存在孩子节点就需要做展开或者隐藏操作
			if (node.getChildCount() > 0) {
				TreeUtils.filterNodeList(mDataList, node);
			} else {
				return;
			}
			holder.personListView.setVisibility(View.VISIBLE);
			notifyDataSetChanged();
		}
	}

	private static class ViewHolder {
		private ImageView icon;
		private TextView label;
		private NestListView personListView;
	}

}
