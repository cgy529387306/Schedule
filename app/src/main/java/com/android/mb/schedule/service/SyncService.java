package com.android.mb.schedule.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.android.mb.schedule.api.ScheduleMethods;
import com.android.mb.schedule.constants.ProjectConstants;
import com.android.mb.schedule.db.GreenDaoManager;
import com.android.mb.schedule.db.Office;
import com.android.mb.schedule.db.Schedule;
import com.android.mb.schedule.db.User;
import com.android.mb.schedule.entitys.CurrentUser;
import com.android.mb.schedule.entitys.OfficeSyncData;
import com.android.mb.schedule.entitys.ScheduleSync;
import com.android.mb.schedule.entitys.ScheduleSyncData;
import com.android.mb.schedule.entitys.TreeData;
import com.android.mb.schedule.entitys.UserBean;
import com.android.mb.schedule.entitys.UserSyncData;
import com.android.mb.schedule.greendao.OfficeDao;
import com.android.mb.schedule.greendao.ScheduleDao;
import com.android.mb.schedule.greendao.UserDao;
import com.android.mb.schedule.retrofit.http.exception.ApiException;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.JsonHelper;
import com.android.mb.schedule.utils.PreferencesHelper;
import com.android.mb.schedule.utils.ToastHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by cgy on 18/10/26.
 */

public class SyncService extends Service {
    protected CompositeSubscription mCompositeSubscription;

