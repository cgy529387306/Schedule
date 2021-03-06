package com.android.mb.schedule.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.mb.schedule.constants.ProjectConstants;
import com.android.mb.schedule.entitys.CurrentUser;
import com.android.mb.schedule.entitys.PushExtras;
import com.android.mb.schedule.rxbus.RxBus;
import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.JsonHelper;
import com.android.mb.schedule.utils.NavigationHelper;
import com.android.mb.schedule.utils.NotificationHelper;
import com.android.mb.schedule.utils.PreferencesHelper;
import com.android.mb.schedule.view.LoginActivity;
import com.android.mb.schedule.view.MainActivity;
import com.android.mb.schedule.view.ScheduleDetailActivity;
import com.android.mb.schedule.view.ScheduleRelateActivity;
import com.android.mb.schedule.view.WeekReportActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 *
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle,context));
		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
			String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
			PreferencesHelper.getInstance().putString(ProjectConstants.KEY_REGISTRATION_ID, regId);
		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
//			openNotification(context, bundle);
		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
			receiveNotification(context, bundle);
		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
			openNotification(context,bundle);
		} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
			Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
			//在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
		} else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
			boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
			Log.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
		} else {
			Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
		}
	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle, Context context) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
				if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
					Log.i(TAG, "This message has no Extra data");
					continue;
				}

				try {
					JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
					Iterator<String> it =  json.keys();

					while (it.hasNext()) {
						String myKey = it.next().toString();
						sb.append("\nkey:" + key + ", value: [" +
								myKey + " - " +json.optString(myKey) + "]");
					}
				} catch (JSONException e) {
					Log.e(TAG, "Get message extra JSON error!");
				}

			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		Log.e("Jpush", sb.toString());
		return sb.toString();
	}

//	private void openCustomNotification(Context context, Bundle bundle){
//		String content = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//		String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
//		String title = bundle.getString(JPushInterface.EXTRA_TITLE);
//		System.out.println("收到了自定义消息@@消息内容是:"+ content);
//		System.out.println("收到了自定义消息@@消息extra是:"+ extra);
//		PushExtras pushExtras = JsonHelper.fromJson(extra,PushExtras.class);
//		Intent intent = null;
//		if (pushExtras!=null){
//			intent = new Intent(context,ScheduleDetailActivity.class);
//			intent.putExtra("id",pushExtras.getId());
//		}
//		NotificationHelper.showNotification(context,1002,title,content,intent);
//	}

	private void receiveNotification(Context context, Bundle bundle){
		String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
		if (Helper.isNotEmpty(extra)){
			PushExtras pushExtras = JsonHelper.fromJson(extra,PushExtras.class);
			if (pushExtras!=null){
				if (Helper.isNotEmpty(pushExtras.getType()) && "week".equals(pushExtras.getType())){
					PreferencesHelper.getInstance().putBoolean(ProjectConstants.KEY_HAS_NEW_NOTIFY+CurrentUser.getInstance().getId(), true);
					sendMsg(ProjectConstants.EVENT_NEW_NOTIFY,null);
				}
			}
		}
	}

	private void openNotification(Context context, Bundle bundle){
		String content = bundle.getString(JPushInterface.EXTRA_MESSAGE);
		String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
		int notificationId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
		JPushInterface.removeLocalNotification(context,notificationId);
		JPushInterface.clearAllNotifications(context);
		System.out.println("收到了自定义消息@@消息内容是:"+ content);
		System.out.println("收到了自定义消息@@消息extra是:"+ extra);
		PushExtras pushExtras = JsonHelper.fromJson(extra,PushExtras.class);
		if (pushExtras!=null){
			if (Helper.isNotEmpty(pushExtras.getType()) && "week".equals(pushExtras.getType())){
				if (CurrentUser.getInstance().isLogin()){
					sendMsg(ProjectConstants.EVENT_NEW_NOTIFY_HIDE,null);
					Intent intent = new Intent(context,WeekReportActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(intent);
				}else{
					Intent intent = new Intent(context,LoginActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(intent);
				}
			}else{
				if (CurrentUser.getInstance().isLogin()){
					Intent intent = new Intent(context,ScheduleDetailActivity.class);
					intent.putExtra("id",pushExtras.getId());
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(intent);
				}else{
					Intent intent = new Intent(context,LoginActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(intent);
				}
			}
		}
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
