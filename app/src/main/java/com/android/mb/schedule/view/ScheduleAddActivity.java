package com.android.mb.schedule.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
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
import com.android.mb.schedule.entitys.CurrentUser;
import com.android.mb.schedule.entitys.FileData;
import com.android.mb.schedule.entitys.ScheduleDetailData;
import com.android.mb.schedule.entitys.ScheduleRequest;
import com.android.mb.schedule.entitys.UserBean;
import com.android.mb.schedule.pop.ScheduleRemindPop;
import com.android.mb.schedule.pop.ScheduleRepeatPop;
import com.android.mb.schedule.pop.ScheduleTimePop;
import com.android.mb.schedule.presenter.SchedulePresenter;
import com.android.mb.schedule.utils.AppHelper;
import com.android.mb.schedule.utils.FileUtils;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.JsonHelper;
import com.android.mb.schedule.utils.NavigationHelper;
import com.android.mb.schedule.utils.NetworkHelper;
import com.android.mb.schedule.utils.PreferencesHelper;
import com.android.mb.schedule.utils.ProjectHelper;
import com.android.mb.schedule.view.interfaces.IScheduleView;
import com.android.mb.schedule.widget.BottomMenuDialog;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * 新增日程
 * Created by cgy on 16/7/18.
 */
public class ScheduleAddActivity extends BaseMvpActivity<SchedulePresenter,IScheduleView> implements IScheduleView, View.OnClickListener{
    private EditText mEdtScheduleName; //日程名称
    private TextView mTvAddress ; //位置
    private EditText mEdtScheduleContent; // 日程内容
    private TextView mTvUploadDocument; // 点击上传文件
    private TextView mTvFileName;//文件名称
    private LinearLayout mLlyStartDate ;
    private TextView mTvStartDate ; //开始日期
    private TextView mTvStartTime ; //开始时间
    private LinearLayout mLlyEndDate;
    private TextView mTvEndDate ; //结束日期
    private TextView mTvEndTime ; //结束时间
    private TextView mTvPersons;
    private ImageView mIvAllDay ; //全天提醒
    private int mIsAllDay = 0;
    private ImageView mIvAddPerson;// 添加相关人员
    private ImageView mIvNoRemind; // 是否提醒相关人员
    private boolean mIsNoRemind = false;
    private TextView mTvShare; //分享给其他人
    private TextView mTvRepeat; //重复
    private TextView mTvWhenRemind; // 日程什么时候开始提醒
    private ImageView mIvImport; //是否重要
    private int mIsImport = 0;
    private ScheduleRepeatPop mScheduleRepeatPop;
    private ScheduleRemindPop mScheduleRemindPop;
    private ScheduleTimePop mScheduleStartTimePop;
    private ScheduleTimePop mScheduleEndTimePop;
    private ScheduleRequest mScheduleRequest;
    private long mFileId = -1;
    private int mType;//0:新建 1:编辑
    public static final String mDateFormat = "yyyy年MM月dd日";
    public static final String mTimeFormat = "HH:mm";
    private List<UserBean> mRelatePersons = new ArrayList<>();
    private List<UserBean> mSharePersons = new ArrayList<>();
    private String mDateStr;
    private String mLocalKey;
    private ScheduleDetailData mDetailData;
    private BottomMenuDialog mCheckDialog;
    private boolean mIsRepeatChange;
    private String mBeginTime,mEndTime;
    @Override
    protected void loadIntent() {
        mLocalKey = "ScheduleRequest"+ CurrentUser.getInstance().getId();
        mType = getIntent().getIntExtra("type",0);
        mDateStr = getIntent().getStringExtra("date");
        if (mType==1){
            mDetailData = (ScheduleDetailData) getIntent().getSerializableExtra("schedule");
            mScheduleRequest = ProjectHelper.transBean(mDetailData);
        }else{
            String localStr = PreferencesHelper.getInstance().getString(mLocalKey);
            if (Helper.isNotEmpty(localStr)){
                mScheduleRequest = JsonHelper.fromJson(localStr,ScheduleRequest.class);
            }
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_schedule_add;
    }

    @Override
    protected void initTitle() {
        setTitleText(mType==1?"编辑日程":"新建日程");
        setRightImage(R.mipmap.icon_checked);
    }

    @Override
    protected void onRightAction() {
        super.onRightAction();
        AppHelper.hideSoftInputFromWindow(mEdtScheduleName);
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
        if (Helper.isEmpty(address)){
            showToastMessage("请输入地点");
            return;
        }
        if (start.getTime()>=end.getTime()){
            showToastMessage("开始时间必须大于结束时间");
            return;
        }
        if (mScheduleRequest==null){
            mScheduleRequest=new ScheduleRequest();
        }
        if (mFileId!=-1){
            mScheduleRequest.setFid(mFileId);
        }
        mScheduleRequest.setTitle(name);
        mScheduleRequest.setDescription(content);
        mScheduleRequest.setAddress(address);
        mScheduleRequest.setImportant(mIsImport);
        mScheduleRequest.setAllDay(mIsAllDay);
        mScheduleRequest.setRelated(Helper.isEmpty(mRelatePersons)?"":ProjectHelper.getIdStr(mRelatePersons));
        mScheduleRequest.setShare(Helper.isEmpty(mSharePersons)?"":ProjectHelper.getIdStr(mSharePersons));
        mScheduleRequest.setSummary("");
        mScheduleRequest.setStart(start.getTime()/1000);
        mScheduleRequest.setEnd(end.getTime()/1000);
        mScheduleRequest.setRemind(mScheduleRemindPop.getType());
        mScheduleRequest.setRepeattype(mScheduleRepeatPop.getType());
        if (NetworkHelper.isNetworkAvailable(mContext)){
            if (mIsRepeatChange){
                checkRepeatChange();
            }else{
                doRequest();
            }

        }else{
            showToastMessage("当前网络不可用，待网络连接后再保存日程");
            PreferencesHelper.getInstance().putString(mLocalKey, JsonHelper.toJson(mScheduleRequest));
        }
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
        mTvPersons = findViewById(R.id.tv_persons);
        mIvAddPerson = findViewById(R.id.iv_add_person);
        mIvNoRemind = findViewById(R.id.iv_no_remind);
        mTvShare = findViewById(R.id.tv_share);
        mTvRepeat = findViewById(R.id.tv_repeat);
        mTvWhenRemind = findViewById(R.id.tv_when_remind);
        mIvImport = findViewById(R.id.iv_import);
        choosePop();
    }

    private void initData(){
        if (mScheduleRequest!=null){
            mEdtScheduleName.setText(ProjectHelper.getCommonText(mScheduleRequest.getTitle()));
            mEdtScheduleName.setSelection(ProjectHelper.getCommonSelection(mScheduleRequest.getTitle()));
            mTvAddress.setText(ProjectHelper.getCommonText(mScheduleRequest.getAddress()));
            mEdtScheduleContent.setText(ProjectHelper.getCommonText(mScheduleRequest.getDescription()));
            mEdtScheduleContent.setSelection(ProjectHelper.getCommonSelection(mScheduleRequest.getDescription()));
            mTvStartDate.setText(Helper.long2DateString(mScheduleRequest.getStart()*1000,mDateFormat));
            mTvStartTime.setText(Helper.long2DateString(mScheduleRequest.getStart()*1000,mTimeFormat));
            mTvEndDate.setText(Helper.long2DateString(mScheduleRequest.getEnd()*1000,mDateFormat));
            mTvEndTime.setText(Helper.long2DateString(mScheduleRequest.getEnd()*1000,mTimeFormat));
            Calendar startCalendar = (Calendar) Calendar.getInstance().clone();
            startCalendar.setTime(Helper.long2Date(mScheduleRequest.getStart()*1000));
            mScheduleStartTimePop.setTime(startCalendar);
            Calendar endCalendar = (Calendar) Calendar.getInstance().clone();
            endCalendar.setTime(Helper.long2Date(mScheduleRequest.getEnd()*1000));
            mScheduleEndTimePop.setTime(endCalendar);

            mIsAllDay = mScheduleRequest.getAllDay();
            mIvAllDay.setImageResource(mIsAllDay==1?R.mipmap.ic_vibrate_open:R.mipmap.ic_vibrate_close);
            mIsImport = mScheduleRequest.getImportant();
            mIvImport.setImageResource(mIsImport==1?R.mipmap.ic_vibrate_open:R.mipmap.ic_vibrate_close);
            mTvRepeat.setText(ProjectHelper.getRepeatStr(mScheduleRequest.getRepeattype()));
            mTvWhenRemind.setText(ProjectHelper.getRemindStr(mScheduleRequest.getRemind()));

            if (Helper.isNotEmpty(mDetailData.getRelated())){
                mRelatePersons = mDetailData.getRelated();
                String relateStr = String.format(mContext.getString(R.string.relate_person), ProjectHelper.getSharePersonStr(mRelatePersons),mRelatePersons.size());
                mTvPersons.setText(relateStr);
            }
            if (Helper.isNotEmpty(mDetailData.getShare())){
                mSharePersons = mDetailData.getShare();
                String shareStr = String.format(mContext.getString(R.string.relate_person), ProjectHelper.getSharePersonStr(mSharePersons),mSharePersons.size());
                mTvShare.setText(shareStr);
            }
        }
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        if (mType == 1){
            initData();
        }else{
            initDate();
            initData();
        }
    }


    @Override
    protected void setListener() {
        findViewById(R.id.lin_share).setOnClickListener(this);
        findViewById(R.id.lly_address).setOnClickListener(this);
        mIvAllDay.setOnClickListener(this);
        mIvAddPerson.setOnClickListener(this);
        mIvNoRemind.setOnClickListener(this);
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
            NavigationHelper.startActivityForResult(ScheduleAddActivity.this,SelectAddressActivity.class,null,ProjectConstants.REQUEST_SELECT_ADDRESS);
        }else  if (id == R.id.tv_upload_document){
            handlerFileIntent();
        }else  if (id == R.id.iv_all_day){
            mIsAllDay = mIsAllDay==0?1:0;
            mIvAllDay.setImageResource(mIsAllDay==1?R.mipmap.ic_vibrate_open:R.mipmap.ic_vibrate_close);
            Date date = Helper.isNotEmpty(mDateStr)?Helper.string2Date(mDateStr):new Date();
            mTvStartDate.setText(Helper.date2String(date,mDateFormat));
            mTvStartTime.setText("00:00");
            mTvEndDate.setText(Helper.date2String(date,mDateFormat));
            mTvEndTime.setText("23:59");
        }else  if (id == R.id.iv_add_person){
            Bundle bundle = new Bundle();
            bundle.putSerializable("selectPerson", (Serializable) mRelatePersons);
            NavigationHelper.startActivityForResult(ScheduleAddActivity.this,SelectPersonActivity.class,bundle,ProjectConstants.REQUEST_SELECT_PERSON);
        }else  if (id == R.id.iv_no_remind){
            mIsNoRemind = !mIsNoRemind;
            mIvNoRemind.setImageResource(mIsNoRemind?R.mipmap.ic_vibrate_open:R.mipmap.ic_vibrate_close);
        }else  if (id == R.id.lin_share){
            Bundle bundle = new Bundle();
            bundle.putSerializable("selectPerson", (Serializable) mSharePersons);
            NavigationHelper.startActivityForResult(ScheduleAddActivity.this,SelectPersonActivity.class,bundle,ProjectConstants.REQUEST_SELECT_SHARE);
        }else  if (id == R.id.tv_repeat){
            if(mScheduleRepeatPop != null){
                mScheduleRepeatPop.showPopupWindow(view);
            }
        }else  if (id == R.id.tv_when_remind){
            if(mScheduleRemindPop != null){
                mScheduleRemindPop.showPopupWindow(view);
            }
        }else  if (id == R.id.iv_import){
            mIsImport = mIsImport==0?1:0;
            mIvImport.setImageResource(mIsImport==1?R.mipmap.ic_vibrate_open:R.mipmap.ic_vibrate_close);
        }else  if (id == R.id.lly_start_date){
            if(mScheduleStartTimePop != null){
                if (Helper.isNotEmpty(mBeginTime)){
                    Calendar calendar = Calendar.getInstance();
                    Date date = Helper.string2Date(mBeginTime,mDateFormat+mTimeFormat);
                    calendar.setTime(date);
                    mScheduleStartTimePop.setTime(calendar);
                }
                mScheduleStartTimePop.showPopupWindow(view);
            }
        }else  if (id == R.id.lly_end_date){
            if(mScheduleEndTimePop != null){
                if (Helper.isNotEmpty(mEndTime)){
                    Calendar calendar = Calendar.getInstance();
                    Date date = Helper.string2Date(mEndTime,mDateFormat+mTimeFormat);
                    calendar.setTime(date);
                    mScheduleEndTimePop.setTime(calendar);
                }
                mScheduleEndTimePop.showPopupWindow(view);
            }
        }
    }

