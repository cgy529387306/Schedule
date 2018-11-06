package com.android.mb.schedule.retrofit.download;

import android.support.annotation.NonNull;

import com.android.mb.schedule.api.BaseHttp;
import com.android.mb.schedule.utils.Helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Description: 下载工具类
 * Created by jia on 2017/11/30.
 * 人之所以能，是相信能
 */
public class DownloadUtils {

    private static final String TAG = "DownloadUtils";

    private static final int DEFAULT_TIMEOUT = 60;

    private Retrofit retrofit;

    private JsDownloadListener listener;


    public DownloadUtils(JsDownloadListener listener) {
        this.listener = listener;
        JsDownloadInterceptor mInterceptor = new JsDownloadInterceptor(listener);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(mInterceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BaseHttp.BASE_URL)
                .client(httpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    /**
     * 开始下载
     *
     * @param url
     * @param filePath
     */
    public void download(@NonNull String url, final String filePath, final String fileName) {

        listener.onStartDownload();

        // subscribeOn()改变调用它之前代码的线程
        // observeOn()改变调用它之后代码的线程
        retrofit.create(DownloadService.class)
                .download(url)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new Func1<ResponseBody, InputStream>() {

                    @Override
                    public InputStream call(ResponseBody responseBody) {
                        return responseBody.byteStream();
                    }
                })
                .observeOn(Schedulers.computation()) // 用于计算任务
                .doOnNext(new Action1<InputStream>() {
                    @Override
                    public void call(InputStream inputStream) {
                        writeFile(inputStream, filePath,fileName);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<InputStream>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if(Helper.isNotEmpty(e.getMessage())){
                            listener.onFail(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(InputStream inputStream) {
                        listener.onFinishDownload();
                    }
                });

    }

    /**
     * 将输入流写入文件
     *
     * @param inputString
     * @param filePath
     */
    private void writeFile(InputStream inputString, String filePath,String fileName) {

        FileOutputStream fos = null;
        byte[] buf = new byte[1024];
        int len;
        try {
            File dir  = new File(filePath);
            if (!dir .exists()) {
                dir .mkdirs();
            }
            File file = new File(dir, fileName);
            if (file.exists()) {
                file.delete();
            }
            fos = new FileOutputStream(file);
            while ((len = inputString.read(buf)) != -1) {
                fos.write(buf,0,len);
            }
            fos.flush();
        } catch (FileNotFoundException e) {
            listener.onFail("FileNotFoundException");
        } catch (IOException e) {
            listener.onFail("IOException");
        }finally {
            try {
                if (inputString != null) inputString.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
