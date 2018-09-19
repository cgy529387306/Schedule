package com.android.mb.schedule.presenter;

import android.text.TextUtils;

import com.android.mb.schedule.base.BaseMvpPresenter;
import com.android.mb.schedule.entitys.ShareData;
import com.android.mb.schedule.presenter.interfaces.IMySharePresenter;
import com.android.mb.schedule.presenter.interfaces.ISharePresenter;
import com.android.mb.schedule.retrofit.http.exception.ApiException;
import com.android.mb.schedule.retrofit.http.exception.NoNetWorkException;
import com.android.mb.schedule.service.ScheduleMethods;
import com.android.mb.schedule.view.interfaces.IMyShareView;
import com.android.mb.schedule.view.interfaces.IShareView;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public class MySharePresenter extends BaseMvpPresenter<IMyShareView> implements IMySharePresenter {

    @Override
    public void getMyShare() {
        Observable observable = ScheduleMethods.getInstance().getIShare();
        toSubscribe(observable,  new Subscriber<List<ShareData>>() {
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
            public void onNext(List<ShareData> result) {
                if (mMvpView!=null){
                    mMvpView.getSuccess(result);
                }
            }
        });
    }
}
