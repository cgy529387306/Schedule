package com.android.mb.schedule.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.android.mb.schedule.api.ScheduleMethods;
import com.android.mb.schedule.constants.ProjectConstants;
import com.android.mb.schedule.db.Delete;
import com.android.mb.schedule.db.GreenDaoManager;
import com.android.mb.schedule.db.Office;
import com.android.mb.schedule.db.Schedule;
import com.android.mb.schedule.db.User;
import com.android.mb.schedule.entitys.CurrentUser;
import com.android.mb.schedule.entitys.OfficeSyncData;
import com.android.mb.schedule.entitys.ScheduleDetailData;
import com.android.mb.schedule.entitys.ScheduleRequest;
import com.android.mb.schedule.entitys.ScheduleSync;
import com.android.mb.schedule.entitys.ScheduleSyncData;
import com.android.mb.schedule.entitys.TreeData;
import com.android.mb.schedule.entitys.UserBean;
import com.android.mb.schedule.entitys.UserSyncData;
import com.android.mb.schedule.greendao.DeleteDao;
import com.android.mb.schedule.greendao.OfficeDao;
import com.android.mb.schedule.greendao.ScheduleDao;
import com.android.mb.schedule.greendao.UserDao;
import com.android.mb.schedule.retrofit.http.exception.ApiException;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.JsonHelper;
import com.android.mb.schedule.utils.PreferencesHelper;
import com.android.mb.schedule.utils.ProjectHelper;
import com.android.mb.schedule.utils.ToastHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by cgy on 18/10/26.
 */

public class PostService extends Service {
    protected CompositeSubscription mCompositeSubscription;

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                DeleteDao deleteDao = GreenDaoManager.getInstance().getNewSession().getDeleteDao();
                List<Delete> deleteList = deleteDao.loadAll();
                if (Helper.isNotEmpty(deleteList)){
                    for (Delete delete:deleteList){
                        doDelete(delete.getSid());
                    }
                }
                long userId = CurrentUser.getInstance().getId();
                ScheduleDao scheduleDao = GreenDaoManager.getInstance().getNewSession().getScheduleDao();
                List<Schedule> scheduleList = scheduleDao.queryBuilder().where(ScheduleDao.Properties.Create_by.eq(userId)).where(ScheduleDao.Properties.Local.eq(1)).list();
                if (Helper.isNotEmpty(scheduleList)){
                    for (Schedule schedule:scheduleList){
                        doAdd(schedule);
                    }
                }
            }
        }).start();
    }

    private void doDelete(final long id){
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("id",id);
        Observable observable = ScheduleMethods.getInstance().deleteSchedule(requestMap);
        toSubscribe(observable,  new Subscriber<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object result) {
                DeleteDao deleteDao = GreenDaoManager.getInstance().getNewSession().getDeleteDao();
                deleteDao.deleteByKey(id);
            }
        });
    }

    private void doAdd(final Schedule schedule){
        Observable observable = ScheduleMethods.getInstance().addSchedule(ProjectHelper.transToRequest(schedule));
        toSubscribe(observable,  new Subscriber<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Object result) {
                schedule.setLocal(0);
                ScheduleDao scheduleDao = GreenDaoManager.getInstance().getNewSession().getScheduleDao();
                scheduleDao.update(schedule);
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