    private void choosePop() {
        int repeatType = mType==1?mScheduleRequest.getRepeattype():1;
        int remindType = mType==1?mScheduleRequest.getRemind():1;
        mScheduleRepeatPop = new ScheduleRepeatPop(this, repeatType,new ScheduleRepeatPop.SelectListener() {
            @Override
            public void onSelected(int type) {
                if (mType==1){
                    mIsRepeatChange = type!=mScheduleRequest.getRemind();
                }
                mTvRepeat.setText(ProjectHelper.getRepeatStr(type));
            }
        });
        mScheduleRemindPop = new ScheduleRemindPop(this, remindType,new ScheduleRemindPop.SelectListener() {
            @Override
            public void onSelected(int type) {
             mTvWhenRemind.setText(ProjectHelper.getRemindStr(type));
            }
        });
        mScheduleStartTimePop = new ScheduleTimePop(this, new ScheduleTimePop.SelectListener() {
            @Override
            public void onSelected(String selectDate, String selectTime) {
                mBeginTime = selectDate+selectTime;
                mTvStartDate.setText(selectDate);
                mTvStartTime.setText(selectTime);
            }
        });

        mScheduleEndTimePop = new ScheduleTimePop(this, new ScheduleTimePop.SelectListener() {
            @Override
            public void onSelected(String selectDate, String selectTime) {
                mEndTime = selectDate+selectTime;
                mTvEndDate.setText(selectDate);
                mTvEndTime.setText(selectTime);
            }
        });
    }

