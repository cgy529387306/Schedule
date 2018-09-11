package com.android.mb.schedule.service;

import com.android.mb.schedule.constants.ProjectConstants;
import com.android.mb.schedule.entitys.LoginData;
import com.android.mb.schedule.retrofit.cache.transformer.CacheTransformer;
import com.android.mb.schedule.retrofit.http.RetrofitHttpClient;
import com.android.mb.schedule.retrofit.http.util.RequestBodyUtil;
import com.android.mb.schedule.utils.PreferencesHelper;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * @Created by cgy on 2017/6/27
 */
@SuppressWarnings("unchecked")
public class ScheduleMethods extends BaseHttp {

    private ScheduleMethods(){}

    private static class SingletonHolder {
        private static final ScheduleMethods INSTANCE = new ScheduleMethods();
    }

    //获取单例
    public static ScheduleMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private IScheduleService getService() {
        return new RetrofitHttpClient.Builder()
                .baseUrl(getServerHost())
                .addHeader(getHead())
                .addDotNetDeserializer(false)
                .addLog(true)
                .build()
                .retrofit()
                .create(IScheduleService.class);
    }



    public Observable userLogin(Map<String,Object> requestMap){
        boolean isLogin = PreferencesHelper.getInstance().getBoolean(ProjectConstants.KEY_IS_LOGIN,false);
        if (isLogin){
            long tokenId = PreferencesHelper.getInstance().getLong(ProjectConstants.KEY_TOKEN_ID,0);
            String token = PreferencesHelper.getInstance().getString(ProjectConstants.KEY_TOKEN);
            requestMap.put("token_id",tokenId);
            requestMap.put("token",token);
        }
        return getService().userLogin(requestMap)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<LoginData>());
    }

    public Observable resetPwd(Map<String,Object> requestMap){
        boolean isLogin = PreferencesHelper.getInstance().getBoolean(ProjectConstants.KEY_IS_LOGIN,false);
        if (isLogin){
            long tokenId = PreferencesHelper.getInstance().getLong(ProjectConstants.KEY_TOKEN_ID,0);
            String token = PreferencesHelper.getInstance().getString(ProjectConstants.KEY_TOKEN);
            requestMap.put("token_id",tokenId);
            requestMap.put("token",token);
        }
        return getService().resetPwd(requestMap)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<Object>());
    }

    public Observable setProfile(Map<String,Object> requestMap){
        boolean isLogin = PreferencesHelper.getInstance().getBoolean(ProjectConstants.KEY_IS_LOGIN,false);
        if (isLogin){
            long tokenId = PreferencesHelper.getInstance().getLong(ProjectConstants.KEY_TOKEN_ID,0);
            String token = PreferencesHelper.getInstance().getString(ProjectConstants.KEY_TOKEN);
            requestMap.put("token_id",tokenId);
            requestMap.put("token",token);
        }
        return getService().setProfile(requestMap)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<Object>());
    }
}
