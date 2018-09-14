package com.android.mb.schedule.presenter;

import android.text.TextUtils;

import com.android.mb.schedule.base.BaseMvpPresenter;
import com.android.mb.schedule.entitys.LoginData;
import com.android.mb.schedule.presenter.interfaces.IUserPresenter;
import com.android.mb.schedule.retrofit.http.exception.ApiException;
import com.android.mb.schedule.retrofit.http.exception.NoNetWorkException;
import com.android.mb.schedule.service.ScheduleMethods;
import com.android.mb.schedule.view.interfaces.IUserView;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;

public class UserPresenter extends BaseMvpPresenter<IUserView> implements IUserPresenter {

    @Override
    public void getUserInfo() {
        Observable observable = ScheduleMethods.getInstance().getUserInfo();
        toSubscribe(observable,  new Subscriber<LoginData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if(mMvpView!=null){
                    if (e instanceof ApiException && !TextUtils.isEmpty(e.getMessage())){
                        mMvpView.showToastMessage(e.getMessage());
                    }else if (e instanceof NoNetWorkException && !TextUtils.isEmpty(e.getMessage())){
                        mMvpView.showToastMessage(e.getMessage());
                    }
                }
            }

            @Override
            public void onNext(LoginData result) {
                if (mMvpView!=null){
                    mMvpView.getUserInfoSuccess(result);
                }
            }
        });
    }

    @Override
    public void setProfile(Map<String, Object> requestMap) {
        Observable observable = ScheduleMethods.getInstance().setProfile(requestMap);
        toSubscribe(observable,  new Subscriber<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if(mMvpView!=null){
                    if (e instanceof ApiException && !TextUtils.isEmpty(e.getMessage())){
                        mMvpView.showToastMessage(e.getMessage());
                    }else if (e instanceof NoNetWorkException && !TextUtils.isEmpty(e.getMessage())){
                        mMvpView.showToastMessage(e.getMessage());
                    }
                }
            }

            @Override
            public void onNext(Object result) {
                if (mMvpView!=null){
                    mMvpView.setSuccess();
                }
            }
        });
    }
}
