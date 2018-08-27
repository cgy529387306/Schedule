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
import com.android.mb.schedule.pop.ScheduleRemindPop;
import com.android.mb.schedule.pop.ScheduleRepeatPop;
import com.android.mb.schedule.pop.ScheduleTimePop;
import com.android.mb.schedule.utils.ToastHelper;
import com.android.mb.schedule.utils.ToastUtils;
import com.android.mb.schedule.widget.DatePicker;
import com.android.mb.schedule.widget.TimePicker;

import java.util.Calendar;


/**
 * 订单
 * Created by cgy on 16/7/18.
 */
public class NewScheduleActivity extends BaseActivity implements View.OnClickListener{

    private EditText mEdtScheduleName; //日程名称
    private TextView mBtnLocation; //定位
    private TextView mTvAddress ; //位置
    private TextView mEdtScheduleContent; // 日程内容
    private TextView mTvUploadDocument; // 点击上传文件
    private TextView mBtnChangeDocument ; //点击替换附件
    private LinearLayout mLlyStartDate ;
    private TextView mTvStartDate ; //开始日期
    private TextView mTvStartTime ; //开始时间
    private LinearLayout mLlyEndDate;
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
    private ScheduleRepeatPop mScheduleRepeatPop;
    private ScheduleRemindPop mScheduleRemindPop;
    private ScheduleTimePop mScheduleStartTimePop;
    private ScheduleTimePop mScheduleEndTimePop;

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
    }

    @Override
    protected void bindViews() {
        mEdtScheduleName = findViewById(R.id.edt_schedulename);
        mBtnLocation = findViewById(R.id.tv_location);
        mTvAddress = findViewById(R.id.tv_address);
        mEdtScheduleContent = findViewById(R.id.edt_schedulecontent);
        mTvUploadDocument = findViewById(R.id.tv_uploaddocument);
        mBtnChangeDocument = findViewById(R.id.tv_changedocument);
        mLlyStartDate = findViewById(R.id.lly_startdate);
        mTvStartDate = findViewById(R.id.tv_startdate);
        mTvStartTime = findViewById(R.id.tv_starttime);
        mLlyEndDate = findViewById(R.id.lly_enddate);
        mTvEndDate = findViewById(R.id.tv_enddate);
        mTvEndTime = findViewById(R.id.tv_endtime);
        mIvRemind = findViewById(R.id.iv_remind);
        mBtnAdd = findViewById(R.id.tv_add);
        mIvNoRemind = findViewById(R.id.iv_noremind);
        mIvShareToOther = findViewById(R.id.iv_sharetoother);
        mTvRepeat = findViewById(R.id.tv_repeat);
        mTvWhenRemind = findViewById(R.id.tv_whenremind);
        mIvImportment = findViewById(R.id.iv_importment);
        choosePop();
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
        mTvUploadDocument.setOnClickListener(this);
        mLlyStartDate.setOnClickListener(this);
        mLlyEndDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_location){
        }else  if (id == R.id.tv_uploaddocument){
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
            if(mScheduleRepeatPop != null){
                mScheduleRepeatPop.showPopupWindow(view);
            }
        }else  if (id == R.id.tv_whenremind){
            if(mScheduleRemindPop != null){
                mScheduleRemindPop.showPopupWindow(view);
            }
        }else  if (id == R.id.iv_importment){
            isImportment = !isImportment;
            mIvImportment.setImageResource(isImportment?R.mipmap.ic_vibrate_open:R.mipmap.ic_vibrate_close);
        }else  if (id == R.id.lly_startdate){
            if(mScheduleStartTimePop != null){
                mScheduleStartTimePop.showPopupWindow(view);
            }
        }else  if (id == R.id.lly_enddate){
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
                    case 0:
                        mTvRepeat.setText("一次性");
                        break;
                    case 1:
                        mTvRepeat.setText("每天");
                        break;
                    case 2:
                        mTvRepeat.setText("每周");
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
                        mTvWhenRemind.setText("5分钟前");
                        break;
                    case 1:
                        mTvWhenRemind.setText("10分钟前");
                        break;
                    case 2:
                        mTvWhenRemind.setText("1小时前");
                        break;
                    case 3:
                        mTvWhenRemind.setText("2小时前");
                        break;
                    case 4:
                        mTvWhenRemind.setText("24小时前");
                        break;
                    case 5:
                        mTvWhenRemind.setText("不在提醒");
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
        mScheduleEndTimePop = new ScheduleTimePop(this, new ScheduleTimePop.SelectListener() {
            @Override
            public void onSelected(String selectDate, String selectTime) {
                mTvEndDate.setText(selectDate);
                mTvEndTime.setText(selectTime);
            }
        });
    }
}
