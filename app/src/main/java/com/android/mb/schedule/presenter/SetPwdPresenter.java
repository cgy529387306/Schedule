package com.android.mb.schedule.presenter;

import android.text.TextUtils;

import com.android.mb.schedule.base.BaseMvpPresenter;
import com.android.mb.schedule.presenter.interfaces.ISetPwdPresenter;
import com.android.mb.schedule.retrofit.http.exception.ApiException;
import com.android.mb.schedule.service.ScheduleMethods;
import com.android.mb.schedule.view.interfaces.ISetPwdView;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cgy on 2018/2/11 0011.
 */

public class SetPwdPresenter extends BaseMvpPresenter<ISetPwdView> implements ISetPwdPresenter {


    @Override
    public void setPwd(Map<String, Object> requestMap) {
        Observable observable = ScheduleMethods.getInstance().resetPwd(requestMap);
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
                }
            }
        });
    }
}
