package com.android.mb.schedule.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.base.BaseActivity;
import com.android.mb.schedule.constants.ProjectConstants;
import com.android.mb.schedule.entitys.CurrentUser;
import com.android.mb.schedule.rxbus.Events;
import com.android.mb.schedule.utils.ImageUtils;
import com.android.mb.schedule.utils.NavigationHelper;
import com.android.mb.schedule.utils.ProjectHelper;
import com.android.mb.schedule.widget.CircleImageView;

import rx.functions.Action1;

/**
 * Created by Administrator on 2018\8\20 0020.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener{

    private CircleImageView mIvHead;
    private TextView mTvName;  // 名字
    private TextView mTvJob;  //职位
    private TextView mBtnSetPwd;
    private ImageView mIvRing;
    private ImageView mIvVibrate;
    private boolean isRemind = false;

    @Override
    protected void loadIntent() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initTitle() {
        setTitleText("设置");
    }

    @Override
    protected void bindViews() {
        mIvHead = findViewById(R.id.iv_head);
        mTvName = findViewById(R.id.tv_name);
        mTvJob = findViewById(R.id.tv_job);
        mBtnSetPwd = findViewById(R.id.tv_set_pwd);
        mIvRing = findViewById(R.id.iv_ring);
        mIvVibrate = findViewById(R.id.iv_vibrate);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        initUserInfo();
        regiestEvent(ProjectConstants.EVENT_UPDATE_USER_INFO, new Action1<Events<?>>() {
            @Override
            public void call(Events<?> events) {
                initUserInfo();
            }
        });
    }

    @Override
    protected void setListener() {
        mBtnSetPwd.setOnClickListener(this);
        mIvRing.setOnClickListener(this);
        mIvVibrate.setOnClickListener(this);
        findViewById(R.id.lly_head).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_set_pwd){
            NavigationHelper.startActivity(SettingActivity.this,SetPwdActivity.class,null,false);
        }else if (id == R.id.iv_ring){
            NavigationHelper.startActivity(SettingActivity.this,RingActivity.class,null,false);
        }else if (id == R.id.iv_vibrate){
            isRemind = !isRemind;
            mIvVibrate.setImageResource(isRemind?R.mipmap.ic_vibrate_open:R.mipmap.ic_vibrate_close);
        }else if (id == R.id.lly_head){
            NavigationHelper.startActivity(SettingActivity.this,PersonalSettingActivity.class,null,false);
        }
    }

    private void initUserInfo(){
        if (CurrentUser.getInstance()!=null){
            mTvName.setText(ProjectHelper.getCommonText(CurrentUser.getInstance().getNickname()));
            mTvJob.setText(ProjectHelper.getCommonText(CurrentUser.getInstance().getOffice_name()));
            ImageUtils.displayAvatar(this,CurrentUser.getInstance().getAvatar(),mIvHead);
        }
    }
}
