package com.android.mb.schedule.presenter;

import android.text.TextUtils;

import com.android.mb.schedule.base.BaseMvpPresenter;
import com.android.mb.schedule.entitys.LoginData;
import com.android.mb.schedule.presenter.interfaces.IHomePresenter;
import com.android.mb.schedule.presenter.interfaces.IUserPresenter;
import com.android.mb.schedule.retrofit.http.exception.ApiException;
import com.android.mb.schedule.service.ScheduleMethods;
import com.android.mb.schedule.view.interfaces.IHomeView;
import com.android.mb.schedule.view.interfaces.IUserView;

import java.io.File;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

public class HomePresenter extends BaseMvpPresenter<IHomeView> implements IHomePresenter {

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

}
