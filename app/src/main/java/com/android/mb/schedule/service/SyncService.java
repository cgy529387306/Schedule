package com.android.mb.schedule.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

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
import com.android.mb.schedule.rxbus.Events;
import com.android.mb.schedule.rxbus.RxBus;
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
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by cgy on 18/10/26.
 */

public class SyncService extends Service {
    protected CompositeSubscription mCompositeSubscription;

    private long mLastUpdateTime;

    private boolean mIsManual;
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
        mIsManual = intent.getBooleanExtra("isManual",false);
        doJobTask();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        onUnsubscribe();
        RxBus.getInstance().unSubscribe(this);
        super.onDestroy();
    }

    private void doJobTask(){
        mLastUpdateTime = PreferencesHelper.getInstance().getLong(ProjectConstants.KEY_LAST_SYNC+ CurrentUser.getInstance().getId(),0);
        syncSchedule();
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
                }else{
                    Log.d("SyncService","同步失败");
                }
            }

            @Override
            public void onNext(ScheduleSyncData result) {
                if (mIsManual){
                    ToastHelper.showLongToast("同步成功");
                }
                PreferencesHelper.getInstance().putLong(ProjectConstants.KEY_LAST_SYNC+ CurrentUser.getInstance().getId(),System.currentTimeMillis()/1000);
                sendMsg(ProjectConstants.EVENT_UPDATE_SCHEDULE_LIST,null);
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


    /**
     * 注册事件.
     * @param event
     * @param onNext
     */
    public void regiestEvent(int event,Action1<Events<?>> onNext){
        RxBus.init()
                .setEvent(event)
                .onNext(onNext)
                .create(this);
    }

    /**
     * 发送事件.
     * @param event
     * @param o
     */
    public void sendMsg(int event,Object o){
        RxBus.getInstance().send(event,o);
    }

}
