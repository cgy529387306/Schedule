package com.android.mb.schedule.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.base.BaseMvpActivity;
import com.android.mb.schedule.constants.ProjectConstants;
import com.android.mb.schedule.entitys.FileData;
import com.android.mb.schedule.entitys.ScheduleRequest;
import com.android.mb.schedule.pop.ScheduleRemindPop;
import com.android.mb.schedule.pop.ScheduleRepeatPop;
import com.android.mb.schedule.pop.ScheduleTimePop;
import com.android.mb.schedule.presenter.SchedulePresenter;
import com.android.mb.schedule.retrofit.cache.util.Utils;
import com.android.mb.schedule.utils.AppHelper;
import com.android.mb.schedule.utils.FileUtils;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.NavigationHelper;
import com.android.mb.schedule.utils.ProjectHelper;
import com.android.mb.schedule.utils.ToastHelper;
import com.android.mb.schedule.view.interfaces.IScheduleView;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * 新增日程
 * Created by cgy on 16/7/18.
 */
public class NewScheduleActivity extends BaseMvpActivity<SchedulePresenter,IScheduleView> implements IScheduleView, View.OnClickListener{

    private EditText mEdtScheduleName; //日程名称
    private TextView mTvAddress ; //位置
    private TextView mEdtScheduleContent; // 日程内容
    private TextView mTvUploadDocument; // 点击上传文件
    private TextView mTvFileName;//文件名称
    private LinearLayout mLlyStartDate ;
    private TextView mTvStartDate ; //开始日期
    private TextView mTvStartTime ; //开始时间
    private LinearLayout mLlyEndDate;
    private TextView mTvEndDate ; //结束日期
    private TextView mTvEndTime ; //结束时间
    private ImageView mIvAllDay ; //全天提醒
    private boolean mIsAllDay = false;
    private ImageView mIvAddPerson;// 添加相关人员
    private ImageView mIvNoRemind; // 是否提醒相关人员
    private boolean mIsNoRemind = false;
    private ImageView mIvShareToOther; //分享给其他人
    private TextView mTvRepeat; //重复
    private TextView mTvWhenRemind; // 日程什么时候开始提醒
    private ImageView mIvImport; //是否重要
    private boolean mIsImport = false;
    private ScheduleRepeatPop mScheduleRepeatPop;
    private ScheduleRemindPop mScheduleRemindPop;
    private ScheduleTimePop mScheduleStartTimePop;
    private ScheduleTimePop mScheduleEndTimePop;
    private ScheduleRequest mScheduleRequest;
    private long mFileId = -1;
    public static final String mDateFormat = "yyyy年MM月dd日";
    public static final String mTimeFormat = "HH:mm";
    @Override
    protected void loadIntent() {
        
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_schedule;
    }

    @Override
    protected void initTitle() {
        setTitleText("新建日程");
        setRightImage(R.mipmap.icon_checked);
    }

    @Override
    protected void onRightAction() {
        super.onRightAction();
        String name = mEdtScheduleName.getText().toString().trim();
        String content = mEdtScheduleContent.getText().toString().trim();
        String address = mTvAddress.getText().toString().trim();
        String startDate = mTvStartDate.getText().toString().trim();
        String startTime = mTvStartTime.getText().toString().trim();
        String endDate = mTvEndDate.getText().toString().trim();
        String endTime = mTvEndTime.getText().toString().trim();
        Date start = Helper.string2Date(startDate+startTime,mDateFormat+mTimeFormat);
        Date end = Helper.string2Date(endDate+endTime,mDateFormat+mTimeFormat);
        if (Helper.isEmpty(name)){
            showToastMessage("请输入日程名称");
            return;
        }
        if (Helper.isEmpty(content)){
            showToastMessage("请输入日程内容");
            return;
        }
        if (start.getTime()>=end.getTime()){
            showToastMessage("开始时间必须大于结束时间");
            return;
        }
        if (mFileId!=-1){
            mScheduleRequest.setFid(mFileId);
        }
        mScheduleRequest.setTitle(name);
        mScheduleRequest.setDescription(content);
        mScheduleRequest.setAddress(address);
        mScheduleRequest.setImportant(mIsImport?1:0);
        mScheduleRequest.setAllDay(mIsAllDay?1:0);
        mScheduleRequest.setRelated("2000,2001");
        mScheduleRequest.setSummary("");
        mScheduleRequest.setShare("2000,2001");
        mScheduleRequest.setStart(start.getTime()/1000);
        mScheduleRequest.setEnd(end.getTime()/1000);
        mScheduleRequest.setRemind(mScheduleRemindPop.getType());
        mScheduleRequest.setRepeattype(mScheduleRepeatPop.getType());
        mPresenter.addSchedule(mScheduleRequest);
    }

