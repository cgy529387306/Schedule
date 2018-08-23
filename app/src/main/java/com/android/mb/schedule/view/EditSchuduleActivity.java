package com.android.mb.schedule.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.base.BaseActivity;
import com.android.mb.schedule.pop.ScheduleRepeatPop;
import com.android.mb.schedule.utils.NavigationHelper;

import java.util.List;

/**
 * Created by Administrator on 2018\8\20 0020.
 */

public class EditSchuduleActivity extends BaseActivity implements View.OnClickListener{

    private TextView mTvScheduleName; //日程名称
    private TextView mBtnLocation; //定位
    private TextView mTvAddress ; //位置
    private TextView mTvScheduleContent; // 日程内容
    private TextView mTvDocument ; //点击替换附件
    private TextView mBtnChangeDocument ; //点击替换附件
    private TextView mTvStartDate ; //开始日期
    private TextView mTvStartTime ; //开始时间
    private TextView mTvEndDate ; //结束日期
    private TextView mTvEndTime ; //结束时间
    private ImageView mIvRemind ; //全天提醒
    private boolean isRemind = false;
    private TextView mBtnAdd;// 添加相关人员
    private ImageView mIvNoRemind; // 是否提醒相关人员
    private boolean isNoRemind = false;
    private ImageView mIvShareToOther; //分享给其他人
    private TextView mTvRepeat; //重复
    private TextView mTvWhenRemind; // 日程什么时候开始提醒
    private ImageView mIvImportment; //是否重要
    private boolean isImportment = false;
    private ScheduleRepeatPop mPop;

    @Override
    protected void loadIntent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_schedule;
    }

    @Override
    protected void initTitle() {
        setTitleText("编辑日程");
        setRightImage(R.mipmap.icon_right);
    }

    @Override
    protected void bindViews() {
        mTvScheduleName = findViewById(R.id.tv_schedulename);
        mBtnLocation = findViewById(R.id.tv_location);
        mTvAddress = findViewById(R.id.tv_address);
        mTvScheduleContent = findViewById(R.id.tv_schedulecontent);
        mTvDocument = findViewById(R.id.tv_document);
        mBtnChangeDocument = findViewById(R.id.tv_changedocument);
        mTvStartDate = findViewById(R.id.tv_startdate);
        mTvStartTime = findViewById(R.id.tv_starttime);
        mTvEndDate = findViewById(R.id.tv_enddate);
        mTvEndTime = findViewById(R.id.tv_endtime);
        mIvRemind = findViewById(R.id.iv_remind);
        mBtnAdd = findViewById(R.id.tv_add);
        mIvNoRemind = findViewById(R.id.iv_noremind);
        mIvShareToOther = findViewById(R.id.iv_sharetoother);
        mTvRepeat = findViewById(R.id.tv_repeat);
        mTvWhenRemind = findViewById(R.id.tv_whenremind);
        mIvImportment = findViewById(R.id.iv_importment);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void setListener() {
        mBtnLocation.setOnClickListener(this);
        mBtnChangeDocument.setOnClickListener(this);
        mIvRemind.setOnClickListener(this);
        mBtnAdd.setOnClickListener(this);
        mIvNoRemind.setOnClickListener(this);
        mIvShareToOther.setOnClickListener(this);
        mTvRepeat.setOnClickListener(this);
        mTvWhenRemind.setOnClickListener(this);
        mIvImportment.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_location){
        }else  if (id == R.id.tv_changedocument){
        }else  if (id == R.id.iv_remind){
            isRemind = !isRemind;
            mIvRemind.setImageResource(isRemind?R.mipmap.ic_vibrate_open:R.mipmap.ic_vibrate_close);
        }else  if (id == R.id.tv_add){
        }else  if (id == R.id.iv_noremind){
            isNoRemind = !isNoRemind;
            mIvNoRemind.setImageResource(isNoRemind?R.mipmap.ic_vibrate_open:R.mipmap.ic_vibrate_close);
        }else  if (id == R.id.iv_sharetoother){
        }else  if (id == R.id.tv_repeat){
            if(mPop != null){
                mPop.showPopupWindow(v);
            }
        }else  if (id == R.id.tv_whenremind){
        }else  if (id == R.id.iv_importment){
            isImportment = !isImportment;
            mIvImportment.setImageResource(isImportment?R.mipmap.ic_vibrate_open:R.mipmap.ic_vibrate_close);
        }
    }

}
