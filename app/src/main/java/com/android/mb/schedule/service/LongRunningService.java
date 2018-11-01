package com.android.mb.schedule.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.android.mb.schedule.R;
import com.android.mb.schedule.app.MBApplication;
import com.android.mb.schedule.db.GreenDaoManager;
import com.android.mb.schedule.db.Schedule;
import com.android.mb.schedule.entitys.CurrentUser;
import com.android.mb.schedule.greendao.ScheduleDao;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.NetworkHelper;
import com.android.mb.schedule.utils.ToastHelper;
import com.android.mb.schedule.view.MainActivity;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class LongRunningService extends Service {
    protected CompositeSubscription mCompositeSubscription;
    public int mInterval = 60*1000;//1分钟
    private Timer mTimer;
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
        mTimer.cancel();
        onUnsubscribe();
        super.onDestroy();
    }

    private void doJobTask(){
        mTimer = new Timer();
        if (!NetworkHelper.isNetworkAvailable(MBApplication.getInstance())){
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    getRemind();
                }
            },0,mInterval);
        }
    }

    private void doRemind(Schedule schedule){
        if (schedule!=null){
            showNotification(this,1001,"日程提醒","日程"+schedule.getTitle()+"快要开始了");
        }
    }

    private void showNotification(Context context, int id, String title, String text) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(title);
        builder.setContentText(text);
        builder.setAutoCancel(true);
        builder.setOnlyAlertOnce(true);
        builder.setDefaults(Notification.DEFAULT_VIBRATE);
        builder.setPriority(Notification.PRIORITY_DEFAULT);
        //自定义打开的界面
        Intent resultIntent = new Intent(context, MainActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager!=null){
            notificationManager.notify(id, builder.build());
        }
    }

    private void getRemind() {
        ScheduleDao scheduleDao = GreenDaoManager.getInstance().getNewSession().getScheduleDao();
        long userId = CurrentUser.getInstance().getId();
        Observable observable = scheduleDao.queryBuilder().where(ScheduleDao.Properties.Create_by.eq(userId)).rx().list();
        toSubscribe(observable,  new Subscriber<List<Schedule>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (Helper.isNotEmpty(e.getMessage())){
                    ToastHelper.showLongToast(e.getMessage());
                }
            }

            @Override
            public void onNext(List<Schedule> result) {
                if (Helper.isNotEmpty(result)){
                    long now = System.currentTimeMillis()/1000;
                    for (Schedule schedule:result){
                        if (getRemindTime(schedule)!=0 && now == getRemindTime(schedule)){
                            doRemind(schedule);
                        }
                    }
                }
            }
        });
    }

    private long getRemindTime(Schedule schedule){
        long remindTime = 0;
        if (schedule!=null){
            long start = schedule.getTime_s();
            switch (schedule.getRemind()){
                case 0:remindTime = 0;//不提醒
                    break;
                case 1:remindTime = start-10*60;//10分钟前
                    break;
                case 2:remindTime = start-15*60;//15分钟前
                    break;
                case 3:remindTime = start-30*60;//30分钟前
                    break;
                case 4:remindTime = start-60*60;//1小时前
                    break;
                case 5:remindTime = start-2*60*60;//2小时前
                    break;
                case 6:remindTime = start-24*60*60;//24小时前
                    break;
                case 7:remindTime = start-2*24*60*60;// 2天前
                    break;
                default:remindTime = 0;
                    break;
            }
        }
        return remindTime;
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
