package com.android.mb.schedule.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.android.mb.schedule.R;
import com.android.mb.schedule.constants.ProjectConstants;

public class NotificationHelper {

    public static void showNotification(Context context, int id, String title, String text,Intent resultIntent) {
        boolean isVibrate = PreferencesHelper.getInstance().getBoolean(ProjectConstants.KEY_IS_VIBRATE,true);
        String path = PreferencesHelper.getInstance().getString(ProjectConstants.KEY_RING);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(title);
        builder.setContentText(text);
        builder.setAutoCancel(true);
        builder.setOnlyAlertOnce(true);
        builder.setPriority(Notification.PRIORITY_DEFAULT);
        if (isVibrate){
            builder.setVibrate(new long[]{1000, 100, 1000});
        }else{
            builder.setVibrate(new long[]{0});
        }
        if (Helper.isNotEmpty(path)){
            try {
                Uri sound = Uri.parse(path);
                builder.setSound(sound);
            }catch (Exception e){
                e.printStackTrace();
                builder.setDefaults(Notification.DEFAULT_SOUND);
            }
        }else{
            builder.setDefaults(Notification.DEFAULT_SOUND);
        }
        if (resultIntent!=null){
            //自定义打开的界面
            resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                    resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIntent);
        }

        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager!=null){
            notificationManager.notify(id, notification);
        }
    }
}
