package com.android.mb.schedule.presenter;

import android.text.TextUtils;

import com.android.mb.schedule.api.ScheduleMethods;
import com.android.mb.schedule.app.MBApplication;
import com.android.mb.schedule.base.BaseMvpPresenter;
import com.android.mb.schedule.entitys.KpiRequest;
import com.android.mb.schedule.presenter.interfaces.IKpiPresenter;
import com.android.mb.schedule.retrofit.http.exception.ApiException;
import com.android.mb.schedule.utils.ProjectHelper;
import com.android.mb.schedule.view.interfaces.IKpiView;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;

public class KpiPresenter extends BaseMvpPresenter<IKpiView> implements IKpiPresenter {

    @Override
    public void addKpi(KpiRequest request) {
        Observable observable = ScheduleMethods.getInstance().addKpi(request);
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
            public void onNext(Object result) {
                if (mMvpView!=null){
                    mMvpView.addSuccess(result);
                }
            }
        });
    }

    @Override
    public void editKpi(KpiRequest request) {

    }

    @Override
    public void viewKpi(Map<String, Object> requestMap) {
        Observable observable = ScheduleMethods.getInstance().getKpi(requestMap);
        toSubscribe(observable,  new Subscriber<KpiRequest>() {
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
            public void onNext(KpiRequest result) {
                if (mMvpView!=null){
                    mMvpView.getSuccess(result);
                }
            }
        });
    }
}
