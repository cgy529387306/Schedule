package com.android.mb.schedule.presenter;

import android.text.TextUtils;

import com.android.mb.schedule.base.BaseMvpPresenter;
import com.android.mb.schedule.entitys.TreeData;
import com.android.mb.schedule.entitys.UserBean;
import com.android.mb.schedule.presenter.interfaces.IPersonPresenter;
import com.android.mb.schedule.retrofit.http.exception.ApiException;
import com.android.mb.schedule.retrofit.http.exception.NoNetWorkException;
import com.android.mb.schedule.api.ScheduleMethods;
import com.android.mb.schedule.view.interfaces.IPersonView;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cgy on 2018/2/11 0011.
 */

public class PersonPresenter extends BaseMvpPresenter<IPersonView> implements IPersonPresenter {


    @Override
    public void getOfficeList() {
        Observable observable = ScheduleMethods.getInstance().getOfficeList();
        toSubscribe(observable,  new Subscriber<TreeData>() {
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
            public void onNext(TreeData result) {
                if (mMvpView!=null){
                    mMvpView.getOrgSuccess(result);
                }
            }
        });
    }

    @Override
    public void getPersons() {
        Observable observable = ScheduleMethods.getInstance().getPersons();
        toSubscribe(observable,  new Subscriber<List<UserBean>>() {
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
            public void onNext(List<UserBean> result) {
                if (mMvpView!=null){
                    mMvpView.getPersonSuccess(result);
                }
            }
        });
    }
}
