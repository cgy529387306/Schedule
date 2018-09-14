package com.android.mb.schedule.service;

import com.android.mb.schedule.entitys.LoginData;
import com.android.mb.schedule.retrofit.http.entity.HttpResult;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * @created by cgy on 2017/6/19
 */
public interface IScheduleService {
    @POST("/app/user/login")
    @FormUrlEncoded
    Observable<HttpResult<LoginData>> userLogin(@FieldMap Map<String,Object> requestMap);

    @POST("/app/user/resetpwd")
    @FormUrlEncoded
    Observable<HttpResult<Object>> resetPwd(@FieldMap Map<String,Object> requestMap);

    @POST("/app/user/profile")
    @FormUrlEncoded
    Observable<HttpResult<Object>> setProfile(@FieldMap Map<String,Object> requestMap);

    @POST("/app/user/get")
    @FormUrlEncoded
    Observable<HttpResult<LoginData>> getUserInfo(@FieldMap Map<String,Object> requestMap);

    @POST("/app/schedule/upload")
    @Multipart
    Observable<HttpResult<Object>> upload(@PartMap Map<String,RequestBody> requestMap, @Part MultipartBody.Part file);

    @POST("app/schedule/add")
    @FormUrlEncoded
    Observable<HttpResult<Object>> addSchedule(@FieldMap Map<String,Object> requestMap);

    @POST("app/schedule/edit")
    @FormUrlEncoded
    Observable<HttpResult<Object>> editSchedule(@FieldMap Map<String,Object> requestMap);

    @POST("/app/common/area")
    @FormUrlEncoded
    Observable<HttpResult<Object>> getAreaList(@FieldMap Map<String,Object> requestMap);

    @POST("/app/common/office")
    @FormUrlEncoded
    Observable<HttpResult<Object>> getOfficeList(@FieldMap Map<String,Object> requestMap);

}
