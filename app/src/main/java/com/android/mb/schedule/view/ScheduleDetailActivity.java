package com.android.mb.schedule.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.base.BaseMvpActivity;
import com.android.mb.schedule.constants.ProjectConstants;
import com.android.mb.schedule.entitys.CurrentUser;
import com.android.mb.schedule.entitys.FileBean;
import com.android.mb.schedule.entitys.KpiRequest;
import com.android.mb.schedule.entitys.ScheduleDetailBean;
import com.android.mb.schedule.entitys.ScheduleDetailData;
import com.android.mb.schedule.entitys.UserBean;
import com.android.mb.schedule.presenter.DetailPresenter;
import com.android.mb.schedule.retrofit.download.DownloadUtils;
import com.android.mb.schedule.retrofit.download.JsDownloadListener;
import com.android.mb.schedule.rxbus.Events;
import com.android.mb.schedule.utils.DialogHelper;
import com.android.mb.schedule.utils.FileOpenUtils;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.NavigationHelper;
import com.android.mb.schedule.utils.NetworkHelper;
import com.android.mb.schedule.utils.ProjectHelper;
import com.android.mb.schedule.view.interfaces.IDetailView;
import com.android.mb.schedule.widget.BottomMenuDialog;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.functions.Action1;


/**
 * 日程详情
 * Created by cgy on 16/7/18.
 */
public class ScheduleDetailActivity extends BaseMvpActivity<DetailPresenter,IDetailView> implements IDetailView, View.OnClickListener{

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
    private TextView mTvShares;//分享人员
    private TextView mTvWhenRemind; // 日程什么时候开始提醒
    private TextView mTvEdit;
    private TextView mTvShare;
    private TextView mTvDelete;
    private TextView mTvUpdateTime;
    private LinearLayout mLinFile;
    private LinearLayout mLinEdit;
    public static final String mDateFormat = "yyyy年MM月dd日";
    public static final String mTimeFormat = "HH:mm";
    private ProgressDialog mProgressDialog;//创建ProgressDialog
    private long mId;
    private String mDate;
    private ScheduleDetailData mDetailData;
    private BottomMenuDialog mCheckDialog;
    private String mFileDir;
    private KpiRequest mKpiRequest;
    private ScheduleDetailBean mDetailBean;
    private boolean mIsCanEditKpi;
    @Override
    protected void loadIntent() {
        mFileDir = Environment.getExternalStorageDirectory() + File.separator + "/Schedule";
        mId = getIntent().getLongExtra("id",0);
        mDate = getIntent().getStringExtra("date");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_schedule_detail;
    }

    @Override
    protected void initTitle() {
        setTitleText("查看日程");
        hideRightText();
    }

    @Override
    protected void onRightAction() {
        super.onRightAction();
        if (mDetailData!=null && mDetailData.getInfo()!=null && mKpiRequest!=null){
            ScheduleDetailBean detailBean = mDetailData.getInfo();
            Bundle bundle = new Bundle();
            if (Helper.isNotEmpty(mDate)){
                bundle.putString("date",mDate);
            }
            bundle.putSerializable("kpiRequest",mKpiRequest);
            bundle.putSerializable("detailBean",detailBean);
            bundle.putBoolean("isValid",mIsCanEditKpi);
            NavigationHelper.startActivityForResult(ScheduleDetailActivity.this,KPIAddActivity.class,bundle,ProjectConstants.REQUEST_KPI_EDIT);
        }
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
        mLinFile = findViewById(R.id.lly_file);
        mTvFileName = findViewById(R.id.tv_file_name);
        mTvDownDocument = findViewById(R.id.tv_download_document);
        mTvPersons = findViewById(R.id.tv_persons);
        mTvShares = findViewById(R.id.tv_shares);
        mTvWhenRemind = findViewById(R.id.tv_when_remind);
        mTvUpdateTime = findViewById(R.id.tv_update_time);
        mTvEdit = findViewById(R.id.tv_edit);
        mTvShare = findViewById(R.id.tv_share);
        mTvDelete = findViewById(R.id.tv_delete);
        mLinEdit = findViewById(R.id.lin_edit);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        getDetail();
    }

    private void getDetail(){
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("id",mId);
        if (Helper.isNotEmpty(mDate)){
            requestMap.put("date",mDate);
        }
        mPresenter.getSchedule(requestMap);
    }

    private void getKpiInfo(){
        if (mDetailData!=null && mDetailData.getInfo()!=null){
            mDetailBean = mDetailData.getInfo();
            Map<String,Object> requestMap = new HashMap<>();
            requestMap.put("sid",mDetailBean.getId());
            requestMap.put("time_s",getTimeStart());
            requestMap.put("time_e",getTimeEnd());
            mPresenter.viewKpi(requestMap);
        }
    }

