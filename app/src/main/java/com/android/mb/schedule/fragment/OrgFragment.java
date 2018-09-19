package com.android.mb.schedule.fragment;

import android.view.View;
import android.widget.ListView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.base.BaseFragment;
import com.android.mb.schedule.entitys.TreeBean;
import com.android.mb.schedule.entitys.UserBean;
import com.android.mb.schedule.widget.tree.TreeAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * 与我相关
 * Created by cgy on 16/7/18.
 */
public class OrgFragment extends BaseFragment {

    private ListView mListView;
    private TreeAdapter mTreeAdapter;
    private List<TreeBean> mDataList = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.frg_me;
    }

    @Override
    protected void bindViews(View view) {
        mListView =  view.findViewById(R.id.listView);
        mTreeAdapter = new TreeAdapter(getActivity());
        mListView.setAdapter(mTreeAdapter);
    }

    @Override
    protected void processLogic() {
    }

    @Override
    protected void setListener() {

    }

    public void setDataList(List<TreeBean> list){
        mDataList = list;
        mTreeAdapter.updateList(mDataList);
    }
}
