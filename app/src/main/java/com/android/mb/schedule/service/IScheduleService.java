package com.android.mb.schedule.service;

import com.android.mb.schedule.retrofit.http.entity.HttpResult;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @created by cgy on 2017/6/19
 */
public interface IScheduleService {
    @POST("/app/user/login")
    Observable<HttpResult<Object>> userLogin(@Body RequestBody requestBody);

    @POST("/app/user/resetpwd")
    Observable<HttpResult<Object>> resetPwd(@Body RequestBody requestBody);

    @POST("/app/user/profile")
    Observable<HttpResult<Object>> setProfile(@Body RequestBody requestBody);

}
