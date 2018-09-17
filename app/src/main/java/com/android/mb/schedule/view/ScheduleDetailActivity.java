package com.android.mb.schedule.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.base.BaseActivity;


/**
 * 新增日程
 * Created by cgy on 16/7/18.
 */
public class ScheduleDetailActivity extends BaseActivity implements View.OnClickListener{

    private TextView mTvScheduleName; //日程名称
    private TextView mTvAddress ; //位置
    private TextView mTvStartDate ; //开始日期
    private TextView mTvStartTime ; //开始时间
    private TextView mTvEndDate ; //结束日期
    private TextView mTvEndTime ; //结束时间
    private TextView mTvImport; // 是否重要
    private TextView mTvScheduleContent; // 日程内容
    private TextView mTvRepeat; //重复
    private TextView mTvFileName;//文件名称
    private TextView mTvDownDocument; // 点击下载文件
    private TextView mTvPersons; // 相关人员
    private TextView mTvWhenRemind; // 日程什么时候开始提醒
    private TextView mTvEdit;
    private TextView mTvShare;
    private TextView mTvDelete;
    @Override
    protected void loadIntent() {
        
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_schedule_detail;
    }

    @Override
    protected void initTitle() {
        setTitleText("查看日程");
    }


    @Override
    protected void bindViews() {
        mTvScheduleName = findViewById(R.id.tv_schedule_name);
        mTvAddress = findViewById(R.id.tv_address);
        mTvStartDate = findViewById(R.id.tv_start_date);
        mTvStartTime = findViewById(R.id.tv_start_time);
        mTvEndDate = findViewById(R.id.tv_end_date);
        mTvEndTime = findViewById(R.id.tv_end_time);
        mTvImport = findViewById(R.id.tv_import);
        mTvScheduleContent = findViewById(R.id.tv_schedule_content);
        mTvRepeat = findViewById(R.id.tv_repeat);
        mTvFileName = findViewById(R.id.tv_file_name);
        mTvDownDocument = findViewById(R.id.tv_download_document);
        mTvPersons = findViewById(R.id.tv_persons);
        mTvWhenRemind = findViewById(R.id.tv_when_remind);
        mTvEdit = findViewById(R.id.tv_edit);
        mTvShare = findViewById(R.id.tv_share);
        mTvDelete = findViewById(R.id.tv_delete);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        initData();
    }

    @Override
    protected void setListener() {
        mTvDownDocument.setOnClickListener(this);
        mTvEdit.setOnClickListener(this);
        mTvShare.setOnClickListener(this);
        mTvDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_download_document){
            //TODO
        }else if (id == R.id.tv_edit){
            //TODO
        }else if (id == R.id.tv_share){
            //TODO
        }else if (id == R.id.tv_delete){
            //TODO
        }
    }


    private void initData(){

    }

}
