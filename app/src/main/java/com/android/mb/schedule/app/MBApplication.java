package com.android.mb.schedule.app;

import android.app.Application;
import android.content.Context;

import com.pgyersdk.crash.PgyCrashManager;

import cn.jpush.android.api.JPushInterface;

/**
 * 主要用来获取全局的Context
 * @author cgy
 *
 */
public class MBApplication extends Application {
	
	private static final String TAG = MBApplication.class.getSimpleName();
	
	private static Context sInstance;
	
	/**
	 * 获取全局Application对象
	 * @since 2013.08.02 修改错误提示内容为Application by pcqpcq
	 * @return
	 */
	public static Context getInstance(){
		if (sInstance == null) {
			throw new RuntimeException("Application must be init");
		}
		return sInstance;
	}

    /**
     * 初始化
     * <p>若未配置manifest可使用此方法初始化</p>
     * @param application 全局context
     */
    public static void init(Context application) {
        sInstance = application;
    }

    @Override
	public void onCreate() {
		super.onCreate();
		sInstance = this;
		initJPush();
		PgyCrashManager.register(this);
	}

	private void initJPush(){
		JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
		JPushInterface.init(this);
	}
}
