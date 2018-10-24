package com.android.mb.schedule.service;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.android.mb.schedule.R;
import com.android.mb.schedule.receiver.MyNewTaskReceiver;
import com.android.mb.schedule.view.MainActivity;

import java.util.Date;

public class LongRunningService extends Service {

    public int mInterval = 60*1000;//1分钟
    private AlarmManager mAlarmManager;
    private PendingIntent mPendingIntent;


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                doRemind();
            }
        }
    };

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
        super.onDestroy();
        mAlarmManager.cancel(mPendingIntent);
    }

    private void doJobTask(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("cgy", "executed at:" + new Date().toString());
                mHandler.sendEmptyMessage(1);
            }
        }).start();
        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long triggerAtTime = SystemClock.elapsedRealtime()+(mInterval);
        Intent i = new Intent(this,MyNewTaskReceiver.class);
        mPendingIntent = PendingIntent.getBroadcast(this,0,i,0);
        mAlarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,mPendingIntent);
    }

    private void doRemind(){
        showNotification(this,1001,"日程提醒","日程快要开始了");
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

}
