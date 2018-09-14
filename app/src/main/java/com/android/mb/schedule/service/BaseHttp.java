package com.android.mb.schedule.service;


import com.android.mb.schedule.entitys.CurrentUser;
import com.android.mb.schedule.retrofit.http.entity.HttpResult;
import com.android.mb.schedule.retrofit.http.exception.ApiException;

import java.util.HashMap;
import java.util.Map;

import rx.functions.Func1;

/**
 * @Description 基础
 * @created by cgy on 2017/6/19
 * @version v1.0
 *
 */

public class BaseHttp {



    public String getServerHost() {
        return "https://shop.5979wenhua.com";
    }

    @SuppressWarnings("unchecked")
    static class HttpCacheResultFunc<T> implements Func1 {
        @Override
        public Object call(Object o) {
            HttpResult<T> httpResult;
            if (o instanceof HttpResult) {
                httpResult = (HttpResult<T>) o;
                if (httpResult.getCode() != 1) {
                    throw new ApiException(40003, httpResult.getMsg());
                }
                return httpResult.getData();
            }
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    static class HttpResultFunc<T> implements Func1 {
        @Override
        public Object call(Object o) {
            HttpResult<T> httpResult;
            if (o instanceof HttpResult) {
                httpResult = (HttpResult<T>) o;
                return httpResult;
            }
            return null;
        }
    }

    /**
     * 获取头部信息
     *
     * @return Map
     */
    Map<String, String> getHead() {
        Map<String, String> cloudOfficeHeader = new HashMap<String, String>();
        if (CurrentUser.getInstance().isLogin()){
            cloudOfficeHeader.put("token_id",CurrentUser.getInstance().getToken_id()+"");
            cloudOfficeHeader.put("token",CurrentUser.getInstance().getToken());
        }
        return cloudOfficeHeader;
    }

}
