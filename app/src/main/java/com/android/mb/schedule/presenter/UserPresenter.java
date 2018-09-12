package com.android.mb.schedule.presenter;

import android.text.TextUtils;

import com.android.mb.schedule.base.BaseMvpPresenter;
import com.android.mb.schedule.presenter.interfaces.IUserPresenter;
import com.android.mb.schedule.retrofit.http.exception.ApiException;
import com.android.mb.schedule.service.ScheduleMethods;
import com.android.mb.schedule.view.interfaces.IUserView;

import java.io.File;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

public class UserPresenter extends BaseMvpPresenter<IUserView> implements IUserPresenter {

    @Override
    public void getUserInfo() {
        Observable observable = ScheduleMethods.getInstance().getUserInfo();
        toSubscribe(observable,  new Subscriber<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if(mMvpView!=null){
                    if (e instanceof ApiException && !TextUtils.isEmpty(e.getMessage())){
                        mMvpView.showToastMessage(e.getMessage());
                    }
                }
            }

            @Override
            public void onNext(Object list) {
                if (mMvpView!=null){
                    mMvpView.getUserInfoSuccess();
                }
            }
        });
    }

    @Override
    public void setProfile(Map<String, Object> requestMap, File file) {
        Observable observable = ScheduleMethods.getInstance().setProfile(requestMap,file);
        toSubscribe(observable,  new Subscriber<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if(mMvpView!=null){
                    if (e instanceof ApiException && !TextUtils.isEmpty(e.getMessage())){
                        mMvpView.showToastMessage(e.getMessage());
                    }
                }
            }

            @Override
            public void onNext(Object list) {
                if (mMvpView!=null){
                    mMvpView.getUserInfoSuccess();
                }
            }
        });
    }
}
