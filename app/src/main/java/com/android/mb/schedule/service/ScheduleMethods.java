package com.android.mb.schedule.service;

import com.android.mb.schedule.entitys.CurrentUser;
import com.android.mb.schedule.entitys.FileData;
import com.android.mb.schedule.entitys.LoginData;
import com.android.mb.schedule.entitys.ScheduleData;
import com.android.mb.schedule.entitys.ScheduleDetailData;
import com.android.mb.schedule.entitys.ScheduleRequest;
import com.android.mb.schedule.entitys.TreeData;
import com.android.mb.schedule.entitys.UserBean;
import com.android.mb.schedule.retrofit.cache.transformer.CacheTransformer;
import com.android.mb.schedule.retrofit.http.RetrofitHttpClient;
import com.android.mb.schedule.utils.ProjectHelper;

import java.io.File;
import java.util.HashMap;
import java.util.List;
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

    public Observable setProfile(Map<String,Object> requestMap){
        if (CurrentUser.getInstance().isLogin()){
            requestMap.put("token_id",CurrentUser.getInstance().getToken_id());
            requestMap.put("token",CurrentUser.getInstance().getToken());
        }
        return getService().setProfile(requestMap)
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

    public Observable getAddress(){
        Map<String,Object> requestMap = new HashMap<>();
        if (CurrentUser.getInstance().isLogin()){
            requestMap.put("token_id",CurrentUser.getInstance().getToken_id());
            requestMap.put("token",CurrentUser.getInstance().getToken());
        }
        return getService().getAddress(requestMap)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<List<String>>());
    }

    public Observable getPersons(){
        Map<String,Object> requestMap = new HashMap<>();
        if (CurrentUser.getInstance().isLogin()){
            requestMap.put("token_id",CurrentUser.getInstance().getToken_id());
            requestMap.put("token",CurrentUser.getInstance().getToken());
        }
        return getService().getPersons(requestMap)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<List<UserBean>>());
    }

    public Observable upload(File file){
        Map<String,Object> requestMap = new HashMap<>();
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part requestBody =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        if (CurrentUser.getInstance().isLogin()){
            requestMap.put("token_id",CurrentUser.getInstance().getToken_id());
            requestMap.put("token",CurrentUser.getInstance().getToken());
        }
        return getService().upload(requestMap,requestBody)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<FileData>());
    }

    private RequestBody convertToRequestBody(String param){
        return RequestBody.create(MediaType.parse("text/plain"), param);
    }

    public Observable addSchedule(ScheduleRequest scheduleRequest){
        Map<String,Object> requestMap = ProjectHelper.objectToMap(scheduleRequest);
        if (CurrentUser.getInstance().isLogin()){
            requestMap.put("token_id",CurrentUser.getInstance().getToken_id());
            requestMap.put("token",CurrentUser.getInstance().getToken());
        }
        return getService().addSchedule(requestMap)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<Object>());
    }

    public Observable editSchedule(ScheduleRequest scheduleRequest){
        Map<String,Object> requestMap = ProjectHelper.objectToMap(scheduleRequest);
        if (CurrentUser.getInstance().isLogin()){
            requestMap.put("token_id",CurrentUser.getInstance().getToken_id());
            requestMap.put("token",CurrentUser.getInstance().getToken());
        }
        return getService().editSchedule(requestMap)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<Object>());
    }

    public Observable getSchedule(Map<String,Object> requestMap){
        if (CurrentUser.getInstance().isLogin()){
            requestMap.put("token_id",CurrentUser.getInstance().getToken_id());
            requestMap.put("token",CurrentUser.getInstance().getToken());
        }
        return getService().getSchedule(requestMap)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<ScheduleDetailData>());
    }

    public Observable deleteSchedule(Map<String,Object> requestMap){
        if (CurrentUser.getInstance().isLogin()){
            requestMap.put("token_id",CurrentUser.getInstance().getToken_id());
            requestMap.put("token",CurrentUser.getInstance().getToken());
        }
        return getService().deleteSchedule(requestMap)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<Object>());
    }

    public Observable getDaySchedule(Map<String,Object> requestMap){
        if (CurrentUser.getInstance().isLogin()){
            requestMap.put("token_id",CurrentUser.getInstance().getToken_id());
            requestMap.put("token",CurrentUser.getInstance().getToken());
        }
        return getService().getDaySchedule(requestMap)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<Object>());
    }

    public Observable getMonthSchedule(Map<String,Object> requestMap){
        if (CurrentUser.getInstance().isLogin()){
            requestMap.put("token_id",CurrentUser.getInstance().getToken_id());
            requestMap.put("token",CurrentUser.getInstance().getToken());
        }
        return getService().getMonthSchedule(requestMap)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<List<ScheduleData>>());
    }

    public Observable getWeekSchedule(Map<String,Object> requestMap){
        if (CurrentUser.getInstance().isLogin()){
            requestMap.put("token_id",CurrentUser.getInstance().getToken_id());
            requestMap.put("token",CurrentUser.getInstance().getToken());
        }
        return getService().getWeekSchedule(requestMap)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<Object>());
    }

    public Observable getIShare(){
        Map<String,Object> requestMap = new HashMap<>();
        if (CurrentUser.getInstance().isLogin()){
            requestMap.put("token_id",CurrentUser.getInstance().getToken_id());
            requestMap.put("token",CurrentUser.getInstance().getToken());
        }
        return getService().getIShare(requestMap)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<LoginData>());
    }

    public Observable getShare(){
        Map<String,Object> requestMap = new HashMap<>();
        if (CurrentUser.getInstance().isLogin()){
            requestMap.put("token_id",CurrentUser.getInstance().getToken_id());
            requestMap.put("token",CurrentUser.getInstance().getToken());
        }
        return getService().getShare(requestMap)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<LoginData>());
    }

    public Observable getRelated(){
        Map<String,Object> requestMap = new HashMap<>();
        if (CurrentUser.getInstance().isLogin()){
            requestMap.put("token_id",CurrentUser.getInstance().getToken_id());
            requestMap.put("token",CurrentUser.getInstance().getToken());
        }
        return getService().getRelated(requestMap)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<LoginData>());
    }

    public Observable getAreaList(){
        Map<String,Object> requestMap = new HashMap<>();
        if (CurrentUser.getInstance().isLogin()){
            requestMap.put("token_id",CurrentUser.getInstance().getToken_id());
            requestMap.put("token",CurrentUser.getInstance().getToken());
        }
        return getService().getAreaList(requestMap)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<Object>());
    }

    public Observable getOfficeList(){
        Map<String,Object> requestMap = new HashMap<>();
        if (CurrentUser.getInstance().isLogin()){
            requestMap.put("token_id",CurrentUser.getInstance().getToken_id());
            requestMap.put("token",CurrentUser.getInstance().getToken());
        }
        return getService().getOfficeList(requestMap)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<TreeData>());
    }
}