    private void initDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY,1);
        if (Helper.isNotEmpty(mDateStr) && Helper.string2Date(mDateStr)!=null){
            calendar.setTime(Helper.string2Date(mDateStr));
        }
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String hourStr = hour<10?("0"+hour):""+hour;
        mBeginTime = Helper.date2String(calendar.getTime(),mDateFormat+mTimeFormat);
        mTvStartDate.setText(Helper.date2String(calendar.getTime(),mDateFormat));
        mTvStartTime.setText(String.format("%s:%s", hourStr, "00"));
        mScheduleStartTimePop.setTime(calendar);


        calendar.add(Calendar.HOUR_OF_DAY,1);
        int endHour = calendar.get(Calendar.HOUR_OF_DAY);
        String endHourStr = endHour<10?("0"+endHour):""+endHour;
        mEndTime = Helper.date2String(calendar.getTime(),mDateFormat+mTimeFormat);
        mTvEndDate.setText(Helper.date2String(calendar.getTime(),mDateFormat));
        mTvEndTime.setText(String.format("%s:%s", endHourStr, "00"));
        mScheduleEndTimePop.setTime(calendar);
    }

    @Override
    protected SchedulePresenter createPresenter() {
        return new SchedulePresenter();
    }

    @Override
    public void addSuccess(Object result) {
        showToastMessage("保存成功");
        PreferencesHelper.getInstance().putString(mLocalKey, "");
        sendMsg(ProjectConstants.EVENT_UPDATE_SCHEDULE_LIST,null);
        finish();
    }

    @Override
    public void editSuccess(Object result) {
        showToastMessage("修改成功");
        PreferencesHelper.getInstance().putString(mLocalKey, "");
        sendMsg(ProjectConstants.EVENT_UPDATE_SCHEDULE_LIST,null);
        sendMsg(ProjectConstants.EVENT_UPDATE_SCHEDULE,null);
        finish();
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
        }else if (ProjectConstants.REQUEST_SELECT_PERSON == requestCode){
            mRelatePersons = (List<UserBean>) data.getSerializableExtra("selectPerson");
            if (Helper.isEmpty(mRelatePersons)){
                mTvPersons.setText("");
            }else{
                String relateStr = String.format(mContext.getString(R.string.relate_person), ProjectHelper.getSharePersonStr(mRelatePersons),mRelatePersons.size());
                mTvPersons.setText(relateStr);
            }

        }else if (ProjectConstants.REQUEST_SELECT_SHARE == requestCode){
            mSharePersons = (List<UserBean>) data.getSerializableExtra("selectPerson");
            if (Helper.isEmpty(mSharePersons)){
                mTvShare.setText("");
            }else{
                String shareStr = String.format(mContext.getString(R.string.relate_person), ProjectHelper.getSharePersonStr(mSharePersons),mSharePersons.size());
                mTvShare.setText(shareStr);
            }
        }
    }

    private void checkRepeatChange() {
        if (mCheckDialog == null) {
            mCheckDialog = new BottomMenuDialog.Builder(ScheduleAddActivity.this)
                    .addMenu("更改所有重复的活动", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mCheckDialog.dismiss();
                            mScheduleRequest.setType(0);
                            doRequest();
                        }
                    }).addMenu("更改此活动和所有将来的活动", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mCheckDialog.dismiss();
                            mScheduleRequest.setType(1);
                            doRequest();
                        }
                    }).create();
        }
        mCheckDialog.show();
    }

    private void doRequest(){
        if (mType==1){
            mPresenter.editSchedule(mScheduleRequest);
        }else{
            mPresenter.addSchedule(mScheduleRequest);
        }
    }


}
