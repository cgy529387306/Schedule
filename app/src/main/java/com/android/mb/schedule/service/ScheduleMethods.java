package com.android.mb.schedule.service;

import com.android.mb.schedule.retrofit.cache.transformer.CacheTransformer;
import com.android.mb.schedule.retrofit.http.RetrofitHttpClient;
import com.android.mb.schedule.retrofit.http.util.RequestBodyUtil;

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
        requestMap.put("sign",RequestUtils.getSignParams(requestMap));
        return getService().userLogin(RequestBodyUtil.getRequestBody(requestMap))
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<Object>());
    }


}
