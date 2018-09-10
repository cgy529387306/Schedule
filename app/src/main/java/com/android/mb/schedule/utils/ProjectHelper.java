package com.android.mb.schedule.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.view.View;
import android.view.ViewParent;

import com.google.gson.Gson;

import java.util.Map;
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

}