    private long mLastUpdateTime;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        doJobTask();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        onUnsubscribe();
        super.onDestroy();
    }

    private void doJobTask(){
        mLastUpdateTime = PreferencesHelper.getInstance().getLong(ProjectConstants.KEY_LAST_SYNC+ CurrentUser.getInstance().getId(),0);
        syncSchedule();
        getOrgPeople();
        getOrgList();
        getAddress();
        getOfficeList();
        getPersonList();
        getUnderList();
    }

    private void syncSchedule(){
        HashMap<String, Object> requestMap = new HashMap<>();
        if (mLastUpdateTime!=0){
            requestMap.put("stamp",mLastUpdateTime);
        }
        Observable observable = ScheduleMethods.getInstance().syncSchedule(requestMap);
        toSubscribe(observable,  new Subscriber<ScheduleSyncData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof ApiException && !TextUtils.isEmpty(e.getMessage())){
                    ToastHelper.showLongToast(e.getMessage());
                }
            }

            @Override
            public void onNext(ScheduleSyncData result) {
                PreferencesHelper.getInstance().putLong(ProjectConstants.KEY_LAST_SYNC+ CurrentUser.getInstance().getId(),System.currentTimeMillis()/1000);
                if (result!=null){
                    if (Helper.isNotEmpty(result.getUpd())){
                        insertSchedule(result.getUpd());
                    }
                    if (Helper.isNotEmpty(result.getDel())){
                        deleteSchedule(result.getDel());
                    }
                }
            }
        });
    }

    private void insertSchedule(final List<ScheduleSync> dataList){
        List<Schedule> insertList = new ArrayList<>();
        for (ScheduleSync scheduleSync:dataList){
            Schedule schedule = new Schedule(scheduleSync.getId(), scheduleSync.getCreate_by(),scheduleSync.getTitle(),scheduleSync.getDescription(),
                    scheduleSync.getDate(),scheduleSync.getTime_s(),scheduleSync.getTime_e(),scheduleSync.getAddress(),scheduleSync.getStartTime(),
                    scheduleSync.getEndTime(),scheduleSync.getAllDay(),scheduleSync.getRepeattype(),scheduleSync.getRemind(),scheduleSync.getImportant(),
                    scheduleSync.getSummary(),scheduleSync.getNot_remind_related(),scheduleSync.getClose_time(),scheduleSync.getCreatetime(),scheduleSync.getUpdatetime(),
                    scheduleSync.getSt_del(), JsonHelper.toJson(scheduleSync.getRelated()),JsonHelper.toJson(scheduleSync.getShare()),JsonHelper.toJson(scheduleSync.getFile()),0);
            insertList.add(schedule);
        }
        ScheduleDao scheduleDao = GreenDaoManager.getInstance().getNewSession().getScheduleDao();
        Observable observable = scheduleDao.rx().insertOrReplaceInTx(insertList);
        toSubscribe(observable,  new Subscriber<List<Schedule>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof ApiException && !TextUtils.isEmpty(e.getMessage())){
                    ToastHelper.showLongToast(e.getMessage());
                }
            }

            @Override
            public void onNext(List<Schedule> result) {

            }
        });
    }

    private void deleteSchedule(final List<Long> dataList){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ScheduleDao scheduleDao = GreenDaoManager.getInstance().getNewSession().getScheduleDao();
                scheduleDao.deleteByKeyInTx(dataList);
            }
        }).start();
    }

    private void getOrgPeople() {
        HashMap<String, Object> requestMap = new HashMap<>();
        if (mLastUpdateTime!=0){
            requestMap.put("stamp",mLastUpdateTime);
        }
        Observable observable = ScheduleMethods.getInstance().syncPeople(requestMap);
        toSubscribe(observable,  new Subscriber<UserSyncData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof ApiException && !TextUtils.isEmpty(e.getMessage())){
                    ToastHelper.showLongToast(e.getMessage());
                }
            }

            @Override
            public void onNext(UserSyncData result) {
                PreferencesHelper.getInstance().putLong(ProjectConstants.KEY_LAST_SYNC+ CurrentUser.getInstance().getId(),System.currentTimeMillis()/1000);
                if (result!=null){
                    if (Helper.isNotEmpty(result.getUpd())){
                        insertOrgPeople(result.getUpd());
                    }
                }
            }
        });
    }

    private void insertOrgPeople(final List<User> dataList){
        UserDao userDao = GreenDaoManager.getInstance().getNewSession().getUserDao();
        Observable observable = userDao.rx().insertOrReplaceInTx(dataList);
        toSubscribe(observable,  new Subscriber<List<User>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof ApiException && !TextUtils.isEmpty(e.getMessage())){
                    ToastHelper.showLongToast(e.getMessage());
                }
            }

            @Override
            public void onNext(List<User> result) {

            }
        });
    }


    private void getOrgList() {
        HashMap<String, Object> requestMap = new HashMap<>();
        if (mLastUpdateTime!=0){
            requestMap.put("stamp",mLastUpdateTime);
        }
        Observable observable = ScheduleMethods.getInstance().syncOffice(requestMap);
        toSubscribe(observable,  new Subscriber<OfficeSyncData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof ApiException && !TextUtils.isEmpty(e.getMessage())){
                    ToastHelper.showLongToast(e.getMessage());
                }
            }

            @Override
            public void onNext(OfficeSyncData result) {
                PreferencesHelper.getInstance().putLong(ProjectConstants.KEY_LAST_SYNC+ CurrentUser.getInstance().getId(),System.currentTimeMillis()/1000);
                if (result!=null){
                    if (Helper.isNotEmpty(result.getUpd())){
                        insertOffice(result.getUpd());
                    }
                }
            }
        });
    }

    private void insertOffice(final List<Office> dataList){
        OfficeDao officeDao = GreenDaoManager.getInstance().getNewSession().getOfficeDao();
        Observable observable = officeDao.rx().insertInTx(dataList);
        toSubscribe(observable,  new Subscriber<List<Office>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof ApiException && !TextUtils.isEmpty(e.getMessage())){
                    ToastHelper.showLongToast(e.getMessage());
                }
            }

            @Override
            public void onNext(List<Office> result) {

            }
        });
    }

    private void getPersonList() {
        Observable observable = ScheduleMethods.getInstance().getPersons();
        toSubscribe(observable,  new Subscriber<List<UserBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof ApiException && !TextUtils.isEmpty(e.getMessage())){
                    ToastHelper.showLongToast(e.getMessage());
                }
            }

            @Override
            public void onNext(List<UserBean> result) {
                if (result!=null){
                    PreferencesHelper.getInstance().putString(ProjectConstants.KEY_PERSON_LIST+CurrentUser.getInstance().getId(), JsonHelper.toJson(result));
                }
            }
        });
    }

    private void getOfficeList() {
        Observable observable = ScheduleMethods.getInstance().getOfficeList();
        toSubscribe(observable,  new Subscriber<TreeData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof ApiException && !TextUtils.isEmpty(e.getMessage())){
                    ToastHelper.showLongToast(e.getMessage());
                }
            }

            @Override
            public void onNext(TreeData result) {
                if (result!=null){
                    PreferencesHelper.getInstance().putString(ProjectConstants.KEY_ORG_LIST+CurrentUser.getInstance().getId(), JsonHelper.toJson(result));
                }
            }
        });
    }

    private void getUnderList() {
        Observable observable = ScheduleMethods.getInstance().getUnderList();
        toSubscribe(observable,  new Subscriber<TreeData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof ApiException && !TextUtils.isEmpty(e.getMessage())){
                    ToastHelper.showLongToast(e.getMessage());
                }
            }

            @Override
            public void onNext(TreeData result) {
                if (result!=null){
                    PreferencesHelper.getInstance().putString(ProjectConstants.KEY_UNDER_LIST+ CurrentUser.getInstance().getId(), JsonHelper.toJson(result));
                }
            }
        });
    }

    private void getAddress() {
        Observable observable = ScheduleMethods.getInstance().getAddress();
        toSubscribe(observable,  new Subscriber<List<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof ApiException && !TextUtils.isEmpty(e.getMessage())){
                    ToastHelper.showLongToast(e.getMessage());
                }
            }

            @Override
            public void onNext(List<String> result) {
                if (result!=null){
                    PreferencesHelper.getInstance().putString(ProjectConstants.KEY_ADDRESS_LIST+ CurrentUser.getInstance().getId(), JsonHelper.toJson(result));
                }
            }
        });
    }

    //RXjava取消注册，以避免内存泄露
    public void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();

            //解绑完不置null第二次绑定会有问题。
            mCompositeSubscription = null;
        }
    }

    protected <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {

        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }

        mCompositeSubscription.add(o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s));
    }

}
