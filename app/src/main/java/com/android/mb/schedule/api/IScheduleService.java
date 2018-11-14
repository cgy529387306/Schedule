package com.android.mb.schedule.api;

import com.android.mb.schedule.entitys.FileData;
import com.android.mb.schedule.entitys.KpiRequest;
import com.android.mb.schedule.entitys.LoginData;
import com.android.mb.schedule.entitys.MyScheduleBean;
import com.android.mb.schedule.entitys.OfficeSyncData;
import com.android.mb.schedule.entitys.RelatedBean;
import com.android.mb.schedule.entitys.ScheduleData;
import com.android.mb.schedule.entitys.ScheduleDetailData;
import com.android.mb.schedule.entitys.ScheduleSyncData;
import com.android.mb.schedule.entitys.SearchBean;
import com.android.mb.schedule.entitys.ShareBean;
import com.android.mb.schedule.entitys.TagBean;
import com.android.mb.schedule.entitys.TagData;
import com.android.mb.schedule.entitys.TreeData;
import com.android.mb.schedule.entitys.UserBean;
import com.android.mb.schedule.entitys.UserSyncData;
import com.android.mb.schedule.retrofit.http.entity.HttpResult;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * @created by cgy on 2017/6/19
 */
public interface IScheduleService {
    @POST("/app/user/login")
    @FormUrlEncoded
    Observable<HttpResult<LoginData>> userLogin(@FieldMap Map<String,Object> requestMap);

    @POST("/app/user/bindWx")
    @FormUrlEncoded
    Observable<HttpResult<Object>> bindWx(@FieldMap Map<String,Object> requestMap);

    @POST("/app/user/resetpwd")
    @FormUrlEncoded
    Observable<HttpResult<Object>> resetPwd(@FieldMap Map<String,Object> requestMap);

    @POST("/app/user/profile")
    @FormUrlEncoded
    Observable<HttpResult<Object>> setProfile(@FieldMap Map<String,Object> requestMap);

    @POST("/app/user/get")
    @FormUrlEncoded
    Observable<HttpResult<LoginData>> getUserInfo(@FieldMap Map<String,Object> requestMap);

    @POST("/app/user/getadr")
    @FormUrlEncoded
    Observable<HttpResult<List<String>>> getAddress(@FieldMap Map<String,Object> requestMap);

    @POST("/app/user/getman")
    @FormUrlEncoded
    Observable<HttpResult<List<UserBean>>> getPersons(@FieldMap Map<String,Object> requestMap);

    @POST("/app/schedule/upload")
    @Multipart
    Observable<HttpResult<FileData>> upload(@QueryMap Map<String,Object> requestMap, @Part MultipartBody.Part file);

    @POST("app/schedule/add")
    @FormUrlEncoded
    Observable<HttpResult<Object>> addSchedule(@FieldMap Map<String,Object> requestMap);

    @POST("app/schedule/edit")
    @FormUrlEncoded
    Observable<HttpResult<Object>> editSchedule(@FieldMap Map<String,Object> requestMap);

    @POST("/app/schedule/get")
    @FormUrlEncoded
    Observable<HttpResult<ScheduleDetailData>> getSchedule(@FieldMap Map<String,Object> requestMap);

    @POST("/app/schedule/toshare")
    @FormUrlEncoded
    Observable<HttpResult<Object>> shareTo(@FieldMap Map<String,Object> requestMap);

    @POST("/app/schedule/del")
    @FormUrlEncoded
    Observable<HttpResult<Object>> deleteSchedule(@FieldMap Map<String,Object> requestMap);

    @POST("/app/schedule/day")
    @FormUrlEncoded
    Observable<HttpResult<Object>> getDaySchedule(@FieldMap Map<String,Object> requestMap);

    @POST("/app/schedule/month")
    @FormUrlEncoded
    Observable<HttpResult<List<ScheduleData>>> getMonthSchedule(@FieldMap Map<String,Object> requestMap);

    @POST("/app/schedule/week")
    @FormUrlEncoded
    Observable<HttpResult<List<ScheduleData>>> getWeekSchedule(@FieldMap Map<String,Object> requestMap);

    @POST("/app/schedule/ishare")
    @FormUrlEncoded
    Observable<HttpResult<List<ShareBean>>> getIShare(@FieldMap Map<String,Object> requestMap);

    @POST("/app/schedule/share")
    @FormUrlEncoded
    Observable<HttpResult<List<RelatedBean>>> getOtherShare(@FieldMap Map<String,Object> requestMap);

    @POST("/app/schedule/related")
    @FormUrlEncoded
    Observable<HttpResult<List<RelatedBean>>> getRelated(@FieldMap Map<String,Object> requestMap);

    @POST("/app/schedule/mine")
    @FormUrlEncoded
    Observable<HttpResult<List<MyScheduleBean>>> getMine(@FieldMap Map<String,Object> requestMap);

    @POST("/app/schedule/other")
    @FormUrlEncoded
    Observable<HttpResult<List<RelatedBean>>> getOther(@FieldMap Map<String,Object> requestMap);

    @POST("/app/common/area")
    @FormUrlEncoded
    Observable<HttpResult<Object>> getAreaList(@FieldMap Map<String,Object> requestMap);

    @POST("/app/common/office")
    @FormUrlEncoded
    Observable<HttpResult<TreeData>> getOfficeList(@FieldMap Map<String,Object> requestMap);

    @POST("/app/common/office2")
    @FormUrlEncoded
    Observable<HttpResult<TreeData>> getUnder(@FieldMap Map<String,Object> requestMap);

    @POST("/app/common/sch")
    @FormUrlEncoded
    Observable<HttpResult<List<SearchBean>>> searchPeople(@FieldMap Map<String,Object> requestMap);

    /**
     * stamp 时间戳
     * @param requestMap
     * @return
     */
    @POST("/app/schedule/sync")
    @FormUrlEncoded
    Observable<HttpResult<ScheduleSyncData>> syncSchedule(@FieldMap Map<String,Object> requestMap);

    @POST("app/common/sync_office")
    @FormUrlEncoded
    Observable<HttpResult<OfficeSyncData>> syncOffice(@FieldMap Map<String,Object> requestMap);

    @POST("app/common/sync_admin")
    @FormUrlEncoded
    Observable<HttpResult<UserSyncData>> syncPeople(@FieldMap Map<String,Object> requestMap);

    @POST("app/common/sync_edit")
    @FormUrlEncoded
    Observable<HttpResult<Object>> syncEdit(@FieldMap Map<String,Object> requestMap);

    @POST("app/schedule/add")
    @FormUrlEncoded
    Observable<HttpResult<Object>> addKpi(@FieldMap Map<String,Object> requestMap);

    @POST("app/schedule/edit")
    @FormUrlEncoded
    Observable<HttpResult<Object>> editKpi(@FieldMap Map<String,Object> requestMap);

    @POST("/app/schedule/get")
    @FormUrlEncoded
    Observable<HttpResult<KpiRequest>> getKpi(@FieldMap Map<String,Object> requestMap);

    @POST("/app/common/tags")
    @FormUrlEncoded
    Observable<HttpResult<TagData>> getTagList(@FieldMap Map<String,Object> requestMap);

    @POST("/app/schedule/other")
    @FormUrlEncoded
    Observable<HttpResult<List<UserBean>>> getTagPerson(@FieldMap Map<String,Object> requestMap);



}
