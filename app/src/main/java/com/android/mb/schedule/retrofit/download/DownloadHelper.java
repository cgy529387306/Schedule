package com.android.mb.schedule.retrofit.download;

import java.io.File;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Chenwy on 2018/1/5 16:57
 */

public class DownloadHelper {
    private FileDownloadCallback<File> fileDownloadCallback;
    /**
     * 管理Rxjava。
     */
    private CompositeSubscription mCompositeSubscription;

    private static class DownloadHelperHolder {
        private static final DownloadHelper INSTANCE = new DownloadHelper();
    }

    public static DownloadHelper getInstance() {
        return DownloadHelperHolder.INSTANCE;
    }

    public void downloadFile(String url, final String destDir, final String fileName, final FileDownloadCallback<File> fileDownLoadObserver) {
        release();
        this.fileDownloadCallback = fileDownLoadObserver;
        fileDownLoadObserver.registerProgressEventBus();
        HttpMethods.getInstance()
                .getDownloadService()
                .download(url)
                .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(Schedulers.io()) //指定线程保存文件
                .observeOn(Schedulers.computation())
                .map(new Func1<ResponseBody, File>() {

                    @Override
                    public File call(ResponseBody responseBody) {
                        try {
                            return fileDownLoadObserver.saveFile(responseBody.byteStream(), destDir, fileName);
                        }catch (Exception e){
                            e.printStackTrace();
                            return null;
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<File>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fileDownLoadObserver.onDownLoadFail(e);
                    }

                    @Override
                    public void onNext(File result) {
                        fileDownLoadObserver.onDownLoadSuccess(result);
                    }
                });
    }

    public void release() {
        onUnsubscribe();
    }

    protected void onUnsubscribe() {
        if (fileDownloadCallback != null) {
            fileDownloadCallback.unRegisterProgressEventBus();
            fileDownloadCallback = null;
        }
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
            mCompositeSubscription = null;
        }
    }

    protected <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {

        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }

        mCompositeSubscription.add(o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s));
    }
}
