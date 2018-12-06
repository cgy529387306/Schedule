package com.android.mb.schedule.presenter;

import android.text.TextUtils;

import com.android.mb.schedule.app.MBApplication;
import com.android.mb.schedule.base.BaseMvpPresenter;
import com.android.mb.schedule.db.Delete;
import com.android.mb.schedule.db.GreenDaoManager;
import com.android.mb.schedule.db.Schedule;
import com.android.mb.schedule.entitys.CurrentUser;
import com.android.mb.schedule.entitys.KpiRequest;
import com.android.mb.schedule.entitys.ScheduleDetailData;
import com.android.mb.schedule.entitys.UserBean;
import com.android.mb.schedule.greendao.DeleteDao;
import com.android.mb.schedule.greendao.ScheduleDao;
import com.android.mb.schedule.presenter.interfaces.IDetailPresenter;
import com.android.mb.schedule.retrofit.http.exception.ApiException;
import com.android.mb.schedule.retrofit.http.exception.NoNetWorkException;
import com.android.mb.schedule.api.ScheduleMethods;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.JsonHelper;
import com.android.mb.schedule.utils.NetworkHelper;
import com.android.mb.schedule.utils.ProjectHelper;
import com.android.mb.schedule.view.interfaces.IDetailView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public class DetailPresenter extends BaseMvpPresenter<IDetailView> implements IDetailPresenter {



    @Override
    public void getSchedule(Map<String, Object> requestMap) {
        ScheduleDao scheduleDao = GreenDaoManager.getInstance().getNewSession().getScheduleDao();
        long id = (long) requestMap.get("id");
        Schedule schedule = scheduleDao.loadByRowId(id);
        boolean isLocal = schedule!=null && schedule.getLocal()==1;
        if (isLocal || !NetworkHelper.isNetworkAvailable(MBApplication.getInstance())){
            getDataFromLocal(requestMap);
        }else{
            Observable observable = ScheduleMethods.getInstance().getSchedule(requestMap);
            toSubscribe(observable,  new Subscriber<ScheduleDetailData>() {
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
                public void onNext(ScheduleDetailData result) {
                    if (mMvpView!=null){
                        mMvpView.getSuccess(result);
                    }
                }
            });
        }
    }

    private void getDataFromLocal(Map<String, Object> requestMap) {
        ScheduleDao scheduleDao = GreenDaoManager.getInstance().getNewSession().getScheduleDao();
        long id = (long) requestMap.get("id");
        String date = "";
        if (requestMap.containsKey("date")){
           date = (String) requestMap.get("date");
        }
        Schedule schedule = scheduleDao.loadByRowId(id);
        if (schedule==null){
            mMvpView.showToastMessage("该日程不存在");
        }else{
            if (mMvpView!=null){
                mMvpView.getSuccess(ProjectHelper.transToScheduleDetailData(schedule,date));
            }
        }
    }

    @Override
    public void deleteSchedule(Map<String, Object> requestMap) {

        if (NetworkHelper.isNetworkAvailable(MBApplication.getInstance())){
            Observable observable = ScheduleMethods.getInstance().deleteSchedule(requestMap);
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
                        ProjectHelper.syncSchedule(MBApplication.getInstance(),false);
                        mMvpView.deleteSuccess(result);
                    }
                }
            });
        }else{
            deleteFromLocal(requestMap);
        }

    }

    private void deleteFromLocal(Map<String, Object> requestMap) {
        ScheduleDao scheduleDao = GreenDaoManager.getInstance().getNewSession().getScheduleDao();
        DeleteDao deleteDao = GreenDaoManager.getInstance().getNewSession().getDeleteDao();
        long id = (long) requestMap.get("id");
        boolean hasDate = requestMap.containsKey("date");
        String date = (String) requestMap.get("date");
        long close = Helper.dateString2Long(date,"yyyy-MM-dd");
        Schedule schedule = scheduleDao.loadByRowId(id);
        if (hasDate){
            schedule.setClose_time(close/1000);
            schedule.setUpdatetime(System.currentTimeMillis()/1000);
            scheduleDao.update(schedule);
        }else{
            scheduleDao.deleteByKey(id);
            if (schedule.getLocal()==0){
                deleteDao.insert(new Delete(null,id));
            }
        }
        if (mMvpView!=null){
            mMvpView.deleteSuccess("");
        }
    }



    @Override
    public void shareTo(long id, List<UserBean> shareList) {
        if (NetworkHelper.isNetworkAvailable(MBApplication.getInstance())){
            Map<String,Object> requestMap = new HashMap<>();
            requestMap.put("sid",id);
            requestMap.put("share", ProjectHelper.getIdStr(shareList));
            Observable observable = ScheduleMethods.getInstance().shareTo(requestMap);
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
                        ProjectHelper.syncSchedule(MBApplication.getInstance(),false);
                        mMvpView.shareSuccess(result);
                    }
                }
            });
        }else{
            shareToLocal(id,shareList);
        }
    }

    private void shareToLocal(long id, List<UserBean> shareList) {
        ScheduleDao scheduleDao = GreenDaoManager.getInstance().getNewSession().getScheduleDao();
        Schedule schedule = scheduleDao.loadByRowId(id);
        schedule.setShare(JsonHelper.toJson(shareList));
        scheduleDao.update(schedule);
        if (mMvpView!=null){
            mMvpView.shareSuccess("");
        }
    }

    @Override
    public void viewKpi(Map<String, Object> requestMap) {
        Observable observable = ScheduleMethods.getInstance().getKpi(requestMap);
        toSubscribe(observable,  new Subscriber<KpiRequest>() {
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
            public void onNext(KpiRequest result) {
                if (mMvpView!=null){
                    mMvpView.getKpiSuccess(result);
                }
            }
        });
    }

}