    @Override
    protected void setListener() {
        regiestEvent(ProjectConstants.EVENT_UPDATE_SCHEDULE, new Action1<Events<?>>() {
            @Override
            public void call(Events<?> events) {
                getDetail();
            }
        });
        mTvDownDocument.setOnClickListener(this);
        mTvEdit.setOnClickListener(this);
        mTvShare.setOnClickListener(this);
        mTvDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_download_document){
            if (isFileExit()){
                File file;
                if (NetworkHelper.isNetworkAvailable(mContext)){
                    file = new File(mFileDir,mDetailData.getFile().get(0).getFilename());
                }else{
                    file =new File(mDetailData.getFile().get(0).getUrl());
                }
                if (file!=null){
                    FileOpenUtils.openFile(ScheduleDetailActivity.this,file);
                }else{
                    showToastMessage("文件不存在");
                }
            }else{
                downloadFile();
            }
        }else if (id == R.id.tv_edit){
            Bundle bundle = new Bundle();
            bundle.putInt("type",1);
            bundle.putSerializable("schedule",mDetailData);
            NavigationHelper.startActivity(ScheduleDetailActivity.this,ScheduleAddActivity.class,bundle,false);
        }else if (id == R.id.tv_share){
            Bundle bundle = new Bundle();
            bundle.putSerializable("selectPerson", (Serializable) mDetailData.getShare());
            NavigationHelper.startActivityForResult(ScheduleDetailActivity.this,SelectPersonActivity.class,bundle,ProjectConstants.REQUEST_SELECT_PERSON);
        }else if (id == R.id.tv_delete){
            if (mDetailData!=null && mDetailData.getInfo()!=null && mDetailData.getInfo().getRepeattype()!=1){
                //重复事件
                checkRepeatChange();
            }else{
                DialogHelper.showConfirmDialog(ScheduleDetailActivity.this, "提示", "确定删除该日程吗？", true, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doDelete(false);
                    }
                },"取消",null);
            }
        }
    }


    private void initData(ScheduleDetailData detailData){
        ScheduleDetailBean detailBean = detailData.getInfo();
        if (detailBean!=null){
            mTvScheduleName.setText(detailBean.getTitle());
            mTvAddress.setText(detailBean.getAddress());
            mTvStartDate.setText(Helper.long2DateString(detailBean.getTime_s()*1000,mDateFormat));
            mTvStartTime.setText(Helper.long2DateString(detailBean.getTime_s()*1000,mTimeFormat));
            mTvEndDate.setText(Helper.long2DateString(detailBean.getTime_e()*1000,mDateFormat));
            mTvEndTime.setText(Helper.long2DateString(detailBean.getTime_e()*1000,mTimeFormat));
            int mIsAllDay = detailBean.getAllDay();
            mTvStartTime.setVisibility(mIsAllDay==1?View.GONE:View.VISIBLE);
            mTvEndTime.setVisibility(mIsAllDay==1?View.GONE:View.VISIBLE);

            mTvImport.setVisibility(detailBean.getImportant()==1?View.VISIBLE:View.GONE);
            mTvScheduleContent.setText(detailBean.getDescription());
            mTvRepeat.setText(ProjectHelper.getRepeatStr(detailBean.getRepeattype()));
            mTvWhenRemind.setText(ProjectHelper.getRemindStr(detailBean.getRemind()));
            String updateTime = Helper.long2DateString(detailBean.getUpdatetime()*1000,"MM-dd HH:mm");
            mTvUpdateTime.setText(String.format(mContext.getString(R.string.update_time),updateTime));
            refreshFileBtn();
            if (Helper.isNotEmpty(detailData.getFile())){
                mLinFile.setVisibility(View.VISIBLE);
                FileBean fileBean = detailData.getFile().get(0);
                mTvFileName.setText(fileBean.getFilename());
            }else{
                mLinFile.setVisibility(View.GONE);
            }
            if (Helper.isNotEmpty(detailData.getRelated())){
                String shareStr = String.format(mContext.getString(R.string.relate_person), ProjectHelper.getSharePersonStr(detailData.getRelated()),detailData.getRelated().size());
                mTvPersons.setText(shareStr);
            }else{
                mTvPersons.setText("");
            }
            if (Helper.isNotEmpty(detailData.getShare())){
                String shareStr = String.format(mContext.getString(R.string.relate_person), ProjectHelper.getSharePersonStr(detailData.getShare()),detailData.getShare().size());
                mTvShares.setText(shareStr);
            }else{
                mTvShares.setText("");
            }

            long dif =(System.currentTimeMillis()/1000) - detailBean.getTime_e();
            boolean isMore3Day = dif>3*24*60*60;
            boolean isCanEdit = detailBean.getCreate_by() == CurrentUser.getInstance().getId() && !isMore3Day;
            mLinEdit.setVisibility(isCanEdit?View.VISIBLE:View.GONE);
        }
    }

    private void downloadFile(){
        if (Helper.isNotEmpty(mDetailData.getFile())){
            FileBean fileBean = mDetailData.getFile().get(0);
            mTvFileName.setText(fileBean.getFilename());
            mProgressDialog =  new ProgressDialog(ScheduleDetailActivity.this);//实例化ProgressDialog
            mProgressDialog.setTitle("文件下载");//设置标题
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//设置样式为横向显示进度的样式
            mProgressDialog.setMessage("正在下载，请稍后...");
            mProgressDialog.setIndeterminate(false);//是否精确显示对话框，flase为是，反之为否
            mProgressDialog.show();
            new DownloadUtils(new JsDownloadListener() {
                @Override
                public void onStartDownload() {

                }

                @Override
                public void onProgress(int progress) {
                    mProgressDialog.setProgress(progress);//设置初始值为0，其实可以不用设置，默认就是0
                }

                @Override
                public void onFinishDownload() {
                    showToastMessage("下载完成,请到sdcard Schedule文件夹下查看下载文件");
                    mProgressDialog.dismiss();
                    refreshFileBtn();
                }

                @Override
                public void onFail(String errorInfo) {
                    showToastMessage("下载失败:"+errorInfo);
                    mProgressDialog.dismiss();
                }
            }).download(fileBean.getUrl(),mFileDir,fileBean.getFilename());
        }
    }

    @Override
    public void getSuccess(ScheduleDetailData result) {
        if (Helper.isNotEmpty(result)){
            mDetailData = result;
            initData(mDetailData);
            getKpiInfo();
        }
    }

    @Override
    public void deleteSuccess(Object result) {
        showToastMessage("删除成功");
        sendMsg(ProjectConstants.EVENT_UPDATE_SCHEDULE_LIST,null);
        finish();
    }

    @Override
    public void shareSuccess(Object result) {
        showToastMessage("分享成功");
        getDetail();
    }

    @Override
    public void getKpiSuccess(KpiRequest result) {
        if (result!=null && mDetailBean!=null){
            mKpiRequest = result;
            long dif =(System.currentTimeMillis()/1000) - mDetailBean.getTime_e();
            boolean isMoreDay = dif>result.getPerformance()*24*60*60;
            mIsCanEditKpi = mDetailBean.getCreate_by() == CurrentUser.getInstance().getId() && !isMoreDay;
            if (mIsCanEditKpi){
                setRightText(result.getResid()==0?"填写实绩":"修改实绩");
            }else {
                if (result.getResid()==0){
                    hideRightText();
                }else{
                    setRightText("查看实绩");
                }
            }
        }
    }

    @Override
    protected DetailPresenter createPresenter() {
        return new DetailPresenter();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (ProjectConstants.REQUEST_SELECT_PERSON == requestCode){
            List<UserBean> list = (List<UserBean>) data.getSerializableExtra("selectPerson");
            mPresenter.shareTo(mId,list);
        }else if (ProjectConstants.REQUEST_KPI_EDIT == requestCode){
            getKpiInfo();
        }
    }

    private void checkRepeatChange() {
        if (mCheckDialog == null) {
            mCheckDialog = new BottomMenuDialog.Builder(mContext)
                    .addMenu("删除所有重复的活动", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mCheckDialog.dismiss();
                            doDelete(false);
                        }
                    }).addMenu("删除此活动和所有将来的活动", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mCheckDialog.dismiss();
                            doDelete(true);
                        }
                    }).create();
        }
        mCheckDialog.show();
    }

    private void doDelete(boolean hasDate){
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("id",mId);
        if (hasDate && Helper.isNotEmpty(mDate)){
            requestMap.put("date",mDate);
        }
        mPresenter.deleteSchedule(requestMap);
    }

    private void refreshFileBtn(){
        mTvDownDocument.setText(isFileExit()?"打开附件":"点击下载附件");
    }

    private boolean isFileExit(){
        boolean isExit = false;
        if (mDetailData!=null && Helper.isNotEmpty(mDetailData.getFile())){
            FileBean fileBean = mDetailData.getFile().get(0);
            isExit = ProjectHelper.isFileExit(mFileDir,fileBean.getFilename()) || new File(fileBean.getUrl()).exists();
        }
        return isExit;
    }

    private long getTimeStart(){
        long time;
        if (mDetailBean.getRepeattype()==1 || Helper.isEmpty(mDate)){
            time = mDetailBean.getTime_s();
        }else{
            String timeStr = Helper.long2DateString(mDetailBean.getTime_s()*1000,mTimeFormat);
            time = Helper.dateString2Long(mDate+" "+timeStr,"yyyy-MM-dd HH:mm")/1000;
        }
        return time;
    }

    private long getTimeEnd(){
        long time;
        if (mDetailBean.getRepeattype()==1 || Helper.isEmpty(mDate)){
            time = mDetailBean.getTime_e();
        }else{
            String timeStr = Helper.long2DateString(mDetailBean.getTime_e()*1000,mTimeFormat);
            time = Helper.dateString2Long(mDate+" "+timeStr,"yyyy-MM-dd HH:mm")/1000;
        }
        return time;
    }

}
