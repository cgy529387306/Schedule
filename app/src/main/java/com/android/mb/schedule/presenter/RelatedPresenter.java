package com.android.mb.schedule.presenter;

import android.text.TextUtils;

import com.android.mb.schedule.base.BaseMvpPresenter;
import com.android.mb.schedule.entitys.RelatedBean;
import com.android.mb.schedule.presenter.interfaces.IRelatedPresenter;
import com.android.mb.schedule.retrofit.http.exception.ApiException;
import com.android.mb.schedule.retrofit.http.exception.NoNetWorkException;
import com.android.mb.schedule.service.ScheduleMethods;
import com.android.mb.schedule.view.interfaces.IRelatedView;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public class RelatedPresenter extends BaseMvpPresenter<IRelatedView> implements IRelatedPresenter {

    @Override
    public void getRelated(Map<String, Object> requestMap) {
        Observable observable = ScheduleMethods.getInstance().getRelated(requestMap);
        toSubscribe(observable,  new Subscriber<List<RelatedBean>>() {
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
            public void onNext(List<RelatedBean> result) {
                if (mMvpView!=null){
                    mMvpView.getSuccess(result);
                }
            }
        });
    }
}
