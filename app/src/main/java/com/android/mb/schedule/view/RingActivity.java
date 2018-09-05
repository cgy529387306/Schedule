package com.android.mb.schedule.view;

import android.content.ContentUris;
import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.adapter.RingAdapter;
import com.android.mb.schedule.base.BaseActivity;
import com.android.mb.schedule.entitys.RingBean;
import com.android.mb.schedule.utils.NavigationHelper;
import com.android.mb.schedule.view.interfaces.OnItemClickListener;
import com.android.mb.schedule.widget.CircleImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018\8\20 0020.
 */

public class RingActivity extends BaseActivity {

    private RingtoneManager mRingtoneManager;
    private List<RingBean> mList;
    private RingAdapter mRingAdapter;
    private RecyclerView mRecyclerViewRing;

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
    }

    @Override
    protected void bindViews() {
        mRecyclerViewRing = findViewById(R.id.recyclerView_ring);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this );
        //设置布局管理器
        mRecyclerViewRing.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper. VERTICAL);
        //设置增加或删除条目的动画
        mRecyclerViewRing.setItemAnimator( new DefaultItemAnimator());

        mRingtoneManager = new RingtoneManager(getApplicationContext());
        mRingtoneManager.setType(RingtoneManager.TYPE_ALARM);
        Cursor mCursor = mRingtoneManager.getCursor();
        mList = new ArrayList<>();
        if (mCursor.moveToFirst()){
            while (mCursor.moveToNext()){
                RingBean mRingBean = new RingBean();
                String mName = mRingtoneManager.getRingtone(mCursor.getPosition()).getTitle(getApplicationContext());
                String mPath = mRingtoneManager.getRingtoneUri(mCursor.getPosition()).toString();
                mRingBean.setName(mName);
                mRingBean.setPath(mPath);
                mList.add(mRingBean);
            }
        }
        mRingAdapter = new RingAdapter(RingActivity.this,mList);
        mRecyclerViewRing.setAdapter(mRingAdapter);
        mRingAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                mRingAdapter.setCurrentIndex(postion);
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
    }

    @Override
    protected void setListener() {
    }

}
