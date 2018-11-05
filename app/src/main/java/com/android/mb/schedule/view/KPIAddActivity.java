package com.android.mb.schedule.view;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.base.BaseMvpActivity;
import com.android.mb.schedule.constants.ProjectConstants;
import com.android.mb.schedule.entitys.FileData;
import com.android.mb.schedule.entitys.KpiRequest;
import com.android.mb.schedule.entitys.ScheduleDetailBean;
import com.android.mb.schedule.presenter.KpiPresenter;
import com.android.mb.schedule.presenter.SchedulePresenter;
import com.android.mb.schedule.utils.AppHelper;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.ProjectHelper;
import com.android.mb.schedule.view.interfaces.IKpiView;
import com.android.mb.schedule.view.interfaces.IScheduleView;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.util.Calendar;
import java.util.Date;


/**
 * 新增实绩
 * Created by cgy on 16/7/18.
 */
public class KPIAddActivity extends BaseMvpActivity<KpiPresenter,IKpiView> implements IKpiView, View.OnClickListener{
    private EditText mEdtScheduleName; //日程名称
    private EditText mEdtScheduleContent; // 日程内容
    private EditText mEdtKpiContent; // 实绩内容
    private TextView mTvStartDate ; //开始日期
    private TextView mTvStartTime ; //开始时间
    private TextView mTvEndDate ; //结束日期
    private TextView mTvEndTime ; //结束时间
    private TimePickerView mScheduleStartTimePop;
    private TimePickerView mScheduleEndTimePop;
    public static final String mDateFormat = "yyyy年MM月dd日";
    public static final String mTimeFormat = "HH:mm";
    private Calendar mStartTime,mEndTime;

    private ScheduleDetailBean mDetailBean;
    @Override
    protected void loadIntent() {
        mDetailBean = (ScheduleDetailBean) getIntent().getSerializableExtra("detailBean");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_kpi_add;
    }

    @Override
    protected void initTitle() {
        setTitleText("日程实绩");
        setRightImage(R.mipmap.icon_checked);
    }

    @Override
    protected void onRightAction() {
        super.onRightAction();
        doSave();
    }

    @Override
    protected void bindViews() {
        mEdtScheduleName = findViewById(R.id.et_schedule_name);
        mEdtScheduleContent = findViewById(R.id.et_schedule_content);
        mEdtKpiContent = findViewById(R.id.et_kpi_content);
        mTvStartDate = findViewById(R.id.tv_start_date);
        mTvStartTime = findViewById(R.id.tv_start_time);
        mTvEndDate = findViewById(R.id.tv_end_date);
        mTvEndTime = findViewById(R.id.tv_end_time);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        initPop();
        initData();
    }

    private void initData(){
        if (mDetailBean!=null){
            mEdtScheduleName.setText(ProjectHelper.getCommonText(mDetailBean.getTitle()));
            mEdtScheduleName.setSelection(ProjectHelper.getCommonSelection(mDetailBean.getTitle()));
            mEdtScheduleContent.setText(ProjectHelper.getCommonText(mDetailBean.getDescription()));
            mEdtScheduleContent.setSelection(ProjectHelper.getCommonSelection(mDetailBean.getDescription()));
            mTvStartDate.setText(Helper.long2DateString(mDetailBean.getTime_s()*1000,mDateFormat));
            mTvStartTime.setText(Helper.long2DateString(mDetailBean.getTime_s()*1000,mTimeFormat));
            mTvEndDate.setText(Helper.long2DateString(mDetailBean.getTime_e()*1000,mDateFormat));
            mTvEndTime.setText(Helper.long2DateString(mDetailBean.getTime_e()*1000,mTimeFormat));

            Date startDate = Helper.long2Date(mDetailBean.getTime_s()*1000);
            mStartTime = (Calendar) Calendar.getInstance().clone();
            mStartTime.setTime(startDate);
            mScheduleStartTimePop.setDate(mStartTime);


            Date endDate = Helper.long2Date(mDetailBean.getTime_e()*1000);
            mEndTime = (Calendar) Calendar.getInstance().clone();
            mEndTime.setTime(endDate);
            mScheduleEndTimePop.setDate(mEndTime);
        }
    }



    @Override
    protected void setListener() {
        findViewById(R.id.lly_start_date).setOnClickListener(this);
        findViewById(R.id.lly_end_date).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.lly_start_date){
            AppHelper.hideSoftInputFromWindow(view);
            mScheduleStartTimePop.setDate(mStartTime);
            mScheduleStartTimePop.show(view);
        }else  if (id == R.id.lly_end_date){
            AppHelper.hideSoftInputFromWindow(view);
            mScheduleEndTimePop.setDate(mEndTime);
            mScheduleEndTimePop.show(view);
        }
    }

    private void initPop() {
        initStartTimePop();
        initEndTimePop();
    }

    private void initStartTimePop(){
        mScheduleStartTimePop = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                mStartTime = (Calendar) calendar.clone();
                mTvStartDate.setText(Helper.date2String(date,mDateFormat));
                mTvStartTime.setText(Helper.date2String(date,mTimeFormat));
            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                    }
                })
                .setType(new boolean[]{false, false, false, true, true, false})
                .setTitleBgColor(0xff2aaeff)
                .setSubmitColor(0xffffffff)
                .setCancelColor(0xffffffff)
                .setTitleColor(0xffffffff)
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .build();
        Dialog mDialog = mScheduleStartTimePop.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);
            params.leftMargin = 0;
            params.rightMargin = 0;
            mScheduleStartTimePop.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }
    }

    private void initEndTimePop() {
        mScheduleEndTimePop = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                mEndTime = (Calendar) calendar.clone();
                mTvEndDate.setText(Helper.date2String(date,mDateFormat));
                mTvEndTime.setText(Helper.date2String(date,mTimeFormat));
            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                    }
                })
                .setType(new boolean[]{false, false, false, true, true, false})
                .setTitleBgColor(0xff2aaeff)
                .setSubmitColor(0xffffffff)
                .setCancelColor(0xffffffff)
                .setTitleColor(0xffffffff)
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .build();
        Dialog mDialog = mScheduleEndTimePop.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);
            params.leftMargin = 0;
            params.rightMargin = 0;
            mScheduleEndTimePop.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }
    }




    @Override
    protected KpiPresenter createPresenter() {
        return new KpiPresenter();
    }

    @Override
    public void addSuccess(Object result) {
        showToastMessage("保存成功");
        sendMsg(ProjectConstants.EVENT_UPDATE_SCHEDULE_LIST,null);
        finish();
    }

    @Override
    public void editSuccess(Object result) {
        showToastMessage("修改成功");
        sendMsg(ProjectConstants.EVENT_UPDATE_SCHEDULE_LIST,null);
        sendMsg(ProjectConstants.EVENT_UPDATE_SCHEDULE,null);
        finish();
    }

    @Override
    public void getSuccess(KpiRequest result) {

    }


    private void doSave(){
        String name = mEdtScheduleName.getText().toString().trim();
        String content = mEdtScheduleContent.getText().toString().trim();
        String kpiContent = mEdtKpiContent.getText().toString().trim();
        String startDate = mTvStartDate.getText().toString().trim();
        String startTime = mTvStartTime.getText().toString().trim();
        String endDate = mTvEndDate.getText().toString().trim();
        String endTime = mTvEndTime.getText().toString().trim();
    }


}
