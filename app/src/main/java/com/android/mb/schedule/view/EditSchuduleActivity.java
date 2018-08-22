package com.android.mb.schedule.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.base.BaseActivity;
import com.android.mb.schedule.utils.NavigationHelper;

/**
 * Created by Administrator on 2018\8\20 0020.
 */

public class EditSchuduleActivity extends BaseActivity implements View.OnClickListener{

    private TextView mTvScheduleName; //日程名称
    private TextView mBtnLocation; //定位
    private TextView mTvAddress ; //位置
    private TextView mTvScheduleContent; // 日程内容

    @Override
    protected void loadIntent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_schedule;
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void bindViews() {
        mTvScheduleName = findViewById(R.id.tv_schedulename);
        mBtnLocation = findViewById(R.id.tv_location);
        mTvAddress = findViewById(R.id.tv_address);
        mTvScheduleContent = findViewById(R.id.tv_schedulecontent);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void setListener() {
        mBtnLocation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_location){
        }
    }

}
