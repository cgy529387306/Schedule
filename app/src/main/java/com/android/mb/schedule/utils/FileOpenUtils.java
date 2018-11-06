package com.android.mb.schedule.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;

public class FileOpenUtils {

    /** 使用自定义方法打开文件 */
    public static void openFile(Activity activityFrom, File file) {
        Intent intent = new Intent();
        intent.setDataAndType(Uri.fromFile(file), getMimeTypeFromFile(file));//也可使用 Uri.parse("file://"+file.getAbsolutePath());
        //以下设置都不是必须的
        intent.setAction(Intent.ACTION_VIEW);// 系统根据不同的Data类型，通过已注册的对应Application显示匹配的结果。
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//系统会检查当前所有已创建的Task中是否有该要启动的Activity的Task
        //若有，则在该Task上创建Activity；若没有则新建具有该Activity属性的Task，并在该新建的Task上创建Activity。
        intent.addCategory(Intent.CATEGORY_DEFAULT);//按照普通Activity的执行方式执行
        activityFrom.startActivity(intent);
    }

    /**使用自定义方法获得文件的MIME类型 */
    public static String getMimeTypeFromFile(File file) {
        String type = "*/*";
        String fName = file.getName();
        //获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex > 0) {
            //获取文件的后缀名
            String end = fName.substring(dotIndex, fName.length()).toLowerCase(Locale.getDefault());
            //在MIME和文件类型的匹配表中找到对应的MIME类型。
            HashMap<String, String> map = MyMimeMap.getMimeMap();
            if (!TextUtils.isEmpty(end) && map.keySet().contains(end)) {
                type = map.get(end);
            }
        }
        Log.i("bqt", "我定义的MIME类型为：" + type);
        return type;
    }


    /** 使用系统API，根据url获得对应的MIME类型 */
    public static String getMimeTypeFromUrl(String url) {
        String type = null;
        //使用系统API，获取URL路径中文件的后缀名（扩展名）
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            //使用系统API，获取MimeTypeMap的单例实例，然后调用其内部方法获取文件后缀名（扩展名）所对应的MIME类型
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        Log.i("bqt", "系统定义的MIME类型为：" + type);
        return type;
    }


    /** 使用系统API打开文件 */
    public static void openFileByUri(Activity activityFrom, String uri) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//If set, this activity will become the start of a new task on this history stack.
        intent.setAction(Intent.ACTION_VIEW);// it is the generic action you can use on a piece of data to get the most reasonable合适的 thing to occur
        intent.addCategory(Intent.CATEGORY_DEFAULT);//Set if the activity should be an option选项 for the default action to perform on a piece of data
        intent.setDataAndType(Uri.parse(uri), getMimeTypeFromUrl(uri));//Set the data for the intent along with an explicit指定的、明确的 MIME data type
        activityFrom.startActivity(intent);
    }
}
