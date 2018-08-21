package com.android.mb.schedule.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.base.BaseActivity;
import com.android.mb.schedule.base.BaseMvpActivity;
import com.android.mb.schedule.presenter.HomePresenter;
import com.android.mb.schedule.view.interfaces.IHomeView;
import com.android.mb.schedule.widget.CircleImageView;

/**
 * Created by Administrator on 2018\8\20 0020.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener{

    private CircleImageView mIvHead;
    private TextView mTvName;  // 名字
    private TextView mTvJob;  //职位
    private TextView mBtnMe;
    private TextView mBtnSetPwd;
    private ImageView mIvVoice;
    private ImageView mIvVibrate;
    private boolean isRemind = true;

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
        mBtnMe = findViewById(R.id.tv_me);
        mBtnSetPwd = findViewById(R.id.tv_setpwd);
        mIvVoice = findViewById(R.id.iv_voice);
        mIvVibrate = findViewById(R.id.iv_vibrate);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
    }

    @Override
    protected void setListener() {
        mBtnMe.setOnClickListener(this);
        mBtnSetPwd.setOnClickListener(this);
        mIvVoice.setOnClickListener(this);
        mIvVibrate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_me){
        }else if (id == R.id.tv_setpwd){
        }else if (id == R.id.iv_voice){
        }else if (id == R.id.iv_vibrate){
            isRemind = !isRemind;
            mIvVibrate.setImageResource(isRemind?R.mipmap.ic_vibrate_open:R.mipmap.ic_vibrate_close);
        }
    }
}
