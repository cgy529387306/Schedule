package com.android.mb.schedule.presenter;

import android.text.TextUtils;

import com.android.mb.schedule.app.MBApplication;
import com.android.mb.schedule.base.BaseMvpPresenter;
import com.android.mb.schedule.db.GreenDaoManager;
import com.android.mb.schedule.db.Schedule;
import com.android.mb.schedule.entitys.CurrentUser;
import com.android.mb.schedule.entitys.FileData;
import com.android.mb.schedule.entitys.ScheduleRequest;
import com.android.mb.schedule.greendao.ScheduleDao;
import com.android.mb.schedule.presenter.interfaces.ISchedulePresenter;
import com.android.mb.schedule.retrofit.http.exception.ApiException;
import com.android.mb.schedule.retrofit.http.exception.NoNetWorkException;
import com.android.mb.schedule.api.ScheduleMethods;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.NetworkHelper;
import com.android.mb.schedule.utils.ProjectHelper;
import com.android.mb.schedule.view.interfaces.IScheduleView;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

public class SchedulePresenter extends BaseMvpPresenter<IScheduleView> implements ISchedulePresenter {


    @Override
    public void addSchedule(ScheduleRequest request) {
        if (NetworkHelper.isNetworkAvailable(MBApplication.getInstance())){
            Observable observable = ScheduleMethods.getInstance().addSchedule(request);
            toSubscribe(observable,  new Subscriber<Object>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    if(mMvpView!=null){
                        if (e instanceof ApiException && !TextUtils.isEmpty(e.getMessage())){
                            mMvpView.showToastMessage(e.getMessage());
                        }
                    }
                }

                @Override
                public void onNext(Object result) {
                    if (mMvpView!=null){
                        mMvpView.addSuccess(result);
                    }
                }
            });
        }else{
            addDataLocal(request);
        }
    }

    private void addDataLocal(ScheduleRequest request) {
        ScheduleDao scheduleDao = GreenDaoManager.getInstance().getNewSession().getScheduleDao();
        scheduleDao.insertOrReplace(ProjectHelper.transToSchedule(request));
        if (mMvpView!=null){
            mMvpView.addSuccess("1");
        }
    }



    @Override
    public void editSchedule(ScheduleRequest request) {
        if (NetworkHelper.isNetworkAvailable(MBApplication.getInstance())){
            Observable observable = ScheduleMethods.getInstance().editSchedule(request);
            toSubscribe(observable,  new Subscriber<Object>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    if(mMvpView!=null){
                        if (e instanceof ApiException && !TextUtils.isEmpty(e.getMessage())){
                            mMvpView.showToastMessage(e.getMessage());
                        }
                    }
                }

                @Override
                public void onNext(Object result) {
                    if (mMvpView!=null){
                        mMvpView.editSuccess(result);
                    }
                }
            });
        }else{
            editDataLocal(request);
        }
    }

    private void editDataLocal(ScheduleRequest request) {
        ScheduleDao scheduleDao = GreenDaoManager.getInstance().getNewSession().getScheduleDao();
        if (request.getType()==1){
            Schedule lastSchedule = scheduleDao.loadByRowId(request.getId());
            lastSchedule.setClose_time(System.currentTimeMillis()/1000);
            scheduleDao.update(lastSchedule);

            Schedule schedule = ProjectHelper.transToEditSchedule(request);
            schedule.setClose_time(0);
            schedule.setCreatetime(System.currentTimeMillis()/1000);
            schedule.setId(null);
            scheduleDao.insertOrReplace(schedule);
        }else{
            Schedule schedule = ProjectHelper.transToEditSchedule(request);
            scheduleDao.update(schedule);
        }
        if (mMvpView!=null){
            mMvpView.editSuccess("1");
        }
    }


    @Override
    public void uploadFile(final File file) {
        mMvpView.showProgressDialog("上传中...");
        Observable observable = ScheduleMethods.getInstance().upload(file);
        toSubscribe(observable,  new Subscriber<FileData>() {
            @Override
            public void onCompleted() {
                if (mMvpView!=null){
                    mMvpView.dismissProgressDialog();
                }
            }

            @Override
            public void onError(Throwable e) {
                if(mMvpView!=null){
                    mMvpView.dismissProgressDialog();
                    if (e instanceof ApiException && !TextUtils.isEmpty(e.getMessage())){
                        mMvpView.showToastMessage(e.getMessage());
                    }
                }
            }

            @Override
            public void onNext(FileData result) {
                if (mMvpView!=null){
                    mMvpView.dismissProgressDialog();
                    if (result!=null){
                        result.setFileName(file.getName());
                    }
                    mMvpView.uploadSuccess(result);
                }
            }
        });
    }
}
