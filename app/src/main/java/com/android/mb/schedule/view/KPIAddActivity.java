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
import com.android.mb.schedule.entitys.CurrentUser;
import com.android.mb.schedule.entitys.FileData;
import com.android.mb.schedule.entitys.KpiRequest;
import com.android.mb.schedule.entitys.ScheduleDetailBean;
import com.android.mb.schedule.presenter.KpiPresenter;
import com.android.mb.schedule.presenter.SchedulePresenter;
import com.android.mb.schedule.utils.AppHelper;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.ProjectHelper;
import com.android.mb.schedule.utils.ToastHelper;
import com.android.mb.schedule.view.interfaces.IKpiView;
import com.android.mb.schedule.view.interfaces.IScheduleView;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


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
    private KpiRequest mKpiRequest;
    private ScheduleDetailBean mDetailBean;
    private boolean mIsValid;
    @Override
    protected void loadIntent() {
        mIsValid = getIntent().getBooleanExtra("isValid",true);
        mKpiRequest = (KpiRequest) getIntent().getSerializableExtra("kpiRequest");
        mDetailBean = (ScheduleDetailBean) getIntent().getSerializableExtra("detailBean");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_kpi_add;
    }

    @Override
    protected void initTitle() {
        setTitleText("填写实绩");
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
        initKpiInfo(mKpiRequest);
        getKpiInfo();
    }

    private void getKpiInfo(){
        if (mDetailBean!=null && mKpiRequest!=null){
            Map<String,Object> requestMap = new HashMap<>();
            requestMap.put("sid",mDetailBean.getId());
            requestMap.put("time_s",mKpiRequest.getTime_s());
            requestMap.put("time_e",mKpiRequest.getTime_e());
            mPresenter.viewKpi(requestMap);
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
            if (mIsValid){
                AppHelper.hideSoftInputFromWindow(view);
                mScheduleStartTimePop.setDate(mStartTime);
                mScheduleStartTimePop.show(view);
            }
        }else  if (id == R.id.lly_end_date){
            if (mIsValid){
                AppHelper.hideSoftInputFromWindow(view);
                mScheduleEndTimePop.setDate(mEndTime);
                mScheduleEndTimePop.show(view);
            }
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
        AppHelper.hideSoftInputFromWindow(mEdtScheduleName);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void editSuccess(Object result) {
        showToastMessage("修改成功");
        AppHelper.hideSoftInputFromWindow(mEdtScheduleName);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void getSuccess(KpiRequest result) {
        initKpiInfo(result);
    }

    private void initKpiInfo(KpiRequest result){
        if (result!=null && mDetailBean!=null){
            if (mIsValid){
                setTitleText(result.getResid()==0?"填写实绩":"修改实绩");
            }else{
                setTitleText("查看实绩");
                hideRightImage();
            }
            mEdtScheduleName.setEnabled(mIsValid);
            mEdtKpiContent.setEnabled(mIsValid);
            mKpiRequest = result;
            mEdtScheduleName.setText(ProjectHelper.getCommonText(result.getTitle()));
            mEdtScheduleName.setSelection(ProjectHelper.getCommonSelection(result.getTitle()));
            mEdtScheduleContent.setText(ProjectHelper.getCommonText(result.getDesc()));
            mEdtScheduleContent.setSelection(ProjectHelper.getCommonSelection(result.getDesc()));
            mEdtKpiContent.setText(ProjectHelper.getCommonText(result.getRes()));
            mEdtKpiContent.setSelection(ProjectHelper.getCommonSelection(result.getRes()));

            mTvStartDate.setText(Helper.long2DateString(result.getRes_time_s()*1000,mDateFormat));
            mTvStartTime.setText(Helper.long2DateString(result.getRes_time_s()*1000,mTimeFormat));
            mTvEndDate.setText(Helper.long2DateString(result.getRes_time_e()*1000,mDateFormat));
            mTvEndTime.setText(Helper.long2DateString(result.getRes_time_e()*1000,mTimeFormat));
//            int mIsAllDay = mDetailBean.getAllDay();
//            mTvStartTime.setVisibility(mIsAllDay==1?View.GONE:View.VISIBLE);
//            mTvEndTime.setVisibility(mIsAllDay==1?View.GONE:View.VISIBLE);
            Date startDate = Helper.long2Date(result.getRes_time_s()*1000);
            mStartTime = (Calendar) Calendar.getInstance().clone();
            mStartTime.setTime(startDate);
            mScheduleStartTimePop.setDate(mStartTime);

            Date endDate = Helper.long2Date(result.getRes_time_e()*1000);
            mEndTime = (Calendar) Calendar.getInstance().clone();
            mEndTime.setTime(endDate);
            mScheduleEndTimePop.setDate(mEndTime);
        }
    }


    private void doSave(){
        String name = mEdtScheduleName.getText().toString().trim();
        String content = mEdtScheduleContent.getText().toString().trim();
        String kpiContent = mEdtKpiContent.getText().toString().trim();
        if (Helper.isEmpty(name)){
            showToastMessage("请输入日程名称");
            return;
        }
        if (mStartTime.getTimeInMillis()>=mEndTime.getTimeInMillis()){
            showToastMessage("开始时间必须大于结束时间");
            return;
        }
        if (mKpiRequest==null){
            mKpiRequest = new KpiRequest();
            mKpiRequest.setSid(mDetailBean.getId());
        }
        mKpiRequest.setTitle(name);
        mKpiRequest.setDesc(content);
        mKpiRequest.setRes(kpiContent);
        mKpiRequest.setRes_time_s(mStartTime.getTimeInMillis()/1000);
        mKpiRequest.setRes_time_e(mEndTime.getTimeInMillis()/1000);
        mKpiRequest.setTime_s(mKpiRequest.getTime_s());
        mKpiRequest.setTime_e(mKpiRequest.getTime_e());
        mPresenter.addKpi(mKpiRequest);
    }

}
