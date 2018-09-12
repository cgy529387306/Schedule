package com.android.mb.schedule.service;

import com.android.mb.schedule.constants.ProjectConstants;
import com.android.mb.schedule.entitys.CurrentUser;
import com.android.mb.schedule.entitys.LoginData;
import com.android.mb.schedule.retrofit.cache.transformer.CacheTransformer;
import com.android.mb.schedule.retrofit.http.RetrofitHttpClient;
import com.android.mb.schedule.retrofit.http.util.RequestBodyUtil;
import com.android.mb.schedule.utils.PreferencesHelper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
        if (CurrentUser.getInstance().isLogin()){
            requestMap.put("token_id",CurrentUser.getInstance().getToken_id());
            requestMap.put("token",CurrentUser.getInstance().getToken());
        }
        return getService().userLogin(requestMap)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<LoginData>());
    }

    public Observable resetPwd(Map<String,Object> requestMap){
        if (CurrentUser.getInstance().isLogin()){
            requestMap.put("token_id",CurrentUser.getInstance().getToken_id());
            requestMap.put("token",CurrentUser.getInstance().getToken());
        }
        return getService().resetPwd(requestMap)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<Object>());
    }

    public Observable setProfile(Map<String,Object> requestMap,File file){
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part requestBody =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        if (CurrentUser.getInstance().isLogin()){
            requestMap.put("token_id",CurrentUser.getInstance().getToken_id());
            requestMap.put("token",CurrentUser.getInstance().getToken());
        }
        return getService().setProfile(requestMap,requestBody)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<Object>());
    }

    public Observable getUserInfo(){
        Map<String,Object> requestMap = new HashMap<>();
        if (CurrentUser.getInstance().isLogin()){
            requestMap.put("token_id",CurrentUser.getInstance().getToken_id());
            requestMap.put("token",CurrentUser.getInstance().getToken());
        }
        return getService().getUserInfo(requestMap)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<LoginData>());
    }
}
