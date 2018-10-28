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

import com.android.mb.schedule.api.BaseHttp;
import com.android.mb.schedule.db.GreenDaoManager;
import com.android.mb.schedule.db.Office;
import com.android.mb.schedule.db.Schedule;
import com.android.mb.schedule.db.User;
import com.android.mb.schedule.entitys.CurrentUser;
import com.android.mb.schedule.entitys.FileBean;
import com.android.mb.schedule.entitys.MyScheduleBean;
import com.android.mb.schedule.entitys.PeopleSection;
import com.android.mb.schedule.entitys.RelatedBean;
import com.android.mb.schedule.entitys.ScheduleBean;
import com.android.mb.schedule.entitys.ScheduleData;
import com.android.mb.schedule.entitys.ScheduleDetailBean;
import com.android.mb.schedule.entitys.ScheduleDetailData;
import com.android.mb.schedule.entitys.ScheduleRequest;
import com.android.mb.schedule.entitys.ScheduleSection;
import com.android.mb.schedule.entitys.SearchBean;
import com.android.mb.schedule.entitys.ShareBean;
import com.android.mb.schedule.entitys.UserBean;
import com.android.mb.schedule.greendao.OfficeDao;
import com.android.mb.schedule.greendao.UserDao;
import com.android.mb.schedule.view.SelectPersonActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

    public static List<MyScheduleBean> transToMyScheduleList(List<Schedule> dataList){
        List<MyScheduleBean> result = new ArrayList<>();
        if (Helper.isNotEmpty(dataList)){
            for (Schedule schedule:dataList){
                MyScheduleBean myScheduleBean = new MyScheduleBean();
                myScheduleBean.setId(schedule.getId());
                myScheduleBean.setTitle(schedule.getTitle());
                myScheduleBean.setDescription(schedule.getDescription());
                myScheduleBean.setDate(schedule.getDate());
                myScheduleBean.setTime_s(schedule.getTime_s());
                myScheduleBean.setTime_e(schedule.getTime_e());
                myScheduleBean.setAddress(schedule.getAddress());
                myScheduleBean.setStartTime(schedule.getStartTime());
                myScheduleBean.setEndTime(schedule.getEndTime());
                myScheduleBean.setAllDay(schedule.getAllDay());
                myScheduleBean.setRepeattype(schedule.getRepeattype());
                myScheduleBean.setRemind(schedule.getRemind());
                myScheduleBean.setImportant(schedule.getImportant());
                myScheduleBean.setSummary(schedule.getSummary());
                myScheduleBean.setNot_remind_related(schedule.getNot_remind_related()+"");
                result.add(myScheduleBean);
            }
        }
        return result;
    }

    public static List<RelatedBean> transToRelateScheduleList(List<Schedule> dataList){
        List<RelatedBean> result = new ArrayList<>();
        UserDao userDao = GreenDaoManager.getInstance().getNewSession().getUserDao();
        OfficeDao officeDao = GreenDaoManager.getInstance().getNewSession().getOfficeDao();
        if (Helper.isNotEmpty(dataList)){
            for (Schedule schedule:dataList){
                RelatedBean relatedBean = new RelatedBean();
                relatedBean.setId(schedule.getId());
                relatedBean.setTitle(schedule.getTitle());
                relatedBean.setAddress(schedule.getAddress());
                relatedBean.setImportant(schedule.getImportant());
                String startTime = Helper.long2DateString(schedule.getTime_s()*1000,"MM-dd HH:mm");
                String endTime = Helper.long2DateString(schedule.getTime_e()*1000,"MM-dd HH:mm");
                relatedBean.setTime(startTime+"-"+endTime);
                relatedBean.setCreate_date(Helper.long2DateString(schedule.getCreatetime()*1000));
                User user = userDao.loadByRowId(schedule.getCreate_by());
                Office office = officeDao.load(user.getOffice_id());
                if (user!=null){
                    String userName = user.getNickname()==null?user.getUsername():user.getNickname();
                    String officeName = office.getName();
                    relatedBean.setUser_avatar(BaseHttp.BASE_URL+user.getAvatar());
                    relatedBean.setUser_name(userName);
                    relatedBean.setUser_office(officeName);
                }
                result.add(relatedBean);
            }
        }
        return result;
    }

    public static List<ShareBean> transToShareScheduleList(List<Schedule> dataList){
        List<ShareBean> result = new ArrayList<>();
        if (Helper.isNotEmpty(dataList)){
            for (Schedule schedule:dataList){
                ShareBean shareBean = new ShareBean();
                shareBean.setId(schedule.getId());
                shareBean.setTitle(schedule.getTitle());
                shareBean.setAddress(schedule.getAddress());
                String startTime = Helper.long2DateString(schedule.getTime_s()*1000,"MM-dd HH:mm");
                String endTime = Helper.long2DateString(schedule.getTime_e()*1000,"MM-dd HH:mm");
                shareBean.setTime(startTime+"-"+endTime);
                shareBean.setCreate_date(Helper.long2DateString(schedule.getCreatetime()*1000));
                List<UserBean> share = JsonHelper.fromJson(schedule.getShare(),new TypeToken<List<UserBean>>(){}.getType());
                shareBean.setShare(share);
                shareBean.setImportant(schedule.getImportant());

                result.add(shareBean);
            }
        }
        return result;
    }


    public static ScheduleDetailData transToScheduleDetailData(Schedule data){
        ScheduleDetailData scheduleDetailData = new ScheduleDetailData();
        if (data!=null){
            ScheduleDetailBean scheduleDetailBean = new ScheduleDetailBean();
            scheduleDetailBean.setId(data.getId());
            scheduleDetailBean.setTitle(data.getTitle());
            scheduleDetailBean.setDescription(data.getDescription());
            scheduleDetailBean.setSummary(data.getSummary());
            scheduleDetailBean.setAddress(data.getAddress());
            scheduleDetailBean.setTime_s(data.getTime_s());
            scheduleDetailBean.setTime_e(data.getTime_e());
            scheduleDetailBean.setDate(data.getDate());
            scheduleDetailBean.setStartTime(data.getStartTime());
            scheduleDetailBean.setEndTime(data.getEndTime());
            scheduleDetailBean.setAllDay(data.getAllDay());
            scheduleDetailBean.setRepeattype(data.getRepeattype());
            scheduleDetailBean.setRemind(data.getRemind());
            scheduleDetailBean.setImportant(data.getImportant());
            scheduleDetailBean.setCreate_by(CurrentUser.getInstance().getId());
            scheduleDetailBean.setUpdate_by(CurrentUser.getInstance().getId());
            scheduleDetailBean.setCreatetime(data.getCreatetime());
            scheduleDetailBean.setUpdatetime(data.getUpdatetime());
            scheduleDetailData.setInfo(scheduleDetailBean);
            scheduleDetailBean.setNot_remind_related(data.getNot_remind_related()+"");
            scheduleDetailData.setInfo(scheduleDetailBean);

            List<UserBean> related = JsonHelper.fromJson(data.getRelated(),new TypeToken<List<UserBean>>(){}.getType());
            List<UserBean> share = JsonHelper.fromJson(data.getShare(),new TypeToken<List<UserBean>>(){}.getType());
            List<FileBean> file = JsonHelper.fromJson(data.getShare(),new TypeToken<List<FileBean>>(){}.getType());
            scheduleDetailData.setRelated(related);
            scheduleDetailData.setShare(share);
            scheduleDetailData.setFile(file);
        }
        return scheduleDetailData;
    }

    public static Schedule transToSchedule(ScheduleRequest scheduleRequest,boolean isEdit){
        Schedule schedule = new Schedule();
        if (scheduleRequest!=null){
            if (isEdit){
                schedule.setId(scheduleRequest.getId());
            }
            schedule.setCreate_by(CurrentUser.getInstance().getId());
            schedule.setTitle(scheduleRequest.getTitle());
            schedule.setDescription(scheduleRequest.getDescription());
            schedule.setDate(Helper.date2String(new Date(),"yyyy-MM-dd"));
            schedule.setTime_s(scheduleRequest.getStart());
            schedule.setTime_e(scheduleRequest.getEnd());
            schedule.setStartTime(Helper.long2DateString(scheduleRequest.getStart()*1000,"HH:mm"));
            schedule.setEndTime(Helper.long2DateString(scheduleRequest.getEnd()*1000,"HH:mm"));
            schedule.setAddress(scheduleRequest.getAddress());
            schedule.setAllDay(scheduleRequest.getAllDay());
            schedule.setRepeattype(scheduleRequest.getRepeattype());
            schedule.setRemind(scheduleRequest.getRemind());
            schedule.setImportant(scheduleRequest.getImportant());
            schedule.setSummary(scheduleRequest.getSummary());
//            schedule.setNot_remind_related(scheduleRequest.getno);
            if (!isEdit){
                schedule.setCreatetime(System.currentTimeMillis()/1000);
            }
            schedule.setUpdatetime(System.currentTimeMillis()/1000);
            schedule.setRelated(scheduleRequest.getRelated());
            schedule.setShare(scheduleRequest.getShare());
            schedule.setFile(scheduleRequest.getFileList());
        }
        return schedule;
    }
}
