package com.android.mb.schedule.retrofit.http.interceptor;

import com.android.mb.schedule.app.MBApplication;
import com.android.mb.schedule.retrofit.http.exception.NoNetWorkException;
import com.android.mb.schedule.utils.NetworkHelper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class NoNetWorkInterceptor  implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        boolean isConnected  = NetworkHelper.isNetworkAvailable(MBApplication.getInstance());
        if(isConnected) {
            return chain.proceed(chain.request());
        }else {
            throw new NoNetWorkException("网络存在问题,请检查网络状态");
        }
    }
}
