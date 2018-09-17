package com.android.mb.schedule.retrofit.download;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Chenwy on 2017/10/16.
 */

public class HttpMethods {
    private Retrofit retrofit;
    private static final int TIME_OUT = 15;
    private DownloadService downloadService;


    public HttpMethods() {
    }

    private static class HttpMethodsHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    public static HttpMethods getInstance() {
        return HttpMethodsHolder.INSTANCE;
    }

    public void init(){
        init("https://shop.5979wenhua.com");
    }

    public void init(String baseUrl) {
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .addInterceptor(new DownloadInterceptor())
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        downloadService = retrofit.create(DownloadService.class);
    }

    public DownloadService getDownloadService() {
        return downloadService;
    }
}