    @Override
    protected void bindViews() {
        mEdtScheduleName = findViewById(R.id.et_schedule_name);
        mTvAddress = findViewById(R.id.tv_address);
        mEdtScheduleContent = findViewById(R.id.et_schedule_content);
        mTvUploadDocument = findViewById(R.id.tv_upload_document);
        mTvFileName = findViewById(R.id.tv_file_name);
        mLlyStartDate = findViewById(R.id.lly_start_date);
        mTvStartDate = findViewById(R.id.tv_start_date);
        mTvStartTime = findViewById(R.id.tv_start_time);
        mLlyEndDate = findViewById(R.id.lly_end_date);
        mTvEndDate = findViewById(R.id.tv_end_date);
        mTvEndTime = findViewById(R.id.tv_end_time);
        mIvAllDay = findViewById(R.id.iv_all_day);
        mIvAddPerson = findViewById(R.id.iv_add_person);
        mIvNoRemind = findViewById(R.id.iv_no_remind);
        mIvShareToOther = findViewById(R.id.iv_share_other);
        mTvRepeat = findViewById(R.id.tv_repeat);
        mTvWhenRemind = findViewById(R.id.tv_when_remind);
        mIvImport = findViewById(R.id.iv_import);
        choosePop();
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        initData();
        if (mScheduleRequest == null){
            mScheduleRequest = new ScheduleRequest();
        }
    }

