package com.android.mb.schedule.view;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.mb.schedule.R;
import com.android.mb.schedule.base.BaseActivity;
import com.android.mb.schedule.base.BaseMvpActivity;
import com.android.mb.schedule.entitys.ScheduleRequest;
import com.android.mb.schedule.pop.ScheduleRemindPop;
import com.android.mb.schedule.pop.ScheduleRepeatPop;
import com.android.mb.schedule.pop.ScheduleTimePop;
import com.android.mb.schedule.presenter.LoginPresenter;
import com.android.mb.schedule.presenter.SchedulePresenter;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.ToastHelper;
import com.android.mb.schedule.utils.ToastUtils;
import com.android.mb.schedule.view.interfaces.ILoginView;
import com.android.mb.schedule.view.interfaces.IScheduleView;
import com.android.mb.schedule.widget.DatePicker;
import com.android.mb.schedule.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;


/**
 * 新增日程
 * Created by cgy on 16/7/18.
 */
public class NewScheduleActivity extends BaseMvpActivity<SchedulePresenter,IScheduleView> implements IScheduleView, View.OnClickListener{

    private EditText mEdtScheduleName; //日程名称
    private TextView mBtnLocation; //定位
    private TextView mTvAddress ; //位置
    private TextView mEdtScheduleContent; // 日程内容
    private TextView mTvUploadDocument; // 点击上传文件
//    private TextView mBtnChangeDocument ; //点击替换附件
    private LinearLayout mLlyStartDate ;
    private TextView mTvStartDate ; //开始日期
    private TextView mTvStartTime ; //开始时间
    private LinearLayout mLlyEndDate;
    private TextView mTvEndDate ; //结束日期
    private TextView mTvEndTime ; //结束时间
    private ImageView mIvAllDay ; //全天提醒
    private boolean mIsAllDay = false;
    private TextView mBtnAdd;// 添加相关人员
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
        mScheduleRequest.setTitle(name);
        mScheduleRequest.setDescription(content);
        mScheduleRequest.setAddress("亚太中心");
        mScheduleRequest.setImportant(mIsImport?1:0);
        mScheduleRequest.setAllDay(mIsAllDay?1:0);
        mScheduleRequest.setRelated("");
        mScheduleRequest.setSummary("");
        mScheduleRequest.setShare("");
        mScheduleRequest.setStart(start.getTime());
        mScheduleRequest.setEnd(end.getTime());
        mScheduleRequest.setRemind(mScheduleRemindPop.getType());
        mScheduleRequest.setRepeattype(mScheduleRepeatPop.getType());
        mPresenter.addSchedule(mScheduleRequest);
    }

    @Override
    protected void bindViews() {
        mEdtScheduleName = findViewById(R.id.et_schedule_name);
        mBtnLocation = findViewById(R.id.tv_location);
        mTvAddress = findViewById(R.id.tv_address);
        mEdtScheduleContent = findViewById(R.id.et_schedule_content);
        mTvUploadDocument = findViewById(R.id.tv_upload_document);
        mLlyStartDate = findViewById(R.id.lly_start_date);
        mTvStartDate = findViewById(R.id.tv_start_date);
        mTvStartTime = findViewById(R.id.tv_start_time);
        mLlyEndDate = findViewById(R.id.lly_end_date);
        mTvEndDate = findViewById(R.id.tv_end_date);
        mTvEndTime = findViewById(R.id.tv_end_time);
        mIvAllDay = findViewById(R.id.iv_all_day);
        mBtnAdd = findViewById(R.id.tv_add);
        mIvNoRemind = findViewById(R.id.iv_no_remind);
        mIvShareToOther = findViewById(R.id.iv_share_other);
        mTvRepeat = findViewById(R.id.tv_repeat);
        mTvWhenRemind = findViewById(R.id.tv_when_remind);
        mIvImport = findViewById(R.id.iv_importment);
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
        mBtnLocation.setOnClickListener(this);
        mIvAllDay.setOnClickListener(this);
        mBtnAdd.setOnClickListener(this);
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
        if (id == R.id.tv_location){

        }else  if (id == R.id.tv_upload_document){

        }else  if (id == R.id.iv_all_day){
            mIsAllDay = !mIsAllDay;
            mIvAllDay.setImageResource(mIsAllDay?R.mipmap.ic_vibrate_open:R.mipmap.ic_vibrate_close);
        }else  if (id == R.id.tv_add){
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
        }else  if (id == R.id.iv_importment){
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
                switch (type){
                    case 1:
                        mTvRepeat.setText("一次性");
                        break;
                    case 2:
                        mTvRepeat.setText("每天");
                        break;
                    case 3:
                        mTvRepeat.setText("每周");
                        break;
                    case 4:
                        mTvRepeat.setText("每月");
                        break;
                    default:
                        break;
                }
            }
        });
        mScheduleRemindPop = new ScheduleRemindPop(this, new ScheduleRemindPop.SelectListener() {
            @Override
            public void onSelected(int type) {
                switch (type){
                    case 0:
                        mTvWhenRemind.setText("不在提醒");
                        break;
                    case 1:
                        mTvWhenRemind.setText("10分钟前");
                        break;
                    case 2:
                        mTvWhenRemind.setText("15分钟前");
                        break;
                    case 3:
                        mTvWhenRemind.setText("30分钟前");
                        break;
                    case 4:
                        mTvWhenRemind.setText("1小时前");
                        break;
                    case 5:
                        mTvWhenRemind.setText("2小时前");
                        break;
                    case 6:
                        mTvWhenRemind.setText("24小时前");
                        break;
                    case 7:
                        mTvWhenRemind.setText("2天前");
                        break;
                    default:
                        break;
                }
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
        finish();
    }

    @Override
    public void editSuccess(Object result) {
        showToastMessage("修改成功");
    }
}
