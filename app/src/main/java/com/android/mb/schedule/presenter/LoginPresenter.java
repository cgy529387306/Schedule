package com.android.mb.schedule.presenter;

import android.text.TextUtils;

import com.android.mb.schedule.base.BaseMvpPresenter;
import com.android.mb.schedule.entitys.LoginData;
import com.android.mb.schedule.presenter.interfaces.ILoginPresenter;
import com.android.mb.schedule.retrofit.http.exception.ApiException;
import com.android.mb.schedule.retrofit.http.exception.NoNetWorkException;
import com.android.mb.schedule.api.ScheduleMethods;
import com.android.mb.schedule.view.interfaces.ILoginView;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cgy on 2018/2/11 0011.
 */

public class LoginPresenter extends BaseMvpPresenter<ILoginView> implements ILoginPresenter {


    @Override
    public void userLogin(Map<String,Object> requestMap) {
        Observable observable = ScheduleMethods.getInstance().userLogin(requestMap);
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
                    mMvpView.loginSuccess(result);
                }
            }
        });
    }
}
