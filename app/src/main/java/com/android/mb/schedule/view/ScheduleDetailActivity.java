package com.android.mb.schedule.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.mb.schedule.R;
import com.android.mb.schedule.base.BaseMvpActivity;
import com.android.mb.schedule.constants.ProjectConstants;
import com.android.mb.schedule.entitys.CurrentUser;
import com.android.mb.schedule.entitys.ScheduleDetailBean;
import com.android.mb.schedule.entitys.ScheduleDetailData;
import com.android.mb.schedule.entitys.UserBean;
import com.android.mb.schedule.presenter.DetailPresenter;
import com.android.mb.schedule.retrofit.download.DownloadHelper;
import com.android.mb.schedule.retrofit.download.FileDownloadCallback;
import com.android.mb.schedule.rxbus.Events;
import com.android.mb.schedule.utils.DialogHelper;
import com.android.mb.schedule.utils.FileUtils;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.NavigationHelper;
import com.android.mb.schedule.utils.ProjectHelper;
import com.android.mb.schedule.view.interfaces.IDetailView;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
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
    private TextView mTvWhenRemind; // 日程什么时候开始提醒
    private TextView mTvEdit;
    private TextView mTvShare;
    private TextView mTvDelete;
    private TextView mTvUpdateTime;
    private LinearLayout mLinFile;
    private LinearLayout mLinEdit;
    public static final String mDateFormat = "yyyy年MM月dd日";
    private ProgressDialog mProgressDialog;//创建ProgressDialog
    private long mId;
    private ScheduleDetailData mDetailData;
    @Override
    protected void loadIntent() {
        mId = getIntent().getLongExtra("id",0);
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
        mLinFile = findViewById(R.id.lly_file);
        mTvFileName = findViewById(R.id.tv_file_name);
        mTvDownDocument = findViewById(R.id.tv_download_document);
        mTvPersons = findViewById(R.id.tv_persons);
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
        mPresenter.getSchedule(requestMap);
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
            downloadFile();
        }else if (id == R.id.tv_edit){
            Bundle bundle = new Bundle();
            bundle.putInt("type",1);
            bundle.putSerializable("schedule",ProjectHelper.transBean(mDetailData));
            NavigationHelper.startActivity(ScheduleDetailActivity.this,ScheduleAddActivity.class,bundle,false);
        }else if (id == R.id.tv_share){
            NavigationHelper.startActivityForResult(ScheduleDetailActivity.this,SelectPersonActivity.class,null,ProjectConstants.REQUEST_SELECT_PERSON);
        }else if (id == R.id.tv_delete){
            DialogHelper.showConfirmDialog(ScheduleDetailActivity.this, "提示", "确定删除改日程吗？", true, "确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Map<String,Object> requestMap = new HashMap<>();
                    requestMap.put("id",mId);
                    mPresenter.deleteSchedule(requestMap);
                }
            },"取消",null);
        }
    }


    private void initData(ScheduleDetailData detailData){
        ScheduleDetailBean detailBean = detailData.getInfo();
        if (detailBean!=null){
            mTvScheduleName.setText(detailBean.getTitle());
            mTvAddress.setText(detailBean.getAddress());
            mTvStartDate.setText(Helper.long2DateString(detailBean.getTime_s()*1000,mDateFormat));
            mTvStartTime.setText(detailBean.getStartTime());
            mTvEndDate.setText(Helper.long2DateString(detailBean.getTime_e()*1000,mDateFormat));
            mTvEndTime.setText(detailBean.getEndTime());
            mTvImport.setVisibility(detailBean.getImportant()==1?View.VISIBLE:View.GONE);
            mTvScheduleContent.setText(detailBean.getDescription());
            mTvRepeat.setText(ProjectHelper.getRepeatStr(detailBean.getRepeattype()));
            mTvWhenRemind.setText(ProjectHelper.getRemindStr(detailBean.getRemind()));
            String updateTime = Helper.long2DateString(detailBean.getUpdatetime()*1000,"MM-dd HH:mm");
            mTvUpdateTime.setText(String.format(mContext.getString(R.string.update_time),updateTime));
            if (Helper.isNotEmpty(detailData.getFile())){
                mLinFile.setVisibility(View.VISIBLE);
                ScheduleDetailData.FileBean fileBean = detailData.getFile().get(0);
                mTvFileName.setText(fileBean.getFilename());
            }else{
                mLinFile.setVisibility(View.GONE);
            }
            if (Helper.isNotEmpty(detailData.getRelated())){
                String shareStr = String.format(mContext.getString(R.string.relate_person), ProjectHelper.getSharePersonStr(detailData.getRelated()),detailData.getRelated().size());
                mTvPersons.setText(shareStr);
            }
//            mLinEdit.setVisibility(detailBean.getCreate_by() == CurrentUser.getInstance().getOffice_id()?View.VISIBLE:View.GONE);
        }
    }

    private void downloadFile(){
        if (Helper.isNotEmpty(mDetailData.getFile())){
            ScheduleDetailData.FileBean fileBean = mDetailData.getFile().get(0);
            mTvFileName.setText(fileBean.getFilename());
            mProgressDialog =  new ProgressDialog(ScheduleDetailActivity.this);//实例化ProgressDialog
            mProgressDialog.setTitle("文件下载");//设置标题
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//设置样式为横向显示进度的样式
            mProgressDialog.setMessage("正在下载，请稍后...");
            mProgressDialog.setMax(100);//设置最大值
            mProgressDialog.setProgress(0);//设置初始值为0，其实可以不用设置，默认就是0
            mProgressDialog.setIndeterminate(false);//是否精确显示对话框，flase为是，反之为否
            mProgressDialog.show();
            DownloadHelper.getInstance()
                    .downloadFile(fileBean.getUrl(), Environment.getExternalStorageDirectory() + File.separator + "/file", fileBean.getFilename(),
                            new FileDownloadCallback<File>() {
                                @Override
                                public void onDownLoadSuccess(File file) {
                                    showToastMessage("下载完成");
                                    mProgressDialog.dismiss();
                                }

                                @Override
                                public void onDownLoadFail(Throwable e) {
                                    showToastMessage("下载失败");
                                    mProgressDialog.dismiss();
                                }

                                @Override
                                public void onProgress(int progress, int total) {
                                    float percent = progress/total*1f;
                                    int currentProgress = (int)(percent*100);
                                    mProgressDialog.setProgress(currentProgress);
                                }
                            });
        }
    }

    @Override
    public void getSuccess(ScheduleDetailData result) {
        if (Helper.isNotEmpty(result)){
            mDetailData = result;
            initData(mDetailData);
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
            Map<String,Object> requestMap = new HashMap<>();
            requestMap.put("sid",mId);
            requestMap.put("share", ProjectHelper.getIdStr(list));
            mPresenter.shareTo(requestMap);
        }
    }
}