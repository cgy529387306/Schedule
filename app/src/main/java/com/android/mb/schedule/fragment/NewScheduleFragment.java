package com.android.mb.schedule.fragment;

import android.app.Activity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.base.BaseFragment;
import com.android.mb.schedule.pop.ScheduleRepeatPop;
import com.android.mb.schedule.view.EditSchuduleActivity;
import com.android.mb.schedule.view.MainActivity;


/**
 * 订单
 * Created by cgy on 16/7/18.
 */
public class NewScheduleFragment extends BaseFragment implements View.OnClickListener{

    private EditText mEdtScheduleName; //日程名称
    private TextView mBtnLocation; //定位
    private TextView mTvAddress ; //位置
    private TextView mEdtScheduleContent; // 日程内容
    private TextView mTvUploadDocument; // 点击上传文件
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
    protected int getLayoutId() {
        return R.layout.frg_new_schedule;
    }

    @Override
    protected void bindViews(View view) {
        mEdtScheduleName = view.findViewById(R.id.edt_schedulename);
        mBtnLocation = view.findViewById(R.id.tv_location);
        mTvAddress = view.findViewById(R.id.tv_address);
        mEdtScheduleContent = view.findViewById(R.id.edt_schedulecontent);
        mTvUploadDocument = view.findViewById(R.id.tv_uploaddocument);
        mBtnChangeDocument = view.findViewById(R.id.tv_changedocument);
        mTvStartDate = view.findViewById(R.id.tv_startdate);
        mTvStartTime = view.findViewById(R.id.tv_starttime);
        mTvEndDate = view.findViewById(R.id.tv_enddate);
        mTvEndTime = view.findViewById(R.id.tv_endtime);
        mIvRemind = view.findViewById(R.id.iv_remind);
        mBtnAdd = view.findViewById(R.id.tv_add);
        mIvNoRemind = view.findViewById(R.id.iv_noremind);
        mIvShareToOther = view.findViewById(R.id.iv_sharetoother);
        mTvRepeat = view.findViewById(R.id.tv_repeat);
        mTvWhenRemind = view.findViewById(R.id.tv_whenremind);
        mIvImportment = view.findViewById(R.id.iv_importment);
        if (getActivity()!=null && getActivity() instanceof MainActivity){
            ((MainActivity)getActivity()).setRightImage(R.mipmap.icon_right);
        }
        mPop = new ScheduleRepeatPop(getActivity(), new ScheduleRepeatPop.SelectListener() {
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
    }

    @Override
    protected void processLogic() {

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
            if(mPop != null){
                mPop.showPopupWindow(view);
            }
        }else  if (id == R.id.tv_whenremind){
        }else  if (id == R.id.iv_importment){
            isImportment = !isImportment;
            mIvImportment.setImageResource(isImportment?R.mipmap.ic_vibrate_open:R.mipmap.ic_vibrate_close);
        }
    }
}
