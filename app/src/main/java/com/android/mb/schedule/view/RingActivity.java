package com.android.mb.schedule.view;

import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.mb.schedule.R;
import com.android.mb.schedule.adapter.RingAdapter;
import com.android.mb.schedule.base.BaseActivity;
import com.android.mb.schedule.constants.ProjectConstants;
import com.android.mb.schedule.entitys.RingBean;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.PreferencesHelper;
import com.android.mb.schedule.utils.ProjectHelper;
import com.android.mb.schedule.widget.MyDividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 铃声选择
 * Created by cgy on 2018\8\20 0020.
 */

public class RingActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private RingAdapter mAdapter;
    private List<RingBean> mDataList = new ArrayList<>();
    private RingtoneManager mRingtoneManager;
    @Override
    protected void loadIntent() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ring;
    }

    @Override
    protected void initTitle() {
        setTitleText("铃声设置");
        setRightText("保存");
    }

    @Override
    protected void onRightAction(View view) {
        super.onRightAction(view);
        ProjectHelper.disableViewDoubleClick(view);
        RingBean ringBean = getSelectRing();
        if (ringBean!=null && Helper.isNotEmpty(ringBean.getPath())){
            PreferencesHelper.getInstance().putString(ProjectConstants.KEY_RING,ringBean.getPath());
        }
        finish();
    }

    private RingBean getSelectRing(){
        if (Helper.isNotEmpty(mDataList) && mDataList.size()>mAdapter.getIndex()){
            return mDataList.get(mAdapter.getIndex());
        }else{
            return new RingBean();
        }
    }

    @Override
    protected void bindViews() {
        mRingtoneManager = new RingtoneManager(getApplicationContext());
        mRingtoneManager.setType(RingtoneManager.TYPE_NOTIFICATION);
        mRecyclerView = findViewById(R.id.recyclerView_ring);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this );
        mRecyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper. VERTICAL);
        mRecyclerView.setItemAnimator( new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(mContext, LinearLayoutManager.VERTICAL,12));
        mAdapter = new RingAdapter(R.layout.item_ring,mDataList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setCurrentIndex(-1);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mAdapter.setCurrentIndex(position);
                RingBean ringBean = mAdapter.getItem(position);
                if (ringBean!=null && ringBean.getRingtone()!=null){
                    ringBean.getRingtone().play();
                }
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        initData();
    }

    @Override
    protected void setListener() {
    }

    private void initData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Cursor mCursor = mRingtoneManager.getCursor();
                mDataList.clear();
                if (mCursor.moveToFirst()){
                    while (mCursor.moveToNext()){
                        RingBean ringBean = new RingBean();
                        Ringtone ringtone = mRingtoneManager.getRingtone(mCursor.getPosition());
                        String name = ringtone.getTitle(getApplicationContext());
                        String path = mRingtoneManager.getRingtoneUri(mCursor.getPosition()).toString();
                        ringBean.setName(name);
                        ringBean.setPath(path);
                        ringBean.setRingtone(ringtone);
                        mDataList.add(ringBean);
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setSelectIndex();
                        mAdapter.setNewData(mDataList);
                        mAdapter.setEmptyView(R.layout.empty_data, (ViewGroup) mRecyclerView.getParent());
                    }
                });
            }
        }).start();
    }

    private void setSelectIndex(){
        String path = PreferencesHelper.getInstance().getString(ProjectConstants.KEY_RING);
        if (Helper.isEmpty(path)){
            Uri uri = RingtoneManager.getActualDefaultRingtoneUri(this,RingtoneManager.TYPE_NOTIFICATION);
            if (uri!=null){
                String name = ProjectHelper.getFileName(uri.toString());
                if (Helper.isNotEmpty(name) && Helper.isNotEmpty(mDataList)){
                    for (int i=0;i<mDataList.size();i++){
                        RingBean ringBean = mDataList.get(i);
                        if (name.equals(ringBean.getName())){
                            mAdapter.setCurrentIndex(i);
                        }
                    }
                }
            }
        }else{
            if (Helper.isNotEmpty(path) && Helper.isNotEmpty(mDataList)){
                for (int i=0;i<mDataList.size();i++){
                    RingBean ringBean = mDataList.get(i);
                    if (path.equals(ringBean.getPath())){
                        mAdapter.setCurrentIndex(i);
                    }
                }
            }
        }
    }

}
