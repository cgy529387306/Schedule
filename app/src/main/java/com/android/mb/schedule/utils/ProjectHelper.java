package com.android.mb.schedule.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;

import com.android.mb.schedule.entitys.ScheduleBean;
import com.android.mb.schedule.entitys.ScheduleData;
import com.android.mb.schedule.entitys.ScheduleDetailBean;
import com.android.mb.schedule.entitys.ScheduleDetailData;
import com.android.mb.schedule.entitys.ScheduleRequest;
import com.android.mb.schedule.entitys.ScheduleSection;
import com.android.mb.schedule.entitys.UserBean;
import com.android.mb.schedule.view.SelectPersonActivity;
import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProjectHelper {
    public static boolean isLogin(){
        return true;
    }

    /**
     * 防止控件被连续点击
     * @param view
     */
    public static void disableViewDoubleClick(final View view) {
        if(Helper.isNull(view)) {
            return;
        }
        view.setEnabled(false);
        view.postDelayed(new Runnable() {

            @Override
            public void run() {
                view.setEnabled(true);
            }
        }, 3000);
    }

    /***
     * 判断 String 是否是 int
     *
     * @param input
     * @return
     */
    public static boolean isInteger(String input){
        Matcher mer = Pattern.compile("^[+-]?[0-9]+$").matcher(input);
        return mer.find();
    }

    /***
     * 判断 String 是否是 int
     *
     * @param input
     * @return
     */
    public static boolean isHttp(String input){
        Matcher mer = Pattern.compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\\\/])+$").matcher(input);
        return mer.find();
    }

    /**
     * 将Map转化为Json
     *
     * @param map
     * @return String
     */
    public static <T> String mapToJson(Map<String, T> map) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(map);
        return jsonStr;
    }
    /**
     * 手机验证
     * @param telNum
     * @return
     */
    public static boolean isMobiPhoneNum(String telNum) {
        String regex = "^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(telNum);
        return m.matches();
    }

    /**
     * 电话号码验证
     * @author ：shijing
     * 2016年12月5日下午4:34:21
     * @param  str
     * @return 验证通过返回true
     */
    public static boolean isPhone(final String str) {
        Pattern p1 = null, p2 = null;
        Matcher m = null;
        boolean b = false;
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
        if (str.length() > 9) {
            m = p1.matcher(str);
            b = m.matches();
        } else {
            m = p2.matcher(str);
            b = m.matches();
        }
        return b;
    }

    /**
     * 密码格式验证
     * @param pwd
     * @return
     */
    public static boolean isPwdValid(String pwd) {
        String regex = "^[a-zA-Z0-9]{6,16}$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(pwd);
        return m.matches();
    }

    /**
     * 手机验证
     * @param idCard
     * @return
     */
    public static boolean isIdcard(String idCard) {
        String regex = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$|^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(idCard);
        return m.matches();
    }

    /**
     * 用Intent打开url(即处理外部链接地址)
     *
     * @param context
     * @param url
     */
    public static void openUrlWithIntent(Context context, String url) {
        if (Helper.isNull(context) || Helper.isEmpty(url)) {
            return;
        }
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 改变父空间触摸事件拦截状态
     *
     * @param parentView
     * @param isDisallow
     */
    public static void changeParentDisallowInterceptState(
            ViewParent parentView, boolean isDisallow) {
        if (Helper.isNull(parentView)) {
            return;
        }
        if (Helper.isNull(parentView.getParent())) {
            return;
        }
        // 改变触摸拦截状态
        parentView.requestDisallowInterceptTouchEvent(isDisallow);
        changeParentDisallowInterceptState(parentView.getParent(), isDisallow);
    }


    /**
     * 保存图片到相册
     *
     * @param context 上下文
     * @param bmp     待保存图片
     * @return 是否保存成功
     * @see <a
     * href="http://changshizhe.blog.51cto.com/6250833/1295241">source</a>
     */
    public static boolean saveImageToGallery(Context context, Bitmap bmp) {
        if (Helper.isNull(context)) {
            return false;
        }
        if (Helper.isNull(bmp)) {
            ToastHelper.showToast("图片保存失败！");
            return false;
        }
        String uriStr = MediaStore.Images.Media.insertImage(
                context.getContentResolver(), bmp, "", "");

        if (Helper.isEmpty(uriStr)) {
            ToastHelper.showToast("图片保存失败！");
            return false;
        }
        String picPath = getFilePathByContentResolver(context,
                Uri.parse(uriStr));
        if (Helper.isNotEmpty(picPath)) {
            ToastHelper.showToast("图片保存成功！");
        }
        return true;
    }

    /**
     * 获取插入图片路径
     *
     * @param context
     * @param uri
     * @return
     * @see <a
     * href="http://blog.csdn.net/xiaanming/article/details/8990627">source</a>
     */
    private static String getFilePathByContentResolver(Context context, Uri uri) {
        if (Helper.isNull(uri) || Helper.isNull(context)) {
            return null;
        }
        Cursor cursor = context.getContentResolver().query(uri, null, null,
                null, null);
        String filePath = null;
        if (Helper.isNull(cursor)) {
            throw new IllegalArgumentException("Query on " + uri
                    + " returns null result.");
        }
        try {
            if ((cursor.getCount() != 1) || !cursor.moveToFirst()) {
            } else {
                filePath = cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaColumns.DATA));
            }
        } finally {
            cursor.close();
        }
        return filePath;
    }


    /**
     * 浏览器打开指定Url
     *
     * @param context 上下文
     * @param url     链接地址
     */
    public static void openUrlByBrowse(Context context, String url) {
        if (Helper.isEmpty(context) || Helper.isEmpty(url)) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

    public static String getCommonText(String data){
        return data == null?"":data;
    }


    public static int getCommonSelection(String data){
        return data == null?0:data.length();
    }


    private final static String CHINESE_NUMBER[] = {"一", "二", "三", "四", "五",
            "六", "七", "八", "九", "十", "十一", "腊"};

    public static String getLunarMonth(int month){
        return month<0?"":CHINESE_NUMBER[month-1];
    }


    public static Map<String, Object> objectToMap(Object obj)  {
        Map<String, Object> map = new HashMap<String, Object>();
        if (obj != null) {
            try {
                Field[] declaredFields = obj.getClass().getDeclaredFields();
                for (Field field : declaredFields) {
                    field.setAccessible(true);
                    if (field.getName()!=null && field.get(obj)!=null){
                        map.put(field.getName(), field.get(obj));
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return map;
    }

    public static List<ScheduleBean> getScheduleList(String currentDate, List<ScheduleData> dataList)  {
        List<ScheduleBean> result = new ArrayList<>();
        for (int i=0; i<dataList.size();i++){
            ScheduleData scheduleData = dataList.get(i);
            String date = scheduleData.getDate();
            if (currentDate.equals(date)){
                result = scheduleData.getList();
                break;
            }
        }
        return result;
    }

    public static String getRepeatStr(int type){
        String result;
        switch (type){
            case 1:
                result = "一次性";
                break;
            case 2:
                result = "每天";
                break;
            case 3:
                result = "每周";
                break;
            case 4:
                result = "每月";
                break;
            default:
                result = "一次性";
                break;
        }
        return result;
    }

    public static String getRemindStr(int type){
        String result;
        switch (type){
            case 0:
                result = "不在提醒";
                break;
            case 1:
                result = "10分钟前";
                break;
            case 2:
                result = "15分钟前";
                break;
            case 3:
                result = "30分钟前";
                break;
            case 4:
                result = "1小时前";
                break;
            case 5:
                result = "2小时前";
                break;
            case 6:
                result = "24小时前";
                break;
            case 7:
                result = "2天前";
                break;
            default:
                result = "不在提醒";
                break;
        }
        return result;
    }

    public static ScheduleRequest transBean(ScheduleDetailData scheduleData){
        ScheduleRequest scheduleRequest = new ScheduleRequest();
        if (scheduleData!=null && scheduleData.getInfo()!=null){
            ScheduleDetailBean detailBean = scheduleData.getInfo();
            scheduleRequest.setId(detailBean.getId());
            scheduleRequest.setTitle(detailBean.getTitle());
            scheduleRequest.setDescription(detailBean.getDescription());
            scheduleRequest.setSummary(detailBean.getSummary());
            scheduleRequest.setAddress(detailBean.getAddress());
            scheduleRequest.setStart(detailBean.getTime_s());
            scheduleRequest.setEnd(detailBean.getTime_e());
            scheduleRequest.setAllDay(detailBean.getAllDay());
            scheduleRequest.setRepeattype(detailBean.getRepeattype());
            scheduleRequest.setRemind(detailBean.getRemind());
            scheduleRequest.setImportant(detailBean.getImportant());
//            scheduleRequest.setFid(0);
//            scheduleRequest.setShare("");
//            scheduleRequest.setRelated("");
        }
        return scheduleRequest;
    }

    public static String getSharePersonStr(List<UserBean> userList){
        StringBuilder shareStr = new StringBuilder();
        if (Helper.isNotEmpty(userList)){
            if (userList.size()>2){
                for (int i=0;i<3;i++) {
                    UserBean userBean = userList.get(i);
                    if (i==2){
                        shareStr.append(userBean.getNickname());
                    }else{
                        shareStr.append(userBean.getNickname()).append(",");
                    }
                }
            }else{
                for (int i=0;i<userList.size();i++) {
                    UserBean userBean = userList.get(i);
                    if (i==userList.size()-1){
                        shareStr.append(userBean.getNickname());
                    }else{
                        shareStr.append(userBean.getNickname()).append(",");
                    }
                }
            }
        }
        return shareStr.toString();
    }

    public static String getIdStr(List<UserBean> userList){
        StringBuilder shareIds = new StringBuilder();
        for (int i=0;i<userList.size();i++) {
            UserBean userBean = userList.get(i);
            if (i==userList.size()-1){
                shareIds.append(userBean.getId());
            }else{
                shareIds.append(userBean.getId()).append(",");
            }
        }
        return shareIds.toString();
    }

    public static List<Long> getSelectIdList(){
        List<Long> selectIdList = new ArrayList<>();
        if (Helper.isNotEmpty(SelectPersonActivity.mSelectList)){
            for (UserBean user:SelectPersonActivity.mSelectList) {
                selectIdList.add(user.getId());
            }
        }
        return selectIdList;
    }

    public static boolean isAmTime(String time){
        if (Helper.isEmpty(time)){
            return false;
        }
        Date date = Helper.string2Date(time,"HH:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY)<12;
    }

    public static List<ScheduleSection> getSectionData(List<ScheduleBean> dataList) {
        List<ScheduleSection> sectionList = new ArrayList<>();
        List<ScheduleBean> allDayList = new ArrayList<>();
        List<ScheduleBean> amList = new ArrayList<>();
        List<ScheduleBean> pmList = new ArrayList<>();
        for (ScheduleBean scheduleBean:dataList) {
            if (scheduleBean.getAllDay()==1){
                scheduleBean.setTimeType(0);
                allDayList.add(scheduleBean);
            }else if (ProjectHelper.isAmTime(scheduleBean.getStartTime())){
                scheduleBean.setTimeType(1);
                amList.add(scheduleBean);
            }else{
                scheduleBean.setTimeType(2);
                pmList.add(scheduleBean);
            }
        }
        if (Helper.isNotEmpty(allDayList)){
            sectionList.add(new ScheduleSection(true, "全天"));
            for (ScheduleBean scheduleBean:allDayList){
                sectionList.add(new ScheduleSection(scheduleBean));
            }
        }
        if (Helper.isNotEmpty(amList)){
            sectionList.add(new ScheduleSection(true, "上午"));
            for (ScheduleBean scheduleBean:amList){
                sectionList.add(new ScheduleSection(scheduleBean));
            }
        }
        if (Helper.isNotEmpty(pmList)){
            sectionList.add(new ScheduleSection(true, "下午"));
            for (ScheduleBean scheduleBean:pmList){
                sectionList.add(new ScheduleSection(scheduleBean));
            }
        }
        return sectionList;
    }

    public static String getMonday(Calendar calendar) {
        if (calendar == null){
            return Helper.date2String(Calendar.getInstance().getTime(),"yyyy-MM-dd");
        }
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); // 获取本周日的日期
        return Helper.date2String(calendar.getTime(),"yyyy-MM-dd");
    }

    public static boolean isSameDay(Date date, Date sameDate) {
        if (null == date || null == sameDate) {
            return false;
        }
        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTime(sameDate);
        nowCalendar.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);
        dateCalendar.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        return nowCalendar.get(Calendar.YEAR) == dateCalendar.get(Calendar.YEAR)
                && nowCalendar.get(Calendar.MONTH) == dateCalendar.get(Calendar.MONTH)
                && nowCalendar.get(Calendar.DATE) == dateCalendar.get(Calendar.DATE);

    }

    public static boolean isToday(String date){
        if (Helper.isEmpty(date)){
            return false;
        }
        Date date1 = Helper.string2Date(date,"yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        if (calendar.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR)
                &&calendar.get(Calendar.DAY_OF_YEAR) == Calendar.getInstance().get(Calendar.DAY_OF_YEAR)) {
            return true;
        }else{
            return false;
        }
    }

    public static long getInterval(Date date){
        if (date==null){
            return 0;
        }
        long startTime = date.getTime();
        long endTime = new Date().getTime();
        int newL = (int) ((endTime - startTime) / (1000 * 3600 * 24));
        return newL;
    }
}
