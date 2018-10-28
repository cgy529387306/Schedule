package com.android.mb.schedule.presenter;

import android.text.TextUtils;

import com.android.mb.schedule.base.BaseMvpPresenter;
import com.android.mb.schedule.entitys.TreeData;
import com.android.mb.schedule.presenter.interfaces.IUnderPresenter;
import com.android.mb.schedule.retrofit.http.exception.ApiException;
import com.android.mb.schedule.retrofit.http.exception.NoNetWorkException;
import com.android.mb.schedule.api.ScheduleMethods;
import com.android.mb.schedule.view.interfaces.IUnderView;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cgy on 2018/2/11 0011.
 */

public class UnderPresenter extends BaseMvpPresenter<IUnderView> implements IUnderPresenter {


    @Override
    public void getOfficeList() {
        Observable observable = ScheduleMethods.getInstance().getUnderList();
        toSubscribe(observable,  new Subscriber<TreeData>() {
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
            public void onNext(TreeData result) {
                if (mMvpView!=null){
                    mMvpView.getOrgSuccess(result);
                }
            }
        });
    }

}