    @Override
    protected void setListener() {
        findViewById(R.id.lly_address).setOnClickListener(this);
        mIvAllDay.setOnClickListener(this);
        mIvAddPerson.setOnClickListener(this);
        mIvNoRemind.setOnClickListener(this);
        mIvShareToOther.setOnClickListener(this);
        mTvRepeat.setOnClickListener(this);
        mTvWhenRemind.setOnClickListener(this);
        mIvImport.setOnClickListener(this);
        mTvUploadDocument.setOnClickListener(this);
        mLlyStartDate.setOnClickListener(this);
        mLlyEndDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.lly_address){
            NavigationHelper.startActivityForResult(NewScheduleActivity.this,SelectAddressActivity.class,null,ProjectConstants.REQUEST_SELECT_ADDRESS);
        }else  if (id == R.id.tv_upload_document){
            handlerFileIntent();
        }else  if (id == R.id.iv_all_day){
            mIsAllDay = !mIsAllDay;
            mIvAllDay.setImageResource(mIsAllDay?R.mipmap.ic_vibrate_open:R.mipmap.ic_vibrate_close);
        }else  if (id == R.id.iv_add_person){
            NavigationHelper.startActivity(NewScheduleActivity.this,SelectPersonActivity.class,null,false);
        }else  if (id == R.id.iv_no_remind){
            mIsNoRemind = !mIsNoRemind;
            mIvNoRemind.setImageResource(mIsNoRemind?R.mipmap.ic_vibrate_open:R.mipmap.ic_vibrate_close);
        }else  if (id == R.id.iv_share_other){
        }else  if (id == R.id.tv_repeat){
            if(mScheduleRepeatPop != null){
                mScheduleRepeatPop.showPopupWindow(view);
            }
        }else  if (id == R.id.tv_when_remind){
            if(mScheduleRemindPop != null){
                mScheduleRemindPop.showPopupWindow(view);
            }
        }else  if (id == R.id.iv_import){
            mIsImport = !mIsImport;
            mIvImport.setImageResource(mIsImport?R.mipmap.ic_vibrate_open:R.mipmap.ic_vibrate_close);
        }else  if (id == R.id.lly_start_date){
            if(mScheduleStartTimePop != null){
                mScheduleStartTimePop.showPopupWindow(view);
            }
        }else  if (id == R.id.lly_end_date){
            if(mScheduleEndTimePop != null){
                mScheduleEndTimePop.showPopupWindow(view);
            }
        }
    }

    private void choosePop() {
        mScheduleRepeatPop = new ScheduleRepeatPop(this, new ScheduleRepeatPop.SelectListener() {
            @Override
            public void onSelected(int type) {
                mTvRepeat.setText(ProjectHelper.getRepeatStr(type));
            }
        });
        mScheduleRemindPop = new ScheduleRemindPop(this, new ScheduleRemindPop.SelectListener() {
            @Override
            public void onSelected(int type) {
             mTvWhenRemind.setText(ProjectHelper.getRemindStr(type));
            }
        });
        mScheduleStartTimePop = new ScheduleTimePop(this, new ScheduleTimePop.SelectListener() {
            @Override
            public void onSelected(String selectDate, String selectTime) {
                mTvStartDate.setText(selectDate);
                mTvStartTime.setText(selectTime);
            }
        });
        mScheduleStartTimePop.setType(0);
        mScheduleEndTimePop = new ScheduleTimePop(this, new ScheduleTimePop.SelectListener() {
            @Override
            public void onSelected(String selectDate, String selectTime) {
                mTvEndDate.setText(selectDate);
                mTvEndTime.setText(selectTime);
            }
        });
        mScheduleEndTimePop.setType(1);
    }

    private void initData(){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY)+1;
        String hourStr = hour<10?("0"+hour):""+hour;
        mTvStartDate.setText(Helper.date2String(calendar.getTime(),mDateFormat));
        mTvStartTime.setText(String.format("%s:%s", hourStr, "00"));

        calendar.add(Calendar.HOUR_OF_DAY,1);
        int endHour = calendar.get(Calendar.HOUR_OF_DAY)+1;
        String endHourStr = endHour<10?("0"+endHour):""+endHour;
        mTvEndDate.setText(Helper.date2String(calendar.getTime(),mDateFormat));
        mTvEndTime.setText(String.format("%s:%s", endHourStr, "00"));
    }

    @Override
    protected SchedulePresenter createPresenter() {
        return new SchedulePresenter();
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
    }

    @Override
    public void uploadSuccess(FileData result) {
        if (result!=null){
            showToastMessage("上传成功");
            String fileUrl = result.getFile();
            if (Helper.isNotEmpty(fileUrl)){
                String fileName = fileUrl.substring(fileUrl.lastIndexOf("/")+1);
                if (Helper.isNotEmpty(fileName)){
                    mFileId = result.getId();
                    mTvFileName.setVisibility(View.VISIBLE);
                    mTvFileName.setText(fileName);
                    mTvUploadDocument.setText("点击替换附件");
                }
            }
        }
    }

    private void handlerFileIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, ProjectConstants.REQUEST_SELECT_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (ProjectConstants.REQUEST_SELECT_FILE == requestCode) {
            final Uri uri = data.getData();
            if (uri != null){
                String filePath;
                if ("file".equalsIgnoreCase(uri.getScheme())) {
                    filePath = uri.getPath();
                } else {
                    filePath = FileUtils.getPath(this, uri);
                }
                mPresenter.uploadFile(new File(filePath));
            }
        }else if (ProjectConstants.REQUEST_SELECT_ADDRESS == requestCode){
            String address = data.getStringExtra("address");
            mTvAddress.setText(ProjectHelper.getCommonText(address));
        }
    }


}
